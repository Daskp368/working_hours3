package org.example;

import org.example.models.Flight;
import org.example.service.FlightDataLoader;
import org.example.service.FlightDataProcessor;
import org.example.service.FlightDataSaver;
import org.example.service.PilotMonthlyStats;
import java.io.IOException;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        FlightDataLoader flightDataLoader = new FlightDataLoader();
        List<Flight> flights = flightDataLoader.loadFlights("flight.txt");
        FlightDataProcessor flightDataProcessor = new FlightDataProcessor();
        Map<String, Map<YearMonth, PilotMonthlyStats>> pilotTimes = flightDataProcessor.processFlights(flights);
        FlightDataSaver flightDataSaver = new FlightDataSaver();
        flightDataSaver.saveData(pilotTimes, "pilot_times.xml");
    }
}