package com.easysoft.logs.nio;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHanler extends SimpleChannelInboundHandler<String>  {

	private Logger logger = LoggerFactory.getLogger(ClientHanler.class);
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		if(StringUtils.isNoneBlank(msg)){
			logger.error("审计日志提交失败:{}",msg);
		}
	}

	/**
	 * 出现异常
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error(cause.getMessage());
	    ctx.close();
	}

}
