package no.ntnu.mikaelr.model.dto.outgoing;

public class UserOut {

    private int id;
    private String username;
    private int score;
    private String avatarUri;
    private int numberOfMissions;
    private int numberOfSuggestions;
    private int numberOfComments;

    public UserOut() {}

    public UserOut(int id, String username) {
        this.id = id;
        this.username = username;
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

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfMissions() {
        return numberOfMissions;
    }

    public void setNumberOfMissions(int numberOfMissions) {
        this.numberOfMissions = numberOfMissions;
    }

    public int getNumberOfSuggestions() {
        return numberOfSuggestions;
    }

    public void setNumberOfSuggestions(int numberOfSuggestions) {
        this.numberOfSuggestions = numberOfSuggestions;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }
}
