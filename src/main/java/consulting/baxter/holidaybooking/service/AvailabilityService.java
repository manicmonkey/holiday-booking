package consulting.baxter.holidaybooking.service;

import consulting.baxter.holidaybooking.dao.BookingDao;
import consulting.baxter.holidaybooking.model.Booking;
import consulting.baxter.holidaybooking.model.Property;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static consulting.baxter.holidaybooking.service.AvailabilityService.AvailabilityFailure.DATE_RANGE_TOO_BIG;
import static consulting.baxter.holidaybooking.service.AvailabilityService.AvailabilityFailure.START_DATE_AFTER_END;

@Component
public class AvailabilityService {
    private final BookingDao bookingDao;

    public AvailabilityService(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    //todo consider wrapping these parameters into an object which gives more flexibility on the predicates (ie including multiple properties)
    public Either<AvailabilityFailure, List<LocalDate>> getAvailableDates(Property property, LocalDate start, LocalDate end) {
        if (start.isAfter(end))
            return Either.left(START_DATE_AFTER_END);

        if (start.plus(Period.ofMonths(1)).isBefore(end))
            return Either.left(DATE_RANGE_TOO_BIG);

        final List<Booking> bookings = bookingDao.findBookingsInDateRange(start, end).stream().filter(booking -> booking.getProperty().equals(property)).collect(Collectors.toList());
        final Set<LocalDate> unavailableDates = bookings.stream().flatMap(booking -> booking.getDateRange().getStartDate().datesUntil(booking.getDateRange().getEndDate())).collect(Collectors.toSet());
        final List<LocalDate> availableDates = start.datesUntil(end).filter(date -> !unavailableDates.contains(date)).collect(Collectors.toList());
        return Either.right(availableDates);
    }

    public enum AvailabilityFailure {
        DATE_RANGE_TOO_BIG,
        START_DATE_AFTER_END
    }
}
