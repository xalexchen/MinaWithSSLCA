package com.sundoctor.mina.example3.ssl.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TLSClientHandler extends IoHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(TLSClientHandler.class);

	public void sessionCreated(IoSession session) throws Exception {
		logger.debug("[NIO Client]>> sessionCreated");
		session.write("pc client create!!");
	}

	public void sessionOpened(IoSession session) throws Exception {
		logger.debug("[NIO Client]>> sessionOpened");
		session.write("pc client open!!");
	}

	public void sessionClosed(IoSession session) throws Exception {
		logger.debug("[NIO Client]>> sessionClosed");
	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		logger.debug("[NIO Client]>> sessionIdle");
	}

	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.debug("[NIO Client]>> exceptionCaught :");
		cause.printStackTrace();
	}

	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.debug("[NIO Client]>> messageReceived");
		System.out.println("[NIO Client Received]>> "+(String) message);
	}

	public void messageSent(IoSession session, Object message) throws Exception {
		logger.debug("[NIO Client]>> messageSent");
		logger.debug("[NIO Client messageSent]>> : {}", (String) message);
	}
}
