package au.org.paperminer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import au.org.paperminer.model.PmQueries;
import au.org.paperminer.model.PmUsers;

@Component
@Scope("session")
public class HistoryFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8576599199689391358L;
	private List<PmQueries> queries = new ArrayList<PmQueries>();
	private PmUsers users;
	
	static final Logger log = Logger.getLogger(HistoryFacade.class);
	
	public List<PmQueries> getQueries() {
		return queries;
	}
	public void setQueries(List<PmQueries> queries) {
		this.queries = queries;
	}
	public PmUsers getUsers() {
		return users;
	}
	public void setUsers(PmUsers users) {
		this.users = users;
	}
	
	public void addSimpleHistory(String keywords) {
		log.info("Record search term " + keywords);
		PmQueries query = new PmQueries();
		query.setDateCreated(new Date());
		query.setDateLastRun(new Date());
		query.setDescr(keywords);
		query.setPmUsers(getUsers());
		query.setQueryType("s");
		query.setQuery(keywords);	
		
		queries.add(query);
	}
}
