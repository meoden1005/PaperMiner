package au.org.paperminer.ws;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.wink.json4j.JSON;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONArtifact;
import org.apache.wink.json4j.JSONObject;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import au.org.paperminer.dao.PmQueriesDAOImpl;
import au.org.paperminer.model.PmQueries;
import au.org.paperminer.model.PmUsers;

import com.google.gson.Gson;

@Controller
@RequestMapping("api")
public class WebController {

	@Autowired(required = true)
	private SessionFactory sessionFactory;
	
	@Autowired(required=false)
	ServletContext context;

	@RequestMapping(value = "simplehistory")
	public @ResponseBody
	String add(@RequestParam("keywords") String keywords) {
		// historyFacade.addSimpleHistory(keywords);

		return new String();
	}

	@RequestMapping(value = "test")
	public @ResponseBody
	String test() {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());
		return String.valueOf(pmQueriesDAO.countAll());
	}

	@Transactional(readOnly = true)
	@RequestMapping(value = "test1", produces = "application/json")
	public @ResponseBody
	List<PmQueries> test1() {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		return pmQueriesDAO.findAll();
	}

	// http://localhost:8080/PaperMiner/ws/api/test2/213213,123123
	@Transactional(readOnly = true)
	@RequestMapping(value = "test2/{data}", produces = "application/json")
	public @ResponseBody
	List<PmQueries> test2(@PathVariable("data") List<String> data) {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		return pmQueriesDAO.findAll();
	}

	@Transactional
	@RequestMapping(value = "saveQuery/userId/{userId}/queryDescr/{queryDescr}/queryType/{queryType}/queryTotal/{queryTotal}/query/{queryTerm}")
	public @ResponseBody
	String saveQuery(@PathVariable("userId") String userId,
			@PathVariable("queryDescr") String queryDescr,
			@PathVariable("queryType") String queryType,
			@PathVariable("queryTotal") String queryTotal,
			@PathVariable("queryTerm") String queryTerm) {
		try {
			PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
			pmQueriesDAO.setSession(sessionFactory.openSession());

			PmQueries query = new PmQueries();

			PmUsers user = new PmUsers();
			user.setId(Integer.valueOf(userId));

			query.setPmUsers(user);
			query.setDateCreated(new Date());
			query.setDateLastRun(new Date());
			query.setDescr(queryDescr);

			// careful for expansion
			query.setQueryType(queryType);

			// fix this one later
			query.setTotalLastRun(Integer.valueOf(queryTotal));
			query.setQuery(queryTerm);

			pmQueriesDAO.save(query);

			return "Save successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Save fail";
		}
		
	}
	
	@RequestMapping(value = "/filedownload", method = RequestMethod.POST, consumes = "text/plain")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public String fileDownload(@RequestBody String requestBody, HttpServletResponse response) throws IOException {
		System.out.println("Take in body message");
		byte[] output = requestBody.getBytes();
		
		//response.setContentType("application/download");
		//response.setHeader("Content-Disposition", "attachment; filename=\"rawdata.json\"");
		//response.getOutputStream().write(output);
		//response.flushBuffer();
		
		String name = "rawdata"+Math.random();
		String path = context.getRealPath(name + ".json");
		
		FileWriter fw = new FileWriter(path);
		fw.write(requestBody);
		fw.close();
		
		return name+".json";
		/*File temp = File.createTempFile(name, ".json");
		FileOutputStream fileOut = new FileOutputStream(temp);
		fileOut.write(output);
		fileOut.close();*/
	}

	
	// http://localhost:8080/PaperMiner/ws/api/getpage
	@RequestMapping(value = "/getpage", method = RequestMethod.POST, consumes = "text/plain")
	@ResponseBody
	@ResponseStatus(value=HttpStatus.OK)
	public String getPage(@RequestBody String requestBody) throws Exception {
		//"http://trove.nla.gov.au/ndp/del/article/124673222?searchTerm=kingkong+china"
		Document doc = Jsoup.connect(requestBody).get();
		Element element = doc.getElementById("initiateCite");
		
		Document doc1 = Jsoup.connect("http://trove.nla.gov.au"+element.attr("href")).get();
		
		Elements links = doc1.getElementsByTag("a");
		
		Pattern pattern = Pattern.compile("http://nla.gov.au/nla.news-page");
		
		String pageNumber = new String();
		
		for(Element link : links){
			String result = link.attr("href");
			Matcher matcher = pattern.matcher(result);
			if(matcher.find()){
				System.out.println(result);
				System.out.println(matcher.replaceAll(new String()));
				pageNumber = matcher.replaceAll(new String());
				break;
			}
		}
		
		Document doc2 = Jsoup.connect("http://trove.nla.gov.au/ndp/del/page/" + pageNumber + "?zoomLevel=1").get();
		Elements elements1 = doc2.getElementsByAttributeValue("title", "Download a PDF containing all pages from this issue");	
		Element lookingElement = elements1.get(0);
		System.out.println(lookingElement.attr("href"));
		
		String printNumber = lookingElement.attr("href");
		return printNumber.replaceAll("/ndp/del/printIssue/", new String());
		
	}
	
	//****** ranking******
	
	// http://localhost:8080/PaperMiner/ws/api/getrank
		@RequestMapping(value = "/getrank", method = RequestMethod.POST, consumes = "text/plain")
		@ResponseBody
		@ResponseStatus(value = HttpStatus.OK)
		public String getRank(@RequestBody String requestBody) throws Exception {
			// call your method to rank result in here, after that return the result
			// back to interface

			String json = requestBody;
			System.out.println("Please wait, fish dictionary is being indexed...");
			JSON json1 = new JSON();
			JSONArtifact jsonArt = json1.parse(requestBody);
			//jsonArt.toString();
			JSONArray testArray = (JSONArray)jsonArt;
			//testArray.size();
			
			for(int i=0;i<testArray.size();i++)
			testArray.getJSONObject(i).getJSONObject("data").getString("snippet");
			//JSONObject testObj = (JSONObject)jsonArt;
			System.out.println(true);
			//Map<Integer, String> snippet=jsonParser(json);
		
			/*for(int k=0;k<19;k++) {
				Directory index = new RAMDirectory();
				// System.out.println(line);
				StringTokenizer tokenizer = new StringTokenizer(snippet.get(k));
				while (tokenizer.hasMoreTokens()) {

					indexToken(tokenizer.nextToken(), index);

				}
			
		
				searchIndex("fire", index);
			}*/

			return requestBody;
		}
		
		/*private static Map<Integer, String> jsonParser(String jsonInput) throws  JSONException, FileNotFoundException, IOException, ParseException {
			
			JSONParser parser=new JSONParser();
			Map<Integer,String> parseJson = new HashMap<Integer,String>();
			
			
			Object obj = parser.parse(jsonInput);
			
			//convert java object to JSON object
			Gson gson = new Gson();
			String json = gson.toJson(obj);
			JSONObject jsonObject = new JSONObject(json);
			
			//parsing JSONbject for finding snippet
			JSONObject response=(JSONObject) jsonObject.get("response");
			
			JSONArray responseArray= (JSONArray) response.getJSONArray("zone");
			JSONObject obresponseObj=(JSONObject)responseArray.getJSONObject(0);
			JSONObject records2=(JSONObject) obresponseObj.get("records");
			JSONArray recordsArray=(JSONArray) records2.getJSONArray("article");
			
			//System.out.println(response.toString());
			
			for(int i=0;i<20;i++){
			JSONObject article=(JSONObject)recordsArray.getJSONObject(i);
			
	        parseJson.put(i, (String) article.get("snippet"));
	        
				//System.out.println(article.get("snippet"));
			}
		
			
		return parseJson;
		
			
		}
		
		
		private static void indexToken(String token, Directory index)
				throws IOException {

			StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
			// Directory index = new RAMDirectory();//need index for search
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42,
					analyzer);
			IndexWriter indexWriter = new IndexWriter(index, config);
			org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();

			document.add(new TextField("Term", token, Field.Store.YES));
			indexWriter.addDocument(document);
			indexWriter.close();

		}
		
		private static void searchIndex(String searchString, Directory index)
				throws IOException, ParseException,
				org.apache.lucene.queryparser.classic.ParseException {

			// System.out.println("Searching for '" + searchString + "'...");

			IndexReader indexReader = DirectoryReader.open(index);
			IndexSearcher Searcher = new IndexSearcher(indexReader);

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

			Query q = new QueryParser(Version.LUCENE_42, "Term", analyzer)
					.parse(searchString + "*");

			int hitsPerPage = 10;
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					hitsPerPage, true);

			Searcher.search(q, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			if (hits.length > 0)
				System.out.println("Found " + hits.length + "hits for "
						+ searchString);
			double snippetScore = 0;
			for (int i = 0; i < hits.length; ++i) {
				System.out.println(i + 1 + "_ " + hits[i].toString() + " "
						+ indexReader.document(hits[i].doc).get("Term"));
				snippetScore += hits[i].score;

			}
			System.out.println(snippetScore);

		}*/
		
}
