package com.easysoft.logs.model;

import java.io.Serializable;
import java.util.Date;

import com.easysoft.logs.annotation.AuditMonitor;

/**
 * 
* Title: AuditLog
* Description: 日志类
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月5日
 */
@SuppressWarnings("serial")
public class AuditLog implements Serializable{
	private String id;
	private String clientIp;// 请求者IP
	private String reqMethod;// 请求方法
	private String reqClass;
	private String reqType;// 请求方式
	private String reqUri;// 请求的地址
	private String operator;// 操作人
	private String operatorId;//操作人ID
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private String totalTime;// 耗时
	private String result;// 请求结果
	private AuditMonitor.AuditType auditType;//类型
	private String remark;//操作的结果
	private String authCode;//授权认证代码

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getReqClass() {
		return reqClass;
	}

	public void setReqClass(String reqClass) {
		this.reqClass = reqClass;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqUri() {
		return reqUri;
	}

	public void setReqUri(String reqUri) {
		this.reqUri = reqUri;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public AuditMonitor.AuditType getAuditType() {
		return auditType;
	}

	public void setAuditType(AuditMonitor.AuditType auditType) {
		this.auditType = auditType;
	}


	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}




	public static enum AuditLogResult {
		SUCCESS, // 成功
		FAILURE, // 失败
		EXCEPRTION// 异常
	}
}
