package mentorsjoy.api.controller;

import mentorsjoy.api.model.Sample;
import mentorsjoy.api.model.TechnicalAssignment;
import mentorsjoy.api.security.services.UserDetailsImpl;
import mentorsjoy.api.service.SampleService;
import mentorsjoy.api.service.TechnicalAssignmentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/technical_assignment")
public class TechnicalAssignmentController {

    @Value("${mentorsjoy.generator.address}")
    private String address;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private TechnicalAssignmentService technicalAssignmentService;


    @GetMapping("/get-all")
    public ResponseEntity<List<TechnicalAssignment>> getAll(Authentication authentication) {
        return ResponseEntity.ok(technicalAssignmentService.findAllByUserId(((UserDetailsImpl) (authentication.getPrincipal())).getId()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(Authentication authentication, @RequestBody Map<String, Long> request) {
        Long technicalAssignmentId = request.get("technicalAssignmentId");
        if (technicalAssignmentId == null) {
            return ResponseEntity.badRequest().body("No technical assignment id id presented");
        }
        Optional<TechnicalAssignment> technicalAssignment = technicalAssignmentService.findById(technicalAssignmentId);
        if (technicalAssignment.isEmpty()) {
            return ResponseEntity.badRequest().body("Technical assignment with such id doesn't exist");
        }
        if (!technicalAssignment.get().getSample().getUser().getUserId().equals(((UserDetailsImpl) (authentication.getPrincipal())).getId())) {
            return ResponseEntity.badRequest().body("Technical assignment isn't owned by user");
        }
        if (Boolean.TRUE.equals(technicalAssignment.get().getDeleted())) {
            return ResponseEntity.badRequest().body("Technical assignment is already deleted");
        }
        technicalAssignment.get().setDeleted(true);
        technicalAssignmentService.save(technicalAssignment.get());
        return ResponseEntity.ok("Success");
    }


    @PostMapping("/generate")
    public ResponseEntity<String> generate(Authentication authentication, @RequestBody Map<String, Long> request) {
        Long sampleId = request.get("sampleId");
        if (sampleId == null) {
            return ResponseEntity.badRequest().body("No sample id presented");
        }
        Optional<Sample> sample = sampleService.get(sampleId);
        if (sample.isEmpty()) {
            return ResponseEntity.badRequest().body("Sample with such id doesn't exist");
        }
        if (!sample.get().getUser().getUserId().equals(((UserDetailsImpl) (authentication.getPrincipal())).getId())) {
            return ResponseEntity.badRequest().body("Sample isn't owned by user");
        }
        if (Boolean.TRUE.equals(sample.get().getDeleted())) {
            return ResponseEntity.badRequest().body("Sample is deleted");
        }
        TechnicalAssignment technicalAssignment = new TechnicalAssignment(sample.get());
        technicalAssignmentService.save(technicalAssignment);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<TechnicalAssignment> requestToPython = new HttpEntity<>(technicalAssignment);
        try {
            ResponseEntity<JSONObject> response = restTemplate.postForEntity(address + "/generate_document", requestToPython, JSONObject.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("Generated");
            } else {
                technicalAssignment.setDeleted(true);
                technicalAssignmentService.save(technicalAssignment);
                return ResponseEntity.badRequest().body("Generation Error");
            }
        } catch (ResourceAccessException exception) {
            throw exception;
            //return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


    @GetMapping(path = "/get-file/{file_id}")
    public ResponseEntity<?> getFile(Authentication authentication, @PathVariable("file_id") Long fileId) {
        Optional<TechnicalAssignment> technicalAssignment = technicalAssignmentService.findById(fileId);
        if (technicalAssignment.isEmpty()) {
            return ResponseEntity.badRequest().body("Technical assignment with such id doesn't exist");
        }
        if (!technicalAssignment.get().getSample().getUser().getUserId().equals(((UserDetailsImpl) (authentication.getPrincipal())).getId())) {
            return ResponseEntity.badRequest().body("Technical assignment isn't owned by user");
        }
        if (Boolean.TRUE.equals(technicalAssignment.get().getDeleted())) {
            return ResponseEntity.badRequest().body("Technical assignment is deleted");
        }
        RestTemplate restTemplate = new RestTemplate();

        byte[] response = restTemplate.getForObject(address + "/get-file?id=" + fileId, byte[].class);

        return ResponseEntity.ok(response);
    }
}
