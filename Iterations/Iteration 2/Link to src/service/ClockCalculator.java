package service;

import model.LoadScenario;
import model.PerformanceMode;

import java.util.HashMap;
import java.util.Map;

/**
 * Berechnet die ungedrosselte CPU-Taktfrequenz der Iteration 1.
 */
public class ClockCalculator {

    private static final double MIN_CLOCK_GHZ = 1.2;
    private static final double MAX_CLOCK_GHZ = 4.2;

    private final Map<String, Map<String, Double>> fixedClockTable;

    public ClockCalculator() {
        this.fixedClockTable = new HashMap<>();
        initializeFixedClockTable();
    }

    public double calculateClock(PerformanceMode mode, LoadScenario scenario) {
        if (mode == null) {
            throw new IllegalArgumentException("Leistungsmodus darf nicht null sein.");
        }

        if (scenario == null) {
            throw new IllegalArgumentException("Lastszenario darf nicht null sein.");
        }

        if (mode.isCustom()) {
            return calculateCustomModeClock(mode, scenario);
        }

        return calculateFixedModeClock(mode, scenario);
    }

    public double calculateFixedModeClock(PerformanceMode mode, LoadScenario scenario) {
        Map<String, Double> scenarioClockValues = fixedClockTable.get(mode.getKey());

        if (scenarioClockValues == null || !scenarioClockValues.containsKey(scenario.getKey())) {
            throw new IllegalArgumentException(
                    "Für die Kombination aus Leistungsmodus und Lastszenario ist keine Taktfrequenz definiert."
            );
        }

        return clampClock(scenarioClockValues.get(scenario.getKey()));
    }

    public double calculateCustomModeClock(PerformanceMode mode, LoadScenario scenario) {
        double rawClockGhz = mode.getMaxClockGhz() * (scenario.getCpuLoadPercent() / 100.0);
        double limitedToCustomMaximum = Math.min(rawClockGhz, mode.getMaxClockGhz());
        return clampClock(Math.max(limitedToCustomMaximum, MIN_CLOCK_GHZ));
    }

    public double clampClock(double clockGhz) {
        if (clockGhz < MIN_CLOCK_GHZ) {
            return MIN_CLOCK_GHZ;
        }

        if (clockGhz > MAX_CLOCK_GHZ) {
            return MAX_CLOCK_GHZ;
        }

        return clockGhz;
    }

    private void initializeFixedClockTable() {
        addClockValues("ENERGY_SAVE", 1.2, 1.4, 1.6, 1.8, 2.0);
        addClockValues("BALANCED", 1.4, 2.0, 2.5, 3.0, 3.2);
        addClockValues("PERFORMANCE", 2.0, 2.8, 3.4, 4.0, 4.2);
    }

    private void addClockValues(
            String modeKey,
            double idleClock,
            double officeClock,
            double multimediaClock,
            double gamingClock,
            double stressTestClock
    ) {
        Map<String, Double> values = new HashMap<>();
        values.put("IDLE", idleClock);
        values.put("OFFICE", officeClock);
        values.put("MULTIMEDIA", multimediaClock);
        values.put("GAMING", gamingClock);
        values.put("STRESS_TEST", stressTestClock);
        fixedClockTable.put(modeKey, values);
    }
}
