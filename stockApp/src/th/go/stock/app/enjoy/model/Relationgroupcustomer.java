package th.go.stock.app.enjoy.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the relationgroupcustomer database table.
 * 
 */
@Entity
@NamedQuery(name="Relationgroupcustomer.findAll", query="SELECT r FROM Relationgroupcustomer r")
public class Relationgroupcustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int cusGroupCode;

	private String cusGroupName;

	private String cusGroupStatus;

	private int groupSalePrice;

	public Relationgroupcustomer() {
	}

	public int getCusGroupCode() {
		return this.cusGroupCode;
	}

	public void setCusGroupCode(int cusGroupCode) {
		this.cusGroupCode = cusGroupCode;
	}

	public String getCusGroupName() {
		return this.cusGroupName;
	}

	public void setCusGroupName(String cusGroupName) {
		this.cusGroupName = cusGroupName;
	}

	public String getCusGroupStatus() {
		return this.cusGroupStatus;
	}

	public void setCusGroupStatus(String cusGroupStatus) {
		this.cusGroupStatus = cusGroupStatus;
	}

	public int getGroupSalePrice() {
		return this.groupSalePrice;
	}

	public void setGroupSalePrice(int groupSalePrice) {
		this.groupSalePrice = groupSalePrice;
	}

}