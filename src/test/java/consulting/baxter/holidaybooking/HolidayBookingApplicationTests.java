package consulting.baxter.holidaybooking;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class HolidayBookingApplicationTests {

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
    void performsGet() {
        when()
            .get("/properties")
        .then()
            .body("isEmpty()", is(true));
    }

}
