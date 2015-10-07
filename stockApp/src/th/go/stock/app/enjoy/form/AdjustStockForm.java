package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.AdjustStockBean;

public class AdjustStockForm {
	
	public static final String NEW 	= "NEW";
	public static final String UPD 	= "UPD";
	public static final String DEL 	= "DEL";
	
	private AdjustStockBean 			adjustStockBean;
	private List<AdjustStockBean> 		adjustHistoryListList;
	private boolean						chk;
	
	public AdjustStockForm(){
		this.adjustStockBean		= new AdjustStockBean();
		this.adjustHistoryListList		= new ArrayList<AdjustStockBean>();
		this.chk							= false;
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
}
