package no.ntnu.mikaelr.model.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "task_elements")
public class TaskElement {

    private Integer taskElementId;
    private List<String> taskElements;

    public List<String> getTaskElements() {
        return taskElements;
    }

    public void setTaskElements(List<String> taskElements) {
        this.taskElements = taskElements;
    }
}
