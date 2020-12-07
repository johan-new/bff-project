package com.yrgo.bff.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yrgo.bff.project.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.Set;

@Entity
public class ApplicationUser {

    //jsonignore due to infinite recursion otherwise when creating json object
    @ManyToMany (mappedBy = "participants") @JsonIgnore
    Set<Game> previousGames;

    @Id
    private String username;

    @ManyToOne
    private Friends friends = new Friends();

    @Autowired @Transient
    NotificationService notificationService;

    //this should never be serialized by the web layer
//    @JsonIgnore
    private String password;

    public ApplicationUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    ApplicationUser(){}

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
        return "User{" +
                "userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void notifyUser(String msg){
        System.out.println("USER MATCHED FOR "+ this.username + "\n" + msg);
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

    public void addFriend(ApplicationUser user) {
        this.friends.addFriend(user);
    }

    public void removeFriend(ApplicationUser user) {
        this.friends.removeFriend(user);
    }

    public Friends getFriends() {
        return this.friends;
    }
}
