package filter;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import users.entity.Users;
import users.entity.UsersList;

public class OnLineCountFilter implements HttpSessionAttributeListener {

	public OnLineCountFilter() {
		System.out.println("线上用户统计监听器实例化");
	}
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("会话范围内存入数据attributeAdded");
		
		String key = event.getName();
		System.out.println("会话范围存放的键："+key);
		
		if ("success".equals(key)) {
			System.out.println("新增");
			Users loginUser=(Users)event.getValue();
			//登录的用户
			UsersList.addUser(loginUser.getUserName());//登录用户名放入集合
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("会话范围内删除数据attributeAdded");
		
		String key = event.getName();
		System.out.println("会话范围存放的键："+key);
		
		if ("success".equals(key)) {
			
			Users loginUser=(Users)event.getValue();
			System.out.println("准备删除当前会话登录的用户是"+loginUser.toString());
			UsersList.removeUser(loginUser.getUserName());//登录用户名放入集合
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("会话范围内删除数据attributeAdded");
		
		String key = event.getName();
		System.out.println("会话范围存放的键："+key);
		
		if ("success".equals(key)) {
			
			Users loginUser=(Users)event.getSession().getAttribute(key);
			System.out.println("当前会话第二次登录的用户是"+loginUser.toString());
			UsersList.addUser(loginUser.getUserName());//登录用户名放入集合
		}
	}

}
