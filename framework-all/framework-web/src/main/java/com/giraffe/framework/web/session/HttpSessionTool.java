package com.tanjin.framework.web.session;

import javax.servlet.http.HttpSession;

import com.tanjin.framework.base.common.utils.EmptyUtil;

public class HttpSessionTool {
    public static String SESSION_KEY = "loginedMember";

    public static String EMPLOYEE_SESSION_KEY = "loginedEmployee";

    public static String LOGINERROR_KEY = "login_error_number";

    /**
     * 将成功登陆的用户保存到session中
     *
     * @param httpSession
     * @param baseUserSession
     * @author LiuYiJun
     * @date 2015年7月14日
     */
    public static void doMemberLogin(HttpSession httpSession, BaseSession baseUserSession) {
        httpSession.setAttribute(SESSION_KEY, baseUserSession);
        httpSession.removeAttribute(LOGINERROR_KEY);
    }

    public static void doEmployeeLogin(HttpSession httpSession, BaseSession baseUserSession) {
        httpSession.setAttribute(EMPLOYEE_SESSION_KEY, baseUserSession);
        httpSession.removeAttribute(LOGINERROR_KEY);
    }


    /**
     * 错误的登陆次数
     *
     * @param httpSession
     * @return
     * @author LiuYiJun
     * @date 2015年7月14日
     */
    public static Integer getLoginErrorNumber(HttpSession httpSession) {
        return (Integer) httpSession.getAttribute(LOGINERROR_KEY);
    }

    /**
     * 增加登陆错误次数
     *
     * @param httpSession
     * @author LiuYiJun
     * @date 2015年7月14日
     */
    public static void addLoginErrorNumber(HttpSession httpSession) {
        Integer number = getLoginErrorNumber(httpSession);
        number = EmptyUtil.isEmpty(number) ? 1 : number + 1;
        httpSession.setAttribute(LOGINERROR_KEY, number);
    }

    /**
     * 清除错误登录次数
     *
     * @param httpSession
     * @author LiuYiJun
     * @date 2015年7月14日
     */
    public static void clearLoginErrorNumber(HttpSession httpSession) {
        httpSession.removeAttribute(LOGINERROR_KEY);
    }

    /**
     * 退出
     *
     * @param httpSession
     * @author LiuYiJun
     * @date 2015年7月14日
     */
    public static void doOut(HttpSession httpSession) {
        httpSession.removeAttribute(SESSION_KEY);

    }


    public static void doEmployeeOut(HttpSession httpSession) {
        httpSession.removeAttribute(EMPLOYEE_SESSION_KEY);
    }


}
