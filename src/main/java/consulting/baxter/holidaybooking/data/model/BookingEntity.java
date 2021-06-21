package consulting.baxter.holidaybooking.data.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private EmbeddableDateRange dateRange;

    @ManyToOne(optional = false, cascade = ALL)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private CustomerEntity customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    // no-args constructor required by JPA
    protected BookingEntity() {
    }

    public BookingEntity(EmbeddableDateRange dateRange, CustomerEntity customer, PropertyEntity property) {
        this.dateRange = dateRange;
        this.customer = customer;
        this.property = property;
    }

    public EmbeddableDateRange getDateRange() {
        return dateRange;
    }

    public BookingEntity setDateRange(EmbeddableDateRange dateRange) {
        this.dateRange = dateRange;
        return this;
    }

    public LocalDate getStartDate() {
        return dateRange.getStartDate();
    }

    public LocalDate getEndDate() {
        return dateRange.getEndDate();
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public BookingEntity setCustomer(CustomerEntity customer) {
        this.customer = customer;
        return this;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public BookingEntity setProperty(PropertyEntity property) {
        this.property = property;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingEntity)) return false;
        BookingEntity booking = (BookingEntity) o;
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
