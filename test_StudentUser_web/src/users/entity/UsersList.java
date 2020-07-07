package users.entity;

import java.util.ArrayList;
import java.util.List;

public class UsersList {
	static List<String> users=new ArrayList<String>();

	//返回所有登录用户名
		public static List<String> getUsers() {
			System.out.println("当前登录的用户"+users);
			return users;
		}
		
		// 返回当前在线人数的方法
			public static int getUsersCount() {
				return users.size();
			}
			
			// 向集合中添加用户
			public static void addUser(String userName) {
				users.add(userName);
			}

			// 从集合移除用户
			public static void removeUser(String userName) {
				users.remove(userName);
			}

}
