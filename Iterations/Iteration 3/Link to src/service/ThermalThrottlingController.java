package service;

import model.ProcessorState;
import model.ThrottlingState;

/**
 * Bestimmt Thermal-Throttling-Stufen und effektive CPU-Taktfrequenzen.
 */
public class ThermalThrottlingController {

    public ThrottlingState determineThrottlingState(double temperatureCelsius) {
        if (temperatureCelsius < 35.0 || temperatureCelsius > 100.0) {
            throw new IllegalArgumentException("CPU-Temperatur muss im Bereich von 35 °C bis 100 °C liegen.");
        }

        if (temperatureCelsius <= ThrottlingState.NORMAL.getMaxTemperatureCelsius()) {
            return ThrottlingState.NORMAL;
        }

        if (temperatureCelsius <= ThrottlingState.WARNUNG.getMaxTemperatureCelsius()) {
            return ThrottlingState.WARNUNG;
        }

        if (temperatureCelsius <= ThrottlingState.REDUZIERT.getMaxTemperatureCelsius()) {
            return ThrottlingState.REDUZIERT;
        }

        return ThrottlingState.KRITISCH;
    }

    public double calculateEffectiveClock(double unthrottledClockGhz, ThrottlingState throttlingState) {
        if (unthrottledClockGhz <= 0.0) {
            throw new IllegalArgumentException("Ungedrosselte CPU-Taktfrequenz muss größer als 0 sein.");
        }

        if (throttlingState == null) {
            throw new IllegalArgumentException("Throttling-Stufe darf nicht null sein.");
        }

        return unthrottledClockGhz * throttlingState.getClockFactor();
    }

    public void applyThrottling(ProcessorState processorState) {
        if (processorState == null) {
            throw new IllegalArgumentException("Prozessorzustand darf nicht null sein.");
        }

        if (!processorState.hasClock()) {
            throw new IllegalArgumentException("Für Thermal Throttling muss eine ungedrosselte CPU-Taktfrequenz vorhanden sein.");
        }

        if (!processorState.hasTemperature()) {
            throw new IllegalArgumentException("Für Thermal Throttling muss eine CPU-Temperatur vorhanden sein.");
        }

        ThrottlingState state = determineThrottlingState(processorState.getTemperatureCelsius());
        double effectiveClockGhz = calculateEffectiveClock(processorState.getClockGhz(), state);
        processorState.updateThrottling(state, effectiveClockGhz);
    }

    public boolean isThrottlingActive(ThrottlingState throttlingState) {
        return throttlingState != null && throttlingState.isActiveThrottling();
    }
}
