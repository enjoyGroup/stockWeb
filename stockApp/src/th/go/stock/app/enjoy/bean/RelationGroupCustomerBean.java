package th.go.stock.app.enjoy.bean;

public class RelationGroupCustomerBean {
	
	private String cusGroupCode;
	private String cusGroupName;
	private String groupSalePrice;
	private String cusGroupStatus;
	private String seq;
	private String rowStatus;
	
	public RelationGroupCustomerBean(){
		this.cusGroupCode 		= "";
		this.cusGroupName 		= "";
		this.groupSalePrice 	= "";
		this.cusGroupStatus		= "";
		this.seq 				= "";
		this.rowStatus			= "";
	}

	public String getCusGroupCode() {
		return cusGroupCode;
	}

	public void setCusGroupCode(String cusGroupCode) {
		this.cusGroupCode = cusGroupCode;
	}

	public String getCusGroupName() {
		return cusGroupName;
	}

	public void setCusGroupName(String cusGroupName) {
		this.cusGroupName = cusGroupName;
	}

	public String getGroupSalePrice() {
		return groupSalePrice;
	}

	public void setGroupSalePrice(String groupSalePrice) {
		this.groupSalePrice = groupSalePrice;
	}

	public String getCusGroupStatus() {
		return cusGroupStatus;
	}

	public void setCusGroupStatus(String cusGroupStatus) {
		this.cusGroupStatus = cusGroupStatus;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getRowStatus() {
		return rowStatus;
	}

	public void setRowStatus(String rowStatus) {
		this.rowStatus = rowStatus;
	}
}
