package com.yrgo.bff.project.controllers;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.exception.BadRequestException;
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

/**
 * GameController
 *
 * REST Controller for handling games. Not used since the current flow consists of a matching request that
 * gets converted into a Game when the adequate number of participants has joined and been confirmed (by the organizer).
 *
 * Currently this controller is not implemented by the front end since the core logic and flow
 * for the users has changed.
 *
 **/


@RestController
public class GameController {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GameService gameService;

    private Log log = LogFactory.getLog(getClass());


    /**
     * @param gameId unique game id
     * @return the requested games data as manually mapped JSON
     */
    @GetMapping(value = "/game/{gameId}")
    JSONObject readGame(@PathVariable("gameId") String gameId){
        return gameService.readGame(Long.parseLong(gameId)).toJSON();
    }

    /**
     * @param gameId unique game id
     * @param newPlayers new players in the current game
     * @return status code ACCEPTED
     */
    @PutMapping(value = "/game/{gameId}")
    ResponseEntity updateGame(@PathVariable("gameId") String gameId, @RequestParam("newPlayers") String[] newPlayers) {
        Game game = gameService.readGame(Long.parseLong(gameId));
        gameService.removeGame(Long.parseLong(gameId));
        gameService.createGame(game.getDate(),game.getTime(),game.getVenue(),game.getLocation(), stringArrayToSet(newPlayers));
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * @param gameId unique game id
     * @return status code NO CONTENT
     */
    @DeleteMapping(value = "/game/{gameId}")
    ResponseEntity deleteGame(@PathVariable("gameId") String gameId){
        gameService.removeGame(Long.parseLong(gameId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * @param players usernames of the players
     * @return set of UserAccount entities of these players
     * @throws BadRequestException when incorrect interval
     */
    private Set<UserAccount> stringArrayToSet(String[] players) throws BadRequestException {

        //allowing only 1-3 players in addition to the logged in user
        if (players.length <= 0 || players.length > 3) {
            final String msg = "Too few/many players (" + players.length + ")";
            log.error(msg);
            throw new BadRequestException(msg);
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
                throw new BadRequestException("User doesn't exists!");
            } else {
                playersSet.add(u);
                log.debug("Adding " + username + "to game");
            }
        }
        return playersSet;
    }
}