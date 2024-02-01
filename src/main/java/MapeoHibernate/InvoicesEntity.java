package MapeoHibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "invoices", schema = "main", catalog = "")
public class InvoicesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "InvoiceId")
    private int invoiceId;
    @Basic
    @Column(name = "CustomerId")
    private int customerId;
    @Basic
    @Column(name = "InvoiceDate")
    private Object invoiceDate;
    @Basic
    @Column(name = "BillingAddress")
    private Object billingAddress;
    @Basic
    @Column(name = "BillingCity")
    private Object billingCity;
    @Basic
    @Column(name = "BillingState")
    private Object billingState;
    @Basic
    @Column(name = "BillingCountry")
    private Object billingCountry;
    @Basic
    @Column(name = "BillingPostalCode")
    private Object billingPostalCode;
    @Basic
    @Column(name = "Total")
    private Date total;
    @OneToMany(mappedBy = "invoicesByInvoiceId")
    private Collection<InvoiceItemsEntity> invoiceItemsByInvoiceId;
    @ManyToOne
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId", nullable = false)
    private CustomersEntity customersByCustomerId;

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

    public Object getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Object invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Object getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Object billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Object getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(Object billingCity) {
        this.billingCity = billingCity;
    }

    public Object getBillingState() {
        return billingState;
    }

    public void setBillingState(Object billingState) {
        this.billingState = billingState;
    }

    public Object getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(Object billingCountry) {
        this.billingCountry = billingCountry;
    }

    public Object getBillingPostalCode() {
        return billingPostalCode;
    }

    public void setBillingPostalCode(Object billingPostalCode) {
        this.billingPostalCode = billingPostalCode;
    }

    public Date getTotal() {
        return total;
    }

    public void setTotal(Date total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoicesEntity that = (InvoicesEntity) o;
        return invoiceId == that.invoiceId && customerId == that.customerId && Objects.equals(invoiceDate, that.invoiceDate) && Objects.equals(billingAddress, that.billingAddress) && Objects.equals(billingCity, that.billingCity) && Objects.equals(billingState, that.billingState) && Objects.equals(billingCountry, that.billingCountry) && Objects.equals(billingPostalCode, that.billingPostalCode) && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, customerId, invoiceDate, billingAddress, billingCity, billingState, billingCountry, billingPostalCode, total);
    }

    public Collection<InvoiceItemsEntity> getInvoiceItemsByInvoiceId() {
        return invoiceItemsByInvoiceId;
    }

    public void setInvoiceItemsByInvoiceId(Collection<InvoiceItemsEntity> invoiceItemsByInvoiceId) {
        this.invoiceItemsByInvoiceId = invoiceItemsByInvoiceId;
    }

    public CustomersEntity getCustomersByCustomerId() {
        return customersByCustomerId;
    }

    public void setCustomersByCustomerId(CustomersEntity customersByCustomerId) {
        this.customersByCustomerId = customersByCustomerId;
    }
}
