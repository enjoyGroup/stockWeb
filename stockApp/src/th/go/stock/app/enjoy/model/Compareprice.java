package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the compareprice database table.
 * 
 */
@Entity
@NamedQuery(name="Compareprice.findAll", query="SELECT c FROM Compareprice c")
public class Compareprice implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComparepricePK id;

	private BigDecimal price;

	private BigDecimal quantity;

	private int vendorCode;

	public Compareprice() {
	}

	public ComparepricePK getId() {
		return this.id;
	}

	public void setId(ComparepricePK id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public int getVendorCode() {
		return this.vendorCode;
	}

	public void setVendorCode(int vendorCode) {
		this.vendorCode = vendorCode;
	}

}