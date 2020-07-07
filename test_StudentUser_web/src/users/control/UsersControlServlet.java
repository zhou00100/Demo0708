package users.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import users.dao.IUsersDAO;
import users.dao.UsersDAOImpl;
import users.entity.Users;

public class UsersControlServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UsersControlServlet() {
		super();
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
		
		String myMethod = request.getParameter("method");
		System.out.println("当前表单提交的功能请求是："+myMethod);
		
		
		if ("login".equals(myMethod)) {
			//true:启动用户登录功能
			this.userLogin(request, response);
		} else if ("register".equals(myMethod)) {
			//true：启动用户注册功能
			this.userRegister(request, response);
		}else if ("all".equals(myMethod)) {
			//true：启动用户全部查询
			this.userAll(request, response);
		}else if ("userSingle".equals(myMethod)) {
			//true：按编号查询准备修改用户信息
			this.userSingle(request, response);
		}else if ("update".equals(myMethod)) {
			//true：修改用户信息
			this.userUpdate(request, response);
		}else if ("delete".equals(myMethod)) {
			//true：按编号删除用户信息
			this.userDelete(request, response);
		}else if ("invalidate".equals(myMethod)) {
			//true：注销用户信息
			this.userInvalidate(request, response);
		}
		
	}
	public void userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("用户登录的方法");
		HttpSession session = request.getSession();
         String name = request.getParameter("userName");
		 String password = request.getParameter("userPassword");
         Users users = new Users();
		 users.setUserName(name);
		 users.setUserPassword(password); 
        IUsersDAO dao = new UsersDAOImpl();
        Users loginUser = dao.selectUserByNameAndPassword(users);
       
        if(loginUser != null){
           session.setAttribute("success", loginUser);//登录的用户对象放入会话范围内
           response.sendRedirect("login.jsp");//登录成功跳转到登录界面显示用户信息管理界面
        }else{
           request.setAttribute("errorMsg", "用户名和密码错误！！");//请求范围内存放错误原因
		   request.getRequestDispatcher("login.jsp").forward(request, response);//请求转发到登录界面login.jsp直接输出错误信息
        }

}
	public void userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("用户注册的方法");
		HttpSession session = request.getSession();
        String name = request.getParameter("userName");
        String pwd = request.getParameter("userPassword");
        //使用DAO类实现新增用户
        IUsersDAO usersDAO = new UsersDAOImpl();
		Users users = new Users();
		users.setUserName(name);
		users.setUserPassword(pwd);
		boolean is = usersDAO.insertUsers(users);//使用DAO发送新增SQL返回新增结果
		if(is){
         session.setAttribute("regUser", users);//将注册成功的用户保存在会话范围内
         response.sendRedirect("userinfo.jsp");//重定向到显示当前用户的注册信息。
        }else{
         request.setAttribute("errorMsg", "注册用户信息失败！");//请求范围内存放错误原因
         request.getRequestDispatcher("errors.jsp").forward(request, response);//请求转发到错误页面errors.jsp
        }
	}
	
	public void userAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("用户全部查询的方法");
		 IUsersDAO dao = new UsersDAOImpl();//实例化一个DAO对象
         List<Users> allUsers = dao.selectAllUsers();//查询所有用户
         HttpSession session = request.getSession();
         session.setAttribute("allUsers", allUsers);
         response.sendRedirect("allusers.jsp");//跳转到全部查询页面
	}
	
	public void userSingle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("按编号查询准备修改用户信息");
		//获取传递的用户编号：
        String id = request.getParameter("userId");
        System.out.println("传递的用户编号："+id);
        Users user1 = new Users();
        user1.setUserId(Integer.parseInt(id));//封装修改用户编号
        IUsersDAO dao = new UsersDAOImpl();
        Users updateUser = dao.selectUserById(user1);//按编号查询用户信息
        
        HttpSession session = request.getSession();
        session.setAttribute("updateUser", updateUser);//放在修改用户表单中待修改的用户信息
        
        response.sendRedirect("update.jsp");//跳转到修改用户页面
	}
	
	public void userUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("按编号修改用户信息");
		//获取修改表单提交的所有用户信息：
        String myId = request.getParameter("myId");
        String name = request.getParameter("userName");
        String password = request.getParameter("userPwd");
        Users users = new Users();
        users.setUserId(Integer.parseInt(myId));//封装用户编号
        users.setUserName(name);
        users.setUserPassword(password); 
        
        IUsersDAO dao = new UsersDAOImpl();
        boolean is = dao.updateUsersById(users);//按编号修改用户信息
       
       if(is){
          //true:修改成功，跳转到全部查询的allusers.jsp查看修改的结果。
          //第一种写法：
          //response.sendRedirect("users?method=all");
          //第二种写法：
          this.userAll(request, response);
       }else{
          //false：修改失败。跳转到出错的页面打印错误信息，允许用户回到首页
           request.setAttribute("errorMsg", "修改用户信息失败！");//请求范围内存放错误原因
           request.getRequestDispatcher("errors.jsp").forward(request, response);//请求转发到错误页面errors.jsp
       }         
	}
	
	public void userDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String id = request.getParameter("userId");
        
        System.out.println("请求传递的用户编号："+id);
        Users users = new Users();
        users.setUserId(Integer.parseInt(id));//封装传递用户编号进实体类
    
        //DAO类执行删除，跳转到全部显示页面显示删除后的结果。
        IUsersDAO dao = new UsersDAOImpl();
        boolean is = dao.deleteUsersById(users);//执行按编号删除
        if(is){
           //true:删除成功
       	 this.userAll(request, response);//跳转到用户信息管理界面显示删除后的用户信息。
        }else{
           //false：删除失败
            request.setAttribute("errorMsg", "删除用户信息失败！");//请求范围内存放错误原因
            request.getRequestDispatcher("errors.jsp").forward(request, response);//请求转发到错误页面errors.jsp
        }
	}
	
	public void userInvalidate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 HttpSession session = request.getSession();
		 session.invalidate();//会话注销
		 response.sendRedirect("login.jsp");//返回登录login.jsp
	}
}
