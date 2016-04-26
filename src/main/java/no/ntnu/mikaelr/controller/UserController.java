package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.UserIn;
import no.ntnu.mikaelr.model.dto.outgoing.UserOut;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.security.SessionUser;
import no.ntnu.mikaelr.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserOut>> getHighscoreUsers() {

        List<User> topUsers = userDao.getTopUsers();
        List<UserOut> users = new ArrayList<UserOut>();

        for (User user : topUsers) {

            int userId = user.getId();

            UserOut userOut = new UserOut();
            userOut.setId(userId);
            userOut.setUsername(user.getUsername());
            userOut.setScore(user.getScore());

            int numberOfMissions = userDao.getNumberOfMissionsCompleted(userId);
            int numberOfSuggestionsPosted = userDao.getNumberOfSuggestionsPosted(userId);
            int numberOfCommentsPosted = userDao.getNumberOfCommentsPosted(userId);

            userOut.setNumberOfMissions(numberOfMissions);
            userOut.setNumberOfSuggestions(numberOfSuggestionsPosted);
            userOut.setNumberOfComments(numberOfCommentsPosted);

            users.add(userOut);
        }

        return new ResponseEntity<List<UserOut>>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserIn incomingUser) {

        List<String> registerCodes = new ArrayList<String>();
        registerCodes.add("alfa");

        String registerCode = incomingUser.getRegisterCode();

        if (registerCodes.contains(registerCode)) {
            User user = userDao.createUser(incomingUser);
            UserOut response = new UserOut();
            response.setId(user.getId());
            response.setUsername(user.getUsername());
            return new ResponseEntity<UserOut>(response, HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.PRECONDITION_FAILED);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserOut> getSelf(@PathVariable Integer userId) {

        if (userId == 0) {
            userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        }

        User user = userDao.getUserById(userId);

        UserOut userOut = new UserOut();
        userOut.setId(userId);
        userOut.setUsername(user.getUsername());
        userOut.setScore(user.getScore());

        int numberOfMissions = userDao.getNumberOfMissionsCompleted(userId);
        int numberOfSuggestionsPosted = userDao.getNumberOfSuggestionsPosted(userId);
        int numberOfCommentsPosted = userDao.getNumberOfCommentsPosted(userId);

        userOut.setNumberOfMissions(numberOfMissions);
        userOut.setNumberOfSuggestions(numberOfSuggestionsPosted);
        userOut.setNumberOfComments(numberOfCommentsPosted);

        return new ResponseEntity<UserOut>(userOut, HttpStatus.OK);
    }

}
