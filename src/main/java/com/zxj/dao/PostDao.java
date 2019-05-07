package com.zxj.dao;

import org.springframework.stereotype.Repository;
import com.zxj.domain.Post;

@Repository
public class PostDao extends BaseDao<Post> {
	// 获取指定主题的所有回复帖子，按照创建时间降序排列
	private static final String GET_PAGED_POSTS = "from Post where topic.topicId = ?0 order by createTime desc ";
	// 删除指定主题下所有帖子
	private static final String DELETE_TOPIC_POSTS = "delete from Post where topic.topicId = ?0";
	private static final String GET_TOPIC_POSTS = "from Post where topic.topicId = ?0";
	
	/**
	 * 获取指定主题的所有回复帖子，按照创建时间降序排列
	 * 
	 * @param topicId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
		return pagedQuery(GET_PAGED_POSTS, pageNo, pageSize, topicId);
	}

	public void deleteTopicPosts(int topicId) {
//		createQuery(DELETE_TOPIC_POSTS, topicId);
		
//		Post p = (Post) getSession().createQuery(GET_TOPIC_POSTS).setParameter(0, topicId).uniqueResult();
//		System.out.println("sssssss" + p.getPostText());
//		getSession().remove(p);
		
		getSession().createQuery(DELETE_TOPIC_POSTS).setParameter(0, topicId).executeUpdate();
	}
}
