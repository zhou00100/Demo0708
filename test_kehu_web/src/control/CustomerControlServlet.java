package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.TbCustomer;

import dao.CustomerDaoImpl;
import dao.ICustomerDAO;

import util.CustomerDTO;
import util.PageBean;

public class CustomerControlServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CustomerControlServlet() {
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
		}else if ("cusdelete".equals(myMethod)) {
			//true：按编号删除用户信息
			this.cusDelete(request, response);
		}else if ("allcus".equals(myMethod)) {
			//true：查询所有学生
			this.cusAll(request, response);
		}else if ("querySingle".equals(myMethod)){
			//students?method=querySingle&id=${stu.stuId }
			//按编号查询学生信息
			this.customerQuerySingle(request, response);
		}else if ("update".equals(myMethod)){
			//<input type="hidden" name="method" value="update"/> <!-- 启动修改 -->
			//修改学生信息
			this.customerUpdate(request, response);
		}else if ("advance".equals(myMethod)){
			// <input type="hidden" name="method" value="advance"/><!-- 启动控制器高级查询 -->
			//按高级查询/组合查询表单传递DTO进行组合查询
			this.customerAdvance(request, response);
		}else if ("page".equals(myMethod)){
			// login.jsp-->   <a href="students?method=page">学生信息管理</a><br/><!-- 实现分页 -->
			//按高级查询/组合查询表单传递DTO进行组合分页查询
			this.customerAdvancePage(request, response);
		}
	}
	
	public void customerAdvancePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int pageNo = 1;//默认为第一页
		int pageSize = 2;//默认为每页2条记录
		System.out.println("进来了");
		if (!isBlank(request.getParameter("pageNo")))
			//true：不为空，传递了页号或者页的大小
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		if (!isBlank(request.getParameter("pageSize")))
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		HttpSession session = request.getSession();
		
		CustomerDTO dto = null;
		if ("1".equals(request.getParameter("flag"))) {
			System.out.println("区分是否第一次提交："+request.getParameter("flag"));
			//true：高级查询表单提交了组合条件
			dto = new CustomerDTO();
			
			String cusId = request.getParameter("search_cusid");
			if (!this.isBlank(cusId)) {
				//文本框传递的学生编号不为空
				dto.setSearch_cusid(Integer.parseInt(cusId.trim()));//学生编号封装进DTO
			}
			
			if (!isBlank(request.getParameter("search_cusname"))) {
				//判断学生姓名文本框是否传值！
				dto.setSearch_cusname(request.getParameter("search_cusname"));
			}
			
			
			
			System.out.println("当前分页DTO传递的条件："+dto.toString());
			
			session.setAttribute("dto", dto);//提交的组合条件DTO存放在会话范围内
		}else{
			//如果已经提交过查询条件，直接自会话范围内获取DTO
			dto = (CustomerDTO) request.getSession().getAttribute("dto");
		}
		
		//使用DAO根据DTO提交的参数执行分页查询
		ICustomerDAO dao = new CustomerDaoImpl();

		
		List<TbCustomer> pageCustomer = dao.selectCustomerByPage(pageNo, pageSize, dto);//根据条件分页查询的所有学生
		
		int count = dao.getTotalCount(dto);//根据条件分页查询的学生记录总数
		
		//使用PageBean分页工具类存放所有分页数据到会话范围内
		PageBean<TbCustomer> pageBean = new PageBean<TbCustomer>();
		pageBean.setData(pageCustomer);//分页结果
		pageBean.setPageNo(pageNo);//默认第几页
		pageBean.setPageSize(pageSize);//默认每一页大小
		pageBean.setTotalRecords(count);//记录总数
		
		session.setAttribute("pb", pageBean);//分页数据存放到会话范围内
		System.out.println("pb"+pageBean);
		response.sendRedirect("customerquery.jsp");//跳转到学生显示界面
	}
	
	public void customerAdvance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet高级/组合查询学生信息");
		HttpSession session = request.getSession();
		
		CustomerDTO dto = new CustomerDTO();
		
		String cusId = request.getParameter("search_cusid");
		if (!this.isBlank(cusId)) {
			//文本框传递的学生编号不为空
			dto.setSearch_cusid(Integer.parseInt(cusId.trim()));//学生编号封装进DTO
		}
		
		if (!isBlank(request.getParameter("search_cusname"))) {
			//判断学生姓名文本框是否传值！
			dto.setSearch_cusname(request.getParameter("search_cusname"));
		}
		
		
		
		System.out.println("当前DTO传递的条件："+dto.toString());
		ICustomerDAO dao = new CustomerDaoImpl();
		
		List<TbCustomer> allCus = dao.cusAdvanceQuery(dto);
		
		session.setAttribute("dto", dto);//在会话范围内保存DTO（组合查询条件）
		session.setAttribute("all", allCus);//在会话范围内保存组合查询的用户信息
		
		response.sendRedirect("customerquery.jsp");//跳转到用户查询界面stuall.jsp
	}
	
	
	public void cusDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cusNo = request.getParameter("id").trim();
		System.out.println("删除传递学生学号："+cusNo);
		ICustomerDAO dao = new CustomerDaoImpl();
		int count = dao.deleteCustomer(Integer.parseInt(cusNo));
		if (count > 0) {
			//删除成功
			this.customerAdvancePage(request, response);//跳转到全部查询Servlet- FindAllStudentsControlServlet
		} else {
			 request.setAttribute("errorMsg", "删除用户信息失败！");//请求范围内存放错误原因
	         request.getRequestDispatcher("errors.jsp").forward(request, response);//请求转发到错误页面errors.jsp
	        
		}
	
	}
	public void cusAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ICustomerDAO dao = new CustomerDaoImpl();
		List<TbCustomer> allCus = dao.selectAllCustomer();//查询所有学生
		
		HttpSession session = request.getSession();//打开会话
		session.setAttribute("all", allCus);//会话存放所有查询的学生信息
	
		response.sendRedirect("customerquery.jsp");//跳转到所有学生信息的管理界面stuall.jsp
	}
	
	public void studentRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet注册学生信息");
		
		//自表单取出学生的每一个值
		String custName =request.getParameter("custName");
		String phone =request.getParameter("phone");
		
		String mobile =request.getParameter("mobile");
		String email =request.getParameter("email");
		String fax =request.getParameter("fax");//yyyy-MM-dd
		String address =request.getParameter("address");
		
		TbCustomer customer = new TbCustomer();
		
		customer.setCustName(custName);
		customer.setPhone(phone);
		customer.setMobile(mobile);
		customer.setEmail(email);
		customer.setFax(fax);
		customer.setAddress(address);
		
		
		
		ICustomerDAO dao = new CustomerDaoImpl();
		int count= dao.insertCustomer(customer);
		
		PrintWriter out = response.getWriter();
		if (count > 0) {
			//新增成功
			response.sendRedirect("customerquery.jsp");//跳转到全部查询页面显示学生信息
		} else {
		   out.println("新增失败，<a href='customerquery.jsp'>学生信息列表</a>");

		}
		
		out.flush();
		out.close();
		
	}
	
	public void customerUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet按编号修改学生信息");
		
						//自stuupdate.jsp修改表单取出学生的每一个值
		String custId = request.getParameter("custId");
		String custName =request.getParameter("custName");
		String phone =request.getParameter("phone");
		
		String mobile =request.getParameter("mobile");
		String email =request.getParameter("email");
		String fax =request.getParameter("fax");//yyyy-MM-dd
		String address =request.getParameter("address");
						
		TbCustomer customer = new TbCustomer();
		customer.setCustId(Integer.parseInt(custId));
		customer.setCustName(custName);
		customer.setPhone(phone);
		customer.setMobile(mobile);
		customer.setEmail(email);
		customer.setFax(fax);
		customer.setAddress(address);
		
		ICustomerDAO dao = new CustomerDaoImpl();
						int count= dao.updateCustomer(customer);
						
						PrintWriter out = response.getWriter();
						if (count > 0) {
							//修改成功
							this.customerAdvancePage(request, response);//全部查询
						} else {
						   out.println("修改失败，<a href='a'>学生信息列表</a>");

						}
						
						out.flush();
						out.close();
	}
	
	public void customerQuerySingle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet按编号查询学生信息");
		ICustomerDAO dao = new CustomerDaoImpl();
		
		String customerNo = request.getParameter("id");
		System.out.println("stuall.jsp传递学生编号："+customerNo);
		TbCustomer cus = dao.selectCustomerById(Integer.parseInt(customerNo));
		System.out.println("待修改的学生："+cus.toString());
		
		
		
		/**
		 * if(stu != null)->查询成功存放会话范围
		 * else out.println(查询失败)
		 */
		System.out.println("传递学生："+cus.toString());
		
		HttpSession session = request.getSession();//打开会话
		session.setAttribute("myStu", cus);//会话存放待修改的学生
		
		response.sendRedirect("customerupdate.jsp");//跳转到学生修改的表单界面
	}	
	
}
