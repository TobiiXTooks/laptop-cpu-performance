import controller.PerformanceController;
import service.ClockCalculator;
import service.EventLogger;
import service.LoadScenarioManager;
import service.PerformanceModeManager;
import service.PowerConsumptionCalculator;
import service.TemperatureSimulator;
import service.ThermalThrottlingController;
import ui.UserInterface;

/**
 * Startpunkt der Anwendung für Iteration 3.
 */
public class Main {

    public static void main(String[] args) {
        PerformanceModeManager performanceModeManager = new PerformanceModeManager();
        LoadScenarioManager loadScenarioManager = new LoadScenarioManager();
        ClockCalculator clockCalculator = new ClockCalculator();
        TemperatureSimulator temperatureSimulator = new TemperatureSimulator();
        ThermalThrottlingController thermalThrottlingController = new ThermalThrottlingController();
        PowerConsumptionCalculator powerConsumptionCalculator = new PowerConsumptionCalculator();
        EventLogger eventLogger = new EventLogger();

        PerformanceController performanceController = new PerformanceController(
                performanceModeManager,
                loadScenarioManager,
                clockCalculator,
                temperatureSimulator,
                thermalThrottlingController,
                powerConsumptionCalculator,
                eventLogger
        );

        UserInterface userInterface = new UserInterface(
                performanceController,
                performanceModeManager,
                loadScenarioManager,
                temperatureSimulator
        );

        userInterface.start();
    }
}
