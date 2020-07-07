<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>学生注册</title>
    
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
    <center>学生注册</center>
     <!-- action="ad"启动AddStudentServlet执行学生注册 -->
     <form action="StudentControlServlet" method="post">
        <input type="hidden" name="method" value="studentreg"/>
     <!-- 如果表单不需要边线请设置border=0 -->
     <table border="1" cellpadding="0" cellspacing="0">
     <!-- 本行居中显示align：对齐 center：居中 -->
        <tr align="center">
        <!-- colspan合并列 -->
            <td colspan="2">
                  学生注册
            </td>
        </tr>
         <tr>
            <td>
                 学生姓名
            </td>
            <td>
                <input type="text" name="stuName"/>
            </td>
        </tr>
        <tr>
            <td>
                 学生年龄
            </td>
            <td>
                <input type="text" name="stuAge"/>
            </td>
        </tr>
        <tr>
            <td>
                 学生性别
            </td>
            <td>
                <input type="radio" name="stuSex" value="0" checked="checked"/>男
                <input type="radio" name="stuSex" value="1"/>女
            </td>
        </tr>
        <tr>
            <td>
                 学生生日
            </td>
            <td>
                <input type="text" name="stuBirthday"/>yyyy-MM-dd
            </td>
        </tr>
        <tr align="center">
        <!-- colspan合并列 -->
            <td colspan="2">
                  <input type="submit" value="注册"/>
                  <input type="reset" value="复位"/>
            </td>
        </tr>
     </table>
  </form>
  </body>
</html>







