package util;

public class CustomerDTO {
	private int search_cusid;//学生学号
	private String search_cusname;//学生姓名
	public CustomerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerDTO(int search_cusid, String search_cusname) {
		super();
		this.search_cusid = search_cusid;
		this.search_cusname = search_cusname;
	}
	public int getSearch_cusid() {
		return search_cusid;
	}
	public void setSearch_cusid(int search_cusid) {
		this.search_cusid = search_cusid;
	}
	public String getSearch_cusname() {
		return search_cusname;
	}
	public void setSearch_cusname(String search_cusname) {
		this.search_cusname = search_cusname;
	}
	@Override
	public String toString() {
		return "CustomerDTO [search_cusid=" + search_cusid
				+ ", search_cusname=" + search_cusname + "]";
	}
	
	
}
