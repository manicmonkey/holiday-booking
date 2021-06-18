package consulting.baxter.holidaybooking.data;

import consulting.baxter.holidaybooking.data.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyDao extends JpaRepository<Property, Long> {
    Property findByName(String name);
}
