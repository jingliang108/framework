package com.tanjin.framework.base.database.domain.returns;

import java.util.Date;

import com.tanjin.framework.base.database.domain.common.SerializableModel;

/**
 * controller层对返回数据的封装基础类，一般只用于操作的接口
 */
public class BaseResponse extends SerializableModel {

	public BaseResponse() {
		super();
		this.success = true;
		this.code = 1000;
		this.date=new Date();
	}

	public BaseResponse(boolean success) {
		this.success = success;
		this.date=new Date();
	}

	public BaseResponse(boolean success, Integer code) {
		this.success = success;
		this.code = code;
		this.date=new Date();
	}

	public BaseResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.date=new Date();
	}

	public BaseResponse(Integer code, String message) {
		this.code = code;
		this.message = message;
		this.date=new Date();
	}

	public BaseResponse(boolean success, Integer code, String message) {
		this.success = success;
		this.code = code;
		this.message = message;
		this.date=new Date();
	}

	/**
	 * 是否成功，true 表示成功
	 */
	protected boolean success;

	/**
	 * 消息信息，一般情况下只有出现错误才会有错误呀信息，如果成功该值可以为空
	 */
	protected String message;

	protected Integer code;

	protected String redirectUrl;
	
	protected Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isSuccess() {
		return success;
	}

	public BaseResponse setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public BaseResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public BaseResponse setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getRedirectUrl() {
		return redirectUrl;

	}

	public BaseResponse setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}
}
