package no.ntnu.mikaelr.model.dto.outgoing;

public class UserOut {

    private int id;
    private String username;
    private String password; //TODO: Remove password?

    public UserOut() {
    }

    public UserOut(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserOut(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
