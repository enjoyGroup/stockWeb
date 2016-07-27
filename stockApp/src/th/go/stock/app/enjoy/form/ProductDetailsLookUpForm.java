package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ProductmasterBean;

public class ProductDetailsLookUpForm {
	
	private ProductmasterBean 			productmasterBean;
	private String						errMsg;
	private int							pageNum;
	private int							totalPage;
	private String						totalRecord;
	private List<ProductmasterBean> 	dataList;
	private HashMap						hashTable;
	
	public ProductDetailsLookUpForm(){
		
		this.productmasterBean 		= new ProductmasterBean();
		this.errMsg					= "";
		this.pageNum				= 1;
		this.totalPage				= 1;
		this.totalRecord			= "";
		this.dataList				= new ArrayList<ProductmasterBean>();
		this.hashTable				= new HashMap();
	}


	public ProductmasterBean getProductmasterBean() {
		return productmasterBean;
	}

	public void setProductmasterBean(ProductmasterBean productmasterBean) {
		this.productmasterBean = productmasterBean;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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

	public List<ProductmasterBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<ProductmasterBean> dataList) {
		this.dataList = dataList;
	}

	public HashMap getHashTable() {
		return hashTable;
	}

	public void setHashTable(HashMap hashTable) {
		this.hashTable = hashTable;
	}
}
