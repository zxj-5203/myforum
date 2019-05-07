package com.zxj.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// 声明该类是一个实体Bean；
@Entity
// 定义缓存策略：不严格的读写模式；使用该模式不会对缓存数据加锁；
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
// 指定对应的数据库表；
@Table(name = "t_board")
public class Board extends BaseDomain {
	private int boardId;
	private String boardName;
	private String boardDesc;
	private int topicNum;
	private Set<User> users = new HashSet<User>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略：使用底层数据主键自增机制完成；
	@Column(name = "board_id") // 将类中的属性映射到数据库表中的相应的列；
	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	@Column(name = "board_name")
	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	@Column(name = "board_desc")
	public String getBoardDesc() {
		return boardDesc;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}

	@Column(name = "topic_num")
	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

	// mapped属性：被控放设置的属性，指定主控方持有的外键属性，将控制的权利交给主控方；
	// 双向关联只能交给一方去控制，不能在双方都设置外键保存关联关系，否则双方都无法保存；
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
				mappedBy = "manBoards", fetch = FetchType.LAZY)
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
