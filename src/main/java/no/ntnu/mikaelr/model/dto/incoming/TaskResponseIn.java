package no.ntnu.mikaelr.model.dto.incoming;

import java.util.List;

public class TaskResponseIn {

    private Integer questionId;
    private List<String> response;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

}
