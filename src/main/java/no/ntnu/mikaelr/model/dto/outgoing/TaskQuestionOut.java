package no.ntnu.mikaelr.model.dto.outgoing;

import java.util.List;

public class TaskQuestionOut {

    private Integer id;
    private String question;
    private List<String> alternatives;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }
}
