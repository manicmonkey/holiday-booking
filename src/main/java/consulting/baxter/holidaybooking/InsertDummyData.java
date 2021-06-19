package consulting.baxter.holidaybooking;

import consulting.baxter.holidaybooking.data.CustomerDao;
import consulting.baxter.holidaybooking.data.model.CustomerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
@Slf4j
public class InsertDummyData {

    public static final CustomerEntity customer = CustomerEntity.builder()
        .name("James Bond")
        .email("007@mi5.gov.uk")
        .address("Thames House, 12 Millbank, London").build();


    private final CustomerDao customerDao;

    public InsertDummyData(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @PostConstruct
    void insertCustomer() {
        log.debug("Inserting customer");
        if (customerDao.findOne(Example.of(CustomerEntity.builder().name(customer.getName()).build())).isEmpty()) {
            customerDao.save(customer);
        }
    }
}
