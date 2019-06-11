package com.easysoft.logs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* Title: ControllerLogMonitor
* Description: Controller的日志配置
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月4日
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditMonitor {
	public abstract AuditType value() default AuditType.Controller;
	/*
	 * 日志监控的层面
	 */
	public static enum AuditType{
		Controller,Service,Dap
	}
}
