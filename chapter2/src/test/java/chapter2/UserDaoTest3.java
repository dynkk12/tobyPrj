package chapter2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import toby.exam1.dao.UserDao;
import toby.exam1.domain.User;

public class UserDaoTest3 {

	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/toby/exam1/dao/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user1 = new User("qwer1", "김김김", "pw1");
		User user2 = new User("qwer2", "도도도", "pw2");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		// USER1의 ID값으로 DB에서 조회 후 실제 데이터가 동일한 지 테스트
		User userGet1 = dao.get(user1.getId());
		assertThat(userGet1.getName(), is(user1.getName()));
		assertThat(userGet1.getPassword(), is(user1.getPassword()));
		
		// USER2의 ID값으로 DB에서 조회 후 실제 데이터가 동일한 지 테스트
		User userGet2 = dao.get(user2.getId());
		assertThat(userGet2.getName(), is(user2.getName()));
		assertThat(userGet2.getPassword(), is(user2.getPassword()));
		
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
	
	// 아래 EmptyResultDataAccessException이 발생하는지 테스트
	// 테스트를 먼저 만들고 UserDao를 수정
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/toby/exam1/dao/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		// DB에 없는 값을 조회
		dao.get("xxxxxxxxx");
	}

}
