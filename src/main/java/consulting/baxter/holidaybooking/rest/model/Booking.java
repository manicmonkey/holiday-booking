package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.BookingEntity;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Booking {
    //todo reference number
    Property property;
    Customer customer;
    DateRange dateRange;

    public static Booking from(BookingEntity bookingEntity) {
        return new Booking(
            Property.from(bookingEntity.getProperty()),
            Customer.from(bookingEntity.getCustomer()),
            DateRange.from(bookingEntity.getDateRange()));
    }
}
