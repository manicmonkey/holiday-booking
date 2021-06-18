package consulting.baxter.holidaybooking.rest.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.joda.money.Money;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AvailableDay {
    LocalDate date;
    Money rate;
}
