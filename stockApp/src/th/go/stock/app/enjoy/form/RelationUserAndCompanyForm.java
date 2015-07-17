package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.RelationUserAndCompanyBean;

public class RelationUserAndCompanyForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	private RelationUserAndCompanyBean 			relationUserAndCompanyBean;
	private List<RelationUserAndCompanyBean> 	relationUserAndCompanyList;
	private String 								tin;
	private String 								companyName;
	private String 								userFullName;
	private String								errMsg;
	private String								seqTemp;
	private boolean								chk;
	
	public RelationUserAndCompanyForm(){
		this.relationUserAndCompanyBean		= new RelationUserAndCompanyBean();
		this.relationUserAndCompanyList		= new ArrayList<RelationUserAndCompanyBean>();
		this.tin							= "";
		this.companyName					= "";
		this.userFullName					= "";
		this.errMsg							= "";
		this.seqTemp						= "0";
		this.chk							= false;
	}

	public RelationUserAndCompanyBean getRelationUserAndCompanyBean() {
		return relationUserAndCompanyBean;
	}

	public void setRelationUserAndCompanyBean(
			RelationUserAndCompanyBean relationUserAndCompanyBean) {
		this.relationUserAndCompanyBean = relationUserAndCompanyBean;
	}

	public List<RelationUserAndCompanyBean> getRelationUserAndCompanyList() {
		return relationUserAndCompanyList;
	}

	public void setRelationUserAndCompanyList(
			List<RelationUserAndCompanyBean> relationUserAndCompanyList) {
		this.relationUserAndCompanyList = relationUserAndCompanyList;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}

	public boolean isChk() {
		return chk;
	}

	public void setChk(boolean chk) {
		this.chk = chk;
	}

	public String getTin() {
		return tin;
	}

	public void setTin(String tin) {
		this.tin = tin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
}
