<%@page import="users.entity.UsersList"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>用户登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
   <%
     List<String> allUsers = UsersList.getUsers();//取出所有登录用户名
     pageContext.setAttribute("all", allUsers);
    %>
  <body>
       
    <c:set var="isLogin" value="${empty sessionScope.success }" scope="page"></c:set>
    <c:if test="${isLogin }"><!--会话范围内没有登录用户-->
    <center>用户登录</center>
        <form action="UsersControlServlet" method="post">
        <c:if test="${not empty errorMsg}">
             <div style="color:red;">${errorMsg}</div><!--  输出错误消息-->
             <c:remove var="errorMsg"/><!-- 删除之前保存的错误消息 -->
         </c:if>
            <input type="hidden" name="method" value="login"/><!-- 以隐藏表单域启动登录 -->
                                用户姓名：<input type="text" name="userName"/><br/>
                                用户密码：<input type="text" name="userPassword"/><br/>
            <input type="submit" value="登录">
        </form>
    </c:if>
    
    <c:if test="${!isLogin }">
       <!-- 会话范围内存在登录用户 -->
                    欢迎：${success.userName }使用本系统！<br/>
         当前在线人数：<%=UsersList.getUsersCount() %>人<br/>
          在线用户名单：
		<select multiple="multiple">
			<c:forEach items="${all}" var="myName">
				<option>${myName}</option>
			</c:forEach>
		</select>            
         
         <center>用户信息管理系统</center>
      <a href="register.jsp">用户注册</a>&nbsp;&nbsp;&nbsp;
      <a href="UsersControlServlet?method=all">用户信息管理</a>&nbsp;&nbsp;&nbsp;
      <a href="UsersControlServlet?method=invalidate">用户注销</a><br/>
      
      
        <center>学生信息管理系统</center><br/>
          <a href="StudentControlServlet?method=allstu">学生信息</a><br/> 
         <a href="StudentControlServlet?method=page">学生信息管理</a><br/><!-- 实现分页 -->
         <a href="stureg.jsp">学生注册（新增学生）</a><br/>
    </c:if>
  </body>
</html>







