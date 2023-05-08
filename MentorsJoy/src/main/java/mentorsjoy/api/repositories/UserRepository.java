package mentorsjoy.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import mentorsjoy.api.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String username);

    Optional<User> findByUsername(String username);
}
