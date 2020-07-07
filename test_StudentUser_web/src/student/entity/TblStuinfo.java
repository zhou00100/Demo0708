package student.entity;

import java.util.Date;

public class TblStuinfo {
	int stuId;//学生编号
	String stuName;//姓名
	String stuSex;//性别
	Date stuBirthday;//出生日期
	int stuAge;//年龄
	public TblStuinfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TblStuinfo(int stuId, String stuName, String stuSex,
			Date stuBirthday, int stuAge) {
		super();
		this.stuId = stuId;
		this.stuName = stuName;
		this.stuSex = stuSex;
		this.stuBirthday = stuBirthday;
		this.stuAge = stuAge;
	}
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	public String getStuSex() {
		return stuSex;
	}
	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	public Date getStuBirthday() {
		return stuBirthday;
	}
	public void setStuBirthday(Date stuBirthday) {
		this.stuBirthday = stuBirthday;
	}
	public int getStuAge() {
		return stuAge;
	}
	public void setStuAge(int stuAge) {
		this.stuAge = stuAge;
	}
	@Override
	public String toString() {
		return "TblStuinfo [stuId=" + stuId + ", stuName=" + stuName
				+ ", stuSex=" + stuSex + ", stuBirthday=" + stuBirthday
				+ ", stuAge=" + stuAge + "]";
	}
	
	
}
