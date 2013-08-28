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
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	
	@RequestMapping(value="simplehistory")
	public @ResponseBody String add(@RequestParam("keywords")String keywords) {
		//historyFacade.addSimpleHistory(keywords);
		
		return new String();
	}
	
	@RequestMapping(value="test")
	public @ResponseBody String test() {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());
		return String.valueOf(pmQueriesDAO.countAll());		
	}
	
	@Transactional(readOnly = true)
	@RequestMapping(value="test1", produces = "application/json")
	public @ResponseBody List<PmQueries> test1() {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());
		
		return pmQueriesDAO.findAll();		
	}
	//http://localhost:8080/PaperMiner/ws/api/test2/213213,123123
	@Transactional(readOnly = true)
	@RequestMapping(value="test2/{data}", produces = "application/json")
	public @ResponseBody List<PmQueries> test2(@PathVariable("data") List<String> data) {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());
		
		return pmQueriesDAO.findAll();		
	}
	
	@RequestMapping(value="saveQuery/{data}/userId/{userId}")
	public void saveQuery(@ModelAttribute("data") List<String> dataList, @PathVariable("userId") String userId) {
		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());
		
		for (String data : dataList) {
			PmQueries query = new PmQueries();
			
			PmUsers user = new PmUsers();
			user.setId(Integer.valueOf(userId));
			
			query.setPmUsers(user);
			query.setDateCreated(new Date());
			query.setDateLastRun(new Date());
			query.setDescr(data);
			
			//careful for expansion
			query.setQueryType("s");
			
			//fix this one later
			query.setTotalLastRun((int)Math.random());
			query.setQuery("&zone" + data);
			
			
		}
		 
			
	}
}
