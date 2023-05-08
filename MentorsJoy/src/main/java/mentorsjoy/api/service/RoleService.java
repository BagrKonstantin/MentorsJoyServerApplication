package mentorsjoy.api.service;

import mentorsjoy.api.model.ERole;
import mentorsjoy.api.model.Role;
import mentorsjoy.api.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name) {
       return roleRepository.findByName(name);
    }
}
