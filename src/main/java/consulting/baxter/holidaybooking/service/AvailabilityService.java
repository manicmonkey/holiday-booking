package consulting.baxter.holidaybooking.service;

import consulting.baxter.holidaybooking.dao.BookingDao;
import consulting.baxter.holidaybooking.model.Booking;
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

    //todo need to retain concept of property
    public List<LocalDate> getAvailableDates(LocalDate start, LocalDate end) {
        final List<Booking> bookings = bookingDao.findByDateRange_StartDateAfterAndDateRange_EndDateBefore(start, end);
        final Set<LocalDate> unavailableDates = bookings.stream().flatMap(booking -> booking.getDateRange().getStartDate().datesUntil(booking.getDateRange().getEndDate())).collect(Collectors.toSet());
        return start.datesUntil(end).filter(date -> !unavailableDates.contains(date)).collect(Collectors.toList());
    }
}
