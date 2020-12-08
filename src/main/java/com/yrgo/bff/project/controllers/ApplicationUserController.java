package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.UserAccountService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApplicationUserController {

    @Autowired
    UserAccountService userAccountService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserController(UserAccountService userAccountService, BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userAccountService = userAccountService;
    }

    @PostMapping("/user")
    public JSONObject createUser(@RequestBody JSONObject user) throws Exception {
        //parsing
        final String username = (String)user.get("username");
        final String password = bCryptPasswordEncoder.encode((String)user.get("password"));

        if (userAccountService.readUser(username)==null) {
        return userAccountService.createUser(username, password).getAsJSON();
        } else {
            throw new Exception("User already exists!");
        }
    }

    @GetMapping("/user")
    JSONObject readUser(@RequestBody JSONObject user) {
        return userAccountService.readLoggedInUser().getAsJSON();
    }

    @GetMapping("/user/previousgames")
    JSONObject readUsersPreviousGames() {
        return userAccountService.readLoggedInUser().getPreviousGamesAsJSON();
    }

    @GetMapping("/loggedinuser")
    JSONObject readUser() {
        return userAccountService.readLoggedInUser().getAsJSON();
    }

    //used to change password or email adress(username)
    @PutMapping("/user")
    void updateUser(@RequestParam(name="newPassword",required = true) String newPassword){
        userAccountService.updateUser(newPassword);
    }

    //TODO: admin can delete anyone, regular user just themselves
    @DeleteMapping("/user")
    void removeUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password){
        userAccountService.removeUser(name);
    }

}
