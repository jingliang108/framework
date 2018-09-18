package com.tanjin.framework.base.database.domain.returns;

public class DataResponse extends BaseResponse {

	public DataResponse() {
		super();
	}

	public DataResponse(boolean success) {
		super(success);
	}

	public DataResponse(boolean success, Object data) {
		super(success);
		this.data = data;
	}

	public DataResponse(Integer code, String message) {
		super(code, message);
	}

	public DataResponse(Integer code, Object data) {
		super(true, code);
		this.data = data;
	}

	public DataResponse(Integer code, String message, Object data) {
		super(code, message);
		this.data = data;
	}

	protected Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
