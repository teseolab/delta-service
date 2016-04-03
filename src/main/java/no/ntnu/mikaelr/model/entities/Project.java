package no.ntnu.mikaelr.model.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private String name;
    private String description;
    private float latitude;
    private float longitude;

    // Relations -------------------------------------------------------------------------------------------------------

    private Set<Task> tasks;

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

    @Column(name = "latitude", nullable = true)
    public float getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", nullable = true)
    public float getLongitude() {
        return longitude;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    public Set<Task> getTasks() {
        return tasks;
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

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    // Relation setters ------------------------------------------------------------------------------------------------

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
