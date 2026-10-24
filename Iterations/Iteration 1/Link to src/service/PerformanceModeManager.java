package service;

import model.PerformanceMode;
import model.SimulationInput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Verwaltet und validiert die Leistungsmodi der Iteration 1.
 */
public class PerformanceModeManager {

    private static final double MIN_CUSTOM_CLOCK_GHZ = 1.2;
    private static final double MAX_CUSTOM_CLOCK_GHZ = 4.2;
    private static final double MIN_CUSTOM_POWER_WATT = 8.0;
    private static final double MAX_CUSTOM_POWER_WATT = 65.0;

    private final Map<String, PerformanceMode> modes;

    public PerformanceModeManager() {
        this.modes = new LinkedHashMap<>();

        addMode(new PerformanceMode("ENERGY_SAVE", "Energiesparen", false, 2.0, 20.0));
        addMode(new PerformanceMode("BALANCED", "Ausgeglichen", false, 3.2, 40.0));
        addMode(new PerformanceMode("PERFORMANCE", "Höchstleistung", false, 4.2, 65.0));
    }

    public List<PerformanceMode> listModes() {
        return new ArrayList<>(modes.values());
    }

    public boolean isValidMode(String modeKey) {
        if (modeKey == null || modeKey.isBlank()) {
            return false;
        }

        String normalizedKey = normalizeKey(modeKey);
        return modes.containsKey(normalizedKey) || "CUSTOM".equals(normalizedKey);
    }

    public PerformanceMode getMode(SimulationInput inputData) {
        if (inputData == null || !inputData.hasRequiredValues()) {
            throw new IllegalArgumentException("Es muss ein Leistungsmodus angegeben werden.");
        }

        String normalizedKey = normalizeKey(inputData.getModeKey());

        if ("CUSTOM".equals(normalizedKey)) {
            if (!validateCustomValues(inputData)) {
                throw new IllegalArgumentException(
                        "Die Werte des benutzerdefinierten Leistungsmodus sind ungültig. "
                                + "Erlaubt sind 1,2 bis 4,2 GHz und 8 bis 65 W."
                );
            }

            return new PerformanceMode(
                    "CUSTOM",
                    "Benutzerdefiniert",
                    true,
                    inputData.getCustomMaxClockGhz(),
                    inputData.getCustomPowerLimitWatt()
            );
        }

        PerformanceMode mode = modes.get(normalizedKey);
        if (mode == null) {
            throw new IllegalArgumentException("Der Leistungsmodus ist unbekannt: " + inputData.getModeKey());
        }

        return mode;
    }

    public boolean validateCustomValues(SimulationInput inputData) {
        if (inputData == null || !inputData.isCustomMode()) {
            return false;
        }

        Double maxClockGhz = inputData.getCustomMaxClockGhz();
        Double powerLimitWatt = inputData.getCustomPowerLimitWatt();

        if (maxClockGhz == null || powerLimitWatt == null) {
            return false;
        }

        return maxClockGhz >= MIN_CUSTOM_CLOCK_GHZ
                && maxClockGhz <= MAX_CUSTOM_CLOCK_GHZ
                && powerLimitWatt >= MIN_CUSTOM_POWER_WATT
                && powerLimitWatt <= MAX_CUSTOM_POWER_WATT;
    }

    private void addMode(PerformanceMode mode) {
        modes.put(mode.getKey(), mode);
    }

    private String normalizeKey(String key) {
        return key.trim().toUpperCase();
    }
}
