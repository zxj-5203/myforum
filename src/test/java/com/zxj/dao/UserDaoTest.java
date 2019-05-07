package com.zxj.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;
import com.zxj.domain.User;

@Transactional
public class UserDaoTest extends BaseDaoTest {
	@Autowired
	private UserDao userDao;

	@Test
//	@Transactional
	public void saveUser() {
		System.out.println("OK..........");

		 User user = new User();
		 user.setUserName("系统管理员22");
		 user.setPassword("1234567");
		 user.setUserType(1);
		 user.setCredit(10);
		 System.out.println("OK2..........");
		 userDao.save(user);
		 System.out.println("OK.3.........");

//		List<User> users = userDao.find("from User u where u.userName = '墨小白'");
//		for (User u : users) {
//			System.out.println(u.getUserName() + "..." + u.getUserType() + ".." + u.getLocked());
//		}
//		System.out.println("size:..." + users.size());

	}
	//
	// @Test
	// public void getUserByUserName() {
	// throw new RuntimeException("Test not implemented");
	// }
	//
	// @Test
	// public void queryUserByUserName() {
	// throw new RuntimeException("Test not implemented");
	// }
}
