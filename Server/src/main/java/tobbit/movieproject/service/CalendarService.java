package tobbit.movieproject.service;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import tobbit.movieproject.model.*;
import tobbit.movieproject.repository.UserRepository;
import tobbit.movieproject.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class CalendarService implements Constants {

    private final UserRepository userRepository;
    private final MongoOperations mongoOperations;

    @Autowired
    public CalendarService(UserRepository userRepository, MongoOperations mongoOperations) {
        this.userRepository = userRepository;
        this.mongoOperations = mongoOperations;
    }

    public List<UserEvent> getAllUserEvents() {
        List<User> allUsers = userRepository.findAll();
        List<UserEvent> allEvents = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            GoogleCredential credential = new GoogleCredential().setAccessToken(allUsers.get(i).getAccessToken());
            Calendar service = new Calendar.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
                    .setApplicationName("Movie Nights")
                    .build();
            updateToken();

            final DateTime startDate = new DateTime("2018-10-05T16:30:00.000+05:30"); // Change to now when done!
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

    private GoogleCredential refreshCredentials(String refreshToken) {
        try {
            GoogleTokenResponse response = new GoogleRefreshTokenRequest(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                    refreshToken, CLIENT_ID, CLIENT_SECRET)
                    .execute();
            return new GoogleCredential().setAccessToken(response.getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateToken() {
        List<User> allUsers = userRepository.findAll();
        for (int i = 0; i < allUsers.size(); i++) {
            long expiresAt = allUsers.get(i).getExpiresAt();
            long now = new DateTime(System.currentTimeMillis()).getValue();
            if (hasTokenExpired(expiresAt, now)) {
                GoogleCredential newCredentials = refreshCredentials(allUsers.get(i).getRefreshToken());
                updateUser(allUsers.get(i).getEmail(), newCredentials.getAccessToken());
            }
        }
    }

    private boolean hasTokenExpired(long expiresAt, long now) {
        org.joda.time.DateTime expire = new org.joda.time.DateTime(expiresAt);
        org.joda.time.DateTime current = new org.joda.time.DateTime(now);
        return current.isBefore(expire);
    }

    private void updateUser(String email, String accessToken) {
        mongoOperations.updateFirst(
                query(where("email").is(email)), Update.update("accessToken", accessToken), "user");
    }
}
