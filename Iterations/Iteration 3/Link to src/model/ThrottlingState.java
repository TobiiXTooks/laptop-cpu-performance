package model;

/**
 * Beschreibt die definierten Thermal-Throttling-Stufen der Iteration 3.
 */
public enum ThrottlingState {
    NORMAL("NORMAL", "Normal", 35.0, 69.0, 1.00),
    WARNUNG("WARNUNG", "Warnung", 70.0, 79.0, 1.00),
    REDUZIERT("REDUZIERT", "Reduziert", 80.0, 89.0, 0.80),
    KRITISCH("KRITISCH", "Kritisch", 90.0, 100.0, 0.60);

    private final String key;
    private final String displayName;
    private final double minTemperatureCelsius;
    private final double maxTemperatureCelsius;
    private final double clockFactor;

    ThrottlingState(
            String key,
            String displayName,
            double minTemperatureCelsius,
            double maxTemperatureCelsius,
            double clockFactor
    ) {
        this.key = key;
        this.displayName = displayName;
        this.minTemperatureCelsius = minTemperatureCelsius;
        this.maxTemperatureCelsius = maxTemperatureCelsius;
        this.clockFactor = clockFactor;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getMinTemperatureCelsius() {
        return minTemperatureCelsius;
    }

    public double getMaxTemperatureCelsius() {
        return maxTemperatureCelsius;
    }

    public double getClockFactor() {
        return clockFactor;
    }

    public boolean isActiveThrottling() {
        return this == REDUZIERT || this == KRITISCH;
    }
}
