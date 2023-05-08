package mentorsjoy.api.service;

import mentorsjoy.api.model.TechnicalAssignment;
import mentorsjoy.api.repositories.TechnicalAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicalAssignmentService {

    @Autowired
    TechnicalAssignmentRepository technicalAssignmentRepository;

    public List<TechnicalAssignment> findAllByUserId(Long userId) {
        return technicalAssignmentRepository.findAllByUserId(userId);
    }

    public Optional<TechnicalAssignment> findById(Long techAssigmentId) {
       return technicalAssignmentRepository.findById(techAssigmentId);
    }

    public void save(TechnicalAssignment technicalAssignment) {
        technicalAssignmentRepository.save(technicalAssignment);
    }
}
