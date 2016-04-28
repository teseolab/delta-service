package no.ntnu.mikaelr.model.entities;

import no.ntnu.mikaelr.util.LogRecordType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log_record")
public class LogRecord {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private Date date;
    private String description;
    @Enumerated(EnumType.STRING)
    private LogRecordType type;
    private Integer generatedScore;

    // Relations -------------------------------------------------------------------------------------------------------

    private User user;
    private Suggestion suggestion;
    private Project project;

    // Constructors ----------------------------------------------------------------------------------------------------

    public LogRecord() {}

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_record_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "type", nullable = false)
    public LogRecordType getType() {
        return type;
    }

    @Column(name = "score", nullable = false)
    public Integer getGeneratedScore() {
        return generatedScore;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_id")
    public Suggestion getSuggestion() {
        return suggestion;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(LogRecordType type) {
        this.type = type;
    }

    public void setGeneratedScore(Integer generatedScore) {
        this.generatedScore = generatedScore;
    }

    // Relation setters ------------------------------------------------------------------------------------------------


    public void setUser(User user) {
        this.user = user;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
