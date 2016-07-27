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

	@EmbeddedId
	private RelationgroupcustomerPK id;

	private String cusGroupName;

	private String cusGroupStatus;

	private int groupSalePrice;

	public Relationgroupcustomer() {
	}

	public RelationgroupcustomerPK getId() {
		return this.id;
	}

	public void setId(RelationgroupcustomerPK id) {
		this.id = id;
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