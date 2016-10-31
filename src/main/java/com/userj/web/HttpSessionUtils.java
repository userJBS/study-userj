package com.userj.web;

import javax.servlet.http.HttpSession;

import com.userj.domain.User;

public class HttpSessionUtils {

	public static final String USER_SESSION_KEY = "sessionedUser";

	public static boolean isLoginUser(HttpSession session) {
		Object sessionedUser = session.getAttribute(USER_SESSION_KEY);

		if (sessionedUser == null) {
			return false; // 세션 값이 없으면
		}
		return true;// 세션 값이 있으면 
	}

	public static User getUserFromSession(HttpSession session) {
		if (!isLoginUser(session)) {
			return null;  // 세션 값이 없으면 null 리턴
		}
		return (User) session.getAttribute(USER_SESSION_KEY);
	}
}
