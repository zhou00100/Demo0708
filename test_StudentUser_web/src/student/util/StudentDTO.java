package student.util;

//ѧ���߼���ѯ�����ݷ�װ��DTO
public class StudentDTO {
	private int search_stuid;//ѧ��ѧ��
	private String search_stuname;//ѧ������
	private int search_stuage1;//ѧ���������
	private int search_stuage2;//ѧ���������
	private String search_birthdayDate1;//��ʼ����
	private String search_birthdayDate2;//��������
	
	
	public int getSearch_stuid() {
		return search_stuid;
	}
	public void setSearch_stuid(int search_stuid) {
		this.search_stuid = search_stuid;
	}
	public String getSearch_stuname() {
		return search_stuname;
	}
	public void setSearch_stuname(String search_stuname) {
		this.search_stuname = search_stuname;
	}
	public int getSearch_stuage1() {
		return search_stuage1;
	}
	public void setSearch_stuage1(int search_stuage1) {
		this.search_stuage1 = search_stuage1;
	}
	public int getSearch_stuage2() {
		return search_stuage2;
	}
	public void setSearch_stuage2(int search_stuage2) {
		this.search_stuage2 = search_stuage2;
	}
	public String getSearch_birthdayDate1() {
		return search_birthdayDate1;
	}
	public void setSearch_birthdayDate1(String search_birthdayDate1) {
		this.search_birthdayDate1 = search_birthdayDate1;
	}
	public String getSearch_birthdayDate2() {
		return search_birthdayDate2;
	}
	public void setSearch_birthdayDate2(String search_birthdayDate2) {
		this.search_birthdayDate2 = search_birthdayDate2;
	}
	public StudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentDTO(int search_stuid, String search_stuname,
			int search_stuage1, int search_stuage2,
			String search_birthdayDate1, String search_birthdayDate2) {
		super();
		this.search_stuid = search_stuid;
		this.search_stuname = search_stuname;
		this.search_stuage1 = search_stuage1;
		this.search_stuage2 = search_stuage2;
		this.search_birthdayDate1 = search_birthdayDate1;
		this.search_birthdayDate2 = search_birthdayDate2;
	}
	@Override
	public String toString() {
		return "StudentsDTO [search_stuid=" + search_stuid
				+ ", search_stuname=" + search_stuname + ", search_stuage1="
				+ search_stuage1 + ", search_stuage2=" + search_stuage2
				+ ", search_birthdayDate1=" + search_birthdayDate1
				+ ", search_birthdayDate2=" + search_birthdayDate2 + "]";
	}
}
