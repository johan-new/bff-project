package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.UserAccountService;
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
    public void createUser(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userAccountService.createUser(user.getUsername(), user.getPassword());
    }

    @GetMapping("/user")
    ApplicationUser readUser(@RequestParam(name="name",required = true) String name) {
        return userAccountService.readUser(name);
    }

    @PutMapping("/user")
    void updateUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password,
                    @RequestParam(name="newPassword",required = true) String newPassword){
        userAccountService.updateUser(name,password,newPassword);
    }

    @DeleteMapping("/user")
    void removeUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password){
        userAccountService.removeUser(name,password);
    }

}
