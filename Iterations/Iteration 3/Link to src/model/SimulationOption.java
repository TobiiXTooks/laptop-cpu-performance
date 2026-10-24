package model;

public abstract class SimulationOption {

    private final String key;
    private final String name;

    protected SimulationOption(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return name;
    }
}