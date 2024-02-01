package MapeoHibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "customers", schema = "main", catalog = "")
public class CustomersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CustomerId")
    private int customerId;
    @Basic
    @Column(name = "FirstName")
    private Object firstName;
    @Basic
    @Column(name = "LastName")
    private Object lastName;
    @Basic
    @Column(name = "Company")
    private Object company;
    @Basic
    @Column(name = "Address")
    private Object address;
    @Basic
    @Column(name = "City")
    private Object city;
    @Basic
    @Column(name = "State")
    private Object state;
    @Basic
    @Column(name = "Country")
    private Object country;
    @Basic
    @Column(name = "PostalCode")
    private Object postalCode;
    @Basic
    @Column(name = "Phone")
    private Object phone;
    @Basic
    @Column(name = "Fax")
    private Object fax;
    @Basic
    @Column(name = "Email")
    private Object email;
    @Basic
    @Column(name = "SupportRepId")
    private Integer supportRepId;
    @ManyToOne
    @JoinColumn(name = "SupportRepId", referencedColumnName = "EmployeeId")
    private EmployeesEntity employeesBySupportRepId;
    @OneToMany(mappedBy = "customersByCustomerId")
    private Collection<InvoicesEntity> invoicesByCustomerId;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getCompany() {
        return company;
    }

    public void setCompany(Object company) {
        this.company = company;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getFax() {
        return fax;
    }

    public void setFax(Object fax) {
        this.fax = fax;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Integer getSupportRepId() {
        return supportRepId;
    }

    public void setSupportRepId(Integer supportRepId) {
        this.supportRepId = supportRepId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomersEntity that = (CustomersEntity) o;
        return customerId == that.customerId && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(company, that.company) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(postalCode, that.postalCode) && Objects.equals(phone, that.phone) && Objects.equals(fax, that.fax) && Objects.equals(email, that.email) && Objects.equals(supportRepId, that.supportRepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, company, address, city, state, country, postalCode, phone, fax, email, supportRepId);
    }

    public EmployeesEntity getEmployeesBySupportRepId() {
        return employeesBySupportRepId;
    }

    public void setEmployeesBySupportRepId(EmployeesEntity employeesBySupportRepId) {
        this.employeesBySupportRepId = employeesBySupportRepId;
    }

    public Collection<InvoicesEntity> getInvoicesByCustomerId() {
        return invoicesByCustomerId;
    }

    public void setInvoicesByCustomerId(Collection<InvoicesEntity> invoicesByCustomerId) {
        this.invoicesByCustomerId = invoicesByCustomerId;
    }
}
