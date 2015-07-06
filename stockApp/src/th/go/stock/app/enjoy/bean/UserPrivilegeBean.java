package th.go.stock.app.enjoy.bean;

import java.util.ArrayList;

public class UserPrivilegeBean {
		
	private String 	  					privilegeCode	  = null;
	private String 	  					privilegeName	  = null;
	private ArrayList<PagesDetailBean> 	pagesList	 	  = null;
	
	public UserPrivilegeBean(){
		this.privilegeCode			  = "";
		this.privilegeName			  = "";
		this.pagesList			  	  = new ArrayList<PagesDetailBean>();
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public ArrayList<PagesDetailBean> getPagesDetail() {
		return pagesList;
	}

	public void setPagesDetail(ArrayList<PagesDetailBean> pagesList) {
		this.pagesList = pagesList;
	}

	
}
