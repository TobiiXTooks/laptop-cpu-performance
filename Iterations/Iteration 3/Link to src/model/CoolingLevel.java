package model;

public class CoolingLevel extends SimulationOption {

    private final double coolingFactor;

    public CoolingLevel(String key, String name, double coolingFactor) {
        super(key, name);
        this.coolingFactor = coolingFactor;
    }

    public double getCoolingFactor() {
        return coolingFactor;
    }
}