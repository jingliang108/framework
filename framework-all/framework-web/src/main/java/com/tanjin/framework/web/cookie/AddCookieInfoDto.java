package com.tanjin.framework.web.cookie;

import javax.servlet.http.HttpServletResponse;

import com.tanjin.framework.base.database.domain.common.SerializableModel;

/**
 * Created by liuyijun on 2016/7/5.
 */
public class AddCookieInfoDto extends SerializableModel {

    private String name;

    private String value;


    private HttpServletResponse response;

    private String sessionDomain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getSessionDomain() {
        return sessionDomain;
    }

    public void setSessionDomain(String sessionDomain) {
        this.sessionDomain = sessionDomain;
    }
}
