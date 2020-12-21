package com.yrgo.bff.project;

import com.yrgo.bff.project.domain.UserAccount;
import com.yrgo.bff.project.service.GameService;
import com.yrgo.bff.project.service.MatchMakingService;
import com.yrgo.bff.project.service.NotificationService;
import com.yrgo.bff.project.service.UserAccountService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
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

		private Log log = LogFactory.getLog(getClass());

		@Override @Transactional
		public void run(ApplicationArguments args) throws Exception {

			log.info("TEST: Detta är ett informationsmeddelande");
			log.debug("TEST: Detta är ett debug-meddelande");
			log.error("TEST: TEST: Detta är ett felmeddelande");

			final String user = "erik@a.a";
			final String user2 = "simon@a.a";
			final String user3 = "johan@a.a";
			final String user4 = "greven@a.a";
			final String password = "a";

			final String location = "Mölndal";

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
			LocalDate ld = LocalDate.parse("2020-12-24");
			LocalTime lt = LocalTime.parse("17:00");
			LocalDate ld2 = LocalDate.parse("2020-12-25");
			LocalTime lt2 = LocalTime.parse("11:00");
			String venue = "GLTK";
			String venue2 = "PadelHallen AB";
			String venue3 = "Padelcenter";
			String venue4 = "Padel4u";

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", user);
			jsonObject.put("date", ld);
			jsonObject.put("time", lt);
			jsonObject.put("reservation", false);
			jsonObject.put("venue", venue);
			jsonObject.put("participants", 2);

			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("username", user2);
			jsonObject2.put("date", ld2);
			jsonObject2.put("time", lt2);
			jsonObject2.put("reservation", true);
			jsonObject2.put("venue", venue2);
			jsonObject2.put("participants", 3);

			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("username", user3);
			jsonObject3.put("date", ld);
			jsonObject3.put("time", lt2);
			jsonObject3.put("reservation", true);
			jsonObject3.put("venue", venue3);
			jsonObject3.put("participants", 1);

			JSONObject jsonObject4 = new JSONObject();
			jsonObject4.put("username", user4);
			jsonObject4.put("date", ld2);
			jsonObject4.put("time", lt2);
			jsonObject4.put("reservation", true);
			jsonObject4.put("venue", venue4);
			jsonObject4.put("participants", 1);

			JSONObject jsonObject5 = new JSONObject();
			jsonObject5.put("username", user5);
			jsonObject5.put("date", ld2);
			jsonObject5.put("time", lt);
			jsonObject5.put("reservation", true);
			jsonObject5.put("venue", venue);
			jsonObject5.put("participants", 2);

			JSONObject jsonObject6 = new JSONObject();
			jsonObject6.put("username", user6);
			jsonObject6.put("date", ld);
			jsonObject6.put("time", lt);
			jsonObject6.put("reservation", true);
			jsonObject6.put("venue", venue3);
			jsonObject6.put("participants", 2);

			JSONObject jsonObject7 = new JSONObject();
			jsonObject7.put("username", user3);
			jsonObject7.put("date", ld2);
			jsonObject7.put("time", lt2);
			jsonObject7.put("reservation", true);
			jsonObject7.put("venue", venue4);
			jsonObject7.put("participants", 1);

			JSONObject jsonObject8 = new JSONObject();
			jsonObject8.put("username", user8);
			jsonObject8.put("date", ld);
			jsonObject8.put("time", lt2);
			jsonObject8.put("reservation", true);
			jsonObject8.put("venue", venue);
			jsonObject8.put("participants", 2);

			matchMakingService.addUserMatchRequest(jsonObject, location);
			matchMakingService.addUserMatchRequest(jsonObject2, location);
			matchMakingService.addUserMatchRequest(jsonObject3, location);
			matchMakingService.addUserMatchRequest(jsonObject4, location);

			matchMakingService.addUserMatchRequest(jsonObject5, "Stockholm");
			matchMakingService.addUserMatchRequest(jsonObject6, "Pajala");
			matchMakingService.addUserMatchRequest(jsonObject7, "Malmö");
			matchMakingService.addUserMatchRequest(jsonObject8, "Borås");

			notificationService.addNotification(user3,"Detta är en notis!", NotificationService.Type.GENERAL);


			userAccountService.readUser(user).addFriend(userAccountService.readUser(user2));
			userAccountService.readUser(user).addFriend(userAccountService.readUser(user3));
			userAccountService.readUser(user).addFriend(userAccountService.readUser(user4));
			userAccountService.readUser(user2).addFriend(userAccountService.readUser(user3));


		}

	}

}
