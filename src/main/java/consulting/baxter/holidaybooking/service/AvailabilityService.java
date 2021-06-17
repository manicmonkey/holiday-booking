package consulting.baxter.holidaybooking.service;

import consulting.baxter.holidaybooking.dao.BookingDao;
import consulting.baxter.holidaybooking.model.Booking;
import consulting.baxter.holidaybooking.model.Property;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AvailabilityService {
    private final BookingDao bookingDao;

    public AvailabilityService(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    //todo consider wrapping these parameters into an object which gives more flexibilty on the predicates (ie including multiple properties)
    public List<LocalDate> getAvailableDates(Property property, LocalDate start, LocalDate end) {
        final List<Booking> bookings = bookingDao.findBookingsInDateRange(start, end).stream().filter(booking -> booking.getProperty().equals(property)).collect(Collectors.toList());
        final Set<LocalDate> unavailableDates = bookings.stream().flatMap(booking -> booking.getDateRange().getStartDate().datesUntil(booking.getDateRange().getEndDate())).collect(Collectors.toSet());
        return start.datesUntil(end).filter(date -> !unavailableDates.contains(date)).collect(Collectors.toList());
    }
}
