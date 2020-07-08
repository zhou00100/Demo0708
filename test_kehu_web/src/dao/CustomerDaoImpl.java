package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import bean.TbCustomer;

import util.CustomerDTO;
import util.DBUtil;

public class CustomerDaoImpl implements ICustomerDAO {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#insertCustomer(bean.TbCustomer)
	 */
	public int insertCustomer(TbCustomer customer) {
		String sql = "insert into tb_customer" +
				"(cust_name,phone,mobile,email,fax,address) values(?,?,?,?,?,?)";
		int count = 0;
		count = this.jdbcTemplate.update(sql, new Object[]{
				customer.getCustName(),
				customer.getPhone(),
				customer.getMobile(),
				customer.getEmail(),
				customer.getFax(),
				customer.getAddress()
		});
		return count;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#updateStudent(student.entity.TblStuinfo)
	 */
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#updateCustomer(bean.TbCustomer)
	 */
	public int updateCustomer(TbCustomer customer) {
		String sql = "update tb_customer set cust_name=?,phone=?,mobile=?,email=?,fax=?,address=? where cust_id=?";
		int count = 0;//添加/删除/修改记录总数
		count = this.jdbcTemplate.update(sql, new Object[]{
				customer.getCustName(),
				customer.getPhone(),
				customer.getMobile(),
				customer.getEmail(),
				customer.getFax(),
				customer.getAddress(),
				customer.getCustId()
			});
			return count;
	}
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#deleteStudent(int)
	 */
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#deleteCustomer(int)
	 */
	public int deleteCustomer(int customerNo) {
		String sql = "delete from tb_customer where cust_id=?";
		
		int count = 0;
		count = this.jdbcTemplate.update(sql, new Object[]{customerNo});
		return count;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectAllStudent()
	 */
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#selectAllCustomer()
	 */
	public List<TbCustomer> selectAllCustomer() {
		String sql = "select * from tb_customer";
		List<TbCustomer> allCus =this.jdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TbCustomer cus = new TbCustomer();
				cus.setCustId(rs.getInt("cust_id"));
				cus.setCustName(rs.getString("cust_name"));
				cus.setPhone(rs.getString("phone"));
				cus.setMobile(rs.getString("mobile"));
				cus.setEmail(rs.getString("email"));
				cus.setFax(rs.getString("fax"));
				cus.setAddress(rs.getString("address"));
				return cus;
			}
		});
		
		return allCus;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectStudentById(int)
	 */
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#selectCustomerById(int)
	 */
	public TbCustomer selectCustomerById(int customerNo) {
		String sql = "select * from tb_customer where cust_id=?";
		TbCustomer cus = null;
		
		List<TbCustomer> allCus =this.jdbcTemplate.query(sql,new Object[]{customerNo} ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TbCustomer cus = new TbCustomer();
				cus.setCustId(rs.getInt("cust_id"));
				cus.setCustName(rs.getString("cust_name"));
				cus.setPhone(rs.getString("phone"));
				cus.setMobile(rs.getString("mobile"));
				cus.setEmail(rs.getString("email"));
				cus.setFax(rs.getString("fax"));
				cus.setAddress(rs.getString("address"));
				return cus;
			}
		});
		if (allCus.size() > 0) {
			//按编号查询了一个学生
			cus = allCus.get(0);
		}
		System.out.println("查询结果："+cus);
		return cus;
	}
	
	//学生信息的组合查询
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#cusAdvanceQuery(util.CustomerDTO)
	 */
	public List<TbCustomer> cusAdvanceQuery(CustomerDTO dto) {
		System.out.println("StudentDaoImpl高级查询");
		System.out.println("高级查询传递的参数："+dto.toString());
		String sql = "select * from tb_customer where 1=1 ";
		
		List params = new ArrayList();//高级查询组合条件SQL
			if (dto != null) {
			//true：表单传递了参数，参数被封装成了一个DTO
			//组合不同的SQL实现不同情况的条件查询
			if (dto.getSearch_cusid() > 0) {
				//true：表单中传递的学生编号（组合按编号查询学生）
				sql += " and cust_id = ?";
				params.add(dto.getSearch_cusid());//在SQL中组合学生编号
			}
			
			if (dto.getSearch_cusname() != null && !"".equals(dto.getSearch_cusname())) {
				//true：表单中传递的学生姓名（组合按姓名模糊查询学生）
				sql += " and cust_name like ?";
				params.add("%"+ dto.getSearch_cusname() +"%");//在SQL中组合模糊查询学生名字
			}
			
			
			
			
		}
		List<TbCustomer> allCus=null;
		
		allCus = this.jdbcTemplate.query(sql, params.toArray() ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TbCustomer cus = new TbCustomer();
				cus.setCustId(rs.getInt("cust_id"));
				cus.setCustName(rs.getString("cust_name"));
				cus.setPhone(rs.getString("phone"));
				cus.setMobile(rs.getString("mobile"));
				cus.setEmail(rs.getString("email"));
				cus.setFax(rs.getString("fax"));
				cus.setAddress(rs.getString("address"));
				return cus;
			}
		});
		return allCus;
	}
	
	//根据组合查询条件计算记录总数
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#getTotalCount(util.CustomerDTO)
	 */
	public int getTotalCount(CustomerDTO dto) {
		int count =0;
		String sql="select count(*) from tb_customer where 1=1 ";//count()聚合函数计算查询记录总数
		List params = new ArrayList();//高级查询组合条件SQL
		
		if (dto!=null) {
			//true：表单传递了参数，参数被封装成了一个DTO
			//组合不同的SQL实现不同情况的条件查询
			if (dto.getSearch_cusid() > 0) {
				//true：表单中传递的学生编号（组合按编号查询学生）
				sql += " and cust_id = ?";
				params.add(dto.getSearch_cusid());//在SQL中组合学生编号
			}
			
			if (dto.getSearch_cusname() != null && !"".equals(dto.getSearch_cusname())) {
				//true：表单中传递的学生姓名（组合按姓名模糊查询学生）
				sql += " and cust_name like ?";
				params.add("%"+ dto.getSearch_cusname() +"%");//在SQL中组合模糊查询学生名字
			}
			
			
			
			
			
		}
		//执行SQL查询
		//queryForInt(查询SQL, 组合条件)：查询结果仅返回整数值可以使用queryForInt
		count = this.jdbcTemplate.queryForInt(sql, params.toArray());
		System.out.println("记录总数查询返回的结果："+count);
		return count;
	}
	
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#selectCustomerByPage(int, int, util.CustomerDTO)
	 */
	public List<TbCustomer> selectCustomerByPage(int pageNo, int pageSize, CustomerDTO dto) {
		//分页查询SQL：
		//分页每一页开始位置：
		int start = (pageNo - 1)*pageSize + 1;
		//分页每一页结束位置：
		int end = pageNo * pageSize;
		String sql = "select * from(select row_number() over(order by cust_id) no, s.* from tb_customer s where 1=1 ";
		
		
		List params = new ArrayList();//高级查询组合条件SQL
		
		if (dto != null) {
			//true：表单传递了参数，参数被封装成了一个DTO
			//组合不同的SQL实现不同情况的条件查询
			if (dto.getSearch_cusid() > 0) {
				//true：表单中传递的学生编号（组合按编号查询学生）
				sql += " and cust_id = ?";
				params.add(dto.getSearch_cusid());//在SQL中组合学生编号
			}
			
			if (dto.getSearch_cusname() != null && !"".equals(dto.getSearch_cusname())) {
				//true：表单中传递的学生姓名（组合按姓名模糊查询学生）
				sql += " and cust_name like ?";
				params.add("%"+ dto.getSearch_cusname() +"%");//在SQL中组合模糊查询学生名字
			}
			
			
			
			
		}
		
		System.out.println("当前查询SQL："+sql);
		sql += ")temp where temp.no between ? and ?";//没有组合任何条件默认就是全部查询的分页
		params.add(start);//between ?-〉每一页开始位置
		params.add(end);//and ?-〉每一页结束位置
		
		System.out.println("当前分页查询SQL："+sql);
		List<TbCustomer> allCus = null;//根据组合条件执行分页SQL后返回查询学生信息
		
		//执行SQL查询
		//query(查询SQL, 组合条件, RowMapper映射列到实体类)
		allCus = this.jdbcTemplate.query(sql, params.toArray() ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TbCustomer cus = new TbCustomer();
				cus.setCustId(rs.getInt("cust_id"));
				cus.setCustName(rs.getString("cust_name"));
				cus.setPhone(rs.getString("phone"));
				cus.setMobile(rs.getString("mobile"));
				cus.setEmail(rs.getString("email"));
				cus.setFax(rs.getString("fax"));
				cus.setAddress(rs.getString("address"));
				return cus;
			}
		});
		System.out.println("分页高级查询返回的学生："+allCus);
		return allCus;//根据组合条件执行分页SQL后返回查询学生信息
	}
	
	
}
