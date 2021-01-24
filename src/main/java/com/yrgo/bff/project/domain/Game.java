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

    /**
     * Regular all args constructor
     *
     * @param date of the game
     * @param time of the game
     * @param venue where to play
     * @param location geographical
     * @param participants the participating users
     */
    public Game(LocalDate date, LocalTime time, String venue, String location, Set<UserAccount> participants) {
        this.date = date;
        this.time = time;
        this.venue = venue;
        this.location = location;
        this.participants = participants;
    }

    /**
     * required by hibernate
     */
    public Game () {}

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the time
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * @return the venue
     */
    public String getVenue() {
        return venue;
    }

    /**
     * @return the geographical location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * @return the participants in the game
     */
    public Set<UserAccount> getParticipants() {
        return participants;
    }

    /**
     * @return the uniqe game id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return object data as JSON
     */
    public JSONObject toJSON(){
        return new JSONObject(mapGameDetails());
    }

    /**
     * @return manually mapped data to avoid
     * infinite recursion
     */
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
