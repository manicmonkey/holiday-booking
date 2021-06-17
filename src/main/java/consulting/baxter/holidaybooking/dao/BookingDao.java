package consulting.baxter.holidaybooking.dao;

import consulting.baxter.holidaybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao extends JpaRepository<Booking, Long> {
    @Query("FROM Booking b WHERE b.dateRange.startDate BETWEEN :start AND :end OR b.dateRange.endDate BETWEEN :start AND :end")
    List<Booking> findBookingsInDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
