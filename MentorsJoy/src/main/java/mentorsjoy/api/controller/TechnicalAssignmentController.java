package mentorsjoy.api.controller;

import mentorsjoy.api.model.TechnicalAssignment;
import mentorsjoy.api.payload.Keys;
import mentorsjoy.api.security.UserDetailsImpl;
import mentorsjoy.api.service.TechnicalAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/technical_assignment")
public class TechnicalAssignmentController {


    @Autowired
    private TechnicalAssignmentService technicalAssignmentService;


    @GetMapping("/get-all")
    public ResponseEntity<List<TechnicalAssignment>> getAll(Authentication authentication) {
        return ResponseEntity.ok(technicalAssignmentService.findAllByUserId(((UserDetailsImpl) (authentication.getPrincipal())).getId()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(Authentication authentication, @RequestBody Map<String, Long> request) {
        Long technicalAssignmentId = request.get("technicalAssignmentId");
        if (technicalAssignmentId == null) {
            return ResponseEntity.badRequest().body("No technical assignment id id presented");
        }
        Map<Keys, String> result = technicalAssignmentService.delete(technicalAssignmentId, ((UserDetailsImpl) (authentication.getPrincipal())).getId());
        if (result.get(Keys.STATUS).equals("ok")) {
            return ResponseEntity.ok(result.get(Keys.MESSAGE));
        } else {
            return ResponseEntity.badRequest().body(result.get(Keys.MESSAGE));
        }
    }


    @PostMapping("/generate")
    public ResponseEntity<String> generate(Authentication authentication, @RequestBody Map<String, Long> request) {
        Long sampleId = request.get("sampleId");
        if (sampleId == null) {
            return ResponseEntity.badRequest().body("No sample id presented");
        }
        Map<Keys, String> result = technicalAssignmentService.generate(sampleId, ((UserDetailsImpl) (authentication.getPrincipal())).getId());
        if (result.get(Keys.STATUS).equals("ok")) {
            return ResponseEntity.ok(result.get(Keys.MESSAGE));
        } else {
            return ResponseEntity.badRequest().body(result.get(Keys.MESSAGE));
        }
    }


    @GetMapping(path = "/get-file/{file_id}")
    public ResponseEntity<?> getFile(Authentication authentication, @PathVariable("file_id") Long fileId) {

        Map<Keys, String> result = technicalAssignmentService.check(fileId, ((UserDetailsImpl) (authentication.getPrincipal())).getId());
        if (result.get(Keys.STATUS).equals("error")) {
            return ResponseEntity.badRequest().body(result.get(Keys.MESSAGE));
        }
        byte[] response = technicalAssignmentService.getFile(fileId);
        return ResponseEntity.ok(response);
    }
}
