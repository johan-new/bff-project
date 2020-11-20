package com.yrgo.bff.project.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long game_id;

    private Date when;
    private String venue;

    @Transient
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
