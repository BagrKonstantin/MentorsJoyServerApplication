package mentorsjoy.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(schema = "extra", name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "firstname")
    private String firstname = "";

    @Column(name = "surname")
    private String surname = "";

    @Column(name = "lastname")
    private String lastname = "";

    @Column(name = "status")
    private String status = "";


    public Integer getPersonId() {
        return personId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSurname() {
        return surname;
    }

    public String getStatus() {
        return status;
    }

    @JsonIgnore
    public String getInRequiredFormat() {
        if (firstname.isEmpty() || surname.isEmpty()) {
            return "";
        }
        return firstname.substring(0, 1) + '.' + (lastname.isEmpty() ? ' ' : lastname.charAt(0) + ". ") + surname;
    }
}
