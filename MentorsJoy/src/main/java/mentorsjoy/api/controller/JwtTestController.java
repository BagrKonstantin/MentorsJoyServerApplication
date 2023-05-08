package mentorsjoy.api.controller;

import mentorsjoy.api.model.User;
import mentorsjoy.api.repositories.UserRepository;
import mentorsjoy.api.security.services.UserDetailsImpl;
import mentorsjoy.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class JwtTestController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String allAccess() {
        return "User content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public User userAccess(Authentication authentication) {
        return userService.findByUsername(((UserDetailsImpl) (authentication.getPrincipal())).getUsername()).get();
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}