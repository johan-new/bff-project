package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.UserAccountService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        //i.e. POST "http://localhost:8080/game?players=Johan,Erik,Simon&venue=Gbg"
    void createGame(@RequestParam("players") String[] players, @RequestParam("venue") String venue/*, User organizedBy, String password*/) throws Exception {
        //TODO: Authenticate and use custom exception, check max players 4
        gameService.createGame(new Date(), venue, stringArrayToSet(players));
    }

    // TODO: for a future admin portal, or remove? A user get the games from ApplicationUserController
    @GetMapping(value = "/game/{gameId}")
    JSONObject readGame(@PathVariable("gameId") String gameId){
        return gameService.readGame(Long.parseLong(gameId)).toJSON();
    }

    @PutMapping(value = "/game/{gameId}")
    void updateGame(@PathVariable("gameId") String gameId, @RequestParam("newPlayers") String[] newPlayers) throws Exception {
        //TODO: Ability to change date?
        Game game = gameService.readGame(Long.parseLong(gameId));
        gameService.removeGame(Long.parseLong(gameId));
        gameService.createGame(game.getWhen(),game.getVenue(),stringArrayToSet(newPlayers));
    }

    @DeleteMapping(value = "/game/{gameId}")
    void deleteGame(@PathVariable("gameId") String gameId){
        gameService.removeGame(Long.parseLong(gameId));
    }


    private Set<ApplicationUser> stringArrayToSet(String[] players) throws Exception {

        //allowing only 1-3 players in addition to the logged in user
        if (players.length <= 0 || players.length > 3) {
            throw new Exception("Too many players");
        }

        Set<ApplicationUser> playersSet = new HashSet<>();

        //adds logged in user to the set
        playersSet.add(userAccountService.readLoggedInUser());

        //adding the rest of the participants
        for (String username:players) {
            System.err.println(username);
            ApplicationUser u = userAccountService.readUser(username);
            if (u == null) {
                System.out.println("********** " + u);
                throw new Exception("User doesn't exists!");
            } else {
                playersSet.add(u);
                System.out.println("Adding " + username + "to game");
            }
        }

        return playersSet;
    }

}
