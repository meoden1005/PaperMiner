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
import au.org.paperminer.model.PmQueries;
import au.org.paperminer.model.PmUsers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:test-resources/applicationContext.xml")
public class TestHistory {

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

}
