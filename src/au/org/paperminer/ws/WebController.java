package au.org.paperminer.ws;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
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

@Controller
@RequestMapping("api")
public class WebController {

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	@Autowired(required = false)
	private final static RAMDirectory ramDirectory = new RAMDirectory();

	@Autowired(required = false)
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
	@ResponseStatus(value = HttpStatus.OK)
	public String fileDownload(@RequestBody String requestBody,
			HttpServletResponse response) throws IOException {
		System.out.println("Take in body message");
		byte[] output = requestBody.getBytes();

		// response.setContentType("application/download");
		// response.setHeader("Content-Disposition",
		// "attachment; filename=\"rawdata.json\"");
		// response.getOutputStream().write(output);
		// response.flushBuffer();

		String name = "rawdata" + Math.random();
		String path = context.getRealPath(name + ".json");

		FileWriter fw = new FileWriter(path);
		fw.write(requestBody);
		fw.close();

		return name + ".json";
		/*
		 * File temp = File.createTempFile(name, ".json"); FileOutputStream
		 * fileOut = new FileOutputStream(temp); fileOut.write(output);
		 * fileOut.close();
		 */
	}

	// http://localhost:8080/PaperMiner/ws/api/getpage
	@RequestMapping(value = "/getpage", method = RequestMethod.POST, consumes = "text/plain")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String getPage(@RequestBody String requestBody) throws Exception {
		// "http://trove.nla.gov.au/ndp/del/article/124673222?searchTerm=kingkong+china"
		Document doc = Jsoup.connect(requestBody).get();
		Element element = doc.getElementById("initiateCite");

		Document doc1 = Jsoup.connect(
				"http://trove.nla.gov.au" + element.attr("href")).get();

		Elements links = doc1.getElementsByTag("a");

		Pattern pattern = Pattern.compile("http://nla.gov.au/nla.news-page");

		String pageNumber = new String();

		for (Element link : links) {
			String result = link.attr("href");
			Matcher matcher = pattern.matcher(result);
			if (matcher.find()) {
				System.out.println(result);
				System.out.println(matcher.replaceAll(new String()));
				pageNumber = matcher.replaceAll(new String());
				break;
			}
		}

		Document doc2 = Jsoup.connect(
				"http://trove.nla.gov.au/ndp/del/page/" + pageNumber
						+ "?zoomLevel=1").get();
		Elements elements1 = doc2.getElementsByAttributeValue("title",
				"Download a PDF containing all pages from this issue");
		Element lookingElement = elements1.get(0);
		System.out.println(lookingElement.attr("href"));

		String printNumber = lookingElement.attr("href");
		return printNumber.replaceAll("/ndp/del/printIssue/", new String());

	}

	// ****** ranking******
	static String soundexIndex = "";

	// http://localhost:8080/PaperMiner/ws/api/getrank
	@RequestMapping(value = "/getrank", method = RequestMethod.POST, consumes = "text/plain")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public String getRank(@RequestBody String requestBody) throws Exception {
		// call your method to rank result in here, after that return the result
		// back to interface
		// String query = "fire";
		double snippetScore = 0;
		
		SortedMap<Double, String> treeMap = 
			    new TreeMap<Double,String>(new Comparator<Double>()
			    {
			        public int compare(Double o1, Double o2)
			        {
			            return o1.compareTo(o2);
			        } 
			});
		Directory index = ramDirectory;

		String city = (String) requestBody.subSequence(0,
				requestBody.indexOf('_'));
		String region = (String) requestBody.subSequence(
				requestBody.indexOf('_'), requestBody.indexOf('*'));
		String query = (String) requestBody.subSequence(
				requestBody.indexOf('*') + 1, requestBody.indexOf('#'));
		requestBody = (String) requestBody.subSequence(
				requestBody.indexOf('#') + 1, requestBody.length());

		String result = null;
		// System.out.println("Please wait, fish dictionary is being indexed...");

		JSONArtifact jsonArt = JSON.parse(requestBody);
		// jsonArt.toString();
		JSONArray testArray = (JSONArray) jsonArt;
		// testArray.size();
		String line = null;

		for (int k = 0; k < testArray.size(); k++) {
			line = testArray.getJSONObject(k).getJSONObject("data")
					.getString("snippet");
			Directory rankingINDEX = new RAMDirectory();
			StringTokenizer tokenizer = new StringTokenizer(line);

			// System.out.println(line);

			while (tokenizer.hasMoreTokens()) {
				String nextToken = tokenizer.nextToken();
				// indexing each token and its soundex
				indexTokenAndSoundex(nextToken, Soundex.soundex(nextToken), index);
				indexToken(nextToken, rankingINDEX);

			}

			snippetScore = searchIndex(query, city, region, index, rankingINDEX);
            treeMap.put(snippetScore, line);
            
          
			result += "[" + snippetScore + "]" + line + "<br/>Query suggestion:"
					+ soundexIndex + "\r\n"+"<br/>"+"\r\n";
			soundexIndex=""; 
			
            }
		/* 
		  Iterator ittwo = treeMap.entrySet().iterator();
          while (ittwo.hasNext()) {
          Map.Entry pairs = (Map.Entry)ittwo.next();
          result +=(pairs.getKey() + " = " + pairs.getValue()) + "Query suggestion: "
					+ soundexIndex + "\r\n";*/
           
          

		return result;
	}

	public static double searchIndex(String searchString, String city,
			String region, Directory index, Directory index2)
			throws IOException, ParseException,
			org.apache.lucene.queryparser.classic.ParseException {
		String stateBrief = null;

		if (region.equalsIgnoreCase("Queensland")) {
			stateBrief = "QLD";
		}
		if (region.equalsIgnoreCase("New South Wales")) {
			stateBrief = "NSW";
		}
		if (region.equalsIgnoreCase("South Australia")) {
			stateBrief = "SA";
		}
		if (region.equalsIgnoreCase("Victoria")) {
			stateBrief = "VIC";
		}
		if (region.equalsIgnoreCase("Australian Capital Territory")) {
			stateBrief = "ACT";
		}
		if (region.equalsIgnoreCase("Tasmania")) {
			stateBrief = "TAS";
		}

		double snippetScore = 0;

		IndexReader indexReader = DirectoryReader.open(index);
		IndexSearcher SearcherInIndexSoundex = new IndexSearcher(indexReader);
		IndexReader indexReader2 = DirectoryReader.open(index2);
		IndexSearcher SearcherInIndexToken = new IndexSearcher(indexReader2);

		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

		String soundexTerm = Soundex.soundex(searchString);
		Query querySoundex = new QueryParser(Version.LUCENE_42, "Soundex", analyzer)
				.parse(soundexTerm + "*");

		int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(
				hitsPerPage, true);

		SearcherInIndexSoundex.search(querySoundex, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		if (soundexTerm.length() > 3) {
			// snippetScore += hits.length;
			for (int i = 0; i < hits.length; ++i) {

				
				soundexIndex += indexReader.document(hits[i].doc).get("Term")
						+ " - ";
				 snippetScore += hits[i].score;
				 

			}
		}

		Query queryTerm = new QueryParser(Version.LUCENE_42, "Term", analyzer)
				.parse(searchString + "*");

		TopScoreDocCollector collector2 = TopScoreDocCollector.create(
				hitsPerPage, true);

		SearcherInIndexToken.search(queryTerm, collector2);
		ScoreDoc[] hits2 = collector2.topDocs().scoreDocs;

		for (int i = 0; i < hits2.length; ++i) {

			snippetScore += hits2[i].score;

		}

		wordNet wn = new wordNet();
		List<String> syn = wn.getWordNet(searchString);

		for (int j = 0; j < syn.size(); j++) {
			Query queryRelevence = new QueryParser(Version.LUCENE_42, "Term", analyzer)
					.parse(syn.get(j) + "*");

			TopScoreDocCollector collector3 = TopScoreDocCollector.create(
					hitsPerPage, true);

			SearcherInIndexToken.search(queryRelevence, collector3);
			ScoreDoc[] hits3 = collector2.topDocs().scoreDocs;
			long termFreq = 0;

			for (int i = 0; i < hits3.length; ++i) {

				termFreq += indexReader2.getTermVector(hits3[i].doc, "Term").getSumTotalTermFreq();

			}

			if(termFreq>0)
			snippetScore += (hits3.length / termFreq);

		}
		Query userLocation = new QueryParser(Version.LUCENE_42, "Term", analyzer)
				.parse(city+" "+stateBrief +" "+region + "*");

		TopScoreDocCollector collector4 = TopScoreDocCollector.create(
				hitsPerPage, true);

		SearcherInIndexToken.search(userLocation, collector4);
		ScoreDoc[] hits4 = collector4.topDocs().scoreDocs;

		for (int i = 0; i < hits4.length; ++i) {
			snippetScore += 2*hits4[i].score;
		}

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42,
				analyzer);
		IndexWriter indexWriter = new IndexWriter(index2, config);
		indexWriter.deleteAll();

		return snippetScore;

	}

	public static void indexTokenAndSoundex(String token, String soundex, Directory index)
			throws IOException {

		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42,
				analyzer);
		IndexWriter indexWriter = new IndexWriter(index, config);
		org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();

		document.add(new TextField("Term", token, Field.Store.YES));
		document.add(new TextField("Soundex", soundex, Field.Store.YES));

		indexWriter.addDocument(document);
		indexWriter.close();

	}

	public static void indexToken(String token, Directory index)
			throws IOException {

		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_42,
				analyzer);
		IndexWriter indexWriter = new IndexWriter(index, config);
		org.apache.lucene.document.Document document = new org.apache.lucene.document.Document();

		document.add(new TextField("Term", token, Field.Store.YES));

		indexWriter.addDocument(document);
		indexWriter.close();

	}

}
