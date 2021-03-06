package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.AdjustStockBean;

public class AdjustStockForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	public static final int 	ORDER_LIMIT 	= 5;
	
	private AdjustStockBean 			adjustStockBean;
	private List<AdjustStockBean> 		adjustStockList;
	private boolean						chk;
	private boolean						limitAdjustHistoryFlag;
	private String						seqTemp;
	
	public AdjustStockForm(){
		this.adjustStockBean			= new AdjustStockBean();
		this.adjustStockList			= new ArrayList<AdjustStockBean>();
		this.chk						= false;
		this.limitAdjustHistoryFlag		= false;
		this.seqTemp					= "0";
	}

	public AdjustStockBean getAdjustStockBean() {
		return adjustStockBean;
	}

	public void setAdjustStockBean(AdjustStockBean adjustStockBean) {
		this.adjustStockBean = adjustStockBean;
	}

	public List<AdjustStockBean> getAdjustStockList() {
		return adjustStockList;
	}

	public void setAdjustStockList(List<AdjustStockBean> adjustStockList) {
		this.adjustStockList = adjustStockList;
	}

	public boolean isChk() {
		return chk;
	}

	public void setChk(boolean chk) {
		this.chk = chk;
	}

	public boolean isLimitAdjustHistoryFlag() {
		return limitAdjustHistoryFlag;
	}

	public void setLimitAdjustHistoryFlag(boolean limitAdjustHistoryFlag) {
		this.limitAdjustHistoryFlag = limitAdjustHistoryFlag;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}
}
