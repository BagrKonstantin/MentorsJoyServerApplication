package mentorsjoy.api.repositories;

import mentorsjoy.api.model.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClazzRepository extends JpaRepository<Classes, Integer> {
    Optional<Classes> findByClassId(Integer clazzId);
}
