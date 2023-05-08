package mentorsjoy.api.service;

import mentorsjoy.api.model.Faculty;
import mentorsjoy.api.repositories.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    FacultyRepository facultyRepository;

    public List<Faculty> getAllFaculties() {return facultyRepository.findAll();}
}
