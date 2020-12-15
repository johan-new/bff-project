package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.UserAccountService;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    public JSONObject createUser(@RequestBody JSONObject user) throws Exception {
        //parsing
        final String username = (String)user.get("username");
        final String password = (String)user.get("password");

        if (!UserAccountServiceImplementation.validEmailAddress(username)) {
            throw new Exception("Invalid email address!");
        }

        if (userAccountService.readUser(username)==null) {
        userAccountService.createUser(username, password);
        return userAccountService.readUser(username).toJSON();
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

    //used to change password or email adress(username)

    @PutMapping("/user")
    void updateUser(@RequestBody JSONObject user) throws Exception {
        final String oldPassword = (String)user.get("oldPassword");
        UserAccount u = userAccountService.readUser((String)user.get("username"));
        final String newPassword = (String)user.get("newPassword");

        if (bCryptPasswordEncoder.matches(oldPassword, u.getPassword()) && !oldPassword.equals(newPassword)) {
            userAccountService.updateUser(oldPassword, newPassword);
        }
        else {
            throw new Exception("ERROR: Password was not changed!");
        }
    }

    //TODO: admin can delete anyone, regular user just themselves
    @DeleteMapping("/user")
    void removeUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password){
        userAccountService.removeUser(name);
    }

}
