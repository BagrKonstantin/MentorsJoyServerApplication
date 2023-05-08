package mentorsjoy.api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "extra", name = "faculty")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Integer facultyId;

    @Column(name = "title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private List<Department> departments;

    public Integer getFacultyId() {
        return facultyId;
    }

    public String getTitle() {
        return title;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
