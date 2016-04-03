package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.outgoing.ProjectOutgoing;
import no.ntnu.mikaelr.model.dto.outgoing.TaskOutgoing;
import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Task;
import no.ntnu.mikaelr.service.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectDao projectDao;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProjectOutgoing>> getProjects() {

        List<Project> projects = projectDao.getProjects();
        List<ProjectOutgoing> response = new ArrayList<ProjectOutgoing>();

        for (Project project : projects) {
            ProjectOutgoing outgoingProject = new ProjectOutgoing();
            outgoingProject.setId(project.getId());
            outgoingProject.setName(project.getName());
            outgoingProject.setLatitude(project.getLatitude());
            outgoingProject.setLongitude(project.getLongitude());
            outgoingProject.setDescription(project.getDescription());
            response.add(outgoingProject);
        }

        return new ResponseEntity<List<ProjectOutgoing>>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectOutgoing> getProject(@PathVariable Integer projectId) {
        Project project = projectDao.getProject(projectId);

        ProjectOutgoing response = new ProjectOutgoing();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setLatitude(project.getLatitude());
        response.setLongitude(project.getLongitude());
        response.setDescription(project.getDescription());

        return new ResponseEntity<ProjectOutgoing>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/tasks", method = RequestMethod.GET)
    public ResponseEntity<List<TaskOutgoing>> getTasks(@PathVariable Integer projectId) {

        Set<Task> tasks = projectDao.getTasks(projectId);
        List<TaskOutgoing> response = new ArrayList<TaskOutgoing>();

        for (Task task : tasks) {
            TaskOutgoing outgoingTask = new TaskOutgoing();
            outgoingTask.setId(task.getId());
            outgoingTask.setTaskType(task.getTaskType());
            outgoingTask.setLatitude(task.getLatitude());
            outgoingTask.setLongitude(task.getLongitude());
            outgoingTask.setDescriptions(task.getDescriptions());
            outgoingTask.setHint(task.getHint());
            response.add(outgoingTask);
        }

        return new ResponseEntity<List<TaskOutgoing>>(response, HttpStatus.OK);

    }

}
