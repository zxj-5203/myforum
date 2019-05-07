package com.zxj.dao;

import org.springframework.stereotype.Repository;
import com.zxj.domain.Topic;

@Repository
public class TopicDao extends BaseDao<Topic> {
	// 获取论坛版块的精华主题帖子；0：非精华帖子，1：精华帖子；
	private static final String GET_BOARD_DIGEST_TOPICS = "from Topic t where t.boardId = ?0 and digest>0 order by t.lastPost desc,digest desc";

	// 列出论坛版块的所有主题帖子；
	private static final String GET_PAGED_TOPICS = "from Topic t where t.boardId = ?0 order by t.lastPost desc";

	// 以关键字为条件对主题帖子的标题进行模糊查询；
	private static final String QUERY_TOPIC_BY_TITLE = "from Topic t where t.topicTitle like ?0 ,order by lastPost desc";

	/**
	 * 获取论坛版块精华主题帖，按照最后回复时间及精华级别 降序排列；
	 * 
	 * @param boardId 论坛版块ID
	 * @param pageNo 页号
	 * @param pageSize 页数据量
	 * @return 返回该论坛下所有精华主题帖
	 */
	public Page getBoardDigestTopic(int boardId, int pageNo, int pageSize) {
		return pagedQuery(GET_BOARD_DIGEST_TOPICS, pageNo, pageSize, boardId);
	}

	/**
	 * 获取论坛版块所有主题帖子
	 * 
	 * @param boardId
	 * @param pageNo
	 * @param pageSize
	 * @return 包含分页信息的page对象
	 */
	public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
		return pagedQuery(GET_PAGED_TOPICS, pageNo, pageSize, boardId);
	}

	/**
	 * 根据主题帖标题模糊查询匹配的主题帖
	 * 
	 * @param title
	 * @param pageNo
	 * @param topicSize
	 * @return
	 */
	public Page queryTopicByTitle(String title, int pageNo, int pageSize) {
		return pagedQuery(QUERY_TOPIC_BY_TITLE, pageNo, pageSize, title);
	}
}
