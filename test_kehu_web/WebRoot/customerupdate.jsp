<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'customerupdate.jsp' starting page</title>
    <style type="text/css">
			body {
				font-family: Tahoma, Verdana;
				font-size: 12px
			}
			
			table {
				font-family: Tahoma, Verdana;
				color: #111111;
				font-size: 12px
				border: 0;
				margin: 0;
				padding:0;
				border: 1px solid #ccc;
				border-collapse: collapse;
				font-size: 12px;
			}
			td{
				LINE-HEIGHT: 180%
			}
			
		</style>
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
   <table width="80%" border="1" align="center"><a href="index.html">首页</a></table>
	<p align="center"><center><h1>客户信息修改</h1></center></p>
<center>
	<form action="CustomerControlServlet" method="post">
	<input type="hidden" name="method" value="update"/> 
	<table width="40%" border="1" align="center">
	<tr>
				<!-- colspan合并列 -->
				<td colspan="2">学生修改</td>
				<!-- 隐藏表单域传递学生学号 -->
				<td>
				<input type="hidden" name="custId" value="${myStu.custId }" />
				</td>
			</tr>
			<tr>
                <td width="30%" align="right">
                    姓名:</td>
                <td>
                
                    <input type="text" name="custName" value="${myStu.custName }"/>
            </tr>
			<tr>
                <td align="right">
                    电话:</td>
                <td>
                    <input type="text" name="phone" value="${myStu.phone }"/>
            </tr>
			<tr>
                <td align="right">
                    手机:</td>
                <td>
                   <input type="text" name="mobile" value="${myStu.mobile }"/></td>
            </tr>
			<tr>
                <td align="right">
                    传真:</td>
                <td>
                    <input type="text" name="fax" value="${myStu.fax }"/>
            </tr>
			<tr>
                <td align="right">
                    email:</td>
                <td>
                   <input type="text" name="email" value="${myStu.email }"/></td>
            </tr>
			<tr>
                <td align="right">
                    地址:</td>
                <td>
					<input type="text" name="address" value="${myStu.address }"/></td>
            </tr>
			
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="修改" />&nbsp;&nbsp;
                    <input id="Button2" type="reset" value="重置" /></td>
            </tr>
        </table>
	  </form>
	  </center>
  </body>
</html>
