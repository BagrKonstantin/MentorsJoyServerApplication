package mentorsjoy.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "users", name = "info")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @NotNull

    private Person person;

    @Column(name = "email")
    private String email;


    @NotBlank
    @Size(max = 20)
    @Column(name = "username")
    private String username;
//
//
    @NotBlank
    @Size(max = 120)
    @Column(name = "password")
    @JsonIgnore
    private String password;


    public User() {
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "settings", name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(String username, String email, String password, Person person) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public String getEmail() {
        return email;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
