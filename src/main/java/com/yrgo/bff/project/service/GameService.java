package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;

import java.util.Date;
import java.util.Set;

public interface GameService {
    Game createGame(Date when, String venue, Set<UserAccount> users);
    Game removeGame(Long game_id);
    Game updateGame(Long game_id, Game game);
    Game readGame(Long game_id);
}
