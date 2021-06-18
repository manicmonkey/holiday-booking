package consulting.baxter.holidaybooking.data;

import consulting.baxter.holidaybooking.data.model.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao extends JpaRepository<BookingEntity, Long> {
    @Query("FROM BookingEntity b WHERE b.dateRange.startDate BETWEEN :start AND :end OR b.dateRange.endDate BETWEEN :start AND :end")
    List<BookingEntity> findBookingsInDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
