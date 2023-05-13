package mentorsjoy.api.service;

import mentorsjoy.api.model.ERole;
import mentorsjoy.api.model.Role;
import mentorsjoy.api.model.User;
import mentorsjoy.api.payload.Keys;
import mentorsjoy.api.payload.request.LoginRequest;
import mentorsjoy.api.payload.request.SignupRequest;
import mentorsjoy.api.payload.response.JwtResponse;
import mentorsjoy.api.security.jwt.JwtUtils;
import mentorsjoy.api.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PersonService personService;
    public JwtResponse singin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public Map<Keys, String> singup(SignupRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Username is already taken!");
            }};
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Email is already in use!");
            }};
        }

        personService.save(signUpRequest.getPerson());

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getPerson());

        Set<Role> roles = new HashSet<>();
        Role userRole = roleService.findByName(ERole.ROLE_USER).get();
        roles.add(userRole);


        user.setRoles(roles);
        userService.save(user);
        return new HashMap<>() {{
            put(Keys.STATUS, "ok");
            put(Keys.MESSAGE, "User registered successfully!");
        }};
    }


}
