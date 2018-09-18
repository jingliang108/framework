package com.giraffe.framework.base.database.domain.page;


import java.util.List;

import com.giraffe.framework.base.database.domain.common.SerializableModel;

/**
 * 分页查询结果集封装对象
 * 
 * @author liuyijun
 * 
 * @param <T>
 */
public class PageResult<T> extends SerializableModel {

	/**
	 * 总记录数
	 */
	private long total;

	/**
	 * 每页的数据
	 */
	private List<T> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
