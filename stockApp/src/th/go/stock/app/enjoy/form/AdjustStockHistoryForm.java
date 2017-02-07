package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.AdjustStockBean;
import th.go.stock.app.enjoy.bean.ComboBean;

public class AdjustStockHistoryForm {
	
	public static final String 	NEW 			= "NEW";
	public static final String 	UPD 			= "UPD";
	public static final String 	DEL 			= "DEL";
	public static final int 	ORDER_LIMIT 	= 5;
	
	private AdjustStockBean 			adjustStockBean;
	private List<AdjustStockBean> 		adjustHistoryListList;
	private boolean						chk;
	private boolean						limitAdjustHistoryFlag;
	
	public AdjustStockHistoryForm(){
		this.adjustStockBean				= new AdjustStockBean();
		this.adjustHistoryListList			= new ArrayList<AdjustStockBean>();
		this.chk							= false;
		this.limitAdjustHistoryFlag			= false;
	}

	public AdjustStockBean getAdjustStockBean() {
		return adjustStockBean;
	}

	public void setAdjustStockBean(AdjustStockBean adjustStockBean) {
		this.adjustStockBean = adjustStockBean;
	}

	public List<AdjustStockBean> getAdjustHistoryListList() {
		return adjustHistoryListList;
	}

	public void setAdjustHistoryListList(List<AdjustStockBean> adjustHistoryListList) {
		this.adjustHistoryListList = adjustHistoryListList;
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
}
