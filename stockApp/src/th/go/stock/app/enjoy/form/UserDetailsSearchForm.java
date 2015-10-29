package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.model.Userprivilege;

public class UserDetailsSearchForm {
	
	public static final String NEW 		= "NEW";
	public static final String EDIT 	= "EDIT";
	
	private UserDetailsBean 		userDetailsBean;
	private List<ComboBean> 		statusCombo;
	private List<Userprivilege> 	userprivilegeList;
	private List<UserDetailsBean> 	userDetailsBeanList;
	private String					errMsg;
	private String					pageMode;
	private String					titlePage;
	private int						pageNum;
	private int						totalPage;
	private String					totalRecord;
	private HashMap					hashTable;
	
	public UserDetailsSearchForm(){
		this.userDetailsBean 		= new UserDetailsBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.userprivilegeList		= new ArrayList<Userprivilege>();
		this.userDetailsBeanList	= new ArrayList<UserDetailsBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
		this.pageNum				= 1;
		this.totalPage				= 1;
		this.totalRecord			= "";
		this.hashTable				= new HashMap();
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

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	public HashMap getHashTable() {
		return hashTable;
	}

	public void setHashTable(HashMap hashTable) {
		this.hashTable = hashTable;
	}
	
	
	
}
