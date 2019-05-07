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
import javax.persistence.Transient;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "t_topic")
public class Topic extends BaseDomain {
	// 精华主题帖子
	public static final int DIGEST_TOPIC = 1;
	// 普通主题帖子
	public static final int NOT_DIGEST_TOPIC = 0;

	private int topicId;
	private int boardId;
	private String topicTitle;
	private User user;
	private Date createTime = new Date();
	private Date lastPost = new Date();
	private int topicViews;
	private int topicReplies;
	private MainPost mainPost = new MainPost();
	private int digest = NOT_DIGEST_TOPIC;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topic_id")
	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	@Column(name = "board_id")
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	@Column(name = "topic_title")
	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	// 一个用户有多个帖子
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "last_post")
	public Date getLastPost() {
		return lastPost;
	}

	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}

	@Column(name = "topic_views")
	public int getTopicViews() {
		return topicViews;
	}

	public void setTopicViews(int topicViews) {
		this.topicViews = topicViews;
	}

	@Column(name = "topic_replies")
	public int getTopicReplies() {
		return topicReplies;
	}

	public void setTopicReplies(int topicReplies) {
		this.topicReplies = topicReplies;
	}

	// 变量不能被序列化，数据库表中没有此属性的对应的字段；
	@Transient
	public MainPost getMainPost() {
		return mainPost;
	}

	public void setMainPost(MainPost mainPost) {
		this.mainPost = mainPost;
	}

	public int getDigest() {
		return digest;
	}

	public void setDigest(int digest) {
		this.digest = digest;
	}

	public static int getDigestTopic() {
		return DIGEST_TOPIC;
	}

	public static int getNotDigestTopic() {
		return NOT_DIGEST_TOPIC;
	}
}
