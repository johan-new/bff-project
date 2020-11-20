package com.yrgo.bff.project.domain;


import java.util.Date;
import java.util.List;

public class Game {
    private Date when;
    private String venue;
    private List<User> participants;

    public Game(Date when, String venue, List<User> participants) {
        this.when = when;
        this.venue = venue;
        this.participants = participants;
    }

    public Date getWhen() {
        return when;
    }

    public String getVenue() {
        return venue;
    }

    public List<User> getParticipants() {
        return participants;
    }
}
