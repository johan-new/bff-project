package com.yrgo.bff.project.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @ManyToMany (mappedBy = "participants")
    Set<Game> previousGames;

    @Id
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    User(){}

    public Set<Game> getPreviousGames() {

        return previousGames;
    }

    public void setPreviousGames(Set<Game> previousGames) {
        this.previousGames = previousGames;
    }

    public String getUserName() {
        return userName;
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
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    void notfifyUser(String msg){
        //TODO: Meddela användare
    }

}
