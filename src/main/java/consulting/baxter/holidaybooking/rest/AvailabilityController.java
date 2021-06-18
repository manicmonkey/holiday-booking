package consulting.baxter.holidaybooking.rest;

import consulting.baxter.holidaybooking.data.PropertyDao;
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

        return propertyDao.findByName(propertyName)
            .map(property -> availabilityService.getAvailableDates(property, start, end))
            .map(this::availabilityResultToResponse)
            .orElse(ResponseEntity.badRequest().body(Failure.PROPERTY_NOT_FOUND.toString()));
    }

    private ResponseEntity<Object> availabilityResultToResponse(Either<AvailabilityService.Failure, List<LocalDate>> res) {
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
            default:
                return badRequest.build();
        }
    }

    enum Failure {
        DATE_RANGE_TOO_BIG,
        START_DATE_AFTER_END,
        PROPERTY_NOT_FOUND
    }
}
