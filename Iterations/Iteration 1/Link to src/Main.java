import controller.PerformanceController;
import service.ClockCalculator;
import service.LoadScenarioManager;
import service.PerformanceModeManager;
import ui.UserInterface;

/**
 * Startpunkt der Anwendung für Iteration 1.
 */
public class Main {

    public static void main(String[] args) {
        PerformanceModeManager performanceModeManager = new PerformanceModeManager();
        LoadScenarioManager loadScenarioManager = new LoadScenarioManager();
        ClockCalculator clockCalculator = new ClockCalculator();

        PerformanceController performanceController = new PerformanceController(
                performanceModeManager,
                loadScenarioManager,
                clockCalculator
        );

        UserInterface userInterface = new UserInterface(
                performanceController,
                performanceModeManager,
                loadScenarioManager
        );

        userInterface.start();
    }
}
