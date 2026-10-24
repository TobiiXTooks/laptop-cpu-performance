package service;

import model.PerformanceMode;
import model.ProcessorState;

/**
 * Berechnet den simulierten Stromverbrauch in Watt.
 */
public class PowerConsumptionCalculator {

    private static final double MIN_POWER_WATT = 8.0;
    private static final double MAX_POWER_WATT = 65.0;

    public double calculatePowerConsumption(PerformanceMode performanceMode, ProcessorState processorState) {
        if (performanceMode == null) {
            throw new IllegalArgumentException("Leistungsmodus darf nicht null sein.");
        }

        if (processorState == null) {
            throw new IllegalArgumentException("Prozessorzustand darf nicht null sein.");
        }

        if (!processorState.hasEffectiveClock()) {
            throw new IllegalArgumentException("Für die Stromverbrauchsberechnung muss eine effektive CPU-Taktfrequenz vorhanden sein.");
        }

        if (performanceMode.getMaxClockGhz() <= 0.0) {
            throw new IllegalArgumentException("Die maximale CPU-Taktfrequenz muss größer als 0 sein.");
        }

        double loadFactor = 0.30 + 0.70 * (processorState.getCpuLoadPercent() / 100.0);
        double clockFactor = processorState.getEffectiveClockGhz() / performanceMode.getMaxClockGhz();
        double rawPowerWatt = performanceMode.getPowerLimitWatt() * loadFactor * clockFactor;

        return clampPowerConsumption(rawPowerWatt);
    }

    public double clampPowerConsumption(double powerWatt) {
        if (powerWatt < MIN_POWER_WATT) {
            return MIN_POWER_WATT;
        }

        if (powerWatt > MAX_POWER_WATT) {
            return MAX_POWER_WATT;
        }

        return powerWatt;
    }
}
