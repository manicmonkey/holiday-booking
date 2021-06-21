package consulting.baxter.holidaybooking.service;

import consulting.baxter.holidaybooking.data.BookingDao;
import consulting.baxter.holidaybooking.data.model.BookingEntity;
import consulting.baxter.holidaybooking.data.model.PropertyEntity;
import consulting.baxter.holidaybooking.rest.model.AvailableDay;
import io.vavr.control.Either;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static consulting.baxter.holidaybooking.service.AvailabilityService.Failure.*;

@Component
public class AvailabilityService {
    private final RateService rateService;
    private final BookingDao bookingDao;

    public AvailabilityService(RateService rateService, BookingDao bookingDao) {
        this.rateService = rateService;
        this.bookingDao = bookingDao;
    }

    //todo consider wrapping these parameters into an object which gives more flexibility on the predicates (ie including multiple properties)
    public Either<Failure, List<AvailableDay>> getAvailability(PropertyEntity property, LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now()))
            return Either.left(START_DATE_IN_PAST);

        if (start.isAfter(end))
            return Either.left(START_DATE_AFTER_END);

        if (start.plus(Period.ofMonths(1)).isBefore(end))
            return Either.left(DATE_RANGE_TOO_BIG);

        final List<BookingEntity> bookings = bookingDao
            .findBookingsInDateRange(start, end).stream()
            .filter(booking -> booking.getProperty().equals(property))
            .collect(Collectors.toList());
        final Set<LocalDate> unavailableDates = bookings.stream()
            .flatMap(booking ->
                booking.getStartDate()
                    .datesUntil(booking.getEndDate()))
            .collect(Collectors.toSet());

        final List<LocalDate> availableDates = start.datesUntil(end).filter(date -> !unavailableDates.contains(date)).collect(Collectors.toList());

        final List<AvailableDay> availability = availableDates.stream().map(date -> {
            val rate = rateService.rateForDate(date);
            return new AvailableDay(date, rate);
        }).collect(Collectors.toList());

        return Either.right(availability);
    }

    public enum Failure {
        DATE_RANGE_TOO_BIG,
        START_DATE_AFTER_END,
        START_DATE_IN_PAST,
    }
}
