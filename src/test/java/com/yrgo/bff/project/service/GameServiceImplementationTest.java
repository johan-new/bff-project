package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.GameDataAccess;
import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceImplementationTest {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    GameService gameService;

    private Game newGame() throws Exception {
        UserAccount user = userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        UserAccount user2 = userAccountService.createUser(FriendsUserAccountServiceImplementationTest.getRandomUsername(),"");
        Set<UserAccount> users = new HashSet<>();
        users.add(user); users.add(user2);
        return gameService.createGame(new Date(),"Helsinki",users);
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
