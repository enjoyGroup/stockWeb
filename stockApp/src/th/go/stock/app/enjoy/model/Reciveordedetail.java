package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the reciveordedetail database table.
 * 
 */
@Entity
@NamedQuery(name="Reciveordedetail.findAll", query="SELECT r FROM Reciveordedetail r")
public class Reciveordedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReciveordedetailPK id;

	private BigDecimal costPrice;

	private BigDecimal discountRate;

	private BigDecimal price;

	private String productCode;

	private BigDecimal quantity;

	public Reciveordedetail() {
	}

	public ReciveordedetailPK getId() {
		return this.id;
	}

	public void setId(ReciveordedetailPK id) {
		this.id = id;
	}

	public BigDecimal getCostPrice() {
		return this.costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getDiscountRate() {
		return this.discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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