package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.UserIn;
import no.ntnu.mikaelr.model.dto.outgoing.AchievementOut;
import no.ntnu.mikaelr.model.dto.outgoing.LogRecordOut;
import no.ntnu.mikaelr.model.dto.outgoing.UserOut;
import no.ntnu.mikaelr.model.entities.Achievement;
import no.ntnu.mikaelr.model.entities.LogRecord;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.model.entities.UserAchievement;
import no.ntnu.mikaelr.security.SessionUser;
import no.ntnu.mikaelr.service.dao.AchievementDao;
import no.ntnu.mikaelr.service.dao.LogRecordDao;
import no.ntnu.mikaelr.service.dao.UserDao;
import no.ntnu.mikaelr.util.Constants;
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

    @Autowired
    private LogRecordDao logRecordDao;

    @Autowired
    private AchievementDao achievementDao;

    @PreAuthorize(value="hasAuthority('USER')")
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
            userOut.setAvatarUri(user.getAvatarUri());

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
    public ResponseEntity<UserOut> createUser(@RequestBody UserIn incomingUser) {

        List<String> registerCodes = new ArrayList<String>();
        registerCodes.add("alfa");

        String registerCode = incomingUser.getRegisterCode();

        if (registerCodes.contains(registerCode)) {

            User existingUser = userDao.getUserByUsername(incomingUser.getUsername());

            if (existingUser != null) {
                return new ResponseEntity(null, HttpStatus.CONFLICT);
            }

            User user = userDao.createUser(incomingUser);
            UserOut response = new UserOut();
            response.setId(user.getId());
            response.setUsername(user.getUsername());

            achievementDao.addRegisterAchievement(user);

            return new ResponseEntity<UserOut>(response, HttpStatus.OK);
        }

        return new ResponseEntity<UserOut>(HttpStatus.PRECONDITION_FAILED);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{userIdOrMe}", method = RequestMethod.GET)
    public ResponseEntity<UserOut> getUser(@PathVariable String userIdOrMe) {

        int userId;
        if (userIdOrMe.equals("me")) {
            userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        } else {
            userId = Integer.valueOf(userIdOrMe);
        }

        User user = userDao.getUserById(userId);

        UserOut userOut = new UserOut();
        userOut.setId(userId);
        userOut.setUsername(user.getUsername());
        userOut.setScore(user.getScore());
        userOut.setAvatarUri(user.getAvatarUri());

        int numberOfMissions = userDao.getNumberOfMissionsCompleted(userId);
        int numberOfSuggestionsPosted = userDao.getNumberOfSuggestionsPosted(userId);
        int numberOfCommentsPosted = userDao.getNumberOfCommentsPosted(userId);

        userOut.setNumberOfMissions(numberOfMissions);
        userOut.setNumberOfSuggestions(numberOfSuggestionsPosted);
        userOut.setNumberOfComments(numberOfCommentsPosted);

        return new ResponseEntity<UserOut>(userOut, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/me/logRecords", method = RequestMethod.GET)
    public ResponseEntity<List<LogRecordOut>> getMyLogRecords() {

        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        List<LogRecord> logRecords = logRecordDao.getLogRecords(userId);
        List<LogRecordOut> logRecordsOut = new ArrayList<LogRecordOut>();

        for (LogRecord logRecord : logRecords) {
            logRecordsOut.add(LogRecordOut.fromLogRecord(logRecord));
        }

        return new ResponseEntity<List<LogRecordOut>>(logRecordsOut, HttpStatus.OK);

    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/me/comments/achievement", method = RequestMethod.GET)
    public ResponseEntity getCommentAchievement() {
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        int commentCount = userDao.getNumberOfCommentsPosted(userId);
        if (commentCount == 5) {
            Achievement achievement = achievementDao.getAchievement(Constants.ACHIEVEMENT_KOMMENTARER_V1);
            return new ResponseEntity<Achievement>(achievement, HttpStatus.OK);
        }
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/me/suggestions/achievement", method = RequestMethod.GET)
    public ResponseEntity getSuggestionAchievement() {
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        int suggestionCount = userDao.getNumberOfSuggestionsPosted(userId);
        if (suggestionCount == 1) {
            Achievement achievement = achievementDao.getAchievement(Constants.ACHIEVEMENT_DELTAKER_V2);
            return new ResponseEntity<Achievement>(achievement, HttpStatus.OK);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/me/avatar", method = RequestMethod.PUT)
    public ResponseEntity putAvatar(@RequestBody String avatarUri) {
        int userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        userDao.putAvatarUri(avatarUri, userId);
        Achievement achievement = achievementDao.addUploadedAvatarAchievement(userId);
        return new ResponseEntity<Achievement>(achievement, HttpStatus.OK);
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/{userIdOrMe}/achievements", method = RequestMethod.GET)
    public ResponseEntity<List<AchievementOut>> getUserAchievements(@PathVariable String userIdOrMe) {

        int userId;
        if (userIdOrMe.equals("me")) {
            userId = ((SessionUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        } else {
            userId = Integer.valueOf(userIdOrMe);
        }

        User user = userDao.getUserById(userId);
        List<UserAchievement> userAchievements = userDao.getUserAchievements(user);
        List<AchievementOut> achievementsOut = new ArrayList<AchievementOut>();

        for (UserAchievement userAchievement : userAchievements) {
            AchievementOut achievementOut = new AchievementOut();
            Achievement achievement = userAchievement.getAchievement();
            achievementOut.setName(achievement.getName());
            achievementOut.setDescription(achievement.getDescription());
            achievementOut.setBadgeName(achievement.getBadgeName());
            achievementsOut.add(achievementOut);
        }

        return new ResponseEntity<List<AchievementOut>>(achievementsOut, HttpStatus.OK);
    }

}
