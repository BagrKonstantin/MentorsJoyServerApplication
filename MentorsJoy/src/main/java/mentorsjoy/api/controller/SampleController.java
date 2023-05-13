package mentorsjoy.api.controller;

import mentorsjoy.api.model.Sample;
import mentorsjoy.api.payload.Keys;
import mentorsjoy.api.security.UserDetailsImpl;
import mentorsjoy.api.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/sample")
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @GetMapping(path = "/get-all")
    public List<Sample> getAll(Authentication authentication) {
        return sampleService.findAllByUserId(((UserDetailsImpl) (authentication.getPrincipal())).getId());
    }

    @PostMapping("/create")
    public ResponseEntity<Sample> createSample(Authentication authentication) {
        return ResponseEntity.ok(sampleService.createNewSample(((UserDetailsImpl) (authentication.getPrincipal())).getId()));
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveSample(Authentication authentication, @RequestBody Sample sample) {
        if (!sample.getUser().getUserId().equals(((UserDetailsImpl) (authentication.getPrincipal())).getId())) {
            return ResponseEntity.badRequest().body("Sample isn't owned by user");
        }
        Map<Keys, String> result = sampleService.saveSample(sample);
        if (result.get(Keys.STATUS).equals("ok")) {
            return ResponseEntity.ok(result.get(Keys.MESSAGE));
        } else {
            return ResponseEntity.badRequest().body(result.get(Keys.MESSAGE));
        }
    }


    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<String> deleteSample(Authentication authentication, @RequestBody Map<String, Long> request) {
        Long sampleId = request.get("sampleId");
        if (sampleId == null) {
            return ResponseEntity.badRequest().body("No sample id presented");
        }
        Map<Keys, String> result = sampleService.deleteSample(sampleId, ((UserDetailsImpl) (authentication.getPrincipal())).getId());
        if (result.get(Keys.STATUS).equals("ok")) {
            return ResponseEntity.ok(result.get(Keys.MESSAGE));
        } else {
            return ResponseEntity.badRequest().body(result.get(Keys.MESSAGE));
        }

    }
}
