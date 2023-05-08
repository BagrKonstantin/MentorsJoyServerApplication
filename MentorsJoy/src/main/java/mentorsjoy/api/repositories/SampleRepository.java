package mentorsjoy.api.repositories;

import mentorsjoy.api.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SampleRepository extends JpaRepository<Sample, Long> {
    @Query("SELECT sample FROM Sample sample WHERE sample.user.userId=:userId AND sample.deleted=FALSE")
    public List<Sample> findAllByUserId(@Param("userId") Long userId);
}
