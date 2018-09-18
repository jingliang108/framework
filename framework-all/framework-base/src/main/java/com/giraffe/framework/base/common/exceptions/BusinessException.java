package com.tanjin.framework.base.common.exceptions;

import java.text.MessageFormat;

import com.tanjin.framework.base.common.injectors.MessageResourceInjector;


/**
 * 用户的操作或提交的数据存在逻辑错误，事务操作失败
 * 
 * @author liuyijun
 * 
 */
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716943269241933289L;

	private String errCode;

	private String errMsg;

	private Object[] args;

	public BusinessException(String errCode, Object... args) {
		this.errCode = errCode;
		this.args = args;
		String message = MessageResourceInjector.getMessage(errCode);
		if (message == null) {
			throw new RuntimeException(errCode + " not defined");
		}
		errMsg = MessageFormat.format(message, args);
	}

	@Override
	public String getMessage() {
		return this.getErrMsg();
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {

		return this.errMsg;
	}

	public Object[] getArgs() {
		return args;
	}

}
