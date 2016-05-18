package no.ntnu.mikaelr.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import no.ntnu.mikaelr.util.TaskType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer taskId;
    private Integer taskOrder;
    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    private String imageUri;
    private float latitude;
    private float longitude;
    private String hint;
    private String description;
    private List<String> taskElements;

    // Relations -------------------------------------------------------------------------------------------------------

    private Project project;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Task() {}

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", unique = true, nullable = false)
    public Integer getTaskId() {
        return taskId;
    }

    @Column(name = "task_order", nullable = false)
    public Integer getTaskOrder() {
        return taskOrder;
    }

    @Column(name = "task_type", nullable = false)
    public TaskType getTaskType() {
        return taskType;
    }

    @Column(name = "image_uri")
    public String getImageUri() {
        return imageUri;
    }

    @Column(name = "latitude", nullable = false)
    public float getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", nullable = false)
    public float getLongitude() {
        return longitude;
    }

    @Column(name = "hint", nullable = false)
    public String getHint() {
        return hint;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_elements", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "task_element", nullable = false)
    public List<String> getTaskElements() {
        return taskElements;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    public Project getProject() {
        return project;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public void setTaskOrder(Integer order) {
        this.taskOrder = order;
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
    public void setHint(String hint) {
        this.hint = hint;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskElements(List<String> taskElements) {
        this.taskElements = taskElements;
    }

    // Relation setters ------------------------------------------------------------------------------------------------

    public void setProject(Project project) {
        this.project = project;
    }
}
