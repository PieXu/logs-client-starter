package com.easysoft.logs.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * Title: AuditUser 
 * Description: 需要审计的用户信息， 其实就是登陆的账号的信息，如果有，统一方法和规划 Company:
 * easysoft.ltd
 * @author IvanHsu
 * @date 2019年6月5日
 */
@SuppressWarnings("serial")
@Component
public class AuditUser implements Serializable{

	/**
	 * 日志存放session的用户
	 */
	public static final String AUDIT_USER_KEY = "_audit_log_user_key";

	private String userId;
	private String userName;

	public AuditUser() {
	}

	public AuditUser(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
