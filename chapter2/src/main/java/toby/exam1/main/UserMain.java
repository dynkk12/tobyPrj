package toby.exam1.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import toby.exam1.dao.UserDao;
import toby.exam1.domain.User;

public class UserMain {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new GenericXmlApplicationContext("/toby/exam1/dao/applicationContext.xml");
		UserDao dao = context.getBean("userDao", UserDao.class);
		User user = new User();
		user.setId("chikeem90");
		user.setName("김도연");
		user.setPassword("1234");
		
		dao.add(user);
		
		System.out.println("[USER] ID : "+ user.getId() + " 등록 성공 !");
		
		User user2 = dao.get(user.getId());
		System.out.println("[USER2] NAME : " + user2.getName());
		System.out.println("[USER2] PASSWORD : " + user2.getPassword());
		
		// insert한 값과 select해온 값이 동일한 지 테스트
		if (!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (이름)");
		} else if (!user.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 실패 (비밀번호)");
		} else {
			System.out.println("조회 테스트 성공");
		}
	}
}
