package org.example.service;

import org.example.models.Flight;
import org.example.models.Pilot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightDataLoader {

    public List<Flight> loadFlights(String filePath) throws IOException {
        List<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String aircraftType = parts[0].trim();
                String aircraftNumber = parts[1].trim();
                LocalDateTime takeoffTime = LocalDateTime.parse(parts[2].trim(), dateTimeFormatter);
                LocalDateTime landingTime = LocalDateTime.parse(parts[3].trim(), dateTimeFormatter);
                String departureAirport = parts[4].trim();
                String arrivalAirport = parts[5].trim();
                List<Pilot> crew = new ArrayList<>();
                for (int i = 6; i < parts.length; i++) {
                    crew.add(new Pilot(parts[i].trim()));
                }
                flights.add(new Flight(aircraftType, aircraftNumber, takeoffTime, landingTime, departureAirport, arrivalAirport, crew));
            }
        }
        return flights;
    }
}
