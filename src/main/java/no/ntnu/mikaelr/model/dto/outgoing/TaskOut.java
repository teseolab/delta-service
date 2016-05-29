package no.ntnu.mikaelr.model.dto.outgoing;

import no.ntnu.mikaelr.util.TaskType;

import java.util.List;

public class TaskOut {

    private Integer id;
    private Integer order;
    private boolean finished;
    private TaskType taskType;
    private String imageUri;
    private String hint;
    private float latitude;
    private float longitude;
    private String description;

    private List<TaskQuestionOut> taskQuestions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TaskQuestionOut> getTaskQuestions() {
        return taskQuestions;
    }

    public void setTaskQuestions(List<TaskQuestionOut> taskQuestions) {
        this.taskQuestions = taskQuestions;
    }
}
