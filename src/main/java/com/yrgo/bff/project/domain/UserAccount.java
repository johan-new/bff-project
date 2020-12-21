package com.yrgo.bff.project.domain;

import com.yrgo.bff.project.service.UserAccountServiceImplementation;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.json.simple.JSONObject;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
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

    public UserAccount(String username, String password) throws Exception {
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

    public void setPreviousGames(Set<Game> previousGames) {
        this.previousGames = previousGames;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) throws Exception {
        if (UserAccountServiceImplementation.validEmailAddress(username)) {
            this.username = username;
        } else {
            throw new Exception("Inte giltigt epostadress!");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
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

    public void setPresentation(@Length (max = 500, message = "Maximum 500 chars") String presentation) {
        this.presentation = presentation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(@Length (max = 50) String city) {
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

    public void setAge(@Range (min=0, max = 120, message = "Not an valid age") int age) {
        this.age = age;
    }

    enum Gender{
        FEMALE,MALE,NONBINARY
    }


}