import controller.PerformanceController;
import model.LoadScenario;
import model.PerformanceMode;
import model.SimulationInput;
import model.SimulationResult;
import service.ClockCalculator;
import service.LoadScenarioManager;
import service.PerformanceModeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Ausführbare Unit-Tests für Iteration 1.
 **/
public class Iteration1UnitTestRunner {

    private static int passed = 0;
    private static int failed = 0;
    private static final List<String> details = new ArrayList<>();

    public static void main(String[] args) {
        PerformanceModeManager performanceModeManager = new PerformanceModeManager();
        LoadScenarioManager loadScenarioManager = new LoadScenarioManager();
        ClockCalculator clockCalculator = new ClockCalculator();

        PerformanceController performanceController = new PerformanceController(
                performanceModeManager,
                loadScenarioManager,
                clockCalculator
        );

        testPerformanceModesAreAvailable(performanceModeManager);
        testSelectedFixedPerformanceModeIsAccepted(performanceModeManager);
        testValidCustomPerformanceModeIsAccepted(performanceModeManager);
        testCustomModeWithTooHighClockIsRejected(performanceModeManager);
        testCustomModeWithTooLowPowerLimitIsRejected(performanceModeManager);

        testLoadScenariosAreAvailable(loadScenarioManager);
        testCpuLoadIsAssignedToScenario(loadScenarioManager);
        testSelectedLoadScenarioIsAccepted(loadScenarioManager);

        testCpuLoadIsShownInResult(performanceController);
        testClockFrequencyIsCalculated(performanceModeManager, loadScenarioManager, clockCalculator);
        testClockFrequencyIsClampedToMinimum(clockCalculator);
        testClockFrequencyIsClampedToMaximum(clockCalculator);
        testClockFrequencyUnitIsShown(performanceController);
        testClockFrequencyIsRecalculated(performanceController);
        testSameInputCreatesSameOutput(performanceController);

        testServicesAreSeparatelyTestable();
        testInvalidPerformanceModeIsRejected(performanceController);
        testInvalidLoadScenarioIsRejected(performanceController);
        testEmptyPerformanceModeIsRejected(performanceController);

        printResult();
    }

    private static void testPerformanceModesAreAvailable(PerformanceModeManager manager) {
        run("TST-I1-001", () -> {
            List<PerformanceMode> modes = manager.listModes();

            assertTrue(
                    containsMode(modes, "ENERGY_SAVE")
                            && containsMode(modes, "BALANCED")
                            && containsMode(modes, "PERFORMANCE"),
                    "Modiliste enthält nicht alle erwarteten festen Leistungsmodi."
            );
        });
    }

    private static void testSelectedFixedPerformanceModeIsAccepted(PerformanceModeManager manager) {
        run("TST-I1-002", () -> {
            PerformanceMode mode = manager.getMode(new SimulationInput("BALANCED", "OFFICE"));

            assertEquals("BALANCED", mode.getKey(), "Falscher Modus-Key.");
            assertEquals("Ausgeglichen", mode.getDisplayName(), "Falscher Anzeigename.");
            assertDoubleEquals(3.2, mode.getMaxClockGhz(), "Falscher maximaler Takt.");
        });
    }

    private static void testValidCustomPerformanceModeIsAccepted(PerformanceModeManager manager) {
        run("TST-I1-003", () -> {
            PerformanceMode mode = manager.getMode(new SimulationInput("CUSTOM", "OFFICE", 3.5, 45.0));

            assertTrue(mode.isCustom(), "Modus wurde nicht als benutzerdefiniert markiert.");
            assertDoubleEquals(3.5, mode.getMaxClockGhz(), "Benutzerdefinierter Takt wurde nicht übernommen.");
            assertDoubleEquals(45.0, mode.getPowerLimitWatt(), "Benutzerdefiniertes Power-Limit wurde nicht übernommen.");
        });
    }

    private static void testCustomModeWithTooHighClockIsRejected(PerformanceModeManager manager) {
        runExpectingIllegalArgumentException(
                "TST-I1-004",
                () -> manager.getMode(new SimulationInput("CUSTOM", "OFFICE", 4.5, 45.0))
        );
    }

    private static void testCustomModeWithTooLowPowerLimitIsRejected(PerformanceModeManager manager) {
        runExpectingIllegalArgumentException(
                "TST-I1-005",
                () -> manager.getMode(new SimulationInput("CUSTOM", "OFFICE", 3.5, 7.0))
        );
    }

    private static void testLoadScenariosAreAvailable(LoadScenarioManager manager) {
        run("TST-I1-006", () -> {
            List<LoadScenario> scenarios = manager.listScenarios();

            assertTrue(
                    containsScenario(scenarios, "IDLE")
                            && containsScenario(scenarios, "OFFICE")
                            && containsScenario(scenarios, "MULTIMEDIA")
                            && containsScenario(scenarios, "GAMING")
                            && containsScenario(scenarios, "STRESS_TEST"),
                    "Szenarioliste enthält nicht alle erwarteten Lastszenarien."
            );
        });
    }

    private static void testCpuLoadIsAssignedToScenario(LoadScenarioManager manager) {
        run("TST-I1-007", () -> {
            LoadScenario scenario = manager.getScenario("OFFICE");
            assertDoubleEquals(35.0, scenario.getCpuLoadPercent(), "OFFICE liefert falsche CPU-Auslastung.");
        });
    }

    private static void testSelectedLoadScenarioIsAccepted(LoadScenarioManager manager) {
        run("TST-I1-008", () -> {
            LoadScenario scenario = manager.getScenario("GAMING");

            assertEquals("GAMING", scenario.getKey(), "Falscher Szenario-Key.");
            assertEquals("Gaming", scenario.getDisplayName(), "Falscher Anzeigename.");
            assertDoubleEquals(85.0, scenario.getCpuLoadPercent(), "Falsche CPU-Auslastung.");
        });
    }

    private static void testCpuLoadIsShownInResult(PerformanceController controller) {
        run("TST-I1-009", () -> {
            SimulationResult result = controller.runSimulation(new SimulationInput("BALANCED", "OFFICE"));
            assertTrue(
                    result.toDisplayText().contains("CPU-Auslastung: 35 %"),
                    "Ergebnistext enthält CPU-Auslastung nicht korrekt."
            );
        });
    }

    private static void testClockFrequencyIsCalculated(
            PerformanceModeManager modeManager,
            LoadScenarioManager scenarioManager,
            ClockCalculator calculator
    ) {
        run("TST-I1-010", () -> {
            PerformanceMode mode = modeManager.getMode(new SimulationInput("BALANCED", "OFFICE"));
            LoadScenario scenario = scenarioManager.getScenario("OFFICE");

            assertDoubleEquals(2.0, calculator.calculateClock(mode, scenario), "Falsche Taktfrequenz.");
        });
    }

    private static void testClockFrequencyIsClampedToMinimum(ClockCalculator calculator) {
        run("TST-I1-011", () ->
                assertDoubleEquals(1.2, calculator.clampClock(0.8), "Minimalbegrenzung funktioniert nicht.")
        );
    }

    private static void testClockFrequencyIsClampedToMaximum(ClockCalculator calculator) {
        run("TST-I1-012", () ->
                assertDoubleEquals(4.2, calculator.clampClock(5.0), "Maximalbegrenzung funktioniert nicht.")
        );
    }

    private static void testClockFrequencyUnitIsShown(PerformanceController controller) {
        run("TST-I1-013", () -> {
            SimulationResult result = controller.runSimulation(new SimulationInput("BALANCED", "OFFICE"));
            assertTrue(
                    result.toDisplayText().contains("Ungedrosselte CPU-Taktfrequenz:")
                            && result.toDisplayText().contains("GHz"),
                    "Ergebnistext enthält die Einheit GHz nicht."
            );
        });
    }

    private static void testClockFrequencyIsRecalculated(PerformanceController controller) {
        run("TST-I1-014", () -> {
            SimulationResult firstRun = controller.runSimulation(new SimulationInput("BALANCED", "OFFICE"));
            SimulationResult secondRun = controller.runSimulation(new SimulationInput("BALANCED", "GAMING"));

            assertDoubleEquals(2.0, firstRun.getProcessorState().getClockGhz(), "Erster Lauf liefert falschen Takt.");
            assertDoubleEquals(3.0, secondRun.getProcessorState().getClockGhz(), "Zweiter Lauf liefert falschen Takt.");
        });
    }

    private static void testSameInputCreatesSameOutput(PerformanceController controller) {
        run("TST-I1-015", () -> {
            SimulationResult firstRun = controller.runSimulation(new SimulationInput("PERFORMANCE", "STRESS_TEST"));
            SimulationResult secondRun = controller.runSimulation(new SimulationInput("PERFORMANCE", "STRESS_TEST"));

            assertDoubleEquals(4.2, firstRun.getProcessorState().getClockGhz(), "Erster Lauf liefert falschen Takt.");
            assertDoubleEquals(4.2, secondRun.getProcessorState().getClockGhz(), "Zweiter Lauf liefert falschen Takt.");
            assertDoubleEquals(100.0, firstRun.getProcessorState().getCpuLoadPercent(), "Erster Lauf liefert falsche Auslastung.");
            assertDoubleEquals(100.0, secondRun.getProcessorState().getCpuLoadPercent(), "Zweiter Lauf liefert falsche Auslastung.");
        });
    }

    private static void testServicesAreSeparatelyTestable() {
        run("TST-I1-016", () -> {
            new PerformanceModeManager();
            new LoadScenarioManager();
            new ClockCalculator();
        });
    }

    private static void testInvalidPerformanceModeIsRejected(PerformanceController controller) {
        runExpectingIllegalArgumentException(
                "TST-I1-017",
                () -> controller.runSimulation(new SimulationInput("UNKNOWN", "OFFICE"))
        );
    }

    private static void testInvalidLoadScenarioIsRejected(PerformanceController controller) {
        runExpectingIllegalArgumentException(
                "TST-I1-018",
                () -> controller.runSimulation(new SimulationInput("BALANCED", "UNKNOWN"))
        );
    }

    private static void testEmptyPerformanceModeIsRejected(PerformanceController controller) {
        runExpectingIllegalArgumentException(
                "TST-I1-019",
                () -> controller.runSimulation(new SimulationInput("", "OFFICE"))
        );
    }

    private static boolean containsMode(List<PerformanceMode> modes, String key) {
        return modes.stream().anyMatch(mode -> key.equals(mode.getKey()));
    }

    private static boolean containsScenario(List<LoadScenario> scenarios, String key) {
        return scenarios.stream().anyMatch(scenario -> key.equals(scenario.getKey()));
    }

    private static void run(String testId, TestAction action) {
        try {
            action.execute();
            markPassed(testId);
        } catch (AssertionError | RuntimeException exception) {
            markFailed(testId, exception.getMessage());
        }
    }

    private static void runExpectingIllegalArgumentException(String testId, TestAction action) {
        try {
            action.execute();
            markFailed(testId, "Es wurde keine IllegalArgumentException ausgelöst.");
        } catch (IllegalArgumentException exception) {
            markPassed(testId);
        } catch (RuntimeException exception) {
            markFailed(testId, "Falscher Exception-Typ: " + exception.getClass().getSimpleName());
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " Erwartet: " + expected + ", Ist: " + actual);
        }
    }

    private static void assertDoubleEquals(double expected, double actual, String message) {
        if (Math.abs(expected - actual) > 0.0001) {
            throw new AssertionError(message + " Erwartet: " + expected + ", Ist: " + actual);
        }
    }

    private static void markPassed(String testId) {
        passed++;
        details.add(testId + ";BESTANDEN;");
    }

    private static void markFailed(String testId, String message) {
        failed++;
        details.add(testId + ";FEHLGESCHLAGEN;" + sanitize(message));
    }

    private static String sanitize(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\n", " ").replace(";", ",");
    }

    private static void printResult() {
        System.out.println("passed=" + passed);
        System.out.println("failed=" + failed);

        for (String detail : details) {
            System.out.println(detail);
        }

        if (failed > 0) {
            System.exit(1);
        }
    }

    @FunctionalInterface
    private interface TestAction {
        void execute();
    }
}
