package org.example.service;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;

public class PilotMonthlyStats {
    private Duration totalMonthlyDuration = Duration.ZERO;
    private Map<LocalDate, Duration> dailyDurations = new HashMap<>();
    private Map<WeekFields, Duration> weeklyDurations = new HashMap<>();
    private boolean exceeded80Hours = false;
    private boolean exceeded36HoursWeek = false;
    private boolean exceeded8HoursDay = false;

    public void addFlightDuration(LocalDateTime takeoffTime, Duration flightDuration) {
        totalMonthlyDuration = totalMonthlyDuration.plus(flightDuration);

        LocalDate flightDate = takeoffTime.toLocalDate();
        dailyDurations.put(flightDate, dailyDurations.getOrDefault(flightDate, Duration.ZERO).plus(flightDuration));

        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfYear = takeoffTime.get(weekFields.weekOfWeekBasedYear());
        weeklyDurations.put(weekFields, weeklyDurations.getOrDefault(weekFields, Duration.ZERO).plus(flightDuration));

        // Check for thresholds
        if (totalMonthlyDuration.toHours() > 80) {
            exceeded80Hours = true;
        }
        if (weeklyDurations.get(weekFields).toHours() > 36) {
            exceeded36HoursWeek = true;
        }
        if (dailyDurations.get(flightDate).toHours() > 8) {
            exceeded8HoursDay = true;
        }
    }

    public Duration getTotalMonthlyDuration() {
        return totalMonthlyDuration;
    }

    public boolean isExceeded80Hours() {
        return exceeded80Hours;
    }

    public boolean isExceeded36HoursWeek() {
        return exceeded36HoursWeek;
    }

    public boolean isExceeded8HoursDay() {
        return exceeded8HoursDay;
    }
}

