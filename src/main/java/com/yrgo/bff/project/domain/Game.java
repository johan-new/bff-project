package com.yrgo.bff.project.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long game_id;

    private Date when;
    private String venue;

    @ManyToMany
    @JoinTable(
            name = "user_game",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    Set<User> participants;

    public Game(Date when, String venue, Set<User> participants) {
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

    public Set<User> getParticipants() {
        return participants;
    }
}
