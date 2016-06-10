package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;

public class InvoiceCashSearchForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private InvoiceCashMasterBean 		invoiceCashMasterBean;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	private int							pageNum;
	private int							totalPage;
	private String						totalRecord;
	private List<InvoiceCashMasterBean> dataList;
	private HashMap						hashTable;
	List<ComboBean>						invoiceStatusCombo;
	private List<ComboBean> 			companyCombo;
	
	public InvoiceCashSearchForm(){
		this.invoiceCashMasterBean 	= new InvoiceCashMasterBean();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
		this.pageNum				= 1;
		this.totalPage				= 1;
		this.totalRecord			= "";
		this.dataList				= new ArrayList<InvoiceCashMasterBean>();
		this.hashTable				= new HashMap();
		this.invoiceStatusCombo		= new ArrayList<ComboBean>();
		this.companyCombo			= new ArrayList<ComboBean>();
	}

	public InvoiceCashMasterBean getInvoiceCashMasterBean() {
		return invoiceCashMasterBean;
	}

	public void setInvoiceCashMasterBean(InvoiceCashMasterBean invoiceCashMasterBean) {
		this.invoiceCashMasterBean = invoiceCashMasterBean;
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

	public List<InvoiceCashMasterBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<InvoiceCashMasterBean> dataList) {
		this.dataList = dataList;
	}

	public HashMap getHashTable() {
		return hashTable;
	}

	public void setHashTable(HashMap hashTable) {
		this.hashTable = hashTable;
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
