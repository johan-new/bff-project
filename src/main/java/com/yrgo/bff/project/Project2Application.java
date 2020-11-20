package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.Game;
import com.yrgo.bff.project.domain.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Project2Application {

	public static void main(String[] args) {

		SpringApplication.run(Project2Application.class, args);

//		User user = new User("Erik", "password");
//		Set<User> u = new HashSet<User>();
//		u.add(user);
//		Game game = new Game(new Date(), "Götlaborg", u);


	}

}
