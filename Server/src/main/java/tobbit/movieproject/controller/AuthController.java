package tobbit.movieproject.controller;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.model.User;
import tobbit.movieproject.repository.UserRepository;
import tobbit.movieproject.utils.Constants;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:4200")
public class AuthController implements Constants {

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //TODO: Refactor  AuthService instead!
    @PostMapping("/verifyUser")
    public ResponseEntity<String> storeAuthCode(@RequestBody String code) {
        System.out.println("CODE: " + code);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();
        Long expiresAt = System.currentTimeMillis() + (tokenResponse.getExpiresInSeconds() * 1000);

        System.out.println("accessToken: " + accessToken);
        System.out.println("refreshToken: " + refreshToken);
        System.out.println("expiresAt: " + expiresAt);

        GoogleIdToken idToken = null;
        try {
            idToken = tokenResponse.parseIdToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();

        storeUser(email, accessToken, refreshToken, expiresAt);
        return ResponseEntity.ok("Access granted for user: " + email);
    }

    private void storeUser(String email, String accessToken, String refreshToken, Long expiresAt) {
        if (!userRepository.existsByEmail(email))
            userRepository.save(new User(email, accessToken, refreshToken, expiresAt));
    }
}
