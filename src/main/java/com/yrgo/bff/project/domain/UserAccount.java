package com.yrgo.bff.project.domain;

import com.yrgo.bff.project.exception.BadRequestException;
import com.yrgo.bff.project.service.useraccount.UserAccountServiceImplementation;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.*;

@Entity
public class UserAccount {
    @Transient
    private Log log = LogFactory.getLog(getClass());

    @ManyToMany (mappedBy = "participants")
    Set<Game> previousGames;

    @Id
    private String username;

    @ElementCollection
    private Set<String> friends;

    private String password, presentation, city, gender;
    private int age;

    public UserAccount(String username, String password) throws BadRequestException {
        setUsername(username);
        setPassword(password);
    }

    public UserAccount(String username, String password, String presentation, String city, Gender gender, int age)
            throws Exception {
        setUsername(username);
        setPassword(password);
        setPresentation(presentation);
        setCity(city);
        setGender(gender);
        setAge(age);
    }

    UserAccount(){
    }

    public Set<Game> getPreviousGames() {
        return Collections.unmodifiableSet(previousGames);
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) throws BadRequestException {
        if (UserAccountServiceImplementation.validEmailAddress(username)) {
            this.username = username;
        } else {
            throw new BadRequestException("Inte giltigt epostadress!");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws BadRequestException {
            if (password != null && !password.isBlank()) {
                this.password = password;
            } else
            {
                throw new BadRequestException("Lösenordet är tomt!");
            }
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
        if (!nullOrEmpty(getPresentation())) json.put("presentation",getPresentation());
        if (!nullOrEmpty(getCity())) json.put("city",getCity());
        if (!nullOrEmpty(getGender())) json.put("gender",getGender());
        if (getAge()!=0) json.put("age",getAge());

        if (!getPreviousGamesAsJSON().isEmpty()) {
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

    public void addFriend(UserAccount newFriend) {
        if (friends==null) {
            friends=new HashSet<>();
        }
        this.friends.add(newFriend.getUsername());
    }

    public void removeFriend(UserAccount friend) {
        try {
            friends.remove(friend.getUsername());
        } catch (NullPointerException e) {
            log.error("Kunde ej ta bort vän\n" + e.getMessage());
        }
    }

    public Set<String> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation.length()>500 ? presentation.substring(0,499) : presentation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city.length()>50) {
            city = city.substring(0,49);
        }
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender.name();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) throws Exception {
        if (age > 15 && age < 120)
        {
            this.age = age;
        } else {
            throw new Exception("Ogiltig ålder!");
        }
    }

    public enum Gender{
        FEMALE,MALE,NONBINARY
    }

    private boolean nullOrEmpty(String s) {
        return s == null || s.isBlank();
    }


}