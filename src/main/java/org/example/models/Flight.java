package org.example.models;

import java.time.LocalDateTime;
import java.util.List;

public class Flight {

    private String aircraftNumber;

    private String aircraftType;

    private LocalDateTime takeoffTime;

    private LocalDateTime landingTime;

    private String departureAirport;

    private String arrivalAirport;

    private List<Pilot> crew;

    public Flight(String aircraftType, String aircraftNumber, LocalDateTime takeoffTime, LocalDateTime landingTime, String departureAirport, String arrivalAirport, List<Pilot> crew) {
        this.aircraftType = aircraftType;
        this.aircraftNumber = aircraftNumber;
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.crew = crew;
    }

    public String getAircraftNumber() {
        return aircraftNumber;
    }

    public void setAircraftNumber(String aircraftNumber) {
        this.aircraftNumber = aircraftNumber;
    }

    public List<Pilot> getCrew() {
        return crew;
    }

    public void setCrew(List<Pilot> crew) {
        this.crew = crew;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(LocalDateTime landingTime) {
        this.landingTime = landingTime;
    }

    public LocalDateTime getTakeoffTime() {
        return takeoffTime;
    }

    public void setTakeoffTime(LocalDateTime takeoffTime) {
        this.takeoffTime = takeoffTime;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }
}
