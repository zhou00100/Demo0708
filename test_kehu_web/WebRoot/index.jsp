<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <style type="text/css">
		body {
			font-family: Tahoma, Verdana;
			font-size: 12px
		}
	</style>
  <body>
   <div align="center"><h1>客户信息管理系统</h1></div>
	<div>
		<div align="center">
			<h5>
				<a href="customeradd.jsp">客户录入</a>
				<a href="CustomerControlServlet?method=page">客户查询</a>
			</h5>
		</div>
	</div>
  </body>
</html>
