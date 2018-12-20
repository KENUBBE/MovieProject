package tobbit.movieproject.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/api/userEvents")
    public List<UserEvent> getAllUserEvent(){
        return calendarService.getAllUserEvents();
    }
}
