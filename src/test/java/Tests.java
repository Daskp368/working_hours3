import org.example.models.Flight;
import org.example.models.Pilot;
import org.example.service.FlightDataLoader;
import org.example.service.FlightDataProcessor;
import org.example.service.FlightDataSaver;
import org.example.service.PilotMonthlyStats;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void testLoadFlights() throws IOException {
        FlightDataLoader flightDataLoader = new FlightDataLoader();
        List<Flight> flights = flightDataLoader.loadFlights("flight.txt");

        assertNotNull(flights);
        assertEquals(4, flights.size());

        Flight flight1 = flights.get(0);
        assertEquals("testType", flight1.getAircraftType());
        assertEquals("testNumber", flight1.getAircraftNumber());
        assertEquals(LocalDateTime.parse("2023-01-01T00:00:00"), flight1.getTakeoffTime());
        assertEquals(LocalDateTime.parse("2023-01-01T10:00:00"), flight1.getLandingTime());
        assertEquals(2, flight1.getCrew().size());
        assertEquals("testNamePilot1", flight1.getCrew().get(0).getName());
    }

    @Test
    public void testProcessFlights() {
        FlightDataProcessor flightDataProcessor = new FlightDataProcessor();

        List<Flight> flights = List.of(
                new Flight("testType", "testNumber",
                        LocalDateTime.parse("2023-01-01T00:00:00"),
                        LocalDateTime.parse("2023-01-01T10:00:00"),
                        "testDepartureAirport", "testArrivalAirport",
                        List.of(new Pilot("Pilot1"), new Pilot("Pilot2"))),
                new Flight("testType", "testNumber2",
                        LocalDateTime.parse("2023-01-02T00:00:00"),
                        LocalDateTime.parse("2023-01-02T05:00:00"),
                        "testDepartureAirport2", "testArrivalAirport2",
                        List.of(new Pilot("Pilot1")))
        );

        Map<String, Map<YearMonth, PilotMonthlyStats>> pilotStats = flightDataProcessor.processFlights(flights);

        assertNotNull(pilotStats);
        assertEquals(2, pilotStats.size());
        assertTrue(pilotStats.containsKey("Pilot1"));
        assertTrue(pilotStats.containsKey("Pilot2"));

        Map<YearMonth, PilotMonthlyStats> pilot1Stats = pilotStats.get("Pilot1");
        assertNotNull(pilot1Stats);
        assertEquals(1, pilot1Stats.size());

        YearMonth january2023 = YearMonth.of(2023, 1);
        PilotMonthlyStats stats = pilot1Stats.get(january2023);
        assertNotNull(stats);
        assertEquals(15, stats.getTotalMonthlyDuration().toHours());
        assertTrue(stats.isExceeded8HoursDay());
    }

    @Test
    public void testSaveData() throws IOException {
        FlightDataSaver flightDataSaver = new FlightDataSaver();

        Map<String, Map<YearMonth, PilotMonthlyStats>> pilotStats = new HashMap<>();
        YearMonth january2023 = YearMonth.of(2023, 1);
        PilotMonthlyStats stats = new PilotMonthlyStats();
        stats.addFlightDuration(LocalDateTime.parse("2023-01-01T00:00:00"), Duration.ofHours(10));
        stats.addFlightDuration(LocalDateTime.parse("2023-01-02T00:00:00"), Duration.ofHours(5));

        Map<YearMonth, PilotMonthlyStats> monthlyStats = new HashMap<>();
        monthlyStats.put(january2023, stats);
        pilotStats.put("Pilot1", monthlyStats);

        String filePath = "test_pilot_times.xml";
        flightDataSaver.saveData(pilotStats, filePath);

        File file = new File(filePath);
        assertTrue(file.exists());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            String xmlContent = content.toString();
            assertTrue(xmlContent.contains("<pilot name=\"Pilot1\">"));
            assertTrue(xmlContent.contains("<totalDuration>15 hours</totalDuration>"));
            assertTrue(xmlContent.contains("<exceeded8HoursDay>true</exceeded8HoursDay>"));
        } finally {
            file.delete();
        }
    }
}
