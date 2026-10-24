package model;

/**
 * Beschreibt ein Lastszenario mit zugeordneter CPU-Auslastung.
 */
public class LoadScenario {

    private final String key;
    private final String name;
    private final double cpuLoadPercent;

    public LoadScenario(String key, String name, double cpuLoadPercent) {
        this.key = key;
        this.name = name;
        this.cpuLoadPercent = cpuLoadPercent;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public double getCpuLoadPercent() {
        return cpuLoadPercent;
    }

    public String getDisplayName() {
        return name;
    }
}
