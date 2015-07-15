package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.ProductDetailsBean;

public class ProductDetailsSearchForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	
	
	private ProductDetailsBean 			productDetailsBean;
	private List<ComboBean> 			statusCombo;
	private String						errMsg;
	private String						pageMode;
	private String						titlePage;
	private int							pageNum;
	private int							totalPage;
	private String						totalRecord;
	private List<ProductDetailsBean> 	dataList;
	private HashMap						hashTable;
	
	public ProductDetailsSearchForm(){
		this.productDetailsBean 	= new ProductDetailsBean();
		this.statusCombo			= new ArrayList<ComboBean>();
		this.errMsg					= "";
		this.pageMode				= NEW;
		this.titlePage				= "";
		this.pageNum				= 1;
		this.totalPage				= 1;
		this.totalRecord			= "";
		this.dataList				= new ArrayList<ProductDetailsBean>();
		this.hashTable				= new HashMap();
	}

	public ProductDetailsBean getProductDetailsBean() {
		return productDetailsBean;
	}

	public void setProductDetailsBean(ProductDetailsBean productDetailsBean) {
		this.productDetailsBean = productDetailsBean;
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

	public List<ProductDetailsBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<ProductDetailsBean> dataList) {
		this.dataList = dataList;
	}

	public HashMap getHashTable() {
		return hashTable;
	}

	public void setHashTable(HashMap hashTable) {
		this.hashTable = hashTable;
	}
}
