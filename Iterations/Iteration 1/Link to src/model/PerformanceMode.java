package model;

/**
 * Beschreibt einen auswählbaren Leistungsmodus.
 */
public class PerformanceMode {

    private final String key;
    private final String name;
    private final boolean custom;
    private final double maxClockGhz;
    private final double powerLimitWatt;

    public PerformanceMode(
            String key,
            String name,
            boolean custom,
            double maxClockGhz,
            double powerLimitWatt
    ) {
        this.key = key;
        this.name = name;
        this.custom = custom;
        this.maxClockGhz = maxClockGhz;
        this.powerLimitWatt = powerLimitWatt;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public boolean isCustom() {
        return custom;
    }

    public double getPowerLimitWatt() {
        return powerLimitWatt;
    }

    public double getMaxClockGhz() {
        return maxClockGhz;
    }

    public String getDisplayName() {
        return name;
    }
}
