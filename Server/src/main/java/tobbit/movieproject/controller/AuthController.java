package tobbit.movieproject.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tobbit.movieproject.service.UserService;
import tobbit.movieproject.utils.Constants;

@RestController
public class AuthController implements Constants {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/verifyUser")
    public ResponseEntity<String> storeAuthCode(@RequestBody String code) {
        return ResponseEntity.ok(userService.verifyUser(code));
    }
}
