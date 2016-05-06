package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.main.ConfigFile;

public class InvoiceCreditMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	public final static String VAT 	= ConfigFile.getVat();
	
	
	private InvoiceCreditMasterBean 		invoiceCreditMasterBean;
	private CustomerDetailsBean 			customerDetailsBean;
	private String							errMsg;
	private String							pageMode;
	private String							titlePage;
	private List<InvoiceCreditDetailBean> 	invoiceCreditDetailList;
	private String							seqTemp;
	private List<ComboBean>					invoiceStatusCombo;
	private List<ComboBean> 				companyCombo;
	
	public InvoiceCreditMaintananceForm(){
		this.invoiceCreditMasterBean 	= new InvoiceCreditMasterBean();
		this.customerDetailsBean 		= new CustomerDetailsBean();
		this.errMsg						= "";
		this.pageMode					= NEW;
		this.titlePage					= "";
		this.invoiceCreditDetailList	= new ArrayList<InvoiceCreditDetailBean>();
		this.seqTemp					= "0";
		this.invoiceStatusCombo			= new ArrayList<ComboBean>();
		this.companyCombo				= new ArrayList<ComboBean>();
	}

	public InvoiceCreditMasterBean getInvoiceCreditMasterBean() {
		return invoiceCreditMasterBean;
	}

	public void setInvoiceCreditMasterBean(InvoiceCreditMasterBean invoiceCreditMasterBean) {
		this.invoiceCreditMasterBean = invoiceCreditMasterBean;
	}

	public CustomerDetailsBean getCustomerDetailsBean() {
		return customerDetailsBean;
	}

	public void setCustomerDetailsBean(CustomerDetailsBean customerDetailsBean) {
		this.customerDetailsBean = customerDetailsBean;
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

	public List<InvoiceCreditDetailBean> getInvoiceCreditDetailList() {
		return invoiceCreditDetailList;
	}

	public void setInvoiceCreditDetailList(List<InvoiceCreditDetailBean> invoiceCreditDetailList) {
		this.invoiceCreditDetailList = invoiceCreditDetailList;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}

	public List<ComboBean> getInvoiceStatusCombo() {
		return invoiceStatusCombo;
	}

	public void setInvoiceStatusCombo(List<ComboBean> invoiceStatusCombo) {
		this.invoiceStatusCombo = invoiceStatusCombo;
	}

	public List<ComboBean> getCompanyCombo() {
		return companyCombo;
	}

	public void setCompanyCombo(List<ComboBean> companyCombo) {
		this.companyCombo = companyCombo;
	}
}
