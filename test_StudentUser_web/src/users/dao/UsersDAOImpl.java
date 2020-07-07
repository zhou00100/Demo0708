package users.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import student.util.DBUtil;
import users.entity.Users;

public class UsersDAOImpl implements IUsersDAO {
	//第一步：实例化JdbcTemplate对象
		JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
		//按编号查询用户
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#selectUserById(users.entity.Users)
		 */
		public Users selectUserById(Users users) {
			Users user = null;
			String sql = "select * from users where userid=?";//按编号查询用户
			//this.jdbcTemplate.query(sql, args, rowMapper);//query(查询SQL，？所在参数值， 表和实体类映射)
			List<Users> resUsers = this.jdbcTemplate.query(sql, new Object[]{users.getUserId()} ,new RowMapper(){
				//mapRow(ResultSet rs->查询结束后返回的结果集, int arg1)
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getInt("userid"));//查询用户编号列值赋值给用户编号成员属性
					users.setUserName(rs.getString("username"));
					users.setUserPassword(rs.getString("userpassword"));
					return users;
				}
			});//JdbcTemplate.query(查询的sql,RowMapper映射实体类和数据库表);
			if (resUsers.size() > 0) {
				//按编号查询到了用户：
				user = resUsers.get(0);
			}
			System.out.println("根据用户编号查询到的用户是："+user.toString());
			return user;
		}
		//按编号修改用户
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#updateUsersById(users.entity.Users)
		 */
		public boolean updateUsersById(Users users) {
			boolean isUpdate = false;//代表修改操做成功或者失败
			 //第二步：使用jdbcTemplate.execute/update/query/queryForObject执行新增、修改、删除和查询
			String sql = "update users set username=?,userpassword=? where userid=? ";
			 //update()方法返回的是新增、修改和删除记录的总数！！！！
			int rowCount =  this.jdbcTemplate.update
			 (sql, new Object[]{users.getUserName(),users.getUserPassword(), users.getUserId()});//jdbcTemplate.update(执行新增、修改和删除的SQL,Object[]->?预置参数的值)
			
			if (rowCount > 0) {
				//true：修改成功！
				isUpdate = true;
			}	
			return isUpdate;//返回修改成功还是失败！
		}
		//按编号删除
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#deleteUsersById(users.entity.Users)
		 */
		public boolean deleteUsersById(Users users) {
			boolean isDelete = false;//代表删除操做成功或者失败
			 //第二步：使用jdbcTemplate.execute/update/query/queryForObject执行新增、修改、删除和查询
			String sql = "delete from users where userid=?";//按编号删除记录
			 //update()方法返回的是新增、修改和删除记录的总数！！！！
			int rowCount =  this.jdbcTemplate.update
			 (sql, new Object[]{users.getUserId()});//jdbcTemplate.update(执行新增、修改和删除的SQL,Object[]->?预置参数的值)
			
			if (rowCount > 0) {
				//true：删除成功！
				isDelete = true;
			}	
			return isDelete;//返回删除成功还是失败！
		}
		//按用户名和密码查询
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#selectUserByNameAndPassword(users.entity.Users)
		 */
		public Users selectUserByNameAndPassword(Users users) {
			Users user = null;
			String sql = "select * from users where username=? and userpassword=?";//查询用户表的所有列
			//this.jdbcTemplate.query(sql, args, rowMapper);//query(查询SQL，？所在参数值， 表和实体类映射)
			List<Users> resUsers = this.jdbcTemplate.query(sql, new Object[]{users.getUserName(), users.getUserPassword()} ,new RowMapper(){
				//mapRow(ResultSet rs->查询结束后返回的结果集, int arg1)
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getInt("userid"));//查询用户编号列值赋值给用户编号成员属性
					users.setUserName(rs.getString("username"));
					users.setUserPassword(rs.getString("userpassword"));
					return users;
				}
			});//JdbcTemplate.query(查询的sql,RowMapper映射实体类和数据库表);
			if (resUsers.size() > 0) {
				//按用户名和密码查询到了用户：
				user = resUsers.get(0);
			}
			System.out.println("根据用户名和密码查询到的用户是："+user.toString());
			return user;
		}
		//查询所有
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#selectAllUsers()
		 */
		public List<Users> selectAllUsers() {
			List<Users> allUsers = null;
			String sql = "select * from users";//查询用户表的所有列
			allUsers = this.jdbcTemplate.query(sql, new RowMapper(){
				//mapRow(ResultSet rs->查询结束后返回的结果集, int arg1)
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getInt("userid"));//查询用户编号列值赋值给用户编号成员属性
					users.setUserName(rs.getString("username"));
					users.setUserPassword(rs.getString("userpassword"));
					return users;
				}
			});
			return allUsers;
		}
		//新增用户
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#insertUsers(users.entity.Users)
		 */
		public boolean  insertUsers(Users users) {
			 boolean isInsert = false;//代表新增操做成功或者失败
			 //第二步：使用jdbcTemplate.execute/update/query/queryForObject执行新增、修改、删除和查询
			 String sql = "insert into users(username,userpassword) values(?,?)";
			 //update()方法返回的是新增、修改和删除记录的总数！！！！
			int rowCount =  this.jdbcTemplate.update
			 (sql, new Object[]{users.getUserName(),users.getUserPassword()});//jdbcTemplate.update(执行新增、修改和删除的SQL,Object[]->?预置参数的值)
			
			if (rowCount > 0) {
				//true：新增成功！
				isInsert = true;
			}	
			return isInsert;//返回新增成功还是失败！
		}
}
