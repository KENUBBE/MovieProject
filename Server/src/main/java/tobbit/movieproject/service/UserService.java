package tobbit.movieproject.service;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import tobbit.movieproject.model.User;
import tobbit.movieproject.repository.UserRepository;

import java.io.IOException;

import static tobbit.movieproject.utils.Constants.CLIENT_ID;
import static tobbit.movieproject.utils.Constants.CLIENT_SECRET;
import static tobbit.movieproject.utils.Constants.REDIRECT_URI;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CalendarService calendarService;

    @Autowired
    public UserService(UserRepository userRepository, CalendarService calendarService) {
        this.userRepository = userRepository;
        this.calendarService = calendarService;
    }

    public String verifyUser(String code) {
        GoogleTokenResponse tokenResponse = null;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://www.googleapis.com/oauth2/v4/token",
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    REDIRECT_URI)
                    .execute();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

        GoogleIdToken idToken = null;
        try {
            idToken = tokenResponse.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();

        storeUser(email, accessToken, refreshToken, expiresAt);
        calendarService.updateToken();

        return "Access granted for user: " + email;
    }

    private void storeUser(String email, String accessToken, String refreshToken, Long expiresAt) {
        if (!userRepository.existsByEmail(email))
            userRepository.save(new User(email, accessToken, refreshToken, expiresAt));
    }
}
