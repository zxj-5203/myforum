package com.zxj.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_login_log")
public class LoginLog extends BaseDomain {
	private int loginLogId;
	private String ip;
	private Date loginDate;
	private User user;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "login_log_id")
	public int getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(int loginLogId) {
		this.loginLogId = loginLogId;
	}

	public String getIp() {
		return ip;
	}

	// ip属性对应数据库表的字段名与属性名相同，所以不用通过@Column再指定字段名
	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "login_datetime")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	@ManyToOne
	@JoinColumn(name = "user_id") // 一方User的主键；
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
