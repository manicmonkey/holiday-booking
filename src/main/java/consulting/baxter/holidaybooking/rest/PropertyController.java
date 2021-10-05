package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.data.PropertyDao;
import consulting.baxter.holidaybooking.rest.model.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyDao propertyDao;

    public PropertyController(PropertyDao propertyDao) {
        this.propertyDao = propertyDao;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Property property) {
        log.info("Creating property: {}", property);
        propertyDao.save(property.toNew());
    }

    @GetMapping
    public List<Property> get() {
        log.info("Getting all properties");
        return propertyDao.findAll(Sort.by("name")).stream().map(Property::from).collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    public Property get(@PathVariable String name) {
        return propertyDao.findByName(name).map(Property::from).orElse(null);
    }
}
