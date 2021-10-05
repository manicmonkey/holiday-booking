package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.data.BookingDao;
import consulting.baxter.holidaybooking.data.CustomerDao;
import consulting.baxter.holidaybooking.data.PropertyDao;
import consulting.baxter.holidaybooking.data.model.BookingEntity;
import consulting.baxter.holidaybooking.rest.model.Booking;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private static final Logger log = LoggerFactory.getLogger(BookingController.class);

    private final BookingDao bookingDao;
    private final CustomerDao customerDao;
    private final PropertyDao propertyDao;

    public BookingController(BookingDao bookingDao, CustomerDao customerDao, PropertyDao propertyDao) {
        this.bookingDao = bookingDao;
        this.customerDao = customerDao;
        this.propertyDao = propertyDao;
    }

    @PostMapping("/{propertyName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@PathVariable String propertyName, @RequestBody Booking booking) throws Exception {
        log.info("Creating booking: {}", booking);
        val maybePropertyEntity = propertyDao.findByName(propertyName);
        //todo grab email from the security context so this can't fail
        val customerEntity = customerDao.findByEmail(booking.getCustomer().getEmail());

        final Function<Booking, URI> toBookingUri = b -> {
            try {
                return new URI("/bookingId");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        };

        return maybePropertyEntity
            .map(propertyEntity -> new BookingEntity(booking.getDateRange().toEmbeddable(), customerEntity, propertyEntity))
            .map(bookingDao::save)
            .map(Booking::from)
            .map(toBookingUri)
            .map(uri -> ResponseEntity.created(uri).build())
            .orElse(ResponseEntity.badRequest().body(Failure.PROPERTY_NOT_FOUND.toString()));
    }

    @GetMapping
    public List<Booking> get() {
        log.info("Getting bookings");
        final List<BookingEntity> bookings = bookingDao.findAll();
        log.info("Got bookings {}", bookings.toArray());
        return bookings.stream().map(Booking::from).collect(Collectors.toList());
    }

    enum Failure {
        PROPERTY_NOT_FOUND
    }
}
