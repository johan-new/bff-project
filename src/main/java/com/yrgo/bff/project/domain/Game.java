package com.yrgo.bff.project.domain;


import org.json.simple.JSONObject;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Game
 *
 * Representing a game in the application.
 *
 * */

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private LocalTime time;

    private String venue, location;

    @ManyToMany
    Set<UserAccount> participants;

    public Game(LocalDate date, LocalTime time, String venue, String location, Set<UserAccount> participants) {
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.location = location;
        this.participants = participants;
    }
    public Game () {}

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getVenue() {
        return venue;
    }

    public String getLocation() {
        return this.location;
    }

    public Set<UserAccount> getParticipants() {
        return participants;
    }

    public Long getId() {
        return id;
    }

    public JSONObject toJSON(){
        return new JSONObject(mapGameDetails());
    }

    public Map<String, String> mapGameDetails() {
        Map<String, String> gameDetails = new HashMap<>();

        StringBuilder players = new StringBuilder();
        for (UserAccount user : participants) {
            players.append(user.getUsername() + ", ");
        }
        String playersStr = players.substring(0,players.length()-2);

        gameDetails.put("id", getId().toString());
        gameDetails.put("when", getDate() + " " + getTime());
        gameDetails.put("venue", getVenue());
        gameDetails.put("location", getLocation());
        gameDetails.put("players", playersStr);

        return gameDetails;
    }
}
