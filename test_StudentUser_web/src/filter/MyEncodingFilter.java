package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyEncodingFilter implements Filter {
//中文乱码过滤器
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String encode=this.config.getInitParameter("myEncoding");
		// 第一步，编写过滤器类，在过滤请求处理的方法doFilter添加乱码处理
		//request.setCharacterEncoding("utf-8");
		//response.setContentType("text/html;charset=utf-8");
		//response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding(encode);
		response.setContentType("text/html;charset="+encode);
		response.setCharacterEncoding(encode);
		//第二步，请求路径继续传递到目标资源
		chain.doFilter(request, response);//过滤器处理后继续向下进入各自的jsp或servlet
		//第三步，配置web.xml中中文乱码过滤器
		//注意：中文乱码过滤器必须是所有过滤器之前的第一个过滤器
		

	}
	FilterConfig config=null;
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		this.config=config;

	}

}
