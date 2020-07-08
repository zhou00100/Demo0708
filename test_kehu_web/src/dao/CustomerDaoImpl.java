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
		int count = 0;//���/ɾ��/�޸ļ�¼����
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
			//����Ų�ѯ��һ��ѧ��
			cus = allCus.get(0);
		}
		System.out.println("��ѯ�����"+cus);
		return cus;
	}
	
	//ѧ����Ϣ����ϲ�ѯ
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#cusAdvanceQuery(util.CustomerDTO)
	 */
	public List<TbCustomer> cusAdvanceQuery(CustomerDTO dto) {
		System.out.println("StudentDaoImpl�߼���ѯ");
		System.out.println("�߼���ѯ���ݵĲ�����"+dto.toString());
		String sql = "select * from tb_customer where 1=1 ";
		
		List params = new ArrayList();//�߼���ѯ�������SQL
			if (dto != null) {
			//true���������˲�������������װ����һ��DTO
			//��ϲ�ͬ��SQLʵ�ֲ�ͬ�����������ѯ
			if (dto.getSearch_cusid() > 0) {
				//true�����д��ݵ�ѧ����ţ���ϰ���Ų�ѯѧ����
				sql += " and cust_id = ?";
				params.add(dto.getSearch_cusid());//��SQL�����ѧ�����
			}
			
			if (dto.getSearch_cusname() != null && !"".equals(dto.getSearch_cusname())) {
				//true�����д��ݵ�ѧ����������ϰ�����ģ����ѯѧ����
				sql += " and cust_name like ?";
				params.add("%"+ dto.getSearch_cusname() +"%");//��SQL�����ģ����ѯѧ������
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
	
	//������ϲ�ѯ���������¼����
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#getTotalCount(util.CustomerDTO)
	 */
	public int getTotalCount(CustomerDTO dto) {
		int count =0;
		String sql="select count(*) from tb_customer where 1=1 ";//count()�ۺϺ��������ѯ��¼����
		List params = new ArrayList();//�߼���ѯ�������SQL
		
		if (dto!=null) {
			//true���������˲�������������װ����һ��DTO
			//��ϲ�ͬ��SQLʵ�ֲ�ͬ�����������ѯ
			if (dto.getSearch_cusid() > 0) {
				//true�����д��ݵ�ѧ����ţ���ϰ���Ų�ѯѧ����
				sql += " and cust_id = ?";
				params.add(dto.getSearch_cusid());//��SQL�����ѧ�����
			}
			
			if (dto.getSearch_cusname() != null && !"".equals(dto.getSearch_cusname())) {
				//true�����д��ݵ�ѧ����������ϰ�����ģ����ѯѧ����
				sql += " and cust_name like ?";
				params.add("%"+ dto.getSearch_cusname() +"%");//��SQL�����ģ����ѯѧ������
			}
			
			
			
			
			
		}
		//ִ��SQL��ѯ
		//queryForInt(��ѯSQL, �������)����ѯ�������������ֵ����ʹ��queryForInt
		count = this.jdbcTemplate.queryForInt(sql, params.toArray());
		System.out.println("��¼������ѯ���صĽ����"+count);
		return count;
	}
	
	/* (non-Javadoc)
	 * @see dao.ICustomerDAO#selectCustomerByPage(int, int, util.CustomerDTO)
	 */
	public List<TbCustomer> selectCustomerByPage(int pageNo, int pageSize, CustomerDTO dto) {
		//��ҳ��ѯSQL��
		//��ҳÿһҳ��ʼλ�ã�
		int start = (pageNo - 1)*pageSize + 1;
		//��ҳÿһҳ����λ�ã�
		int end = pageNo * pageSize;
		String sql = "select * from(select row_number() over(order by cust_id) no, s.* from tb_customer s where 1=1 ";
		
		
		List params = new ArrayList();//�߼���ѯ�������SQL
		
		if (dto != null) {
			//true���������˲�������������װ����һ��DTO
			//��ϲ�ͬ��SQLʵ�ֲ�ͬ�����������ѯ
			if (dto.getSearch_cusid() > 0) {
				//true�����д��ݵ�ѧ����ţ���ϰ���Ų�ѯѧ����
				sql += " and cust_id = ?";
				params.add(dto.getSearch_cusid());//��SQL�����ѧ�����
			}
			
			if (dto.getSearch_cusname() != null && !"".equals(dto.getSearch_cusname())) {
				//true�����д��ݵ�ѧ����������ϰ�����ģ����ѯѧ����
				sql += " and cust_name like ?";
				params.add("%"+ dto.getSearch_cusname() +"%");//��SQL�����ģ����ѯѧ������
			}
			
			
			
			
		}
		
		System.out.println("��ǰ��ѯSQL��"+sql);
		sql += ")temp where temp.no between ? and ?";//û������κ�����Ĭ�Ͼ���ȫ����ѯ�ķ�ҳ
		params.add(start);//between ?-��ÿһҳ��ʼλ��
		params.add(end);//and ?-��ÿһҳ����λ��
		
		System.out.println("��ǰ��ҳ��ѯSQL��"+sql);
		List<TbCustomer> allCus = null;//�����������ִ�з�ҳSQL�󷵻ز�ѯѧ����Ϣ
		
		//ִ��SQL��ѯ
		//query(��ѯSQL, �������, RowMapperӳ���е�ʵ����)
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
		System.out.println("��ҳ�߼���ѯ���ص�ѧ����"+allCus);
		return allCus;//�����������ִ�з�ҳSQL�󷵻ز�ѯѧ����Ϣ
	}
	
	
}
