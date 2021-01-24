package com.yrgo.bff.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.simple.JSONObject;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/***
 * MatchingRequest class
 *
 * Contains all data for a MatchingRequest. This request will eventually be converted to at Game.
 * Other users kan as to join this (coming) game, by sending a request.
 * Only the organizer of the game can accept/deny users that want to join.
 *
 */


public class MatchingRequest {

    public static Long LATEST_ID;

    static {
        LATEST_ID = 900L;
    }

    public Long getId() {
        return id;
    }

    private Long id;

    //organizer
    private String username;

    private LocalDate date;
    @JsonIgnore
    private LocalTime localTime;
    private boolean courtIsBooked;
    private String venue ,location;
    private int requestedParticipants;

    private Set<String> confirmedParticipants;

    //contains only JoinRequest objects
    private List<JoinRequest> joinRequests;

    @Transient
    private Log log = LogFactory.getLog(getClass());

    public static final String GENERAL_NOTIFICATION = "Detta är en generell notis";
    public static final String REJECTED_JOIN_NOTIFICATION = "Spelarrangören tog bort din förfrågan";
    public static final String ACCEPTED_JOIN_NOTIFICATION = "Spelarrangören accepterade din förfrågan";
    public static final String NEW_JOIN_NOTIFICATION = "En spelare vill gå med i din match";
    public static final String GAME_CREATED_NOTIFICATION = "Lobbyn är full och ett spel är skapat";

    /**
     * Constructor handling all the parsing of the jsonobject that is passed on as a parameter from the wbeblayer.
     * Keeps the web layer nice and clean.
     *
     * @param jsonObject
     */
    public MatchingRequest(JSONObject jsonObject) {

        this.id = LATEST_ID++;

        String dateString = jsonObject.get("date").toString();
        String timeString = jsonObject.get("time").toString();


        this.username = (String)jsonObject.get("username");
        this.date = LocalDate.parse(dateString);
        this.localTime = LocalTime.parse(timeString);
        this.courtIsBooked = (boolean)jsonObject.get("reservation");
        this.venue = (String)jsonObject.get("venue");
        this.location = (String)jsonObject.get("location");

        try {
            setRequestedParticipants(Integer.parseInt(jsonObject.get("participants").toString()));
            if (getRequestedParticipants() <= 0 && getRequestedParticipants() >= 4) {
                throw new Exception("Valid participants 1-3 persons (the organizer is assumed to participate)");
            }
        } catch (Exception e) { //could be triggered with both NumberFormatException or the above
            log.debug(getClass().getSimpleName() + ": " + toString() +
                    "\nException creating MatchingRequest, fallback to default value participants=3");
            log.debug(e.getMessage());
            requestedParticipants = 3;
        }

        joinRequests = new ArrayList();
        confirmedParticipants = new HashSet<>();
    }

    /**
     * Trying to accept a pending joinRequest (only the organizer is able to)
     * @param elementNumber
     */
    public void accept(int elementNumber){
        joinRequests.get(elementNumber).accept();
    }

    /**
     * Trying to reject a pending joinRequest (only the organizer is able to)
     * @param elementNumber
     */
    public void reject(int elementNumber){
    joinRequests.get(elementNumber).reject();
    }

    /**
     * Some user asks to join the game
     * @param userAccount
     */
    public void askToJoin(UserAccount userAccount) {
        this.joinRequests.add(new JoinRequest(userAccount, this));
    }

    /**
     * @return a set of strings, the usernames of the confirmed participants
     */
    public Set<String> getConfirmedParticipants() {
        return Collections.unmodifiableSet(confirmedParticipants);
    }

    /**
     * @return the list mapped with element numbers, needed for
     *          requests (join etc)
     */
    public Map<Integer,JoinRequest> getJoinRequests() {
        Map<Integer,JoinRequest> returnValues = new HashMap();
        for (int i = 0; i < joinRequests.size(); i++) {
            returnValues.put(i,joinRequests.get(i));
        }
        return returnValues;
    }

    /**
     * Fool-proofed set-er
     * @param requestedParticipants amount of players besides toe organizer
     */
    private void setRequestedParticipants(int requestedParticipants) {
        if (requestedParticipants>0 && requestedParticipants < 4)
            this.requestedParticipants = requestedParticipants;
        else //sets default value
            this.requestedParticipants = 3;
    }

    /**
     * @return the username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the suggested date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the suggested time formatted in a nice way
     */
    public String getTime() {
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * @return the suggested time
     */
    public LocalTime getLocalTime() { return this.localTime; }

    public boolean isCourtIsBooked() {
        return courtIsBooked;
    }

    /**
     * @return the venue where the game will take place
     */
    public String getVenue() {
        return venue;
    }

    /**
     * @return geographical location, i.e. Brooklyn, Stockholm or Beijing
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the number of requested participants
     */
    public int getRequestedParticipants() {
        return requestedParticipants;
    }

    /**
     * @return a nicely formattad manually mappend json String
     */
    @Override
    public String toString() {
        return new JSONObject(mapped()).toString();
    }

    /**
     * @return nicely manually mapped data of the object
     * (to avoid infinite recursion)
     */
    public Map<Object,Object> mapped(){
        Map request = new HashMap<String,String>();
        request.put("id",id);
        request.put("organizer",username);
        request.put("venue",venue);
        request.put("date",date);
        request.put("time",localTime);
        request.put("requestedParticipants", requestedParticipants);
        request.put("joinRequests", joinRequestsMapped());
        request.put("confirmedParticipants", confirmedParticipants.toString());
        return request;
    }

    /**
     * @return mapped data as json
     */
    public JSONObject toJSON(){
        return new JSONObject(mapped());
    }

    /**
     * @return nicely manually mapped data of the requests
     * (to avoid infinite recursion)
     */
    private Map<Integer,String> joinRequestsMapped() {
        Map<Integer,String> joinRequestsData = new HashMap<>();
        for (int i = 0; i < joinRequests.size(); i++) {
            joinRequestsData.put(i,joinRequests.get(i).toString());
        }
        return joinRequestsData;
    }

    /**
     * Inner class, only used by the MatchingRequest.
     */
    public class JoinRequest{
        JoinRequestStatus status;

        @JsonIgnore
        MatchingRequest matchingRequestParent;
        //person who requested to join
        String sender;

        /**
         * Set-ers and of course setting the status (Poor mans state design pattern?).
         * @param sender the person who requested to join
         * @param matchingRequest passing a reference to the object it belongs to
         */
        JoinRequest(UserAccount sender, MatchingRequest matchingRequest){
            this.sender = sender.getUsername();
            this.matchingRequestParent = matchingRequest;
            status = JoinRequestStatus.PENDING;
        }

        /**
         * Accepting the request
         */
        void accept(){
            status = JoinRequestStatus.ACCEPTED;
            confirmedParticipants.add(sender);
        }

        /**
         * Rejecting the request
         */
        void reject(){
            status = JoinRequestStatus.REJECTED;
        }

        /**
         * @return the reference to the parent MatchingRequest object
         */
        public MatchingRequest getMatchingRequestParent() {
            return matchingRequestParent;
        }

        /**
         * @return the current status, cannot be null since its setted in
         * t        the constructor.
         */
        public JoinRequestStatus getStatus() {
            return status;
        }

        /**
         * @return the username of the person whom sent the request
         */
        public String getSender() {
            return sender;
        }

        /**
         * @return is the requests is pending
         */
        boolean isPending(){
            return this.status==JoinRequestStatus.PENDING;
        }

        /**
         * @return manual mapping to avoid infinate recursion.
         *          As JSON to simplify use in web layer.
         */
        @Override
        public String toString() {
            Map<String,String> data = new HashMap<>();
            data.put("sender",sender);
            data.put("status",status.name());
            return new JSONObject(data).toString();
        }
    }

    /**
     * Possible states of the status of the request
     */
    enum JoinRequestStatus{
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
