package tobbit.movieproject.controller;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.model.UserEvent;
import tobbit.movieproject.service.CalendarService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("/api/userEvents")
    public List<UserEvent> getAllUserEvent() {
        return calendarService.getAllUserEvents();
    }

    @PostMapping("/api/scheduleEvent")
    public Event scheduleEvent(@RequestBody UserEvent event) {
        return calendarService.scheduleEvent(event.getSummary(), event.getStartDate(), event.getCreatedBy());
    }
}
