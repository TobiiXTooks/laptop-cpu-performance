package model;

/**
 * Bündelt die Benutzereingaben für einen Simulationslauf.
 */
public class SimulationInput {

    private final String modeKey;
    private final String scenarioKey;
    private final Double customMaxClockGhz;
    private final Double customPowerLimitWatt;

    public SimulationInput(String modeKey, String scenarioKey) {
        this(modeKey, scenarioKey, null, null);
    }

    public SimulationInput(
            String modeKey,
            String scenarioKey,
            Double customMaxClockGhz,
            Double customPowerLimitWatt
    ) {
        this.modeKey = modeKey;
        this.scenarioKey = scenarioKey;
        this.customMaxClockGhz = customMaxClockGhz;
        this.customPowerLimitWatt = customPowerLimitWatt;
    }

    public String getModeKey() {
        return modeKey;
    }

    public String getScenarioKey() {
        return scenarioKey;
    }

    public Double getCustomMaxClockGhz() {
        return customMaxClockGhz;
    }

    public Double getCustomPowerLimitWatt() {
        return customPowerLimitWatt;
    }

    public boolean isCustomMode() {
        return "CUSTOM".equalsIgnoreCase(modeKey);
    }

    public boolean hasRequiredValues() {
        return modeKey != null
                && !modeKey.isBlank()
                && scenarioKey != null
                && !scenarioKey.isBlank();
    }
}
