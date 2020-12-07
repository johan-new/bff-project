package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.ApplicationUser;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@SpringBootTest
class Project2ApplicationTests {

    @Test
    void testTableJoin() {
        ApplicationUser user = new ApplicationUser("Erik", "password");
        ApplicationUser user1 = new ApplicationUser("Nahid", "password");
        Set<ApplicationUser> u = new HashSet<ApplicationUser>();
        u.add(user);
        u.add(user1);
        Game game = new Game(new Date(), "Götlaborg", u);
    }
	
}
