<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>用户注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body> 
     <center>用户注册：</center>
     <form action="UsersControlServlet" method="post">
     <input type="hidden" name="method" value="register"/><!-- 提交POST请求时携带method=register -->
          用户名：<input type="text" name="userName"/> <br/>
          密码：<input type="text" name="userPassword"/> <br/>
        <input type="submit" value="注册用户"/>
     </form>
  </body>
</html>












