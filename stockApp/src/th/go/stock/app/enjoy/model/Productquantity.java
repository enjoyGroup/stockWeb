package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the productquantity database table.
 * 
 */
@Entity
@NamedQuery(name="Productquantity.findAll", query="SELECT p FROM Productquantity p")
public class Productquantity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductquantityPK id;

	private BigDecimal quantity;

	public Productquantity() {
	}

	public ProductquantityPK getId() {
		return this.id;
	}

	public void setId(ProductquantityPK id) {
		this.id = id;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

}