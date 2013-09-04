package au.org.paperminer.ws;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.org.paperminer.bean.HistoryFacade;
import au.org.paperminer.dao.PmQueriesDAOImpl;
import au.org.paperminer.model.PmQueries;
import au.org.paperminer.model.PmUsers;

@Controller
@RequestMapping("api")
public class WebController {

	@Autowired(required = true)
	private SessionFactory sessionFactory;

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
}
