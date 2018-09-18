package com.tanjin.framework.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.tanjin.framework.base.common.utils.EmptyUtil;
import com.tanjin.framework.web.cookie.AddCookieInfoDto;

public class CookiesUtil {

    public static void addInfoToCookie(AddCookieInfoDto addCookieInfoDto) {
        Cookie cookie = new Cookie(addCookieInfoDto.getName(), addCookieInfoDto.getValue());
        cookie.setPath("/");
        cookie.setMaxAge(-1);//永不过期
        if (EmptyUtil.isNotEmpty(addCookieInfoDto.getSessionDomain())) {
            cookie.setDomain(addCookieInfoDto.getSessionDomain());
        }
        addCookieInfoDto.getResponse().addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
