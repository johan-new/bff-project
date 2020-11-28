package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RestController
public class GameController {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GameService gameService;

    @PostMapping(value = "/game")
        //i.e. POST "http://localhost:8080/game?players=Johan,Erik,Simon,Greven&venue=Gbg"
    void createGame(@RequestParam("players") String[] players, @RequestParam("venue") String venue/*, User organizedBy, String password*/) throws Exception {
        //TODO: Authenticate and use custom exception
        Set<User> playersSet = new HashSet<>();

        for (String username:players) {
            User u = userAccountService.readUser(username);
            if (u == null) {
                throw new Exception("User doesn't exists!");
            } else {
                playersSet.add(u);
                System.out.println("Adding " + username + "to game");
            }
        }
        gameService.createGame(new Date(), venue, playersSet);
    }
}
