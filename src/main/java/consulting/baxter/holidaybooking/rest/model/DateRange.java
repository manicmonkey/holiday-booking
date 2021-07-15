package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.EmbeddableDateRange;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class DateRange {
    LocalDate from;
    LocalDate to;

    static DateRange from(EmbeddableDateRange dateRange) {
        return new DateRange(dateRange.getStartDate(), dateRange.getEndDate());
    }

    public EmbeddableDateRange toEmbeddable() {
        return new EmbeddableDateRange(from, to);
    }
}
