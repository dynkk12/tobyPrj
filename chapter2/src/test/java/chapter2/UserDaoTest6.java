package chapter2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import toby.exam1.dao.UserDao;
import toby.exam1.domain.User;

// 스프링이 제공하는 테스트 컨텍스트 프레임워크를 사용하겠다
@RunWith(SpringJUnit4ClassRunner.class)
// 애플리케이션컨텍스트의 설정정보 위치를 명시해줌으로써 해당 설정정보를 갖고 애플리케이션 컨텍스트 생성
@ContextConfiguration(locations="/toby/exam1/dao/applicationContext.xml")
public class UserDaoTest6 {
	
	// autowired를 사용하면 해당 변수와 타입이 맞는 빈을 알아서 주입해줌
	@Autowired
	private ApplicationContext context;
	
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	// 각 테스트를 수행하기 전 먼저 최초 실행되도록 해줌
	@Before
	public void setUp() {
		dao = context.getBean("userDao", UserDao.class);
		user1 = new User("qwer1", "김김김", "pw1");
		user2 = new User("qwer2", "도도도", "pw2");
		user3 = new User("qwer3", "연연연", "pw3");
		// 스프링이 제공하는 테스트 컨텍스트 프레임워크를 사용하면 애플리케이션 컨텍스트 한번만 생성해서 공유 
		System.out.println(">>>>>>>>> context :" + context);
	}
	
	@Test
	public void addAndGet() throws SQLException {
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
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		// DB에 없는 값을 조회
		dao.get("xxxxxxxxx");
	}

}
