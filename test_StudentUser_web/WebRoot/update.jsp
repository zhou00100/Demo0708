<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>用户信息修改</title>
    
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
      <center>用户信息修改 </center>
      <form action="UsersControlServlet" method="post">
            <input type="hidden" name="method" value="update"/>
                用户编号：
            <input type="text"  disabled="disabled" value="${updateUser.userId}"/><br/>
            <input type="hidden" name="myId" value="${updateUser.userId}"/>
              用户姓名：<input type="text" name="userName" value="${updateUser.userName}" /><br/>
              用户密码：<input type="text" name="userPwd" value="${updateUser.userPassword}" /><br/>
            <input type="submit" value="修改">
      </form>
  </body>
</html>









