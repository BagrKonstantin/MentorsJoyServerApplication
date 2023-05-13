package mentorsjoy.api.service;

import mentorsjoy.api.model.Sample;
import mentorsjoy.api.model.TechnicalAssignment;
import mentorsjoy.api.payload.Keys;
import mentorsjoy.api.repositories.SampleRepository;
import mentorsjoy.api.repositories.TechnicalAssignmentRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TechnicalAssignmentService {

    @Value("${mentorsjoy.generator.address}")
    private String address;

    @Autowired
    TechnicalAssignmentRepository technicalAssignmentRepository;

    @Autowired
    SampleRepository sampleRepository;


    public Map<Keys, String> delete(Long technicalAssignmentId, Long userId) {
        Optional<TechnicalAssignment> technicalAssignment = technicalAssignmentRepository.findById(technicalAssignmentId);
        if (technicalAssignment.isEmpty()) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Technical assignment with such id doesn't exist");
            }};
        }
        if (!technicalAssignment.get().getSample().getUser().getUserId().equals(userId)) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Technical assignment isn't owned by user");
            }};
        }
        if (Boolean.TRUE.equals(technicalAssignment.get().getDeleted())) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Technical assignment is already deleted");
            }};
        }
        technicalAssignment.get().setDeleted(true);
        technicalAssignmentRepository.save(technicalAssignment.get());
        return new HashMap<>() {{
            put(Keys.STATUS, "ok");
            put(Keys.MESSAGE, "Success");
        }};
    }

    public  Map<Keys, String> generate(Long sampleId, Long userId) {
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
        TechnicalAssignment technicalAssignment = new TechnicalAssignment(sample.get());
        technicalAssignmentRepository.save(technicalAssignment);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<TechnicalAssignment> requestToPython = new HttpEntity<>(technicalAssignment);
        try {
            ResponseEntity<JSONObject> response = restTemplate.postForEntity(address + "/generate_document", requestToPython, JSONObject.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return new HashMap<>() {{
                    put(Keys.STATUS, "ok");
                    put(Keys.MESSAGE, "Generated");
                }};
            } else {
                technicalAssignment.setDeleted(true);
                technicalAssignmentRepository.save(technicalAssignment);
                return new HashMap<>() {{
                    put(Keys.STATUS, "error");
                    put(Keys.MESSAGE, "Generation Error");
                }};
            }
        } catch (ResourceAccessException exception) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, exception.getMessage());
            }};
        }
    }

    public Map<Keys, String> check(Long fileId, Long userId) {
        Optional<TechnicalAssignment> technicalAssignment = technicalAssignmentRepository.findById(fileId);
        if (technicalAssignment.isEmpty()) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Technical assignment with such id doesn't exist");
            }};
        }
        if (!technicalAssignment.get().getSample().getUser().getUserId().equals(userId)) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Technical assignment isn't owned by user");
            }};
        }
        if (Boolean.TRUE.equals(technicalAssignment.get().getDeleted())) {
            return new HashMap<>() {{
                put(Keys.STATUS, "error");
                put(Keys.MESSAGE, "Technical assignment is deleted");
            }};
        }
        return new HashMap<>();
    }

    public byte[] getFile(Long fileId) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(address + "/get-file?id=" + fileId, byte[].class);
    }

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
