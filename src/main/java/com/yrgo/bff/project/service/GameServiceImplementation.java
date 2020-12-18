package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.GameDataAccess;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class GameServiceImplementation implements GameService {

    @Autowired
    GameDataAccess gameDataAccess;

    private Log log = LogFactory.getLog(getClass());


    /**
     * Creates a Game and persists it in the database
     *
     * @param when - Date when of the Game you want to create
     * @param venue - String of the Game you want to create
     * @param users - Set of <Users> for the Game you want to create
     * @return An instance of Game
     *
     * */
    @Override
    public Game createGame(Date when, String venue, Set<UserAccount> users) {
        Game game = new Game(when, venue, users);
        gameDataAccess.save(game);
        return game;
    }

    /**
     * Removes a Game from the database
     *
     * @param game_id - Long game_id of the Game you want to delete
     * @return An instance of Game that was deleted
     *
     * */
    @Override
    public Game removeGame(Long game_id) {
        Game game = gameDataAccess.findById(game_id).get();
        gameDataAccess.delete(game);
        return game;
    }

    /**
     * Updates a Game by removing a Game and creating a new Game and then
     * persists it in the database
     *
     * @param game_id - Long of the game you want to update
     * @return An instance of Game that was updated
     *
     * */
    @Override
    public Game updateGame(Long game_id, Game game) {
        removeGame(game_id);
        gameDataAccess.save(game);
        return game;
    }

    /**
     * Fetches a game from database
     *
     * @return a game_id of the Game
     *
     * */
    @Override
    public Game readGame(Long game_id) {
        return gameDataAccess.findById(game_id).get();
    }
}
