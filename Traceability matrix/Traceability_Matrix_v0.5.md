# Traceability Matrix

## 1. Zweck dieses Dokuments

Diese Traceability Matrix dokumentiert die Nachverfolgbarkeit der Anforderungen des Laptop-Prozessorleistungsmoduls über die relevanten Projektartefakte hinweg.

Die Matrix verbindet:

```text
Lastenheft → Pflichtenheft → Requirement → Teilfunktionalität → Iteration → Architekturkomponente → Schnittstelle → Klasse/Datenmodell → Code-Modul → Testfall → Testergebnis
```

Für Iteration 1 wurden Implementierung, Unit-Tests, manuelle Validierung und Test Reporting bereits durchgeführt. Die Iteration-1-Requirements sind daher mit konkreten Java-Code-Dateien, konkreten Testfällen und dem Status `Implementiert und getestet` versehen.

Für Iteration 2 wurden Architektur, Feindesign, Implementierung, automatisierte Tests, Testdokumentation und Test Reporting fortgeschrieben. Die Requirements der Iteration 2 zu Kühlungsstufe, Temperaturberechnung, Stromverbrauch und einfacher Ereignisprotokollierung sind den Java-Code-Dateien und Testfällen zugeordnet.

Thermal Throttling ist weiterhin Bestandteil der Iteration 3. Requirements, die Thermal Throttling voraussetzen, bleiben als vorbereitet oder geplant markiert.

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

## 3. Änderungen gegenüber Version 0.4

| Änderung | Beschreibung |
|---|---|
| Versionsstand | Version wurde von `0.4` auf `0.5` erhöht. |
| Iteration 2 | Requirements REQ-F-013 bis REQ-F-018 und REQ-F-020 bis REQ-F-023 wurden auf konkrete Java-Implementierung und Tests aktualisiert. |
| REQ-F-019 | Requirement wurde als vorbereitet, aber noch nicht vollständig implementiert markiert, da es Thermal Throttling aus Iteration 3 benötigt. |
| Code-Zuordnung | Python-Dateipfade aus der Planungsphase wurden durch konkrete Java-Dateipfade ersetzt. |
| Testfall-Zuordnung | Iteration-2-Testfälle aus `Iteration2UnitTestRunner.java` wurden ergänzt. |
| Status | Erfolgreich getestete Iteration-2-Requirements wurden auf `Implementiert und getestet` gesetzt. |
| Nicht-funktionale Requirements | REQ-NF-005 und REQ-NF-006 wurden mit Iteration-2-Testfällen verknüpft. |
| Regression | Regressionstests für Iteration-1-Funktionalität wurden aufgenommen. |

---

## 4. Traceability Matrix – Funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | TF-ID | Iteration | Verifikation | CMP-ID | Komponente | IF-ID | Klasse / Datenmodell | Code-Modul | Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-F-001 | TF-01 | REQ-F-001 | TF-01 | 1 | Unit-Test, manuelle Validierung | CMP-001 | `PerformanceModeManager` | IF-002 | `PerformanceMode`, `PerformanceModeManager` | `src/service/PerformanceModeManager.java`, `src/model/PerformanceMode.java` | TST-I1-001, TST-I1-020 | Implementiert und getestet |
| LH-F-001 | TF-01 | REQ-F-002 | TF-01 | 1 | Unit-Test, manuelle Validierung | CMP-001, CMP-008 | `PerformanceModeManager`, `PerformanceController` | IF-002 | `PerformanceMode`, `SimulationInput` | `src/service/PerformanceModeManager.java`, `src/controller/PerformanceController.java`, `src/model/SimulationInput.java` | TST-I1-002, TST-I1-020 | Implementiert und getestet |
| LH-F-002 | TF-01 | REQ-F-003 | TF-01 | 1 | Unit-Test, Entscheidungstabellen-Test | CMP-001 | `PerformanceModeManager` | IF-002 | `PerformanceMode`, `SimulationInput` | `src/service/PerformanceModeManager.java`, `src/model/PerformanceMode.java`, `src/model/SimulationInput.java` | TST-I1-003 | Implementiert und getestet |
| LH-F-002 | TF-01 | REQ-F-004 | TF-01 | 1 | Unit-Test, Grenzwerttest | CMP-001, CMP-009 | `PerformanceModeManager`, `UserInterface` | IF-001, IF-002 | `SimulationInput`, `PerformanceMode` | `src/service/PerformanceModeManager.java`, `src/ui/UserInterface.java`, `src/model/SimulationInput.java` | TST-I1-004, TST-I1-005 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-005 | TF-02 | 1 | Unit-Test, manuelle Validierung | CMP-002 | `LoadScenarioManager` | IF-003 | `LoadScenario`, `LoadScenarioManager` | `src/service/LoadScenarioManager.java`, `src/model/LoadScenario.java` | TST-I1-006, TST-I1-020 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-006 | TF-02 | 1 | Unit-Test | CMP-002 | `LoadScenarioManager` | IF-003 | `LoadScenario` | `src/service/LoadScenarioManager.java`, `src/model/LoadScenario.java` | TST-I1-007 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-007 | TF-02 | 1 | Unit-Test, manuelle Validierung | CMP-002, CMP-008 | `LoadScenarioManager`, `PerformanceController` | IF-003 | `LoadScenario`, `SimulationInput` | `src/service/LoadScenarioManager.java`, `src/controller/PerformanceController.java`, `src/model/SimulationInput.java` | TST-I1-008, TST-I1-020 | Implementiert und getestet |
| LH-F-004 | TF-02 | REQ-F-008 | TF-02 | 1 | Integrationstest, manuelle Validierung | CMP-002, CMP-009 | `LoadScenarioManager`, `UserInterface` | IF-003, IF-009 | `LoadScenario`, `SimulationResult`, `ProcessorState` | `src/service/LoadScenarioManager.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I1-009, TST-I1-020 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-009 | TF-03 | 1 | Unit-Test, Integrationstest, Regressionstest | CMP-003 | `ClockCalculator` | IF-004 | `ClockCalculator`, `ProcessorState` | `src/service/ClockCalculator.java`, `src/model/ProcessorState.java`, `src/controller/PerformanceController.java` | TST-I1-010, TST-I1-014, REG-I1-002 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-010 | TF-03 | 1 | Unit-Test, Grenzwerttest, Regressionstest | CMP-003 | `ClockCalculator` | IF-004 | `ClockCalculator`, `ProcessorState` | `src/service/ClockCalculator.java`, `src/model/ProcessorState.java` | TST-I1-011, TST-I1-012, REG-I1-002 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-011 | TF-03 | 1 | Integrationstest, manuelle Validierung, Regressionstest | CMP-003, CMP-009 | `ClockCalculator`, `UserInterface` | IF-004, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/ClockCalculator.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I1-013, TST-I1-020, TC-NF-UNIT-001 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-012 | TF-03 | 1 | Integrationstest, Regressionstest | CMP-008, CMP-003 | `PerformanceController`, `ClockCalculator` | IF-001, IF-004, IF-009 | `SimulationInput`, `ProcessorState`, `SimulationResult` | `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`, `src/model/SimulationInput.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TST-I1-014, REG-I1-001, REG-I1-002 | Implementiert und getestet |
| LH-F-005 | TF-04 | REQ-F-013 | TF-04 | 2 | Review, Funktionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-001, IF-005 | `CoolingLevel`, `TemperatureSimulator`, `SimulationInput` | `src/model/CoolingLevel.java`, `src/service/TemperatureSimulator.java`, `src/ui/UserInterface.java` | TC-TMP-001 | Implementiert und getestet |
| LH-F-005 | TF-04 | REQ-F-014 | TF-04 | 2 | Funktionstest, Negativtest, Integrationstest | CMP-004, CMP-008, CMP-009 | `TemperatureSimulator`, `PerformanceController`, `UserInterface` | IF-001, IF-005 | `SimulationInput`, `CoolingLevel`, `SimulationResult` | `src/model/SimulationInput.java`, `src/service/TemperatureSimulator.java`, `src/controller/PerformanceController.java`, `src/ui/UserInterface.java` | TC-TMP-002, TC-TMP-NEG-001 | Implementiert und getestet |
| LH-F-006 | TF-04 | REQ-F-015 | TF-04 | 2 | Funktionstest, Grenzwerttest | CMP-004 | `TemperatureSimulator` | IF-005 | `TemperatureSimulator`, `ProcessorState`, `CoolingLevel` | `src/service/TemperatureSimulator.java`, `src/model/ProcessorState.java`, `src/model/CoolingLevel.java` | TC-TMP-003, TC-TMP-003A, TC-TMP-003B, TC-TMP-003C | Implementiert und getestet |
| LH-F-006 | TF-04 | REQ-F-016 | TF-04 | 2 | Funktionstest, Integrationstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-005, IF-009 | `ProcessorState`, `SimulationResult` | `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/ui/UserInterface.java` | TC-TMP-004, TC-NF-UNIT-001 | Implementiert und getestet |
| LH-F-009 | TF-06 | REQ-F-017 | TF-06 | 2 | Funktionstest, Grenzwerttest | CMP-006 | `PowerConsumptionCalculator` | IF-007 | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceMode` | `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/PerformanceMode.java` | TC-PWR-001, TC-PWR-001A, TC-PWR-001B | Implementiert und getestet |
| LH-F-009 | TF-06 | REQ-F-018 | TF-06 | 2 | Funktionstest, Integrationstest | CMP-006, CMP-009 | `PowerConsumptionCalculator`, `UserInterface` | IF-007, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java`, `src/ui/UserInterface.java` | TC-PWR-002, TC-NF-UNIT-001 | Implementiert und getestet |
| LH-F-009 | TF-06 | REQ-F-019 | TF-06 | 3 | Integrationstest | CMP-006, CMP-005, CMP-008 | `PowerConsumptionCalculator`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-007 | `ProcessorState`, `SimulationResult` | `src/service/PowerConsumptionCalculator.java`, `src/service/ThermalThrottlingController.java`, `src/controller/PerformanceController.java` | TC-INT-002 | Vorbereitet durch Iteration 2; vollständig offen bis Iteration 3 |
| LH-F-010 | TF-07 | REQ-F-020 | TF-07 | 2 | Funktionstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-008 | `EventLogger`, `LogEntry`, `PerformanceMode` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java` | TC-LOG-001 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-021 | TF-07 | 2 | Funktionstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-008 | `EventLogger`, `LogEntry`, `LoadScenario` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java` | TC-LOG-002 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-022 | TF-07 | 2 | Grenzwerttest, Integrationstest | CMP-007, CMP-004 | `EventLogger`, `TemperatureSimulator` | IF-005, IF-008 | `EventLogger`, `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/service/TemperatureSimulator.java`, `src/model/ProcessorState.java` | TC-LOG-003 | Implementiert und getestet |
| LH-F-010 | TF-07 | REQ-F-023 | TF-07 | 2 | Review, Funktionstest | CMP-007 | `EventLogger` | IF-008 | `LogEntry`, `ProcessorState`, `SimulationResult` | `src/model/LogEntry.java`, `src/service/EventLogger.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TC-LOG-004 | Implementiert und getestet |
| LH-F-007 | TF-05 | REQ-F-024 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ThermalThrottlingController`, `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TC-THR-001 | Design zugeordnet |
| LH-F-008 | TF-05 | REQ-F-025 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TC-THR-002 | Design zugeordnet |
| LH-F-008 | TF-05 | REQ-F-026 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TC-THR-003 | Design zugeordnet |
| LH-F-008 | TF-05 | REQ-F-027 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TC-THR-004 | Design zugeordnet |
| LH-F-007 | TF-05 | REQ-F-028 | TF-05 | 3 | Funktionstest | CMP-005, CMP-009 | `ThermalThrottlingController`, `UserInterface` | IF-006, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/ThermalThrottlingController.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java` | TC-THR-005 | Design zugeordnet |
| LH-F-007 | TF-05 | REQ-F-029 | TF-05 | 3 | Integrationstest | CMP-005, CMP-004, CMP-008 | `ThermalThrottlingController`, `TemperatureSimulator`, `PerformanceController` | IF-005, IF-006 | `ProcessorState`, `SimulationResult` | `src/service/ThermalThrottlingController.java`, `src/service/TemperatureSimulator.java`, `src/controller/PerformanceController.java` | TC-INT-003 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-030 | TF-07 | 3 | Grenzwerttest | CMP-007, CMP-005 | `EventLogger`, `ThermalThrottlingController` | IF-006, IF-008 | `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/service/ThermalThrottlingController.java`, `src/model/LogEntry.java`, `src/model/ProcessorState.java` | TC-LOG-005 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-031 | TF-07 | 3 | Integrationstest | CMP-007, CMP-005, CMP-008 | `EventLogger`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-008 | `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/service/ThermalThrottlingController.java`, `src/controller/PerformanceController.java` | TC-LOG-006 | Design zugeordnet |
| LH-F-011 | TF-07 | REQ-F-032 | TF-07 | 3 | Funktionstest | CMP-007, CMP-009 | `EventLogger`, `UserInterface` | IF-008, IF-009 | `LogEntry`, `SimulationResult` | `src/service/EventLogger.java`, `src/ui/UserInterface.java`, `src/model/LogEntry.java`, `src/model/SimulationResult.java` | TC-LOG-007 | Design zugeordnet |
| LH-F-011 | TF-07 | REQ-F-033 | TF-07 | 3 | Integrationstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-001, IF-008 | `EventLogger`, `ProcessorState`, `SimulationResult` | `src/service/EventLogger.java`, `src/controller/PerformanceController.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TC-LOG-008 | Design zugeordnet |

---

## 5. Traceability Matrix – Nicht-funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | Kategorie | Iteration | Verifikation | CMP-ID / Artefakt | Komponente / Artefakt | IF-ID | Klasse / Datenmodell | Code-Modul / Artefakt | Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-NF-002 | PH-NF-003 | REQ-NF-001 | Determinismus | 1–2 | Unit-Test, Wiederholungstest, Regressionstest | CMP-008, CMP-003, CMP-004, CMP-006 | `PerformanceController`, `ClockCalculator`, `TemperatureSimulator`, `PowerConsumptionCalculator` | IF-001, IF-004, IF-005, IF-007, IF-009 | `ProcessorState`, `SimulationResult` | `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`, `src/service/TemperatureSimulator.java`, `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TST-I1-015, REG-I1-001, REG-I1-002, TC-INT-001 | Implementiert und getestet |
| LH-NF-005 | PH-NF-006 | REQ-NF-002 | Testbarkeit | 1–2 | Unit-Test, Traceability-Review | Projektartefakt | Traceability Matrix, Testfälle, Unit-TestRunner | alle relevanten IFs | alle relevanten Klassen | `Projekt/Iteration 1/Test cases/Iteration1_Testfaelle.md`, `Projekt/Iteration 1/test/Iteration1UnitTestRunner.java`, `Projekt/Iteration 2/Link to Test cases/Iteration2_Testfaelle.md`, `test/Iteration2UnitTestRunner.java`, `Projekt/Traceability/Traceability_Matrix_v0.5.md` | TST-I1-001 bis TST-I1-020, TC-TMP-001 bis TC-NF-UNIT-001 | Implementiert und getestet |
| LH-NF-003 | PH-NF-004 | REQ-NF-003 | Robustheit | 1–2 | Unit-Test, Negativtest, manuelle Validierung | CMP-009, CMP-008, CMP-001, CMP-002, CMP-004 | `UserInterface`, `PerformanceController`, Manager-Komponenten, `TemperatureSimulator` | IF-001, IF-002, IF-003, IF-005 | `SimulationInput` | `src/ui/UserInterface.java`, `src/controller/PerformanceController.java`, `src/service/PerformanceModeManager.java`, `src/service/LoadScenarioManager.java`, `src/service/TemperatureSimulator.java`, `src/model/SimulationInput.java` | TST-I1-017, TST-I1-018, TST-I1-019, TST-I1-020, TC-TMP-NEG-001 | Implementiert und getestet |
| LH-NF-006 | PH-NF-007 | REQ-NF-004 | Konfigurationsmanagement | 1–3 | Baseline-Review | Projektartefakt | Git-Branch, Baseline, Dokumentation | nicht zutreffend | nicht zutreffend | Git-Branch je Iteration, Dokumentationsstand, Testreporting, Traceability Matrix | Baseline-Prüfung nach Commit/Tag | Iteration 2 offen bis Baseline |
| nicht direkt zugeordnet | PH-NF-003 | REQ-NF-005 | Performance | 2 | Laufzeittest | CMP-008 | `PerformanceController` | IF-001 bis IF-009 | `SimulationResult` | `src/controller/PerformanceController.java`, `src/model/SimulationResult.java` | TC-NF-PERF-001 | Implementiert und getestet |
| LH-NF-004 | PH-NF-006 | REQ-NF-006 | Ausgabequalität | 2 | Funktionstest | CMP-009, CMP-008 | `UserInterface`, `PerformanceController` | IF-009 | `SimulationResult`, `ProcessorState` | `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TC-NF-UNIT-001, TC-TMP-004, TC-PWR-002 | Implementiert und getestet |
| LH-NF-005 | PH-NF-006 | REQ-NF-007 | Änderbarkeit / Traceability | 3 | Traceability-Review | Projektartefakt | Requirements, Architektur, Tests, Traceability Matrix | alle relevanten IFs | alle relevanten Klassen | `Projekt/Traceability/Traceability_Matrix.md` | TC-NF-CHG-001 | Design zugeordnet |

---

## 6. Testfall-ID-Konzept

| Präfix | Bedeutung |
|---|---|
| REG-I1 | Regressionstests für vorhandene Funktionalität aus Iteration 1 |
| TC-INT | Integrationstests über mehrere Komponenten |
| TC-LOG | Tests für `EventLogger` und `LogEntry` |
| TC-MAN | Manuelle Validierung aus Benutzersicht |
| TC-NF | Tests und Reviews für nicht-funktionale Requirements |
| TC-PMM | Tests für `PerformanceModeManager` |
| TC-LSM | Tests für `LoadScenarioManager` |
| TC-CLK | Tests für `ClockCalculator` |
| TC-PWR | Tests für `PowerConsumptionCalculator` |
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

## 8. Aktueller Status der Traceability

| Bereich | Status |
|---|---|
| Lastenheft zu Requirements | vorhanden |
| Pflichtenheft zu Requirements | vorhanden |
| Requirements zu Teilfunktionalitäten | vorhanden |
| Requirements zu Iterationen | vorhanden |
| Requirements zu Komponenten | für Iteration 1 und 2 konkretisiert |
| Requirements zu Schnittstellen | für Iteration 1 und 2 konkretisiert |
| Requirements zu Klassen/Datenmodellen | für Iteration 1 und 2 konkretisiert |
| Requirements zu Code-Modulen | für Iteration 1 und 2 konkret umgesetzt; Iteration 3 geplant |
| Requirements zu Testfällen | für Iteration 1 und 2 konkret durchgeführt; Iteration 3 geplant |
| Requirements zu Testergebnissen | für Iteration 1 und 2 vorhanden; Iteration 3 offen |
| Regression von Iteration 1 in Iteration 2 | vorhanden |
| Baseline Iteration 2 | offen bis Git-Commit und Tag |