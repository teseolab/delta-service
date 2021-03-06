package no.ntnu.mikaelr.model.dto.outgoing;

public class ProjectOutgoing {

    private Integer id;
    private String name;
    private String description;
    private String imageUri;
    private float latitude;
    private float longitude;
    private boolean missionEnabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public boolean isMissionEnabled() {
        return missionEnabled;
    }

    public void setMissionEnabled(boolean missionEnabled) {
        this.missionEnabled = missionEnabled;
    }
}
