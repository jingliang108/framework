package com.giraffe.framework.base.common.exceptions;

public class UnauthorizedException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965728880412317949L;

	/**
	 * 实际请求的路径
	 */
	private String requestPath;
	
	/**
	 * REST注册的路径
	 */
	private String resourcePath;

	public UnauthorizedException(String requestPath, String resourcePath) {
		this.requestPath = requestPath;
		this.resourcePath = resourcePath;
	}

	@Override
	public String getMessage() {
		return "Unauthorized '" + resourcePath + "' by '"+requestPath+"'";
	}
	
	
}
