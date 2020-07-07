<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>用户信息管理</title>
    
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
  欢迎：${success.userName }使用首页->用户信息管理！<a href="login.jsp">返回</a>
        <center><h1>用户信息管理</h1></center>
          <table cellpadding="0" cellspacing="0" border="1">
                  <tr>
                     <td>序号</td>
                     <td>用户编号</td>
                     <td>用户姓名</td>
                     <td>用户密码</td>
                     <td>用户管理</td>
                  </tr>
                  <c:forEach items="${allUsers }" var="user" varStatus="status">
                  <tr>
                       <td>${status.count }</td>
                       <td>${user.userId }</td>
                       <td>${user.userName }</td>
                       <td>${user.userPassword }</td>
                       <td><a href="UsersControlServlet?method=userSingle&userId=${user.userId }">修改</a>&nbsp;&nbsp;&nbsp;
                           <a href="UsersControlServlet?method=delete&userId=${user.userId }">删除</a>
                       </td>
                       </tr>
                  </c:forEach>
         </table>
       
  </body>
</html>











