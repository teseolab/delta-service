package no.ntnu.mikaelr.model.entities;

import no.ntnu.mikaelr.TaskType;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    @Enumerated(EnumType.STRING)
    private TaskType taskType;
    private float latitude;
    private float longitude;
    private String hint;
    private String[] descriptions;

    // Relations -------------------------------------------------------------------------------------------------------

    private Project project;

    // Constructors ----------------------------------------------------------------------------------------------------

    public Task() {}

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "task_type", nullable = false)
    public TaskType getTaskType() {
        return taskType;
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

    @Column(name = "descriptions", nullable = false)
    public String[] getDescriptions() {
        return descriptions;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
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

    public void setDescriptions(String[] descriptions) {
        this.descriptions = descriptions;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    // Relation setters ------------------------------------------------------------------------------------------------

    public void setProject(Project project) {
        this.project = project;
    }
}
