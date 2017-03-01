package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the invoicecashdetail database table.
 * 
 */
@Entity
@NamedQuery(name="Invoicecashdetail.findAll", query="SELECT i FROM Invoicecashdetail i")
public class Invoicecashdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InvoicecashdetailPK id;

	private BigDecimal discount;

	private BigDecimal price;

	private BigDecimal pricePerUnit;

	private int productCode;

	private BigDecimal quantity;

	public Invoicecashdetail() {
	}

	public InvoicecashdetailPK getId() {
		return this.id;
	}

	public void setId(InvoicecashdetailPK id) {
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

	public int getProductCode() {
		return this.productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

}
