package com.tanjin.framework.base.database.domain.page.builders;



import java.util.List;

import com.tanjin.framework.base.common.utils.EmptyUtil;
import com.tanjin.framework.base.database.domain.page.PageResult;

/**
 * 分页结果对象的构造器
 * 
 * @author LiuYiJun
 * @date 2015年7月20日
 * @param <T>
 */
public class PageResultBuilder<T> {

	private PageResult<T> result = null;

	/**
	 * 构建分页结果对象的总记录数
	 * 
	 * @author LiuYiJun
	 * @date 2015年7月20日
	 * @param total
	 * @return
	 */
	public PageResultBuilder<T> buildPageData(int total) {
		if (EmptyUtil.isEmpty(result)) {
			result = new PageResult<T>();
		}
		result.setTotal(total);
		return this;
	}

	/**
	 * 构建分页结果对象的分页数据信息
	 * 
	 * @author LiuYiJun
	 * @date 2015年7月20日
	 * @param rows
	 * @return
	 */
	public PageResultBuilder<T> buildPageData(List<T> rows) {
		if (EmptyUtil.isEmpty(result)) {
			result = new PageResult<T>();
		}
		result.setRows(rows);
		return this;
	}

	/**
	 * 构建分页结果对象的总记录数和分页数据信息
	 * 
	 * @author LiuYiJun
	 * @date 2015年7月20日
	 * @param total
	 * @param rows
	 * @return
	 */
	public PageResultBuilder<T> buildPageData(int total, List<T> rows) {
		if (EmptyUtil.isEmpty(result)) {
			result = new PageResult<T>();
		}
		result.setRows(rows);
		result.setTotal(total);
		return this;
	}

	/**
	 * 返回分页结果对象信息
	 * @author LiuYiJun
	 * @date 2015年7月20日
	 * @return
	 */
	public PageResult<T> toPageResult() {
		return result;
	}

}
