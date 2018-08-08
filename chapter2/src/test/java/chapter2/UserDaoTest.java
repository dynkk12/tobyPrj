package chapter2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import toby.exam1.dao.UserDao;
import toby.exam1.domain.User;

public class UserDaoTest {

	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/toby/exam1/dao/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user = new User();
		user.setId("dynkk12");
		user.setName("Do Yeon Kim");
		user.setPassword("5678");
		
		dao.add(user);
		
		User user2 = dao.get(user.getId());
		
		// JUnit에서 제공하는 매처 사용해서 체크
		assertThat(user2.getName(), is(user.getName()));
		//assertThat(user2.getPassword(), is(user.getPassword()));
		assertThat(user2.getPassword(), is(user.getId()));
	}

}
