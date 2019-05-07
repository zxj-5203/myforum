package com.zxj.domain;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// 回复的帖子

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_post")
// 指定PO映射继承关系：每个类层次结构一张表
// Post类和其子类MainPost都映射到t_post表中，表中用post_type字段值区分；
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 指定辨别符列：哪列用于区分子父类；
// 辨别符列名字默认是DTYPE，默认值是实体名，默认类型是String；
@DiscriminatorColumn(name = "post_type", discriminatorType = DiscriminatorType.STRING)
// 指定用来辨别该类的值：1表示MainPost，2表示Post；
@DiscriminatorValue("2")
public class Post extends BaseDomain {
	private int postId;
	private int boardId;
	private Topic topic;
	private User user;
	private String postTitle;
	private String postText;
	private Date createTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	@Column(name = "board_id")
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	// 定义Post与Topic的多对一关系：一个Topic有多个Post
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	// 定义了多对一的关联关系；
	// 若没有此注解，系统默认在主表中创建连接列：主题的关联属性名_被关联的主键列名；
	@JoinColumn(name = "topic_id")
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@ManyToOne // By default no operations are cascaded.
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "post_title")
	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	@Column(name = "post_text")
	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
