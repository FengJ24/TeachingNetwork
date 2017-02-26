package com.university.education.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
	public static String getMD5(String val) {
		String rst = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(val.getBytes());
			byte[] b = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < b.length; i++) {
				int temp = 0xFF & b[i];
				String s = Integer.toHexString(temp);
				if (temp <= 0x0F) {
					s = "0" + s;
				}
				sb.append(s);
			}
			rst = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return rst;
	}
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
