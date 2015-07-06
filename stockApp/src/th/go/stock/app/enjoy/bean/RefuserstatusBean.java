package th.go.stock.app.enjoy.bean;

public class RefuserstatusBean {
	
	private String userStatusCode;
	private String userStatusName;
	
	public RefuserstatusBean(){
		this.userStatusCode = "";
		this.userStatusName = "";
	}

	public String getUserStatusCode() {
		return userStatusCode;
	}

	public void setUserStatusCode(String userStatusCode) {
		this.userStatusCode = userStatusCode;
	}

	public String getUserStatusName() {
		return userStatusName;
	}

	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}
	
}
