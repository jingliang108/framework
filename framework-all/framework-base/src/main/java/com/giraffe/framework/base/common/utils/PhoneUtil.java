package com.giraffe.framework.base.common.utils;

public class PhoneUtil {
    private final static String yiDong = "134、135、136、137、138、139、150、151、152、157、158、159、1705、178、182、183、184、187、188、147";

    private final static String lianTong = "130、131、132、155、156、1709、176、185、186、145";

    private final static String dianXin = "133、153、1700、177、180、181、189";

    /**
     * @Title:获取手机所属移动商
     * @Description: 1为移动, 2为联通 ,3为电信,4未知号段
     * @author LILEI
     * @date 2015年9月7日 上午11:16:06
     * @version V1.0
     */
    public static Integer getPhoneOperators(String phone) {
        String str = phone.substring(0, 3);
        if (str.equals("170")) {
            str = phone.substring(0, 4);
        }
        if (yiDong.contains(str)) {
            return 1;
        } else if (lianTong.contains(str)) {
            return 2;
        } else if (dianXin.contains(str)) {
            return 3;
        } else {
            return 4;
        }
    }
}
