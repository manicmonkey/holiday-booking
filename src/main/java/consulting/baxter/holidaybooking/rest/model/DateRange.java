package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.EmbeddableDateRange;
import lombok.Value;

import java.time.LocalDate;

@Value
public class DateRange {
    LocalDate from;
    LocalDate to;

    public static DateRange from(EmbeddableDateRange dateRange) {
        return new DateRange(dateRange.getStartDate(), dateRange.getEndDate());
    }

    public EmbeddableDateRange toEmbeddable() {
        return new EmbeddableDateRange(from, to);
    }
}
