package com.tanjin.framework.web.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getRequestUrl(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        return requestUri.substring(contextPath.length());
    }

    public static String getRefererUrl(HttpServletRequest request) {
        return request.getHeader("referer").trim();
    }

}
