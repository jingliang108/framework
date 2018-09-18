package com.giraffe.framework.base.common.utils;


import java.util.List;

import com.giraffe.framework.base.database.domain.page.PageSearch;


/**
 * 对一个list集合进行分页操作
 * 
 * @author LiuYiJun
 * @date 2015年7月24日
 */
public class PageFromListUtil {

	public static <T> List<T> getPageList(List<T> list, PageSearch search) {
		List<T> subList = null;
		int total = list.size();
		int pageCount = 0;
		int m = total % search.getRows();
		total = list.size();
		if (m > 0){
			pageCount = total / search.getRows() + 1;
		} else{
			pageCount = total / search.getRows();
		}

		if (search.getPage() == pageCount) {
			subList = list.subList((search.getPage() - 1) * search.getRows(), total);
		} else {
			subList = list.subList((search.getPage() - 1) * search.getRows(), search.getRows() * (search.getPage()));
		}

		return subList;
	}

	public static <T> List<T> getPageList(List<T> list, int page, int rows) {
		List<T> subList = null;
		int total = list.size();
		int pageCount = 0;
		int m = total % rows;
		total = list.size();
		if (m > 0) {
			pageCount = total / rows + 1;
		} else {
			pageCount = total / rows;
		}

		if (page == pageCount) {
			subList = list.subList((page - 1) * rows, total);
		} else {
			subList = list.subList((page - 1) * rows, rows * page);
		}

		return subList;
	}

}
