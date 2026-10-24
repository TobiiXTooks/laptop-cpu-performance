package model;

import java.util.Locale;

/**
 * Bündelt die finalen Ergebniswerte eines Simulationslaufs.
 */
public class SimulationResult {

    private final PerformanceMode performanceMode;
    private final LoadScenario loadScenario;
    private final ProcessorState processorState;
    private final String summary;

    public SimulationResult(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            ProcessorState processorState,
            String summary
    ) {
        this.performanceMode = performanceMode;
        this.loadScenario = loadScenario;
        this.processorState = processorState;
        this.summary = summary;
    }

    public PerformanceMode getPerformanceMode() {
        return performanceMode;
    }

    public LoadScenario getLoadScenario() {
        return loadScenario;
    }

    public ProcessorState getProcessorState() {
        return processorState;
    }

    public String getSummary() {
        return summary;
    }

    public String toDisplayText() {
        String clockText = processorState.hasClock()
                ? String.format(Locale.GERMANY, "%.1f GHz", processorState.getClockGhz())
                : "nicht berechnet";

        return "Leistungsmodus: " + performanceMode.getDisplayName() + System.lineSeparator()
                + "Lastszenario: " + loadScenario.getDisplayName() + System.lineSeparator()
                + String.format(Locale.GERMANY, "CPU-Auslastung: %.0f %%", processorState.getCpuLoadPercent()) + System.lineSeparator()
                + "Ungedrosselte CPU-Taktfrequenz: " + clockText + System.lineSeparator()
                + "Zusammenfassung: " + summary;
    }
}
