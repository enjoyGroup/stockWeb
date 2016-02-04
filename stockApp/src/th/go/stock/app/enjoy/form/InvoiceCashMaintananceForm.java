package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.InvoiceCashDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.main.ConfigFile;

public class InvoiceCashMaintananceForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	public final static String VAT 	= ConfigFile.getVat();
	
	
	private InvoiceCashMasterBean 		invoiceCashMasterBean;
	private CustomerDetailsBean 		customerDetailsBean;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	private List<InvoiceCashDetailBean> invoiceCashDetailList;
	private String						seqTemp;
	private List<ComboBean>				invoiceStatusCombo;
	private List<ComboBean> 			companyCombo;
	
	public InvoiceCashMaintananceForm(){
		this.invoiceCashMasterBean 		= new InvoiceCashMasterBean();
		this.customerDetailsBean 		= new CustomerDetailsBean();
		this.errMsg						= "";
		this.pageMode					= NEW;
		this.titlePage					= "";
		this.invoiceCashDetailList		= new ArrayList<InvoiceCashDetailBean>();
		this.seqTemp					= "0";
		this.invoiceStatusCombo			= new ArrayList<ComboBean>();
		this.companyCombo				= new ArrayList<ComboBean>();
	}

	public InvoiceCashMasterBean getInvoiceCashMasterBean() {
		return invoiceCashMasterBean;
	}

	public void setInvoiceCashMasterBean(InvoiceCashMasterBean invoiceCashMasterBean) {
		this.invoiceCashMasterBean = invoiceCashMasterBean;
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

	public List<InvoiceCashDetailBean> getInvoiceCashDetailList() {
		return invoiceCashDetailList;
	}

	public void setInvoiceCashDetailList(
			List<InvoiceCashDetailBean> invoiceCashDetailList) {
		this.invoiceCashDetailList = invoiceCashDetailList;
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
