package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.ApplicationUser;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.MatchingService;
import com.yrgo.bff.project.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Project2Application {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	GameService gameService;

	@Autowired
	MatchingService matchingService;


	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}


	@Component
	public class AppStartupRunner implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {
			final String user = "Erik@a.a";
			final String user2 = "Simon@a.a";
			final String user3 = "Johan@a.a";
			final String user4 = "Greven@a.a";
			final String password = bCryptPasswordEncoder().encode("a");
			final String venue = "Mölndal";

			final String user5 = "e@e.e";
			final String user6 = "s@s.s";
			final String user7 = "j@j.j";
			final String user8 = "f@f.f";

			userAccountService.createUser(user,password);
			userAccountService.createUser(user2,password);
			userAccountService.createUser(user3,password);
			userAccountService.createUser(user4,password);

			userAccountService.createUser(user5,password);
			userAccountService.createUser(user6,password);
			userAccountService.createUser(user7,password);
			userAccountService.createUser(user8,password);

			Set<ApplicationUser> users = new HashSet<>();

			users.add(userAccountService.readUser(user));
			users.add(userAccountService.readUser(user2));
			users.add(userAccountService.readUser(user3));

			gameService.createGame(new Date(),"Göteborg",users);

			/*matchingService.addUserMatchRequest(userAccountService.readUser(user),venue);
			matchingService.addUserMatchRequest(userAccountService.readUser(user2),venue);
			matchingService.addUserMatchRequest(userAccountService.readUser(user3),venue);
			matchingService.addUserMatchRequest(userAccountService.readUser(user4),venue);*/

			ApplicationUser u1 = new ApplicationUser(user5, password);
			ApplicationUser u2 = new ApplicationUser(user6, password);
			ApplicationUser u3 = new ApplicationUser(user7, password);
			ApplicationUser u4 = new ApplicationUser(user8, password);

			matchingService.addUserMatchRequest(u1, "Stockholm");
			matchingService.addUserMatchRequest(u2, "Norrut");
			matchingService.addUserMatchRequest(u3, "Skåne");
			matchingService.addUserMatchRequest(u4, "Pajala");

		}

	}

}
