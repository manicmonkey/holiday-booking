package consulting.baxter.holidaybooking.data;

import consulting.baxter.holidaybooking.data.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByEmail(String email);
}
