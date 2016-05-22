package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_achievement")
public class UserAchievement {

    private Integer userAchievementId;
    private Date date;

    private Achievement achievement;
    private User user;

    // ATTRIBUTE GETTERS -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_achievement_id", unique = true, nullable = false)
    public Integer getUserAchievementId() {
        return userAchievementId;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    // RELATIONS GETTERS -----------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "achievement_id", nullable = false)
    public Achievement getAchievement() {
        return achievement;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    // ATTRIBUTE SETTERS -----------------------------------------------------------------------------------------------

    public void setUserAchievementId(Integer userAchievementId) {
        this.userAchievementId = userAchievementId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // RELATIONS SETTERS -----------------------------------------------------------------------------------------------

    public void setAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
