package mentorsjoy.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(schema = "extra", name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Column(name = "title")
    private String title;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public String getTitle() {
        return title;
    }
//
    @JsonIgnore
    public String getFaculty() {
        return faculty.getTitle();
    }

//    public void setFaculty(Faculty faculty) {
//        this.faculty = faculty;
//    }
}
