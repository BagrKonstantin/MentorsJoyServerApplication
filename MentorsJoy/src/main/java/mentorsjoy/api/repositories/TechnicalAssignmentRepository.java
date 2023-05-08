package mentorsjoy.api.repositories;

import mentorsjoy.api.model.TechnicalAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechnicalAssignmentRepository extends JpaRepository<TechnicalAssignment, Long> {

    @Query("SELECT ta FROM TechnicalAssignment ta WHERE ta.sample.user.userId=:userId AND NOT ta.deleted")
    List<TechnicalAssignment> findAllByUserId(@Param("userId") Long userId);
}
