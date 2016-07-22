package com.haikuo.utiltools;

import javax.servlet.http.HttpServletRequest;

public class ClientInfoHelper {

	/**
	 * 获取客户端ip
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if(ip==null || ip.equals("")) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
