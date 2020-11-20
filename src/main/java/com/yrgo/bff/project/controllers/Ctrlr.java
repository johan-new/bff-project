package com.yrgo.bff.project.controllers;


import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.UserAccountService;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Ctrlr {

    //init object, as a singleton

   // @Autowired
    //MatchingService matchingService;

    @Autowired
    UserAccountService userAccountService;

    @RequestMapping("/")
    String hello(){
       return "Hello!";
    }

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
                    @RequestParam(name="password",required = true) String password){
        userAccountService.updateUser(name,password);
    }

    @DeleteMapping("/user")
    void removeUser(@RequestParam(name="name",required = true) String name,
                    @RequestParam(name="password",required = true) String password){
        userAccountService.removeUser(name,password);
    }

    @PostMapping("/match")
    String match(){
        //matchingService.addUserMatchRequest();
        //matchingService.matchUsers();
        return "Match";
    }
}
