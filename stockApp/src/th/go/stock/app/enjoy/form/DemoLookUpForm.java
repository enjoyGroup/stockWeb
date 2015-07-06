package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.DemoLookUpBean;

public class DemoLookUpForm {
	
	private String					find;
	private String					column;
	private String					orderBy;
	private String					sortBy;
	private String					likeFlag;
	
	private List<DemoLookUpBean> 	columnList;
	private List<DemoLookUpBean> 	orderByList;
	private List<DemoLookUpBean> 	sortByList;
	private List<DemoLookUpBean> 	dataList;
	
	private int						pageNum;
	private int						totalPage;
	private String					totalRecord;
	private HashMap					hashTable;
	
	public DemoLookUpForm(){
		
		this.find				= "";
		this.column				= "";
		this.orderBy			= "";
		this.sortBy				= "";
		this.likeFlag			= "";
		
		
		this.columnList				= new ArrayList<DemoLookUpBean>();
		this.orderByList			= new ArrayList<DemoLookUpBean>();
		this.sortByList				= new ArrayList<DemoLookUpBean>();
		this.dataList				= new ArrayList<DemoLookUpBean>();
		
		this.pageNum				= 1;
		this.totalPage				= 1;
		this.totalRecord			= "";
		this.hashTable				= new HashMap();
	}

	public String getFind() {
		return find;
	}

	public void setFind(String find) {
		this.find = find;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(String likeFlag) {
		this.likeFlag = likeFlag;
	}

	public List<DemoLookUpBean> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<DemoLookUpBean> columnList) {
		this.columnList = columnList;
	}

	public List<DemoLookUpBean> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<DemoLookUpBean> orderByList) {
		this.orderByList = orderByList;
	}

	public List<DemoLookUpBean> getSortByList() {
		return sortByList;
	}

	public void setSortByList(List<DemoLookUpBean> sortByList) {
		this.sortByList = sortByList;
	}

	public List<DemoLookUpBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<DemoLookUpBean> dataList) {
		this.dataList = dataList;
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

	public HashMap getHashTable() {
		return hashTable;
	}

	public void setHashTable(HashMap hashTable) {
		this.hashTable = hashTable;
	}

	
	
	
	
}
