package com.zxj.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.zxj.domain.User;

@Repository
public class UserDao extends BaseDao<User> {
	// 根据用户名查询用户对象
	private static final String GET_USER_BY_USERNAME = "from User u where u.userName = ?0";
	// 根据用户名模糊查询匹配的用户对象
	private static final String QUERY_USER_BY_USERNAME = "from User u where u.userName like ?0";

	/**
	 * 根据用户名查询User对象
	 * 
	 * @param userName
	 * @return 对应userName的User对象，若不存在，返回null；
	 */
	public User getUserByUserName(String userName) {
		List<User> users = find(GET_USER_BY_USERNAME, new Object[] { userName });
		if (users.size() == 0)
			return null;
		else
			return users.get(0);
	}

	/**
	 * 根据用户名模糊查询 所有前缀匹配的User对象；
	 * 
	 * @param userName
	 * @return
	 */
	public List<User> queryUserByUserName(String userName) {
		return find(QUERY_USER_BY_USERNAME, new Object[] { userName+"%" });
	}
}
