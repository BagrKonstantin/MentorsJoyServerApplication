package mentorsjoy.api.service;

import mentorsjoy.api.model.Sample;
import mentorsjoy.api.repositories.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SampleService {
    @Autowired
    SampleRepository sampleRepository;

    public List<Sample> getAll() {return sampleRepository.findAll();}

    public Optional<Sample> get(Long sampleId) {return sampleRepository.findById(sampleId);}

    public List<Sample> findAllByUserId(Long userId) {
        return sampleRepository.findAllByUserId(userId);
    }

    public void save(Sample sample) {
        sampleRepository.save(sample);
    }
}
