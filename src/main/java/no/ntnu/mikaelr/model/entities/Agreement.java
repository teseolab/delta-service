package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "agreements")
public class Agreement {

    private Integer id;
    private User user;
    private Suggestion suggestion;

    public Agreement() {}

    public Agreement(Suggestion suggestion, User user) {
        this.suggestion = suggestion;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreement_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suggestion_id", nullable = false)
    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
