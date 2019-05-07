package com.zxj.service;

import static org.junit.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import com.zxj.domain.Board;
import com.zxj.domain.MainPost;
import com.zxj.domain.Post;
import com.zxj.domain.Topic;
import com.zxj.domain.User;

public class ForumServiceTest extends BaseServiceTest {
	@Autowired
	private ForumService forumService;
	@Autowired
	private UserService userService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Test
	public void addBoard() {
		Board board = new Board();
		board.setBoardName("Spring");
		board.setBoardDesc("学习使用Spring框架；");
		board.setTopicNum(0);
		forumService.addBoard(board);
		Board boardDb = forumService.getBoardByBoardId(board.getBoardId());
		assertEquals(boardDb.getBoardName(), "Spring");
	}

	// 新增主题帖子
	@Test
	public void addTopic() throws Exception {
		//		topic_id board_id topic_title user_id create_time last_post topic_views	topic_replies	digest
		//		1			1		宝宝成长秘籍	1		2011/5/7	2011/9/18	0		1				0
		Topic topic = new Topic();
		topic.setBoardId(39);
		topic.setTopicTitle("spring搭建");
		topic.setCreateTime(sdf.parse("2019-01-02"));
		topic.setLastPost(sdf.parse("2019-04-05"));
		topic.setTopicViews(0);
		topic.setTopicReplies(1);
		topic.setDigest(0);
		//		user_id	user_name	credit	password	locked	user_type	last_visit	last_ip
		//		1	     tom		100		123456		0		1			2011/6/6	127.0.0.1
		User user = new User();
		user.setUserName("普通用户");
		user.setCredit(100);
		user.setPassword("123456");
		user.setLocked(0);
		user.setUserType(1);
		user.setLastVisit(new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-01"));
		user.setLastIp("127.0.0.1");
		//		post_id	board_id topic_id user_id	post_type post_title	post_text	               create_time
		//		1		1			1		1		1		 育儿		 "培育健康聪明宝贝，分享您的经验心得." 2011/5/7
		MainPost post = new MainPost();
		post.setTopic(topic);
		post.setPostTitle("spring框架");
		post.setPostText("spring是一站式框架： 在JavaEE三层结构中，每一层都提供不同的解决技术");
		post.setCreateTime(sdf.parse("2019-02-01"));
		post.setBoardId(39);
		post.setTopic(topic);
		post.setUser(user);

		topic.setUser(user);
		topic.setMainPost(post);
		forumService.addTopic(topic);

		Board boardDb = forumService.getBoardByBoardId(39);
		User userDb = userService.getUserByUserName("普通用户");
		assertEquals(boardDb.getTopicNum(), 1);
		assertEquals(userDb.getCredit(), 110);
		assertEquals(topic.getTopicId() > 0, true);
	}

	@Test
	public void removeTopic() {
		forumService.removeTopic(21);
		Topic topicDb = forumService.getTopicByTopicId(6);
		assertNull(topicDb);
	}

	@Test
	public void addPost() throws Exception {
		//		user_id	user_name	credit	password	locked	user_type	last_visit	last_ip
		//		1	     tom		100		123456		0		1			2011/6/6	127.0.0.1
		User user = userService.getUserByUserId(3);
		Topic topic = forumService.getTopicByTopicId(3);
		assertEquals(topic.getTopicId(), 3);
		//  post_id	board_id topic_id user_id	post_type post_title	post_text	               create_time
		// 1		1			1		1		1		 育儿		 "培育健康聪明宝贝，分享您的经验心得." 2011/5/7
		Post post = new Post();
		post.setPostTitle("spring框架1");
		post.setPostText("spring是一站式框架： 在JavaEE三层结构中，每一层都提供不同的解决技术");
		post.setCreateTime(sdf.parse("2019-02-01"));
		post.setBoardId(39);
		post.setUser(user);
		post.setTopic(topic);

		forumService.addPost(post);

		User userDb = userService.getUserByUserName("张三");
		Topic topicDb = forumService.getTopicByTopicId(3);
		assertEquals(post.getPostId() > 1, true);
		assertEquals(userDb.getCredit(), 20);
		assertEquals(topicDb.getTopicReplies(), 5);
	}

	@Test
	public void removePost() {
		forumService.removePost(9);
		
		Post post = forumService.getPostByPostId(10);
		User user = userService.getUserByUserName("张三");
		Topic topic = forumService.getTopicByTopicId(3);

		assertNull(post);
		assertEquals(user.getCredit(), 80);
		assertEquals(topic.getTopicReplies(), 3);
	}

	// 测试置精华主题帖的服务方法
	@Test
	public void makeDigestTopic() {
		forumService.makeDigestTopic(3);
		User user = userService.getUserByUserName("普通用户");
		Topic topic = forumService.getTopicByTopicId(3);
		assertEquals(user.getCredit(), 310);
		assertEquals(topic.getDigest(), Topic.DIGEST_TOPIC);
	}

	@Test
	public void addBoardManager() {
		forumService.addBoardManager(12, "张三");
		User user = userService.getUserByUserName("张三");
		assertEquals(user.getManBoards().size()>0, true);
		
	}

	@Test
	public void getAllBoard() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getBoardByBoardId() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getPagedPosts() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getPagedTopics() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getPostByPostId() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void getTopicByTopicId() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void queryTopicByTitle() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void removeBoard() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setBoardDao() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setPostDao() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setTopicDao() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void setUserDao() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void updatePost() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void updateTopic() {
		throw new RuntimeException("Test not implemented");
	}
}
