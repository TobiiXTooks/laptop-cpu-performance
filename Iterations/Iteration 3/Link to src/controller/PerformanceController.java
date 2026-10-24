package controller;

import model.CoolingLevel;
import model.LoadScenario;
import model.LogEntry;
import model.PerformanceMode;
import model.ProcessorState;
import model.SimulationInput;
import model.SimulationResult;
import service.ClockCalculator;
import service.EventLogger;
import service.LoadScenarioManager;
import service.PerformanceModeManager;
import service.PowerConsumptionCalculator;
import service.TemperatureSimulator;
import service.ThermalThrottlingController;

import java.util.List;
import java.util.Locale;

/**
 * Koordiniert den vollständigen Simulationsablauf der Iteration 3.
 */
public class PerformanceController {

    private final PerformanceModeManager performanceModeManager;
    private final LoadScenarioManager loadScenarioManager;
    private final ClockCalculator clockCalculator;
    private final TemperatureSimulator temperatureSimulator;
    private final ThermalThrottlingController thermalThrottlingController;
    private final PowerConsumptionCalculator powerConsumptionCalculator;
    private final EventLogger eventLogger;

    public PerformanceController(
            PerformanceModeManager performanceModeManager,
            LoadScenarioManager loadScenarioManager,
            ClockCalculator clockCalculator,
            TemperatureSimulator temperatureSimulator,
            ThermalThrottlingController thermalThrottlingController,
            PowerConsumptionCalculator powerConsumptionCalculator,
            EventLogger eventLogger
    ) {
        this.performanceModeManager = performanceModeManager;
        this.loadScenarioManager = loadScenarioManager;
        this.clockCalculator = clockCalculator;
        this.temperatureSimulator = temperatureSimulator;
        this.thermalThrottlingController = thermalThrottlingController;
        this.powerConsumptionCalculator = powerConsumptionCalculator;
        this.eventLogger = eventLogger;
    }

    public SimulationResult runSimulation(SimulationInput inputData) {
        if (inputData == null || !inputData.hasRequiredValues()) {
            throw new IllegalArgumentException("Es müssen Leistungsmodus, Lastszenario und Kühlungsstufe angegeben werden.");
        }

        PerformanceMode performanceMode = performanceModeManager.getMode(inputData);
        LoadScenario loadScenario = loadScenarioManager.getScenario(inputData.getScenarioKey());
        CoolingLevel coolingLevel = temperatureSimulator.getCoolingLevel(inputData.getCoolingLevelKey());

        ProcessorState processorState = createInitialState(loadScenario);

        double clockGhz = clockCalculator.calculateClock(performanceMode, loadScenario);
        processorState.updateClock(clockGhz);

        double temperatureCelsius = temperatureSimulator.calculateTemperature(processorState, coolingLevel);
        processorState.updateTemperature(temperatureCelsius);

        thermalThrottlingController.applyThrottling(processorState);

        double powerConsumptionWatt = powerConsumptionCalculator.calculatePowerConsumption(performanceMode, processorState);
        processorState.updatePowerConsumption(powerConsumptionWatt);

        int logStartIndex = eventLogger.getEntries().size();

        eventLogger.logModeChangeIfNeeded(performanceMode, loadScenario, processorState);
        eventLogger.logScenarioChangeIfNeeded(performanceMode, loadScenario, processorState);
        eventLogger.logTemperatureWarningIfNeeded(performanceMode, loadScenario, processorState);
        eventLogger.logThrottlingActivatedIfNeeded(performanceMode, loadScenario, processorState);
        eventLogger.logThrottlingChangedIfNeeded(performanceMode, loadScenario, processorState);

        List<LogEntry> logEntries = eventLogger.getEntriesFromIndex(logStartIndex);
        return createResult(
                performanceMode,
                loadScenario,
                coolingLevel,
                processorState,
                logEntries
        );
    }

    public ProcessorState createInitialState(LoadScenario loadScenario) {
        if (loadScenario == null) {
            throw new IllegalArgumentException("Lastszenario darf nicht null sein.");
        }

        return new ProcessorState(loadScenario.getCpuLoadPercent());
    }

    public SimulationResult createResult(
            PerformanceMode performanceMode,
            LoadScenario loadScenario,
            CoolingLevel coolingLevel,
            ProcessorState processorState,
            List<LogEntry> logEntries
    ) {
        String summary = String.format(
                Locale.GERMANY,
                "Modus %s mit Szenario %s und Kühlung %s ergibt %.0f %% CPU-Auslastung, %.1f GHz ungedrosselten CPU-Takt, %.1f GHz effektiven CPU-Takt, %.0f °C, Throttling-Stufe %s und %.0f W.",
                performanceMode.getDisplayName(),
                loadScenario.getDisplayName(),
                coolingLevel.getDisplayName(),
                processorState.getCpuLoadPercent(),
                processorState.getClockGhz(),
                processorState.getEffectiveClockGhz(),
                processorState.getTemperatureCelsius(),
                processorState.getThrottlingStateDisplayName(),
                processorState.getPowerConsumptionWatt()
        );

        return new SimulationResult(
                performanceMode,
                loadScenario,
                coolingLevel,
                processorState,
                logEntries,
                summary
        );
    }

    public void resetSimulation() {
        eventLogger.clear();
        eventLogger.logSimulationReset();
    }
}
