package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.TaskResponseIn;
import no.ntnu.mikaelr.model.dto.outgoing.*;
import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.security.SessionUser;
import no.ntnu.mikaelr.service.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectResponseDao projectResponseDao;

    @Autowired
    private SuggestionDao suggestionDao;

    @PreAuthorize(value="hasAuthority('USER')")
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
            outgoingProject.setImageUri(project.getImageUri());
            response.add(outgoingProject);
        }

        return new ResponseEntity<List<ProjectOutgoing>>(response, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public ResponseEntity<ProjectOutgoing> getProject(@PathVariable Integer projectId) {
        Project project = projectDao.getProject(projectId);

        ProjectOutgoing response = new ProjectOutgoing();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setLatitude(project.getLatitude());
        response.setLongitude(project.getLongitude());
        response.setDescription(project.getDescription());
        response.setImageUri(project.getImageUri());

        return new ResponseEntity<ProjectOutgoing>(response, HttpStatus.OK);
    }

    //@PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{projectId}/tasks", method = RequestMethod.GET)
    public ResponseEntity<List<TaskOut>> getTasks(@PathVariable Integer projectId) {

        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        User user = userDao.getUserById(userId);
        Project project = projectDao.getProject(projectId);
        List<Task> tasks = projectDao.getTasks(project);
        List<TaskOut> tasksOut = new ArrayList<TaskOut>();

        for (Task task : tasks) {
            TaskOut taskOut = new TaskOut();
            taskOut.setId(task.getTaskId());
            taskOut.setOrder(task.getTaskOrder());
            taskOut.setFinished(projectResponseDao.taskIsFinished(user, project, task));
            taskOut.setTaskType(task.getTaskType());
            taskOut.setImageUri(task.getImageUri());
            taskOut.setLatitude(task.getLatitude());
            taskOut.setLongitude(task.getLongitude());
            taskOut.setDescription(task.getDescription());
            taskOut.setTaskElements(task.getTaskElements());
            taskOut.setHint(task.getHint());
            tasksOut.add(taskOut);
        }

        return new ResponseEntity<List<TaskOut>>(tasksOut, HttpStatus.OK);

    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{projectId}/responses", method = RequestMethod.POST)
    public ResponseEntity<TaskResponseIn> postResponse(@PathVariable Integer projectId, @RequestBody TaskResponseIn incoming) {

        if (projectId == incoming.getProjectId()) {
            projectResponseDao.createProjectResponse(incoming);
            return new ResponseEntity<TaskResponseIn>(incoming, HttpStatus.OK);
        }
        return new ResponseEntity<TaskResponseIn>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{projectId}/mission/complete", method = RequestMethod.POST)
    public ResponseEntity setMissionComplete(@PathVariable Integer projectId) {
        Integer userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        projectDao.setMissionComplete(projectId, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{projectId}/mission/isCompleted", method = RequestMethod.GET)
    public ResponseEntity<Boolean> missionForProjectIsCompletedByUser(@PathVariable Integer projectId) {
        Boolean result = projectResponseDao.missionForProjectIsCompletedByUser(projectId);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{projectId}/suggestions", method = RequestMethod.GET)
    public ResponseEntity<List<SuggestionOut>> getSuggestions(@PathVariable Integer projectId) {

        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();

        User user = userDao.getUserById(userId);

        List<Suggestion> suggestions = suggestionDao.getSuggestions(projectId);
        List<SuggestionOut> suggestionsOut = new ArrayList<SuggestionOut>();

        for (Suggestion suggestion : suggestions) {

            SuggestionOut suggestionOut = new SuggestionOut();
            suggestionOut.setId(suggestion.getId());
            suggestionOut.setDate(suggestion.getDate());
            suggestionOut.setImageUri(suggestion.getImageUri());
            suggestionOut.setUser(new UserOut(suggestion.getUser().getId(), suggestion.getUser().getUsername()));
            suggestionOut.setTitle(suggestion.getTitle());
            suggestionOut.setDetails(suggestion.getDetails());
            suggestionOut.setAgreements(suggestion.getAgreements().size());
            suggestionOut.setDisagreements(suggestion.getDisagreements().size());
            suggestionOut.setAgrees(suggestionDao.userAgrees(suggestion, user));
            suggestionsOut.add(suggestionOut);
        }

        return new ResponseEntity<List<SuggestionOut>>(suggestionsOut, HttpStatus.OK);
    }

}
