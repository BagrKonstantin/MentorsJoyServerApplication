package mentorsjoy.api.controller;

import jakarta.validation.Valid;
import mentorsjoy.api.payload.Keys;
import mentorsjoy.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mentorsjoy.api.payload.request.LoginRequest;
import mentorsjoy.api.payload.request.SignupRequest;
import mentorsjoy.api.payload.response.JwtResponse;

import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.singin(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        Map<Keys, String> result = authService.singup(signUpRequest);
        if (result.get(Keys.STATUS).equals("ok")) {
            return ResponseEntity.ok(result.get(Keys.MESSAGE));
        } else {
            return ResponseEntity.badRequest().body(result.get(Keys.MESSAGE));
        }
    }
}
