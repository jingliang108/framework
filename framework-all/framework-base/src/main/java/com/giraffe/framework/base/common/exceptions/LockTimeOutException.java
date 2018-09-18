package com.tanjin.framework.base.common.exceptions;

/**
 * 用户获取共享锁超时
 * 
 * @author liuyijun
 * 
 */
public class LockTimeOutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716943269241933289L;

	private String errMsg;

	public LockTimeOutException(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String getMessage() {
		return this.errMsg;
	}

}
