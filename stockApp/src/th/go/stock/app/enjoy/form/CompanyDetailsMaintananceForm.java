package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.RefuserstatusBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.model.Userprivilege;

public class CompanyDetailsMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private CompanyDetailsBean 		companyDetailsBean;
	private List<ComboBean> 		statusCombo;
	private String					errMsg;
	private String					pageMode;
	private String					titlePage;
	
	public CompanyDetailsMaintananceForm(){
		this.companyDetailsBean 	= new CompanyDetailsBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
	}

	public CompanyDetailsBean getCompanyDetailsBean() {
		return companyDetailsBean;
	}

	public void setCompanyDetailsBean(CompanyDetailsBean companyDetailsBean) {
		this.companyDetailsBean = companyDetailsBean;
	}

	public List<ComboBean> getStatusCombo() {
		return statusCombo;
	}

	public void setStatusCombo(List<ComboBean> statusCombo) {
		this.statusCombo = statusCombo;
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
	
}
