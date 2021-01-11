package com.yrgo.bff.project.domain;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/***
 * MatchingRequest class
 *
 *
 *
 */


public class MatchingRequest {

    //organizer
    private String username;

    private LocalDate date;
    private LocalTime time;
    private boolean courtIsBooked;
    private String venue;
    private int requestedParticipants;

    private List<String> participants;

    //contains only JoinRequest objects
    private List<JoinRequest> joinRequests;


    public MatchingRequest(JSONObject jsonObject) {
        String dateString = jsonObject.get("date").toString();
        String timeString = jsonObject.get("time").toString();


        this.username = (String)jsonObject.get("username");
        this.date = LocalDate.parse(dateString);
        this.time = LocalTime.parse(timeString);
        this.courtIsBooked = (boolean)jsonObject.get("reservation");
        this.venue = (String)jsonObject.get("venue");

        try {
            setRequestedParticipants(Integer.parseInt(jsonObject.get("participants").toString()));
        } catch (NumberFormatException | NullPointerException e) {
            //sets default value to three
            requestedParticipants = 3;
        }

        joinRequests = new ArrayList();
        participants = new ArrayList();
    }

    public void accept(int elementNumber){
        joinRequests.get(elementNumber).accept(this);
    }

    public void reject(int elementNumber){
        joinRequests.get(elementNumber).reject(this);
    }

    public void askToJoin(UserAccount userAccount) {
        this.joinRequests.add(new JoinRequest(userAccount, this));
    }

    public Map<Integer,JoinRequest> getJoinRequests() {
        Map<Integer,JoinRequest> returnValues = new HashMap();
        for (int i = 0; i < joinRequests.size(); i++) {
            returnValues.put(i,joinRequests.get(i));
        }
        return returnValues;
    }

    /*public List<JoinRequest> getPendingJoinRequests(){
        return this.joinRequests
                .stream()
                .filter(JoinRequest::isPending)
                .collect(Collectors.toList());
    }*/

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

    public LocalTime getTime() {
        return time;
    }

    public boolean isCourtIsBooked() {
        return courtIsBooked;
    }

    public String getVenue() {
        return venue;
    }

    public int getRequestedParticipants() {
        return requestedParticipants;
    }

    @Override
    public String toString() {
        Map request = new HashMap<>();
        request.put("organizer",username);
        request.put("venue",venue);
        request.put("date",date);
        request.put("time",time);
        request.put("requestedParticipants", requestedParticipants);
        return new JSONObject(request).toJSONString();
    }

    public class JoinRequest{
        JoinRequestStatus status;
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
            participants.add(sender);
        }

        void reject(MatchingRequest matchingRequest){
            status = JoinRequestStatus.REJECTED;
        }

        public JoinRequestStatus getStatus() {
            return status;
        }

        boolean isPending(){
            return this.status==JoinRequestStatus.PENDING;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " from " + sender + "\t"
                    + status.name() + "\t" + matchingRequestParent.toString();
        }
    }

    enum JoinRequestStatus{
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
