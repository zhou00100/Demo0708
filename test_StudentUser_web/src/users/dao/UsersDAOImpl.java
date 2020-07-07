package users.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import student.util.DBUtil;
import users.entity.Users;

public class UsersDAOImpl implements IUsersDAO {
	//��һ����ʵ����JdbcTemplate����
		JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
		//����Ų�ѯ�û�
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#selectUserById(users.entity.Users)
		 */
		public Users selectUserById(Users users) {
			Users user = null;
			String sql = "select * from users where userid=?";//����Ų�ѯ�û�
			//this.jdbcTemplate.query(sql, args, rowMapper);//query(��ѯSQL�������ڲ���ֵ�� ���ʵ����ӳ��)
			List<Users> resUsers = this.jdbcTemplate.query(sql, new Object[]{users.getUserId()} ,new RowMapper(){
				//mapRow(ResultSet rs->��ѯ�����󷵻صĽ����, int arg1)
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getInt("userid"));//��ѯ�û������ֵ��ֵ���û���ų�Ա����
					users.setUserName(rs.getString("username"));
					users.setUserPassword(rs.getString("userpassword"));
					return users;
				}
			});//JdbcTemplate.query(��ѯ��sql,RowMapperӳ��ʵ��������ݿ��);
			if (resUsers.size() > 0) {
				//����Ų�ѯ�����û���
				user = resUsers.get(0);
			}
			System.out.println("�����û���Ų�ѯ�����û��ǣ�"+user.toString());
			return user;
		}
		//������޸��û�
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#updateUsersById(users.entity.Users)
		 */
		public boolean updateUsersById(Users users) {
			boolean isUpdate = false;//�����޸Ĳ����ɹ�����ʧ��
			 //�ڶ�����ʹ��jdbcTemplate.execute/update/query/queryForObjectִ���������޸ġ�ɾ���Ͳ�ѯ
			String sql = "update users set username=?,userpassword=? where userid=? ";
			 //update()�������ص����������޸ĺ�ɾ����¼��������������
			int rowCount =  this.jdbcTemplate.update
			 (sql, new Object[]{users.getUserName(),users.getUserPassword(), users.getUserId()});//jdbcTemplate.update(ִ���������޸ĺ�ɾ����SQL,Object[]->?Ԥ�ò�����ֵ)
			
			if (rowCount > 0) {
				//true���޸ĳɹ���
				isUpdate = true;
			}	
			return isUpdate;//�����޸ĳɹ�����ʧ�ܣ�
		}
		//�����ɾ��
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#deleteUsersById(users.entity.Users)
		 */
		public boolean deleteUsersById(Users users) {
			boolean isDelete = false;//����ɾ�������ɹ�����ʧ��
			 //�ڶ�����ʹ��jdbcTemplate.execute/update/query/queryForObjectִ���������޸ġ�ɾ���Ͳ�ѯ
			String sql = "delete from users where userid=?";//�����ɾ����¼
			 //update()�������ص����������޸ĺ�ɾ����¼��������������
			int rowCount =  this.jdbcTemplate.update
			 (sql, new Object[]{users.getUserId()});//jdbcTemplate.update(ִ���������޸ĺ�ɾ����SQL,Object[]->?Ԥ�ò�����ֵ)
			
			if (rowCount > 0) {
				//true��ɾ���ɹ���
				isDelete = true;
			}	
			return isDelete;//����ɾ���ɹ�����ʧ�ܣ�
		}
		//���û����������ѯ
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#selectUserByNameAndPassword(users.entity.Users)
		 */
		public Users selectUserByNameAndPassword(Users users) {
			Users user = null;
			String sql = "select * from users where username=? and userpassword=?";//��ѯ�û����������
			//this.jdbcTemplate.query(sql, args, rowMapper);//query(��ѯSQL�������ڲ���ֵ�� ���ʵ����ӳ��)
			List<Users> resUsers = this.jdbcTemplate.query(sql, new Object[]{users.getUserName(), users.getUserPassword()} ,new RowMapper(){
				//mapRow(ResultSet rs->��ѯ�����󷵻صĽ����, int arg1)
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getInt("userid"));//��ѯ�û������ֵ��ֵ���û���ų�Ա����
					users.setUserName(rs.getString("username"));
					users.setUserPassword(rs.getString("userpassword"));
					return users;
				}
			});//JdbcTemplate.query(��ѯ��sql,RowMapperӳ��ʵ��������ݿ��);
			if (resUsers.size() > 0) {
				//���û����������ѯ�����û���
				user = resUsers.get(0);
			}
			System.out.println("�����û����������ѯ�����û��ǣ�"+user.toString());
			return user;
		}
		//��ѯ����
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#selectAllUsers()
		 */
		public List<Users> selectAllUsers() {
			List<Users> allUsers = null;
			String sql = "select * from users";//��ѯ�û����������
			allUsers = this.jdbcTemplate.query(sql, new RowMapper(){
				//mapRow(ResultSet rs->��ѯ�����󷵻صĽ����, int arg1)
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getInt("userid"));//��ѯ�û������ֵ��ֵ���û���ų�Ա����
					users.setUserName(rs.getString("username"));
					users.setUserPassword(rs.getString("userpassword"));
					return users;
				}
			});
			return allUsers;
		}
		//�����û�
		/* (non-Javadoc)
		 * @see users.dao.IUsersDAO#insertUsers(users.entity.Users)
		 */
		public boolean  insertUsers(Users users) {
			 boolean isInsert = false;//�������������ɹ�����ʧ��
			 //�ڶ�����ʹ��jdbcTemplate.execute/update/query/queryForObjectִ���������޸ġ�ɾ���Ͳ�ѯ
			 String sql = "insert into users(username,userpassword) values(?,?)";
			 //update()�������ص����������޸ĺ�ɾ����¼��������������
			int rowCount =  this.jdbcTemplate.update
			 (sql, new Object[]{users.getUserName(),users.getUserPassword()});//jdbcTemplate.update(ִ���������޸ĺ�ɾ����SQL,Object[]->?Ԥ�ò�����ֵ)
			
			if (rowCount > 0) {
				//true�������ɹ���
				isInsert = true;
			}	
			return isInsert;//���������ɹ�����ʧ�ܣ�
		}
}
