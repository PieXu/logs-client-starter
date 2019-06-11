package com.easysoft.logs.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.easysoft.logs.aspect.AuditLogAspect;
import com.easysoft.logs.nio.LogClient;

/**
 * Title: AuditLogConfiguration Description: 配置自动启用日志 Company: easysoft.ltd
 * 
 * @author IvanHsu
 * @date 2019年6月4日
 */
@Configuration
public class AuditLogConfiguration {

	private Logger logger = LoggerFactory.getLogger(AuditLogConfiguration.class);

	/**
	 * Title: controllerLogAspect
	 * Description: 定义切面
	 * @return
	 */
	@Bean(name = "auditLogAspect")
	public AuditLogAspect auditLogAspect() {
		AuditLogAspect auditLogAspect = null;
		try {
			if(LogClient.connect()){
				auditLogAspect = new AuditLogAspect();
				logger.info("启用审计日志初始化完成......");
			}
		} catch (Exception e) {
			logger.info("启用审计日志始化异常：{}",e.getMessage());
		}
		return auditLogAspect;
	}

}
