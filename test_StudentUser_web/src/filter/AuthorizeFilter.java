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
	//集合中存放解析web.xml中所有需要放开的url请求路径
	List<String> unchecks=new ArrayList<String>();
	
	public boolean checkURLs(String url) {
		boolean isCheck = false;
		if (this.unchecks.contains(url)) {
			//true->请求的是login.jsp或者LoginServlet的login
			isCheck = true;
		}
		return isCheck;
	}
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 第一步：取出浏览器发出请求的路径
				//class HttpServletRequest extends ServletRequest
				HttpServletRequest httpRequest = (HttpServletRequest) request;
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				String path = httpRequest.getServletPath();
				System.out.println("浏览器发出请求的路径："+path);
				
				//第二步：自会话范围内获取登录用户对象
				HttpSession session=httpRequest.getSession();
				Users loginUser=(Users)session.getAttribute("success");
				
				//第三步：判断路径是否属于登录路径或者会话中是否存在登录用户
				//任意一个成立，放开目标资源（进入JSP/Servlet）
				if (loginUser != null || checkURLs(path)) {
					chain.doFilter(request, response);
				} else {
					//第四步：否则跳转到登录页面login.jsp
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}

	}

	public void init(FilterConfig config) throws ServletException {
		String paramValue= config.getInitParameter("uncheckurls");
		String [] allValue= paramValue.split(";");
		for (String url : allValue) {
			System.out.println("遍历每一个放开的url路径："+url);
			this.unchecks.add(url);
		}

	}

}
