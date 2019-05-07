package com.zxj.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象，包含当前页数据及分页控制信息，如：每页的数据行数，当前的页码，总页数，总记录数；
 */
public class Page implements Serializable {
	private static int DEFAULT_PAGE_SIZE = 20; // 默认的每页的记录数
	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数
	private long start; // 当前页第一条数据在List中的位置，从0开始；
	private List data; // 当前页中存放的记录，类型一般为List
	private long totalCount; // 总记录数

	/**
	 * 帯参构造函数
	 * 
	 * @param start 本页数据在数据库中的起始位置
	 * @param totalCount 数据库中的总记录数
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 */
	public Page(long start, long totalCount, int pageSize, List data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalCount;
		this.data = data;
	}

	/**
	 * 空参构造函数：只构造空页；
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<>());
	}

	/**
	 * @return 返回总记录数；
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @return 返回总页数；
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * @return 返回每页数据容量；
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return 返回当前页中的记录
	 */
	public List getResult() {
		return data;
	}

	/**
	 * @return 返回当前页的页码：页码从0开始；
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * @return 返回当前页是否有下一页；
	 */
	public boolean isHasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * @return 返回当前页是否有上一页；
	 */
	public boolean isHasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获取任一页的第一条数据在数据集中的位置；
	 * 
	 * @param pageNo 从1开始的页号
	 * @param pageSize 每页的记录的条数；
	 * @return 该页第一条数据所在位置；
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 获取任一页的第一条数据在数据集中的位置，每页条数使用默认；
	 * 
	 * @param pageNo 从1开始的页号；
	 * @return
	 */
	public static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}
}
