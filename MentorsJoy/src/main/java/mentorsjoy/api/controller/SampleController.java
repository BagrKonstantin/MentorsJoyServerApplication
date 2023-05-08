package mentorsjoy.api.controller;

import mentorsjoy.api.model.Person;
import mentorsjoy.api.model.Sample;
import mentorsjoy.api.repositories.UserRepository;
import mentorsjoy.api.security.services.UserDetailsImpl;
import mentorsjoy.api.service.ClazzService;
import mentorsjoy.api.service.DepartmentService;
import mentorsjoy.api.service.PersonService;
import mentorsjoy.api.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/app/sample")
public class SampleController {
    @Autowired
    SampleService sampleService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    ClazzService clazzService;

    @Autowired
    PersonService personService;

    @GetMapping(path = "/get-all")
    public List<Sample> get(Authentication authentication) {
        return sampleService.findAllByUserId(((UserDetailsImpl) (authentication.getPrincipal())).getId());
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Sample> createSample(Authentication authentication) {
        Person teacher = new Person();
        Person headTeacher = new Person();
        personService.save(teacher);
        personService.save(headTeacher);
        Sample sample = new Sample(
                userRepository.findByUsername(((UserDetailsImpl) (authentication.getPrincipal())).getUsername()).get(),
                teacher,
                headTeacher,
                departmentService.findDepartmentById(1),
                clazzService.findById(1));


        sampleService.save(sample);

        return ResponseEntity.ok(sample);
    }

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<String> saveSample(Authentication authentication, @RequestBody Sample sample) {
        if (!sample.getUser().getUserId().equals(((UserDetailsImpl) (authentication.getPrincipal())).getId())) {
            return ResponseEntity.badRequest().body("Sample isn't owned by user");
        }
        sample.setDepartment(departmentService.findDepartmentById(sample.getDepartment().getDepartmentId()));
        sample.setClazz(clazzService.findById(sample.getClazz().getClassId()));
        personService.save(sample.getTeacher());
        personService.save(sample.getHeadTeacher());
        sampleService.save(sample);

        return ResponseEntity.ok("Success");
    }


    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<String> deleteSample(Authentication authentication, @RequestBody Map<String, Long> request) {
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
            return ResponseEntity.badRequest().body("Sample is already deleted");
        }
        sample.get().setDeleted(Boolean.TRUE);
        sampleService.save(sample.get());

        return ResponseEntity.ok("Success");
    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping(path = "/get-sample-{sampleId}")
//    public Sample get(@PathVariable Long sampleId) {
//        return sampleService.get(sampleId).get();
//    }
}
