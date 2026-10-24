import controller.PerformanceController;
import service.ClockCalculator;
import service.EventLogger;
import service.LoadScenarioManager;
import service.PerformanceModeManager;
import service.PowerConsumptionCalculator;
import service.TemperatureSimulator;
import ui.UserInterface;

/**
 * Startpunkt der Anwendung für Iteration 2.
 */
public class Main {

    public static void main(String[] args) {
        PerformanceModeManager performanceModeManager = new PerformanceModeManager();
        LoadScenarioManager loadScenarioManager = new LoadScenarioManager();
        ClockCalculator clockCalculator = new ClockCalculator();
        TemperatureSimulator temperatureSimulator = new TemperatureSimulator();
        PowerConsumptionCalculator powerConsumptionCalculator = new PowerConsumptionCalculator();
        EventLogger eventLogger = new EventLogger();

        PerformanceController performanceController = new PerformanceController(
                performanceModeManager,
                loadScenarioManager,
                clockCalculator,
                temperatureSimulator,
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