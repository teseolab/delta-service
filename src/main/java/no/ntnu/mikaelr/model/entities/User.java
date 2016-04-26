package no.ntnu.mikaelr.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    private Integer id;
    private String username;
    private String password;
    private Integer score = 0;

    private Set<UserRole> roles = new HashSet<UserRole>(0);

    private Set<Suggestion> suggestions;
    private Set<Comment> comments;
    private Set<FinishedMission> finishedMissions;

    private Set<Agreement> agreements;
    private Set<Disagreement> disagreements;

    public User() {} // Empty constructor is needed

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    @Column(name = "password", unique = true, nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    public Set<UserRole> getRoles() {
        return roles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    public Set<Suggestion> getSuggestions() {
        return suggestions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    public Set<Comment> getComments() {
        return comments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    public Set<FinishedMission> getFinishedMissions() {
        return finishedMissions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Agreement> getAgreements() {
        return agreements;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public Set<Disagreement> getDisagreements() {
        return disagreements;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public void setSuggestions(Set<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setFinishedMissions(Set<FinishedMission> finishedMissions) {
        this.finishedMissions = finishedMissions;
    }

    public void setAgreements(Set<Agreement> agreements) {
        this.agreements = agreements;
    }

    public void setDisagreements(Set<Disagreement> disagreements) {
        this.disagreements = disagreements;
    }
}
