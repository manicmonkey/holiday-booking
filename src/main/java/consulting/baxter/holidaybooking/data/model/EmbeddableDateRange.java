package consulting.baxter.holidaybooking.data.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED) //for JPA
public class EmbeddableDateRange {
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate; //todo make sure this is after startDate!

    public EmbeddableDateRange(LocalDate startDate, LocalDate endDate) {
        Assert.notNull(startDate, "Start Date cannot be null");
        Assert.notNull(endDate, "End Date cannot be null");
        Assert.isTrue(startDate.isBefore(endDate), "Start Date must be before End Date");
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmbeddableDateRange)) return false;
        EmbeddableDateRange dateRange = (EmbeddableDateRange) o;
        return Objects.equals(startDate, dateRange.startDate) && Objects.equals(endDate, dateRange.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }

    @Override
    public String toString() {
        return "DateRange{" +
            "startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
