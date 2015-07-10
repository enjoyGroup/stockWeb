package th.go.stock.app.enjoy.bean;

public class RelationUserAndCompanyBean {
	
	private String 	tin;
	private String	userUniqueId;
	private String 	companyName;
	private String 	userName;
	private String 	userSurname;
	private String 	empName;
	
	public RelationUserAndCompanyBean(){
		this.tin 				= "";
		this.companyName 		= "";
		this.userUniqueId 		= "";
		this.userName 			= "";
		this.userSurname 		= "";
		this.empName 			= "";
		
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
