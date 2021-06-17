package consulting.baxter.holidaybooking.controller;

import consulting.baxter.holidaybooking.dao.BookingDao;
import consulting.baxter.holidaybooking.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingDao bookingDao;

    public BookingController(BookingDao bookingDao) {
        this.bookingDao = bookingDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Booking booking) {
        log.info("Creating booking: {}", booking);
        bookingDao.save(booking);
    }

    //todo consider specific REST model to avoid @JsonIgnore on collection references?
    // ... would also make relationship handling easier (ie don't need to send whole ID...)
    @GetMapping
    public List<Booking> get() {
        log.info("Getting bookings");
        final List<Booking> bookings = bookingDao.findAll();
        log.info("Got bookings {}", bookings.toArray());
        return bookings;
    }
}
