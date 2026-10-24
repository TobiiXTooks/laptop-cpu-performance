package model;

public class LoadScenario extends SimulationOption {

    private final double cpuLoadPercent;

    public LoadScenario(String key, String name, double cpuLoadPercent) {
        super(key, name);
        this.cpuLoadPercent = cpuLoadPercent;
    }

    public double getCpuLoadPercent() {
        return cpuLoadPercent;
    }
}