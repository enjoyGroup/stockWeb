package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;

public class InvoiceCreditSearchForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private InvoiceCreditMasterBean 		invoiceCreditMasterBean;
	private String							errMsg;
	private String							pageMode;
	private String							titlePage;
	private int								pageNum;
	private int								totalPage;
	private String							totalRecord;
	private List<InvoiceCreditMasterBean> 	dataList;
	private HashMap							hashTable;
	List<ComboBean>							invoiceStatusCombo;
	
	public InvoiceCreditSearchForm(){
		this.invoiceCreditMasterBean 	= new InvoiceCreditMasterBean();
		this.errMsg						= "";
		this.pageMode					= NEW;
		this.titlePage					= "";
		this.pageNum					= 1;
		this.totalPage					= 1;
		this.totalRecord				= "";
		this.dataList					= new ArrayList<InvoiceCreditMasterBean>();
		this.hashTable					= new HashMap();
		this.invoiceStatusCombo			= new ArrayList<ComboBean>();
	}

	public InvoiceCreditMasterBean getInvoiceCreditMasterBean() {
		return invoiceCreditMasterBean;
	}

	public void setInvoiceCreditMasterBean(InvoiceCreditMasterBean invoiceCreditMasterBean) {
		this.invoiceCreditMasterBean = invoiceCreditMasterBean;
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

	public List<InvoiceCreditMasterBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<InvoiceCreditMasterBean> dataList) {
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
}
