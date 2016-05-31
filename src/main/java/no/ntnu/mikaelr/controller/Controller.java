package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.MissionLocationIn;
import no.ntnu.mikaelr.model.entities.MissionLocation;
import no.ntnu.mikaelr.service.dao.ProjectDao;
import no.ntnu.mikaelr.service.dao.ProjectResponseDao;
import no.ntnu.mikaelr.service.dao.SuggestionDao;
import no.ntnu.mikaelr.service.dao.UserDao;
import no.ntnu.mikaelr.util.TestData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/admin")
public class Controller {

    @Autowired
    private TestData testData;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private SuggestionDao suggestionDao;

    @Autowired
    private ProjectResponseDao projectResponseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    @PreAuthorize(value="hasAuthority('ADMIN')")
    @RequestMapping(value = "/suggestions/{suggestionId}/delete", method = RequestMethod.PUT)
    public void deleteSuggestion(@PathVariable Integer suggestionId) {
        suggestionDao.deleteSuggestion(suggestionId);
    }

    @PreAuthorize(value="hasAuthority('ADMIN')")
    @RequestMapping(value = "/projects/{projectId}/tasks/delete", method = RequestMethod.PUT)
    public void deleteTasks(@PathVariable Integer projectId) {
        projectDao.deleteTasks(projectId);
    }

    @PreAuthorize(value="hasAuthority('ADMIN')")
    @RequestMapping(value = "/responses/delete", method = RequestMethod.PUT)
    public void deleteTaskResponses() {
        projectResponseDao.deleteAllTaskResponses();
    }

    @PreAuthorize(value="hasAuthority('ADMIN')")
    @RequestMapping(value = "/users/{userId}/delete", method = RequestMethod.PUT)
    public void deleteUser(@PathVariable Integer userId) {
        userDao.deleteUser(userId);
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST)
    public void createAdmin() {
        testData.createAdmin();
    }

    @PreAuthorize(value="hasAuthority('ADMIN')")
    @RequestMapping(value = "/createTestData", method = RequestMethod.POST)
    public void createTestData() {
        testData.initializeTestData();
    }

    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/postMissionLocations", method = RequestMethod.POST)
    public ResponseEntity postMissionLocations(@RequestBody List<MissionLocationIn> missionLocationInList) {

        Random random = new Random();
        String characters = "abcdefghijklmnopqrstuvwqyz";

        char[] text = new char[20];
        for (int i = 0; i < 20; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }

        String key = new String(text);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        for (MissionLocationIn missionLocationIn : missionLocationInList) {
            MissionLocation missionLocation = new MissionLocation();
            missionLocation.setKey(key);
            missionLocation.setLatitude(missionLocationIn.getLatitude());
            missionLocation.setLongitude(missionLocationIn.getLongitude());
            session.save(missionLocation);
        }

        session.getTransaction().commit();
        session.close();

        return new ResponseEntity(HttpStatus.OK);
    }


    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Input length violation")  // 400
    @ExceptionHandler(org.hibernate.exception.DataException.class)
    public void inputLengthViolation() {}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> violation() {
        return new ResponseEntity<String>("Exception", HttpStatus.BAD_REQUEST);
    }

}
