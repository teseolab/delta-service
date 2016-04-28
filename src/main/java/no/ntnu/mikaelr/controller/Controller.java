package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.util.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class Controller {

    @Autowired
    private TestData testData;

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
