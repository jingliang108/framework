package com.giraffe.framework.base.common.utils;

import java.util.Random;

public class RandomUtil {

	public static Random random = new Random();

	public static String getRandomNumber(int paramInt) {
		String str = String.valueOf(Math.abs(random.nextLong()));
		while (str.length() < paramInt) {
			str = str + String.valueOf(Math.abs(random.nextLong()));
		}
		return str.substring(0, paramInt);
	}
}
