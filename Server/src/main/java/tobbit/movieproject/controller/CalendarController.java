package tobbit.movieproject.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.model.UserEvent;
import tobbit.movieproject.service.CalendarService;

import java.util.List;

@RestController
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

    @GetMapping("/api/getAllEvent")
    public ResponseEntity<List<UserEvent>> getAllEvent() {
        return ResponseEntity.ok(calendarService.getAllUserEvents());
    }
}
