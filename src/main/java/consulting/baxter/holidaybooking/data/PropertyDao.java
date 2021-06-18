package consulting.baxter.holidaybooking.data;

import consulting.baxter.holidaybooking.data.model.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyDao extends JpaRepository<PropertyEntity, Long> {
    Optional<PropertyEntity> findByName(String name);
}
