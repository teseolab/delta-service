package no.ntnu.mikaelr.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private String name;
    private String description;
    private String imageUri;
    private float latitude;
    private float longitude;
    private boolean missionEnabled;

    // Relations -------------------------------------------------------------------------------------------------------

    private List<Task> tasks;
    private List<Suggestion> suggestions;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Project() {}

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "description", nullable = false, columnDefinition = "varchar(600)")
    public String getDescription() {
        return description;
    }

    @Column(name = "image_uri")
    public String getImageUri() {
        return imageUri;
    }

    @Column(name = "latitude", nullable = true)
    public float getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", nullable = true)
    public float getLongitude() {
        return longitude;
    }

    @Column(name = "mission_enabled")
    public boolean isMissionEnabled() {
        return missionEnabled;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonManagedReference
    public List<Task> getTasks() {
        return tasks;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonManagedReference
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setMissionEnabled(boolean missionEnabled) {
        this.missionEnabled = missionEnabled;
    }

    // Relation setters ------------------------------------------------------------------------------------------------

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
}
