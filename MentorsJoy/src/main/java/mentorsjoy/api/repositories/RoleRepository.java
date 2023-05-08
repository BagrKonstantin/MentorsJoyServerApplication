package mentorsjoy.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import mentorsjoy.api.model.ERole;
import mentorsjoy.api.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}