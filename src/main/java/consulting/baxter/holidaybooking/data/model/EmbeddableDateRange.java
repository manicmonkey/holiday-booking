package consulting.baxter.holidaybooking.data.model;

import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED) //for JPA
@Getter
@ToString
@EqualsAndHashCode
public class EmbeddableDateRange {
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    public EmbeddableDateRange(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "Start Date cannot be null");
        Assert.notNull(endDate, "End Date cannot be null");
        Assert.isTrue(startDate.isBefore(endDate), "Start Date must be before End Date");
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
