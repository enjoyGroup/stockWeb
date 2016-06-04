package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;

public class UserDetailsLookUpForm {
	
	private String					find;
	private String					column;
	private String					orderBy;
	private String					sortBy;
	private String					likeFlag;
	private	String					tin;
	
	private List<ComboBean> 		columnList;
	private List<ComboBean> 		orderByList;
	private List<ComboBean> 		sortByList;
	private List<UserDetailsBean> 	dataList;
	
	private int						pageNum;
	private int						totalPage;
	private String					totalRecord;
	private HashMap					hashTable;
	
	public UserDetailsLookUpForm(){
		
		this.find				= "";
		this.column				= "";
		this.orderBy			= "";
		this.sortBy				= "";
		this.likeFlag			= "";
		this.tin				= "";
		
		this.columnList				= new ArrayList<ComboBean>();
		this.orderByList			= new ArrayList<ComboBean>();
		this.sortByList				= new ArrayList<ComboBean>();
		this.dataList				= new ArrayList<UserDetailsBean>();
		
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

	public List<ComboBean> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ComboBean> columnList) {
		this.columnList = columnList;
	}

	public List<ComboBean> getOrderByList() {
		return orderByList;
	}

	public void setOrderByList(List<ComboBean> orderByList) {
		this.orderByList = orderByList;
	}

	public List<ComboBean> getSortByList() {
		return sortByList;
	}

	public void setSortByList(List<ComboBean> sortByList) {
		this.sortByList = sortByList;
	}

	public List<UserDetailsBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<UserDetailsBean> dataList) {
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

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}
}
