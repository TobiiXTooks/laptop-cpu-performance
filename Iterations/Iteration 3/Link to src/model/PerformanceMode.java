package model;

public class PerformanceMode extends SimulationOption {

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
        super(key, name);
        this.custom = custom;
        this.maxClockGhz = maxClockGhz;
        this.powerLimitWatt = powerLimitWatt;
    }

    public boolean isCustom() {
        return custom;
    }

    public double getMaxClockGhz() {
        return maxClockGhz;
    }

    public double getPowerLimitWatt() {
        return powerLimitWatt;
    }
}