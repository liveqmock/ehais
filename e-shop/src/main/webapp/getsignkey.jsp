<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="org.ehais.weixin.WXConstants" %>
<%@page import="org.ehais.util.EHttpClientUtil" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信新证书验证</title>
</head>
<body>
<%
String res = null;
try {
	String mch_id = "1340156101";
	String secret = "EhaisZkef1234567890oktylerokokok";
	String content = "<org.ehais.weixin.model.WeiXinGetSignKey>"+
			  "<mch_id>1340156101</mch_id>"+
			  "<nonce_str>oj1iamxi54x5rw5cbtynmeqd9e0rjh0m</nonce_str>"+
			  "<sign>876B1583AD955E070C19FE58EF5E40B7</sign>"+
			"</org.ehais.weixin.model.WeiXinGetSignKey>";
	res = EHttpClientUtil.httpPostEntity(WXConstants.getsignkey, content);;
	System.out.println(res);
}catch(Exception e) {
	e.printStackTrace();
}

%>
<%=res %>
</body>
</html>