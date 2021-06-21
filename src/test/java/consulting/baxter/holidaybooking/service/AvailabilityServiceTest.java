package consulting.baxter.holidaybooking.service;

import consulting.baxter.holidaybooking.data.BookingDao;
import consulting.baxter.holidaybooking.data.model.BookingEntity;
import consulting.baxter.holidaybooking.data.model.EmbeddableDateRange;
import consulting.baxter.holidaybooking.data.model.PropertyEntity;
import consulting.baxter.holidaybooking.rest.model.AvailableDay;
import lombok.val;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AvailabilityServiceTest {

    private static final PropertyEntity dummyProperty = new PropertyEntity("dummyProperty");

    @Test
    void checksDateNotInPast() {
        //given
        val today = LocalDate.now();
        val yesterday = today.minusDays(1);
        val availabilityService = new AvailabilityService(null, null);

        //when
        val result = availabilityService.getAvailability(dummyProperty, yesterday, today);

        //then
        assertTrue(result.isLeft(), "Expected failure");
        assertEquals(result.getLeft(), AvailabilityService.Failure.START_DATE_IN_PAST);
    }

    @Test
    void checksStartDateBeforeEndDate() {
        //given
        val today = LocalDate.now();
        val tomorrow = today.plusDays(1);
        val availabilityService = new AvailabilityService(null, null);

        //when
        val result = availabilityService.getAvailability(dummyProperty, tomorrow, today);

        //then
        assertTrue(result.isLeft(), "Expected failure");
        assertEquals(result.getLeft(), AvailabilityService.Failure.START_DATE_AFTER_END);
    }

    @Test
    void checksDateRangeNotTooBig() {
        //given
        val today = LocalDate.now();
        val future = today.plusMonths(1).plusDays(1);
        val availabilityService = new AvailabilityService(null, null);

        //when
        val result = availabilityService.getAvailability(dummyProperty, today, future);

        //then
        assertTrue(result.isLeft(), "Expected failure");
        assertEquals(result.getLeft(), AvailabilityService.Failure.DATE_RANGE_TOO_BIG);
    }

    @Test
    void findsFullAvailability() {
        //given
        val today = LocalDate.now();
        val tomorrow = today.plusDays(1);
        val rate = Money.of(CurrencyUnit.AUD, 250);

        val rateService = mock(RateService.class);
        val bookingDao = mock(BookingDao.class);
        when(bookingDao.findBookingsInDateRange(today, tomorrow)).thenReturn(Collections.emptyList());
        when(rateService.rateForDate(today)).thenReturn(rate);

        val availabilityService = new AvailabilityService(rateService, bookingDao);

        //when
        val result = availabilityService.getAvailability(dummyProperty, today, tomorrow);

        //then
        assertTrue(result.isRight(), "Expected success");
        assertEquals(List.of(new AvailableDay(today, rate)), result.get());
    }

    @Test
    void findsLimitedAvailability() {
        //given
        val today = LocalDate.now();
        val future = today.plusDays(3);
        val startOfSingleDayBooking = today.plusDays(1);
        val rate = Money.of(CurrencyUnit.AUD, 250);

        val rateService = mock(RateService.class);
        val bookingDao = mock(BookingDao.class);
        when(bookingDao.findBookingsInDateRange(today, future))
            .thenReturn(List.of(new BookingEntity(new EmbeddableDateRange(startOfSingleDayBooking, startOfSingleDayBooking.plusDays(1)), null, dummyProperty)));
        when(rateService.rateForDate(any())).thenReturn(rate);

        val availabilityService = new AvailabilityService(rateService, bookingDao);

        //when
        val result = availabilityService.getAvailability(dummyProperty, today, future);

        //then
        assertTrue(result.isRight(), "Expected success");
        assertEquals(List.of(new AvailableDay(today, rate), new AvailableDay(today.plusDays(2), rate)), result.get());
    }
}
