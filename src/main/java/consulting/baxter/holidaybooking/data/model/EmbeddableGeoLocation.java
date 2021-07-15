package consulting.baxter.holidaybooking.data.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED) //for JPA
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class EmbeddableGeoLocation {
    @Column(nullable = false, precision = 6, scale = 4)
    private BigDecimal longitude;
    @Column(nullable = false, precision = 6, scale = 4)
    private BigDecimal latitude;
}

