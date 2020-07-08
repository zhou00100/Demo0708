package dao;

import java.util.List;

import util.CustomerDTO;
import bean.TbCustomer;

public interface ICustomerDAO {

	public abstract int insertCustomer(TbCustomer customer);

	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#updateStudent(student.entity.TblStuinfo)
	 */
	public abstract int updateCustomer(TbCustomer customer);

	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#deleteStudent(int)
	 */
	public abstract int deleteCustomer(int customerNo);

	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectAllStudent()
	 */
	public abstract List<TbCustomer> selectAllCustomer();

	/* (non-Javadoc)
	 * @see student.dao.IStudentDAO#selectStudentById(int)
	 */
	public abstract TbCustomer selectCustomerById(int customerNo);

	//学生信息的组合查询
	public abstract List<TbCustomer> cusAdvanceQuery(CustomerDTO dto);

	//根据组合查询条件计算记录总数
	public abstract int getTotalCount(CustomerDTO dto);

	public abstract List<TbCustomer> selectCustomerByPage(int pageNo,
			int pageSize, CustomerDTO dto);

}