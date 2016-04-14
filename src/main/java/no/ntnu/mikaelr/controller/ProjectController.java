package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.ProjectResponseIncoming;
import no.ntnu.mikaelr.model.dto.outgoing.*;
import no.ntnu.mikaelr.model.entities.Comment;
import no.ntnu.mikaelr.model.entities.Project;
import no.ntnu.mikaelr.model.entities.Suggestion;
import no.ntnu.mikaelr.model.entities.Task;
import no.ntnu.mikaelr.service.dao.ProjectDao;
import no.ntnu.mikaelr.service.dao.ProjectResponseDao;
import no.ntnu.mikaelr.service.dao.SuggestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectResponseDao projectResponseDao;

    @Autowired
    private SuggestionDao suggestionDao;

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

        List<Task> tasks = projectDao.getTasks(projectId);
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

    @RequestMapping(value = "/{projectId}/responses", method = RequestMethod.POST)
    public ResponseEntity<ProjectResponseIncoming> postResponse(@PathVariable Integer projectId, @RequestBody ProjectResponseIncoming incoming) {

        if (projectId == incoming.getProjectId()) {
            System.out.println(incoming);
            projectResponseDao.createProjectResponse(incoming);
            return new ResponseEntity<ProjectResponseIncoming>(incoming, HttpStatus.OK);
        }
        return new ResponseEntity<ProjectResponseIncoming>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{projectId}/mission/user/{userId}/isCompleted", method = RequestMethod.GET)
    public ResponseEntity<Boolean> missionForProjectIsCompletedByUser(@PathVariable Integer projectId, @PathVariable Integer userId) {
        Boolean result = projectResponseDao.missionForProjectIsCompletedByUser(projectId, userId);
        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}/suggestions", method = RequestMethod.GET)
    public ResponseEntity<List<SuggestionOut>> getSuggestions(@PathVariable Integer projectId) {

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
            suggestionOut.setAgreements(suggestion.getAgreements());
            suggestionOut.setDisagreements(suggestion.getDisagreements());

            List<Comment> comments = suggestion.getComments();
            List<CommentOut> commentsOut = new ArrayList<CommentOut>();

            for (Comment comment : comments) {
                CommentOut commentOut = new CommentOut();
                commentOut.setId(comment.getId());
                commentOut.setDate(comment.getDate());
                commentOut.setUser(new UserOut(comment.getUser().getId(), comment.getUser().getUsername()));
                commentOut.setComment(comment.getComment());
                commentsOut.add(commentOut);
            }

            suggestionOut.setComments(commentsOut);
            suggestionsOut.add(suggestionOut);
        }

        return new ResponseEntity<List<SuggestionOut>>(suggestionsOut, HttpStatus.OK);
    }

}
