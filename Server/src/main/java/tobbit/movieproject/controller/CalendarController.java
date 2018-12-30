package tobbit.movieproject.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.model.UserEvent;
import tobbit.movieproject.service.CalendarService;

@RestController
@CrossOrigin("http://localhost:4200")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @PostMapping("/api/scheduleEvent")
    public ResponseEntity<Event> scheduleEvent(@RequestBody UserEvent event) {
        return ResponseEntity.ok(calendarService.scheduleEvent(event.getSummary(), event.getStartDate(), event.getCreatedBy()));
    }
}
