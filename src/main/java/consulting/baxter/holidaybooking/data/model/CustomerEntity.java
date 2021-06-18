package consulting.baxter.holidaybooking.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor //for JPA
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @OneToMany(cascade = ALL, mappedBy = "customer")
    private Set<BookingEntity> bookings;

    // no-args constructor required by JPA
//    protected CustomerEntity() {
//    }
//
//    public CustomerEntity(String name, String address) {
//        this.name = name;
//        this.address = address;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public CustomerEntity setEmail(String email) {
//        this.email = email;
//        return this;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public Set<BookingEntity> getBookings() {
//        return bookings;
//    }
//
//    public void setBookings(Set<BookingEntity> bookings) {
//        this.bookings = bookings;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof CustomerEntity)) return false;
//        CustomerEntity customer = (CustomerEntity) o;
//        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(address, customer.address);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, name, address);
//    }
//
//    @Override
//    public String toString() {
//        return "Customer{" +
//            "id=" + id +
//            ", name='" + name + '\'' +
//            ", address='" + address + '\'' +
//            '}';
//    }
}
