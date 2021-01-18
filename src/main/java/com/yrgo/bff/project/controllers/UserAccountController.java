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

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody JSONObject user) throws BadRequestException {
        //parsing
        final String username = (String)user.get("username");
        final String password = (String)user.get("password");

        if (!UserAccountServiceImplementation.validEmailAddress(username)) {
            throw new BadRequestException("Invalid email address!");
        }

        if (userAccountService.readUser(username)==null) {
        userAccountService.createUser(username, password);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(userAccountService.readUser(username).toJSON());
        } else {
            throw new BadRequestException("User already exists!");
        }
    }

    @GetMapping("/user")
    JSONObject readUser(@RequestParam String username) {
        return userAccountService.readUser(username).toJSON();
    }

    @GetMapping("/user/previousgames")
    JSONObject readUsersPreviousGames() {
        return userAccountService.readLoggedInUser().getPreviousGamesAsJSON();
    }

    @GetMapping("/loggedinuser")
    public JSONObject readUser() {
        try {
            UserAccount loggedInUser = userAccountService.readLoggedInUser();
        } catch (NullPointerException e) {
                final String errorLogMessage = "Cannot read logged in user";
                log.error(errorLogMessage);
                throw new InternalServerErrorException(errorLogMessage);
        }
        return userAccountService.readLoggedInUser().toJSON();
    }

    @PutMapping("/user")
    ResponseEntity updateUser(@RequestBody JSONObject newUserInformation) throws Exception {
        userAccountService.updateUser(newUserInformation);
        return ResponseEntity.status(HttpStatus.ACCEPTED).
                body(userAccountService.updateUser(newUserInformation).toJSON());
    }

    //TODO: admin can delete anyone, regular user just themselves
    @DeleteMapping("/user/admin")
    ResponseEntity removeUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password){
        userAccountService.removeUser(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
