package consulting.baxter.holidaybooking;

import consulting.baxter.holidaybooking.data.CustomerDao;
import consulting.baxter.holidaybooking.data.PropertyDao;
import consulting.baxter.holidaybooking.data.model.CustomerEntity;
import consulting.baxter.holidaybooking.data.model.EmbeddableGeoLocation;
import consulting.baxter.holidaybooking.data.model.PropertyEntity;
import io.vavr.collection.Array;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Profile("dev")
@Component
@Slf4j
public class InsertDummyData {

    public static final CustomerEntity customer = CustomerEntity.builder()
        .name("James Bond")
        .email("007@mi5.gov.uk")
        .address("Thames House, 12 Millbank, London").build();

    private static final PropertyEntity wimpoleHall = new PropertyEntity(
        "Wimpole Hall",
        "Wimpole Estate, Arrington, Royston, SG8 0BW, United Kingdom",
        new EmbeddableGeoLocation(new BigDecimal("-0.0443"), new BigDecimal("52.1411")));

    private static final PropertyEntity marriottNewcastle = new PropertyEntity("Grand Hotel Gosforth Park",
        "High Gosforth Park, Newcastle upon Tyne, NE3 5HN, United Kingdom",
        new EmbeddableGeoLocation(new BigDecimal("-1.6218"), new BigDecimal("55.0353")));

    private final CustomerDao customerDao;
    private final PropertyDao propertyDao;

    public InsertDummyData(CustomerDao customerDao, PropertyDao propertyDao) {
        this.customerDao = customerDao;
        this.propertyDao = propertyDao;
    }

    @PostConstruct
    void insertCustomer() {
        log.debug("Inserting customer");
        if (!customerDao.exists(Example.of(customer))) {
            customerDao.save(customer);
        }
    }

    @PostConstruct
    void insertProperties() {
        log.debug("Inserting properties");
        Array.of(wimpoleHall, marriottNewcastle)
            .filter(property -> !propertyDao.exists(Example.of(property)))
            .forEach(propertyDao::save);
    }
}
