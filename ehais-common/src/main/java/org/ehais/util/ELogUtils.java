package org.ehais.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ELogUtils {


	private final static Logger EHAISLOG = LoggerFactory.getLogger("EHAIS_LOG");
	private final static Logger EHAISLOG_ERROR = LoggerFactory.getLogger("EHAIS_ERR_LOG");
	private final static Logger EHAISLOG_MESSAGE = LoggerFactory.getLogger("EHAIS_MSG_LOG");

	final static String LOG_STRING_REQ_MSG_BEGIN = "============================== EHAIS MSG BEGIN ==============================";
	final static String LOG_STRING_REQ_MSG_END = "==============================  EHAIS MSG END  ==============================";

	/**
	 * 记录普通日志
	 * 
	 * @param cont
	 */
	public static void writeLog(String cont) {
		EHAISLOG.info(cont);
	}

	/**
	 * 记录ERORR日志
	 * 
	 * @param cont
	 */
	public static void writeErrorLog(String cont) {
		EHAISLOG_ERROR.error(cont);
	}

	/**
	 * 记录ERROR日志
	 * 
	 * @param cont
	 * @param ex
	 */
	public static void writeErrorLog(String cont, Throwable ex) {
		EHAISLOG_ERROR.error(cont, ex);
	}

	/**
	 * 记录通信报文
	 * 
	 * @param msg
	 */
	public static void writeMessage(String msg) {
		EHAISLOG_MESSAGE.info(msg);
	}

	/**
	 * 打印请求报文
	 * 
	 * @param reqParam
	 */
	
	public static void printRequestLog(Map<String, String> reqParam) {
		writeMessage(LOG_STRING_REQ_MSG_BEGIN);
		Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			writeMessage("[" + en.getKey() + "] = [" + en.getValue() + "]");
		}
		writeMessage(LOG_STRING_REQ_MSG_END);
	}
	
	
	public static void printRequestLog(String title,HttpServletRequest request) {
		writeMessage(LOG_STRING_REQ_MSG_BEGIN);
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String name = (String) temp.nextElement();
				String value = request.getParameter(name);
				writeMessage(title + " : [" + name + "] = [" + value + "]");
			}
		}
		
		writeMessage(LOG_STRING_REQ_MSG_END);
	}
	

	/**
	 * 打印响应报文.
	 * 
	 * @param res
	 */
	public static void printResponseLog(String res) {
		writeMessage(LOG_STRING_REQ_MSG_BEGIN);
		writeMessage(res);
		writeMessage(LOG_STRING_REQ_MSG_END);
	}

	/**
	 * debug方法
	 * 
	 * @param cont
	 */
	public static void debug(String cont) {
		if (EHAISLOG.isDebugEnabled()) {
			EHAISLOG.debug(cont);
		}
	}
	
}
