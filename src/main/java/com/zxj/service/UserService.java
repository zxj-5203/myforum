package com.zxj.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zxj.dao.LoginLogDao;
import com.zxj.dao.UserDao;
import com.zxj.domain.LoginLog;
import com.zxj.domain.User;
import com.zxj.exception.UserExistException;

// 用户管理服务器：查询、注册、锁定用户

@Transactional
@Service
public class UserService {
	private UserDao userDao;
	private LoginLogDao loginLogDao;

	/**
	 * 注册新用户，若用户已经存在，则抛异常UserExistException；否则积分出事初始为100；
	 * 
	 * @param user
	 * @throws UserExistException
	 */
	public void register(User user) throws UserExistException {
		User u = this.getUserByUserName(user.getUserName());
		if (u != null) {
			throw new UserExistException("用户名已经存在！");
		} else {
			user.setCredit(100);
			user.setUserType(1);
			userDao.save(user);
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void update(User user) {
		userDao.update(user);
	}

	/**
	 * 根据用户名查询User对象
	 * 
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	/**
	 * 根据userID获取User对象；
	 * 
	 * @param userId
	 * @return
	 */
	public User getUserByUserId(int userId) {
		return userDao.get(userId);
	}

	/**
	 * 将用户锁定，锁定的用户不能登录；
	 * 
	 * @param userName 锁定目标用户的用户名
	 */
	public void lockUser(String userName) {
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_LOCK);
		userDao.update(user);
	}

	/**
	 * 解除用户的锁定
	 * 
	 * @param userName 解除锁定目标用户的用户名
	 */
	public void unLockUser(String userName) {
		User user = userDao.getUserByUserName(userName);
		user.setLocked(User.USER_UNLOCK);
		userDao.update(user);
	}

	/**
	 * 根据用户名模糊查询 所有前缀匹配的User对象；
	 * 
	 * @param userName
	 * @return
	 */
	public List<User> queryUserByUserName(String userName) {
		return userDao.queryUserByUserName(userName);
	}

	/**
	 * 获取所有用户；
	 * 
	 * @return
	 */
	public List<User> getAllUsers() {
		return userDao.loadAll();
	}

	/**
	 * 用户登录成功，积分加5，添加登录日志；
	 * 
	 * @param user
	 */
	public void loginSuccess(User user) {
		user.setCredit(5 + user.getCredit());
		LoginLog loginLog = new LoginLog();
		loginLog.setUser(user);
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(new Date());

		userDao.update(user);
		loginLogDao.save(loginLog);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setLoginLogDao(LoginLogDao loginLogDao) {
		this.loginLogDao = loginLogDao;
	}
}
