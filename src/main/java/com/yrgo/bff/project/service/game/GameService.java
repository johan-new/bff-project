package com.yrgo.bff.project.service.game;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public interface GameService {
    Game createGame(LocalDate date, LocalTime time, String venue, String location, Set<UserAccount> users);
    Game removeGame(Long game_id);
    Game updateGame(Long game_id, Game game);
    Game readGame(Long game_id);
}
