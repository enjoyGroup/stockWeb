package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the invoicecreditmaster database table.
 * 
 */
@Entity
@NamedQuery(name="Invoicecreditmaster.findAll", query="SELECT i FROM Invoicecreditmaster i")
public class Invoicecreditmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InvoicecreditmasterPK id;

	private String branchName;

	private String cusCode;

	private String invoiceCash;

	private String invoiceDate;

	private BigDecimal invoiceDeposit;

	private BigDecimal invoicediscount;

	private BigDecimal invoicePrice;

	private String invoiceStatus;

	private BigDecimal invoiceTotal;

	private String invoiceType;

	private BigDecimal invoiceVat;

	private String remark;

	private BigDecimal saleCommission;

	private int saleUniqueId;

	private int userUniqueId;

	public Invoicecreditmaster() {
	}

	public InvoicecreditmasterPK getId() {
		return this.id;
	}

	public void setId(InvoicecreditmasterPK id) {
		this.id = id;
	}

	public String getBranchName() {
		return this.branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCusCode() {
		return this.cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}

	public String getInvoiceCash() {
		return this.invoiceCash;
	}

	public void setInvoiceCash(String invoiceCash) {
		this.invoiceCash = invoiceCash;
	}

	public String getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getInvoiceDeposit() {
		return this.invoiceDeposit;
	}

	public void setInvoiceDeposit(BigDecimal invoiceDeposit) {
		this.invoiceDeposit = invoiceDeposit;
	}

	public BigDecimal getInvoicediscount() {
		return this.invoicediscount;
	}

	public void setInvoicediscount(BigDecimal invoicediscount) {
		this.invoicediscount = invoicediscount;
	}

	public BigDecimal getInvoicePrice() {
		return this.invoicePrice;
	}

	public void setInvoicePrice(BigDecimal invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public String getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public BigDecimal getInvoiceTotal() {
		return this.invoiceTotal;
	}

	public void setInvoiceTotal(BigDecimal invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}

	public String getInvoiceType() {
		return this.invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getInvoiceVat() {
		return this.invoiceVat;
	}

	public void setInvoiceVat(BigDecimal invoiceVat) {
		this.invoiceVat = invoiceVat;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getSaleCommission() {
		return this.saleCommission;
	}

	public void setSaleCommission(BigDecimal saleCommission) {
		this.saleCommission = saleCommission;
	}

	public int getSaleUniqueId() {
		return this.saleUniqueId;
	}

	public void setSaleUniqueId(int saleUniqueId) {
		this.saleUniqueId = saleUniqueId;
	}

	public int getUserUniqueId() {
		return this.userUniqueId;
	}

	public void setUserUniqueId(int userUniqueId) {
		this.userUniqueId = userUniqueId;
	}

}