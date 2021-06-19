package consulting.baxter.holidaybooking;

import consulting.baxter.holidaybooking.rest.model.Booking;
import consulting.baxter.holidaybooking.rest.model.Customer;
import consulting.baxter.holidaybooking.rest.model.DateRange;
import consulting.baxter.holidaybooking.rest.model.Property;
import lombok.val;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.time.temporal.ChronoUnit.WEEKS;
import static org.hamcrest.Matchers.*;

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

    @Test
    void createsBooking() {
        final Property property = Property.builder().name("batcave").build();

        WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080").build()
            .post().uri("/properties")
            .bodyValue(property)
            .exchange()
            .expectStatus().isCreated();

        final Customer customer = Customer.from(InsertDummyData.customer);
        final DateRange dateRange = DateRange.builder().from(LocalDate.now()).to(LocalDate.now().plus(1, WEEKS)).build();
        final Booking booking = Booking.builder().property(property).customer(customer).dateRange(dateRange).build();
        given()
            .body(booking)
            .contentType("application/json")
        .when()
            .post("/bookings/" + property.getName())
        .then()
            .statusCode(201);

        final Booking[] bookings = when()
            .get("/bookings")
            .as(Booking[].class);

        Assertions.assertArrayEquals(new Booking[]{booking}, bookings);
    }
}
