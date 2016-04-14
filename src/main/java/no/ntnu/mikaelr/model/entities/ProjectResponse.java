package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "project_responses")
public class ProjectResponse {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer id;
    private String[] response;

    // Relations -------------------------------------------------------------------------------------------------------

    private User user;
    private Project project;
    private Task task;

    // Constructors ----------------------------------------------------------------------------------------------------

    public ProjectResponse() {}

    public ProjectResponse(String[] response, User user, Project project, Task task) {
        this.id = id;
        this.response = response;
        this.user = user;
        this.project = project;
        this.task = task;
    }

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_response_id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    @Column(name = "response", nullable = false)
    public String[] getResponse() {
        return response;
    }

    // Relation getters ------------------------------------------------------------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    public Task getTask() {
        return task;
    }

    // Attribute setters -----------------------------------------------------------------------------------------------

    public void setId(Integer id) {
        this.id = id;
    }

    public void setResponse(String[] response) {
        this.response = response;
    }

    // Relation setters ------------------------------------------------------------------------------------------------


    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
