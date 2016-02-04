package th.go.stock.app.enjoy.form;

import th.go.stock.app.enjoy.bean.CompanyVendorBean;

public class CompanyVendorDisplayForm {
	
	
	private CompanyVendorBean 			companyVendorBean;
	private String						titlePage;
	
	public CompanyVendorDisplayForm(){
		this.companyVendorBean 		= new CompanyVendorBean();
		this.titlePage				= "";
	}

	public CompanyVendorBean getCompanyVendorBean() {
		return companyVendorBean;
	}

	public void setCompanyVendorBean(CompanyVendorBean companyVendorBean) {
		this.companyVendorBean = companyVendorBean;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

}
