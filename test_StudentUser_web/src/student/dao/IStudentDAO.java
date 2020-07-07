package student.dao;

import java.util.List;

import student.entity.TblStuinfo;
import student.util.StudentDTO;

public interface IStudentDAO {
	//查询记录的总数
	public abstract int getTotalCount(StudentDTO dto);
		
	//根据条件按分页SQL执行分页查询
	public abstract List<TblStuinfo> selectStudentsByPage(int pageNo, int pageSize,StudentDTO dto);
	
	public abstract int insertStudent(TblStuinfo student);

	public abstract int updateStudent(TblStuinfo student);

	public abstract int deleteStudent(int studentNo);

	public abstract List<TblStuinfo> selectAllStudent();

	public abstract TblStuinfo selectStudentById(int studentNo);
	
	public abstract List<TblStuinfo> stuAdvanceQuery(StudentDTO dto);

}