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

import javax.transaction.Transactional;
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

		@Override @Transactional
		public void run(ApplicationArguments args) throws Exception {

			final String user = "erik@a.a";
			final String user2 = "simon@a.a";
			final String user3 = "johan@a.a";
			final String user4 = "greven@a.a";
			final String password = "a";

			final String venue = "Mölndal";

			final String user5 = "nahid@a.a";
			final String user6 = "anders@a.a";
			final String user7 = "hampus@a.a";
			final String user8 = "jon@a.a";

			userAccountService.createUser(user,password);
			userAccountService.createUser(user2,password);
			userAccountService.createUser(user3,password);
			userAccountService.createUser(user4,password);


			Set<UserAccount> users = new HashSet<>();
			Set<UserAccount> users2 = new HashSet<>();
			Set<UserAccount> users3 = new HashSet<>();

			userAccountService.createUser(user5,password);
			userAccountService.createUser(user6,password);
			userAccountService.createUser(user7,password);
			userAccountService.createUser(user8,password);

			users.add(userAccountService.readUser(user));
			users.add(userAccountService.readUser(user2));
			users.add(userAccountService.readUser(user3));
			users2.add(userAccountService.readUser(user8));

			users2.add(userAccountService.readUser(user5));
			users2.add(userAccountService.readUser(user3));
			users2.add(userAccountService.readUser(user2));
			users2.add(userAccountService.readUser(user));

			users3.add(userAccountService.readUser(user));
			users3.add(userAccountService.readUser(user2));
			users3.add(userAccountService.readUser(user3));
			users3.add(userAccountService.readUser(user5));

			gameService.createGame(new Date(),"Göteborg",users);
			gameService.createGame(new Date(), "Borås", users2);
			gameService.createGame(new Date(), "Halmstad", users3);

			matchMakingService.addUserMatchRequest(userAccountService.readUser(user),venue);
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user2),venue);
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user3),venue);
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user4),venue);

			matchMakingService.addUserMatchRequest(userAccountService.readUser(user5),"Stockholm");
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user6),"Norrut");
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user7),"Skåne");
			matchMakingService.addUserMatchRequest(userAccountService.readUser(user8),"Pajala");

			notificationService.addNotification(user3,"Detta är en notis!", NotificationService.Type.GENERAL);


			userAccountService.readUser(user).addFriend(userAccountService.readUser(user2));
			userAccountService.readUser(user2).addFriend(userAccountService.readUser(user3));


		}

	}

}
