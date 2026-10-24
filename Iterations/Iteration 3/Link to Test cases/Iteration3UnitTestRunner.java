import controller.PerformanceController;
import model.CoolingLevel;
import model.Displayable;
import model.LoadScenario;
import model.LogEntry;
import model.PerformanceMode;
import model.ProcessorState;
import model.SimulationInput;
import model.SimulationOption;
import model.SimulationResult;
import model.ThrottlingState;
import service.ClockCalculator;
import service.EventLogger;
import service.LoadScenarioManager;
import service.PerformanceModeManager;
import service.PowerConsumptionCalculator;
import service.TemperatureSimulator;
import service.ThermalThrottlingController;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Automatisierter TestRunner für Iteration 3.
 *
 * Ausführung:
 * javac -encoding UTF-8 -d out $(find src -name "*.java")
 * javac -encoding UTF-8 -cp out -d out test/Iteration3UnitTestRunner.java
 * java -cp out Iteration3UnitTestRunner
 */
public class Iteration3UnitTestRunner {

    private static int passed = 0;
    private static int failed = 0;

    private static final double DELTA = 0.0001;

    public static void main(String[] args) {
        System.out.println("Iteration 3 – Unit- und Integrationstests");
        System.out.println("================================================");

        run("TC-THR-001 – Throttling-Stufe Normal", Iteration3UnitTestRunner::testThr001Normal);
        run("TC-THR-002 – Throttling-Stufe Warnung", Iteration3UnitTestRunner::testThr002Warnung);
        run("TC-THR-003 – Throttling-Stufe Reduziert", Iteration3UnitTestRunner::testThr003Reduziert);
        run("TC-THR-004 – Throttling-Stufe Kritisch", Iteration3UnitTestRunner::testThr004Kritisch);
        run("TC-THR-005 – Grenzwert 69 °C", Iteration3UnitTestRunner::testThr005Grenzwert69);
        run("TC-THR-006 – Grenzwert 70 °C", Iteration3UnitTestRunner::testThr006Grenzwert70);
        run("TC-THR-007 – Grenzwert 79 °C", Iteration3UnitTestRunner::testThr007Grenzwert79);
        run("TC-THR-008 – Grenzwert 80 °C", Iteration3UnitTestRunner::testThr008Grenzwert80);
        run("TC-THR-009 – Grenzwert 89 °C", Iteration3UnitTestRunner::testThr009Grenzwert89);
        run("TC-THR-010 – Grenzwert 90 °C", Iteration3UnitTestRunner::testThr010Grenzwert90);
        run("TC-THR-011 – Ungültige Temperatur unter Minimum", Iteration3UnitTestRunner::testThr011InvalidLow);
        run("TC-THR-012 – Ungültige Temperatur über Maximum", Iteration3UnitTestRunner::testThr012InvalidHigh);

        run("TC-ECLK-001 – Effektiver Takt bei Normal", Iteration3UnitTestRunner::testEclk001Normal);
        run("TC-ECLK-002 – Effektiver Takt bei Warnung", Iteration3UnitTestRunner::testEclk002Warnung);
        run("TC-ECLK-003 – Effektiver Takt bei Reduziert", Iteration3UnitTestRunner::testEclk003Reduziert);
        run("TC-ECLK-004 – Effektiver Takt bei Kritisch", Iteration3UnitTestRunner::testEclk004Kritisch);
        run("TC-ECLK-005 – Throttling-Werte im ProcessorState", Iteration3UnitTestRunner::testEclk005ProcessorState);

        run("TC-PWR-I3-001 – Verbrauch basiert auf effektivem Takt", Iteration3UnitTestRunner::testPwr001EffectiveClock);
        run("TC-PWR-I3-002 – Mindestwert Stromverbrauch", Iteration3UnitTestRunner::testPwr002MinClamp);
        run("TC-PWR-I3-003 – Maximalwert Stromverbrauch", Iteration3UnitTestRunner::testPwr003MaxClamp);
        run("TC-PWR-I3-004 – Fehlender effektiver Takt", Iteration3UnitTestRunner::testPwr004MissingEffectiveClock);

        run("TC-LOG-I3-001 – Temperaturwarnung ab 70 °C", Iteration3UnitTestRunner::testLog001TemperatureWarning);
        run("TC-LOG-I3-002 – Keine Temperaturwarnung unter 70 °C", Iteration3UnitTestRunner::testLog002NoTemperatureWarning);
        run("TC-LOG-I3-003 – Throttling aktiviert bei Reduziert", Iteration3UnitTestRunner::testLog003ThrottlingReduced);
        run("TC-LOG-I3-004 – Throttling aktiviert bei Kritisch", Iteration3UnitTestRunner::testLog004ThrottlingCritical);
        run("TC-LOG-I3-005 – Kein aktives Throttling bei Normal", Iteration3UnitTestRunner::testLog005NoActiveThrottling);
        run("TC-LOG-I3-006 – Throttling-Stufe geändert", Iteration3UnitTestRunner::testLog006ThrottlingChanged);
        run("TC-LOG-I3-007 – Ereignisse ab Startindex", Iteration3UnitTestRunner::testLog007EntriesFromIndex);
        run("TC-LOG-I3-008 – Chronologische Ereignisausgabe", Iteration3UnitTestRunner::testLog008Chronological);
        run("TC-LOG-I3-009 – Reset-Ereignis wird protokolliert", Iteration3UnitTestRunner::testLog009ResetEvent);

        run("TC-INT-I3-001 – Vollständiger Lauf mit kritischem Throttling", Iteration3UnitTestRunner::testInt001CriticalThrottling);
        run("TC-INT-I3-002 – Vollständiger Lauf ohne aktives Throttling", Iteration3UnitTestRunner::testInt002NoActiveThrottling);
        run("TC-INT-I3-003 – Temperaturänderung führt zu neuer Stufe", Iteration3UnitTestRunner::testInt003TemperatureChangesState);
        run("TC-INT-I3-004 – Verbrauch nach Throttling neu berechnet", Iteration3UnitTestRunner::testInt004PowerAfterThrottling);
        run("TC-INT-I3-005 – Nur aktuelle Logeinträge im Ergebnis", Iteration3UnitTestRunner::testInt005OnlyCurrentLogEntries);
        run("TC-INT-I3-006 – Ergebnistext enthält Throttling und Einheiten", Iteration3UnitTestRunner::testInt006DisplayText);

        run("TC-OO-I3-001 – PerformanceMode erbt von SimulationOption", Iteration3UnitTestRunner::testOo001PerformanceModeInheritance);
        run("TC-OO-I3-002 – LoadScenario erbt von SimulationOption", Iteration3UnitTestRunner::testOo002LoadScenarioInheritance);
        run("TC-OO-I3-003 – CoolingLevel erbt von SimulationOption", Iteration3UnitTestRunner::testOo003CoolingLevelInheritance);
        run("TC-OO-I3-004 – LogEntry implementiert Displayable", Iteration3UnitTestRunner::testOo004LogEntryDisplayable);
        run("TC-OO-I3-005 – SimulationResult implementiert Displayable", Iteration3UnitTestRunner::testOo005SimulationResultDisplayable);

        run("REG-I1-001 – ENERGY_SAVE bleibt verfügbar", Iteration3UnitTestRunner::testRegI1001EnergySave);
        run("REG-I1-002 – BALANCED bleibt verfügbar", Iteration3UnitTestRunner::testRegI1002Balanced);
        run("REG-I1-003 – PERFORMANCE bleibt verfügbar", Iteration3UnitTestRunner::testRegI1003Performance);
        run("REG-I1-004 – IDLE bleibt verfügbar", Iteration3UnitTestRunner::testRegI1004Idle);
        run("REG-I1-005 – GAMING bleibt verfügbar", Iteration3UnitTestRunner::testRegI1005Gaming);
        run("REG-I1-006 – Benutzerdefinierter Modus bleibt gültig", Iteration3UnitTestRunner::testRegI1006CustomMode);
        run("REG-I2-001 – Kühlungsstufe NIEDRIG bleibt verfügbar", Iteration3UnitTestRunner::testRegI2001CoolingLow);
        run("REG-I2-002 – Kühlungsstufe MITTEL bleibt verfügbar", Iteration3UnitTestRunner::testRegI2002CoolingMedium);
        run("REG-I2-003 – Kühlungsstufe HOCH bleibt verfügbar", Iteration3UnitTestRunner::testRegI2003CoolingHigh);
        run("REG-I2-004 – Temperaturberechnung bleibt deterministisch", Iteration3UnitTestRunner::testRegI2004TemperatureDeterministic);
        run("REG-I2-005 – Basisevents bleiben erhalten", Iteration3UnitTestRunner::testRegI2005BaseEvents);

        run("TC-NF-I3-001 – Deterministisches Ergebnis", Iteration3UnitTestRunner::testNf001DeterministicResult);
        run("TC-NF-I3-002 – Laufzeit unter 5 Sekunden", Iteration3UnitTestRunner::testNf002Runtime);
        run("TC-NF-I3-003 – Ausgabe enthält Einheiten", Iteration3UnitTestRunner::testNf003Units);
        run("TC-NF-I3-004 – Keine externen Abhängigkeiten", Iteration3UnitTestRunner::testNf004NoExternalDependencies);

        System.out.println("================================================");
        System.out.println("Testergebnis Iteration 3");
        System.out.println("Bestanden: " + passed);
        System.out.println("Fehlgeschlagen: " + failed);

        if (failed > 0) {
            throw new AssertionError("Es sind " + failed + " Test(s) fehlgeschlagen.");
        }
    }

    // ------------------------------------------------------------
    // Thermal Throttling
    // ------------------------------------------------------------

    private static void testThr001Normal() {
        assertEquals(ThrottlingState.NORMAL, new ThermalThrottlingController().determineThrottlingState(65.0));
    }

    private static void testThr002Warnung() {
        assertEquals(ThrottlingState.WARNUNG, new ThermalThrottlingController().determineThrottlingState(75.0));
    }

    private static void testThr003Reduziert() {
        assertEquals(ThrottlingState.REDUZIERT, new ThermalThrottlingController().determineThrottlingState(85.0));
    }

    private static void testThr004Kritisch() {
        assertEquals(ThrottlingState.KRITISCH, new ThermalThrottlingController().determineThrottlingState(95.0));
    }

    private static void testThr005Grenzwert69() {
        assertEquals(ThrottlingState.NORMAL, new ThermalThrottlingController().determineThrottlingState(69.0));
    }

    private static void testThr006Grenzwert70() {
        assertEquals(ThrottlingState.WARNUNG, new ThermalThrottlingController().determineThrottlingState(70.0));
    }

    private static void testThr007Grenzwert79() {
        assertEquals(ThrottlingState.WARNUNG, new ThermalThrottlingController().determineThrottlingState(79.0));
    }

    private static void testThr008Grenzwert80() {
        assertEquals(ThrottlingState.REDUZIERT, new ThermalThrottlingController().determineThrottlingState(80.0));
    }

    private static void testThr009Grenzwert89() {
        assertEquals(ThrottlingState.REDUZIERT, new ThermalThrottlingController().determineThrottlingState(89.0));
    }

    private static void testThr010Grenzwert90() {
        assertEquals(ThrottlingState.KRITISCH, new ThermalThrottlingController().determineThrottlingState(90.0));
    }

    private static void testThr011InvalidLow() {
        assertThrows(IllegalArgumentException.class,
                () -> new ThermalThrottlingController().determineThrottlingState(20.0));
    }

    private static void testThr012InvalidHigh() {
        assertThrows(IllegalArgumentException.class,
                () -> new ThermalThrottlingController().determineThrottlingState(120.0));
    }

    // ------------------------------------------------------------
    // Effektive CPU-Taktfrequenz
    // ------------------------------------------------------------

    private static void testEclk001Normal() {
        assertEquals(4.0, new ThermalThrottlingController().calculateEffectiveClock(4.0, ThrottlingState.NORMAL), DELTA);
    }

    private static void testEclk002Warnung() {
        assertEquals(4.0, new ThermalThrottlingController().calculateEffectiveClock(4.0, ThrottlingState.WARNUNG), DELTA);
    }

    private static void testEclk003Reduziert() {
        assertEquals(3.2, new ThermalThrottlingController().calculateEffectiveClock(4.0, ThrottlingState.REDUZIERT), DELTA);
    }

    private static void testEclk004Kritisch() {
        assertEquals(2.4, new ThermalThrottlingController().calculateEffectiveClock(4.0, ThrottlingState.KRITISCH), DELTA);
    }

    private static void testEclk005ProcessorState() {
        ProcessorState state = new ProcessorState(85.0);
        state.updateClock(4.0);
        state.updateTemperature(95.0);

        new ThermalThrottlingController().applyThrottling(state);

        assertTrue(state.hasThrottlingState(), "Throttling-Stufe wurde nicht gespeichert.");
        assertTrue(state.hasEffectiveClock(), "Effektive CPU-Taktfrequenz wurde nicht gespeichert.");
        assertEquals(ThrottlingState.KRITISCH, state.getThrottlingState());
        assertEquals(2.4, state.getEffectiveClockGhz(), DELTA);
    }

    // ------------------------------------------------------------
    // Stromverbrauch
    // ------------------------------------------------------------

    private static void testPwr001EffectiveClock() {
        PerformanceMode mode = new PerformanceMode("TEST", "Test", false, 4.0, 60.0);

        ProcessorState throttledState = new ProcessorState(100.0);
        throttledState.updateClock(4.0);
        throttledState.updateTemperature(95.0);
        throttledState.updateThrottling(ThrottlingState.KRITISCH, 2.4);

        ProcessorState unthrottledState = new ProcessorState(100.0);
        unthrottledState.updateClock(4.0);
        unthrottledState.updateTemperature(65.0);
        unthrottledState.updateThrottling(ThrottlingState.NORMAL, 4.0);

        PowerConsumptionCalculator calculator = new PowerConsumptionCalculator();

        double throttledPower = calculator.calculatePowerConsumption(mode, throttledState);
        double unthrottledPower = calculator.calculatePowerConsumption(mode, unthrottledState);

        assertTrue(throttledPower < unthrottledPower, "Verbrauch wurde nicht durch effektiven Takt reduziert.");
    }

    private static void testPwr002MinClamp() {
        assertEquals(8.0, new PowerConsumptionCalculator().clampPowerConsumption(2.0), DELTA);
    }

    private static void testPwr003MaxClamp() {
        assertEquals(65.0, new PowerConsumptionCalculator().clampPowerConsumption(100.0), DELTA);
    }

    private static void testPwr004MissingEffectiveClock() {
        PerformanceMode mode = new PerformanceMode("TEST", "Test", false, 4.0, 60.0);
        ProcessorState state = new ProcessorState(50.0);
        state.updateClock(2.0);
        state.updateTemperature(60.0);

        assertThrows(IllegalArgumentException.class,
                () -> new PowerConsumptionCalculator().calculatePowerConsumption(mode, state));
    }

    // ------------------------------------------------------------
    // EventLogger
    // ------------------------------------------------------------

    private static void testLog001TemperatureWarning() {
        EventLogger logger = new EventLogger();
        logger.logTemperatureWarningIfNeeded(testMode(), testScenario(), readyState(50.0, 2.0, 70.0, ThrottlingState.WARNUNG, 2.0));
        assertTrue(containsEvent(logger.getEntries(), EventLogger.EVENT_TEMPERATURE_WARNING), "Temperaturwarnung fehlt.");
    }

    private static void testLog002NoTemperatureWarning() {
        EventLogger logger = new EventLogger();
        logger.logTemperatureWarningIfNeeded(testMode(), testScenario(), readyState(50.0, 2.0, 69.0, ThrottlingState.NORMAL, 2.0));
        assertFalse(containsEvent(logger.getEntries(), EventLogger.EVENT_TEMPERATURE_WARNING), "Temperaturwarnung wurde fälschlich erzeugt.");
    }

    private static void testLog003ThrottlingReduced() {
        EventLogger logger = new EventLogger();
        logger.logThrottlingActivatedIfNeeded(testMode(), testScenario(), readyState(85.0, 4.0, 85.0, ThrottlingState.REDUZIERT, 3.2));
        assertTrue(containsEvent(logger.getEntries(), EventLogger.EVENT_THROTTLING_ACTIVATED), "Throttling-Aktivierung fehlt.");
    }

    private static void testLog004ThrottlingCritical() {
        EventLogger logger = new EventLogger();
        logger.logThrottlingActivatedIfNeeded(testMode(), testScenario(), readyState(85.0, 4.0, 95.0, ThrottlingState.KRITISCH, 2.4));
        assertTrue(containsEvent(logger.getEntries(), EventLogger.EVENT_THROTTLING_ACTIVATED), "Throttling-Aktivierung fehlt.");
    }

    private static void testLog005NoActiveThrottling() {
        EventLogger logger = new EventLogger();
        logger.logThrottlingActivatedIfNeeded(testMode(), testScenario(), readyState(50.0, 2.0, 65.0, ThrottlingState.NORMAL, 2.0));
        assertFalse(containsEvent(logger.getEntries(), EventLogger.EVENT_THROTTLING_ACTIVATED), "Aktivierungsereignis wurde fälschlich erzeugt.");
    }

    private static void testLog006ThrottlingChanged() {
        EventLogger logger = new EventLogger();

        logger.logThrottlingChangedIfNeeded(testMode(), testScenario(), readyState(50.0, 2.0, 65.0, ThrottlingState.NORMAL, 2.0));
        logger.logThrottlingChangedIfNeeded(testMode(), testScenario(), readyState(85.0, 4.0, 95.0, ThrottlingState.KRITISCH, 2.4));

        assertTrue(countEvent(logger.getEntries(), EventLogger.EVENT_THROTTLING_CHANGED) >= 2,
                "Änderung der Throttling-Stufe wurde nicht protokolliert.");
    }

    @SuppressWarnings("unchecked")
    private static void testLog007EntriesFromIndex() {
        EventLogger logger = new EventLogger();
        PerformanceMode mode = testMode();
        LoadScenario scenario = testScenario();

        logger.logModeChangeIfNeeded(mode, scenario, readyState(50.0, 2.0, 65.0, ThrottlingState.NORMAL, 2.0));
        int startIndex = logger.getEntries().size();

        logger.logTemperatureWarningIfNeeded(mode, scenario, readyState(85.0, 4.0, 95.0, ThrottlingState.KRITISCH, 2.4));
        logger.logThrottlingActivatedIfNeeded(mode, scenario, readyState(85.0, 4.0, 95.0, ThrottlingState.KRITISCH, 2.4));

        try {
            Method method = EventLogger.class.getMethod("getEntriesFromIndex", int.class);
            List<LogEntry> entriesFromIndex = (List<LogEntry>) method.invoke(logger, startIndex);

            assertEquals(2, entriesFromIndex.size());
            assertFalse(containsEvent(entriesFromIndex, EventLogger.EVENT_MODE_CHANGE),
                    "Alte Ereignisse wurden nicht vom aktuellen Protokollabschnitt getrennt.");
        } catch (NoSuchMethodException exception) {
            throw new AssertionError("EventLogger.getEntriesFromIndex(int) fehlt.");
        } catch (ReflectiveOperationException exception) {
            throw new AssertionError("EventLogger.getEntriesFromIndex(int) konnte nicht ausgeführt werden: " + exception.getMessage());
        }
    }

    private static void testLog008Chronological() {
        EventLogger logger = new EventLogger();
        PerformanceMode mode = testMode();
        LoadScenario scenario = testScenario();

        logger.logModeChangeIfNeeded(mode, scenario, readyState(50.0, 2.0, 65.0, ThrottlingState.NORMAL, 2.0));
        logger.logScenarioChangeIfNeeded(mode, scenario, readyState(50.0, 2.0, 65.0, ThrottlingState.NORMAL, 2.0));
        logger.logTemperatureWarningIfNeeded(mode, scenario, readyState(85.0, 4.0, 90.0, ThrottlingState.KRITISCH, 2.4));

        List<LogEntry> chronologicalEntries = logger.getEntriesChronological();
        assertTrue(isChronological(chronologicalEntries), "Ereignisse sind nicht chronologisch sortiert.");
    }

    private static void testLog009ResetEvent() {
        EventLogger logger = new EventLogger();
        PerformanceController controller = createController(logger);

        controller.resetSimulation();

        assertTrue(containsEvent(logger.getEntries(), EventLogger.EVENT_SIMULATION_RESET),
                "Reset-Ereignis fehlt. Prüfe die Reihenfolge in resetSimulation(): erst clear(), dann logSimulationReset().");
    }

    // ------------------------------------------------------------
    // Integration
    // ------------------------------------------------------------

    private static void testInt001CriticalThrottling() {
        SimulationResult result = createController(new EventLogger())
                .runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"));

        ProcessorState state = result.getProcessorState();

        assertEquals(ThrottlingState.KRITISCH, state.getThrottlingState());
        assertTrue(state.getEffectiveClockGhz() < state.getClockGhz(), "Effektiver Takt wurde nicht reduziert.");
        assertTrue(containsEvent(result.getLogEntries(), EventLogger.EVENT_THROTTLING_ACTIVATED), "Throttling-Aktivierung fehlt im Ergebnis.");
    }

    private static void testInt002NoActiveThrottling() {
        SimulationResult result = createController(new EventLogger())
                .runSimulation(new SimulationInput("BALANCED", "IDLE", "HOCH"));

        ProcessorState state = result.getProcessorState();

        assertFalse(state.getThrottlingState().isActiveThrottling(), "Bei niedriger Last darf kein aktives Throttling auftreten.");
        assertEquals(state.getClockGhz(), state.getEffectiveClockGhz(), DELTA);
    }

    private static void testInt003TemperatureChangesState() {
        PerformanceController controller = createController(new EventLogger());

        SimulationResult lowLoad = controller.runSimulation(new SimulationInput("BALANCED", "IDLE", "HOCH"));
        SimulationResult highLoad = controller.runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"));

        assertNotEquals(lowLoad.getProcessorState().getThrottlingState(), highLoad.getProcessorState().getThrottlingState());
    }

    private static void testInt004PowerAfterThrottling() {
        SimulationResult result = createController(new EventLogger())
                .runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"));

        ProcessorState state = result.getProcessorState();

        assertEquals(ThrottlingState.KRITISCH, state.getThrottlingState());
        assertTrue(state.getEffectiveClockGhz() < state.getClockGhz(), "Effektiver Takt ist nicht kleiner als ungedrosselter Takt.");
        assertTrue(state.getPowerConsumptionWatt() < 65.0, "Verbrauch wurde nicht plausibel anhand des reduzierten Takts berechnet.");
    }

    private static void testInt005OnlyCurrentLogEntries() {
        PerformanceController controller = createController(new EventLogger());

        SimulationResult first = controller.runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"));
        SimulationResult second = controller.runSimulation(new SimulationInput("BALANCED", "IDLE", "HOCH"));

        assertTrue(first.getLogEntries().size() > 0, "Erster Simulationslauf hat keine Ereignisse erzeugt.");
        assertTrue(second.getLogEntries().size() < first.getLogEntries().size() + second.getLogEntries().size(),
                "Plausibilitätsprüfung der Loggrößen fehlgeschlagen.");

        assertFalse(hasEntryWithMode(second.getLogEntries(), "Höchstleistung"),
                "Das zweite SimulationResult enthält noch Logeinträge aus dem ersten Simulationslauf.");
    }

    private static void testInt006DisplayText() {
        SimulationResult result = createController(new EventLogger())
                .runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"));

        String text = result.toDisplayText();

        assertContains(text, "Effektive CPU-Taktfrequenz");
        assertContains(text, "Throttling");
        assertContains(text, "GHz");
        assertContains(text, "°C");
        assertContains(text, "W");
        assertContains(text, "%");
    }

    // ------------------------------------------------------------
    // OO-Tests
    // ------------------------------------------------------------

    private static void testOo001PerformanceModeInheritance() {
        assertTrue(SimulationOption.class.isAssignableFrom(PerformanceMode.class),
                "PerformanceMode erbt nicht von SimulationOption.");
        PerformanceMode mode = testMode();
        assertEquals("TEST", mode.getKey());
        assertEquals("Testmodus", mode.getName());
        assertEquals("Testmodus", mode.getDisplayName());
    }

    private static void testOo002LoadScenarioInheritance() {
        assertTrue(SimulationOption.class.isAssignableFrom(LoadScenario.class),
                "LoadScenario erbt nicht von SimulationOption.");
        LoadScenario scenario = testScenario();
        assertEquals("TEST_SCENARIO", scenario.getKey());
        assertEquals("Testszenario", scenario.getName());
        assertEquals("Testszenario", scenario.getDisplayName());
    }

    private static void testOo003CoolingLevelInheritance() {
        assertTrue(SimulationOption.class.isAssignableFrom(CoolingLevel.class),
                "CoolingLevel erbt nicht von SimulationOption.");
        CoolingLevel coolingLevel = new CoolingLevel("TEST_COOLING", "Testkühlung", 1.0);
        assertEquals("TEST_COOLING", coolingLevel.getKey());
        assertEquals("Testkühlung", coolingLevel.getName());
        assertEquals("Testkühlung", coolingLevel.getDisplayName());
    }

    private static void testOo004LogEntryDisplayable() {
        assertTrue(Displayable.class.isAssignableFrom(LogEntry.class),
                "LogEntry implementiert Displayable nicht.");

        LogEntry entry = new LogEntry(
                LocalDateTime.now(),
                "TEST_EVENT",
                "Testbeschreibung",
                "Testmodus",
                "Testszenario",
                50.0,
                2.0,
                65.0,
                "Normal"
        );

        String text = entry.toDisplayText();
        assertContains(text, "TEST_EVENT");
        assertContains(text, "Testbeschreibung");
    }

    private static void testOo005SimulationResultDisplayable() {
        assertTrue(Displayable.class.isAssignableFrom(SimulationResult.class),
                "SimulationResult implementiert Displayable nicht.");

        SimulationResult result = createController(new EventLogger())
                .runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"));

        String text = result.toDisplayText();
        assertContains(text, "Leistungsmodus");
        assertContains(text, "Effektive CPU-Taktfrequenz");
        assertContains(text, "Throttling");
    }

    // ------------------------------------------------------------
    // Regression Iteration 1 und 2
    // ------------------------------------------------------------

    private static void testRegI1001EnergySave() {
        PerformanceMode mode = new PerformanceModeManager().getMode(new SimulationInput("ENERGY_SAVE", "IDLE", "MITTEL"));
        assertEquals("ENERGY_SAVE", mode.getKey());
    }

    private static void testRegI1002Balanced() {
        PerformanceMode mode = new PerformanceModeManager().getMode(new SimulationInput("BALANCED", "IDLE", "MITTEL"));
        assertEquals("BALANCED", mode.getKey());
    }

    private static void testRegI1003Performance() {
        PerformanceMode mode = new PerformanceModeManager().getMode(new SimulationInput("PERFORMANCE", "IDLE", "MITTEL"));
        assertEquals("PERFORMANCE", mode.getKey());
    }

    private static void testRegI1004Idle() {
        LoadScenario scenario = new LoadScenarioManager().getScenario("IDLE");
        assertEquals(15.0, scenario.getCpuLoadPercent(), DELTA);
    }

    private static void testRegI1005Gaming() {
        LoadScenario scenario = new LoadScenarioManager().getScenario("GAMING");
        assertEquals(85.0, scenario.getCpuLoadPercent(), DELTA);
    }

    private static void testRegI1006CustomMode() {
        PerformanceMode mode = new PerformanceModeManager()
                .getMode(new SimulationInput("CUSTOM", "GAMING", "MITTEL", 3.6, 45.0));

        assertEquals("CUSTOM", mode.getKey());
        assertTrue(mode.isCustom(), "Custom-Modus wurde nicht als benutzerdefiniert markiert.");
        assertEquals(3.6, mode.getMaxClockGhz(), DELTA);
        assertEquals(45.0, mode.getPowerLimitWatt(), DELTA);
    }

    private static void testRegI2001CoolingLow() {
        CoolingLevel cooling = new TemperatureSimulator().getCoolingLevel("NIEDRIG");
        assertEquals("NIEDRIG", cooling.getKey());
        assertTrue(cooling.getCoolingFactor() > 0.0, "Kühlungsfaktor muss positiv sein.");
    }

    private static void testRegI2002CoolingMedium() {
        CoolingLevel cooling = new TemperatureSimulator().getCoolingLevel("MITTEL");
        assertEquals("MITTEL", cooling.getKey());
        assertTrue(cooling.getCoolingFactor() > 0.0, "Kühlungsfaktor muss positiv sein.");
    }

    private static void testRegI2003CoolingHigh() {
        CoolingLevel cooling = new TemperatureSimulator().getCoolingLevel("HOCH");
        assertEquals("HOCH", cooling.getKey());
        assertTrue(cooling.getCoolingFactor() > 0.0, "Kühlungsfaktor muss positiv sein.");
    }

    private static void testRegI2004TemperatureDeterministic() {
        TemperatureSimulator simulator = new TemperatureSimulator();
        CoolingLevel cooling = simulator.getCoolingLevel("MITTEL");

        ProcessorState first = new ProcessorState(85.0);
        first.updateClock(4.0);

        ProcessorState second = new ProcessorState(85.0);
        second.updateClock(4.0);

        assertEquals(
                simulator.calculateTemperature(first, cooling),
                simulator.calculateTemperature(second, cooling),
                DELTA
        );
    }

    private static void testRegI2005BaseEvents() {
        EventLogger logger = new EventLogger();
        PerformanceMode mode = testMode();
        LoadScenario scenario = testScenario();
        ProcessorState state = readyState(85.0, 4.0, 95.0, ThrottlingState.KRITISCH, 2.4);

        logger.logModeChangeIfNeeded(mode, scenario, state);
        logger.logScenarioChangeIfNeeded(mode, scenario, state);
        logger.logTemperatureWarningIfNeeded(mode, scenario, state);

        List<LogEntry> entries = logger.getEntries();

        assertTrue(containsEvent(entries, EventLogger.EVENT_MODE_CHANGE), "MODUSWECHSEL fehlt.");
        assertTrue(containsEvent(entries, EventLogger.EVENT_SCENARIO_CHANGE), "LASTSZENARIOWECHSEL fehlt.");
        assertTrue(containsEvent(entries, EventLogger.EVENT_TEMPERATURE_WARNING), "TEMPERATURWARNUNG fehlt.");
    }

    // ------------------------------------------------------------
    // Nicht-funktionale Tests
    // ------------------------------------------------------------

    private static void testNf001DeterministicResult() {
        SimulationInput input = new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG");

        SimulationResult first = createController(new EventLogger()).runSimulation(input);
        SimulationResult second = createController(new EventLogger()).runSimulation(input);

        ProcessorState a = first.getProcessorState();
        ProcessorState b = second.getProcessorState();

        assertEquals(a.getClockGhz(), b.getClockGhz(), DELTA);
        assertEquals(a.getTemperatureCelsius(), b.getTemperatureCelsius(), DELTA);
        assertEquals(a.getThrottlingState(), b.getThrottlingState());
        assertEquals(a.getEffectiveClockGhz(), b.getEffectiveClockGhz(), DELTA);
        assertEquals(a.getPowerConsumptionWatt(), b.getPowerConsumptionWatt(), DELTA);
    }

    private static void testNf002Runtime() {
        PerformanceController controller = createController(new EventLogger());
        SimulationInput input = new SimulationInput("PERFORMANCE", "STRESS_TEST", "NIEDRIG");

        long start = System.nanoTime();
        controller.runSimulation(input);
        long end = System.nanoTime();

        double seconds = (end - start) / 1_000_000_000.0;
        assertTrue(seconds < 5.0, "Simulationslauf dauerte länger als 5 Sekunden: " + seconds);
    }

    private static void testNf003Units() {
        String text = createController(new EventLogger())
                .runSimulation(new SimulationInput("PERFORMANCE", "GAMING", "NIEDRIG"))
                .toDisplayText();

        assertContains(text, "%");
        assertContains(text, "GHz");
        assertContains(text, "°C");
        assertContains(text, "W");
    }

    private static void testNf004NoExternalDependencies() {
        assertNotNull(new ClockCalculator());
        assertNotNull(new TemperatureSimulator());
        assertNotNull(new ThermalThrottlingController());
        assertNotNull(new PowerConsumptionCalculator());
    }

    // ------------------------------------------------------------
    // Hilfsmethoden
    // ------------------------------------------------------------

    private static PerformanceController createController(EventLogger eventLogger) {
        return new PerformanceController(
                new PerformanceModeManager(),
                new LoadScenarioManager(),
                new ClockCalculator(),
                new TemperatureSimulator(),
                new ThermalThrottlingController(),
                new PowerConsumptionCalculator(),
                eventLogger
        );
    }

    private static PerformanceMode testMode() {
        return new PerformanceMode("TEST", "Testmodus", false, 4.0, 60.0);
    }

    private static LoadScenario testScenario() {
        return new LoadScenario("TEST_SCENARIO", "Testszenario", 85.0);
    }

    private static ProcessorState readyState(
            double cpuLoadPercent,
            double clockGhz,
            double temperatureCelsius,
            ThrottlingState throttlingState,
            double effectiveClockGhz
    ) {
        ProcessorState state = new ProcessorState(cpuLoadPercent);
        state.updateClock(clockGhz);
        state.updateTemperature(temperatureCelsius);
        state.updateThrottling(throttlingState, effectiveClockGhz);
        return state;
    }

    private static boolean containsEvent(List<LogEntry> entries, String eventType) {
        for (LogEntry entry : entries) {
            if (eventType.equals(entry.getEventType())) {
                return true;
            }
        }
        return false;
    }

    private static int countEvent(List<LogEntry> entries, String eventType) {
        int count = 0;
        for (LogEntry entry : entries) {
            if (eventType.equals(entry.getEventType())) {
                count++;
            }
        }
        return count;
    }

    private static boolean hasEntryWithMode(List<LogEntry> entries, String modeName) {
        for (LogEntry entry : entries) {
            if (modeName.equals(entry.getPerformanceModeName())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isChronological(List<LogEntry> entries) {
        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).getTimestamp().isBefore(entries.get(i - 1).getTimestamp())) {
                return false;
            }
        }
        return true;
    }

    private static void run(String testName, TestCase testCase) {
        try {
            testCase.run();
            passed++;
            System.out.println("[OK]   " + testName);
        } catch (Throwable throwable) {
            failed++;
            System.out.println("[FAIL] " + testName);
            System.out.println("       " + throwable.getMessage());
        }
    }

    private static void assertEquals(Object expected, Object actual) {
        if (expected == null && actual == null) {
            return;
        }

        if (expected != null && expected.equals(actual)) {
            return;
        }

        throw new AssertionError("Erwartet: " + expected + ", erhalten: " + actual);
    }

    private static void assertNotEquals(Object first, Object second) {
        if (first == null && second == null) {
            throw new AssertionError("Beide Werte sind gleich: null");
        }

        if (first != null && first.equals(second)) {
            throw new AssertionError("Beide Werte sind gleich: " + first);
        }
    }

    private static void assertEquals(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError("Erwartet: " + expected + ", erhalten: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertContains(String text, String expectedContent) {
        if (text == null || !text.contains(expectedContent)) {
            throw new AssertionError("Text enthält erwarteten Inhalt nicht: " + expectedContent);
        }
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Objekt darf nicht null sein.");
        }
    }

    private static void assertThrows(Class<? extends Throwable> expectedException, TestCase testCase) {
        try {
            testCase.run();
        } catch (Throwable throwable) {
            if (expectedException.isInstance(throwable)) {
                return;
            }

            throw new AssertionError(
                    "Falscher Exception-Typ. Erwartet: "
                            + expectedException.getSimpleName()
                            + ", erhalten: "
                            + throwable.getClass().getSimpleName()
            );
        }

        throw new AssertionError("Erwartete Exception wurde nicht ausgelöst: " + expectedException.getSimpleName());
    }

    private interface TestCase {
        void run() throws Exception;
    }
}
