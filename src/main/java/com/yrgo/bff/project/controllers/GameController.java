package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.game.GameService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class GameController {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GameService gameService;

    private Log log = LogFactory.getLog(getClass());



    // TODO: for a future admin portal, or remove? A user get the games from ApplicationUserController
    @GetMapping(value = "/game/{gameId}")
    JSONObject readGame(@PathVariable("gameId") String gameId){
        return gameService.readGame(Long.parseLong(gameId)).toJSON();
    }

    @PutMapping(value = "/game/{gameId}")
    ResponseEntity updateGame(@PathVariable("gameId") String gameId, @RequestParam("newPlayers") String[] newPlayers) throws Exception {
        //TODO: Ability to change date?
        Game game = gameService.readGame(Long.parseLong(gameId));
        gameService.removeGame(Long.parseLong(gameId));
        gameService.createGame(game.getDate(),game.getTime(),game.getVenue(),stringArrayToSet(newPlayers));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping(value = "/game/{gameId}")
    ResponseEntity deleteGame(@PathVariable("gameId") String gameId){
        gameService.removeGame(Long.parseLong(gameId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private Set<UserAccount> stringArrayToSet(String[] players) throws Exception {

        //allowing only 1-3 players in addition to the logged in user
        if (players.length <= 0 || players.length > 3) {
            throw new Exception("Too many players");
        }

        Set<UserAccount> playersSet = new HashSet<>();

        //adds logged in user to the set
        playersSet.add(userAccountService.readLoggedInUser());

        //adding the rest of the participants
        for (String username:players) {
            System.err.println(username);
            UserAccount u = userAccountService.readUser(username);
            if (u == null) {
                log.error("********** " + u);
                throw new Exception("User doesn't exists!");
            } else {
                playersSet.add(u);
                log.debug("Adding " + username + "to game");
            }
        }
        return playersSet;
    }
}