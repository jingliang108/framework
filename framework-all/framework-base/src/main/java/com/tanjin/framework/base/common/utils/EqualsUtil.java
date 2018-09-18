package com.tanjin.framework.base.common.utils;

/**
 * 类信息描述
 *
 *
 * @date   16/01/11
 * @author liuyijun
 */
public class EqualsUtil {

    /**
     * 方法的功能描述：
     *
     *
     * @param val
     * @param val1
     * @return
     *
     * @author liuyijun
     * @date 16/01/11
    */
    public static boolean equals(Integer val, Integer val1) {
        if (EmptyUtil.isNotEmpty(val) && EmptyUtil.isNotEmpty(val1)) {
            if (val.intValue() == val1.intValue()) {
                return true;
            }
        }

        return false;
    }

    /**
     * 方法的功能描述：
     *
     *
     * @param val
     * @param val1
     * @return
     *
     * @author liuyijun
     * @date 16/01/11
    */
    public static boolean equals(Object val, Object val1) {
        if (EmptyUtil.isNotEmpty(val) && EmptyUtil.isNotEmpty(val1)) {
            if (val.toString().equals(val1.toString())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 方法的功能描述：
     *
     *
     * @param val
     * @param val1
     * @return
     *
     * @author liuyijun
     * @date 16/01/11
     */
    public static boolean notEquals(Integer val, Integer val1) {
        if (EmptyUtil.isNotEmpty(val) && EmptyUtil.isNotEmpty(val1)) {
            if (val.intValue() != val1.intValue()) {
                return true;
            }
        }

        return false;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
