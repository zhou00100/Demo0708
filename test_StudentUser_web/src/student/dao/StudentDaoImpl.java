package student.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import student.entity.TblStuinfo;
import student.util.DBUtil;
import student.util.StudentDTO;

public class StudentDaoImpl implements IStudentDAO {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(DBUtil.getDataSource());
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#insertStudent(student.entity.TblStuinfo)
	 */
	public int insertStudent(TblStuinfo student) {
		String sql = "insert into tbl_stuinfo" +
				"(stu_name,stu_sex,stu_birthday,stu_age) values(?,?,?,?)";
		int count = 0;
		count = this.jdbcTemplate.update(sql, new Object[]{
				student.getStuName(),
				student.getStuSex(),
				new Timestamp(student.getStuBirthday().getTime()),//Oracle���ڱ���TimeStamp
				student.getStuAge()
		});
		return count;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#updateStudent(student.entity.TblStuinfo)
	 */
	public int updateStudent(TblStuinfo student) {
		System.out.println("StudentDaoImpl�޸�ѧ��"+student.toString());
		String sql = "update tbl_stuinfo set stu_name=?,stu_sex=?,stu_birthday=?,stu_age=? where stu_id=?";
		int count = 0;//���/ɾ��/�޸ļ�¼����
		count = this.jdbcTemplate.update(sql, new Object[]{
					student.getStuName(),
					student.getStuSex(),
					new Timestamp(student.getStuBirthday().getTime()),
					student.getStuAge(),
					student.getStuId()
			});
			return count;
	}
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#deleteStudent(int)
	 */
	public int deleteStudent(int studentNo) {
		System.out.println("StudentDaoImplɾ��ѧ����"+ studentNo);
		String sql = "delete from tbl_stuinfo where stu_id=?";
		
		int count = 0;
		count = this.jdbcTemplate.update(sql, new Object[]{studentNo});
		return count;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectAllStudent()
	 */
	public List<TblStuinfo> selectAllStudent() {
		System.out.println("StudentDaoImpl��selectAllStudent-��ȫ����ѯѧ��");
		String sql = "select * from tbl_stuinfo";
		List<TblStuinfo> allStu =this.jdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TblStuinfo stu = new TblStuinfo();
				stu.setStuId(rs.getInt("stu_id"));
				stu.setStuName(rs.getString("stu_name"));
				stu.setStuSex(rs.getString("stu_sex"));
				stu.setStuBirthday(rs.getTimestamp("stu_birthday"));
				stu.setStuAge(rs.getInt("stu_age"));
				System.out.println("��¼ת��ѧ������"+stu.toString());
				return stu;
			}
		});
		System.out.println("ִ�н�����"+allStu.toString());
		return allStu;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectStudentById(int)
	 */
	public TblStuinfo selectStudentById(int studentNo) {
		System.out.println("StudentDaoImpl����Ų�ѯѧ����"+studentNo);
		String sql = "select * from tbl_stuinfo where stu_id=?";
		System.out.println("selectStudentById");
		TblStuinfo stu = null;
		
		List<TblStuinfo> allStu =this.jdbcTemplate.query(sql,new Object[]{studentNo} ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TblStuinfo stu = new TblStuinfo();
				stu.setStuId(rs.getInt("stu_id"));
				stu.setStuName(rs.getString("stu_name"));
				stu.setStuSex(rs.getString("stu_sex"));
				stu.setStuBirthday(rs.getTimestamp("stu_birthday"));
				stu.setStuAge(rs.getInt("stu_age"));
				System.out.println("��¼ת��ѧ������"+stu.toString());
				return stu;
			}
		});
		if (allStu.size() > 0) {
			//����Ų�ѯ��һ��ѧ��
			stu = allStu.get(0);
		}
		System.out.println("��ѯ�����"+stu);
		return stu;
	}
	
	//ѧ����Ϣ����ϲ�ѯ
	public List<TblStuinfo> stuAdvanceQuery(StudentDTO dto) {
		System.out.println("StudentDaoImpl�߼���ѯ");
		System.out.println("�߼���ѯ���ݵĲ�����"+dto.toString());
		String sql = "select * from tbl_stuinfo where 1=1 ";
		
		List params = new ArrayList();//�߼���ѯ�������SQL
			if (dto != null) {
			//true���������˲�������������װ����һ��DTO
			//��ϲ�ͬ��SQLʵ�ֲ�ͬ�����������ѯ
			if (dto.getSearch_stuid() > 0) {
				//true�����д��ݵ�ѧ����ţ���ϰ���Ų�ѯѧ����
				sql += " and stu_id = ?";
				params.add(dto.getSearch_stuid());//��SQL�����ѧ�����
			}
			
			if (dto.getSearch_stuname() != null && !"".equals(dto.getSearch_stuname())) {
				//true�����д��ݵ�ѧ����������ϰ�����ģ����ѯѧ����
				sql += " and stu_name like ?";
				params.add("%"+ dto.getSearch_stuname() +"%");//��SQL�����ģ����ѯѧ������
			}
			//���ѧ����������
			if (dto.getSearch_stuage1() >= 0 && dto.getSearch_stuage2() > 0 
					&& dto.getSearch_stuage1()< dto.getSearch_stuage2()) {
				sql += " and stu_age between ? and ? ";
				params.add(dto.getSearch_stuage1());
				params.add(dto.getSearch_stuage2());
			}
			
			//���ѧ����������
			String temp1 = dto.getSearch_birthdayDate1();
			String temp2 = dto.getSearch_birthdayDate2();
			if ((temp1 != null && !"".equals(temp1))
					&& (temp2 != null && !"".equals(temp2))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date payDate1 = sdf.parse(temp1);
					Date payDate2 = sdf.parse(temp2);
					if (payDate1.before(payDate2)) {
						sql += " and stu_birthday between ? and ? ";
						params.add(payDate1);
						params.add(payDate2);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		List<TblStuinfo> allStus=null;
		
		allStus = this.jdbcTemplate.query(sql, params.toArray() ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TblStuinfo stu = new TblStuinfo();
				stu.setStuId(rs.getInt("stu_id"));
				stu.setStuName(rs.getString("stu_name"));
				stu.setStuSex(rs.getString("stu_sex"));
				stu.setStuBirthday(rs.getTimestamp("stu_birthday"));
				stu.setStuAge(rs.getInt("stu_age"));
				System.out.println("��¼ת��ѧ������"+stu.toString());
				return stu;
			}
		});
		return allStus;
	}
	
	//������ϲ�ѯ���������¼����
	public int getTotalCount(StudentDTO dto) {
		int count =0;
		String sql="select count(*) from tbl_stuinfo where 1=1 ";//count()�ۺϺ��������ѯ��¼����
		List params = new ArrayList();//�߼���ѯ�������SQL
		
		if (dto!=null) {
			//true���������˲�������������װ����һ��DTO
			//��ϲ�ͬ��SQLʵ�ֲ�ͬ�����������ѯ
			if (dto.getSearch_stuid() > 0) {
				//true�����д��ݵ�ѧ����ţ���ϰ���Ų�ѯѧ����
				sql += " and stu_id = ?";
				params.add(dto.getSearch_stuid());//��SQL�����ѧ�����
			}
			
			if (dto.getSearch_stuname() != null && !"".equals(dto.getSearch_stuname())) {
				//true�����д��ݵ�ѧ����������ϰ�����ģ����ѯѧ����
				sql += " and stu_name like ?";
				params.add("%"+ dto.getSearch_stuname() +"%");//��SQL�����ģ����ѯѧ������
			}
			
			//���ѧ����������
			if (dto.getSearch_stuage1() >= 0 && dto.getSearch_stuage2() > 0 
					&& dto.getSearch_stuage1()< dto.getSearch_stuage2()) {
				sql += " and stu_age between ? and ? ";
				params.add(dto.getSearch_stuage1());
				params.add(dto.getSearch_stuage2());
			}
			
			//���ѧ����������
			String temp1 = dto.getSearch_birthdayDate1();
			String temp2 = dto.getSearch_birthdayDate2();
			if ((temp1 != null && !"".equals(temp1))
					&& (temp2 != null && !"".equals(temp2))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date payDate1 = sdf.parse(temp1);
					Date payDate2 = sdf.parse(temp2);
					if (payDate1.before(payDate2)) {
						sql += " and stu_birthday between ? and ? ";
						params.add(payDate1);
						params.add(payDate2);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
		}
		//ִ��SQL��ѯ
		//queryForInt(��ѯSQL, �������)����ѯ�������������ֵ����ʹ��queryForInt
		count = this.jdbcTemplate.queryForInt(sql, params.toArray());
		System.out.println("��¼������ѯ���صĽ����"+count);
		return count;
	}
	
	public List<TblStuinfo> selectStudentsByPage(int pageNo, int pageSize, StudentDTO dto) {
		//��ҳ��ѯSQL��
		//��ҳÿһҳ��ʼλ�ã�
		int start = (pageNo - 1)*pageSize + 1;
		//��ҳÿһҳ����λ�ã�
		int end = pageNo * pageSize;
		String sql = "select * from(select row_number() over(order by stu_id) no, s.* from tbl_stuinfo s where 1=1 ";
		
		
		List params = new ArrayList();//�߼���ѯ�������SQL
		
		if (dto != null) {
			//true���������˲�������������װ����һ��DTO
			//��ϲ�ͬ��SQLʵ�ֲ�ͬ�����������ѯ
			if (dto.getSearch_stuid() > 0) {
				//true�����д��ݵ�ѧ����ţ���ϰ���Ų�ѯѧ����
				sql += " and stu_id = ?";
				params.add(dto.getSearch_stuid());//��SQL�����ѧ�����
			}
			
			if (dto.getSearch_stuname() != null && !"".equals(dto.getSearch_stuname())) {
				//true�����д��ݵ�ѧ����������ϰ�����ģ����ѯѧ����
				sql += " and stu_name like ?";
				params.add("%"+ dto.getSearch_stuname() +"%");//��SQL�����ģ����ѯѧ������
			}
			
			//���ѧ����������
			if (dto.getSearch_stuage1() >= 0 && dto.getSearch_stuage2() > 0 
					&& dto.getSearch_stuage1()< dto.getSearch_stuage2()) {
				sql += " and stu_age between ? and ? ";
				params.add(dto.getSearch_stuage1());
				params.add(dto.getSearch_stuage2());
			}
			
			//���ѧ����������
			String temp1 = dto.getSearch_birthdayDate1();
			String temp2 = dto.getSearch_birthdayDate2();
			if ((temp1 != null && !"".equals(temp1))
					&& (temp2 != null && !"".equals(temp2))) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date payDate1 = sdf.parse(temp1);
					Date payDate2 = sdf.parse(temp2);
					if (payDate1.before(payDate2)) {
						sql += " and stu_birthday between ? and ? ";
						params.add(payDate1);
						params.add(payDate2);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("��ǰ��ѯSQL��"+sql);
		sql += ")temp where temp.no between ? and ?";//û������κ�����Ĭ�Ͼ���ȫ����ѯ�ķ�ҳ
		params.add(start);//between ?-��ÿһҳ��ʼλ��
		params.add(end);//and ?-��ÿһҳ����λ��
		
		System.out.println("��ǰ��ҳ��ѯSQL��"+sql);
		List<TblStuinfo> allStus = null;//�����������ִ�з�ҳSQL�󷵻ز�ѯѧ����Ϣ
		
		//ִ��SQL��ѯ
		//query(��ѯSQL, �������, RowMapperӳ���е�ʵ����)
		allStus = this.jdbcTemplate.query(sql, params.toArray() ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TblStuinfo stu = new TblStuinfo();
				stu.setStuId(rs.getInt("stu_id"));
				stu.setStuName(rs.getString("stu_name"));
				stu.setStuSex(rs.getString("stu_sex"));
				stu.setStuBirthday(rs.getTimestamp("stu_birthday"));
				stu.setStuAge(rs.getInt("stu_age"));
				System.out.println("��ҳ��¼ת��ѧ������"+stu.toString());
				return stu;
			}
		});
		System.out.println("��ҳ�߼���ѯ���ص�ѧ����"+allStus);
		return allStus;//�����������ִ�з�ҳSQL�󷵻ز�ѯѧ����Ϣ
	}
	
	
}
