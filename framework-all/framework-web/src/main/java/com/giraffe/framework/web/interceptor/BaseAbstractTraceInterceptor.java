package com.tanjin.framework.web.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public abstract class BaseAbstractTraceInterceptor extends HandlerInterceptorAdapter {
    protected boolean remoteInvoke;

    public boolean isRemoteInvoke() {
        return remoteInvoke;
    }

    public void setRemoteInvoke(boolean remoteInvoke) {
        this.remoteInvoke = remoteInvoke;
    }

}
