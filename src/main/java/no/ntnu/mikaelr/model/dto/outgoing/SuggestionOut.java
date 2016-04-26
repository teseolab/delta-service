package no.ntnu.mikaelr.model.dto.outgoing;

import no.ntnu.mikaelr.model.entities.Suggestion;
import no.ntnu.mikaelr.util.Constants;

import java.util.Date;
import java.util.Set;

public class SuggestionOut {

    private Integer id;
    private Date date;
    private String imageUri;
    private String title;
    private String details;
    private Integer agreements = 0;
    private Integer disagreements = 0;
    private String agrees = Constants.NA;

    private UserOut user;

    public SuggestionOut() {}

    public SuggestionOut(Suggestion suggestion) {
        this.id = suggestion.getId();
        this.date = suggestion.getDate();
        this.imageUri = suggestion.getImageUri();
        this.title = suggestion.getTitle();
        this.details = suggestion.getDetails();
        Set agreements = suggestion.getAgreements();
        if (agreements != null) this.agreements = agreements.size();
        Set disagreements = suggestion.getDisagreements();
        if (disagreements != null) this.disagreements = suggestion.getDisagreements().size();
        this.user = new UserOut(suggestion.getUser().getId(), suggestion.getUser().getUsername());
    }

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

    public String getAgrees() {
        return agrees;
    }

    public void setAgrees(String agrees) {
        this.agrees = agrees;
    }

    public UserOut getUser() {
        return user;
    }

    public void setUser(UserOut user) {
        this.user = user;
    }

}
