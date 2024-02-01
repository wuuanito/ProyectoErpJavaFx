package MapeoHibernate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "invoice_items", schema = "main", catalog = "")
public class InvoiceItemsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "InvoiceLineId")
    private int invoiceLineId;
    @Basic
    @Column(name = "InvoiceId")
    private int invoiceId;
    @Basic
    @Column(name = "TrackId")
    private int trackId;
    @Basic
    @Column(name = "UnitPrice")
    private Date unitPrice;
    @Basic
    @Column(name = "Quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "InvoiceId", referencedColumnName = "InvoiceId", nullable = false)
    private InvoicesEntity invoicesByInvoiceId;
    @ManyToOne
    @JoinColumn(name = "TrackId", referencedColumnName = "TrackId", nullable = false)
    private TracksEntity tracksByTrackId;

    public int getInvoiceLineId() {
        return invoiceLineId;
    }

    public void setInvoiceLineId(int invoiceLineId) {
        this.invoiceLineId = invoiceLineId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public Date getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Date unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceItemsEntity that = (InvoiceItemsEntity) o;
        return invoiceLineId == that.invoiceLineId && invoiceId == that.invoiceId && trackId == that.trackId && quantity == that.quantity && Objects.equals(unitPrice, that.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceLineId, invoiceId, trackId, unitPrice, quantity);
    }

    public InvoicesEntity getInvoicesByInvoiceId() {
        return invoicesByInvoiceId;
    }

    public void setInvoicesByInvoiceId(InvoicesEntity invoicesByInvoiceId) {
        this.invoicesByInvoiceId = invoicesByInvoiceId;
    }

    public TracksEntity getTracksByTrackId() {
        return tracksByTrackId;
    }

    public void setTracksByTrackId(TracksEntity tracksByTrackId) {
        this.tracksByTrackId = tracksByTrackId;
    }
}
