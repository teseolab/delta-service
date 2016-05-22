package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "achievements")
public class Achievement {

    private Integer acgievementId;
    private String badgeName;
    private String name;
    private String description;

    // ATTRIBUTE GETTERS -----------------------------------------------------------------------------------------------

    @Id
    @Column(name = "achievement_id", unique = true, nullable = false)
    public Integer getAcgievementId() {
        return acgievementId;
    }

    @Column(name = "badge_uri")
    public String getBadgeName() {
        return badgeName;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    // ATTRIBUTE SETTERS -----------------------------------------------------------------------------------------------

    public void setAcgievementId(Integer acgievementId) {
        this.acgievementId = acgievementId;
    }

    public void setBadgeName(String badgUri) {
        this.badgeName = badgUri;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
