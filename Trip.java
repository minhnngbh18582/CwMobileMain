package com.example.cwmobile;

public class Trip {
    protected int tripId;
    protected String tripName;
    protected String tripDestination;
    protected String tripDate;
    protected String tripRisk;
    protected String tripDescription;
    protected int tripParticipants;
    protected int tripEstimatedCost;

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripRisk() {
        return tripRisk;
    }

    public void setTripRisk(String tripRisk) {
        this.tripRisk = tripRisk;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public int getTripParticipants() {
        return tripParticipants;
    }

    public void setTripParticipants(int tripParticipants) {
        this.tripParticipants = tripParticipants;
    }

    public int getTripEstimatedCost() {
        return tripEstimatedCost;
    }

    public void setTripEstimatedCost(int tripEstimatedCost) {
        this.tripEstimatedCost = tripEstimatedCost;
    }

}
