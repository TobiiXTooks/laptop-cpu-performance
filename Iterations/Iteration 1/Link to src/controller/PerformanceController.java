package controller;

import model.LoadScenario;
import model.PerformanceMode;
import model.ProcessorState;
import model.SimulationInput;
import model.SimulationResult;
import service.ClockCalculator;
import service.LoadScenarioManager;
import service.PerformanceModeManager;

import java.util.Locale;

/**
 * Koordiniert den vollständigen Simulationsablauf der Iteration 1.
 */
public class PerformanceController {

    private final PerformanceModeManager performanceModeManager;
    private final LoadScenarioManager loadScenarioManager;
    private final ClockCalculator clockCalculator;

    public PerformanceController(
            PerformanceModeManager performanceModeManager,
            LoadScenarioManager loadScenarioManager,
            ClockCalculator clockCalculator
    ) {
        this.performanceModeManager = performanceModeManager;
        this.loadScenarioManager = loadScenarioManager;
        this.clockCalculator = clockCalculator;
    }

    public SimulationResult runSimulation(SimulationInput inputData) {
        if (inputData == null || !inputData.hasRequiredValues()) {
            throw new IllegalArgumentException("Leistungsmodus und Lastszenario müssen angegeben werden.");
        }

        PerformanceMode performanceMode = performanceModeManager.getMode(inputData);
        LoadScenario loadScenario = loadScenarioManager.getScenario(inputData.getScenarioKey());

        ProcessorState state = createInitialState(loadScenario);
        double clockGhz = clockCalculator.calculateClock(performanceMode, loadScenario);
        state.updateClock(clockGhz);

        return createResult(performanceMode, loadScenario, state);
    }

    public ProcessorState createInitialState(LoadScenario scenario) {
        if (scenario == null) {
            throw new IllegalArgumentException("Lastszenario darf nicht null sein.");
        }

        return new ProcessorState(scenario.getCpuLoadPercent());
    }

    public SimulationResult createResult(PerformanceMode mode, LoadScenario scenario, ProcessorState processorState) {
        if (mode == null || scenario == null || processorState == null) {
            throw new IllegalArgumentException("Für das Simulationsergebnis fehlen erforderliche Werte.");
        }

        String summary = String.format(
                Locale.GERMANY,
                "Modus %s mit Szenario %s ergibt %.0f %% CPU-Auslastung und %.1f GHz CPU-Takt.",
                mode.getDisplayName(),
                scenario.getDisplayName(),
                processorState.getCpuLoadPercent(),
                processorState.getClockGhz()
        );

        return new SimulationResult(mode, scenario, processorState, summary);
    }
}
