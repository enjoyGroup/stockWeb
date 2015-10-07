package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComparePriceBean;

public class ComparePriceForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	private ComparePriceBean 			comparePriceBean;
	private List<ComparePriceBean> 		comparePriceList;
	private String 						productCode;
	private String 						productName;
	private String						errMsg;
	private String						seqTemp;
	private boolean						chk;
	private String						pageAction;
	
	public ComparePriceForm(){
		this.comparePriceBean		= new ComparePriceBean();
		this.comparePriceList		= new ArrayList<ComparePriceBean>();
		this.productCode			= "";
		this.productName			= "";
		this.errMsg					= "";
		this.seqTemp				= "0";
		this.chk					= false;
		this.pageAction				= "";
	}

	public ComparePriceBean getComparePriceBean() {
		return comparePriceBean;
	}

	public void setComparePriceBean(ComparePriceBean comparePriceBean) {
		this.comparePriceBean = comparePriceBean;
	}

	public List<ComparePriceBean> getComparePriceList() {
		return comparePriceList;
	}

	public void setComparePriceList(List<ComparePriceBean> comparePriceList) {
		this.comparePriceList = comparePriceList;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public String getPageAction() {
		return pageAction;
	}

	public void setPageAction(String pageAction) {
		this.pageAction = pageAction;
	}
}
