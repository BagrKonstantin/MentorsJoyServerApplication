package mentorsjoy.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(schema = "classificatory", name = "classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(name = "chapter_id", referencedColumnName = "chapter_id")
    private Chapters chapters;


    public Integer getClassId() {
        return classId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @JsonIgnore
    public String getChapterCode() {
        return chapters.getCode();
    }
}
