package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the invoicecashmaster database table.
 * 
 */
@Entity
@NamedQuery(name="Invoicecashmaster.findAll", query="SELECT i FROM Invoicecashmaster i")
public class Invoicecashmaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InvoicecashmasterPK id;

	private String branchName;

	private String cusCode;

	private String invoiceCredit;

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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_dt")
	private Date createdDt;

	public Invoicecashmaster() {
	}

	public InvoicecashmasterPK getId() {
		return this.id;
	}

	public void setId(InvoicecashmasterPK id) {
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

	public String getInvoiceCredit() {
		return this.invoiceCredit;
	}

	public void setInvoiceCredit(String invoiceCredit) {
		this.invoiceCredit = invoiceCredit;
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

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

}