package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.data.PropertyDao;
import consulting.baxter.holidaybooking.data.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private static final Logger log = LoggerFactory.getLogger(PropertyController.class);

    private final PropertyDao propertyDao;

    public PropertyController(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Property property) {
        log.info("Creating property: {}", property);
        propertyDao.save(property);
    }

    @GetMapping
    public List<Property> get() {
        return propertyDao.findAll(Sort.by("name"));
    }

    @GetMapping("/{name}")
    public Property get(@PathVariable String name) {
        return propertyDao.findByName(name);
    }
}
