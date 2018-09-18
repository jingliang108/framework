package com.giraffe.framework.base.database.domain.returns;

import java.io.Serializable;

import com.giraffe.framework.base.common.utils.MyBeanUtils;
import com.giraffe.framework.base.database.domain.common.SerializableModel;

/**
 * 方法调用结果封装基础类，主要用于业务层的方法返回值的封装
 */
public class BaseResult<T extends Serializable> extends SerializableModel {

    public BaseResult() {
        super();
    }

    public BaseResult(boolean success) {
        super();
        this.success = success;
    }

    /**
     * 该构造函数默认success是为true的
     *
     * @param obj 要传递的对象
     */
    public BaseResult(T obj) {
        super();
        this.data = obj;
        this.success = true;
    }

    public BaseResult(boolean success, T data) {
        super();
        this.data = data;
        this.success = success;
    }

    /**
     * 是否成功，true 表示成功
     */
    protected boolean success;

    /**
     * 消息信息，一般情况下只有出现错误才会有错误呀信息，如果成功该值可以为空
     */
    protected String message;


    protected T data;

    public T getData() {
        return data;
    }

    public BaseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public BaseResult<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResult<T> setMessage(String msg) {
        this.message = msg;
        return this;
    }


    public BaseResponse getBaseResponse() {
        return MyBeanUtils.copyBean(this, BaseResponse.class);
    }

    public DataResponse getDataResponse() {
        return MyBeanUtils.copyBean(this, DataResponse.class);
    }


}
