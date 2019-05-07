package com.zxj.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import com.zxj.domain.Board;

public class BoardDaoTest extends BaseDaoTest {
	@Autowired
	private BoardDao boardDao;

	// 测试添加版块
	@Test
	public void addBoard() {
		Board board = new Board();
		board.setBoardName("spring框架125480");
		board.setTopicNum(0);
		board.setBoardDesc("Spring是开源的轻量级的框架；主要核心有2部分：IOC、AOP；");
		boardDao.save(board);
		System.out.println("OK...");
	}
	
	@Test
	public void getBoardNum() {
		
	}
}
