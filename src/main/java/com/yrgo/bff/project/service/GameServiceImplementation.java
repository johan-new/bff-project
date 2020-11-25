package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.GameDataAccess;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class GameServiceImplementation implements GameService {

    @Autowired
    GameDataAccess gameDataAccess;

    @Override
    public Game createGame(Date when, String venue, Set<User> users) {
        Game game = new Game(when, venue, users);
        gameDataAccess.save(game);
        return game;
    }

    @Override
    public Game removeGame(Long game_id) {
        Game game = gameDataAccess.findById(game_id).get();
        gameDataAccess.delete(game);
        return game;
    }

    @Override
    public Game updateGame(Long game_id, Game game) {
        removeGame(game_id);
        gameDataAccess.save(game);
        return game;
    }

    @Override
    public Game readGame(Long game_id) {
        return gameDataAccess.findById(game_id).get();
    }
}
