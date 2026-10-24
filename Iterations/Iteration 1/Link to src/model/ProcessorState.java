package model;

/**
 * Speichert den Prozessorzustand innerhalb eines Simulationslaufs.
 */
public class ProcessorState {

    private final double cpuLoadPercent;
    private Double clockGhz;

    public ProcessorState(double cpuLoadPercent) {
        this.cpuLoadPercent = cpuLoadPercent;
        this.clockGhz = null;
    }

    public double getCpuLoadPercent() {
        return cpuLoadPercent;
    }

    public Double getClockGhz() {
        return clockGhz;
    }

    public void updateClock(double clockGhz) {
        this.clockGhz = clockGhz;
    }

    public boolean hasClock() {
        return clockGhz != null;
    }
}
