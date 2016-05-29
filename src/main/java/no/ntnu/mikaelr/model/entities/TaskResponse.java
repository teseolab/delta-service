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
    private TaskQuestion question;

    // Constructors ----------------------------------------------------------------------------------------------------

    public TaskResponse() {}

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
    @JoinColumn(name = "question_id", nullable = false)
    public TaskQuestion getQuestion() {
        return question;
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

    public void setQuestion(TaskQuestion task) {
        this.question = task;
    }
}
