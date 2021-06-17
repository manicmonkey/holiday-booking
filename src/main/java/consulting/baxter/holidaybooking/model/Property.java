package consulting.baxter.holidaybooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "properties")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "property")
    private List<Booking> bookings;

    // no-args constructor required by JPA
    protected Property() {
    }

    public Property(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Property setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Property setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id) && Objects.equals(name, property.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}
