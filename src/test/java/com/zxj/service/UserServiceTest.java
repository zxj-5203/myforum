package com.zxj.service;

import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import com.google.common.base.Verify;
import com.zxj.dao.UserDao;
import com.zxj.domain.User;
import com.zxj.exception.UserExistException;

public class UserServiceTest extends BaseServiceTest {
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserService userService;

	@Test
	public void register() throws UserExistException {
		User user = new User();
		user.setUserName("叶薇");
		user.setPassword("123456");

		userService.register(user);
		assertEquals(user.getUserId(), 10);
	}

	@Test
	public void getUserByUserName() {
		User user = userService.getUserByUserName("叶薇");
		assertNotNull(user);
		assertEquals(user.getCredit(), 100);
	}

	@Test
	public void queryUserByUserName() {
		List<User> list = userService.queryUserByUserName("叶");
		System.out.println(list.size());
		for(User u : list) {
			System.out.println(u.getUserName());
		}
	}

	@Test
	public void lockUser() {
		userService.lockUser("叶薇");
		assertEquals(userService.getUserByUserId(10).getLocked(), User.USER_LOCK);
	}

	@Test
	public void unLockUser() {
		userService.unLockUser("叶薇");
		assertEquals(userService.getUserByUserId(10).getLocked(), User.USER_UNLOCK);
	}

	@Test
	public void getAllUser() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getUserByUserId() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void loginSuccess() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setLoginLogDao() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setUserDao() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void update() {
		throw new RuntimeException("Test not implemented");
	}
}
