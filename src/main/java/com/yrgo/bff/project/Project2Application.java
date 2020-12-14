package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.MatchMakingService;
import com.yrgo.bff.project.service.NotificationService;
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
	MatchMakingService matchMakingService;

	@Autowired
	NotificationService notificationService;


	public static void main(String[] args) {
		SpringApplication.run(Project2Application.class, args);
	}


	@Component
	public class AppStartupRunner implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {

			final String user = "erik@a.a";
			final String user2 = "simon@a.a";
			final String user3 = "sohan@a.a";
			final String user4 = "sreven@a.a";
			final String password = "a";

			final String venue = "Mölndal";

			final String user5 = "e@e.e";
			final String user6 = "s@s.s";
			final String user7 = "j@j.j";
			final String user8 = "f@f.f";

			userAccountService.createUser(user,password);
			userAccountService.createUser(user2,password);
			userAccountService.createUser(user3,password);
			userAccountService.createUser(user4,password);


			Set<UserAccount> users = new HashSet<>();

			userAccountService.createUser(user5,password);
			userAccountService.createUser(user6,password);
			userAccountService.createUser(user7,password);
			userAccountService.createUser(user8,password);

			users.add(userAccountService.readUser(user));
			users.add(userAccountService.readUser(user2));
			users.add(userAccountService.readUser(user3));

			gameService.createGame(new Date(),"Göteborg",users);

			matchMakingService.addUserMatchRequest(userAccountService.readUser(user),venue);
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user2),venue);
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user3),venue);
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user4),venue);

			notificationService.addNotification(user3,"Detta är en notis!", NotificationService.Type.GENERAL);

			UserAccount u1 = new UserAccount(user5, password);
			UserAccount u2 = new UserAccount(user6, password);
			UserAccount u3 = new UserAccount(user7, password);
			UserAccount u4 = new UserAccount(user8, password);

			matchMakingService.addUserMatchRequest(u1, "Stockholm");
			matchMakingService.addUserMatchRequest(u2, "Norrut");
			matchMakingService.addUserMatchRequest(u3, "Skåne");
			matchMakingService.addUserMatchRequest(u4, "Pajala");

		}

	}

}
