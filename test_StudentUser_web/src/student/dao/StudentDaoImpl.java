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
				new Timestamp(student.getStuBirthday().getTime()),//Oracle日期必须TimeStamp
				student.getStuAge()
		});
		return count;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#updateStudent(student.entity.TblStuinfo)
	 */
	public int updateStudent(TblStuinfo student) {
		System.out.println("StudentDaoImpl修改学生"+student.toString());
		String sql = "update tbl_stuinfo set stu_name=?,stu_sex=?,stu_birthday=?,stu_age=? where stu_id=?";
		int count = 0;//添加/删除/修改记录总数
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
		System.out.println("StudentDaoImpl删除学生："+ studentNo);
		String sql = "delete from tbl_stuinfo where stu_id=?";
		
		int count = 0;
		count = this.jdbcTemplate.update(sql, new Object[]{studentNo});
		return count;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectAllStudent()
	 */
	public List<TblStuinfo> selectAllStudent() {
		System.out.println("StudentDaoImpl：selectAllStudent-〉全部查询学生");
		String sql = "select * from tbl_stuinfo";
		List<TblStuinfo> allStu =this.jdbcTemplate.query(sql, new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TblStuinfo stu = new TblStuinfo();
				stu.setStuId(rs.getInt("stu_id"));
				stu.setStuName(rs.getString("stu_name"));
				stu.setStuSex(rs.getString("stu_sex"));
				stu.setStuBirthday(rs.getTimestamp("stu_birthday"));
				stu.setStuAge(rs.getInt("stu_age"));
				System.out.println("记录转换学生对象："+stu.toString());
				return stu;
			}
		});
		System.out.println("执行结束后："+allStu.toString());
		return allStu;
	}
	
	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectStudentById(int)
	 */
	public TblStuinfo selectStudentById(int studentNo) {
		System.out.println("StudentDaoImpl按编号查询学生："+studentNo);
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
				System.out.println("记录转换学生对象："+stu.toString());
				return stu;
			}
		});
		if (allStu.size() > 0) {
			//按编号查询了一个学生
			stu = allStu.get(0);
		}
		System.out.println("查询结果："+stu);
		return stu;
	}
	
	//学生信息的组合查询
	public List<TblStuinfo> stuAdvanceQuery(StudentDTO dto) {
		System.out.println("StudentDaoImpl高级查询");
		System.out.println("高级查询传递的参数："+dto.toString());
		String sql = "select * from tbl_stuinfo where 1=1 ";
		
		List params = new ArrayList();//高级查询组合条件SQL
			if (dto != null) {
			//true：表单传递了参数，参数被封装成了一个DTO
			//组合不同的SQL实现不同情况的条件查询
			if (dto.getSearch_stuid() > 0) {
				//true：表单中传递的学生编号（组合按编号查询学生）
				sql += " and stu_id = ?";
				params.add(dto.getSearch_stuid());//在SQL中组合学生编号
			}
			
			if (dto.getSearch_stuname() != null && !"".equals(dto.getSearch_stuname())) {
				//true：表单中传递的学生姓名（组合按姓名模糊查询学生）
				sql += " and stu_name like ?";
				params.add("%"+ dto.getSearch_stuname() +"%");//在SQL中组合模糊查询学生名字
			}
			//组合学生年龄区间
			if (dto.getSearch_stuage1() >= 0 && dto.getSearch_stuage2() > 0 
					&& dto.getSearch_stuage1()< dto.getSearch_stuage2()) {
				sql += " and stu_age between ? and ? ";
				params.add(dto.getSearch_stuage1());
				params.add(dto.getSearch_stuage2());
			}
			
			//组合学生生日区间
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
				System.out.println("记录转换学生对象："+stu.toString());
				return stu;
			}
		});
		return allStus;
	}
	
	//根据组合查询条件计算记录总数
	public int getTotalCount(StudentDTO dto) {
		int count =0;
		String sql="select count(*) from tbl_stuinfo where 1=1 ";//count()聚合函数计算查询记录总数
		List params = new ArrayList();//高级查询组合条件SQL
		
		if (dto!=null) {
			//true：表单传递了参数，参数被封装成了一个DTO
			//组合不同的SQL实现不同情况的条件查询
			if (dto.getSearch_stuid() > 0) {
				//true：表单中传递的学生编号（组合按编号查询学生）
				sql += " and stu_id = ?";
				params.add(dto.getSearch_stuid());//在SQL中组合学生编号
			}
			
			if (dto.getSearch_stuname() != null && !"".equals(dto.getSearch_stuname())) {
				//true：表单中传递的学生姓名（组合按姓名模糊查询学生）
				sql += " and stu_name like ?";
				params.add("%"+ dto.getSearch_stuname() +"%");//在SQL中组合模糊查询学生名字
			}
			
			//组合学生年龄区间
			if (dto.getSearch_stuage1() >= 0 && dto.getSearch_stuage2() > 0 
					&& dto.getSearch_stuage1()< dto.getSearch_stuage2()) {
				sql += " and stu_age between ? and ? ";
				params.add(dto.getSearch_stuage1());
				params.add(dto.getSearch_stuage2());
			}
			
			//组合学生生日区间
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
		//执行SQL查询
		//queryForInt(查询SQL, 组合条件)：查询结果仅返回整数值可以使用queryForInt
		count = this.jdbcTemplate.queryForInt(sql, params.toArray());
		System.out.println("记录总数查询返回的结果："+count);
		return count;
	}
	
	public List<TblStuinfo> selectStudentsByPage(int pageNo, int pageSize, StudentDTO dto) {
		//分页查询SQL：
		//分页每一页开始位置：
		int start = (pageNo - 1)*pageSize + 1;
		//分页每一页结束位置：
		int end = pageNo * pageSize;
		String sql = "select * from(select row_number() over(order by stu_id) no, s.* from tbl_stuinfo s where 1=1 ";
		
		
		List params = new ArrayList();//高级查询组合条件SQL
		
		if (dto != null) {
			//true：表单传递了参数，参数被封装成了一个DTO
			//组合不同的SQL实现不同情况的条件查询
			if (dto.getSearch_stuid() > 0) {
				//true：表单中传递的学生编号（组合按编号查询学生）
				sql += " and stu_id = ?";
				params.add(dto.getSearch_stuid());//在SQL中组合学生编号
			}
			
			if (dto.getSearch_stuname() != null && !"".equals(dto.getSearch_stuname())) {
				//true：表单中传递的学生姓名（组合按姓名模糊查询学生）
				sql += " and stu_name like ?";
				params.add("%"+ dto.getSearch_stuname() +"%");//在SQL中组合模糊查询学生名字
			}
			
			//组合学生年龄区间
			if (dto.getSearch_stuage1() >= 0 && dto.getSearch_stuage2() > 0 
					&& dto.getSearch_stuage1()< dto.getSearch_stuage2()) {
				sql += " and stu_age between ? and ? ";
				params.add(dto.getSearch_stuage1());
				params.add(dto.getSearch_stuage2());
			}
			
			//组合学生生日区间
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
		
		System.out.println("当前查询SQL："+sql);
		sql += ")temp where temp.no between ? and ?";//没有组合任何条件默认就是全部查询的分页
		params.add(start);//between ?-〉每一页开始位置
		params.add(end);//and ?-〉每一页结束位置
		
		System.out.println("当前分页查询SQL："+sql);
		List<TblStuinfo> allStus = null;//根据组合条件执行分页SQL后返回查询学生信息
		
		//执行SQL查询
		//query(查询SQL, 组合条件, RowMapper映射列到实体类)
		allStus = this.jdbcTemplate.query(sql, params.toArray() ,new RowMapper() {
			
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				TblStuinfo stu = new TblStuinfo();
				stu.setStuId(rs.getInt("stu_id"));
				stu.setStuName(rs.getString("stu_name"));
				stu.setStuSex(rs.getString("stu_sex"));
				stu.setStuBirthday(rs.getTimestamp("stu_birthday"));
				stu.setStuAge(rs.getInt("stu_age"));
				System.out.println("分页记录转换学生对象："+stu.toString());
				return stu;
			}
		});
		System.out.println("分页高级查询返回的学生："+allStus);
		return allStus;//根据组合条件执行分页SQL后返回查询学生信息
	}
	
	
}
