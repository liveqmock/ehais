package com.ehais.minaunit.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import net.sf.json.JSONObject;


public class HandlerTwo extends IoHandlerAdapter {

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// TODO Auto-generated method stub
	//	super.messageReceived(session, message);
		System.out.println("HandlerTwo received message :"+message);
		JSONObject json = JSONObject.fromObject(message);
		MinaSessionMap sessionMap = MinaSessionMap.newInstance();
		
		IoSession s = sessionMap.getSession(json.getString("wxid"));
		if(s==null)sessionMap.addSession(json.getString("wxid"), session);
		
//		session.write("我接收到了，返回给你吧："+message);
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}

}
