package service;

import model.LoadScenario;
import model.LogEntry;
import model.PerformanceMode;
import model.ProcessorState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Erfasst einfache Ereignisse eines Simulationslaufs.
 */
public class EventLogger {

    public static final String EVENT_MODE_CHANGE = "MODUSWECHSEL";
    public static final String EVENT_SCENARIO_CHANGE = "LASTSZENARIOWECHSEL";
    public static final String EVENT_TEMPERATURE_WARNING = "TEMPERATURWARNUNG";

    private static final double TEMPERATURE_WARNING_THRESHOLD_CELSIUS = 70.0;

    private final List<LogEntry> entries;
    private String lastModeKey;
    private String lastScenarioKey;

    public EventLogger() {
        this.entries = new ArrayList<>();
        this.lastModeKey = null;
        this.lastScenarioKey = null;
    }

    public void logModeChangeIfNeeded(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        validateEventData(performanceMode, loadScenario, processorState);

        if (lastModeKey == null || !lastModeKey.equals(performanceMode.getKey())) {
            entries.add(createEntry(
                    EVENT_MODE_CHANGE,
                    "Leistungsmodus wurde ausgewählt oder geändert.",
                    performanceMode,
                    loadScenario,
                    processorState
            ));

            lastModeKey = performanceMode.getKey();
        }
    }

    public void logScenarioChangeIfNeeded(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        validateEventData(performanceMode, loadScenario, processorState);

        if (lastScenarioKey == null || !lastScenarioKey.equals(loadScenario.getKey())) {
            entries.add(createEntry(
                    EVENT_SCENARIO_CHANGE,
                    "Lastszenario wurde ausgewählt oder geändert.",
                    performanceMode,
                    loadScenario,
                    processorState
            ));

            lastScenarioKey = loadScenario.getKey();
        }
    }

    public void logTemperatureWarningIfNeeded(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        validateEventData(performanceMode, loadScenario, processorState);

        if (processorState.getTemperatureCelsius() >= TEMPERATURE_WARNING_THRESHOLD_CELSIUS) {
            entries.add(createEntry(
                    EVENT_TEMPERATURE_WARNING,
                    "CPU-Temperatur hat mindestens 70 °C erreicht.",
                    performanceMode,
                    loadScenario,
                    processorState
            ));
        }
    }

    public LogEntry createEntry(
            String eventType,
            String description,
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        validateEventData(performanceMode, loadScenario, processorState);

        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("Ereignistyp darf nicht leer sein.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Ereignisbeschreibung darf nicht leer sein.");
        }

        return new LogEntry(
                LocalDateTime.now(),
                eventType,
                description,
                performanceMode.getDisplayName(),
                loadScenario.getDisplayName(),
                processorState.getCpuLoadPercent(),
                processorState.getClockGhz(),
                processorState.getTemperatureCelsius(),
                "Normal"
        );
    }

    public List<LogEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    public void clear() {
        entries.clear();
        lastModeKey = null;
        lastScenarioKey = null;
    }

    private void validateEventData(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        if (performanceMode == null) {
            throw new IllegalArgumentException("Leistungsmodus darf nicht null sein.");
        }

        if (loadScenario == null) {
            throw new IllegalArgumentException("Lastszenario darf nicht null sein.");
        }

        if (processorState == null) {
            throw new IllegalArgumentException("Prozessorzustand darf nicht null sein.");
        }

        if (!processorState.hasClock()) {
            throw new IllegalArgumentException("Für ein Ereignis muss eine CPU-Taktfrequenz vorhanden sein.");
        }

        if (!processorState.hasTemperature()) {
            throw new IllegalArgumentException("Für ein Ereignis muss eine CPU-Temperatur vorhanden sein.");
        }
    }
}