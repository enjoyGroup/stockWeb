package th.go.stock.app.enjoy.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the invoicecashdetail database table.
 * 
 */
@Embeddable
public class InvoicecashdetailPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String invoiceCode;

	private int seq;

	public InvoicecashdetailPK() {
	}
	public String getInvoiceCode() {
		return this.invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public int getSeq() {
		return this.seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InvoicecashdetailPK)) {
			return false;
		}
		InvoicecashdetailPK castOther = (InvoicecashdetailPK)other;
		return 
			this.invoiceCode.equals(castOther.invoiceCode)
			&& (this.seq == castOther.seq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.invoiceCode.hashCode();
		hash = hash * prime + this.seq;
		
		return hash;
	}
}