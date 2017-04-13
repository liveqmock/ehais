package cn.org.test;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.ehais.util.EHttpClientUtil;

import com.ehais.minaunit.handler.HandlerOne;

import net.sf.json.JSONObject;

public class ClintTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建客户端连接器. 
		NioSocketConnector connector = new NioSocketConnector(); 
		connector.getFilterChain().addLast( "logger", new LoggingFilter() ); 
		connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "GBK" )))); //设置编码过滤器 
		connector.setHandler(new HandlerOne());//设置事件处理器 
		ConnectFuture cf = connector.connect( 
		new InetSocketAddress("127.0.0.1", 8888));//建立连接 
		cf.awaitUninterruptibly();//等待连接创建完成 
		IoSession session = cf.getSession();
		session.getConfig().setUseReadOperation(true);
		JSONObject json = new JSONObject();
		json.put("wxid", 1);
		
		session.write(json.toString());
		
		ReadFuture readFuture = session.read();
		readFuture.awaitUninterruptibly();  
		String url = "";
		String result = "";
		
		try{

			if (readFuture.awaitUninterruptibly(30, TimeUnit.SECONDS)) {

				String message = (String)readFuture.getMessage();
				System.out.println("同步返回的消息是:"+message);
				JSONObject jsonR = JSONObject.fromObject(message);
				
	            // TODO 处理消息				
				url = "http://localhost/apix/wx"+jsonR.get("wxid")+"?signature="+jsonR.get("signature")+"&timestamp="+jsonR.get("timestamp")+"&nonce="+jsonR.get("nonce")+"&echostr="+jsonR.get("echostr");
				result = EHttpClientUtil.httpPostEntity(url, jsonR.get("notityXml").toString());
				json.put("result", result);
				WriteFuture future = session.write(json.toString());//发送消息
				future.awaitUninterruptibly();
				
	        } else {

	             // 读超时

	        }
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		 
//		session.close(true);
//		session.getCloseFuture().awaitUninterruptibly();//等待连接断开 
//		connector.dispose(); 

	}

}
