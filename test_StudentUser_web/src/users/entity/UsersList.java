package users.entity;

import java.util.ArrayList;
import java.util.List;

public class UsersList {
	static List<String> users=new ArrayList<String>();

	//�������е�¼�û���
		public static List<String> getUsers() {
			System.out.println("��ǰ��¼���û�"+users);
			return users;
		}
		
		// ���ص�ǰ���������ķ���
			public static int getUsersCount() {
				return users.size();
			}
			
			// �򼯺�������û�
			public static void addUser(String userName) {
				users.add(userName);
			}

			// �Ӽ����Ƴ��û�
			public static void removeUser(String userName) {
				users.remove(userName);
			}

}
