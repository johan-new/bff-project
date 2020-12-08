package com.yrgo.bff.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yrgo.bff.project.service.NotificationService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.*;

@Entity
public class ApplicationUser {

    //jsonignore due to infinite recursion otherwise when creating json object
    @ManyToMany (mappedBy = "participants") @JsonIgnore
    Set<Game> previousGames;

    @Id
    private String username;

    @Autowired @Transient
    NotificationService notificationService;

    //this should never be serialized by the web layer
    private String password;

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    ApplicationUser(){
        //generating dummy data
        Set<ApplicationUser> participants = new HashSet<>();
        participants.add(this);
        previousGames.add(new Game(new Date(),"Gbg",participants));
    }

    public Set<Game> getPreviousGames() {
        return previousGames;
    }

    public void setPreviousGames(Set<Game> previousGames) {
        this.previousGames = previousGames;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username;
    }

    public void notifyUser(String msg){
        notificationService.addNotification(getUsername(),msg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUser user = (ApplicationUser) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    //used to not expose passwords
    public JSONObject getAsJSON(){
        JSONObject json = new JSONObject();
        json.put("username",getUsername());
        return json;
    }

    public JSONObject getPreviousGamesAsJSON() {
        Map<String, List<String>> previousGamesMapped = new HashMap<>();

        for (Game game: previousGames) {
            List<String> players = new ArrayList<>();
            for (ApplicationUser user: game.participants) {
                players.add(user.getUsername());
            }
            final String key = game.getWhen() + " @ " + game.getVenue();
            previousGamesMapped.put(key,players);
        }

        return new JSONObject(previousGamesMapped);
    }
}
