package com.yrgo.bff.project.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Entity
public class User {

    @Transient
    private List<String> previousGames;

    @Id
    private String userName;

    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    User(){}

    public List<String> getPreviousGames() {
        return previousGames;
    }

    public void setPreviousGames(List<String> previousGames) {
        this.previousGames = previousGames;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
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
