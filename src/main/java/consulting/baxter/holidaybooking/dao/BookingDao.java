package consulting.baxter.holidaybooking.dao;

import consulting.baxter.holidaybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao extends JpaRepository<Booking, Long> {
    //todo inclusive start should use keyword between?
    //todo this is wrong as it'll only find bookings which are enclosed, not which are overlapping
    //JQL will probably be easier - startDate between or endDate between
    List<Booking> findByDateRange_StartDateAfterAndDateRange_EndDateBefore(LocalDate start, LocalDate end);
}
