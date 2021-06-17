package consulting.baxter.holidaybooking.controller;

import consulting.baxter.holidaybooking.dao.PropertyDao;
import consulting.baxter.holidaybooking.model.Property;
import consulting.baxter.holidaybooking.service.AvailabilityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static consulting.baxter.holidaybooking.service.AvailabilityService.AvailabilityFailure.DATE_RANGE_TOO_BIG;
import static consulting.baxter.holidaybooking.service.AvailabilityService.AvailabilityFailure.START_DATE_AFTER_END;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {
    private final PropertyDao propertyDao;
    private final AvailabilityService availabilityService;

    public AvailabilityController(PropertyDao propertyDao, AvailabilityService availabilityService) {
        this.propertyDao = propertyDao;
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<Object> getAvailableDates(
        @RequestParam(name = "property")
            String propertyName,
        @RequestParam(name = "start")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,
        @RequestParam(name = "end")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {
        final Property property = propertyDao.findByName(propertyName);
        final var availabilityResult = availabilityService.getAvailableDates(property, start, end);

        return availabilityResult.fold(
            AvailabilityController::mapFailure,
            ResponseEntity::ok
        );
    }

    private static ResponseEntity<Object> mapFailure(AvailabilityService.AvailabilityFailure failure) {
        switch (failure) {
            case DATE_RANGE_TOO_BIG:
                return ResponseEntity.badRequest().body(DATE_RANGE_TOO_BIG.toString());
            case START_DATE_AFTER_END:
                return ResponseEntity.badRequest().body(START_DATE_AFTER_END.toString());
            default:
                return ResponseEntity.badRequest().build();
        }
    }
}
