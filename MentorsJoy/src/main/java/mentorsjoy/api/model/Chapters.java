package mentorsjoy.api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema = "classificatory", name = "chapters")
public class Chapters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Integer chapterId;

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @OneToMany
    @JoinColumn(name = "chapter_id")
    private List<Classes> classes;

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public List<Classes> getClasses() {
        return classes;
    }
}
