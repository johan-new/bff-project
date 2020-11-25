package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class Project2ApplicationTests {

    @Test
    void testTableJoin() {
        User user = new User("Erik", "password");
        User user1 = new User("Nahid", "password");
        Set<User> u = new HashSet<User>();
        u.add(user);
        u.add(user1);
        Game game = new Game(new Date(), "Götlaborg", u);
    }
	
}
