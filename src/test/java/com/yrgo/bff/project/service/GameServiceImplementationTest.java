package com.yrgo.bff.project.service;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.game.GameService;
import com.yrgo.bff.project.service.useraccount.UserAccountService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GameService gameService;

    private Game newGame() throws Exception {
        UserAccount user = userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"hej");
        UserAccount user2 = userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"hej");
        Set<UserAccount> users = new HashSet<>();
        users.add(user); users.add(user2);
        return gameService.createGame(LocalDate.now(),LocalTime.now(), "Sprängtporten TK","Helsinki",users);
    }

    @Test
    public void createGameIsSuccessful() throws Exception {
        Long newGameId = newGame().getId();
        assertNotNull(gameService.readGame(newGameId));
    }


    @Test
    public void removeGameIsSuccessful() throws Exception {
        Long newGameId = newGame().getId();
        assertNotNull(gameService.readGame(newGameId));
        gameService.removeGame(newGameId);
        assertThrows(NoSuchElementException.class, ()->{gameService.readGame(newGameId);});
    }


    @Test //uppdatera gör egetnligen bara att det gamla spelet tas bort och ett nytt skapas
    public void updateGameIsSuccessful() throws Exception {
        Long newGameId = newGame().getId();
        Long anotherNewGameId = newGame().getId();
        assertDoesNotThrow(()->gameService.readGame(anotherNewGameId));
        assertDoesNotThrow(()->gameService.readGame(newGameId));
        gameService.updateGame(newGameId, gameService.readGame(anotherNewGameId));
        assertDoesNotThrow(()->gameService.readGame(anotherNewGameId));
        assertThrows(NoSuchElementException.class, ()->gameService.readGame(newGameId));
    }

    @Test
    public void readGameIsSuccessFul() throws Exception {
        Long newGameId = newGame().getId();
        assertDoesNotThrow(()->gameService.readGame(newGameId));
    }

}
