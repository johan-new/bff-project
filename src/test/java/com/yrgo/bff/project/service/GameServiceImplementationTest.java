package com.yrgo.bff.project.service;

import com.yrgo.bff.project.dao.GameDataAccess;
import com.yrgo.bff.project.dao.UserAccountDataAccess;
import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.GameServiceImplementation;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceImplementationTest {

    @MockBean
    private GameDataAccess gameDataAccess;

    @MockBean
    private UserAccountDataAccess userAccountDataAccess;

    @Autowired
    private GameServiceImplementation gameServiceImplementation;

    @Autowired
    private UserAccountServiceImplementation userAccountServiceImplementation;

    private ApplicationUser simpassword;
    private Set<ApplicationUser> userSet;
    private Game game;

    @BeforeEach
    private void init() {
        simpassword = new ApplicationUser("Simon", "password");
        Mockito.when(userAccountDataAccess.findByUsername(simpassword.getUsername())).thenReturn(simpassword);
        Mockito.when(userAccountDataAccess.save(simpassword)).thenReturn(simpassword);

        Set<ApplicationUser> users = new HashSet<>();
        users.add(simpassword);
        game = new Game(new Date(), "Norrbo Padel", users);
        Mockito.when(gameDataAccess.save(game)).thenReturn(game);
        Mockito.when(gameDataAccess.findById(game.getId())).thenReturn(Optional.of(game));
    }

    //TESTAR OM MAN KAN SKAPA ETT GAME TILLFREDSTÄLLANDE

    @Test
    public void createGameIsSuccessful() {
        //Kollar att game är tom
        assertFalse(gameDataAccess.findAll().iterator().hasNext());
        Game game2 = gameServiceImplementation.createGame(game.getWhen(), game.getVenue(), game.getParticipants());
        assertEquals(game.getId(), game2.getId());
    }

    //TESTA ATT TA BORT ETT GAME TILLFREDSSTÄLLANDE

    @Test
    public void removeGameIsSuccessful() {
        gameServiceImplementation.removeGame(game.getId());
        Mockito.verify(gameDataAccess, Mockito.times(1)).delete(game);
    }

    //TESTAR OM DET GÅR ATT UPPDATERA ETT GAME TILLFREDSSTÄLLANDE

    @Test
    public void updateGameIsSuccessful() {
        assertNotNull(gameDataAccess.findAll());
        Game newGame = gameServiceImplementation.createGame(new Date(), "Göteborg", userSet);
        gameServiceImplementation.updateGame(game.getId(), newGame);
        assertNotEquals(game, newGame);
        assertNull(game.getId());

    }

    //TESTAR OM DET GÅR ATT SÖKA PÅ ETT GAME VIA ID

    @Test
    public void readGameIsSuccessFul() {
        Game game2 = gameServiceImplementation.readGame(game.getId());
        assertEquals(game2.getId(), game.getId());
    }

}
