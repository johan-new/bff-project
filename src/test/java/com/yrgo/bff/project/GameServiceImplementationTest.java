package com.yrgo.bff.project;

import com.yrgo.bff.project.dao.GameDataAccess;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.User;
import com.yrgo.bff.project.service.GameServiceImplementation;
import com.yrgo.bff.project.service.UserAccountService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceImplementationTest {

    @Autowired
    private GameDataAccess gameDataAccess;

    @Autowired
    private GameServiceImplementation gameServiceImplementation;

    @Autowired
    private UserAccountService userAccountService;

//    @BeforeEach
//    private void setUp() {
//        Set<User> users = new HashSet<>();
//        users.add(new User("Erik", "hello"));
//        users.add(new User("Johan", "haj"));
//        users.add(new User("Simon", "password"));
//        Game game = new Game(new Date(), "Norrbo Padel", users);
//        Mockito.when(gameDataAccess.findById(game.getId())).thenReturn(java.util.Optional.of(game));
//    }

    //TESTAR OM MAN KAN SKAPA ETT GAME TILLFREDSTÄLLANDE
    @Test
    public void createGameIsSuccessful() {
        userAccountService.createUser("Erik", "hello");
        User user = userAccountService.readUser("Erik", "hello");
        Set<User> users = new HashSet<>();
        users.add(user);
        //Kollar att game är tom
        assertFalse(gameDataAccess.findAll().iterator().hasNext());
        Game game = gameServiceImplementation.createGame(new Date(), "Göteborg", users);
        assertNotNull(gameDataAccess.findAll());
    }

    //TESTA ATT TA BORT ETT GAME TILLFREDSSTÄLLANDE - Fungerar inte ännu som den ska

    @Test
    public void removeGameIsSuccessful() {
        userAccountService.createUser("Johan", "hello");
        User user = userAccountService.readUser("Johan", "hello");
        Set<User> users = new HashSet<>();
        users.add(user);
        Game game = gameServiceImplementation.createGame(new Date(), "Göteborg", users);
        assertNotNull(gameDataAccess.findAll());
        gameServiceImplementation.removeGame(game.getId());
        assertNull(gameDataAccess.findAll());
//        assertFalse(gameDataAccess.findAll().iterator().hasNext());

    }

    //TESTAR OM DET GÅR ATT UPPDATERA ETT GAME TILLFREDSSTÄLLANDE

    @Test
    public void updateGameIsSuccessful() {
        userAccountService.createUser("Johan", "hello");
        User user = userAccountService.readUser("Johan", "hello");
        Set<User> users = new HashSet<>();
        users.add(user);
        Game game = gameServiceImplementation.createGame(new Date(), "Göteborg", users);
        assertNotNull(gameDataAccess.findAll());
        Game newGame = gameServiceImplementation.createGame(new Date(), "Norrbo", users);
        gameServiceImplementation.updateGame(game.getId(), newGame);
        assertNotNull(gameDataAccess.findAll());

    }

    @Test
    public void readGameIsSuccessFul() {
        userAccountService.createUser("Johan", "hello");
        User user = userAccountService.readUser("Johan", "hello");
        Set<User> users = new HashSet<>();
        users.add(user);
        Game game = gameServiceImplementation.createGame(new Date(), "Göteborg", users);
        gameServiceImplementation.readGame(game.getId());
        assertNotNull(gameDataAccess.findById(game.getId()));
    }

}
