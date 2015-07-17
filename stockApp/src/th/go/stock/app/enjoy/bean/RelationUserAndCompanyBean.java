package th.go.stock.app.enjoy.bean;

public class RelationUserAndCompanyBean {
	
	private String userUniqueId;
	private String tin;
	private String userId;
	private String userFullName;
	private String userStatus;
	private String userStatusName;
	private String rowStatus;
	private String seq;
	
	public RelationUserAndCompanyBean(){
		this.userUniqueId 		= "";
		this.tin 				= "";
		this.userId 			= "";
		this.userFullName 		= "";
		this.userStatus 		= "";
		this.userStatusName 	= "";
		this.rowStatus 			= "";
		this.seq 				= "";
	}

	public String getUserUniqueId() {
		return userUniqueId;
	}

	public void setUserUniqueId(String userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserStatusName() {
		return userStatusName;
	}

	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}

	public String getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
}
