package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the productgroup database table.
 * 
 */
@Entity
@NamedQuery(name="Productgroup.findAll", query="SELECT p FROM Productgroup p")
public class Productgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductgroupPK id;

	private String productGroupName;

	private String productGroupStatus;

	public Productgroup() {
	}

	public ProductgroupPK getId() {
		return this.id;
	}

	public void setId(ProductgroupPK id) {
		this.id = id;
	}

	public String getProductGroupName() {
		return this.productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getProductGroupStatus() {
		return this.productGroupStatus;
	}

	public void setProductGroupStatus(String productGroupStatus) {
		this.productGroupStatus = productGroupStatus;
	}

}