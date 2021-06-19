package consulting.baxter.holidaybooking;

import consulting.baxter.holidaybooking.rest.model.Booking;
import consulting.baxter.holidaybooking.rest.model.Customer;
import consulting.baxter.holidaybooking.rest.model.DateRange;
import consulting.baxter.holidaybooking.rest.model.Property;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.time.temporal.ChronoUnit.WEEKS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BookingControllerIntegrationTest {

    @Test
    void findsNoBookings() {
        when()
            .get("/bookings")
        .then()
            .body("isEmpty()", is(true));
    }

    @Test
    void cantCreateBookingForUnknownProperty() {
        final Property unknownProperty = Property.builder().name("unknown").build();
        final Customer customer = Customer.from(InsertDummyData.customer);
        final DateRange dateRange = DateRange.builder().from(LocalDate.now()).to(LocalDate.now().plus(1, WEEKS)).build();
        final Booking booking = Booking.builder().property(unknownProperty).customer(customer).dateRange(dateRange).build();
        given()
            .body(booking)
            .contentType("application/json")
        .when()
            .post("/bookings/" + unknownProperty.getName())
        .then()
            .statusCode(400)
            .body(equalTo("PROPERTY_NOT_FOUND"));
    }
}
