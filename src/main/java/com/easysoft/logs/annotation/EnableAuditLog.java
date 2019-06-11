package com.easysoft.logs.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.easysoft.logs.config.AuditLogConfiguration;

/**
 * 
* Title: EnableAuditLog
* Description: 
* Company: easysoft.ltd 
* @author IvanHsu
* @date 2019年6月4日
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ AuditLogConfiguration.class })
public @interface EnableAuditLog {

}
