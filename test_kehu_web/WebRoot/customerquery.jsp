<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'customerquery.jsp' starting page</title>
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
    <table width="80%" border="1" align="center"><a href="index.jsp">首页</a></table>
	<p align="center"><center><h1>客户查询</h1></center></p>
	<center>
	<form name="form1" method="post" action="CustomerControlServlet">
	<input type="hidden" name="method" value="page"/><!-- 启动控制器高级查询 -->
			   <!--以隐藏表单域区分全部和高级-->
			    <input type="hidden" name="flag" value="1"/>
	  <table width="60%" border="1" align="center">
		<tr>
		  <td width="30%" >姓名：
			<input type="text" name="search_cusname" value="${dto.search_cusname }"/>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="查询" size="15">
			</td>
		</tr>
	  </table>

	  <table width="60%" border="1" align="center">
		<tr bgcolor="#d7e5f6">
		  <td width="4%" nowrap><div align="center"><strong>序号</strong></td>
		  <td width="14%"><div align="center"><strong>姓名</strong></td>
		  <td width="20%"><div align="center"><strong>电话</strong></td>
		  <td width="19%"><div align="center"><strong>手机</strong></td>
		  <td width="19%"><div align="center"><strong>传真</strong></td>
		  <td width="19%" colspan="2"><div align="center"><strong>操作</strong></td>
		</tr>
		
		<c:forEach items="${pb.data }" var="cus" varStatus="status">
		   <tr>
			 <td>${status.count }</td>
             <td>${cus.custName }</td>
             <td>${cus.phone }</td>
             <td>${cus.mobile}</td>
             <td>${cus.fax}</td>
			 
			 <td><a href="CustomerControlServlet?method=querySingle&id=${cus.custId }">修改</a>&nbsp;&nbsp;
			     <a href="CustomerControlServlet?method=cusdelete&id=${cus.custId }">删除</a></td>
		   </tr>
		</c:forEach>
		
		<tr>
			<table width="60%">
			<tr>
			<td width="12%">
			<a href="CustomerControlServlet?method=page&pageNo=${pb.firstPage}&pageSize=${pb.pageSize}">首页</a>
			</td>
			<td width="12%">
			<a href="CustomerControlServlet?method=page&pageNo=${pb.previousPage}&pageSize=${pb.pageSize}">上一页</a>
			</td>
			<td width="12%">
			<a href="CustomerControlServlet?method=page&pageNo=${pb.nextPage}&pageSize=${pb.pageSize}">下一页</a>
			</td>
			<td width="12%">
			<a href="CustomerControlServlet?method=page&pageNo=${pb.lastPage}&pageSize=${pb.pageSize}">末页</a>
			<td width="10%"><a>共${pb.totalRecords }条记录</a></td>
			<td width="10%"><a>共${pb.lastPage }页</a></td>
			<td width="10%"><a>第${pb.pageNo }页</a></td>
			<td width="25%"><a>每页显示${pb.pageSize }条记录</a></td>
			</tr>
			</table>
		</tr>
	  </table>
	</form>
	</center>
  </body>
</html>
