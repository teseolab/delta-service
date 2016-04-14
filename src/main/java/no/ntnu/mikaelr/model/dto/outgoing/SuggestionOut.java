package no.ntnu.mikaelr.model.dto.outgoing;

import java.util.Date;
import java.util.List;

public class SuggestionOut {

    private Integer id;
    private Date date;
    private String imageUri;
    private String title;
    private String details;
    private Integer agreements;
    private Integer disagreements;

    private UserOut user;
    private List<CommentOut> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getAgreements() {
        return agreements;
    }

    public void setAgreements(Integer agreements) {
        this.agreements = agreements;
    }

    public Integer getDisagreements() {
        return disagreements;
    }

    public void setDisagreements(Integer disagreements) {
        this.disagreements = disagreements;
    }

    public UserOut getUser() {
        return user;
    }

    public void setUser(UserOut user) {
        this.user = user;
    }

    public List<CommentOut> getComments() {
        return comments;
    }

    public void setComments(List<CommentOut> comments) {
        this.comments = comments;
    }
}