package org.example.service;

import org.example.models.Flight;
import org.example.models.Pilot;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightDataProcessor {

    public Map<String, Map<YearMonth, PilotMonthlyStats>> processFlights(List<Flight> flights) {
        Map<String, Map<YearMonth, PilotMonthlyStats>> pilotStats = new HashMap<>();

        for (Flight flight : flights) {
            List<Pilot> crew = flight.getCrew();
            LocalDateTime takeoff = flight.getTakeoffTime();
            LocalDateTime landing = flight.getLandingTime();
            Duration flightDuration = Duration.between(takeoff, landing);

            YearMonth yearMonth = YearMonth.from(takeoff);

            for (Pilot pilot : crew) {
                pilotStats.putIfAbsent(pilot.getName(), new HashMap<>());
                Map<YearMonth, PilotMonthlyStats> monthlyStats = pilotStats.get(pilot.getName());

                monthlyStats.putIfAbsent(yearMonth, new PilotMonthlyStats());
                PilotMonthlyStats stats = monthlyStats.get(yearMonth);

                stats.addFlightDuration(takeoff, flightDuration);
            }
        }

        return pilotStats;
    }
}

