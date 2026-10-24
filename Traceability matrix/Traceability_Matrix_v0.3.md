# Traceability Matrix

## 1. Zweck dieses Dokuments

Diese Traceability Matrix dokumentiert die Nachverfolgbarkeit der Anforderungen des Laptop-Prozessorleistungsmoduls über die relevanten Projektartefakte hinweg.

Die Matrix verbindet:

```text
Lastenheft → Pflichtenheft → Requirement → Teilfunktionalität → Iteration → Architekturkomponente → Schnittstelle → Klasse/Datenmodell → geplantes Code-Modul → geplanter Testfall
```

Für Iteration 1 wurden Implementierung, Unit-Tests, manuelle Validierung und Test Reporting bereits durchgeführt. Die Iteration-1-Requirements sind daher mit konkreten Java-Code-Dateien, konkreten Testfällen und dem Status `Implementiert und getestet` versehen. Requirements späterer Iterationen bleiben als geplante Zuordnung dokumentiert.

---

## 2. Legende

| Spalte | Bedeutung |
|---|---|
| LH-ID | Kundenanforderung aus dem Lastenheft |
| PH-Bezug | Konkretisierung im Pflichtenheft oder zugehörige Teilfunktionalität |
| REQ-ID | Konkretes funktionales oder nicht-funktionales Requirement |
| TF-ID | Zugehörige Teilfunktionalität |
| Iteration | Geplante Iteration der Umsetzung |
| Verifikation | Vorgesehene Prüfart |
| CMP-ID | Zugeordnete Architekturkomponente |
| Komponente | Name der Architekturkomponente |
| IF-ID | Relevante Schnittstelle aus der Schnittstellendokumentation |
| Klasse / Datenmodell | Relevante Klasse oder relevantes Datenobjekt aus dem Feindesign |
| Geplantes Code-Modul | Erwartete spätere Code-Zuordnung |
| Geplanter Testfall | Vorläufige Testfall-ID für die spätere Testdokumentation |
| Status | Aktueller Traceability-Status |

---

## 3. Änderungsnotiz Version 0.3

Version 0.3 wurde nach Abschluss der Iteration 1 erstellt.

Neu ergänzt oder geändert wurden:

| Änderung | Beschreibung |
|---|---|
| Iteration 1 konkretisiert | Die Requirements der Iteration 1 wurden von geplanten Zuordnungen auf konkrete Implementierung aktualisiert. |
| Java-Code-Dateien ergänzt | Python-Planungspfade wurden für Iteration 1 durch konkrete Java-Dateien ersetzt. |
| Testfälle ergänzt | Die tatsächlich ausgeführten Iteration-1-Testfälle `TST-I1-001` bis `TST-I1-020` wurden ergänzt. |
| Status aktualisiert | Die Iteration-1-Requirements wurden auf `Implementiert und getestet` gesetzt. |
| Nicht-funktionale Requirements aktualisiert | Determinismus, Testbarkeit und Robustheit wurden mit Iteration-1-Testfällen verbunden. |
| Spätere Iterationen beibehalten | Requirements für Iteration 2 und 3 blieben weiterhin als geplante Design-Zuordnung erhalten. |
| Testfall-ID-Konzept erweitert | Das Präfix `TST-I1` wurde ergänzt, um tatsächlich ausgeführte Iteration-1-Testfälle von geplanten Testfällen zu unterscheiden. |

Diese Version bildet den Übergang von einer reinen Planungs-Traceability zu einer nachweisbaren Implementierungs- und Test-Traceability für Iteration 1.

---

## 4. Traceability Matrix – Funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | TF-ID | Iteration | Verifikation | CMP-ID | Komponente | IF-ID | Klasse / Datenmodell | Geplantes Code-Modul | Geplanter Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-F-001 | TF-01 | REQ-F-001 | TF-01 | 1 | Unit-Test, manuelle Validierung | CMP-001 | `PerformanceModeManager` | IF-002 | `PerformanceMode`, `PerformanceModeManager` | `src/service/PerformanceModeManager.java`, `src/model/PerformanceMode.java` | TST-I1-001, TST-I1-020 | Implementiert und getestet |
| LH-F-001 | TF-01 | REQ-F-002 | TF-01 | 1 | Unit-Test, manuelle Validierung | CMP-001, CMP-008 | `PerformanceModeManager`, `PerformanceController` | IF-002 | `PerformanceMode`, `SimulationInput` | `src/service/PerformanceModeManager.java`, `src/controller/PerformanceController.java`, `src/model/SimulationInput.java` | TST-I1-002, TST-I1-020 | Implementiert und getestet |
| LH-F-002 | TF-01 | REQ-F-003 | TF-01 | 1 | Unit-Test, Entscheidungstabellen-Test | CMP-001 | `PerformanceModeManager` | IF-002 | `PerformanceMode`, `SimulationInput` | `src/service/PerformanceModeManager.java`, `src/model/PerformanceMode.java`, `src/model/SimulationInput.java` | TST-I1-003 | Implementiert und getestet |
| LH-F-002 | TF-01 | REQ-F-004 | TF-01 | 1 | Unit-Test, Grenzwerttest | CMP-001, CMP-009 | `PerformanceModeManager`, `UserInterface` | IF-001, IF-002 | `SimulationInput`, `PerformanceMode` | `src/service/PerformanceModeManager.java`, `src/ui/UserInterface.java`, `src/model/SimulationInput.java` | TST-I1-004, TST-I1-005 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-005 | TF-02 | 1 | Unit-Test, manuelle Validierung | CMP-002 | `LoadScenarioManager` | IF-003 | `LoadScenario`, `LoadScenarioManager` | `src/service/LoadScenarioManager.java`, `src/model/LoadScenario.java` | TST-I1-006, TST-I1-020 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-006 | TF-02 | 1 | Unit-Test | CMP-002 | `LoadScenarioManager` | IF-003 | `LoadScenario` | `src/service/LoadScenarioManager.java`, `src/model/LoadScenario.java` | TST-I1-007 | Implementiert und getestet |
| LH-F-003 | TF-02 | REQ-F-007 | TF-02 | 1 | Unit-Test, manuelle Validierung | CMP-002, CMP-008 | `LoadScenarioManager`, `PerformanceController` | IF-003 | `LoadScenario`, `SimulationInput` | `src/service/LoadScenarioManager.java`, `src/controller/PerformanceController.java`, `src/model/SimulationInput.java` | TST-I1-008, TST-I1-020 | Implementiert und getestet |
| LH-F-004 | TF-02 | REQ-F-008 | TF-02 | 1 | Integrationstest, manuelle Validierung | CMP-002, CMP-009 | `LoadScenarioManager`, `UserInterface` | IF-003, IF-009 | `LoadScenario`, `SimulationResult`, `ProcessorState` | `src/service/LoadScenarioManager.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I1-009, TST-I1-020 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-009 | TF-03 | 1 | Unit-Test, Integrationstest | CMP-003 | `ClockCalculator` | IF-004 | `ClockCalculator`, `ProcessorState` | `src/service/ClockCalculator.java`, `src/model/ProcessorState.java`, `src/controller/PerformanceController.java` | TST-I1-010, TST-I1-014 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-010 | TF-03 | 1 | Unit-Test, Grenzwerttest | CMP-003 | `ClockCalculator` | IF-004 | `ClockCalculator`, `ProcessorState` | `src/service/ClockCalculator.java`, `src/model/ProcessorState.java` | TST-I1-011, TST-I1-012 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-011 | TF-03 | 1 | Integrationstest, manuelle Validierung | CMP-003, CMP-009 | `ClockCalculator`, `UserInterface` | IF-004, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/ClockCalculator.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I1-013, TST-I1-020 | Implementiert und getestet |
| LH-F-004 | TF-03 | REQ-F-012 | TF-03 | 1 | Integrationstest | CMP-008, CMP-003 | `PerformanceController`, `ClockCalculator` | IF-001, IF-004, IF-009 | `SimulationInput`, `ProcessorState`, `SimulationResult` | `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`, `src/model/SimulationInput.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TST-I1-014 | Implementiert und getestet |
| LH-F-005 | TF-04 | REQ-F-013 | TF-04 | 2 | Review, Funktionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-001, IF-005 | `SimulationInput`, `ProcessorState` | `src/temperature_simulator.py`, `src/user_interface.py` | TC-TMP-001 | Design zugeordnet |
| LH-F-005 | TF-04 | REQ-F-014 | TF-04 | 2 | Funktionstest | CMP-004, CMP-008 | `TemperatureSimulator`, `PerformanceController` | IF-001, IF-005 | `SimulationInput`, `ProcessorState` | `src/temperature_simulator.py`, `src/performance_controller.py` | TC-TMP-002 | Design zugeordnet |
| LH-F-006 | TF-04 | REQ-F-015 | TF-04 | 2 | Funktionstest, Grenzwerttest | CMP-004 | `TemperatureSimulator` | IF-005 | `TemperatureSimulator`, `ProcessorState` | `src/temperature_simulator.py` | TC-TMP-003 | Design zugeordnet |
| LH-F-006 | TF-04 | REQ-F-016 | TF-04 | 2 | Funktionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-005, IF-009 | `ProcessorState`, `SimulationResult` | `src/temperature_simulator.py`, `src/user_interface.py` | TC-TMP-004 | Design zugeordnet |
| LH-F-009 | TF-06 | REQ-F-017 | TF-06 | 2 | Funktionstest, Grenzwerttest | CMP-006 | `PowerConsumptionCalculator` | IF-007 | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceMode` | `src/power_consumption_calculator.py` | TC-PWR-001 | Design zugeordnet |
| LH-F-009 | TF-06 | REQ-F-018 | TF-06 | 2 | Funktionstest | CMP-006, CMP-009 | `PowerConsumptionCalculator`, `UserInterface` | IF-007, IF-009 | `ProcessorState`, `SimulationResult` | `src/power_consumption_calculator.py`, `src/user_interface.py` | TC-PWR-002 | Design zugeordnet |
| LH-F-009 | TF-06 | REQ-F-019 | TF-06 | 2 | Integrationstest | CMP-006, CMP-005, CMP-008 | `PowerConsumptionCalculator`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-007 | `ProcessorState`, `SimulationResult` | `src/power_consumption_calculator.py`, `src/thermal_throttling_controller.py` | TC-INT-002 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-020 | TF-07 | 2 | Funktionstest | CMP-007, CMP-001 | `EventLogger`, `PerformanceModeManager` | IF-008 | `EventLogger`, `LogEntry`, `PerformanceMode` | `src/event_logger.py` | TC-LOG-001 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-021 | TF-07 | 2 | Funktionstest | CMP-007, CMP-002 | `EventLogger`, `LoadScenarioManager` | IF-008 | `EventLogger`, `LogEntry`, `LoadScenario` | `src/event_logger.py` | TC-LOG-002 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-022 | TF-07 | 2 | Grenzwerttest | CMP-007, CMP-004 | `EventLogger`, `TemperatureSimulator` | IF-005, IF-008 | `EventLogger`, `LogEntry`, `ProcessorState` | `src/event_logger.py`, `src/temperature_simulator.py` | TC-LOG-003 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-023 | TF-07 | 2 | Review, Funktionstest | CMP-007 | `EventLogger` | IF-008 | `LogEntry`, `ProcessorState`, `SimulationResult` | `src/event_logger.py` | TC-LOG-004 | Design zugeordnet |
| LH-F-007 | TF-05 | REQ-F-024 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ThermalThrottlingController`, `ProcessorState` | `src/thermal_throttling_controller.py` | TC-THR-001 | Design zugeordnet |
| LH-F-008 | TF-05 | REQ-F-025 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/thermal_throttling_controller.py` | TC-THR-002 | Design zugeordnet |
| LH-F-008 | TF-05 | REQ-F-026 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/thermal_throttling_controller.py` | TC-THR-003 | Design zugeordnet |
| LH-F-008 | TF-05 | REQ-F-027 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/thermal_throttling_controller.py` | TC-THR-004 | Design zugeordnet |
| LH-F-007 | TF-05 | REQ-F-028 | TF-05 | 3 | Funktionstest | CMP-005, CMP-009 | `ThermalThrottlingController`, `UserInterface` | IF-006, IF-009 | `ProcessorState`, `SimulationResult` | `src/thermal_throttling_controller.py`, `src/user_interface.py` | TC-THR-005 | Design zugeordnet |
| LH-F-007 | TF-05 | REQ-F-029 | TF-05 | 3 | Integrationstest | CMP-005, CMP-004, CMP-008 | `ThermalThrottlingController`, `TemperatureSimulator`, `PerformanceController` | IF-005, IF-006 | `ProcessorState`, `SimulationResult` | `src/thermal_throttling_controller.py`, `src/performance_controller.py` | TC-INT-003 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-030 | TF-07 | 3 | Grenzwerttest | CMP-007, CMP-005 | `EventLogger`, `ThermalThrottlingController` | IF-006, IF-008 | `LogEntry`, `ProcessorState` | `src/event_logger.py`, `src/thermal_throttling_controller.py` | TC-LOG-005 | Design zugeordnet |
| LH-F-010 | TF-07 | REQ-F-031 | TF-07 | 3 | Integrationstest | CMP-007, CMP-005, CMP-008 | `EventLogger`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-008 | `LogEntry`, `ProcessorState` | `src/event_logger.py`, `src/performance_controller.py` | TC-LOG-006 | Design zugeordnet |
| LH-F-011 | TF-07 | REQ-F-032 | TF-07 | 3 | Funktionstest | CMP-007, CMP-009 | `EventLogger`, `UserInterface` | IF-008, IF-009 | `LogEntry`, `SimulationResult` | `src/event_logger.py`, `src/user_interface.py` | TC-LOG-007 | Design zugeordnet |
| LH-F-011 | TF-07 | REQ-F-033 | TF-07 | 3 | Integrationstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-001, IF-008 | `EventLogger`, `ProcessorState`, `SimulationResult` | `src/event_logger.py`, `src/performance_controller.py` | TC-LOG-008 | Design zugeordnet |

---

## 5. Traceability Matrix – Nicht-funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | Kategorie | Iteration | Verifikation | CMP-ID / Artefakt | Komponente / Artefakt | IF-ID | Klasse / Datenmodell | Geplantes Code-Modul / Artefakt | Geplanter Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-NF-002 | PH-NF-003 | REQ-NF-001 | Determinismus | 1 | Unit-Test / Wiederholungstest | CMP-008, CMP-003 | `PerformanceController`, `ClockCalculator` | IF-001, IF-004, IF-009 | `ProcessorState`, `SimulationResult` | `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`, `src/model/ProcessorState.java`, `src/model/SimulationResult.java` | TST-I1-015 | Implementiert und getestet |
| LH-NF-005 | PH-NF-006 | REQ-NF-002 | Testbarkeit | 1 | Unit-Test, Traceability-Review | Projektartefakt | Traceability Matrix, Testfälle, Unit-TestRunner | alle relevanten IFs | alle relevanten Klassen | `Projekt/Iteration 1/Test cases/Iteration1_Testfaelle.md`, `Projekt/Iteration 1/test/Iteration1UnitTestRunner.java`, `Projekt/Iteration 1/Traceability Matrix.md` | TST-I1-016, TST-I1-001 bis TST-I1-020 | Implementiert und getestet |
| LH-NF-003 | PH-NF-004 | REQ-NF-003 | Robustheit | 1 | Unit-Test, Negativtest, manuelle Validierung | CMP-009, CMP-008, CMP-001, CMP-002 | `UserInterface`, `PerformanceController`, Manager-Komponenten | IF-001, IF-002, IF-003 | `SimulationInput` | `src/ui/UserInterface.java`, `src/controller/PerformanceController.java`, `src/service/PerformanceModeManager.java`, `src/service/LoadScenarioManager.java`, `src/model/SimulationInput.java` | TST-I1-017, TST-I1-018, TST-I1-019, TST-I1-020 | Implementiert und getestet |
| LH-NF-006 | PH-NF-007 | REQ-NF-004 | Konfigurationsmanagement | 1 | Baseline-Review | Projektartefakt | Git-Branch, Baseline, Dokumentation | nicht zutreffend | nicht zutreffend | Git-Branch je Iteration, Dokumentationsstand | Baseline-Prüfung nach Commit/Tag | Offen bis Baseline |
| nicht direkt zugeordnet | PH-NF-003 | REQ-NF-005 | Performance | 2 | Laufzeittest | CMP-008 | `PerformanceController` | IF-001 bis IF-009 | `SimulationResult` | `src/performance_controller.py` | TC-NF-PERF-001 | Design zugeordnet |
| LH-NF-004 | PH-NF-006 | REQ-NF-006 | Ausgabequalität | 2 | Funktionstest | CMP-009, CMP-008 | `UserInterface`, `PerformanceController` | IF-009 | `SimulationResult`, `ProcessorState` | `src/user_interface.py`, `src/simulation_result.py` | TC-NF-UNIT-001 | Design zugeordnet |
| LH-NF-005 | PH-NF-006 | REQ-NF-007 | Änderbarkeit / Traceability | 3 | Traceability-Review | Projektartefakt | Requirements, Architektur, Tests, Traceability Matrix | alle relevanten IFs | alle relevanten Klassen | `Projekt/Traceability/Traceability_Matrix.md` | TC-NF-CHG-001 | Design zugeordnet |

---

## 6. Testfall-ID-Konzept

| Präfix | Bedeutung |
|---|---|
| TC-PMM | Tests für `PerformanceModeManager` |
| TC-LSM | Tests für `LoadScenarioManager` |
| TC-CLK | Tests für `ClockCalculator` |
| TC-TMP | Tests für `TemperatureSimulator` |
| TC-THR | Tests für `ThermalThrottlingController` |
| TC-PWR | Tests für `PowerConsumptionCalculator` |
| TC-LOG | Tests für `EventLogger` |
| TC-INT | Integrationstests über mehrere Komponenten |
| TC-NF | Tests und Reviews für nicht-funktionale Requirements |
| TST-I1 | Tatsächlich ausgeführte Testfälle der Iteration 1 |

---

## 7. Aktueller Status der Traceability

| Bereich | Status |
|---|---|
| Lastenheft zu Requirements | vorhanden |
| Pflichtenheft zu Requirements | vorhanden |
| Requirements zu Teilfunktionalitäten | vorhanden |
| Requirements zu Iterationen | vorhanden |
| Requirements zu Komponenten | ergänzt in Version 0.2 |
| Requirements zu Schnittstellen | ergänzt in Version 0.2 |
| Requirements zu Klassen/Datenmodellen | ergänzt in Version 0.2 |
| Requirements zu Code-Modulen | für Iteration 1 konkret umgesetzt; Iteration 2 und 3 geplant |
| Requirements zu Testfällen | für Iteration 1 konkret durchgeführt; Iteration 2 und 3 geplant |
| Requirements zu Testergebnissen | für Iteration 1 vorhanden; Iteration 2 und 3 offen |