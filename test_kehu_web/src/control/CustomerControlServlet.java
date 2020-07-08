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
	//�жϱ����ݵĲ����Ƿ�Ϊ��
	public boolean isBlank(String param) {
		return "".equals(param) || param == null;//true���ı���û�������κ�ֵ
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
		
		//ʹ�ñ��������ر��򴫵�method�������ֲ�ͬ���û�����
		String myMethod = request.getParameter("method");
		System.out.println("��ǰ���ύ�Ĺ��������ǣ�"+myMethod);
		
		
		if ("studentreg".equals(myMethod)) {
			//true:����ѧ��ע�Ṧ��
			this.studentRegister(request, response);
		}else if ("cusdelete".equals(myMethod)) {
			//true�������ɾ���û���Ϣ
			this.cusDelete(request, response);
		}else if ("allcus".equals(myMethod)) {
			//true����ѯ����ѧ��
			this.cusAll(request, response);
		}else if ("querySingle".equals(myMethod)){
			//students?method=querySingle&id=${stu.stuId }
			//����Ų�ѯѧ����Ϣ
			this.customerQuerySingle(request, response);
		}else if ("update".equals(myMethod)){
			//<input type="hidden" name="method" value="update"/> <!-- �����޸� -->
			//�޸�ѧ����Ϣ
			this.customerUpdate(request, response);
		}else if ("advance".equals(myMethod)){
			// <input type="hidden" name="method" value="advance"/><!-- �����������߼���ѯ -->
			//���߼���ѯ/��ϲ�ѯ������DTO������ϲ�ѯ
			this.customerAdvance(request, response);
		}else if ("page".equals(myMethod)){
			// login.jsp-->   <a href="students?method=page">ѧ����Ϣ����</a><br/><!-- ʵ�ַ�ҳ -->
			//���߼���ѯ/��ϲ�ѯ������DTO������Ϸ�ҳ��ѯ
			this.customerAdvancePage(request, response);
		}
	}
	
	public void customerAdvancePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		int pageNo = 1;//Ĭ��Ϊ��һҳ
		int pageSize = 2;//Ĭ��Ϊÿҳ2����¼
		System.out.println("������");
		if (!isBlank(request.getParameter("pageNo")))
			//true����Ϊ�գ�������ҳ�Ż���ҳ�Ĵ�С
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		if (!isBlank(request.getParameter("pageSize")))
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		HttpSession session = request.getSession();
		
		CustomerDTO dto = null;
		if ("1".equals(request.getParameter("flag"))) {
			System.out.println("�����Ƿ��һ���ύ��"+request.getParameter("flag"));
			//true���߼���ѯ���ύ���������
			dto = new CustomerDTO();
			
			String cusId = request.getParameter("search_cusid");
			if (!this.isBlank(cusId)) {
				//�ı��򴫵ݵ�ѧ����Ų�Ϊ��
				dto.setSearch_cusid(Integer.parseInt(cusId.trim()));//ѧ����ŷ�װ��DTO
			}
			
			if (!isBlank(request.getParameter("search_cusname"))) {
				//�ж�ѧ�������ı����Ƿ�ֵ��
				dto.setSearch_cusname(request.getParameter("search_cusname"));
			}
			
			
			
			System.out.println("��ǰ��ҳDTO���ݵ�������"+dto.toString());
			
			session.setAttribute("dto", dto);//�ύ���������DTO����ڻỰ��Χ��
		}else{
			//����Ѿ��ύ����ѯ������ֱ���ԻỰ��Χ�ڻ�ȡDTO
			dto = (CustomerDTO) request.getSession().getAttribute("dto");
		}
		
		//ʹ��DAO����DTO�ύ�Ĳ���ִ�з�ҳ��ѯ
		ICustomerDAO dao = new CustomerDaoImpl();

		
		List<TbCustomer> pageCustomer = dao.selectCustomerByPage(pageNo, pageSize, dto);//����������ҳ��ѯ������ѧ��
		
		int count = dao.getTotalCount(dto);//����������ҳ��ѯ��ѧ����¼����
		
		//ʹ��PageBean��ҳ�����������з�ҳ���ݵ��Ự��Χ��
		PageBean<TbCustomer> pageBean = new PageBean<TbCustomer>();
		pageBean.setData(pageCustomer);//��ҳ���
		pageBean.setPageNo(pageNo);//Ĭ�ϵڼ�ҳ
		pageBean.setPageSize(pageSize);//Ĭ��ÿһҳ��С
		pageBean.setTotalRecords(count);//��¼����
		
		session.setAttribute("pb", pageBean);//��ҳ���ݴ�ŵ��Ự��Χ��
		System.out.println("pb"+pageBean);
		response.sendRedirect("customerquery.jsp");//��ת��ѧ����ʾ����
	}
	
	public void customerAdvance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet�߼�/��ϲ�ѯѧ����Ϣ");
		HttpSession session = request.getSession();
		
		CustomerDTO dto = new CustomerDTO();
		
		String cusId = request.getParameter("search_cusid");
		if (!this.isBlank(cusId)) {
			//�ı��򴫵ݵ�ѧ����Ų�Ϊ��
			dto.setSearch_cusid(Integer.parseInt(cusId.trim()));//ѧ����ŷ�װ��DTO
		}
		
		if (!isBlank(request.getParameter("search_cusname"))) {
			//�ж�ѧ�������ı����Ƿ�ֵ��
			dto.setSearch_cusname(request.getParameter("search_cusname"));
		}
		
		
		
		System.out.println("��ǰDTO���ݵ�������"+dto.toString());
		ICustomerDAO dao = new CustomerDaoImpl();
		
		List<TbCustomer> allCus = dao.cusAdvanceQuery(dto);
		
		session.setAttribute("dto", dto);//�ڻỰ��Χ�ڱ���DTO����ϲ�ѯ������
		session.setAttribute("all", allCus);//�ڻỰ��Χ�ڱ�����ϲ�ѯ���û���Ϣ
		
		response.sendRedirect("customerquery.jsp");//��ת���û���ѯ����stuall.jsp
	}
	
	
	public void cusDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String cusNo = request.getParameter("id").trim();
		System.out.println("ɾ������ѧ��ѧ�ţ�"+cusNo);
		ICustomerDAO dao = new CustomerDaoImpl();
		int count = dao.deleteCustomer(Integer.parseInt(cusNo));
		if (count > 0) {
			//ɾ���ɹ�
			this.customerAdvancePage(request, response);//��ת��ȫ����ѯServlet- FindAllStudentsControlServlet
		} else {
			 request.setAttribute("errorMsg", "ɾ���û���Ϣʧ�ܣ�");//����Χ�ڴ�Ŵ���ԭ��
	         request.getRequestDispatcher("errors.jsp").forward(request, response);//����ת��������ҳ��errors.jsp
	        
		}
	
	}
	public void cusAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ICustomerDAO dao = new CustomerDaoImpl();
		List<TbCustomer> allCus = dao.selectAllCustomer();//��ѯ����ѧ��
		
		HttpSession session = request.getSession();//�򿪻Ự
		session.setAttribute("all", allCus);//�Ự������в�ѯ��ѧ����Ϣ
	
		response.sendRedirect("customerquery.jsp");//��ת������ѧ����Ϣ�Ĺ������stuall.jsp
	}
	
	public void studentRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServletע��ѧ����Ϣ");
		
		//�Ա�ȡ��ѧ����ÿһ��ֵ
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
			//�����ɹ�
			response.sendRedirect("customerquery.jsp");//��ת��ȫ����ѯҳ����ʾѧ����Ϣ
		} else {
		   out.println("����ʧ�ܣ�<a href='customerquery.jsp'>ѧ����Ϣ�б�</a>");

		}
		
		out.flush();
		out.close();
		
	}
	
	public void customerUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet������޸�ѧ����Ϣ");
		
						//��stuupdate.jsp�޸ı�ȡ��ѧ����ÿһ��ֵ
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
							//�޸ĳɹ�
							this.customerAdvancePage(request, response);//ȫ����ѯ
						} else {
						   out.println("�޸�ʧ�ܣ�<a href='a'>ѧ����Ϣ�б�</a>");

						}
						
						out.flush();
						out.close();
	}
	
	public void customerQuerySingle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet����Ų�ѯѧ����Ϣ");
		ICustomerDAO dao = new CustomerDaoImpl();
		
		String customerNo = request.getParameter("id");
		System.out.println("stuall.jsp����ѧ����ţ�"+customerNo);
		TbCustomer cus = dao.selectCustomerById(Integer.parseInt(customerNo));
		System.out.println("���޸ĵ�ѧ����"+cus.toString());
		
		
		
		/**
		 * if(stu != null)->��ѯ�ɹ���ŻỰ��Χ
		 * else out.println(��ѯʧ��)
		 */
		System.out.println("����ѧ����"+cus.toString());
		
		HttpSession session = request.getSession();//�򿪻Ự
		session.setAttribute("myStu", cus);//�Ự��Ŵ��޸ĵ�ѧ��
		
		response.sendRedirect("customerupdate.jsp");//��ת��ѧ���޸ĵı�����
	}	
	
}
