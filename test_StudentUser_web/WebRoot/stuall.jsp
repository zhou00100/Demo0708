<%@page import="java.text.SimpleDateFormat"%>
<%@page import="student.util.StudentDTO"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'stuall.jsp' starting page</title>
    
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
  
  <%
  StudentDTO dto = (StudentDTO)session.getAttribute("dto");
       if(dto != null){
          String beforeDate = dto.getSearch_birthdayDate1();
          String afterDate = dto.getSearch_birthdayDate2();
          if(beforeDate != null && afterDate != null && !"".equals(beforeDate) && !"".equals(afterDate)){
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          
		  Date date1 = sdf.parse(beforeDate);
		  Date date2 = sdf.parse(afterDate);
		  pageContext.setAttribute("d1", date1);
		  pageContext.setAttribute("d2", date2);
		  }else{
  			  out.println("date为空值");
  		  }
          }else{
  			  out.println("dto为空值");
  		  }
  
   %>
      <center>学生信息管理</center>
      组合查询
		<div>
			<form action="StudentControlServlet" method="post">
			    <input type="hidden" name="method" value="page"/><!-- 启动控制器高级查询 -->
			    <!--以隐藏表单域区分全部和高级-->
			    <input type="hidden" name="flag" value="1"/>
				学生编号：
				<input type="text" name="search_stuid" value="${dto.search_stuid eq 0?'': dto.search_stuid}"/>
				学生姓名：
				<input type="text" name="search_stuname" value="${dto.search_stuname }"/><br/>
				学生年龄：
				<input type="text" name="search_stuage1" value="${dto.search_stuage1 eq 0 ?'': dto.search_stuage1}"/>
				至
				<input type="text" name="search_stuage2" value="${dto.search_stuage2 eq 0 ? '':dto.search_stuage2}"/><br/>
				出生日期：
				<input type="text" name="search_birthdayDate1" value='<fmt:formatDate value="${d1}" pattern="yyyy-MM-dd hh:mm:ss" />'/>
				至
				<input type="text" name="search_birthdayDate2" value='<fmt:formatDate value="${d2}" pattern="yyyy-MM-dd hh:mm:ss" />'/>
				<input type="submit" value="查询" />
			</form>
		</div>
		
		<table border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td>序号</td>
			<td>学生学号</td>
			<td>学生姓名</td>
			<td>学生年龄</td>
			<td>学生性别</td>
			<td>学生生日</td>
			<td>操&nbsp;作</td>
		</tr>
		
		<!-- pb.data使用EL表达式获取分页查询的所有学生 -->
		<c:forEach items="${pb.data }" var="stu" varStatus="status">
		   <tr>
			 <td>${status.count }</td>
             <td>${stu.stuId }</td>
             <td>${stu.stuName }</td>
             <td>${stu.stuAge }</td>
             <td>${stu.stuSex}</td>
			 <td><fmt:formatDate value="${stu.stuBirthday}" pattern="yyyy年MM月dd日"></fmt:formatDate></td>
			 <td><a href="StudentControlServlet?method=querySingle&id=${stu.stuId }">修改</a>&nbsp;&nbsp;
			     <a href="StudentControlServlet?method=studelete&id=${stu.stuId }">删除</a></td>
		   </tr>
		</c:forEach>
	</table>
	
	<a href="StudentControlServlet?method=page&pageNo=${pb.firstPage}&pageSize=${pb.pageSize}">首页</a>
	
	<a href="StudentControlServlet?method=page&pageNo=${pb.previousPage}&pageSize=${pb.pageSize}">上一页</a>
	
	<a href="StudentControlServlet?method=page&pageNo=${pb.nextPage}&pageSize=${pb.pageSize}">下一页</a>
	
	<a href="StudentControlServlet?method=page&pageNo=${pb.lastPage}&pageSize=${pb.pageSize}">末页</a>
	
	<a>共${pb.totalRecords }条记录</a>
		
	<a>共${pb.lastPage }页</a>
		
	<a>第${pb.pageNo }页</a>
		
	<a>每页显示${pb.pageSize }条记录</a><br/>
	
	<a href="login.jsp">返回登录</a>
  </body>
</html>






