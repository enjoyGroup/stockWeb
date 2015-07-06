package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.RefuserstatusBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.model.Refuserstatus;
import th.go.stock.app.enjoy.model.Userprivilege;

public class ChangePasswordForm {

	
	String	oldUserPassword;
	String	newUserPassword;
	String	confirmUserPassword;
	String	errMsg;
	
	public ChangePasswordForm (){
		this.oldUserPassword 		= "";
		this.newUserPassword		= "";
		this.confirmUserPassword	= "";
		this.errMsg					= "";
	}
	
	public String getOldUserPassword() {
		return oldUserPassword;
	}


	public void setOldUserPassword(String oldUserPassword) {
		this.oldUserPassword = oldUserPassword;
	}


	public String getNewUserPassword() {
		return newUserPassword;
	}


	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}


	public String getConfirmUserPassword() {
		return confirmUserPassword;
	}


	public void setConfirmUserPassword(String confirmUserPassword) {
		this.confirmUserPassword = confirmUserPassword;
	}


	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	
	
}
