package th.go.stock.app.enjoy.form;

import java.util.ArrayList;
import java.util.List;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.RelationGroupCustomerBean;

public class RelationGroupCustomerForm {
	
	public static final String NEW 	= "NEW";
	public static final String EDIT = "EDIT";
	public static final String DEL 	= "DEL";
	
	private List<ComboBean> 					groupSalePriceCombo;
	private String 								jsonGroupSalePriceCombo;
	private String								titlePage;
	private List<RelationGroupCustomerBean> 	relationGroupCustomerList;
	private String								seqTemp;
	
	public RelationGroupCustomerForm(){
		this.groupSalePriceCombo			= new ArrayList<ComboBean>();
		this.jsonGroupSalePriceCombo		= "";
		this.titlePage						= "";
		this.relationGroupCustomerList		= new ArrayList<RelationGroupCustomerBean>();
		this.seqTemp						= "0";
	}

	public List<ComboBean> getGroupSalePriceCombo() {
		return groupSalePriceCombo;
	}

	public void setGroupSalePriceCombo(List<ComboBean> groupSalePriceCombo) {
		this.groupSalePriceCombo = groupSalePriceCombo;
	}

	public String getTitlePage() {
		return titlePage;
	}

	public void setTitlePage(String titlePage) {
		this.titlePage = titlePage;
	}

	public List<RelationGroupCustomerBean> getRelationGroupCustomerList() {
		return relationGroupCustomerList;
	}

	public void setRelationGroupCustomerList(
			List<RelationGroupCustomerBean> relationGroupCustomerList) {
		this.relationGroupCustomerList = relationGroupCustomerList;
	}

	public String getSeqTemp() {
		return seqTemp;
	}

	public void setSeqTemp(String seqTemp) {
		this.seqTemp = seqTemp;
	}

	public String getJsonGroupSalePriceCombo() {
		return jsonGroupSalePriceCombo;
	}

	public void setJsonGroupSalePriceCombo(String jsonGroupSalePriceCombo) {
		this.jsonGroupSalePriceCombo = jsonGroupSalePriceCombo;
	}
}
