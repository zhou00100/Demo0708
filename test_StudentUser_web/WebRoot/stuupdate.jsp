<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改学生信息</title>
    
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
   欢迎：${success.userName }使用学生信息管理
	<br /> 首页-〉学生信息管理-〉学生信息修改
	<br />
	<center>学生信息修改</center>
	<!-- action="students"启动StudentControlServlet执行学生修改 -->
	<form action="StudentControlServlet" method="post">
	   <input type="hidden" name="method" value="update"/> <!-- 启动修改 -->
		<!-- 如果表单不需要边线请设置border=0 -->
		<table border="1" cellpadding="0" cellspacing="0">
			<!-- 本行居中显示align：对齐 center：居中 -->
			<tr align="center">
				<!-- colspan合并列 -->
				<td colspan="2">学生修改</td>
				<!-- 隐藏表单域传递学生学号 -->
				<input type="hidden" name="stuId" value="${myStu.stuId }" />

			</tr>
			<tr>
				<td>学生姓名</td>
				<td><input type="text" name="stuName" value="${myStu.stuName }" /></td>
			</tr>
			<tr>
				<td>学生年龄</td>
				<td><input type="text" name="stuAge" value="${myStu.stuAge}" /></td>
			</tr>
			<tr>
				<td>学生性别</td>
				<td>
				<input type="radio" name="stuSex" value="0" ${myStu.stuSex eq 0? 'checked=checked ':'' }/>男 
				<input type="radio" name="stuSex" value="1" ${myStu.stuSex eq 1?'checked=checked ':''}/>女
				</td>
			</tr>
			<tr>
				<td>学生生日</td>
				<td><input type="text" name="stuBirthday" value="${myStu.stuBirthday}" />yyyy-MM-dd</td>
			</tr>
			<tr align="center">
				<!-- colspan合并列 -->
				<td colspan="2">
				<input type="submit" value="修改" /> 
				<input type="reset" value="复位" />
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
