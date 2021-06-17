package consulting.baxter.holidaybooking.controller;

import consulting.baxter.holidaybooking.service.AvailabilityService;
import org.springframework.format.annotation.DateTimeFormat;
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

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public List<LocalDate> getAvailableDates(
        @RequestParam(name = "start")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate start,
        @RequestParam(name = "end")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate end) {
        //todo make sure the user can't request a rediculous date range
        return availabilityService.getAvailableDates(start, end);
    }
}
