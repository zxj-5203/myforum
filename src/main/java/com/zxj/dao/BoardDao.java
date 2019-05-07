package com.zxj.dao;

import org.springframework.stereotype.Repository;
import com.zxj.domain.Board;

@Repository
public class BoardDao extends BaseDao<Board> {
	private static final String GET_BOARD_NUM = "select count(b.boardId) from Board b";

	/**
	 * 返回所有论坛板块的数目；
	 * 
	 * @return
	 */
	public long getBoardNum() {
		// return (long)getHibernateTemplate().iterate(GET_BOARD_NUM, null).next();
		return (long) getSession().createQuery(GET_BOARD_NUM).uniqueResult();
	}
}
