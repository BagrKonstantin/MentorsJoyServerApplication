package mentorsjoy.api.repositories;

import mentorsjoy.api.model.Chapters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChaptersRepository extends JpaRepository<Chapters, Long> {
}
