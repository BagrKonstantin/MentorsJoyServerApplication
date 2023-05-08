package mentorsjoy.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import mentorsjoy.api.model.User;
import mentorsjoy.api.service.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path="/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> get() {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/addUser")
    @PreAuthorize("hasRole('ADMIN')")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }
}
