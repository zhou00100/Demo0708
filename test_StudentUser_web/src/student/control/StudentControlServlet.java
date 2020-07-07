package student.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import student.dao.IStudentDAO;
import student.dao.StudentDaoImpl;
import student.entity.TblStuinfo;
import student.util.PageBean;
import student.util.StudentDTO;

public class StudentControlServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public StudentControlServlet() {
		super();
	}
	
	//判断表单传递的参数是否为空
	public boolean isBlank(String param) {
		return "".equals(param) || param == null;//true：文本框没有输入任何值
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		
		//使用表单利用隐藏表单域传递method参数区分不同的用户功能
		String myMethod = request.getParameter("method");
		System.out.println("当前表单提交的功能请求是："+myMethod);
		
		
		if ("studentreg".equals(myMethod)) {
			//true:启动学生注册功能
			this.studentRegister(request, response);
		}else if ("studelete".equals(myMethod)) {
			//true：按编号删除用户信息
			this.stuDelete(request, response);
		}else if ("allstu".equals(myMethod)) {
			//true：查询所有学生
			this.stuAll(request, response);
		}else if ("querySingle".equals(myMethod)){
			//students?method=querySingle&id=${stu.stuId }
			//按编号查询学生信息
			this.studentQuerySingle(request, response);
		}else if ("update".equals(myMethod)){
			//<input type="hidden" name="method" value="update"/> <!-- 启动修改 -->
			//修改学生信息
			this.studentUpdate(request, response);
		}else if ("advance".equals(myMethod)){
			// <input type="hidden" name="method" value="advance"/><!-- 启动控制器高级查询 -->
			//按高级查询/组合查询表单传递DTO进行组合查询
			this.studentAdvance(request, response);
		}else if ("page".equals(myMethod)){
			// login.jsp-->   <a href="students?method=page">学生信息管理</a><br/><!-- 实现分页 -->
			//按高级查询/组合查询表单传递DTO进行组合分页查询
			this.studentAdvancePage(request, response);
		}
	}
	
	public void studentAdvancePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet高级/组合分页查询学生信息");
		
		int pageNo = 1;//默认为第一页
		int pageSize = 2;//默认为每页2条记录
		if (!isBlank(request.getParameter("pageNo")))
			//true：不为空，传递了页号或者页的大小
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		if (!isBlank(request.getParameter("pageSize")))
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		HttpSession session = request.getSession();
		
		StudentDTO dto = null;
		if ("1".equals(request.getParameter("flag"))) {
			System.out.println("区分是否第一次提交："+request.getParameter("flag"));
			//true：高级查询表单提交了组合条件
			dto = new StudentDTO();
			
			String stuId = request.getParameter("search_stuid");
			if (!this.isBlank(stuId)) {
				//文本框传递的学生编号不为空
				dto.setSearch_stuid(Integer.parseInt(stuId.trim()));//学生编号封装进DTO
			}
			
			if (!isBlank(request.getParameter("search_stuname"))) {
				//判断学生姓名文本框是否传值！
				dto.setSearch_stuname(request.getParameter("search_stuname"));
			}
			if (!isBlank(request.getParameter("search_stuage1"))&& !isBlank(request.getParameter("search_stuage2"))) {
				//判断年龄文本框是否传值！
				dto.setSearch_stuage1(Integer.parseInt(request.getParameter("search_stuage1")));
				dto.setSearch_stuage2(Integer.parseInt(request.getParameter("search_stuage2")));
			}
			if (!isBlank(request.getParameter("search_birthdayDate1")) && !isBlank(request.getParameter("search_birthdayDate2"))) {
				//判断出生日期两个文本框是否传值！
				dto.setSearch_birthdayDate1(request.getParameter("search_birthdayDate1"));
				dto.setSearch_birthdayDate2(request.getParameter("search_birthdayDate2"));
			}
			
			System.out.println("当前分页DTO传递的条件："+dto.toString());
			
			session.setAttribute("dto", dto);//提交的组合条件DTO存放在会话范围内
		}else{
			//如果已经提交过查询条件，直接自会话范围内获取DTO
			dto = (StudentDTO) request.getSession().getAttribute("dto");
		}
		
		//使用DAO根据DTO提交的参数执行分页查询
		IStudentDAO dao = new StudentDaoImpl();
//		String no = request.getParameter("pageNo");
//		System.out.println("传递的页号："+no);
//		pageNo = Integer.parseInt(no);
//		
//		String size = request.getParameter("pageSize");
//		System.out.println("传递的页大小："+size);
//		pageSize = Integer.parseInt(size);
		
		List<TblStuinfo> pageStudents = dao.selectStudentsByPage(pageNo, pageSize, dto);//根据条件分页查询的所有学生
		
		int count = dao.getTotalCount(dto);//根据条件分页查询的学生记录总数
		
		//使用PageBean分页工具类存放所有分页数据到会话范围内
		PageBean<TblStuinfo> pageBean = new PageBean<TblStuinfo>();
		pageBean.setData(pageStudents);//分页结果
		pageBean.setPageNo(pageNo);//默认第几页
		pageBean.setPageSize(pageSize);//默认每一页大小
		pageBean.setTotalRecords(count);//记录总数
		
		session.setAttribute("pb", pageBean);//分页数据存放到会话范围内
		
		response.sendRedirect("stuall.jsp");//跳转到学生显示界面
	}
	
	public void studentAdvance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet高级/组合查询学生信息");
		HttpSession session = request.getSession();
		
		StudentDTO dto = new StudentDTO();
		
		String stuId = request.getParameter("search_stuid");
		if (!this.isBlank(stuId)) {
			//文本框传递的学生编号不为空
			dto.setSearch_stuid(Integer.parseInt(stuId.trim()));//学生编号封装进DTO
		}
		
		if (!isBlank(request.getParameter("search_stuname"))) {
			//判断学生姓名文本框是否传值！
			dto.setSearch_stuname(request.getParameter("search_stuname"));
		}
		if (!isBlank(request.getParameter("search_stuage1"))
				&& !isBlank(request.getParameter("search_stuage2"))) {
			//判断年龄文本框是否传值！
					dto.setSearch_stuage1(Integer.parseInt(request.getParameter("search_stuage1")));
					dto.setSearch_stuage2(Integer.parseInt(request.getParameter("search_stuage2")));
		}
		if (!isBlank(request.getParameter("search_birthdayDate1")) && !isBlank(request.getParameter("search_birthdayDate2"))) {
			//判断出生日期两个文本框是否传值！
			dto.setSearch_birthdayDate1(request.getParameter("search_birthdayDate1"));
			dto.setSearch_birthdayDate2(request.getParameter("search_birthdayDate2"));
		}
		
		System.out.println("当前DTO传递的条件："+dto.toString());
		
		IStudentDAO dao = new StudentDaoImpl();
		List<TblStuinfo> allStu = dao.stuAdvanceQuery(dto);
		
		session.setAttribute("dto", dto);//在会话范围内保存DTO（组合查询条件）
		session.setAttribute("all", allStu);//在会话范围内保存组合查询的用户信息
		
		response.sendRedirect("stuall.jsp");//跳转到用户查询界面stuall.jsp
	}
	
	
	public void stuDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String stuNo = request.getParameter("id").trim();
		System.out.println("删除传递学生学号："+stuNo);
		IStudentDAO dao = new StudentDaoImpl();
		int count = dao.deleteStudent(Integer.parseInt(stuNo));
		if (count > 0) {
			//删除成功
			this.stuAll(request, response);//跳转到全部查询Servlet- FindAllStudentsControlServlet
		} else {
			 request.setAttribute("errorMsg", "删除用户信息失败！");//请求范围内存放错误原因
	         request.getRequestDispatcher("errors.jsp").forward(request, response);//请求转发到错误页面errors.jsp
	        
		}
	
	}
	public void stuAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		IStudentDAO dao = new StudentDaoImpl();
		List<TblStuinfo> allStu = dao.selectAllStudent();//查询所有学生
		
		HttpSession session = request.getSession();//打开会话
		session.setAttribute("all", allStu);//会话存放所有查询的学生信息
	
		response.sendRedirect("stuall.jsp");//跳转到所有学生信息的管理界面stuall.jsp
	}
	
	public void studentRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet注册学生信息");
		
		//自表单取出学生的每一个值
		String stuName =request.getParameter("stuName");
		String stuAge =request.getParameter("stuAge");
		//  <input type="radio" name="stuSex" value="0" checked="checked"/>男
		String stuSex =request.getParameter("stuSex");
		System.out.println("表单传递性别："+stuSex);
		String stuBirthday =request.getParameter("stuBirthday");//yyyy-MM-dd
		System.out.println("表单传递的String类型日期："+stuBirthday);
		
		TblStuinfo student = new TblStuinfo();
		student.setStuName(stuName);
		student.setStuAge(Integer.parseInt(stuAge));
		int mySex = Integer.parseInt(stuSex);
		
		switch (mySex) {
		case 0:
			student.setStuSex("男");
			break;
		case 1:
			student.setStuSex("女");
			break;
		default:
			System.out.println("性别错误");
			break;
		}
		
		//必须将表单日期字符串转换为指定格式的java.util.Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myBirthday = null;
		try {
			myBirthday = sdf.parse(stuBirthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("转换后的日期："+myBirthday.toLocaleString());
		student.setStuBirthday(myBirthday);
		
		System.out.println("表单封装的学生结果："+student.toString());
		IStudentDAO dao = new StudentDaoImpl();
		int count= dao.insertStudent(student);
		
		PrintWriter out = response.getWriter();
		if (count > 0) {
			//新增成功
			response.sendRedirect("stuall.jsp");//跳转到全部查询页面显示学生信息
		} else {
		   out.println("新增失败，<a href='stuall.jsp'>学生信息列表</a>");

		}
		
		out.flush();
		out.close();
		
	}
	
	public void studentUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet按编号修改学生信息");
		
						//自stuupdate.jsp修改表单取出学生的每一个值
						String stuNo = request.getParameter("stuId");
						String stuName =request.getParameter("stuName");
						String stuAge =request.getParameter("stuAge");
						//  <input type="radio" name="stuSex" value="0" checked="checked"/>男
						String stuSex =request.getParameter("stuSex");
						System.out.println("表单传递性别："+stuSex);
						String stuBirthday =request.getParameter("stuBirthday");
						System.out.println("表单传递的String类型日期："+stuBirthday);
						
						TblStuinfo student = new TblStuinfo();
						student.setStuId(Integer.parseInt(stuNo));
						student.setStuName(stuName);
						student.setStuAge(Integer.parseInt(stuAge));
						
						int mySex = Integer.parseInt(stuSex);
						switch (mySex) {
						case 0:
							student.setStuSex("男");
							break;
						case 1:
							student.setStuSex("女");
							break;
						default:
							System.out.println("性别错误");
							break;
						}
						
						//必须将表单日期字符串转换为指定格式的java.util.Date
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date myBirthday = null;
						try {
							myBirthday = sdf.parse(stuBirthday);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("转换后的日期："+myBirthday.toLocaleString());
						student.setStuBirthday(myBirthday);
						//MyBeanUtils.populate(student,Students.class)
						System.out.println("表单封装的结果："+student.toString());
						IStudentDAO dao = new StudentDaoImpl();
						int count= dao.updateStudent(student);
						
						PrintWriter out = response.getWriter();
						if (count > 0) {
							//修改成功
							this.stuAll(request, response);//全部查询
						} else {
						   out.println("修改失败，<a href='a'>学生信息列表</a>");

						}
						
						out.flush();
						out.close();
	}
	
	public void studentQuerySingle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet按编号查询学生信息");
		IStudentDAO dao = new StudentDaoImpl();
		
		String studentNo = request.getParameter("id");
		System.out.println("stuall.jsp传递学生编号："+studentNo);
		TblStuinfo stu = dao.selectStudentById(Integer.parseInt(studentNo));
		System.out.println("待修改的学生："+stu.toString());
		
		//数据库中查询学生中，性别列的值是男或者女。为了修改表单配置默认
		//其中的值转换为0或者1。
		String mySex1 = stu.getStuSex();
		String sex = "";
		System.out.println("判断男女"+("男".equals(mySex1)));
		if ("男".equals(mySex1)) {
			System.out.println("判断男");
			sex = "0";
		} else if("女".equals(mySex1)){
			System.out.println("判断女");
			sex = "1";
		}
		System.out.println("mySex："+sex);
		stu.setStuSex(sex);
		
		/**
		 * if(stu != null)->查询成功存放会话范围
		 * else out.println(查询失败)
		 */
		System.out.println("传递学生："+stu.toString());
		
		HttpSession session = request.getSession();//打开会话
		session.setAttribute("myStu", stu);//会话存放待修改的学生
		
		response.sendRedirect("stuupdate.jsp");//跳转到学生修改的表单界面
	}	
	
}
