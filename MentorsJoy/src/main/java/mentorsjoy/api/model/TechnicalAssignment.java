package mentorsjoy.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(schema = "documents", name = "tech_assigment")
public class TechnicalAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_assigment_id")
    private Integer techAssigmentId;

    @OneToOne
    @JoinColumn(name = "sample_id")
    private Sample sample;


    @Column(name = "is_deleted")
    private Boolean deleted = false;

    public TechnicalAssignment() {
    }

    public TechnicalAssignment(Sample sample) {
        this.sample = sample;
    }

    public Integer getTechAssigmentId() {
        return techAssigmentId;
    }

    public Sample getSample() {
        return sample;
    }

    public String getFaculty() {
        return sample.getDepartment().getFaculty();
    }

    public String getChapterCode() {
        return sample.getClazz().getChapterCode();
    }

    public String getTeacherName() {
        return sample.getTeacher().getInRequiredFormat();
    }

    public String getHeadTeacherName() {
        return sample.getHeadTeacher().getInRequiredFormat();
    }

    public String getUserName() {
        return sample.getUser().getPerson().getInRequiredFormat();
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @JsonIgnore
    public Boolean getDeleted() {
        return deleted;
    }
}
