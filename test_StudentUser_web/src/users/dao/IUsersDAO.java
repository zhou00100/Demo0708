package users.dao;

import java.util.List;

import users.entity.Users;

public interface IUsersDAO {

	//����Ų�ѯ�û�
	public abstract Users selectUserById(Users users);

	//������޸��û�
	public abstract boolean updateUsersById(Users users);

	//�����ɾ��
	public abstract boolean deleteUsersById(Users users);

	//���û����������ѯ
	public abstract Users selectUserByNameAndPassword(Users users);

	//��ѯ����
	public abstract List<Users> selectAllUsers();

	//�����û�
	public abstract boolean insertUsers(Users users);

}