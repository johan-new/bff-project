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

/**
 * UserAccount
 *
 * Representing a user of the application. Passwords are encoded by the service classes, and not here.
 * To avoid sending password data, the JSON parsing is done manually.
 *
 * */

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

    /**
     * Set-ers used to ensure valid username
     *
     * @param username username, the unique identifier and
     *                 primary key in the database table
     * @param password
     * @throws BadRequestException when not using a valid email address
     */
    public UserAccount(String username, String password) throws BadRequestException {
        setUsername(username);
        setPassword(password);
    }


    /**
     * required by hibernate
     */
    UserAccount(){
    }

    /**
     * @return previous games as a set
     */
    public Set<Game> getPreviousGames() {
        return Collections.unmodifiableSet(previousGames);
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username desired username (email address)
     * @throws BadRequestException when its not
     */
    private void setUsername(String username) throws BadRequestException {
        if (UserAccountServiceImplementation.validEmailAddress(username)) {
            this.username = username;
        } else {
            throw new BadRequestException("Not a valid email address!");
        }
    }

    /**
     * Always hashed, but the hashing does not occur in this layer.
     * This method is used by the security implementation.
     *
     * @return the password as a String.
     */
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

    /**
     * @return the username only. Other data only needed
     *      by web layer, and then toJSON is used.
     */
    @Override
    public String toString() {
        return username;
    }

    /**
     * Since there can only by one with a specific username, the comparison
     * of those are enough. IDE generated.
     *
     * @param o the object
     * @return if the usernames are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount user = (UserAccount) o;
        return Objects.equals(username, user.username);
    }

    /**
     * @return hashcode of the username
     */
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    /**
     * Used in order not to expose password hashes, and
     * to avoid infinite recursion.
     * @return the object data as json
     */
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

    /**
     * @return the games, future or historic ones, as json
     */
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

    /**
     * @param newFriend the new friend
     */
    public void addFriend(UserAccount newFriend) {
        if (friends==null) {
            friends=new HashSet<>();
        }
        this.friends.add(newFriend.getUsername());
    }

    /**
     * @param friend to remove
     */
    public void removeFriend(UserAccount friend) {
        try {
            friends.remove(friend.getUsername());
        } catch (NullPointerException e) {
            log.error("Could not delete friend " + friend + " from " + this.username + "s' friend list.");
        }
    }

    /**
     * @return the friends, read-only (the only
     *          way to modify this should be by
     *          adding or removing a friend)
     */
    public Set<String> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    /**
     * @return short bio of the user
     */
    public String getPresentation() {
        return presentation;
    }

    /**
     * @param presentation setting a short bio (keeps
     *                     it to max 500 chars)
     */
    public void setPresentation(String presentation) {
        this.presentation = presentation.length()>500 ? presentation.substring(0,499) : presentation;
    }

    /**
     * @return the city where the user lives
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city.length()>50) {
            city = city.substring(0,49);
        }
        this.city = city;
    }

    /**
     * @return gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender sets the gender of the user
     */
    public void setGender(Gender gender) {
        this.gender = gender.name();
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age set age within reasonable interval
     */
    public void setAge(int age) {
        if (age > 15 && age < 120)
        {
            this.age = age;
        } else {
            log.debug("Proper age interval is 16-119 years. Property not set.");
        }
    }

    /**
     * genders
     */
    public enum Gender{
        FEMALE,MALE,NONBINARY
    }

    /**
     * @param s the reference to the string to check
     * @return whether its null or empty (taken whitespaces into
     *          account)
     */
    private boolean nullOrEmpty(String s) {
        return s == null || s.isBlank();
    }


}