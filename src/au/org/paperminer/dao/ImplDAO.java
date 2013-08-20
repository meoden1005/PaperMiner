package au.org.paperminer.dao;

import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dao")
public class ImplDAO implements InterfaceDAO {

	static final Logger log = Logger.getLogger(ImplDAO.class);
	
	@Autowired(required=true)
	private HibernateUtil hibernateUtil;

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void search(int userId) {
		// TODO Auto-generated method stub
		return;
	}
	
	/*@Autowired
	private User user;
	
	public void insert() {
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		SessionFactory sf = hibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		//session.merge(user);
		session.saveOrUpdate(user);
		log.info("test");
		tx.commit();
		
		session.flush();
		session.close();
		System.out.println("save successfull");
		
	}

	public User search(int userId) {
		SessionFactory sf = hibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		
		User user = new User();
		user.setUser_id(new Long(userId));
		
		List<User> list_user = new ArrayList<User>();
		list_user = session.createCriteria(User.class).add(Example.create(user)).list();
		session.flush();
		session.close();
		return list_user.get(0);
	}*/

}
