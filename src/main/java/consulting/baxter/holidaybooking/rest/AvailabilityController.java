package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.data.PropertyDao;
import consulting.baxter.holidaybooking.rest.model.AvailableDay;
import consulting.baxter.holidaybooking.service.AvailabilityService;
import io.vavr.control.Either;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;
    private final PropertyDao propertyDao;

    public AvailabilityController(AvailabilityService availabilityService, PropertyDao propertyDao) {
        this.availabilityService = availabilityService;
        this.propertyDao = propertyDao;
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
        return propertyDao.findByName(propertyName)
            .map(property -> availabilityService.getAvailability(property, start, end))
            .map(this::availabilityResultToResponse)
            .orElse(ResponseEntity.badRequest().body(Failure.PROPERTY_NOT_FOUND.toString()));
    }

    private ResponseEntity<Object> availabilityResultToResponse(Either<AvailabilityService.Failure, List<AvailableDay>> res) {
        return res.fold(
            AvailabilityController::mapFailure,
            ResponseEntity::ok
        );
    }

    private static ResponseEntity<Object> mapFailure(AvailabilityService.Failure failure) {
        final var badRequest = ResponseEntity.badRequest();
        switch (failure) {
            case DATE_RANGE_TOO_BIG:
                return badRequest.body(Failure.DATE_RANGE_TOO_BIG.toString());
            case START_DATE_AFTER_END:
                return badRequest.body(Failure.START_DATE_AFTER_END.toString());
            case START_DATE_IN_PAST:
                return badRequest.body(Failure.START_DATE_IN_PAST.toString());
            default:
                return badRequest.build();
        }
    }

    enum Failure {
        DATE_RANGE_TOO_BIG,
        START_DATE_AFTER_END,
        START_DATE_IN_PAST,
        PROPERTY_NOT_FOUND
    }
}
