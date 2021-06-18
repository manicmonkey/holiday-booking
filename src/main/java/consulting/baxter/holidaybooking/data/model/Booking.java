package consulting.baxter.holidaybooking.data.model;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DateRange dateRange;

    @ManyToOne(optional = false, cascade = ALL)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    // no-args constructor required by JPA
    protected Booking() {
    }

    public Booking(DateRange dateRange, Customer customer, Property property) {
        this.dateRange = dateRange;
        this.customer = customer;
        this.property = property;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public Booking setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Booking setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public Property getProperty() {
        return property;
    }

    public Booking setProperty(Property property) {
        this.property = property;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(dateRange, booking.dateRange) && Objects.equals(customer, booking.customer) && Objects.equals(property, booking.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateRange, customer, property);
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + id +
            ", dateRange=" + dateRange +
            ", customer=" + customer +
            ", property=" + property +
            '}';
    }
}
