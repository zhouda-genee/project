package com.genee.service.framework.core.base;

/**
 * 分页工具类
 * @author zhouda
 * @since 2009-3-28
 * @param <E>
 * 
 * @version 0.1 
 */
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.genee.service.module.pojo.EquipmentIndexEntity;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({EquipmentIndexEntity.class})
public class PageSupport<T> {

	private int totalCount = 0;// 总记录数
	private int pageCount;// 总页数
	private int pageSize = 5;// 每页显示记录数
	private int page = 1;// 当前页

	private HashMap<String, String> queryBuilder; // 页面查询条件

	/**
	 * 当前页之前和之后显示的页数个数 如：假设当前页是 6 共有11页 那么 显示分页条会显示 1 2 3 4 5 [6] 7 8 9 10 11
	 */
	private int num = 5;

	@XmlElement(name="items")  
	private List<T> items;// 当前页记录内容集合

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public HashMap<String, String> getQueryBuilder() {
		if (queryBuilder == null)
			queryBuilder = new HashMap<String, String>();
		return queryBuilder;
	}

	public void setQueryBuilder(HashMap<String, String> queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	/**
	 * 计算总页数
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (totalCount % pageSize == 0) {
			setPageCount(totalCount / pageSize);
		} else {
			setPageCount((totalCount / pageSize) + 1);
		}
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 获取前一页
	 * 
	 * @return
	 */
	public int getPrev() {
		return page - 1;
	}

	/**
	 * 获取后一页
	 * 
	 * @return
	 */
	public int getNext() {
		return page + 1;
	}

	/**
	 * 获取最后一页
	 * 
	 * @return
	 */
	public int getLast() {
		return pageCount;
	}

	/**
	 * 获取每页显示记录数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示记录数
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 得到当前页数
	 * 
	 * @return
	 */
	public int getPage() {
		return page;
	}

	/**
	 * 设置当前页数
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 获取当前页之前或之后显示的页数个数
	 * 
	 * @return
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 设置当前页之前或之后显示的页数个数
	 * 
	 * @param num
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 得到总页数
	 * 
	 * @return
	 */
	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 判断是否有前一页
	 * 
	 * @return
	 */
	public boolean getIsPrev() {
		if (page > 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否有后一页
	 * 
	 * @return
	 */
	public boolean getIsNext() {
		if (page < pageCount) {
			return true;
		}
		return false;
	}

	/**
	 * 当前页的前num条页 假设当前页是 6 共有11页 如：1 2 3 4 5
	 * 
	 * @return
	 */
	public Integer[] getPrevPages() {
		Integer[] list = new Integer[num];
		int _frontStart = 1;

		if (page > num) {
			_frontStart = page - num;
		}
		int j = 0;
		for (int i = _frontStart; i < page; i++, j++) {
			list[j] = i;
		}

		return list;
	}

	/**
	 * 当前页的后num条页 假设当前页是 6 共有11页 如：7 8 9 10 11
	 * 
	 * @return
	 */
	public Integer[] getNextPages() {
		Integer[] list = new Integer[num];
		int _endCount = pageCount;

		if (num < pageCount && (page + num) < pageCount) {
			_endCount = page + _endCount;
		}
		int j = 0;
		for (int i = page + 1; i <= _endCount && j < num; i++, j++) {
			list[j] = i;
		}

		return list;
	}
}
