package com.tanjin.framework.base.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class TokenUtil {

	public static synchronized String getToken(String key) {
		return DigestUtils.md5Hex(key + RandomUtil.getRandomNumber(8) + "wmm");
	}

	public static synchronized String getToken(Integer key) {
		return DigestUtils.md5Hex(key + RandomUtil.getRandomNumber(8) + "wmm");
	}
}
