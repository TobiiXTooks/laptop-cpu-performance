package model;

/**
 * Beschreibt eine auswählbare Kühlungsstufe für die Temperaturberechnung.
 */
public class CoolingLevel {

    private final String key;
    private final String name;
    private final double coolingFactor;

    public CoolingLevel(String key, String name, double coolingFactor) {
        this.key = key;
        this.name = name;
        this.coolingFactor = coolingFactor;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public double getCoolingFactor() {
        return coolingFactor;
    }

    public String getDisplayName() {
        return name;
    }
}