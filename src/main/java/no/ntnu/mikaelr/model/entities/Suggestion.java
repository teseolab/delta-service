package no.ntnu.mikaelr.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "suggestions")
public class Suggestion {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private Date date;
    private String imageUri;
    private String title;
    private String details;
    private Integer agreements = 0;
    private Integer disagreements = 0;

    // Relations -------------------------------------------------------------------------------------------------------

    private User user;
    private Project project;
    private List<Comment> comments;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Suggestion() {}

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

    @Column(name = "agreements")
    public Integer getAgreements() {
        return agreements;
    }

    @Column(name = "disagreements")
    public Integer getDisagreements() {
        return disagreements;
    }


    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "suggestion")
    @JsonManagedReference
    public List<Comment> getComments() {
        return comments;
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

    public void setAgreements(Integer agreements) {
        this.agreements = agreements;
    }

    public void setDisagreements(Integer disagreements) {
        this.disagreements = disagreements;
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
}
