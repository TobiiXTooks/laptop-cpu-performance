package service;

import model.LoadScenario;
import model.LogEntry;
import model.PerformanceMode;
import model.ProcessorState;
import model.ThrottlingState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Erfasst Ereignisse eines Simulationslaufs.
 */
public class EventLogger {

    public static final String EVENT_MODE_CHANGE = "MODUSWECHSEL";
    public static final String EVENT_SCENARIO_CHANGE = "LASTSZENARIOWECHSEL";
    public static final String EVENT_TEMPERATURE_WARNING = "TEMPERATURWARNUNG";
    public static final String EVENT_THROTTLING_ACTIVATED = "THROTTLING_AKTIVIERT";
    public static final String EVENT_THROTTLING_CHANGED = "THROTTLING_GEÄNDERT";
    public static final String EVENT_SIMULATION_RESET = "SIMULATION_ZURÜCKGESETZT";

    private static final double TEMPERATURE_WARNING_THRESHOLD_CELSIUS = 70.0;

    private final List<LogEntry> entries;
    private String lastModeKey;
    private String lastScenarioKey;
    private ThrottlingState lastThrottlingState;

    public EventLogger() {
        this.entries = new ArrayList<>();
        this.lastModeKey = null;
        this.lastScenarioKey = null;
        this.lastThrottlingState = null;
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

    public void logThrottlingActivatedIfNeeded(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        validateEventData(performanceMode, loadScenario, processorState);

        if (processorState.getThrottlingState().isActiveThrottling()) {
            entries.add(createEntry(
                    EVENT_THROTTLING_ACTIVATED,
                    "Thermal Throttling wurde aktiviert.",
                    performanceMode,
                    loadScenario,
                    processorState
            ));
        }
    }

    public void logThrottlingChangedIfNeeded(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState
    ) {
        validateEventData(performanceMode, loadScenario, processorState);

        ThrottlingState currentState = processorState.getThrottlingState();
        if (lastThrottlingState == null || lastThrottlingState != currentState) {
            entries.add(createEntry(
                    EVENT_THROTTLING_CHANGED,
                    "Throttling-Stufe wurde ausgewählt oder geändert.",
                    performanceMode,
                    loadScenario,
                    processorState
            ));

            lastThrottlingState = currentState;
        }
    }

    public void logSimulationReset() {
        entries.add(new LogEntry(
                LocalDateTime.now(),
                EVENT_SIMULATION_RESET,
                "Simulationslauf wurde zurückgesetzt.",
                "-",
                "-",
                0.0,
                0.0,
                0.0,
                "-"
        ));

        lastModeKey = null;
        lastScenarioKey = null;
        lastThrottlingState = null;
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
                processorState.getEffectiveClockGhz(),
                processorState.getTemperatureCelsius(),
                processorState.getThrottlingStateDisplayName()
        );
    }

    public List<LogEntry> getEntries() {
        return new ArrayList<>(entries);
    }

    public List<LogEntry> getEntriesFromIndex(int startIndex) {
        if (startIndex < 0 || startIndex > entries.size()) {
            throw new IllegalArgumentException("Startindex für Ereignisprotokoll ist ungültig.");
        }

        return new ArrayList<>(entries.subList(startIndex, entries.size()));
    }

    public List<LogEntry> getEntriesChronological() {
        List<LogEntry> chronologicalEntries = new ArrayList<>(entries);
        chronologicalEntries.sort(Comparator.comparing(LogEntry::getTimestamp));
        return chronologicalEntries;
    }

    public void clear() {
        entries.clear();
        lastModeKey = null;
        lastScenarioKey = null;
        lastThrottlingState = null;
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

        if (!processorState.hasTemperature()) {
            throw new IllegalArgumentException("Für ein Ereignis muss eine CPU-Temperatur vorhanden sein.");
        }

        if (!processorState.hasThrottlingState()) {
            throw new IllegalArgumentException("Für ein Ereignis muss eine Throttling-Stufe vorhanden sein.");
        }

        if (!processorState.hasEffectiveClock()) {
            throw new IllegalArgumentException("Für ein Ereignis muss eine effektive CPU-Taktfrequenz vorhanden sein.");
        }
    }
}
