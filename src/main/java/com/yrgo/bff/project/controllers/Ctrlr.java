package com.yrgo.bff.project.controllers;


import com.yrgo.bff.project.dao.GameDataAccess;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.UserAccountService;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
public class Ctrlr {

    //init object, as a singleton

   // @Autowired
    //MatchingService matchingService;

    @Autowired
    UserAccountService userAccountService;
    @Autowired
    GameService gameService;


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
                    @RequestParam(name="password",required = true) String password,
                    @RequestParam(name="newPassword",required = true) String newPassword){
        userAccountService.updateUser(name,password,newPassword);
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

    @PostMapping(value = "/game")
    void createGame() {
        Set<User> userSet = new HashSet<>();
//        userSet.add(new User("Johan", "nahidlover"));
//        userSet.add(new User("Erik", "anderslover"));
  //      Game game = new Game(new Date(), "Göteborg", userSet);
        userAccountService.createUser("Johan", "nahidlover");
        userAccountService.createUser("Erik", "anderslover");
        userSet = userAccountService.findAll();
        gameService.createGame(new Date(), "Göteborg", userSet);
    }

}
