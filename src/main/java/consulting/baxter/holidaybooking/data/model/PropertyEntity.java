package consulting.baxter.holidaybooking.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "properties")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class PropertyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @Getter
    @Setter
    private String address;

    @Column(nullable = false)
    @Getter
    @Setter
    private EmbeddableGeoLocation location;

    @OneToMany(mappedBy = "property")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<BookingEntity> bookings;

    public PropertyEntity(String name, String address, EmbeddableGeoLocation location) {
        this.name = name;
        this.address = address;
        this.location = location;
    }
}
