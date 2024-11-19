package org.example.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.Map;

public class FlightDataSaver {

    public void saveData(Map<String, Map<YearMonth, PilotMonthlyStats>> pilotStats, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<pilots>\n");
            for (Map.Entry<String, Map<YearMonth, PilotMonthlyStats>> entry : pilotStats.entrySet()) {
                writer.write("  <pilot name=\"" + entry.getKey() + "\">\n");
                for (Map.Entry<YearMonth, PilotMonthlyStats> monthEntry : entry.getValue().entrySet()) {
                    PilotMonthlyStats stats = monthEntry.getValue();
                    writer.write("    <month year=\"" + monthEntry.getKey().getYear() + "\" month=\"" + monthEntry.getKey().getMonthValue() + "\">\n");
                    writer.write("      <totalDuration>" + stats.getTotalMonthlyDuration().toHours() + " hours</totalDuration>\n");
                    writer.write("      <exceeded80Hours>" + stats.isExceeded80Hours() + "</exceeded80Hours>\n");
                    writer.write("      <exceeded36HoursWeek>" + stats.isExceeded36HoursWeek() + "</exceeded36HoursWeek>\n");
                    writer.write("      <exceeded8HoursDay>" + stats.isExceeded8HoursDay() + "</exceeded8HoursDay>\n");
                    writer.write("    </month>\n");
                }
                writer.write("  </pilot>\n");
            }
            writer.write("</pilots>\n");
        }
    }
}

