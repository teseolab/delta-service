package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
public class Comment {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private Date date;
    private String comment;

    // Relations -------------------------------------------------------------------------------------------------------

    private User user;
    private Suggestion suggestion;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Comment() {}

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    @Column(name = "comment", nullable = false, columnDefinition = "varchar(600)")
    public String getComment() {
        return comment;
    }


    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggestion_id", nullable = false)
    public Suggestion getSuggestion() {
        return suggestion;
    }


    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    // Relation setters ------------------------------------------------------------------------------------------------


    public void setUser(User user) {
        this.user = user;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
