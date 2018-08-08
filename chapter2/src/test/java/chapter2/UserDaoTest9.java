package chapter2;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import toby.exam1.dao.UserDao;
import toby.exam1.domain.User;
 
public class UserDaoTest9 {
	private UserDao dao;
	private User user1;
	private User user2;
	private User user3;
	
	// 각 테스트를 수행하기 전 먼저 최초 실행되도록 해줌
	@Before
	public void setUp() {
		// 애플리케이션 컨텍스트를 사용하지 않고 직접 객체를 생성하고 의존관계 주입을 하여 관계를 만들어 줌.
		dao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:oracle:thin:@127.0.0.1:1521:orcl","scott","tiger", true); 
		dao.setDataSource(dataSource);
		user1 = new User("qwer1", "김김김", "pw1");
		user2 = new User("qwer2", "도도도", "pw2");
		user3 = new User("qwer3", "연연연", "pw3");
	}
	
	@Test
	public void addAndGet() throws SQLException {
		System.out.println("addAndGet");
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
		System.out.println("count");
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
		System.out.println("getUserFailure");
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		// DB에 없는 값을 조회
		dao.get("xxxxxxxxx");
	}

}
