package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Bündelt die finalen Ergebniswerte eines Simulationslaufs.
 */
public class SimulationResult implements Displayable {

    private final PerformanceMode performanceMode;
    private final LoadScenario loadScenario;
    private final CoolingLevel coolingLevel;
    private final ProcessorState processorState;
    private final List<LogEntry> logEntries;
    private final String summary;

    public SimulationResult(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            CoolingLevel coolingLevel,
            ProcessorState processorState,
            List<LogEntry> logEntries,
            String summary
    ) {
        this.performanceMode = performanceMode;
        this.loadScenario = loadScenario;
        this.coolingLevel = coolingLevel;
        this.processorState = processorState;
        this.logEntries = new ArrayList<>(logEntries);
        this.summary = summary;
    }

    public PerformanceMode getPerformanceMode() { return performanceMode; }
    public LoadScenario getLoadScenario() { return loadScenario; }
    public CoolingLevel getCoolingLevel() { return coolingLevel; }
    public ProcessorState getProcessorState() { return processorState; }
    public List<LogEntry> getLogEntries() { return new ArrayList<>(logEntries); }
    public String getSummary() { return summary; }

    @Override
    public String toDisplayText() {
        String clockText = processorState.hasClock()
                ? String.format(Locale.GERMANY, "%.1f GHz", processorState.getClockGhz())
                : "nicht berechnet";
        String effectiveClockText = processorState.hasEffectiveClock()
                ? String.format(Locale.GERMANY, "%.1f GHz", processorState.getEffectiveClockGhz())
                : "nicht berechnet";
        String temperatureText = processorState.hasTemperature()
                ? String.format(Locale.GERMANY, "%.0f °C", processorState.getTemperatureCelsius())
                : "nicht berechnet";
        String throttlingText = processorState.hasThrottlingState()
                ? processorState.getThrottlingStateDisplayName()
                : "nicht berechnet";
        String powerText = processorState.hasPowerConsumption()
                ? String.format(Locale.GERMANY, "%.0f W", processorState.getPowerConsumptionWatt())
                : "nicht berechnet";

        StringBuilder builder = new StringBuilder();
        builder.append("Leistungsmodus: ").append(performanceMode.getDisplayName()).append(System.lineSeparator());
        builder.append("Lastszenario: ").append(loadScenario.getDisplayName()).append(System.lineSeparator());
        builder.append("Kühlungsstufe: ").append(coolingLevel.getDisplayName()).append(System.lineSeparator());
        builder.append(String.format(Locale.GERMANY, "CPU-Auslastung: %.0f %%%n", processorState.getCpuLoadPercent()));
        builder.append("Ungedrosselte CPU-Taktfrequenz: ").append(clockText).append(System.lineSeparator());
        builder.append("Effektive CPU-Taktfrequenz: ").append(effectiveClockText).append(System.lineSeparator());
        builder.append("CPU-Temperatur: ").append(temperatureText).append(System.lineSeparator());
        builder.append("Throttling-Stufe: ").append(throttlingText).append(System.lineSeparator());
        builder.append("Stromverbrauch: ").append(powerText).append(System.lineSeparator());
        builder.append("Zusammenfassung: ").append(summary).append(System.lineSeparator());

        if (!logEntries.isEmpty()) {
            builder.append(System.lineSeparator());
            builder.append("Ereignisprotokoll:").append(System.lineSeparator());
            for (LogEntry entry : logEntries) {
                builder.append("- ").append(entry.toDisplayText()).append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
