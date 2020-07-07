package student.dao;

import java.util.List;

import student.entity.TblStuinfo;
import student.util.StudentDTO;

public interface IStudentDAO {
	//��ѯ��¼������
	public abstract int getTotalCount(StudentDTO dto);
		
	//������������ҳSQLִ�з�ҳ��ѯ
	public abstract List<TblStuinfo> selectStudentsByPage(int pageNo, int pageSize,StudentDTO dto);
	
	public abstract int insertStudent(TblStuinfo student);

	public abstract int updateStudent(TblStuinfo student);

	public abstract int deleteStudent(int studentNo);

	public abstract List<TblStuinfo> selectAllStudent();

	public abstract TblStuinfo selectStudentById(int studentNo);
	
	public abstract List<TblStuinfo> stuAdvanceQuery(StudentDTO dto);

}