package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.UserAccountService;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
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

    public UserAccountController(UserAccountService userAccountService, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody JSONObject user) throws Exception {
        //parsing
        final String username = (String)user.get("username");
        final String password = (String)user.get("password");

        if (!UserAccountServiceImplementation.validEmailAddress(username)) {
            throw new Exception("Invalid email address!");
        }

        if (userAccountService.readUser(username)==null) {
        userAccountService.createUser(username, password);
        return ResponseEntity.
                status(HttpStatus.CREATED).
                body(userAccountService.readUser(username).toJSON());
        } else {
            throw new Exception("User already exists!");
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
    JSONObject readUser() {
        return userAccountService.readLoggedInUser().toJSON();
    }

    @PutMapping("/user")
    ResponseEntity updateUser(@RequestBody JSONObject newUserInformation) throws Exception {
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
