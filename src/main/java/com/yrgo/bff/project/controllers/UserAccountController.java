package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.exception.BadRequestException;
import com.yrgo.bff.project.exception.InternalServerErrorException;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import com.yrgo.bff.project.service.useraccount.UserAccountServiceImplementation;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * UserAccountController
 *
 * REST Controller for UserAccount CRUD operations.
 *
 **/


@RestController
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private Log log = LogFactory.getLog(getClass());

    public UserAccountController(UserAccountService userAccountService, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userAccountService = userAccountService;
    }

    /**
     * @param user jsonobject with the variables username and password
     * @return proper status code and the user data if available
     * @throws BadRequestException when username is invalid/already exists
     */
    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody JSONObject user) throws BadRequestException {
        //parsing
        final String username = (String)user.get("username");
        final String password = (String)user.get("password");

        if (!UserAccountServiceImplementation.validEmailAddress(username)) {
            final String msg = "Invalid email address: " + username;
            log.error(msg);
            throw new BadRequestException(msg);
        }

        if (userAccountService.readUser(username)==null) {
        userAccountService.createUser(username, password);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(userAccountService.readUser(username).toJSON());
        } else {
            final String msg = "Username already exists: " + username;
            log.error(msg);
            throw new BadRequestException(msg);
        }
    }

    /**
     * @param username username
     * @return the data of the user
     */
    @GetMapping("/user")
    JSONObject readUser(@RequestParam String username) {
        return userAccountService.readUser(username).toJSON();
    }

    /**
     * @return the future and historic games of the logged in user
     */
    @GetMapping("/user/previousgames")
    JSONObject readUsersPreviousGames() {
        return userAccountService.readLoggedInUser().getPreviousGamesAsJSON();
    }

    /**
     * @return the data of the logged in user
     */
    @GetMapping("/loggedinuser")
    public JSONObject readUser() {
        try {
            return userAccountService.readLoggedInUser().toJSON();
        } catch (NullPointerException e) {
                final String msg = "Cannot read logged in user";
                log.error(msg);
                throw new InternalServerErrorException(msg);
        }
    }

    /**
     * @param newUserInformation receives new data in json. Updates the changes.
     * @return proper status code.
     */
    @PutMapping("/user")
    ResponseEntity updateUser(@RequestBody JSONObject newUserInformation) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).
                    body(userAccountService.updateUser(newUserInformation).toJSON());
        } catch (BadRequestException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Changes not accepted");
        }
    }


}
