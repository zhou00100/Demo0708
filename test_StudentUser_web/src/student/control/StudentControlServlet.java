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
		}else if ("studelete".equals(myMethod)) {
			//true�������ɾ���û���Ϣ
			this.stuDelete(request, response);
		}else if ("allstu".equals(myMethod)) {
			//true����ѯ����ѧ��
			this.stuAll(request, response);
		}else if ("querySingle".equals(myMethod)){
			//students?method=querySingle&id=${stu.stuId }
			//����Ų�ѯѧ����Ϣ
			this.studentQuerySingle(request, response);
		}else if ("update".equals(myMethod)){
			//<input type="hidden" name="method" value="update"/> <!-- �����޸� -->
			//�޸�ѧ����Ϣ
			this.studentUpdate(request, response);
		}else if ("advance".equals(myMethod)){
			// <input type="hidden" name="method" value="advance"/><!-- �����������߼���ѯ -->
			//���߼���ѯ/��ϲ�ѯ������DTO������ϲ�ѯ
			this.studentAdvance(request, response);
		}else if ("page".equals(myMethod)){
			// login.jsp-->   <a href="students?method=page">ѧ����Ϣ����</a><br/><!-- ʵ�ַ�ҳ -->
			//���߼���ѯ/��ϲ�ѯ������DTO������Ϸ�ҳ��ѯ
			this.studentAdvancePage(request, response);
		}
	}
	
	public void studentAdvancePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet�߼�/��Ϸ�ҳ��ѯѧ����Ϣ");
		
		int pageNo = 1;//Ĭ��Ϊ��һҳ
		int pageSize = 2;//Ĭ��Ϊÿҳ2����¼
		if (!isBlank(request.getParameter("pageNo")))
			//true����Ϊ�գ�������ҳ�Ż���ҳ�Ĵ�С
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		if (!isBlank(request.getParameter("pageSize")))
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		HttpSession session = request.getSession();
		
		StudentDTO dto = null;
		if ("1".equals(request.getParameter("flag"))) {
			System.out.println("�����Ƿ��һ���ύ��"+request.getParameter("flag"));
			//true���߼���ѯ���ύ���������
			dto = new StudentDTO();
			
			String stuId = request.getParameter("search_stuid");
			if (!this.isBlank(stuId)) {
				//�ı��򴫵ݵ�ѧ����Ų�Ϊ��
				dto.setSearch_stuid(Integer.parseInt(stuId.trim()));//ѧ����ŷ�װ��DTO
			}
			
			if (!isBlank(request.getParameter("search_stuname"))) {
				//�ж�ѧ�������ı����Ƿ�ֵ��
				dto.setSearch_stuname(request.getParameter("search_stuname"));
			}
			if (!isBlank(request.getParameter("search_stuage1"))&& !isBlank(request.getParameter("search_stuage2"))) {
				//�ж������ı����Ƿ�ֵ��
				dto.setSearch_stuage1(Integer.parseInt(request.getParameter("search_stuage1")));
				dto.setSearch_stuage2(Integer.parseInt(request.getParameter("search_stuage2")));
			}
			if (!isBlank(request.getParameter("search_birthdayDate1")) && !isBlank(request.getParameter("search_birthdayDate2"))) {
				//�жϳ������������ı����Ƿ�ֵ��
				dto.setSearch_birthdayDate1(request.getParameter("search_birthdayDate1"));
				dto.setSearch_birthdayDate2(request.getParameter("search_birthdayDate2"));
			}
			
			System.out.println("��ǰ��ҳDTO���ݵ�������"+dto.toString());
			
			session.setAttribute("dto", dto);//�ύ���������DTO����ڻỰ��Χ��
		}else{
			//����Ѿ��ύ����ѯ������ֱ���ԻỰ��Χ�ڻ�ȡDTO
			dto = (StudentDTO) request.getSession().getAttribute("dto");
		}
		
		//ʹ��DAO����DTO�ύ�Ĳ���ִ�з�ҳ��ѯ
		IStudentDAO dao = new StudentDaoImpl();
//		String no = request.getParameter("pageNo");
//		System.out.println("���ݵ�ҳ�ţ�"+no);
//		pageNo = Integer.parseInt(no);
//		
//		String size = request.getParameter("pageSize");
//		System.out.println("���ݵ�ҳ��С��"+size);
//		pageSize = Integer.parseInt(size);
		
		List<TblStuinfo> pageStudents = dao.selectStudentsByPage(pageNo, pageSize, dto);//����������ҳ��ѯ������ѧ��
		
		int count = dao.getTotalCount(dto);//����������ҳ��ѯ��ѧ����¼����
		
		//ʹ��PageBean��ҳ�����������з�ҳ���ݵ��Ự��Χ��
		PageBean<TblStuinfo> pageBean = new PageBean<TblStuinfo>();
		pageBean.setData(pageStudents);//��ҳ���
		pageBean.setPageNo(pageNo);//Ĭ�ϵڼ�ҳ
		pageBean.setPageSize(pageSize);//Ĭ��ÿһҳ��С
		pageBean.setTotalRecords(count);//��¼����
		
		session.setAttribute("pb", pageBean);//��ҳ���ݴ�ŵ��Ự��Χ��
		
		response.sendRedirect("stuall.jsp");//��ת��ѧ����ʾ����
	}
	
	public void studentAdvance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet�߼�/��ϲ�ѯѧ����Ϣ");
		HttpSession session = request.getSession();
		
		StudentDTO dto = new StudentDTO();
		
		String stuId = request.getParameter("search_stuid");
		if (!this.isBlank(stuId)) {
			//�ı��򴫵ݵ�ѧ����Ų�Ϊ��
			dto.setSearch_stuid(Integer.parseInt(stuId.trim()));//ѧ����ŷ�װ��DTO
		}
		
		if (!isBlank(request.getParameter("search_stuname"))) {
			//�ж�ѧ�������ı����Ƿ�ֵ��
			dto.setSearch_stuname(request.getParameter("search_stuname"));
		}
		if (!isBlank(request.getParameter("search_stuage1"))
				&& !isBlank(request.getParameter("search_stuage2"))) {
			//�ж������ı����Ƿ�ֵ��
					dto.setSearch_stuage1(Integer.parseInt(request.getParameter("search_stuage1")));
					dto.setSearch_stuage2(Integer.parseInt(request.getParameter("search_stuage2")));
		}
		if (!isBlank(request.getParameter("search_birthdayDate1")) && !isBlank(request.getParameter("search_birthdayDate2"))) {
			//�жϳ������������ı����Ƿ�ֵ��
			dto.setSearch_birthdayDate1(request.getParameter("search_birthdayDate1"));
			dto.setSearch_birthdayDate2(request.getParameter("search_birthdayDate2"));
		}
		
		System.out.println("��ǰDTO���ݵ�������"+dto.toString());
		
		IStudentDAO dao = new StudentDaoImpl();
		List<TblStuinfo> allStu = dao.stuAdvanceQuery(dto);
		
		session.setAttribute("dto", dto);//�ڻỰ��Χ�ڱ���DTO����ϲ�ѯ������
		session.setAttribute("all", allStu);//�ڻỰ��Χ�ڱ�����ϲ�ѯ���û���Ϣ
		
		response.sendRedirect("stuall.jsp");//��ת���û���ѯ����stuall.jsp
	}
	
	
	public void stuDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String stuNo = request.getParameter("id").trim();
		System.out.println("ɾ������ѧ��ѧ�ţ�"+stuNo);
		IStudentDAO dao = new StudentDaoImpl();
		int count = dao.deleteStudent(Integer.parseInt(stuNo));
		if (count > 0) {
			//ɾ���ɹ�
			this.stuAll(request, response);//��ת��ȫ����ѯServlet- FindAllStudentsControlServlet
		} else {
			 request.setAttribute("errorMsg", "ɾ���û���Ϣʧ�ܣ�");//����Χ�ڴ�Ŵ���ԭ��
	         request.getRequestDispatcher("errors.jsp").forward(request, response);//����ת��������ҳ��errors.jsp
	        
		}
	
	}
	public void stuAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		IStudentDAO dao = new StudentDaoImpl();
		List<TblStuinfo> allStu = dao.selectAllStudent();//��ѯ����ѧ��
		
		HttpSession session = request.getSession();//�򿪻Ự
		session.setAttribute("all", allStu);//�Ự������в�ѯ��ѧ����Ϣ
	
		response.sendRedirect("stuall.jsp");//��ת������ѧ����Ϣ�Ĺ������stuall.jsp
	}
	
	public void studentRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServletע��ѧ����Ϣ");
		
		//�Ա�ȡ��ѧ����ÿһ��ֵ
		String stuName =request.getParameter("stuName");
		String stuAge =request.getParameter("stuAge");
		//  <input type="radio" name="stuSex" value="0" checked="checked"/>��
		String stuSex =request.getParameter("stuSex");
		System.out.println("�������Ա�"+stuSex);
		String stuBirthday =request.getParameter("stuBirthday");//yyyy-MM-dd
		System.out.println("�����ݵ�String�������ڣ�"+stuBirthday);
		
		TblStuinfo student = new TblStuinfo();
		student.setStuName(stuName);
		student.setStuAge(Integer.parseInt(stuAge));
		int mySex = Integer.parseInt(stuSex);
		
		switch (mySex) {
		case 0:
			student.setStuSex("��");
			break;
		case 1:
			student.setStuSex("Ů");
			break;
		default:
			System.out.println("�Ա����");
			break;
		}
		
		//���뽫�������ַ���ת��Ϊָ����ʽ��java.util.Date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date myBirthday = null;
		try {
			myBirthday = sdf.parse(stuBirthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ת��������ڣ�"+myBirthday.toLocaleString());
		student.setStuBirthday(myBirthday);
		
		System.out.println("����װ��ѧ�������"+student.toString());
		IStudentDAO dao = new StudentDaoImpl();
		int count= dao.insertStudent(student);
		
		PrintWriter out = response.getWriter();
		if (count > 0) {
			//�����ɹ�
			response.sendRedirect("stuall.jsp");//��ת��ȫ����ѯҳ����ʾѧ����Ϣ
		} else {
		   out.println("����ʧ�ܣ�<a href='stuall.jsp'>ѧ����Ϣ�б�</a>");

		}
		
		out.flush();
		out.close();
		
	}
	
	public void studentUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet������޸�ѧ����Ϣ");
		
						//��stuupdate.jsp�޸ı�ȡ��ѧ����ÿһ��ֵ
						String stuNo = request.getParameter("stuId");
						String stuName =request.getParameter("stuName");
						String stuAge =request.getParameter("stuAge");
						//  <input type="radio" name="stuSex" value="0" checked="checked"/>��
						String stuSex =request.getParameter("stuSex");
						System.out.println("�������Ա�"+stuSex);
						String stuBirthday =request.getParameter("stuBirthday");
						System.out.println("�����ݵ�String�������ڣ�"+stuBirthday);
						
						TblStuinfo student = new TblStuinfo();
						student.setStuId(Integer.parseInt(stuNo));
						student.setStuName(stuName);
						student.setStuAge(Integer.parseInt(stuAge));
						
						int mySex = Integer.parseInt(stuSex);
						switch (mySex) {
						case 0:
							student.setStuSex("��");
							break;
						case 1:
							student.setStuSex("Ů");
							break;
						default:
							System.out.println("�Ա����");
							break;
						}
						
						//���뽫�������ַ���ת��Ϊָ����ʽ��java.util.Date
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						Date myBirthday = null;
						try {
							myBirthday = sdf.parse(stuBirthday);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("ת��������ڣ�"+myBirthday.toLocaleString());
						student.setStuBirthday(myBirthday);
						//MyBeanUtils.populate(student,Students.class)
						System.out.println("����װ�Ľ����"+student.toString());
						IStudentDAO dao = new StudentDaoImpl();
						int count= dao.updateStudent(student);
						
						PrintWriter out = response.getWriter();
						if (count > 0) {
							//�޸ĳɹ�
							this.stuAll(request, response);//ȫ����ѯ
						} else {
						   out.println("�޸�ʧ�ܣ�<a href='a'>ѧ����Ϣ�б�</a>");

						}
						
						out.flush();
						out.close();
	}
	
	public void studentQuerySingle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		System.out.println("StudentControlServlet����Ų�ѯѧ����Ϣ");
		IStudentDAO dao = new StudentDaoImpl();
		
		String studentNo = request.getParameter("id");
		System.out.println("stuall.jsp����ѧ����ţ�"+studentNo);
		TblStuinfo stu = dao.selectStudentById(Integer.parseInt(studentNo));
		System.out.println("���޸ĵ�ѧ����"+stu.toString());
		
		//���ݿ��в�ѯѧ���У��Ա��е�ֵ���л���Ů��Ϊ���޸ı�����Ĭ��
		//���е�ֵת��Ϊ0����1��
		String mySex1 = stu.getStuSex();
		String sex = "";
		System.out.println("�ж���Ů"+("��".equals(mySex1)));
		if ("��".equals(mySex1)) {
			System.out.println("�ж���");
			sex = "0";
		} else if("Ů".equals(mySex1)){
			System.out.println("�ж�Ů");
			sex = "1";
		}
		System.out.println("mySex��"+sex);
		stu.setStuSex(sex);
		
		/**
		 * if(stu != null)->��ѯ�ɹ���ŻỰ��Χ
		 * else out.println(��ѯʧ��)
		 */
		System.out.println("����ѧ����"+stu.toString());
		
		HttpSession session = request.getSession();//�򿪻Ự
		session.setAttribute("myStu", stu);//�Ự��Ŵ��޸ĵ�ѧ��
		
		response.sendRedirect("stuupdate.jsp");//��ת��ѧ���޸ĵı�����
	}	
	
}
