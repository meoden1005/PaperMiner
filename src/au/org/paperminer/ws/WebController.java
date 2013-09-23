package au.org.paperminer.ws;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import au.org.paperminer.bean.HistoryFacade;
import au.org.paperminer.dao.PmQueriesDAOImpl;
import au.org.paperminer.model.PmQueries;
import au.org.paperminer.model.PmUsers;

@Controller
@RequestMapping("api")
public class WebController {

	@Autowired(required = true)
	private SessionFactory sessionFactory;
	
	@Autowired
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
		
		return context.getContextPath()+"/"+name+".json";
		/*File temp = File.createTempFile(name, ".json");
		FileOutputStream fileOut = new FileOutputStream(temp);
		fileOut.write(output);
		fileOut.close();*/
	}
}
