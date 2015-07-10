package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;

public class CustomerDetailsMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private CustomerDetailsBean 	customerDetailsBean;
	private List<ComboBean> 		statusCombo;
	private List<ComboBean> 		sexCombo;
	private String					errMsg;
	private String					pageMode;
	private String					titlePage;
	
	public CustomerDetailsMaintananceForm(){
		this.customerDetailsBean 	= new CustomerDetailsBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.sexCombo				= new ArrayList<ComboBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
	}

	public CustomerDetailsBean getCustomerDetailsBean() {
		return customerDetailsBean;
	}

	public void setCustomerDetailsBean(CustomerDetailsBean customerDetailsBean) {
		this.customerDetailsBean = customerDetailsBean;
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

	public List<ComboBean> getSexCombo() {
		return sexCombo;
	}

	public void setSexCombo(List<ComboBean> sexCombo) {
		this.sexCombo = sexCombo;
	}
	
}
