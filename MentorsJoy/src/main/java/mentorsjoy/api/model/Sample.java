package mentorsjoy.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.time.Year;

@Entity
@Table(schema = "documents", name = "sample")
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sample_id")
    private Integer sampleId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "person_id")
    private Person teacher;

    @OneToOne
    @JoinColumn(name = "head_teacher_id", referencedColumnName = "person_id")
    private Person headTeacher;


    @OneToOne
    @Nullable
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "year_of_work")
    private Short year = (short) Year.now().getValue();

    @Column(name = "program_name")
    private String programName = "";

    @Column(name = "program_short_name")
    private String programShortName = "";

    @Column(name = "program_name_en")
    private String programNameEnglish = "";

    @Column(name = "description")
    private String description = "";

    @Column(name = "by_document")
    private String byDocument = "";


    @OneToOne
    @JoinColumn(name = "class_id")
    private Classes clazz;


    @Column(name = "is_deleted")
    private Boolean deleted = false;


    public Sample() {

    }

    public Sample(User user, Person teacher, Person headTeacher, Department department, Classes clazz) {
        this.user = user;
        this.teacher = teacher;
        this.headTeacher = headTeacher;
        this.department = department;
        this.clazz = clazz;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public User getUser() {
        return user;
    }

    public Person getTeacher() {
        return teacher;
    }

    public Person getHeadTeacher() {
        return headTeacher;
    }

    public Department getDepartment() {
        return department;
    }

    public Short getYear() {
        return year;
    }

    public String getProgramName() {
        return programName;
    }

    public String getProgramShortName() {
        return programShortName;
    }

    public String getProgramNameEnglish() {
        return programNameEnglish;
    }

    public String getByDocument() {
        return byDocument;
    }

    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public Boolean getDeleted() {
        return deleted;
    }

    public Classes getClazz() {
        return clazz;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setHeadTeacher(Person headTeacher) {
        this.headTeacher = headTeacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public void setClazz(Classes clazz) {
        this.clazz = clazz;
    }
}
