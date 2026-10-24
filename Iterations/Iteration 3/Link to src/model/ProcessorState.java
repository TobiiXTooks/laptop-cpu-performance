package model;

/**
 * Speichert den Prozessorzustand innerhalb eines Simulationslaufs.
 */
public class ProcessorState {

    private final double cpuLoadPercent;
    private Double clockGhz;
    private Double temperatureCelsius;
    private ThrottlingState throttlingState;
    private Double effectiveClockGhz;
    private Double powerConsumptionWatt;

    public ProcessorState(double cpuLoadPercent) {
        this.cpuLoadPercent = cpuLoadPercent;
        this.clockGhz = null;
        this.temperatureCelsius = null;
        this.throttlingState = null;
        this.effectiveClockGhz = null;
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

    public ThrottlingState getThrottlingState() {
        return throttlingState;
    }

    public String getThrottlingStateDisplayName() {
        return throttlingState == null ? "nicht berechnet" : throttlingState.getDisplayName();
    }

    public Double getEffectiveClockGhz() {
        return effectiveClockGhz;
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

    public void updateThrottling(ThrottlingState throttlingState, double effectiveClockGhz) {
        if (throttlingState == null) {
            throw new IllegalArgumentException("Throttling-Stufe darf nicht null sein.");
        }

        this.throttlingState = throttlingState;
        this.effectiveClockGhz = effectiveClockGhz;
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

    public boolean hasThrottlingState() {
        return throttlingState != null;
    }

    public boolean hasEffectiveClock() {
        return effectiveClockGhz != null;
    }

    public boolean hasPowerConsumption() {
        return powerConsumptionWatt != null;
    }
}
