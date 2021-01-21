package com.yrgo.bff.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeId;
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

    public static final String GENERAL_NOTIFICATION = "Detta är en generell notis!";
    public static final String REJECTED_JOIN_NOTIFICATION = "Spelarrangören tog bort din förfrågan.";
    public static final String ACCEPTED_JOIN_NOTIFICATION = "Spelarrangören accepterade din förfrågan.";
    public static final String NEW_JOIN_NOTIFICATION = "En spelare vill gå med i din match.";
    public static final String GAME_CREATED_NOTIFICATION = "Lobbyn är full och ett spel är skapat!";

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
        } catch (Exception e) {
            log.debug(getClass().getSimpleName() + ": " + toString() +
                    "\nException creating MatchingRequest, fallback to default value participants=3");
            log.debug(e.getMessage());
            requestedParticipants = 3;
        }

        joinRequests = new ArrayList();
        confirmedParticipants = new HashSet<>();
    }

    public void accept(int elementNumber){
        joinRequests.get(elementNumber).accept(this);
    }

//    public void reject(int elementNumber){
//        joinRequests.get(elementNumber).reject(this);
//    }
    public void reject(int elementNumber){
    joinRequests.get(elementNumber).reject(this);
    }

    public void askToJoin(UserAccount userAccount) {
        System.out.println("*** " + userAccount);
        this.joinRequests.add(new JoinRequest(userAccount, this));
    }

    public Set<String> getConfirmedParticipants() {
        return Collections.unmodifiableSet(confirmedParticipants);
    }

    public Map<Integer,JoinRequest> getJoinRequests() {
        System.out.println("join requests size" + joinRequests.size());
        Map<Integer,JoinRequest> returnValues = new HashMap();
        for (int i = 0; i < joinRequests.size(); i++) {
            returnValues.put(i,joinRequests.get(i));
        }
        return returnValues;
    }

    private void setRequestedParticipants(int requestedParticipants) {
        if (requestedParticipants>0 && requestedParticipants < 4)
            this.requestedParticipants = requestedParticipants;
        else //sets default value
            this.requestedParticipants = 3;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTime() {
        return localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalTime getLocalTime() { return this.localTime; }

    public boolean isCourtIsBooked() {
        return courtIsBooked;
    }

    public String getVenue() {
        return venue;
    }

    public String getLocation() {
        return location;
    }

    public int getRequestedParticipants() {
        return requestedParticipants;
    }

    @Override
    public String toString() {
        return new JSONObject(mapped()).toString();
    }

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

    public JSONObject toJSON(){
        return null;
    }

    private Map<Integer,String> joinRequestsMapped() {
        Map<Integer,String> joinRequestsData = new HashMap<>();
        for (int i = 0; i < joinRequests.size(); i++) {
            joinRequestsData.put(i,joinRequests.get(i).toString());
        }
        return joinRequestsData;
    }

    public class JoinRequest{
        JoinRequestStatus status;

        @JsonIgnore
        MatchingRequest matchingRequestParent;
        //person who requested to join
        String sender;

        JoinRequest(UserAccount sender, MatchingRequest matchingRequest){
            this.sender = sender.getUsername();
            this.matchingRequestParent = matchingRequest;
            status = JoinRequestStatus.PENDING;
        }

        void accept(MatchingRequest matchingRequest){
            status = JoinRequestStatus.ACCEPTED;
            confirmedParticipants.add(sender);
        }

        void reject(MatchingRequest matchingRequest){
            status = JoinRequestStatus.REJECTED;
        }

        public MatchingRequest getMatchingRequestParent() {
            return matchingRequestParent;
        }

        public JoinRequestStatus getStatus() {
            return status;
        }

        public String getSender() {
            return sender;
        }

        boolean isPending(){
            return this.status==JoinRequestStatus.PENDING;
        }

        @Override
        public String toString() {
            Map<String,String> data = new HashMap<>();
            data.put("sender",sender);
            data.put("status",status.name());
            return new JSONObject(data).toString();
        }
    }

    enum JoinRequestStatus{
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
