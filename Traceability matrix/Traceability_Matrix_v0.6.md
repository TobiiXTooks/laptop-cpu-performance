# Traceability Matrix v0.6

## 1. Zweck dieses Dokuments

Diese Traceability Matrix dokumentiert die Nachverfolgbarkeit der Anforderungen des Laptop-Prozessorleistungsmoduls über die relevanten Projektartefakte hinweg.

Die Matrix verbindet:

```text
Lastenheft → Pflichtenheft → Requirement → Teilfunktionalität → Iteration → Architekturkomponente → Schnittstelle → Klasse/Datenmodell → Code-Modul → Testfall → Testergebnis
```

Für Iteration 1 wurden Implementierung, Unit-Tests, manuelle Validierung und Test Reporting durchgeführt.

Für Iteration 2 wurden Architektur, Feindesign, Implementierung, automatisierte Tests, Testdokumentation und Test Reporting fortgeschrieben. Die Requirements zu Kühlungsstufe, Temperaturberechnung, Stromverbrauch und einfacher Ereignisprotokollierung wurden implementiert und getestet.

Für Iteration 3 wurden Thermal Throttling, effektive CPU-Taktfrequenz, Stromverbrauch nach Throttling, erweiterte Ereignisprotokollierung, Reset-Funktion, Vererbung durch `SimulationOption` und Interface-Nutzung durch `Displayable` umgesetzt und automatisiert getestet.

---

## 2. Legende

| Spalte | Bedeutung |
|---|---|
| LH-ID | Kundenanforderung aus dem Lastenheft |
| PH-Bezug | Konkretisierung im Pflichtenheft oder zugehörige Teilfunktionalität |
| REQ-ID | Konkretes funktionales oder nicht-funktionales Requirement |
| TF-ID | Zugehörige Teilfunktionalität |
| Iteration | Geplante oder tatsächliche Iteration der Umsetzung |
| Verifikation | Vorgesehene oder durchgeführte Prüfart |
| CMP-ID | Zugeordnete Architekturkomponente |
| Komponente | Name der Architekturkomponente |
| IF-ID | Relevante Schnittstelle aus der Schnittstellendokumentation |
| Klasse / Datenmodell | Relevante Klasse oder relevantes Datenobjekt aus dem Feindesign |
| Code-Modul | Zugeordnete Java-Datei oder Projektartefakt |
| Testfall | Zugeordnete Testfall-ID |
| Status | Aktueller Traceability-Status |

---

## 3. Änderungen gegenüber Version 0.5

| Änderung | Beschreibung |
|---|---|
| Versionsstand | Version wurde von `0.5` auf `0.6` erhöht. |
| Iteration 3 | Requirements REQ-F-019 und REQ-F-024 bis REQ-F-033 wurden auf konkrete Java-Implementierung und Tests aktualisiert. |
| REQ-F-019 | Wurde von „vorbereitet / offen bis Iteration 3“ auf `Implementiert und getestet` gesetzt. |
| Thermal Throttling | `ThermalThrottlingController` und `ThrottlingState` wurden als konkrete Umsetzung ergänzt. |
| Effektive CPU-Taktfrequenz | `ProcessorState` und `SimulationResult` wurden um effektive CPU-Taktfrequenz und Throttling-Stufe ergänzt. |
| Stromverbrauch | `PowerConsumptionCalculator` wurde auf Berechnung mit effektiver CPU-Taktfrequenz nach Throttling aktualisiert. |
| Ereignisprotokollierung | `EventLogger` wurde um Throttling-, Änderungs-, Reset- und laufbezogene Logik ergänzt. |
| Oberklasse | `SimulationOption` wurde für `PerformanceMode`, `LoadScenario` und `CoolingLevel` ergänzt. |
| Interface | `Displayable` wurde für `LogEntry` und `SimulationResult` ergänzt. |
| Testfall-Zuordnung | Iteration-3-Testfälle aus `Iteration3UnitTestRunner.java` wurden ergänzt. |
| Testergebnis | 56 automatisierte Tests wurden erfolgreich ausgeführt, 0 fehlgeschlagen. |
| Status | Alle Iteration-3-Requirements wurden auf `Implementiert und getestet` gesetzt. |
| Nicht-funktionale Requirements | REQ-NF-001, REQ-NF-004, REQ-NF-005, REQ-NF-006 und REQ-NF-007 wurden mit Iteration-3-Testfällen verknüpft. |
| Baseline-Status | Iteration 3 ist testseitig abgeschlossen; Baseline steht noch aus. |

---

## 4. Traceability Matrix – Funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | TF-ID | Iteration | Verifikation | CMP-ID | Komponente | IF-ID | Klasse / Datenmodell | Code-Modul | Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-F-001 | TF-01 | REQ-F-001 | TF-01 | 1 | Unit-Test, manuelle Validierung, Regressionstest | CMP-001 | `PerformanceModeManager` | IF-002 | `PerformanceMode`, `SimulationOption`, `PerformanceModeManager` | `src/service/PerformanceModeManager.java`, `src/model/PerformanceMode.java`, `src/model/SimulationOption.java` | TST-I1-001, TST-I1-020, REG-I1-001, REG-I1-002, REG-I1-003 | Implementiert und getestet |
| LH-F-001 | TF-01 | REQ-F-002 | TF-01 | 1 | Unit-Test, manuelle Validierung, Regressionstest | CMP-001, CMP-008 | `PerformanceModeManager`, `PerformanceController` | IF-002 | `PerformanceMode`, `SimulationInput` | `src/service/PerformanceModeManager.java`, `src/controller/PerformanceController.java`, `src/model/SimulationInput.java` | TST-I1-002, TST-I1-020, REG-I1-001, REG-I1-002, REG-I1-003 | Implementiert und getestet |
| LH-F-002 | TF-01 | REQ-F-003 | TF-01 | 1 | Unit-Test, Entscheidungstabellen-Test, Regressionstest | CMP-001 | `PerformanceModeManager` | IF-002 | `PerformanceMode`, `SimulationInput` | `src/service/PerformanceModeManager.java`, `src/model/PerformanceMode.java`, `src/model/SimulationInput.java` | TST-I1-003, REG-I1-006 | Implementiert und getestet |
| LH-F-002 | TF-01 | REQ-F-004 | TF-01 | 1 | Unit-Test, Grenzwerttest | CMP-001, CMP-009 | `PerformanceModeManager`, `UserInterface` | IF-001, IF-002 | `SimulationInput`, `PerformanceMode` | `src/service/PerformanceModeManager.java`, `src/ui/UserInterface.java`, `src/model/SimulationInput.java` | TST-I1-004, TST-I1-005, REG-I1-006 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-005 | TF-02 | 1 | Unit-Test, manuelle Validierung, Regressionstest | CMP-002 | `LoadScenarioManager` | IF-003 | `LoadScenario`, `SimulationOption`, `LoadScenarioManager` | `src/service/LoadScenarioManager.java`, `src/model/LoadScenario.java`, `src/model/SimulationOption.java` | TST-I1-006, TST-I1-020, REG-I1-004, REG-I1-005 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-006 | TF-02 | 1 | Unit-Test, Regressionstest | CMP-002 | `LoadScenarioManager` | IF-003 | `LoadScenario` | `src/service/LoadScenarioManager.java`, `src/model/LoadScenario.java` | TST-I1-007, REG-I1-004, REG-I1-005 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-007 | TF-02 | 1 | Unit-Test, manuelle Validierung, Regressionstest | CMP-002, CMP-008 | `LoadScenarioManager`, `PerformanceController` | IF-003 | `LoadScenario`, `SimulationInput` | `src/service/LoadScenarioManager.java`, `src/controller/PerformanceController.java`, `src/model/SimulationInput.java` | TST-I1-008, TST-I1-020, REG-I1-004, REG-I1-005 | Implementiert und getestet |
| LH-F-004 | TF-02 | REQ-F-008 | TF-02 | 1 | Integrationstest, manuelle Validierung, Regressionstest | CMP-002, CMP-009 | `LoadScenarioManager`, `UserInterface` | IF-003, IF-009 | `LoadScenario`, `SimulationResult`, `ProcessorState` | `src/service/LoadScenarioManager.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I1-009, TST-I1-020, TC-INT-I3-006 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-009 | TF-03 | 1 | Unit-Test, Integrationstest, Regressionstest | CMP-003 | `ClockCalculator` | IF-004 | `ClockCalculator`, `ProcessorState` | `src/service/ClockCalculator.java`, `src/model/ProcessorState.java`, `src/controller/PerformanceController.java` | TST-I1-010, TST-I1-014, REG-I1-002, TC-NF-I3-001 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-010 | TF-03 | 1 | Unit-Test, Grenzwerttest, Regressionstest | CMP-003 | `ClockCalculator` | IF-004 | `ClockCalculator`, `ProcessorState` | `src/service/ClockCalculator.java`, `src/model/ProcessorState.java` | TST-I1-011, TST-I1-012, REG-I1-002 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-011 | TF-03 | 1 | Integrationstest, manuelle Validierung, Regressionstest | CMP-003, CMP-009 | `ClockCalculator`, `UserInterface` | IF-004, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/ClockCalculator.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I1-013, TST-I1-020, TC-NF-UNIT-001, TC-INT-I3-006 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-012 | TF-03 | 1 | Integrationstest, Regressionstest | CMP-008, CMP-003 | `PerformanceController`, `ClockCalculator` | IF-001, IF-004, IF-009 | `SimulationInput`, `ProcessorState`, `SimulationResult` | `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`, `src/model/SimulationInput.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TST-I1-014, REG-I1-001, REG-I1-002, TC-INT-I3-001, TC-INT-I3-002 | Implementiert und getestet |
| LH-F-005 | TF-04 | REQ-F-013 | TF-04 | 2 | Review, Funktionstest, Regressionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-001, IF-005 | `CoolingLevel`, `SimulationOption`, `TemperatureSimulator`, `SimulationInput` | `src/model/CoolingLevel.java`, `src/model/SimulationOption.java`, `src/service/TemperatureSimulator.java`, `src/ui/UserInterface.java` | TC-TMP-001, REG-I2-001, REG-I2-002, REG-I2-003 | Implementiert und getestet |
| LH-F-005 | TF-04 | REQ-F-014 | TF-04 | 2 | Funktionstest, Negativtest, Integrationstest | CMP-004, CMP-008, CMP-009 | `TemperatureSimulator`, `PerformanceController`, `UserInterface` | IF-001, IF-005 | `SimulationInput`, `CoolingLevel`, `SimulationResult` | `src/model/SimulationInput.java`, `src/service/TemperatureSimulator.java`, `src/controller/PerformanceController.java`, `src/ui/UserInterface.java` | TC-TMP-002, TC-TMP-NEG-001, REG-I2-001, REG-I2-002, REG-I2-003 | Implementiert und getestet |
| LH-F-006 | TF-04 | REQ-F-015 | TF-04 | 2 | Funktionstest, Grenzwerttest, Regressionstest | CMP-004 | `TemperatureSimulator` | IF-005 | `TemperatureSimulator`, `ProcessorState`, `CoolingLevel` | `src/service/TemperatureSimulator.java`, `src/model/ProcessorState.java`, `src/model/CoolingLevel.java` | TC-TMP-003, TC-TMP-003A, TC-TMP-003B, TC-TMP-003C, REG-I2-004 | Implementiert und getestet |
| LH-F-006 | TF-04 | REQ-F-016 | TF-04 | 2 | Funktionstest, Integrationstest, Regressionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-005, IF-009 | `ProcessorState`, `SimulationResult` | `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/ui/UserInterface.java` | TC-TMP-004, TC-NF-UNIT-001, TC-INT-I3-006 | Implementiert und getestet |
| LH-F-009 | TF-06 | REQ-F-017 | TF-06 | 2 | Funktionstest, Grenzwerttest, Regressionstest | CMP-006 | `PowerConsumptionCalculator` | IF-007 | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceMode` | `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/PerformanceMode.java` | TC-PWR-001, TC-PWR-001A, TC-PWR-001B, TC-PWR-I3-002, TC-PWR-I3-003 | Implementiert und getestet |
| LH-F-009 | TF-06 | REQ-F-018 | TF-06 | 2 | Funktionstest, Integrationstest, Regressionstest | CMP-006, CMP-009 | `PowerConsumptionCalculator`, `UserInterface` | IF-007, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/ui/UserInterface.java` | TC-PWR-002, TC-NF-UNIT-001, TC-PWR-I3-002, TC-PWR-I3-003 | Implementiert und getestet |
| LH-F-009 | TF-06 | REQ-F-019 | TF-06 | 3 | Modultest, Integrationstest, Negativtest | CMP-006, CMP-005, CMP-008 | `PowerConsumptionCalculator`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-007 | `ProcessorState`, `SimulationResult`, `ThrottlingState` | `src/service/PowerConsumptionCalculator.java`, `src/service/ThermalThrottlingController.java`, `src/controller/PerformanceController.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/model/ThrottlingState.java` | TC-PWR-I3-001, TC-PWR-I3-004, TC-INT-I3-004 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-020 | TF-07 | 2 | Funktionstest, Regressionstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-008 | `EventLogger`, `LogEntry`, `PerformanceMode` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java` | TC-LOG-001, REG-I2-005 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-021 | TF-07 | 2 | Funktionstest, Regressionstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-008 | `EventLogger`, `LogEntry`, `LoadScenario` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java` | TC-LOG-002, REG-I2-005 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-022 | TF-07 | 2 | Grenzwerttest, Integrationstest, Regressionstest | CMP-007, CMP-004 | `EventLogger`, `TemperatureSimulator` | IF-005, IF-008 | `EventLogger`, `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/service/TemperatureSimulator.java`, `src/model/ProcessorState.java` | TC-LOG-003, TC-LOG-I3-001, TC-LOG-I3-002, REG-I2-005 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-023 | TF-07 | 2 | Review, Funktionstest, Regressionstest | CMP-007 | `EventLogger` | IF-008 | `LogEntry`, `ProcessorState`, `SimulationResult` | `src/model/LogEntry.java`, `src/service/EventLogger.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TC-LOG-004, TC-LOG-I3-008, REG-I2-005 | Implementiert und getestet |
| LH-F-007 | TF-05 | REQ-F-024 | TF-05 | 3 | Grenzwerttest, Negativtest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ThermalThrottlingController`, `ProcessorState`, `ThrottlingState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TC-THR-001, TC-THR-002, TC-THR-003, TC-THR-004, TC-THR-005, TC-THR-006, TC-THR-007, TC-THR-008, TC-THR-009, TC-THR-010, TC-THR-011, TC-THR-012 | Implementiert und getestet |
| LH-F-008 | TF-05 | REQ-F-025 | TF-05 | 3 | Modultest, Integrationstest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState`, `ThrottlingState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TC-ECLK-001, TC-ECLK-002, TC-INT-I3-002 | Implementiert und getestet |
| LH-F-008 | TF-05 | REQ-F-026 | TF-05 | 3 | Modultest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState`, `ThrottlingState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TC-ECLK-003 | Implementiert und getestet |
| LH-F-008 | TF-05 | REQ-F-027 | TF-05 | 3 | Modultest, Integrationstest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState`, `ThrottlingState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TC-ECLK-004, TC-INT-I3-001 | Implementiert und getestet |
| LH-F-007 | TF-05 | REQ-F-028 | TF-05 | 3 | Funktionstest, Integrationstest, Ausgabetest | CMP-005, CMP-009 | `ThermalThrottlingController`, `UserInterface` | IF-006, IF-009 | `ProcessorState`, `SimulationResult`, `Displayable` | `src/service/ThermalThrottlingController.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/Displayable.java` | TC-ECLK-005, TC-INT-I3-006 | Implementiert und getestet |
| LH-F-007 | TF-05 | REQ-F-029 | TF-05 | 3 | Integrationstest | CMP-005, CMP-004, CMP-008 | `ThermalThrottlingController`, `TemperatureSimulator`, `PerformanceController` | IF-005, IF-006 | `ProcessorState`, `SimulationResult`, `ThrottlingState` | `src/service/ThermalThrottlingController.java`, `src/service/TemperatureSimulator.java`, `src/controller/PerformanceController.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/model/ThrottlingState.java` | TC-INT-I3-003 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-030 | TF-07 | 3 | Modultest, Integrationstest | CMP-007, CMP-005 | `EventLogger`, `ThermalThrottlingController` | IF-006, IF-008 | `LogEntry`, `ProcessorState`, `ThrottlingState` | `src/service/EventLogger.java`, `src/service/ThermalThrottlingController.java`, `src/model/LogEntry.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TC-LOG-I3-003, TC-LOG-I3-004, TC-INT-I3-001 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-031 | TF-07 | 3 | Zustandsbasierter Test, Integrationstest | CMP-007, CMP-005, CMP-008 | `EventLogger`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-008 | `LogEntry`, `ProcessorState`, `ThrottlingState` | `src/service/EventLogger.java`, `src/service/ThermalThrottlingController.java`, `src/controller/PerformanceController.java`, `src/model/LogEntry.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TC-LOG-I3-006 | Implementiert und getestet |
| LH-F-011 | TF-07 | REQ-F-032 | TF-07 | 3 | Funktionstest, Integrationstest, Ausgabetest | CMP-007, CMP-009, CMP-008 | `EventLogger`, `UserInterface`, `PerformanceController` | IF-008, IF-009 | `LogEntry`, `SimulationResult`, `Displayable` | `src/service/EventLogger.java`, `src/ui/UserInterface.java`, `src/model/LogEntry.java`, `src/model/SimulationResult.java`, `src/model/Displayable.java`, `src/controller/PerformanceController.java` | TC-LOG-I3-007, TC-LOG-I3-008, TC-INT-I3-005 | Implementiert und getestet |
| LH-F-011 | TF-07 | REQ-F-033 | TF-07 | 3 | Modultest, Integrationstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-001, IF-008 | `EventLogger`, `LogEntry`, `SimulationResult` | `src/service/EventLogger.java`, `src/controller/PerformanceController.java`, `src/model/LogEntry.java`, `src/model/SimulationResult.java` | TC-LOG-I3-009 | Implementiert und getestet |

---

## 5. Traceability Matrix – Nicht-funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | Kategorie | Iteration | Verifikation | CMP-ID / Artefakt | Komponente / Artefakt | IF-ID | Klasse / Datenmodell | Code-Modul / Artefakt | Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-NF-002 | PH-NF-003 | REQ-NF-001 | Determinismus | 1–3 | Unit-Test, Wiederholungstest, Regressionstest | CMP-008, CMP-003, CMP-004, CMP-005, CMP-006 | `PerformanceController`, `ClockCalculator`, `TemperatureSimulator`, `ThermalThrottlingController`, `PowerConsumptionCalculator` | IF-001, IF-004, IF-005, IF-006, IF-007, IF-009 | `ProcessorState`, `SimulationResult`, `ThrottlingState` | `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`, `src/service/TemperatureSimulator.java`, `src/service/ThermalThrottlingController.java`, `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/model/ThrottlingState.java` | TST-I1-015, REG-I1-001, REG-I1-002, TC-INT-001, TC-NF-I3-001 | Implementiert und getestet |
| LH-NF-005 | PH-NF-006 | REQ-NF-002 | Testbarkeit | 1–3 | Unit-Test, Traceability-Review | Projektartefakt | Traceability Matrix, Testfälle, Unit-TestRunner | alle relevanten IFs | alle relevanten Klassen | `Projekt/Iteration 1/Test cases/Iteration1_Testfaelle.md`, `Projekt/Iteration 1/test/Iteration1UnitTestRunner.java`, `Projekt/Iteration 2/Link to Test cases/Iteration2_Testfaelle.md`, `test/Iteration2UnitTestRunner.java`, `Projekt/Iteration 3/Test cases/Iteration3_Testfaelle.md`, `test/Iteration3UnitTestRunner.java`, `Projekt/Traceability/Traceability_Matrix_v0.6.md` | TST-I1-001 bis TST-I1-020, TC-TMP-001 bis TC-NF-UNIT-001, TC-THR-001 bis TC-NF-I3-004 | Implementiert und getestet |
| LH-NF-003 | PH-NF-004 | REQ-NF-003 | Robustheit | 1–3 | Unit-Test, Negativtest, manuelle Validierung | CMP-009, CMP-008, CMP-001, CMP-002, CMP-004, CMP-005, CMP-006 | `UserInterface`, `PerformanceController`, Manager-Komponenten, `TemperatureSimulator`, `ThermalThrottlingController`, `PowerConsumptionCalculator` | IF-001, IF-002, IF-003, IF-005, IF-006, IF-007 | `SimulationInput`, `ProcessorState`, `ThrottlingState` | `src/ui/UserInterface.java`, `src/controller/PerformanceController.java`, `src/service/PerformanceModeManager.java`, `src/service/LoadScenarioManager.java`, `src/service/TemperatureSimulator.java`, `src/service/ThermalThrottlingController.java`, `src/service/PowerConsumptionCalculator.java`, `src/model/SimulationInput.java`, `src/model/ProcessorState.java`, `src/model/ThrottlingState.java` | TST-I1-017, TST-I1-018, TST-I1-019, TST-I1-020, TC-TMP-NEG-001, TC-THR-011, TC-THR-012, TC-PWR-I3-004 | Implementiert und getestet |
| LH-NF-006 | PH-NF-007 | REQ-NF-004 | Konfigurationsmanagement / OO-Struktur | 1–3 | Baseline-Review, OO-Test | Projektartefakt, Modellbereich | Git-Branch, Baseline, Dokumentation, `SimulationOption` | nicht zutreffend | `SimulationOption`, `PerformanceMode`, `LoadScenario`, `CoolingLevel` | Git-Branch je Iteration, Dokumentationsstand, Testreporting, Traceability Matrix, `src/model/SimulationOption.java`, `src/model/PerformanceMode.java`, `src/model/LoadScenario.java`, `src/model/CoolingLevel.java` | TC-OO-I3-001, TC-OO-I3-002, TC-OO-I3-003, Baseline-Prüfung nach Commit/Tag | Implementiert und getestet; Baseline Iteration 3 noch offen |
| nicht direkt zugeordnet | PH-NF-003 | REQ-NF-005 | Performance | 2–3 | Laufzeittest | CMP-008 | `PerformanceController` | IF-001 bis IF-009 | `SimulationResult` | `src/controller/PerformanceController.java`, `src/model/SimulationResult.java` | TC-NF-PERF-001, TC-NF-I3-002 | Implementiert und getestet |
| LH-NF-004 | PH-NF-006 | REQ-NF-006 | Ausgabequalität | 2–3 | Funktionstest, Ausgabetest, Interface-Test | CMP-009, CMP-008 | `UserInterface`, `PerformanceController`, `Displayable` | IF-009 | `SimulationResult`, `ProcessorState`, `LogEntry`, `Displayable` | `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java`, `src/model/LogEntry.java`, `src/model/Displayable.java` | TC-NF-UNIT-001, TC-TMP-004, TC-PWR-002, TC-INT-I3-006, TC-OO-I3-004, TC-OO-I3-005, TC-NF-I3-003 | Implementiert und getestet |
| LH-NF-005 | PH-NF-006 | REQ-NF-007 | Änderbarkeit / Traceability / Abhängigkeitsarmut | 3 | Traceability-Review, Kompilierungstest | Projektartefakt | Requirements, Architektur, Tests, Traceability Matrix, Standard-Java | alle relevanten IFs | alle relevanten Klassen | `Projekt/Traceability/Traceability_Matrix_v0.6.md`, `test/Iteration3UnitTestRunner.java`, `src/` | TC-NF-CHG-001, TC-NF-I3-004 | Implementiert und getestet |

---

## 6. Testfall-ID-Konzept

| Präfix | Bedeutung |
|---|---|
| REG-I1 | Regressionstests für vorhandene Funktionalität aus Iteration 1 |
| REG-I2 | Regressionstests für vorhandene Funktionalität aus Iteration 2 |
| TC-ECLK | Tests für effektive CPU-Taktfrequenz |
| TC-INT | Integrationstests über mehrere Komponenten |
| TC-INT-I3 | Integrationstests der Iteration 3 |
| TC-LOG | Tests für `EventLogger` und `LogEntry` |
| TC-LOG-I3 | Tests für erweiterte Ereignisprotokollierung der Iteration 3 |
| TC-MAN | Manuelle Validierung aus Benutzersicht |
| TC-NF | Tests und Reviews für nicht-funktionale Requirements |
| TC-NF-I3 | Nicht-funktionale Tests der Iteration 3 |
| TC-OO-I3 | Tests für Vererbung, Oberklasse und Interface in Iteration 3 |
| TC-PMM | Tests für `PerformanceModeManager` |
| TC-LSM | Tests für `LoadScenarioManager` |
| TC-CLK | Tests für `ClockCalculator` |
| TC-PWR | Tests für `PowerConsumptionCalculator` |
| TC-PWR-I3 | Tests für Stromverbrauch nach Thermal Throttling |
| TC-THR | Tests für `ThermalThrottlingController` |
| TC-TMP | Tests für `TemperatureSimulator` und `CoolingLevel` |
| TST-I1 | Tatsächlich ausgeführte Testfälle der Iteration 1 |

---

## 7. Testausführungsstatus Iteration 2

| Testbereich | Testfälle | Ergebnis |
|---|---|---|
| Kühlungsstufen | TC-TMP-001, TC-TMP-002, TC-TMP-NEG-001 | bestanden |
| Temperaturberechnung | TC-TMP-003, TC-TMP-003A, TC-TMP-003B, TC-TMP-003C, TC-TMP-004 | bestanden |
| Stromverbrauchsberechnung | TC-PWR-001, TC-PWR-001A, TC-PWR-001B, TC-PWR-002 | bestanden |
| Ereignisprotokollierung | TC-LOG-001, TC-LOG-002, TC-LOG-003, TC-LOG-004 | bestanden |
| Regression Iteration 1 | REG-I1-001, REG-I1-002 | bestanden |
| Integration | TC-INT-001 | bestanden |
| Nicht-funktionale Tests | TC-NF-PERF-001, TC-NF-UNIT-001 | bestanden |

Gesamtergebnis der automatisierten Iteration-2-Tests:

```text
passed=21
failed=0
```

---

## 8. Testausführungsstatus Iteration 3

| Testbereich | Testfälle | Ergebnis |
|---|---|---|
| Thermal Throttling | TC-THR-001 bis TC-THR-012 | bestanden |
| Effektive CPU-Taktfrequenz | TC-ECLK-001 bis TC-ECLK-005 | bestanden |
| Stromverbrauch nach Throttling | TC-PWR-I3-001 bis TC-PWR-I3-004 | bestanden |
| Ereignisprotokollierung | TC-LOG-I3-001 bis TC-LOG-I3-009 | bestanden |
| Integration | TC-INT-I3-001 bis TC-INT-I3-006 | bestanden |
| Objektorientierte Struktur | TC-OO-I3-001 bis TC-OO-I3-005 | bestanden |
| Regression Iteration 1 | REG-I1-001 bis REG-I1-006 | bestanden |
| Regression Iteration 2 | REG-I2-001 bis REG-I2-005 | bestanden |
| Nicht-funktionale Tests | TC-NF-I3-001 bis TC-NF-I3-004 | bestanden |

Gesamtergebnis der automatisierten Iteration-3-Tests:

```text
Bestanden: 56
Fehlgeschlagen: 0
Exit Code: 0
```

---

## 9. Aktueller Status der Traceability

| Bereich | Status |
|---|---|
| Lastenheft zu Requirements | vorhanden |
| Pflichtenheft zu Requirements | vorhanden |
| Requirements zu Teilfunktionalitäten | vorhanden |
| Requirements zu Iterationen | vorhanden |
| Requirements zu Komponenten | für Iteration 1, 2 und 3 konkretisiert |
| Requirements zu Schnittstellen | für Iteration 1, 2 und 3 konkretisiert |
| Requirements zu Klassen/Datenmodellen | für Iteration 1, 2 und 3 konkretisiert |
| Requirements zu Code-Modulen | für Iteration 1, 2 und 3 konkret umgesetzt |
| Requirements zu Testfällen | für Iteration 1, 2 und 3 konkret durchgeführt |
| Requirements zu Testergebnissen | für Iteration 1, 2 und 3 vorhanden |
| Regression von Iteration 1 in Iteration 2 | vorhanden |
| Regression von Iteration 1 und 2 in Iteration 3 | vorhanden |
| Baseline Iteration 2 | abgeschlossen |
| Baseline Iteration 3 | offen bis Git-Commit und Tag |

---

## 10. Ergebnisbewertung

Die Traceability Matrix v0.6 zeigt, dass alle funktionalen Requirements der Iteration 3 einem konkreten Code-Modul und mindestens einem konkreten Testfall zugeordnet sind.

Die Anforderungen an Thermal Throttling, effektive CPU-Taktfrequenz, Stromverbrauch nach Throttling, Ereignisprotokollierung und Reset-Funktion sind vollständig implementiert und getestet.

Die automatisierte Testausführung der Iteration 3 wurde mit folgendem Ergebnis abgeschlossen:

```text
56 Tests bestanden
0 Tests fehlgeschlagen
```

Damit ist die Traceability für Iteration 3 aus fachlicher und testbezogener Sicht vollständig.

---
