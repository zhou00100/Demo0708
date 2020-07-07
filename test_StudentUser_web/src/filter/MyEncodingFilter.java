package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyEncodingFilter implements Filter {
//�������������
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String encode=this.config.getInitParameter("myEncoding");
		// ��һ������д�������࣬�ڹ���������ķ���doFilter������봦��
		//request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
		//response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding(encode);
		response.setContentType("text/html;charset="+encode);
		response.setCharacterEncoding(encode);
		//�ڶ���������·���������ݵ�Ŀ����Դ
		chain.doFilter(request, response);//�����������������½�����Ե�jsp��servlet
		//������������web.xml���������������
		//ע�⣺����������������������й�����֮ǰ�ĵ�һ��������
		

	}
	FilterConfig config=null;
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.config=config;

	}

}
