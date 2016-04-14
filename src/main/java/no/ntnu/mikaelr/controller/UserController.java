package no.ntnu.mikaelr.controller;

import no.ntnu.mikaelr.model.dto.incoming.UserIncoming;
import no.ntnu.mikaelr.model.dto.outgoing.UserOut;
import no.ntnu.mikaelr.model.entities.User;
import no.ntnu.mikaelr.service.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserOut> createUser(@RequestBody UserIncoming incomingUser) {
        User user = userDao.createUser(incomingUser);
        UserOut response = new UserOut(user.getId(), user.getUsername(), user.getPassword());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<UserOut> getUser(@PathVariable Integer userId) {
        User user = userDao.getUserById(userId);
        UserOut response = new UserOut(user.getId(), user.getUsername(), user.getPassword());
        return new ResponseEntity<UserOut>(response, HttpStatus.OK);
    }

}
