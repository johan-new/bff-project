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
    Set<ApplicationUser> participants;
//    @JoinTable(
//            name = "user_game",
//            joinColumns = @JoinColumn(name = "userName"),
//            inverseJoinColumns = @JoinColumn(name = "id")
//    )

    public Game(Date when, String venue, Set<ApplicationUser> participants) {
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

    public Set<ApplicationUser> getParticipants() {
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
        List<String> players = new ArrayList<>();

        for (ApplicationUser user : participants) {
            players.add(user.getUsername());
        }

        gameDetails.put("id", getId().toString());
        gameDetails.put("when", getWhen().toString());
        gameDetails.put("venue", getVenue());
        gameDetails.put("players", players.toString());

        return gameDetails;
    }
}
