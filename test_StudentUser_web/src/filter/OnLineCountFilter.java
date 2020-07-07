package filter;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import users.entity.Users;
import users.entity.UsersList;

public class OnLineCountFilter implements HttpSessionAttributeListener {

	public OnLineCountFilter() {
		System.out.println("�����û�ͳ�Ƽ�����ʵ����");
	}
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("�Ự��Χ�ڴ�������attributeAdded");
		
		String key = event.getName();
		System.out.println("�Ự��Χ��ŵļ���"+key);
		
		if ("success".equals(key)) {
			System.out.println("����");
			Users loginUser=(Users)event.getValue();
			//��¼���û�
			UsersList.addUser(loginUser.getUserName());//��¼�û������뼯��
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("�Ự��Χ��ɾ������attributeAdded");
		
		String key = event.getName();
		System.out.println("�Ự��Χ��ŵļ���"+key);
		
		if ("success".equals(key)) {
			
			Users loginUser=(Users)event.getValue();
			System.out.println("׼��ɾ����ǰ�Ự��¼���û���"+loginUser.toString());
			UsersList.removeUser(loginUser.getUserName());//��¼�û������뼯��
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("�Ự��Χ��ɾ������attributeAdded");
		
		String key = event.getName();
		System.out.println("�Ự��Χ��ŵļ���"+key);
		
		if ("success".equals(key)) {
			
			Users loginUser=(Users)event.getSession().getAttribute(key);
			System.out.println("��ǰ�Ự�ڶ��ε�¼���û���"+loginUser.toString());
			UsersList.addUser(loginUser.getUserName());//��¼�û������뼯��
		}
	}

}
