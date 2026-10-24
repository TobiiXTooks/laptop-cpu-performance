import controller.PerformanceController;
import model.CoolingLevel;
import model.LoadScenario;
import model.LogEntry;
import model.PerformanceMode;
import model.ProcessorState;
import model.SimulationInput;
import model.SimulationResult;
import service.ClockCalculator;
import service.EventLogger;
import service.LoadScenarioManager;
import service.PerformanceModeManager;
import service.PowerConsumptionCalculator;
import service.TemperatureSimulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Ausführbare Unit- und Integrationstests für Iteration 2.
 */
public class Iteration2UnitTestRunner {

    private static int passed = 0;
    private static int failed = 0;
    private static final List<String> details = new ArrayList<>();

    public static void main(String[] args) {
        testCoolingLevelsAreAvailable();
        testSelectedCoolingLevelIsAccepted();
        testInvalidCoolingLevelIsRejected();

        testTemperatureIsCalculated();
        testTemperatureIsClampedToMinimum();
        testTemperatureIsClampedToMaximum();
        testBetterCoolingReducesTemperature();
        testTemperatureUnitIsShownInResult();

        testPowerConsumptionIsCalculated();
        testPowerConsumptionIsClampedToMinimum();
        testPowerConsumptionIsClampedToMaximum();
        testPowerConsumptionUnitIsShownInResult();

        testModeChangeIsLogged();
        testScenarioChangeIsLogged();
        testTemperatureWarningIsLogged();
        testLogEntryContainsSimulationData();

        testIteration1InputConstructorStillWorks();
        testIteration1ClockCalculationStillWorks();
        testFullIteration2SimulationCreatesCompleteResult();

        testSimulationFinishesWithinFiveSeconds();
        testNumericValuesAreShownWithUnits();

        printResult();
    }

    private static PerformanceController createController(
            PerformanceModeManager performanceModeManager,
            LoadScenarioManager loadScenarioManager,
            ClockCalculator clockCalculator,
            TemperatureSimulator temperatureSimulator,
            PowerConsumptionCalculator powerConsumptionCalculator,
            EventLogger eventLogger
    ) {
        return new PerformanceController(
                performanceModeManager,
                loadScenarioManager,
                clockCalculator,
                temperatureSimulator,
                powerConsumptionCalculator,
                eventLogger
        );
    }

    private static PerformanceController createDefaultController() {
        return createController(
                new PerformanceModeManager(),
                new LoadScenarioManager(),
                new ClockCalculator(),
                new TemperatureSimulator(),
                new PowerConsumptionCalculator(),
                new EventLogger()
        );
    }

    private static void testCoolingLevelsAreAvailable() {
        run("TC-TMP-001", () -> {
            TemperatureSimulator simulator = new TemperatureSimulator();
            List<CoolingLevel> coolingLevels = simulator.listCoolingLevels();

            assertTrue(
                    containsCoolingLevel(coolingLevels, "NIEDRIG")
                            && containsCoolingLevel(coolingLevels, "MITTEL")
                            && containsCoolingLevel(coolingLevels, "HOCH"),
                    "Kühlungsstufen enthalten nicht NIEDRIG, MITTEL und HOCH."
            );
        });
    }

    private static void testSelectedCoolingLevelIsAccepted() {
        run("TC-TMP-002", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE", "HOCH")
            );

            assertEquals("HOCH", result.getCoolingLevel().getKey(), "Kühlungsstufe wurde nicht übernommen.");
        });
    }

    private static void testInvalidCoolingLevelIsRejected() {
        runExpectingIllegalArgumentException(
                () -> {
                    PerformanceController controller = createDefaultController();
                    controller.runSimulation(new SimulationInput("BALANCED", "OFFICE", "UNBEKANNT"));
                }
        );
    }

    private static void testTemperatureIsCalculated() {
        run("TC-TMP-003", () -> {
            TemperatureSimulator simulator = new TemperatureSimulator();
            ProcessorState state = new ProcessorState(85.0);
            state.updateClock(4.0);

            CoolingLevel coolingLevel = simulator.getCoolingLevel("MITTEL");
            double temperature = simulator.calculateTemperature(state, coolingLevel);

            assertTrue(
                    temperature >= 35.0 && temperature <= 100.0,
                    "Temperatur liegt nicht im gültigen Bereich von 35 °C bis 100 °C."
            );
        });
    }

    private static void testTemperatureIsClampedToMinimum() {
        run("TC-TMP-003A", () -> {
            TemperatureSimulator simulator = new TemperatureSimulator();

            assertDoubleEquals(
                    35.0,
                    simulator.clampTemperature(20.0),
                    "Temperatur wurde nicht auf den Minimalwert begrenzt."
            );
        });
    }

    private static void testTemperatureIsClampedToMaximum() {
        run("TC-TMP-003B", () -> {
            TemperatureSimulator simulator = new TemperatureSimulator();

            assertDoubleEquals(
                    100.0,
                    simulator.clampTemperature(140.0),
                    "Temperatur wurde nicht auf den Maximalwert begrenzt."
            );
        });
    }

    private static void testBetterCoolingReducesTemperature() {
        run("TC-TMP-003C", () -> {
            TemperatureSimulator simulator = new TemperatureSimulator();
            ProcessorState state = new ProcessorState(85.0);
            state.updateClock(4.0);

            double lowCoolingTemperature = simulator.calculateTemperature(
                    state,
                    simulator.getCoolingLevel("NIEDRIG")
            );

            double highCoolingTemperature = simulator.calculateTemperature(
                    state,
                    simulator.getCoolingLevel("HOCH")
            );

            assertTrue(
                    highCoolingTemperature < lowCoolingTemperature,
                    "Bessere Kühlung reduziert die Temperatur nicht."
            );
        });
    }

    private static void testTemperatureUnitIsShownInResult() {
        run("TC-TMP-004", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE", "MITTEL")
            );

            assertTrue(
                    result.toDisplayText().contains("°C"),
                    "Temperatur wird nicht mit Einheit °C ausgegeben."
            );
        });
    }

    private static void testPowerConsumptionIsCalculated() {
        run("TC-PWR-001", () -> {
            PerformanceModeManager modeManager = new PerformanceModeManager();
            LoadScenarioManager scenarioManager = new LoadScenarioManager();
            PowerConsumptionCalculator calculator = new PowerConsumptionCalculator();

            PerformanceMode mode = modeManager.getMode(new SimulationInput("BALANCED", "OFFICE"));
            LoadScenario scenario = scenarioManager.getScenario("OFFICE");

            ProcessorState state = new ProcessorState(scenario.getCpuLoadPercent());
            state.updateClock(2.0);

            double power = calculator.calculatePowerConsumption(mode, state);

            assertTrue(
                    power >= 8.0 && power <= 65.0,
                    "Stromverbrauch liegt nicht im gültigen Bereich von 8 W bis 65 W."
            );
        });
    }

    private static void testPowerConsumptionIsClampedToMinimum() {
        run("TC-PWR-001A", () -> {
            PowerConsumptionCalculator calculator = new PowerConsumptionCalculator();

            assertDoubleEquals(
                    8.0,
                    calculator.clampPowerConsumption(2.0),
                    "Stromverbrauch wurde nicht auf den Minimalwert begrenzt."
            );
        });
    }

    private static void testPowerConsumptionIsClampedToMaximum() {
        run("TC-PWR-001B", () -> {
            PowerConsumptionCalculator calculator = new PowerConsumptionCalculator();

            assertDoubleEquals(
                    65.0,
                    calculator.clampPowerConsumption(100.0),
                    "Stromverbrauch wurde nicht auf den Maximalwert begrenzt."
            );
        });
    }

    private static void testPowerConsumptionUnitIsShownInResult() {
        run("TC-PWR-002", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE", "MITTEL")
            );

            assertTrue(
                    result.toDisplayText().contains("W"),
                    "Stromverbrauch wird nicht mit Einheit W ausgegeben."
            );
        });
    }

    private static void testModeChangeIsLogged() {
        run("TC-LOG-001", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE", "MITTEL")
            );

            assertTrue(
                    containsLogEntry(result.getLogEntries(), EventLogger.EVENT_MODE_CHANGE),
                    "MODUSWECHSEL wurde nicht protokolliert."
            );
        });
    }

    private static void testScenarioChangeIsLogged() {
        run("TC-LOG-002", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE", "MITTEL")
            );

            assertTrue(
                    containsLogEntry(result.getLogEntries(), EventLogger.EVENT_SCENARIO_CHANGE),
                    "LASTSZENARIOWECHSEL wurde nicht protokolliert."
            );
        });
    }

    private static void testTemperatureWarningIsLogged() {
        run("TC-LOG-003", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("PERFORMANCE", "STRESS_TEST", "NIEDRIG")
            );

            assertTrue(
                    containsLogEntry(result.getLogEntries(), EventLogger.EVENT_TEMPERATURE_WARNING),
                    "TEMPERATURWARNUNG wurde bei hoher Temperatur nicht protokolliert."
            );
        });
    }

    private static void testLogEntryContainsSimulationData() {
        run("TC-LOG-004", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("PERFORMANCE", "STRESS_TEST", "NIEDRIG")
            );

            LogEntry firstEntry = result.getLogEntries().getFirst();

            assertTrue(firstEntry.getTimestamp() != null, "Zeitpunkt fehlt im LogEntry.");
            assertTrue(firstEntry.getEventType() != null && !firstEntry.getEventType().isBlank(), "Ereignistyp fehlt im LogEntry.");
            assertTrue(firstEntry.getDescription() != null && !firstEntry.getDescription().isBlank(), "Beschreibung fehlt im LogEntry.");
            assertTrue(firstEntry.getPerformanceModeName() != null && !firstEntry.getPerformanceModeName().isBlank(), "Leistungsmodus fehlt im LogEntry.");
            assertTrue(firstEntry.getLoadScenarioName() != null && !firstEntry.getLoadScenarioName().isBlank(), "Lastszenario fehlt im LogEntry.");
            assertTrue(firstEntry.getCpuLoadPercent() >= 0.0, "CPU-Auslastung fehlt im LogEntry.");
            assertTrue(firstEntry.getClockGhz() >= 1.2, "CPU-Taktfrequenz fehlt im LogEntry.");
            assertTrue(firstEntry.getTemperatureCelsius() >= 35.0, "CPU-Temperatur fehlt im LogEntry.");
            assertEquals("Normal", firstEntry.getThrottlingState(), "Throttling-State ist in Iteration 2 nicht Normal.");
        });
    }

    private static void testIteration1InputConstructorStillWorks() {
        run("REG-I1-001", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE")
            );

            assertEquals("MITTEL", result.getCoolingLevel().getKey(), "Alter SimulationInput-Konstruktor setzt nicht MITTEL als Standardkühlung.");
            assertDoubleEquals(35.0, result.getProcessorState().getCpuLoadPercent(), "CPU-Auslastung aus Iteration 1 ist fehlerhaft.");
        });
    }

    private static void testIteration1ClockCalculationStillWorks() {
        run("REG-I1-002", () -> {
            PerformanceModeManager modeManager = new PerformanceModeManager();
            LoadScenarioManager scenarioManager = new LoadScenarioManager();
            ClockCalculator clockCalculator = new ClockCalculator();

            PerformanceMode mode = modeManager.getMode(new SimulationInput("BALANCED", "OFFICE"));
            LoadScenario scenario = scenarioManager.getScenario("OFFICE");

            double clock = clockCalculator.calculateClock(mode, scenario);

            assertDoubleEquals(2.0, clock, "Taktberechnung aus Iteration 1 wurde verändert.");
        });
    }

    private static void testFullIteration2SimulationCreatesCompleteResult() {
        run("TC-INT-001", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("PERFORMANCE", "GAMING", "HOCH")
            );

            assertTrue(result.getPerformanceMode() != null, "PerformanceMode fehlt im Ergebnis.");
            assertTrue(result.getLoadScenario() != null, "LoadScenario fehlt im Ergebnis.");
            assertTrue(result.getCoolingLevel() != null, "CoolingLevel fehlt im Ergebnis.");
            assertTrue(result.getProcessorState().hasClock(), "CPU-Takt fehlt im Ergebnis.");
            assertTrue(result.getProcessorState().hasTemperature(), "Temperatur fehlt im Ergebnis.");
            assertTrue(result.getProcessorState().hasPowerConsumption(), "Stromverbrauch fehlt im Ergebnis.");
            assertTrue(result.getSummary() != null && !result.getSummary().isBlank(), "Zusammenfassung fehlt im Ergebnis.");
        });
    }

    private static void testSimulationFinishesWithinFiveSeconds() {
        run("TC-NF-PERF-001", () -> {
            PerformanceController controller = createDefaultController();

            long start = System.nanoTime();

            controller.runSimulation(
                    new SimulationInput("PERFORMANCE", "STRESS_TEST", "NIEDRIG")
            );

            long end = System.nanoTime();
            long durationMillis = (end - start) / 1_000_000;

            assertTrue(
                    durationMillis <= 5000,
                    "Simulation dauert länger als 5 Sekunden."
            );
        });
    }

    private static void testNumericValuesAreShownWithUnits() {
        run("TC-NF-UNIT-001", () -> {
            PerformanceController controller = createDefaultController();

            SimulationResult result = controller.runSimulation(
                    new SimulationInput("BALANCED", "OFFICE", "MITTEL")
            );

            String output = result.toDisplayText();

            assertTrue(output.contains("%"), "CPU-Auslastung wird nicht mit Einheit Prozent ausgegeben.");
            assertTrue(output.contains("GHz"), "CPU-Taktfrequenz wird nicht mit Einheit GHz ausgegeben.");
            assertTrue(output.contains("°C"), "CPU-Temperatur wird nicht mit Einheit °C ausgegeben.");
            assertTrue(output.contains("W"), "Stromverbrauch wird nicht mit Einheit W ausgegeben.");
        });
    }

    private static boolean containsCoolingLevel(List<CoolingLevel> coolingLevels, String key) {
        for (CoolingLevel coolingLevel : coolingLevels) {
            if (coolingLevel.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    private static boolean containsLogEntry(List<LogEntry> entries, String eventType) {
        for (LogEntry entry : entries) {
            if (entry.getEventType().equals(eventType)) {
                return true;
            }
        }

        return false;
    }

    private static void run(String testId, Runnable test) {
        try {
            test.run();
            passed++;
            details.add(testId + ";BESTANDEN;");
        } catch (AssertionError | RuntimeException exception) {
            failed++;
            details.add(testId + ";FEHLGESCHLAGEN;" + exception.getMessage());
        }
    }

    private static void runExpectingIllegalArgumentException(Runnable test) {
        try {
            test.run();
            failed++;
            details.add("TC-TMP-NEG-001" + ";FEHLGESCHLAGEN;IllegalArgumentException wurde erwartet.");
        } catch (IllegalArgumentException expectedException) {
            passed++;
            details.add("TC-TMP-NEG-001" + ";BESTANDEN;");
        } catch (RuntimeException exception) {
            failed++;
            details.add("TC-TMP-NEG-001" + ";FEHLGESCHLAGEN;Falscher Exception-Typ: " + exception.getClass().getSimpleName());
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) {
            return;
        }

        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " Erwartet: " + expected + ", Ist: " + actual);
        }
    }

    private static void assertDoubleEquals(double expected, double actual, String message) {
        double tolerance = 0.0001;

        if (Math.abs(expected - actual) > tolerance) {
            throw new AssertionError(message + " Erwartet: " + expected + ", Ist: " + actual);
        }
    }

    private static void printResult() {
        System.out.println("passed=" + passed);
        System.out.println("failed=" + failed);

        for (String detail : details) {
            System.out.println(detail);
        }
    }
}