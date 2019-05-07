package com.zxj.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_user")
public class User extends BaseDomain {
	public static final int USER_LOCK = 1; // 锁定用户对应的状态值
	public static final int USER_UNLOCK = 0; // 解锁用户对应的状态值
	public static final int FORUM_ADMIN = 2; // 管理员类型
	public static final int NORMAL_USER = 1; // 普通用户类型

	private int userId;
	private String userName;
	private int userType = NORMAL_USER;
	private Date lastVisit;
	private String lastIp;
	private String password;
	private int locked = USER_UNLOCK;
	private int credit;
	private Set<Board> manBoards = new HashSet<Board>(); // 管理的版块

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "user_name")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_type")
	public int getUserType() {
		return userType;
	}

	/**1：普通用户； 2：管理员；
	 * @param userType
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Column(name = "last_visit")
	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	@Column(name = "last_ip")
	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	// 板块与管理员之间多对多关系
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	// name：指定维护多对多关联关系的中间表；
	// joinColumns：本表的外键列
	@JoinTable(name = "t_board_manager", joinColumns = { @JoinColumn(name = "user_id") }, 
			   inverseJoinColumns = { @JoinColumn(name = "board_id") })
	public Set<Board> getManBoards() {
		return manBoards;
	}

	public void setManBoards(Set<Board> manBoards) {
		this.manBoards = manBoards;
	}

	public static int getUserLock() {
		return USER_LOCK;
	}

	public static int getUserUnlock() {
		return USER_UNLOCK;
	}

	public static int getForumAdmin() {
		return FORUM_ADMIN;
	}

	public static int getNormalUser() {
		return NORMAL_USER;
	}
}
