package mentorsjoy.api.service;

import mentorsjoy.api.model.Department;
import mentorsjoy.api.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public Department findDepartmentById(Integer departmentId) {
        return departmentRepository.findDepartmentByDepartmentId(departmentId).get();
    }
}
