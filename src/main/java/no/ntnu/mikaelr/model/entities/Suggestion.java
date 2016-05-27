package no.ntnu.mikaelr.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import sun.rmi.runtime.Log;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "suggestions")
public class Suggestion {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private Date date;
    private String imageUri;
    private String title;
    private String details;

    // Relations -------------------------------------------------------------------------------------------------------

    private User user;
    private Project project;
    private List<Comment> comments;
    private Set<Agreement> agreements;
    private Set<Disagreement> disagreements;
    private Set<LogRecord> logRecords;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Suggestion() {}

    public Suggestion(String title, String details, Date date, String imageUri) {
        this.title = title;
        this.details = details;
        this.date = date;
        this.imageUri = imageUri;
    }

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suggestion_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    @Column(name = "image_uri")
    public String getImageUri() {
        return imageUri;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "details", nullable = false, columnDefinition = "varchar(600)")
    public String getDetails() {
        return details;
    }


    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "suggestion", cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<Comment> getComments() {
        return comments;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "suggestion", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<Agreement> getAgreements() {
        return agreements;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "suggestion", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<Disagreement> getDisagreements() {
        return disagreements;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "suggestion", cascade = CascadeType.ALL)
    @JsonManagedReference
    public Set<LogRecord> getLogRecords() {
        return logRecords;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }



    // Relation setters ------------------------------------------------------------------------------------------------


    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void setAgreements(Set<Agreement> agreements) {
        this.agreements = agreements;
    }

    public void setDisagreements(Set<Disagreement> disagreements) {
        this.disagreements = disagreements;
    }

    public void setLogRecords(Set<LogRecord> logRecords) {
        this.logRecords = logRecords;
    }
}
