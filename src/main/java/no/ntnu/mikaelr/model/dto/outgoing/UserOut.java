package no.ntnu.mikaelr.model.dto.outgoing;

import no.ntnu.mikaelr.model.entities.User;

public class UserOut {

    private int id;
    private String username;

    public UserOut() {
    }

    public UserOut(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserOut(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
