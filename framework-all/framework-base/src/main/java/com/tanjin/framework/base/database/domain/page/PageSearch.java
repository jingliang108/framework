package com.tanjin.framework.base.database.domain.page;

import com.tanjin.framework.base.common.utils.EmptyUtil;
import com.tanjin.framework.base.database.domain.common.SerializableModel;

/**
 * 分页查询条件封装类
 *
 * @author liuyijun
 */
public class PageSearch extends SerializableModel {
	/**
	 * 分页请求时当前页变量
	 */
	protected Integer page = 1;

	/**
	 * 分页请求时每页显示数量变量
	 */
	protected Integer rows = 10;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		if (EmptyUtil.isEmpty(page) || page < 1) {
			page = 1;
		}
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;

	}

	public int getCurrentResult() {
		return (this.page - 1) * this.rows;
	}

}
