package toby.exam11.user.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import toby.exam1.user.domain.User;
import toby.exam11.user.dao.UserDao;

public class UserMain {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new GenericXmlApplicationContext("/toby/exam11/user/dao/applicationContext.xml");
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
		
		System.out.println("[USER2] ID : " + user2.getId() + " 조회 성공 !");
	}
}
