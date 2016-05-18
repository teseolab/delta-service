package no.ntnu.mikaelr.model.dto.incoming;

import java.util.List;

public class TaskResponseIn {

    private List<String> response;
    private int projectId;
    private int taskId;

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
