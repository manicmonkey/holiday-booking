package consulting.baxter.holidaybooking.rest.model;

import consulting.baxter.holidaybooking.data.model.BookingEntity;
import lombok.Value;

@Value
public class Booking {
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
