package com.yrgo.bff.project.domain;

import org.json.simple.JSONObject;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatchingRequest {

    private String username;
    private LocalDate date;
    private LocalTime time;
    private boolean reservation;
    private String venue;
    private int requestedParticipants;
    private String organizer;

    private List<String> participants;

    //contains only JoinRequest objects
    private List<JoinRequest> joinRequests;

    public MatchingRequest(JSONObject jsonObject) {
        String dateString = jsonObject.get("date").toString();
        String timeString = jsonObject.get("time").toString();


        this.username = (String)jsonObject.get("username");
        this.date = LocalDate.parse(dateString);
        this.time = LocalTime.parse(timeString);
        this.reservation = (boolean)jsonObject.get("reservation");
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

    public void askToJoin(UserAccount userAccount) {
        this.joinRequests.add(new JoinRequest(userAccount));
    }

    public List<JoinRequest> getJoinRequests() {
        return Collections.unmodifiableList(joinRequests);
    }

    public List<JoinRequest> getPendingJoinRequests(){
        return this.joinRequests
                .stream()
                .filter(JoinRequest::isPending)
                .collect(Collectors.toList());
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(UserAccount organizer) {
        this.organizer = organizer.getUsername();
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

    public LocalTime getTime() {
        return time;
    }

    public boolean isReservation() {
        return reservation;
    }

    public String getVenue() {
        return venue;
    }

    public int getRequestedParticipants() {
        return requestedParticipants;
    }

    @Override
    public String toString() {
        return "MatchingRequest{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", reservation=" + reservation +
                ", venue='" + venue + '\'' +
                ", requestedParticipants=" + requestedParticipants +
                ", organizer='" + organizer + '\'' +
                ", joinRequests=" + joinRequests +
                '}';
    }

    private class JoinRequest{
        JoinRequestStatus status;
        //person who requested to join
        String sender;

        JoinRequest(UserAccount sender){
            this.sender = sender.getUsername();
            status = JoinRequestStatus.PENDING;
        }

        void accept(){
            status = JoinRequestStatus.ACCEPTED;
            participants.add(sender);
        }

        void reject(){
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
            return this.getClass().getSimpleName() + "\t" + sender + "\t" + status.name();
        }
    }

    enum JoinRequestStatus{
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
