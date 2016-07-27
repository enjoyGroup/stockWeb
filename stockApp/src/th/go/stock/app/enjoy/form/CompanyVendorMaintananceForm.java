package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyVendorBean;

public class CompanyVendorMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	
	
	private CompanyVendorBean 			companyVendorBean;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	
	public CompanyVendorMaintananceForm(){
		this.companyVendorBean 		= new CompanyVendorBean();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
	}

	public CompanyVendorBean getCompanyVendorBean() {
		return companyVendorBean;
	}

	public void setCompanyVendorBean(CompanyVendorBean companyVendorBean) {
		this.companyVendorBean = companyVendorBean;
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
