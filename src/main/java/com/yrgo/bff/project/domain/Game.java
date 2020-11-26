package com.yrgo.bff.project.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date when;
    private String venue;

    @ManyToMany
    Set<User> participants;
//    @JoinTable(
//            name = "user_game",
//            joinColumns = @JoinColumn(name = "userName"),
//            inverseJoinColumns = @JoinColumn(name = "id")
//    )

    public Game(Date when, String venue, Set<User> participants) {
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

    public Set<User> getParticipants() {
        return participants;
    }

    public Long getId() {
        return id;
    }
}
