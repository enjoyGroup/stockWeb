package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the userdetails database table.
 * 
 */
@Entity
@Table(name="userdetails")
@NamedQuery(name="Userdetail.findAll", query="SELECT u FROM Userdetail u")
public class Userdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int userUniqueId;

	private String flagAlertStock;

	private String flagChangePassword;

	private String userEmail;

	private String userId;

	private String userLevel;

	private String userName;

	private String userPassword;

	private String userPrivilege;

	private String userStatus;

	private String userSurname;

	public Userdetail() {
	}

	public int getUserUniqueId() {
		return this.userUniqueId;
	}

	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

	public String getFlagAlertStock() {
		return this.flagAlertStock;
	}

	public void setFlagAlertStock(String flagAlertStock) {
		this.flagAlertStock = flagAlertStock;
	}

	public String getFlagChangePassword() {
		return this.flagChangePassword;
	}

	public void setFlagChangePassword(String flagChangePassword) {
		this.flagChangePassword = flagChangePassword;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPrivilege() {
		return this.userPrivilege;
	}

	public void setUserPrivilege(String userPrivilege) {
		this.userPrivilege = userPrivilege;
	}

	public String getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserSurname() {
		return this.userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

}