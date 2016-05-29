package no.ntnu.mikaelr.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private String username;
    private String password;
    private Integer score = 0;
    private String avatarUri;

    // Relations -------------------------------------------------------------------------------------------------------

    private Set<UserRole> roles = new HashSet<UserRole>(0);

    private Set<Suggestion> suggestions;
    private Set<Comment> comments;
    private Set<FinishedMission> finishedMissions;
    private Set<Agreement> agreements;
    private Set<Disagreement> disagreements;
    private List<LogRecord> logRecords;
    private List<UserAchievement> achievements;
    private List<TaskResponse> taskResponses;

    // Constructors ----------------------------------------------------------------------------------------------------

    public User() {} // Empty constructor is needed

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Attribute getters -----------------------------------------------------------------------------------------------

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

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    @Column(name = "avatar_uri")
    public String getAvatarUri() {
        return avatarUri;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<UserRole> getRoles() {
        return roles;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<Suggestion> getSuggestions() {
        return suggestions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<Comment> getComments() {
        return comments;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<FinishedMission> getFinishedMissions() {
        return finishedMissions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Agreement> getAgreements() {
        return agreements;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<Disagreement> getDisagreements() {
        return disagreements;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public List<LogRecord> getLogRecords() {
        return logRecords;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<UserAchievement> getAchievements() {
        return achievements;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public List<TaskResponse> getTaskResponses() {
        return taskResponses;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

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

    public void incrementScore(int score) {
        this.score += score;
    }

    public void decrementScore(int score) {
        this.score -= score;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    // Relation setters ------------------------------------------------------------------------------------------------

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

    public void setLogRecords(List<LogRecord> logRecords) {
        this.logRecords = logRecords;
    }

    public void setAchievements(List<UserAchievement> achievements) {
        this.achievements = achievements;
    }

    public void setTaskResponses(List<TaskResponse> taskResponses) {
        this.taskResponses = taskResponses;
    }
}
