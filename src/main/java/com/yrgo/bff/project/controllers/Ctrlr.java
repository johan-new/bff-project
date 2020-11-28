package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class Ctrlr {

    @Autowired
    UserAccountService userAccountService;


    @PostMapping("/user")
    void createUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password){
        userAccountService.createUser(name,password);
    }

    @GetMapping("/user")
    User readUser(@RequestParam(name="name",required = true) String name,
                  @RequestParam(name="password",required = true) String password){
        return userAccountService.readUser(name,password);
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
