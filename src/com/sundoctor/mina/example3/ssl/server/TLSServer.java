package com.sundoctor.mina.example3.ssl.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sundoctor.mina.example3.ssl.BogusSslContextFactory;


public class TLSServer {

	private static final Logger logger = LoggerFactory.getLogger(TLSServer.class);
	private static final int PORT = 4123;

	public static void main(String[] args) throws Exception {
		//创建服务器端连接器
		SocketAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setReuseAddress(true);		
		
		//获取默认过滤器
		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		
		//设置加密过滤器 
		SslFilter sslFilter = new SslFilter(BogusSslContextFactory.getInstance(true));
		//设置客户连接时需要验证客户端证书
		sslFilter.setNeedClientAuth(true);		
		chain.addLast("sslFilter", sslFilter);
		
		//设置编码过滤器和按行读取数据模式
		chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		
		//设置事件处理器
		acceptor.setHandler(new TLSServerHandler());
		
		//服务绑定到此端口号		
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("服务器在 等待连接..."+PORT);
	}
}