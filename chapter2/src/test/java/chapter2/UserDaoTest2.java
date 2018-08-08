package chapter2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import toby.exam1.dao.UserDao;
import toby.exam1.domain.User;

public class UserDaoTest2 {

	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/toby/exam1/dao/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		User user = new User();
		user.setId("dynkk12");
		user.setName("Do Yeon Kim");
		user.setPassword("5678");
		
		dao.add(user);
		assertThat(dao.getCount(), is(1));
		
		User user2 = dao.get(user.getId());
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
		//assertThat(user2.getPassword(), is(user.getId()));
	}
	
	@Test
	public void count() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/toby/exam1/dao/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User("qwer1", "김김김", "pw1");
		User user2 = new User("qwer2", "도도도", "pw2");
		User user3 = new User("qwer3", "연연연", "pw3");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		//assertThat(dao.getCount(), is(5));
	}
}
