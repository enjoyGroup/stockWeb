package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.model.Userprivilege;

public class UserDetailsMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private UserDetailsBean 		userDetailsBean;
	private List<ComboBean> 		statusCombo;
	private List<Userprivilege> 	userprivilegeList;
	private List<UserDetailsBean> 	userDetailsBeanList;
	private List<ComboBean> 		companyCombo;
	private String					errMsg;
	private String					pageMode;
	private String					titlePage;
	
	public UserDetailsMaintananceForm(){
		this.userDetailsBean 		= new UserDetailsBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.userprivilegeList		= new ArrayList<Userprivilege>();
		this.userDetailsBeanList	= new ArrayList<UserDetailsBean>();
		this.companyCombo			= new ArrayList<ComboBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
	}

	public UserDetailsBean getUserDetailsBean() {
		return userDetailsBean;
	}

	public void setUserDetailsBean(UserDetailsBean userDetailsBean) {
		this.userDetailsBean = userDetailsBean;
	}

	public List<ComboBean> getStatusCombo() {
		return statusCombo;
	}

	public void setStatusCombo(List<ComboBean> statusCombo) {
		this.statusCombo = statusCombo;
	}

	public List<Userprivilege> getUserprivilegeList() {
		return userprivilegeList;
	}

	public void setUserprivilegeList(List<Userprivilege> userprivilegeList) {
		this.userprivilegeList = userprivilegeList;
	}

	public List<UserDetailsBean> getUserDetailsBeanList() {
		return userDetailsBeanList;
	}

	public void setUserDetailsBeanList(List<UserDetailsBean> userDetailsBeanList) {
		this.userDetailsBeanList = userDetailsBeanList;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getPageMode() {
		return pageMode;
	}

	public void setPageMode(String pageMode) {
		this.pageMode = pageMode;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

	public List<ComboBean> getCompanyCombo() {
		return companyCombo;
	}

	public void setCompanyCombo(List<ComboBean> companyCombo) {
		this.companyCombo = companyCombo;
	}
	
}
