package filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.HttpRequestHandler;

import users.entity.Users;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class AuthorizeFilter implements Filter {
	//�����д�Ž���web.xml��������Ҫ�ſ���url����·��
	List<String> unchecks=new ArrayList<String>();
	
	public boolean checkURLs(String url) {
		boolean isCheck = false;
		if (this.unchecks.contains(url)) {
			//true->�������login.jsp����LoginServlet��login
			isCheck = true;
		}
		return isCheck;
	}
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// ��һ����ȡ����������������·��
				//class HttpServletRequest extends ServletRequest
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				String path = httpRequest.getServletPath();
				System.out.println("��������������·����"+path);
				
				//�ڶ������ԻỰ��Χ�ڻ�ȡ��¼�û�����
				HttpSession session=httpRequest.getSession();
				Users loginUser=(Users)session.getAttribute("success");
				
				//���������ж�·���Ƿ����ڵ�¼·�����߻Ự���Ƿ���ڵ�¼�û�
				//����һ���������ſ�Ŀ����Դ������JSP/Servlet��
				if (loginUser != null || checkURLs(path)) {
					chain.doFilter(request, response);
				} else {
					//���Ĳ���������ת����¼ҳ��login.jsp
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}

	}

	public void init(FilterConfig config) throws ServletException {
		String paramValue= config.getInitParameter("uncheckurls");
		String [] allValue= paramValue.split(";");
		for (String url : allValue) {
			System.out.println("����ÿһ���ſ���url·����"+url);
			this.unchecks.add(url);
		}

	}

}
