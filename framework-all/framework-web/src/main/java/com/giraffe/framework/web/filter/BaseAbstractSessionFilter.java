package com.tanjin.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本拦截器只是对HttpSession对象的转储，将httpsession对象和usersession对象存入线程池中
 *
 * @author LiuYiJun
 * @date 2015年7月14日
 */
public abstract class BaseAbstractSessionFilter implements Filter {

    private static String[] staticUrl = {".css", ".js", ".ico", ".gif", ".html", "htm", ".jpg", ".png", ".bmp",
            ".jpeg", ".swf", ".apk", ".tif", ".flv", ".xml", ".txt", ".svg", ".json", ".map", ".mp4", ".avi"};

    Logger logger = LoggerFactory.getLogger(BaseAbstractSessionFilter.class);

    private static ThreadLocal<HttpSession> threadLocalHttpSession = new ThreadLocal<HttpSession>();

    public static HttpSession getHttpSession() {
        return threadLocalHttpSession.get();
    }


    public void destroy() {

    }


    public void doFilter(ServletRequest request, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        HttpServletResponse response = (HttpServletResponse) arg1;
        HttpServletRequest req = (HttpServletRequest) request;
        this.doSessionInfo(session, threadLocalHttpSession, req);
        arg2.doFilter(request, arg1);
    }

    public void doSessionInfo(HttpSession session, ThreadLocal<HttpSession> threadLocalHttpSession,
                              HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!isStaticFile(uri)) {
            threadLocalHttpSession.set(session);
            this.saveMembersSessionOrEmployeeSessionToThreadLocal(session);
        }
    }


    public abstract void saveMembersSessionOrEmployeeSessionToThreadLocal(HttpSession session);

    public void init(FilterConfig arg0) throws ServletException {

    }

    public boolean isStaticFile(String url) {
        for (String string : staticUrl) {
            if (url.toLowerCase().endsWith(string)) {
                return true;
            }
        }
        return false;
    }

}
