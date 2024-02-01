package MapeoHibernate;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "employees", schema = "main", catalog = "")
public class EmployeesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EmployeeId")
    private int employeeId;
    @Basic
    @Column(name = "LastName")
    private Object lastName;
    @Basic
    @Column(name = "FirstName")
    private Object firstName;
    @Basic
    @Column(name = "Title")
    private Object title;
    @Basic
    @Column(name = "ReportsTo")
    private Integer reportsTo;
    @Basic
    @Column(name = "BirthDate")
    private Object birthDate;
    @Basic
    @Column(name = "HireDate")
    private Object hireDate;
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
    @OneToMany(mappedBy = "employeesBySupportRepId")
    private Collection<CustomersEntity> customersByEmployeeId;
    @ManyToOne
    @JoinColumn(name = "ReportsTo", referencedColumnName = "EmployeeId")
    private EmployeesEntity employeesByReportsTo;
    @OneToMany(mappedBy = "employeesByReportsTo")
    private Collection<EmployeesEntity> employeesByEmployeeId;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Integer getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Integer reportsTo) {
        this.reportsTo = reportsTo;
    }

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public Object getHireDate() {
        return hireDate;
    }

    public void setHireDate(Object hireDate) {
        this.hireDate = hireDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeesEntity that = (EmployeesEntity) o;
        return employeeId == that.employeeId && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(title, that.title) && Objects.equals(reportsTo, that.reportsTo) && Objects.equals(birthDate, that.birthDate) && Objects.equals(hireDate, that.hireDate) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(country, that.country) && Objects.equals(postalCode, that.postalCode) && Objects.equals(phone, that.phone) && Objects.equals(fax, that.fax) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, lastName, firstName, title, reportsTo, birthDate, hireDate, address, city, state, country, postalCode, phone, fax, email);
    }

    public Collection<CustomersEntity> getCustomersByEmployeeId() {
        return customersByEmployeeId;
    }

    public void setCustomersByEmployeeId(Collection<CustomersEntity> customersByEmployeeId) {
        this.customersByEmployeeId = customersByEmployeeId;
    }

    public EmployeesEntity getEmployeesByReportsTo() {
        return employeesByReportsTo;
    }

    public void setEmployeesByReportsTo(EmployeesEntity employeesByReportsTo) {
        this.employeesByReportsTo = employeesByReportsTo;
    }

    public Collection<EmployeesEntity> getEmployeesByEmployeeId() {
        return employeesByEmployeeId;
    }

    public void setEmployeesByEmployeeId(Collection<EmployeesEntity> employeesByEmployeeId) {
        this.employeesByEmployeeId = employeesByEmployeeId;
    }
}
