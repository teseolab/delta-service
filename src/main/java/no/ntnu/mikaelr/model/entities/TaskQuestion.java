package no.ntnu.mikaelr.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_questions")
public class TaskQuestion {

    private Integer questionId;
    private String question;
    private List<String> alternatives;

    private Task task;

    public TaskQuestion() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", unique = true, nullable = false)
    public Integer getQuestionId() {
        return questionId;
    }

    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "question_alternatives", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "question_alternative", nullable = false)
    public List<String> getAlternatives() {
        return alternatives;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    public Task getTask() {
        return task;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
