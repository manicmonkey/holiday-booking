package consulting.baxter.holidaybooking.dao;

import consulting.baxter.holidaybooking.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyDao extends JpaRepository<Property, Long> {
    Property findByName(String name);
}
