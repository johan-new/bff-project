package com.yrgo.bff.project.domain;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;

public class MatchingRequest {

    private String username;
    private LocalDate date;
    private LocalTime time;
    private boolean reservation;
    private String venue;
    private int participants;

    public MatchingRequest(JSONObject jsonObject) {
        String dateString = jsonObject.get("date").toString();
        String timeString = jsonObject.get("time").toString();
        this.username = (String)jsonObject.get("username");
        this.date = LocalDate.parse(dateString);
        this.time = LocalTime.parse(timeString);
        this.reservation = (boolean)jsonObject.get("reservation");
        this.venue = (String)jsonObject.get("venue");
        this.participants = (Integer)jsonObject.get("participants");
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

    public int getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "MatchingRequest{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", reservation=" + reservation +
                ", venue='" + venue + '\'' +
                ", participants=" + participants +
                '}';
    }
}
