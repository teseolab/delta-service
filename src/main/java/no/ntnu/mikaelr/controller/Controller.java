package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.TestData;
import no.ntnu.mikaelr.model.dto.outgoing.UserOutgoing;
import no.ntnu.mikaelr.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Controller {

    @Autowired
    private TestData testData;

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserOutgoing> getFriendships() {

        UserOutgoing mikael = new UserOutgoing();
        mikael.setId(1);
        mikael.setUsername("Mikael");

        return new ResponseEntity<UserOutgoing>(mikael, HttpStatus.OK);
    }


    @PreAuthorize(value="hasAuthority('USER')")
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ResponseEntity<UserOutgoing> testAuth() {

        UserOutgoing mikael = new UserOutgoing();
        mikael.setId(1);
        mikael.setUsername("Mikael");

        return new ResponseEntity<UserOutgoing>(mikael, HttpStatus.OK);
    }

    @RequestMapping(value = "/createTestData", method = RequestMethod.GET)
    public void createTestData() {
        testData.initializeTestData();
    }


    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Input length violation")  // 400
    @ExceptionHandler(org.hibernate.exception.DataException.class)
    public void inputLengthViolation() {}

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> violation() {
        return new ResponseEntity<String>("Exception", HttpStatus.BAD_REQUEST);
    }



}
