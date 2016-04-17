package no.ntnu.mikaelr.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import no.ntnu.mikaelr.model.dto.incoming.SuggestionIn;
import no.ntnu.mikaelr.model.dto.outgoing.*;
import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.service.dao.SuggestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    @Autowired
    private SuggestionDao suggestionDao;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SuggestionOut> postSuggestion(@RequestBody SuggestionIn in) {
        Suggestion suggestion = suggestionDao.createSuggestion(in);
        SuggestionOut out = new SuggestionOut(suggestion);
        return new ResponseEntity<SuggestionOut>(out, HttpStatus.OK);
    }

    @RequestMapping(value = "/{suggestionId}/agree", method = RequestMethod.POST)
    public ResponseEntity postAgreement(@PathVariable Integer suggestionId) {
        int userId = 1; //TODO: Use real user id
        MultiValueMap<String, String> result = suggestionDao.postAgreement(suggestionId, userId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{suggestionId}/disagree", method = RequestMethod.POST)
    public ResponseEntity postDisagreement(@PathVariable Integer suggestionId) {
        int userId = 1; //TODO: Use real user id
        MultiValueMap<String, String> result = suggestionDao.postDisagreement(suggestionId, userId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{suggestionId}/comments", method = RequestMethod.GET)
    public ResponseEntity<List<CommentOut>> getComments(@PathVariable Integer suggestionId) {

        List<Comment> comments = suggestionDao.getComments(suggestionId);
        List<CommentOut> commentsOut = new ArrayList<CommentOut>();

        for (Comment comment : comments) {
            CommentOut commentOut = new CommentOut();
            commentOut.setId(comment.getId());
            commentOut.setDate(comment.getDate());
            commentOut.setUser(new UserOut(comment.getUser().getId(), comment.getUser().getUsername()));
            commentOut.setComment(comment.getComment());
            commentsOut.add(commentOut);
        }

        return new ResponseEntity<List<CommentOut>>(commentsOut, HttpStatus.OK);
    }

    @RequestMapping(value = "/{suggestionId}/comments", method = RequestMethod.POST)
    public ResponseEntity<List<CommentOut>> postComment(@PathVariable Integer suggestionId, @RequestBody String commentText) {

        List<Comment> comments = suggestionDao.postComment(suggestionId, commentText);
        List<CommentOut> commentsOut = new ArrayList<CommentOut>();

        for (Comment comment : comments) {
            CommentOut commentOut = new CommentOut();
            commentOut.setId(comment.getId());
            commentOut.setDate(comment.getDate());
            commentOut.setUser(new UserOut(comment.getUser().getId(), comment.getUser().getUsername()));
            commentOut.setComment(comment.getComment());
            commentsOut.add(commentOut);
        }

        return new ResponseEntity<List<CommentOut>>(commentsOut, HttpStatus.OK);
    }

}
