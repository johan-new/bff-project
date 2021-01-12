package com.yrgo.bff.project.domain;


import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date when;
    private String venue;

    @ManyToMany
    Set<UserAccount> participants;

    public Game(Date when, String venue, Set<UserAccount> participants) {
        this.when = when;
        this.venue = venue;
        this.participants = participants;
    }
    public Game () {}

    public Date getWhen() {
        return when;
    }

    public String getVenue() {
        return venue;
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
        gameDetails.put("when", getWhen().toString());
        gameDetails.put("venue", getVenue());
        gameDetails.put("players", playersStr);

        return gameDetails;
    }
}
