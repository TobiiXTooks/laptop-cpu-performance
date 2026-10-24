package service;

import model.CoolingLevel;
import model.ProcessorState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Verwaltet Kühlungsstufen und berechnet die simulierte CPU-Temperatur.
 */
public class TemperatureSimulator {

    private static final double MIN_TEMPERATURE_CELSIUS = 35.0;
    private static final double MAX_TEMPERATURE_CELSIUS = 100.0;

    private final Map<String, CoolingLevel> coolingLevels;

    public TemperatureSimulator() {
        this.coolingLevels = new LinkedHashMap<>();

        addCoolingLevel(new CoolingLevel("NIEDRIG", "Niedrig", 0.85));
        addCoolingLevel(new CoolingLevel("MITTEL", "Mittel", 1.00));
        addCoolingLevel(new CoolingLevel("HOCH", "Hoch", 1.15));
    }

    public List<CoolingLevel> listCoolingLevels() {
        return new ArrayList<>(coolingLevels.values());
    }

    public boolean isValidCoolingLevel(String coolingLevelKey) {
        if (coolingLevelKey == null || coolingLevelKey.isBlank()) {
            return false;
        }

        return coolingLevels.containsKey(normalizeKey(coolingLevelKey));
    }

    public CoolingLevel getCoolingLevel(String coolingLevelKey) {
        if (coolingLevelKey == null || coolingLevelKey.isBlank()) {
            throw new IllegalArgumentException("Es muss eine Kühlungsstufe angegeben werden.");
        }

        CoolingLevel coolingLevel = coolingLevels.get(normalizeKey(coolingLevelKey));
        if (coolingLevel == null) {
            throw new IllegalArgumentException("Die Kühlungsstufe ist unbekannt: " + coolingLevelKey);
        }

        return coolingLevel;
    }

    public double calculateTemperature(ProcessorState processorState, CoolingLevel coolingLevel) {
        if (processorState == null) {
            throw new IllegalArgumentException("Prozessorzustand darf nicht null sein.");
        }

        if (coolingLevel == null) {
            throw new IllegalArgumentException("Kühlungsstufe darf nicht null sein.");
        }

        if (!processorState.hasClock()) {
            throw new IllegalArgumentException("Für die Temperaturberechnung muss eine CPU-Taktfrequenz vorhanden sein.");
        }

        double rawTemperature =
                MIN_TEMPERATURE_CELSIUS
                        + ((processorState.getCpuLoadPercent() * 0.35)
                        + (processorState.getClockGhz() * 8.0))
                        / coolingLevel.getCoolingFactor();

        return clampTemperature(rawTemperature);
    }

    public double clampTemperature(double temperatureCelsius) {
        if (temperatureCelsius < MIN_TEMPERATURE_CELSIUS) {
            return MIN_TEMPERATURE_CELSIUS;
        }

        if (temperatureCelsius > MAX_TEMPERATURE_CELSIUS) {
            return MAX_TEMPERATURE_CELSIUS;
        }

        return temperatureCelsius;
    }

    private void addCoolingLevel(CoolingLevel coolingLevel) {
        coolingLevels.put(coolingLevel.getKey(), coolingLevel);
    }

    private String normalizeKey(String key) {
        return key.trim().toUpperCase();
    }
}