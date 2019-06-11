package com.easysoft.logs.nio;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.easysoft.logs.model.AuditLog;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 
 * Title: LogClient Description: 发送消息客户端
 * Company: easysoft.ltd
 * @author IvanHsu
 * @date 2019年6月5日
 */
@Component
public class LogClient {

	private static Logger logger = LoggerFactory.getLogger(LogClient.class);

	private static int port;
	
	private static String host;
	
	private static String authcode;
	
	@Value("${easysoft.log.client.serverPort}")
	public void setPort(int port) {
		LogClient.port = port;
	}
	@Value("${easysoft.log.client.serverHost}")
	public void setHost(String host) {
		LogClient.host = host;
	}
	
	@Value("${easysoft.log.client.authcode}")
	public void setAuthcode(String authcode) {
		LogClient.authcode = authcode;
	}

	private static Channel channel;

	/**
	 * 
	 * Title: connect Description: 连接服务器
	 * 
	 * @param nPort
	 * @param strHost
	 * @throws Exception
	 */
	public static boolean connect() {
		
		if(StringUtils.isEmpty(authcode)){
			logger.error("连接日志服务器[{}:{}]位置设置应用授权CODE,请申请并设置 [{}] 属性",host,port,"easysoft.log.client.authcode");
			return false;
		}
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast("decoder", new StringDecoder());
							ch.pipeline().addLast("encoder", new StringEncoder());
							ch.pipeline().addLast(new ClientHanler());
						}
					});
			channel = b.connect(host, port).sync().channel();
			logger.info("连接日志服务器[{}:{}] 成功",host,port); //channel.closeFuture().sync();
			return true;
		} catch (Exception e) {
			logger.error("连接日志服务器[{}:{}] 异常：{}",host,port,e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * 
	 * Title: sendLogMsg 
	 * Description: 发送消息
	 * @param channel
	 * @param msg
	 */
	public static void sendLogMsg(AuditLog log) {
		if (null == channel || !channel.isActive()) {
			connect();
		}
		log.setAuthCode(authcode);
		channel.writeAndFlush(JSON.toJSONString(log));
	}

}
