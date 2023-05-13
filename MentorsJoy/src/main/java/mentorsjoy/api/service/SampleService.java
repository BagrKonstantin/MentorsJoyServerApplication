package mentorsjoy.api.service;

import mentorsjoy.api.model.Person;
import mentorsjoy.api.model.Sample;
import mentorsjoy.api.payload.Keys;
import mentorsjoy.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SampleService {
    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ClazzRepository clazzRepository;

    @Autowired
    PersonRepository personRepository;

    @Transactional

    public Sample createNewSample(Long userId) {
        Person teacher = new Person();
        Person headTeacher = new Person();
        personRepository.save(teacher);
        personRepository.save(headTeacher);
        Sample sample = new Sample(
                userRepository.findByUserId(userId).get(),
                teacher,
                headTeacher,
                departmentRepository.findDepartmentByDepartmentId(1).get(),
                clazzRepository.findByClassId(1).get()
        );
        sampleRepository.save(sample);
        return sample;
    }

    @Transactional

    public Map<Keys, String> saveSample(Sample sample) {
        try {
            sample.setDepartment(departmentRepository.findDepartmentByDepartmentId(sample.getDepartment().getDepartmentId()).get());
            sample.setClazz(clazzRepository.findById(sample.getClazz().getClassId()).get());
            personRepository.save(sample.getTeacher());
            personRepository.save(sample.getHeadTeacher());
            sampleRepository.save(sample);
        } catch (Exception exception) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, exception.getMessage());
            }};
        }
        return new HashMap<>() {{
            put(Keys.STATUS, "ok");
            put(Keys.MESSAGE, "Success");
        }};
    }

    public Map<Keys, String> deleteSample(Long sampleId, Long userId) {
        Optional<Sample> sample = sampleRepository.findById(sampleId);
        if (sample.isEmpty()) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Sample with such id doesn't exist");
            }};
        }
        if (!sample.get().getUser().getUserId().equals(userId)) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Sample isn't owned by user");
            }};
        }
        if (Boolean.TRUE.equals(sample.get().getDeleted())) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Sample is already deleted");
            }};
        }
        sample.get().setDeleted(Boolean.TRUE);
        sampleRepository.save(sample.get());
        return new HashMap<>() {{
            put(Keys.STATUS, "ok");
            put(Keys.MESSAGE, "Success");
        }};
    }

    public List<Sample> getAll() {
        return sampleRepository.findAll();
    }

    public Optional<Sample> get(Long sampleId) {
        return sampleRepository.findById(sampleId);
    }

    public List<Sample> findAllByUserId(Long userId) {
        return sampleRepository.findAllByUserId(userId);
    }

}
