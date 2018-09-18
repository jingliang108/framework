package com.tanjin.framework.web.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tanjin.framework.base.common.exceptions.BusinessException;

/**
 * springmvc 异常控制，主要是对自定义异常做特殊处理
 *
 * @author liuyijun
 */
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler
	public void exp(HttpServletRequest request, Exception ex, HttpServletResponse response) throws Exception {
		if (ex instanceof BusinessException) {
			BusinessException exception = (BusinessException) ex;
			logger.error(exception.getMessage(), exception);
			response.setStatus(600);
			response.setHeader("err-code", exception.getErrCode());
			response.setCharacterEncoding("UTF-8");
			response.setHeader("err-msg", URLEncoder.encode(exception.getErrMsg(), "UTF-8"));
			response.setContentType("text/plain");

			response.getWriter().write(exception.getMessage());

		} else {
			ex.printStackTrace();
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false, true));
	}

	/**
	 * @Title: web端默认返回的jsp页面的路径
	 * @Description: 当前controller的RequestMapping的第一个参数的名称做为JSP文件夹目录+所调用的方法名称做为JSP文件的名称
	 * @return
	 * @author LILEI
	 * @date 2016年10月12日 下午2:55:24
	 * @version V1.0
	 */
	private String getPagePath() {
		String pagePath = "";
		// 获得上一个调用类的相关信息
		StackTraceElement stack = new Exception().getStackTrace()[1];
		try {
			// 通过反射实例化这个类
			Class<?> newClass = Class.forName(stack.getClassName());
			// 获得RequestMapping的第一个参数的值,做为JSP文件夹目录
			String menu = newClass.getAnnotation(RequestMapping.class).value()[0];
			// 获得上一个调用类的调用方法的名称, 做为JSP文件的名称
			String page = stack.getMethodName();
			if (menu.startsWith("/")) {
				menu = menu.substring(1);
			}
			pagePath = menu + "/" + page;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagePath;
	}
}
