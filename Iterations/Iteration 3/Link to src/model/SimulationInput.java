package model;

/**
 * Bündelt die Benutzereingaben für einen Simulationslauf.
 */
public class SimulationInput {

    private static final String DEFAULT_COOLING_LEVEL_KEY = "MITTEL";

    private final String modeKey;
    private final String scenarioKey;
    private final String coolingLevelKey;
    private final Double customMaxClockGhz;
    private final Double customPowerLimitWatt;

    public SimulationInput(String modeKey, String scenarioKey) {
        this(modeKey, scenarioKey, DEFAULT_COOLING_LEVEL_KEY, null, null);
    }

    public SimulationInput(String modeKey, String scenarioKey, Double customMaxClockGhz, Double customPowerLimitWatt) {
        this(modeKey, scenarioKey, DEFAULT_COOLING_LEVEL_KEY, customMaxClockGhz, customPowerLimitWatt);
    }

    public SimulationInput(String modeKey, String scenarioKey, String coolingLevelKey) {
        this(modeKey, scenarioKey, coolingLevelKey, null, null);
    }

    public SimulationInput(
            String modeKey,
            String scenarioKey,
            String coolingLevelKey,
            Double customMaxClockGhz,
            Double customPowerLimitWatt
    ) {
        this.modeKey = modeKey;
        this.scenarioKey = scenarioKey;
        this.coolingLevelKey = coolingLevelKey;
        this.customMaxClockGhz = customMaxClockGhz;
        this.customPowerLimitWatt = customPowerLimitWatt;
    }

    public String getModeKey() { return modeKey; }
    public String getScenarioKey() { return scenarioKey; }
    public String getCoolingLevelKey() { return coolingLevelKey; }
    public Double getCustomMaxClockGhz() { return customMaxClockGhz; }
    public Double getCustomPowerLimitWatt() { return customPowerLimitWatt; }

    public boolean isCustomMode() {
        return "CUSTOM".equalsIgnoreCase(modeKey);
    }

    public boolean hasRequiredValues() {
        return modeKey != null
                && !modeKey.isBlank()
                && scenarioKey != null
                && !scenarioKey.isBlank()
                && coolingLevelKey != null
                && !coolingLevelKey.isBlank();
    }
}
