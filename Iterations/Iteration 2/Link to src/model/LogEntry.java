package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Beschreibt einen Ereignisprotokolleintrag eines Simulationslaufs.
 */
public class LogEntry {

    private final LocalDateTime timestamp;
    private final String eventType;
    private final String description;
    private final String performanceModeName;
    private final String loadScenarioName;
    private final double cpuLoadPercent;
    private final double clockGhz;
    private final double temperatureCelsius;
    private final String throttlingState;

    public LogEntry(
            LocalDateTime timestamp,
            String eventType,
            String description,
            String performanceModeName,
            String loadScenarioName,
            double cpuLoadPercent,
            double clockGhz,
            double temperatureCelsius,
            String throttlingState
    ) {
        this.timestamp = timestamp;
        this.eventType = eventType;
        this.description = description;
        this.performanceModeName = performanceModeName;
        this.loadScenarioName = loadScenarioName;
        this.cpuLoadPercent = cpuLoadPercent;
        this.clockGhz = clockGhz;
        this.temperatureCelsius = temperatureCelsius;
        this.throttlingState = throttlingState;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public String getPerformanceModeName() {
        return performanceModeName;
    }

    public String getLoadScenarioName() {
        return loadScenarioName;
    }

    public double getCpuLoadPercent() {
        return cpuLoadPercent;
    }

    public double getClockGhz() {
        return clockGhz;
    }

    public double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public String getThrottlingState() {
        return throttlingState;
    }

    public String toDisplayText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format(
                Locale.GERMANY,
                "[%s] %s: %s | Modus: %s | Szenario: %s | Auslastung: %.0f %% | Takt: %.1f GHz | Temperatur: %.0f °C | Throttling: %s",
                timestamp.format(formatter),
                eventType,
                description,
                performanceModeName,
                loadScenarioName,
                cpuLoadPercent,
                clockGhz,
                temperatureCelsius,
                throttlingState
        );
    }
}