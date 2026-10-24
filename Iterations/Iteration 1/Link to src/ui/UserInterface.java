package ui;

import controller.PerformanceController;
import model.LoadScenario;
import model.PerformanceMode;
import model.SimulationInput;
import model.SimulationResult;
import service.LoadScenarioManager;
import service.PerformanceModeManager;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {

    private final PerformanceController performanceController;
    private final PerformanceModeManager performanceModeManager;
    private final LoadScenarioManager loadScenarioManager;
    private final Scanner scanner;

    public UserInterface(
            PerformanceController performanceController,
            PerformanceModeManager performanceModeManager,
            LoadScenarioManager loadScenarioManager
    ) {
        this.performanceController = performanceController;
        this.performanceModeManager = performanceModeManager;
        this.loadScenarioManager = loadScenarioManager;
        this.scanner = new Scanner(System.in);
        this.scanner.useLocale(Locale.GERMANY);
    }

    public void start() {
        System.out.println("Laptop-Prozessorleistungsmodul – Iteration 1");
        System.out.println("------------------------------------------------");

        boolean repeat = true;

        while (repeat) {
            try {
                SimulationInput input = readInput();
                SimulationResult result = performanceController.runSimulation(input);
                showResult(result);
            } catch (IllegalArgumentException exception) {
                showError(exception.getMessage());
            }

            repeat = askForRepeat();
        }

        System.out.println("Anwendung beendet.");
    }

    public SimulationInput readInput() {
        showAvailableModes();
        System.out.print("Leistungsmodus eingeben: ");
        String modeKey = scanner.nextLine().trim();

        Double customMaxClockGhz = null;
        Double customPowerLimitWatt = null;

        if ("CUSTOM".equalsIgnoreCase(modeKey)) {
            customMaxClockGhz = readDouble("Maximale CPU-Taktfrequenz eingeben (1,2 bis 4,2 GHz): ");
            customPowerLimitWatt = readDouble("Stromverbrauchsgrenzwert eingeben (8 bis 65 W): ");
        }

        showAvailableScenarios();
        System.out.print("Lastszenario eingeben: ");
        String scenarioKey = scanner.nextLine().trim();

        return new SimulationInput(modeKey, scenarioKey, customMaxClockGhz, customPowerLimitWatt);
    }

    public void showResult(SimulationResult result) {
        System.out.println();
        System.out.println("Simulationsergebnis");
        System.out.println("------------------------------------------------");
        System.out.println(result.toDisplayText());
        System.out.println();
    }

    public void showError(String message) {
        System.out.println();
        System.out.println("Fehler: " + message);
        System.out.println("Die Simulation wurde nicht ausgeführt.");
        System.out.println();
    }

    private void showAvailableModes() {
        List<PerformanceMode> modes = performanceModeManager.listModes();

        System.out.println();
        System.out.println("Verfügbare Leistungsmodi:");
        for (PerformanceMode mode : modes) {
            System.out.printf(
                    Locale.GERMANY,
                    "- %s (%s, max. %.1f GHz, %.0f W)%n",
                    mode.getKey(),
                    mode.getDisplayName(),
                    mode.getMaxClockGhz(),
                    mode.getPowerLimitWatt()
            );
        }

        System.out.println("- CUSTOM (Benutzerdefiniert)");
    }

    private void showAvailableScenarios() {
        List<LoadScenario> scenarios = loadScenarioManager.listScenarios();

        System.out.println();
        System.out.println("Verfügbare Lastszenarien:");
        for (LoadScenario scenario : scenarios) {
            System.out.printf(
                    Locale.GERMANY,
                    "- %s (%s, %.0f %% CPU-Auslastung)%n",
                    scenario.getKey(),
                    scenario.getDisplayName(),
                    scenario.getCpuLoadPercent()
            );
        }
    }

    private Double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().replace(",", ".");

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException exception) {
                System.out.println("Ungültige Zahl. Bitte erneut eingeben.");
            }
        }
    }

    private boolean askForRepeat() {
        System.out.print("Weitere Simulation ausführen? (j/n): ");
        String answer = scanner.nextLine().trim();
        return answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja");
    }
}
