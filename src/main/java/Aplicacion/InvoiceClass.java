package Aplicacion;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "invoice", schema = "chinook", catalog = "")
public class InvoiceClass {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "InvoiceId")
    private int invoiceId;
    @Basic
    @Column(name = "CustomerId")
    private int customerId;
    @Basic
    @Column(name = "InvoiceDate")
    private Timestamp invoiceDate;
    @Basic
    @Column(name = "BillingAddress")
    private String billingAddress;
    @Basic
    @Column(name = "BillingCity")
    private String billingCity;
    @Basic
    @Column(name = "BillingState")
    private String billingState;
    @Basic
    @Column(name = "BillingCountry")
    private String billingCountry;
    @Basic
    @Column(name = "BillingPostalCode")
    private String billingPostalCode;
    @Basic
    @Column(name = "Total")
    private BigDecimal total;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Timestamp invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingPostalCode() {
        return billingPostalCode;
    }

    public void setBillingPostalCode(String billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InvoiceClass that = (InvoiceClass) o;

        if (invoiceId != that.invoiceId) return false;
        if (customerId != that.customerId) return false;
        if (invoiceDate != null ? !invoiceDate.equals(that.invoiceDate) : that.invoiceDate != null) return false;
        if (billingAddress != null ? !billingAddress.equals(that.billingAddress) : that.billingAddress != null)
            return false;
        if (billingCity != null ? !billingCity.equals(that.billingCity) : that.billingCity != null) return false;
        if (billingState != null ? !billingState.equals(that.billingState) : that.billingState != null) return false;
        if (billingCountry != null ? !billingCountry.equals(that.billingCountry) : that.billingCountry != null)
            return false;
        if (billingPostalCode != null ? !billingPostalCode.equals(that.billingPostalCode) : that.billingPostalCode != null)
            return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = invoiceId;
        result = 31 * result + customerId;
        result = 31 * result + (invoiceDate != null ? invoiceDate.hashCode() : 0);
        result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
        result = 31 * result + (billingCity != null ? billingCity.hashCode() : 0);
        result = 31 * result + (billingState != null ? billingState.hashCode() : 0);
        result = 31 * result + (billingCountry != null ? billingCountry.hashCode() : 0);
        result = 31 * result + (billingPostalCode != null ? billingPostalCode.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }
}
