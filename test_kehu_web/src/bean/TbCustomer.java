package bean;

public class TbCustomer {
	int custId;
	String custName;
	String phone;
	String mobile;
	String email;
	String fax;
	String address;
	public TbCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TbCustomer(int custId, String custName, String phone, String mobile,
			String email, String fax, String address) {
		super();
		this.custId = custId;
		this.custName = custName;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.fax = fax;
		this.address = address;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "TbCustomer [custId=" + custId + ", custName=" + custName
				+ ", phone=" + phone + ", mobile=" + mobile + ", email="
				+ email + ", fax=" + fax + ", address=" + address + "]";
	}
	
	
}
