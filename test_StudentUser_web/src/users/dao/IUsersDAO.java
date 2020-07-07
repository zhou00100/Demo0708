package users.dao;

import java.util.List;

import users.entity.Users;

public interface IUsersDAO {

	//按编号查询用户
	public abstract Users selectUserById(Users users);

	//按编号修改用户
	public abstract boolean updateUsersById(Users users);

	//按编号删除
	public abstract boolean deleteUsersById(Users users);

	//按用户名和密码查询
	public abstract Users selectUserByNameAndPassword(Users users);

	//查询所有
	public abstract List<Users> selectAllUsers();

	//新增用户
	public abstract boolean insertUsers(Users users);

}