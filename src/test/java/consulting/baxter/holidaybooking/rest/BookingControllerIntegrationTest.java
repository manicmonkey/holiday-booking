package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.data.CustomerDao;
import consulting.baxter.holidaybooking.data.model.CustomerEntity;
import consulting.baxter.holidaybooking.rest.model.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
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

    private static final CustomerEntity CUSTOMER = CustomerEntity.builder()
        .name("Jessica Hyde")
        .email("utopia@ch4.co.uk")
        .address("124 Horseferry Road, London, England").build();

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME);

    @DynamicPropertySource
    static void datasourceConfiguration(DynamicPropertyRegistry registry) {
        registry.add("app.datasource.jdbc-url", postgreSQLContainer::getJdbcUrl);
        registry.add("app.datasource.username", postgreSQLContainer::getUsername);
        registry.add("app.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private CustomerDao customerDao;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    public void setupRestAssured() {
        RestAssured.port = serverPort;
    }

    @BeforeEach
    public void insertTestCustomer() {
        customerDao.save(CUSTOMER);
    }

    @AfterEach
    public void removeTestCustomer() {
        customerDao.delete(customerDao.findByEmail(CUSTOMER.getEmail()));
    }

    @Test
    @Order(1)
        // check for no bookings before we execute other tests
    void findsNoBookings() {
        when()
            .get("/api/bookings")
            .then()
            .body("isEmpty()", is(true));
    }

    @Test
    void cantCreateBookingForUnknownProperty() {
        final Property unknownProperty = Property.builder().name("unknown").build();
        final Customer customer = Customer.from(CUSTOMER);
        final DateRange dateRange = DateRange.builder().from(LocalDate.now()).to(LocalDate.now().plus(1, WEEKS)).build();
        final Booking booking = Booking.builder().property(unknownProperty).customer(customer).dateRange(dateRange).build();
        given()
            .body(booking)
            .contentType("application/json")
            .when()
            .post("/api/bookings/" + unknownProperty.getName())
            .then()
            .statusCode(400)
            .body(equalTo("PROPERTY_NOT_FOUND"));
    }

    @Test
    void createsBooking() {
        final Property property = Property.builder()
            .name("batcave")
            .address("Gotham City")
            .location(GeoLocation.builder()
                .longitude(new BigDecimal("0.0000"))
                .latitude(new BigDecimal("0.0000")).build()).build();

        WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + serverPort).build()
            .post().uri("/api/properties")
            .bodyValue(property)
            .exchange()
            .expectStatus().isCreated();

        final Customer customer = Customer.from(CUSTOMER);
        final DateRange dateRange = DateRange.builder().from(LocalDate.now()).to(LocalDate.now().plus(1, WEEKS)).build();
        final Booking booking = Booking.builder().property(property).customer(customer).dateRange(dateRange).build();
        given()
            .body(booking)
            .contentType("application/json")
            .when()
            .post("/api/bookings/" + property.getName())
            .then()
            .statusCode(201);

        final Booking[] bookings = when()
            .get("/api/bookings")
            .as(Booking[].class);

        Assertions.assertArrayEquals(new Booking[]{booking}, bookings);
    }
}
