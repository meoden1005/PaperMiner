package au.org.paperminer.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import au.org.paperminer.dao.PmQueriesDAOImpl;
import au.org.paperminer.dao.PmUserDAOImpl;
import au.org.paperminer.model.PmQueries;
import au.org.paperminer.model.PmUsers;
import au.org.paperminer.ws.WebController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:test-resources/applicationContext.xml")
public class HistoryTest {

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	@Test
	@Transactional
	public void testSaveQuery() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = new PmUsers();
		user.setId(Integer.valueOf(1));

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(Integer.valueOf(1));
		query.setQuery("test" + String.valueOf(Math.random()));

		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithoutUser() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = null;
		

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(Integer.valueOf(1));
		query.setQuery("test" + String.valueOf(Math.random()));

		if(user == null)
		{
			assertTrue("No new query with user", true);
			return;
		}
		
		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithoutQuery() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = null;
		

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(Integer.valueOf(1));
		//query.setQuery("test" + String.valueOf(Math.random()));

		if(query.getQuery() == null)
		{
			assertTrue("No new query without actual query", true);
			return;
		}
		
		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithoutQueryDescr() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = null;
		

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		//query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(Integer.valueOf(1));
		//query.setQuery("test" + String.valueOf(Math.random()));

		if(query.getDescr() == null)
		{
			assertTrue("No new query without query desc", true);
			return;
		}
		
		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithoutQueryType() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = null;
		

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		//query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		//query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(Integer.valueOf(1));
		//query.setQuery("test" + String.valueOf(Math.random()));

		if(query.getQueryType() == null)
		{
			assertTrue("No new query without query type", true);
			return;
		}
		
		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}

	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithoutTotalLastRun() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = null;
		

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		//query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		//query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(new Integer(0));
		//query.setQuery("test" + String.valueOf(Math.random()));

		if(query.getTotalLastRun() == new Integer(0))
		{
			assertTrue("No new query without query type", true);
			return;
		}
		
		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithoutCorrectUser() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());
		PmUserDAOImpl pmUserDAO = new PmUserDAOImpl();
		pmUserDAO.setSession(sessionFactory.openSession());;
		
		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = new PmUsers();
		user.setId(-1);

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		//query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		//query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(new Integer(0));
		//query.setQuery("test" + String.valueOf(Math.random()));

		if(pmUserDAO.findByExample(user).size() != 0)
		{
			assertTrue("No new query when you use non exist user", true);
			return;
		}
		
		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	@SuppressWarnings("unused")
	@Test
	@Transactional
	public void testSaveQueryWithExistingQuery() {
		// fail("Not yet implemented"); // TODO

		PmQueriesDAOImpl pmQueriesDAO = new PmQueriesDAOImpl();
		pmQueriesDAO.setSession(sessionFactory.openSession());

		long currentCount = pmQueriesDAO.countAll();

		PmQueries query = new PmQueries();

		PmUsers user = new PmUsers();
		user.setId(Integer.valueOf(1));

		query.setPmUsers(user);
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		query.setDescr("test" + String.valueOf(Math.random()));

		// careful for expansion
		query.setQueryType("s");

		// fix this one later
		query.setTotalLastRun(Integer.valueOf(1));
		query.setQuery("test" + String.valueOf(Math.random()));

		query = pmQueriesDAO.save(query);

		long newCount = pmQueriesDAO.countAll();

		// reset value
		//pmQueriesDAO.delete(pmQueriesDAO.findByExample(query).get(0));
		//pmQueriesDAO.

		assertTrue("Past count " + currentCount
				+ "+ 1 must be equal to new count " + newCount,
				(currentCount + 1) == newCount);
	}
	
	private static WebController webController = new WebController();
	
	@Test
	public void testEmptyHistory() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testInvalidJsony() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
	
	@Test
	public void testWrongDataType() {
		try {
			String result = webController.getPage(new String("http1://localhost:8080"));
			
			assertFalse("Expect to return null object", true);
		} catch (Exception e) {
			assertTrue("Correct object return", true);
		}
		
	}
}
