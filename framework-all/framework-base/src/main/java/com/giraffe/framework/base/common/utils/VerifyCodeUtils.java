package com.giraffe.framework.base.common.utils;

import java.util.Random;

/**
 * 手机验证码工具类
 * 
 * @author liuyijun
 * 
 */
public class VerifyCodeUtils {

	private static final String VERIFY_CODES = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 获取验证码
	 * 
	 * @author LiuYiJun
	 * @date 2015年7月16日
	 * @param verifySize
	 * @param sources
	 * @return
	 */
	private static String generateVerifyCode(int verifySize, String sources) {
		if (sources == null || sources.length() == 0) {
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for (int i = 0; i < verifySize; i++) {
			verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
		}
		return verifyCode.toString();
	}

	/**
	 * 创建手机验证码
	 * 
	 * @param verifySize
	 *            长度
	 * @return
	 * @author liuyijun
	 */
	public static String createMobileVerifyCode(int verifySize) {
		return generateVerifyCode(verifySize, "0123456789");
	}

}
