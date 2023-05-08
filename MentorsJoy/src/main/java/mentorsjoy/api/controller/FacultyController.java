package mentorsjoy.api.controller;

import mentorsjoy.api.model.Faculty;
import mentorsjoy.api.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/extra")
public class FacultyController {
    @Autowired
    FacultyService facultyService;

    @GetMapping(path="/get-all-faculties")
    public List<Faculty> get() {
        return facultyService.getAllFaculties();
    }

}
