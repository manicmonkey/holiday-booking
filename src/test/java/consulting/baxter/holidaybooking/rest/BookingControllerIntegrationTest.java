package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.InsertDummyData;
import consulting.baxter.holidaybooking.rest.model.Booking;
import consulting.baxter.holidaybooking.rest.model.Customer;
import consulting.baxter.holidaybooking.rest.model.DateRange;
import consulting.baxter.holidaybooking.rest.model.Property;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.time.temporal.ChronoUnit.WEEKS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookingControllerIntegrationTest {

    private static final DockerImageName POSTGRES_IMAGE_NAME = DockerImageName.parse("postgres:13.3");

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME);

    @DynamicPropertySource
    static void datasourceConfiguration(DynamicPropertyRegistry registry) {
        registry.add("app.datasource.jdbc-url", postgreSQLContainer::getJdbcUrl);
        registry.add("app.datasource.username", postgreSQLContainer::getUsername);
        registry.add("app.datasource.password", postgreSQLContainer::getPassword);
    }

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    public void setupRestAssured() {
        RestAssured.port = serverPort;
    }

    @Test
    @Order(1) // check for no bookings before we execute other tests
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
            .baseUrl("http://localhost:" + serverPort).build()
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
