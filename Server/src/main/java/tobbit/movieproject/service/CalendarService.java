package tobbit.movieproject.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tobbit.movieproject.model.User;
import tobbit.movieproject.model.UserEvent;
import tobbit.movieproject.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarService {

    private final UserRepository userRepository;

    @Autowired
    public CalendarService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEvent> getAllUserEvents() {
        List<User> allUsers = userRepository.findAll();
        List<UserEvent> allEvents = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            GoogleCredential credential = new GoogleCredential().setAccessToken(allUsers.get(i).getAccessToken());
            Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                    .setApplicationName("Movie Nights")
                    .build();

            final DateTime startDate = new DateTime("2018-10-05T16:30:00.000+05:30"); // Change to now!
            Events events = null;
            try {
                events = service.events().list(allUsers.get(i).getEmail())
                        .setMaxResults(10)
                        .setTimeMin(startDate)
                        .setOrderBy("startTime")
                        .setSingleEvents(true)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Event> items = events.getItems();
            if (items.isEmpty()) {
                System.out.println("No events found");
            } else {
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    DateTime end = event.getEnd().getDateTime();
                    if (end == null) {
                        end = event.getEnd().getDate();
                    }
                    allEvents.add(new UserEvent(event.getSummary(), start, end));
                }
            }
        }
        return allEvents;
    }
}
