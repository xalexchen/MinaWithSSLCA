//package com.sundoctor.mina.example3.ssl.client;
//
//import java.net.InetSocketAddress;
//
//import org.apache.mina.core.future.ConnectFuture;
//import org.apache.mina.core.session.IoSession;
//import org.apache.mina.transport.socket.nio.NioSocketConnector;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class TSLClientSpring {
//
//	public static void main(String[] args) throws Exception {
//
//		ApplicationContext context = getApplicationContext();
//		NioSocketConnector ioConnectorWithSSL = (NioSocketConnector) context.getBean("ioConnectorWithSSL");
//		
//		// 创建连接
//		ConnectFuture future = ioConnectorWithSSL.connect(new InetSocketAddress("localhost", 50003));
//		// 等待连接创建完成
//		future.awaitUninterruptibly();
//		// 获取连接会话
//		IoSession session = future.getSession();
//		// 发送信息
//		session.write("我是安全的吗?");
//		// 等待连接断开
//		session.getCloseFuture().awaitUninterruptibly();
//		ioConnectorWithSSL.dispose();
//	}
//
//	public static ConfigurableApplicationContext getApplicationContext() {
//		return new ClassPathXmlApplicationContext("com/sundoctor/mina/example3/ssl/client/clientContext.xml");
//	}
//}
