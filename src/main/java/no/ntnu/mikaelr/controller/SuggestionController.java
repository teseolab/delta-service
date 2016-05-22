package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.SuggestionIn;
import no.ntnu.mikaelr.model.dto.outgoing.*;
import no.ntnu.mikaelr.model.entities.*;
import no.ntnu.mikaelr.security.SessionUser;
import no.ntnu.mikaelr.service.dao.AchievementDao;
import no.ntnu.mikaelr.service.dao.LogRecordDao;
import no.ntnu.mikaelr.service.dao.SuggestionDao;
import no.ntnu.mikaelr.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    @Autowired
    private SuggestionDao suggestionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LogRecordDao logRecordDao;

    @Autowired
    private AchievementDao achievementDao;

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SuggestionOut> postSuggestion(@RequestBody SuggestionIn in) {
        Suggestion suggestion = suggestionDao.createSuggestion(in);
        SuggestionOut out = new SuggestionOut(suggestion);
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        achievementDao.addSuggestionAchievement(userId);
        return new ResponseEntity<SuggestionOut>(out, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{suggestionId}/agree", method = RequestMethod.POST)
    public ResponseEntity postAgreement(@PathVariable Integer suggestionId) {
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        MultiValueMap<String, String> result = suggestionDao.postAgreement(suggestionId, userId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{suggestionId}/disagree", method = RequestMethod.POST)
    public ResponseEntity postDisagreement(@PathVariable Integer suggestionId) {
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
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
            UserOut userOut = new UserOut();
            userOut.setId(comment.getUser().getId());
            userOut.setUsername(comment.getUser().getUsername());
            userOut.setAvatarUri(comment.getUser().getAvatarUri());
            commentOut.setUser(userOut);
            commentOut.setComment(comment.getComment());
            commentsOut.add(commentOut);
        }

        return new ResponseEntity<List<CommentOut>>(commentsOut, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{suggestionId}/comments", method = RequestMethod.POST)
    public ResponseEntity<List<CommentOut>> postComment(@PathVariable Integer suggestionId, @RequestBody String commentText) {

        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        List<Comment> comments = suggestionDao.postComment(suggestionId, userId, commentText);
        logRecordDao.logCommentPosted(suggestionId, userId);
        List<CommentOut> commentsOut = new ArrayList<CommentOut>();

        for (Comment comment : comments) {
            CommentOut commentOut = new CommentOut();
            commentOut.setId(comment.getId());
            commentOut.setDate(comment.getDate());
            UserOut userOut = new UserOut();
            userOut.setId(comment.getUser().getId());
            userOut.setUsername(comment.getUser().getUsername());
            userOut.setAvatarUri(comment.getUser().getAvatarUri());
            commentOut.setUser(userOut);
            commentOut.setComment(comment.getComment());
            commentsOut.add(commentOut);
        }

        achievementDao.addCommentAchievement(userId);

        return new ResponseEntity<List<CommentOut>>(commentsOut, HttpStatus.OK);
    }

}
