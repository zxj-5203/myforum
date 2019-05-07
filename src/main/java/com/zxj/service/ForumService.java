package com.zxj.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.zxj.dao.BoardDao;
import com.zxj.dao.Page;
import com.zxj.dao.PostDao;
import com.zxj.dao.TopicDao;
import com.zxj.dao.UserDao;
import com.zxj.domain.Board;
import com.zxj.domain.MainPost;
import com.zxj.domain.Post;
import com.zxj.domain.Topic;
import com.zxj.domain.User;

@Service
@Transactional
public class ForumService {
	private BoardDao boardDao;
	private UserDao userDao;
	private PostDao postDao;
	private TopicDao topicDao;

	/**
	 * 新增一个话题：发表一个主题帖子，用户积分加10，论坛版块的主题帖子数加1；
	 * 
	 * @param topic
	 */
	public void addTopic(Topic topic) {
		Board board = boardDao.get(topic.getBoardId()); // 根据topic获取其所板块的id，然后再根据boardId获取board；
		board.setTopicNum(board.getTopicNum() + 1); // topic所在版块的topic数+1；
		topicDao.save(topic);
		// topicDao.getHibernateTemplate().flush();

		topic.getMainPost().setTopic(topic); // 通过topic实例 创建主题帖；
		MainPost post = topic.getMainPost();
		post.setCreateTime(new Date());
		post.setUser(topic.getUser());
		post.setPostTitle(topic.getTopicTitle());
		post.setBoardId(topic.getBoardId());
		postDao.save(topic.getMainPost()); // 持久化主题帖

		User user = topic.getUser();// 更新用户积分
		user.setCredit(user.getCredit() + 10);
		userDao.update(user);
	}

	/**
	 * 根据帖子ID删除一个主题帖；<br/>
	 * 用户积分－50，论坛版块主题帖数-1，删除主题帖所有关联的帖子；
	 * 
	 * @param topicId 要删除的主题帖的Id；
	 */
	public void removeTopic(int topicId) {
		Topic topic = topicDao.get(topicId);

		// 论坛版块主题帖数-1；
		Board board = boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() - 1);

		// 发表主题帖的用户积分-50；
		User user = topic.getUser();
		user.setCredit(user.getCredit() - 50);

		// 删除该主题帖及其关联的帖子；
		System.out.println("删除回复贴开始::" + topicId);
		postDao.deleteTopicPosts(topicId); 
		System.out.println("删除回复贴成功");
		topicDao.remove(topic);
	}

	/**
	 * 添加一个回复帖子，用户积分+5，主题帖子回复数+1，更新最后回复时间；
	 * 
	 * @param post
	 */
	public void addPost(Post post) {
		postDao.save(post); // 保存回复贴；

		// 用户积分+5
		User user = post.getUser();
		user.setCredit(user.getCredit() + 5);
		userDao.update(user);

		// 主题帖回复数+1，更新最后回复时间；
		// 该方法从数据表中加载Topic实例，所以该实例处于Hibernate受管状态，在方法中调整回复数和最后更新时间两个属性，
		// hibernate会将topic状态更改自动同步到数据表中，无需显示调用topicDao.update()方法；
		Topic topic = topicDao.get(post.getTopic().getTopicId());
		topic.setTopicReplies(topic.getTopicReplies() + 1);
		topic.setLastPost(new Date());
		// topicDao.update(topic);
	}

	/**
	 * 根据postId删除回复贴，发表回复贴的用户积分-20，主题帖回复数-1；
	 * 
	 * @param postId
	 */
	public void removePost(int postId) {
		// 根据postId删除回复贴;
		Post post = postDao.get(postId);
		postDao.remove(post);

		// 发表回复贴的用户积分-20
		User user = post.getUser();
		user.setCredit(user.getCredit() - 20);
		// userDao.update(user);

		// 主题帖回复数-1
		Topic topic = topicDao.get(post.getTopic().getTopicId());
		topic.setTopicReplies(topic.getTopicReplies() - 1);
		// topicDao.update(topic); // post、topic处于Hibernate受管状态，无须显示更新
	}

	/**
	 * 创建一个新的论坛版块；
	 * 
	 * @param board
	 */
	public void addBoard(Board board) {
		boardDao.save(board);
	}

	/**
	 * 根据boardId删除一个论坛版块；
	 * 
	 * @param boardId
	 */
	public void removeBoard(int boardId) {
		boardDao.remove(boardDao.get(boardId));
	}

	/**
	 * 将帖子置为精华主题帖，发表该帖子的用户积分+100；
	 * 
	 * @param topicId 操作的目标主题帖的id；
	 */
	public void makeDigestTopic(int topicId) {
		Topic topic = topicDao.get(topicId); // topic处于hibernate受管状态，无需显示更新；
		topic.setDigest(Topic.DIGEST_TOPIC);
		User user = topic.getUser();
		user.setCredit(user.getCredit() + 100);
	}

	/**
	 * 获取所有的论坛版块；
	 * 
	 * @return
	 */
	public List<Board> getAllBoards() {
		return boardDao.loadAll();
	}

	/**
	 * 根据boardId获取指定论坛版块 某一页主题帖，按照最后回复时间降序；
	 * 
	 * @param boardId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
		return (Page) topicDao.getPagedTopics(boardId, pageNo, pageSize);
	}

	/**
	 * 根据topicId获取指定主题 每一页帖子，按照最后回复时间降序；
	 * 
	 * @param topicId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
		return (Page) postDao.getPagedPosts(topicId, pageNo, pageSize);
	}

	/**
	 * 根据title查询所有标题包含title的主题帖；
	 * 
	 * @param title
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page queryTopicByTitle(String title, int pageNo, int pageSize) {
		return (Page) topicDao.queryTopicByTitle(title, pageNo, pageSize);
	}

	/**
	 * 根据boardId获取board对象；
	 * 
	 * @param boardId
	 * @return
	 */
	public Board getBoardByBoardId(int boardId) {
		return boardDao.get(boardId);
	}

	/**
	 * 根据topicId获取topic对象；
	 * 
	 * @param topicId
	 * @return
	 */
	public Topic getTopicByTopicId(int topicId) {
		return topicDao.get(topicId);
	}

	/**
	 * 根据postId获取post对象；
	 * 
	 * @param postId
	 * @return
	 */
	public Post getPostByPostId(int postId) {
		return postDao.get(postId);
	}

	/**
	 * 将指定用户设为指定论坛的管理员；
	 * 
	 * @param boardId 论坛版块id；
	 * @param userName 设为论坛管理员的用户名；
	 */
	public void addBoardManager(int boardId, String userName) {
		User user = userDao.getUserByUserName(userName);
		if (user == null) {
			throw new RuntimeException("用户名为" + userName + "的用户不存在！");
		} else {
			Board board = boardDao.get(boardId);
			user.getManBoards().add(board);
			userDao.update(user);
		}
	}

	/**
	 * 更新主题帖；
	 * 
	 * @param topic
	 */
	public void updateTopic(Topic topic) {
		topicDao.update(topic);
	}

	/**
	 * 更改回复帖子的内容；
	 * 
	 * @param post
	 */
	public void updatePost(Post post) {
		postDao.update(post);
	}

	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	@Autowired
	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}
}
