package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the invoicecreditdetail database table.
 * 
 */
@Entity
@NamedQuery(name="Invoicecreditdetail.findAll", query="SELECT i FROM Invoicecreditdetail i")
public class Invoicecreditdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InvoicecreditdetailPK id;

	private BigDecimal discount;

	private BigDecimal price;

	private BigDecimal pricePerUnit;

	private String productCode;

	private BigDecimal quantity;

	public Invoicecreditdetail() {
	}

	public InvoicecreditdetailPK getId() {
		return this.id;
	}

	public void setId(InvoicecreditdetailPK id) {
		this.id = id;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPricePerUnit() {
		return this.pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public String getProductCode() {
		return this.productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

}