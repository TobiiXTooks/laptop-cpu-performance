package model;

/**
 * Speichert den Prozessorzustand innerhalb eines Simulationslaufs.
 */
public class ProcessorState {

    private final double cpuLoadPercent;
    private Double clockGhz;
    private Double temperatureCelsius;
    private Double powerConsumptionWatt;

    public ProcessorState(double cpuLoadPercent) {
        this.cpuLoadPercent = cpuLoadPercent;
        this.clockGhz = null;
        this.temperatureCelsius = null;
        this.powerConsumptionWatt = null;
    }

    public double getCpuLoadPercent() {
        return cpuLoadPercent;
    }

    public Double getClockGhz() {
        return clockGhz;
    }

    public Double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public Double getPowerConsumptionWatt() {
        return powerConsumptionWatt;
    }

    public void updateClock(double clockGhz) {
        this.clockGhz = clockGhz;
    }

    public void updateTemperature(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public void updatePowerConsumption(double powerConsumptionWatt) {
        this.powerConsumptionWatt = powerConsumptionWatt;
    }

    public boolean hasClock() {
        return clockGhz != null;
    }

    public boolean hasTemperature() {
        return temperatureCelsius != null;
    }

    public boolean hasPowerConsumption() {
        return powerConsumptionWatt != null;
    }
}