package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_responses")
public class TaskResponse {

    // Attributes ------------------------------------------------------------------------------------------------------

    private Integer responseId;
    private List<String> response;

    // Relations -------------------------------------------------------------------------------------------------------

    private User user;
    private Project project;
    private Task task;

    // Constructors ----------------------------------------------------------------------------------------------------

    public TaskResponse() {}

    public TaskResponse(List<String> response, User user, Project project, Task task) {
        this.response = response;
        this.user = user;
        this.project = project;
        this.task = task;
    }

    // Attribute getters -----------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id", unique = true, nullable = false)
    public Integer getResponseId() {
        return responseId;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_response_elements", joinColumns = @JoinColumn(name = "response_id"))
    @Column(name = "response_element", nullable = false)
    public List<String> getResponse() {
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

    public void setResponseId(Integer responseId) {
        this.responseId = responseId;
    }


    public void setResponse(List<String> response) {
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
