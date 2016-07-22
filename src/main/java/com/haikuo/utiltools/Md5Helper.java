package com.haikuo.utiltools;

import java.security.MessageDigest;

public class Md5Helper {

	public static String md5Encrypt(String source) {
		try {
			MessageDigest digester = MessageDigest.getInstance("MD5");
			byte[] sbs = source.getBytes("UTF8");
			digester.update(sbs);
			byte[] rbs = digester.digest();
			return toHexString(rbs);
		} catch (Exception e) {
			return null;
		}
	}

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String toHexString(byte[] buf) {
		return toHexString(buf, null, Integer.MAX_VALUE);
	}

	private static String toHexString(byte[] buf, String sep, int lineLen) {
		if (buf == null)
			return null;
		if (lineLen <= 0)
			lineLen = Integer.MAX_VALUE;
		// StringBuffer res = new StringBuffer(buf.length * 2);
		StringBuilder res = new StringBuilder(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			int b = buf[i];
			res.append(HEX_DIGITS[(b >> 4) & 0xf]);
			res.append(HEX_DIGITS[b & 0xf]);
			if (i > 0 && (i % lineLen) == 0)
				res.append('\n');
			else if (sep != null && i < lineLen - 1)
				res.append(sep);
		}
		return res.toString();
	}

}
