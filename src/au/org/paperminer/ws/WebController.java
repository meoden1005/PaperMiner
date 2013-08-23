package au.org.paperminer.ws;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.org.paperminer.bean.HistoryFacade;
import au.org.paperminer.dao.PmQueriesDAOImpl;
import au.org.paperminer.model.PmQueries;

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
	
}
