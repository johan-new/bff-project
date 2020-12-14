package com.yrgo.bff.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yrgo.bff.project.service.NotificationService;
import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.*;

@Entity
public class UserAccount {

    //jsonignore due to infinite recursion otherwise when creating json object
    @ManyToMany (mappedBy = "participants")
    Set<Game> previousGames;

    @Id
    private String username;

    @Autowired
    @Transient
    NotificationService notificationService;

    @ManyToOne(cascade = CascadeType.ALL)
    private Friends friends = new Friends();

    //this should never be serialized by the web layer
//    @JsonIgnore
    private String password;

    public UserAccount(String username, String password) {
        setUsername(username);
        this.password = password;
    }

    UserAccount(){
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

    private void setUsername(String username) {
        if (UserAccountServiceImplementation.validEmailAddress(username)) {
            this.username = username;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount user = (UserAccount) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    //used to not expose passwords
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("username",getUsername());

        JSONObject games = getPreviousGamesAsJSON();

        if (!games.isEmpty()) {
        json.put("games",getPreviousGamesAsJSON());
        }

        return json;
    }

    public JSONObject getPreviousGamesAsJSON() {
        try {
            Map<String, Map<String, String>> previousGamesMapped = new HashMap<>();

            for (Game game : previousGames) {
                previousGamesMapped.put(game.getId().toString(), game.mapGameDetails());
            }

            return new JSONObject(previousGamesMapped);
        } catch (NullPointerException e) {
            return new JSONObject();
        }
    }

    public void addFriend(UserAccount user) {
        this.friends.addFriend(user);
    }

    public void removeFriend(UserAccount user) {
        this.friends.removeFriend(user);
    }

    public Friends getFriends() {
        return this.friends;
    }
}
