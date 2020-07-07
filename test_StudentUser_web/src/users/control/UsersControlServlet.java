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
		System.out.println("��ǰ���ύ�Ĺ��������ǣ�"+myMethod);
		
		
		if ("login".equals(myMethod)) {
			//true:�����û���¼����
			this.userLogin(request, response);
		} else if ("register".equals(myMethod)) {
			//true�������û�ע�Ṧ��
			this.userRegister(request, response);
		}else if ("all".equals(myMethod)) {
			//true�������û�ȫ����ѯ
			this.userAll(request, response);
		}else if ("userSingle".equals(myMethod)) {
			//true������Ų�ѯ׼���޸��û���Ϣ
			this.userSingle(request, response);
		}else if ("update".equals(myMethod)) {
			//true���޸��û���Ϣ
			this.userUpdate(request, response);
		}else if ("delete".equals(myMethod)) {
			//true�������ɾ���û���Ϣ
			this.userDelete(request, response);
		}else if ("invalidate".equals(myMethod)) {
			//true��ע���û���Ϣ
			this.userInvalidate(request, response);
		}
		
	}
	public void userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("�û���¼�ķ���");
		HttpSession session = request.getSession();
         String name = request.getParameter("userName");
		 String password = request.getParameter("userPassword");
         Users users = new Users();
		 users.setUserName(name);
		 users.setUserPassword(password); 
        IUsersDAO dao = new UsersDAOImpl();
        Users loginUser = dao.selectUserByNameAndPassword(users);
       
        if(loginUser != null){
           session.setAttribute("success", loginUser);//��¼���û��������Ự��Χ��
           response.sendRedirect("login.jsp");//��¼�ɹ���ת����¼������ʾ�û���Ϣ�������
        }else{
           request.setAttribute("errorMsg", "�û�����������󣡣�");//����Χ�ڴ�Ŵ���ԭ��
		   request.getRequestDispatcher("login.jsp").forward(request, response);//����ת������¼����login.jspֱ�����������Ϣ
        }

}
	public void userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("�û�ע��ķ���");
		HttpSession session = request.getSession();
        String name = request.getParameter("userName");
        String pwd = request.getParameter("userPassword");
        //ʹ��DAO��ʵ�������û�
        IUsersDAO usersDAO = new UsersDAOImpl();
		Users users = new Users();
		users.setUserName(name);
		users.setUserPassword(pwd);
		boolean is = usersDAO.insertUsers(users);//ʹ��DAO��������SQL�����������
		if(is){
         session.setAttribute("regUser", users);//��ע��ɹ����û������ڻỰ��Χ��
         response.sendRedirect("userinfo.jsp");//�ض�����ʾ��ǰ�û���ע����Ϣ��
        }else{
         request.setAttribute("errorMsg", "ע���û���Ϣʧ�ܣ�");//����Χ�ڴ�Ŵ���ԭ��
         request.getRequestDispatcher("errors.jsp").forward(request, response);//����ת��������ҳ��errors.jsp
        }
	}
	
	public void userAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("�û�ȫ����ѯ�ķ���");
		 IUsersDAO dao = new UsersDAOImpl();//ʵ����һ��DAO����
         List<Users> allUsers = dao.selectAllUsers();//��ѯ�����û�
         HttpSession session = request.getSession();
         session.setAttribute("allUsers", allUsers);
         response.sendRedirect("allusers.jsp");//��ת��ȫ����ѯҳ��
	}
	
	public void userSingle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("����Ų�ѯ׼���޸��û���Ϣ");
		//��ȡ���ݵ��û���ţ�
        String id = request.getParameter("userId");
        System.out.println("���ݵ��û���ţ�"+id);
        Users user1 = new Users();
        user1.setUserId(Integer.parseInt(id));//��װ�޸��û����
        IUsersDAO dao = new UsersDAOImpl();
        Users updateUser = dao.selectUserById(user1);//����Ų�ѯ�û���Ϣ
        
        HttpSession session = request.getSession();
        session.setAttribute("updateUser", updateUser);//�����޸��û����д��޸ĵ��û���Ϣ
        
        response.sendRedirect("update.jsp");//��ת���޸��û�ҳ��
	}
	
	public void userUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("������޸��û���Ϣ");
		//��ȡ�޸ı��ύ�������û���Ϣ��
        String myId = request.getParameter("myId");
        String name = request.getParameter("userName");
        String password = request.getParameter("userPwd");
        Users users = new Users();
        users.setUserId(Integer.parseInt(myId));//��װ�û����
        users.setUserName(name);
        users.setUserPassword(password); 
        
        IUsersDAO dao = new UsersDAOImpl();
        boolean is = dao.updateUsersById(users);//������޸��û���Ϣ
       
       if(is){
          //true:�޸ĳɹ�����ת��ȫ����ѯ��allusers.jsp�鿴�޸ĵĽ����
          //��һ��д����
          //response.sendRedirect("users?method=all");
          //�ڶ���д����
          this.userAll(request, response);
       }else{
          //false���޸�ʧ�ܡ���ת�������ҳ���ӡ������Ϣ�������û��ص���ҳ
           request.setAttribute("errorMsg", "�޸��û���Ϣʧ�ܣ�");//����Χ�ڴ�Ŵ���ԭ��
           request.getRequestDispatcher("errors.jsp").forward(request, response);//����ת��������ҳ��errors.jsp
       }         
	}
	
	public void userDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String id = request.getParameter("userId");
        
        System.out.println("���󴫵ݵ��û���ţ�"+id);
        Users users = new Users();
        users.setUserId(Integer.parseInt(id));//��װ�����û���Ž�ʵ����
    
        //DAO��ִ��ɾ������ת��ȫ����ʾҳ����ʾɾ����Ľ����
        IUsersDAO dao = new UsersDAOImpl();
        boolean is = dao.deleteUsersById(users);//ִ�а����ɾ��
        if(is){
           //true:ɾ���ɹ�
       	 this.userAll(request, response);//��ת���û���Ϣ���������ʾɾ������û���Ϣ��
        }else{
           //false��ɾ��ʧ��
            request.setAttribute("errorMsg", "ɾ���û���Ϣʧ�ܣ�");//����Χ�ڴ�Ŵ���ԭ��
            request.getRequestDispatcher("errors.jsp").forward(request, response);//����ת��������ҳ��errors.jsp
        }
	}
	
	public void userInvalidate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 HttpSession session = request.getSession();
		 session.invalidate();//�Ựע��
		 response.sendRedirect("login.jsp");//���ص�¼login.jsp
	}
}
