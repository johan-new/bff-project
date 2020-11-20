package com.yrgo.bff.project.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {
    private List<String> previousGames;

    @Id
    private String userName;

    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    void notfifyUser(String msg){
        //TODO: Meddela användare
    }

}
