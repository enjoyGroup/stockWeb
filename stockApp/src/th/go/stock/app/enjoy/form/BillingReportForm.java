package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;

public class BillingReportForm {
	
	private InvoiceCreditMasterBean 		invoiceCreditMasterBean;
	private String							errMsg;
	private String							titlePage;
	private List<InvoiceCreditMasterBean> 	dataList;
	private List<ComboBean>					invoiceTypeCombo;
	
	public BillingReportForm(){
		this.invoiceCreditMasterBean 	= new InvoiceCreditMasterBean();
		this.errMsg						= "";
		this.titlePage					= "";
		this.dataList					= new ArrayList<InvoiceCreditMasterBean>();
		this.invoiceTypeCombo			= new ArrayList<ComboBean>();
	}

	public InvoiceCreditMasterBean getInvoiceCreditMasterBean() {
		return invoiceCreditMasterBean;
	}

	public void setInvoiceCreditMasterBean(
			InvoiceCreditMasterBean invoiceCreditMasterBean) {
		this.invoiceCreditMasterBean = invoiceCreditMasterBean;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

	public List<InvoiceCreditMasterBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<InvoiceCreditMasterBean> dataList) {
		this.dataList = dataList;
	}

	public List<ComboBean> getInvoiceTypeCombo() {
		return invoiceTypeCombo;
	}

	public void setInvoiceTypeCombo(List<ComboBean> invoiceTypeCombo) {
		this.invoiceTypeCombo = invoiceTypeCombo;
	}
}
