package com.easysoft.logs.aspect;

import java.lang.reflect.Method;
import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.easysoft.logs.annotation.AuditMonitor;
import com.easysoft.logs.model.AuditLog;
import com.easysoft.logs.model.AuditLog.AuditLogResult;
import com.easysoft.logs.model.AuditUser;
import com.easysoft.logs.nio.LogClient;
import com.easysoft.logs.utils.IPUtils;

/**
 * 
* Title: ControllerLogAspect
* Description: 切面
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月5日
 */
@Aspect
public class AuditLogAspect {
	
	private Logger logger = LoggerFactory.getLogger(AuditLogAspect.class);
	/**
	 * 定义切面
	* Title: controllerLog
	* Description:
	 */
	@Pointcut("@annotation(com.easysoft.logs.annotation.AuditMonitor)")
	public void logAdvise() {
	}

	/**
	 * 
	* Title: doAround
	* Description: 切面方法，around 执行中
	* @param joinPoint
	* @return
	* @throws Throwable
	 */
	@Around(value = "logAdvise()")
    public Object doAround(ProceedingJoinPoint joinPoint)
	{
	    Object result = null;
		AuditLog auditLog = new AuditLog();
		try {
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = attributes.getRequest();
			//1、 获取用户名
			AuditUser user = (AuditUser) request.getSession().getAttribute(AuditUser.AUDIT_USER_KEY);
			if (null != user) {
				auditLog.setOperatorId(user.getUserId());
				auditLog.setOperator(user.getUserName());
			} else {
				logger.info("用户未登录访问");
				auditLog.setOperator("佚名");
			}
			String reqType = request.getMethod();
			auditLog.setReqType(reqType);
			String reqClass = joinPoint.getSignature().getDeclaringTypeName();
			auditLog.setReqClass(reqClass);
			String reqMethod = joinPoint.getSignature().getName();
			auditLog.setReqMethod(reqMethod);
			String reqUri = request.getRequestURI();
			auditLog.setReqUri(reqUri);
			auditLog.setClientIp(IPUtils.getClientIp(request));
			long startTime = System.currentTimeMillis();
			result = joinPoint.proceed(joinPoint.getArgs());
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			auditLog.setStartTime(new Date(startTime));
			auditLog.setEndTime(new Date(endTime));
			auditLog.setTotalTime(String.valueOf(totalTime));
			//2、获取类型
			MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
			Method method = methodSignature.getMethod();
			AuditMonitor operation = method.getAnnotation(AuditMonitor.class);
			if (operation != null) {
				AuditMonitor.AuditType auditType = operation.value();
				auditLog.setAuditType(auditType);
			}
			//3、设置操作结果
			auditLog.setResult(AuditLogResult.SUCCESS.toString());
			auditLog.setRemark(auditLog.getOperator()+"访问"+auditLog.getReqClass()+"."+auditLog.getReqMethod()+" 正常，操作成功");
		} catch (Throwable e) {
			auditLog.setResult(AuditLogResult.EXCEPRTION.toString());
			auditLog.setRemark("访问失败，异常描述信息："+e.getMessage());
			logger.error("操作日志拦截异常{}",e.getMessage());
		}
		//写日志到服务器端
		LogClient.sendLogMsg(auditLog);
        return result;
    }
}
