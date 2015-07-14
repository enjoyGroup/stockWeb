package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the productdiscount database table.
 * 
 */
@Entity
@NamedQuery(name="Productdiscount.findAll", query="SELECT p FROM Productdiscount p")
public class Productdiscount implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductdiscountPK id;

	private BigDecimal discount;

	private BigDecimal quanDiscount;

	public Productdiscount() {
	}

	public ProductdiscountPK getId() {
		return this.id;
	}

	public void setId(ProductdiscountPK id) {
		this.id = id;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getQuanDiscount() {
		return this.quanDiscount;
	}

	public void setQuanDiscount(BigDecimal quanDiscount) {
		this.quanDiscount = quanDiscount;
	}

}