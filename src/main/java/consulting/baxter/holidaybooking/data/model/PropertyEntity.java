package consulting.baxter.holidaybooking.data.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "properties")
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "property")
    private List<BookingEntity> bookings;

    // no-args constructor required by JPA
    protected PropertyEntity() {
    }

    public PropertyEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public PropertyEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PropertyEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyEntity)) return false;
        PropertyEntity property = (PropertyEntity) o;
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
