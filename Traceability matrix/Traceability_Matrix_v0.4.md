# Traceability Matrix

## 1. Zweck dieses Dokuments

Diese Traceability Matrix dokumentiert die Nachverfolgbarkeit der Anforderungen des Laptop-Prozessorleistungsmoduls über die relevanten Projektartefakte hinweg.

Die Matrix verbindet:

```text
Lastenheft → Pflichtenheft → Requirement → Teilfunktionalität → Iteration → Architekturkomponente → Schnittstelle → Klasse/Datenmodell → Code-Modul → Testfall
```

Für Iteration 1 wurden Implementierung, Unit-Tests, manuelle Validierung und Test Reporting durchgeführt. Die Iteration-1-Requirements sind daher mit konkreten Java-Code-Dateien, konkreten Testfällen und dem Status `Implementiert und getestet` versehen.

Für Iteration 2 ist diese Version als Startbaseline vorbereitet. Das bedeutet: Die fachlichen Requirements der Iteration 2 sind vollständig auf Komponenten, Schnittstellen, geplante Java-Code-Dateien und geplante Testfälle abgebildet. Der Status lautet deshalb `Startbereit für Iteration 2`, solange Implementierung und Testausführung noch nicht abgeschlossen sind.

---

## 2. Startbaseline Iteration 2

### 2.1 In Iteration 2 eingeplanter Umfang

| Bereich | Inhalt |
|---|---|
| Neue Teilfunktionalitäten | TF-04 Temperaturberechnung, TF-06 Stromverbrauch, erste Version TF-07 Ereignisprotokoll |
| Funktionale Requirements | REQ-F-013 bis REQ-F-018, REQ-F-020 bis REQ-F-023 |
| Nicht-funktionale Requirements | REQ-NF-005, REQ-NF-006 sowie Regression zu REQ-NF-001 bis REQ-NF-004 |
| Neue / erweiterte Komponenten | CMP-004 `TemperatureSimulator`, CMP-006 `PowerConsumptionCalculator`, CMP-007 `EventLogger`, Erweiterungen an CMP-008 `PerformanceController` und CMP-009 `UserInterface` |
| Neue / erweiterte Datenmodelle | `CoolingLevel`, Erweiterung `SimulationInput`, Erweiterung `ProcessorState`, Erweiterung `SimulationResult`, `LogEntry` |
| Geplante Testfälle | TST-I2-001 bis TST-I2-013 |
| Status der Matrix | Startbereit für Iteration 2 |

### 2.2 Bewusste Abgrenzung zu Iteration 3

REQ-F-019 bezieht sich auf eine Änderung der effektiven CPU-Taktfrequenz durch Thermal Throttling. Da Thermal Throttling als TF-05 erst in Iteration 3 umgesetzt wird, wird REQ-F-019 in dieser Matrix ebenfalls Iteration 3 zugeordnet. In Iteration 2 wird der Stromverbrauch bereits grundsätzlich berechnet; die spätere Neuberechnung nach Throttling bleibt für Iteration 3 vorbereitet.

---

## 3. Legende

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
| Geplantes Code-Modul | Konkrete oder geplante Code-Zuordnung |
| Geplanter Testfall | Testfall-ID für die Testdokumentation |
| Status | Aktueller Traceability-Status |

---

## 4. Änderungen gegenüber Version 0.3

| Änderung | Beschreibung |
|---|---|
| Status angepasst | Von allgemeinem Entwurf zu `Iteration-2-Startbaseline`. |
| Iteration-2-Scope geklärt | REQ-F-013 bis REQ-F-018 und REQ-F-020 bis REQ-F-023 sind startbereit eingeplant. |
| REQ-F-019 abgegrenzt | REQ-F-019 wird wegen Abhängigkeit zu Thermal Throttling in Iteration 3 vorbereitet. |
| Code-Pfade vereinheitlicht | Geplante Code-Module wurden auf Java-Dateipfade umgestellt und an die Iteration-1-Struktur angepasst. |
| Testfall-IDs vereinheitlicht | Iteration-2-Testfälle verwenden das Präfix `TST-I2`. |
| Regression ergänzt | Rückversicherung der Iteration-1-Funktionalität ist als TST-I2-012 geplant. |
| NF-Requirements präzisiert | REQ-NF-005 und REQ-NF-006 sind Iteration 2 startbereit zugeordnet. |

---

## 5. Traceability Matrix – Funktionale Requirements

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
| LH-F-005 | TF-04 | REQ-F-013 | TF-04 | 2 | Review, Funktionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-001, IF-005 | `SimulationInput`, `CoolingLevel`, `ProcessorState` | `src/model/CoolingLevel.java`, `src/model/SimulationInput.java`, `src/service/TemperatureSimulator.java`, `src/ui/UserInterface.java` | TST-I2-001 | Startbereit für Iteration 2 |
| LH-F-005 | TF-04 | REQ-F-014 | TF-04 | 2 | Funktionstest | CMP-004, CMP-008 | `TemperatureSimulator`, `PerformanceController` | IF-001, IF-005 | `SimulationInput`, `ProcessorState` | `src/model/SimulationInput.java`, `src/controller/PerformanceController.java`, `src/service/TemperatureSimulator.java` | TST-I2-002 | Startbereit für Iteration 2 |
| LH-F-006 | TF-04 | REQ-F-015 | TF-04 | 2 | Funktionstest, Grenzwerttest | CMP-004 | `TemperatureSimulator` | IF-005 | `TemperatureSimulator`, `ProcessorState`, `CoolingLevel` | `src/service/TemperatureSimulator.java`, `src/model/ProcessorState.java`, `src/model/CoolingLevel.java` | TST-I2-003 | Startbereit für Iteration 2 |
| LH-F-006 | TF-04 | REQ-F-016 | TF-04 | 2 | Funktionstest | CMP-004, CMP-009 | `TemperatureSimulator`, `UserInterface` | IF-005, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/TemperatureSimulator.java`, `src/model/SimulationResult.java`, `src/ui/UserInterface.java` | TST-I2-004 | Startbereit für Iteration 2 |
| LH-F-009 | TF-06 | REQ-F-017 | TF-06 | 2 | Funktionstest, Grenzwerttest | CMP-006 | `PowerConsumptionCalculator` | IF-007 | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceMode` | `src/service/PowerConsumptionCalculator.java`, `src/model/ProcessorState.java`, `src/model/PerformanceMode.java` | TST-I2-005 | Startbereit für Iteration 2 |
| LH-F-009 | TF-06 | REQ-F-018 | TF-06 | 2 | Funktionstest | CMP-006, CMP-009 | `PowerConsumptionCalculator`, `UserInterface` | IF-007, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/PowerConsumptionCalculator.java`, `src/model/SimulationResult.java`, `src/ui/UserInterface.java` | TST-I2-006 | Startbereit für Iteration 2 |
| LH-F-010 | TF-07 | REQ-F-020 | TF-07 | 2 | Funktionstest | CMP-007, CMP-001, CMP-008 | `EventLogger`, `PerformanceModeManager`, `PerformanceController` | IF-002, IF-008 | `EventLogger`, `LogEntry`, `PerformanceMode` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java` | TST-I2-007 | Startbereit für Iteration 2 |
| LH-F-010 | TF-07 | REQ-F-021 | TF-07 | 2 | Funktionstest | CMP-007, CMP-002, CMP-008 | `EventLogger`, `LoadScenarioManager`, `PerformanceController` | IF-003, IF-008 | `EventLogger`, `LogEntry`, `LoadScenario` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java` | TST-I2-008 | Startbereit für Iteration 2 |
| LH-F-010 | TF-07 | REQ-F-022 | TF-07 | 2 | Grenzwerttest | CMP-007, CMP-004 | `EventLogger`, `TemperatureSimulator` | IF-005, IF-008 | `EventLogger`, `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/service/TemperatureSimulator.java` | TST-I2-009 | Startbereit für Iteration 2 |
| LH-F-010 | TF-07 | REQ-F-023 | TF-07 | 2 | Review, Funktionstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-008 | `LogEntry`, `ProcessorState`, `SimulationResult` | `src/service/EventLogger.java`, `src/model/LogEntry.java`, `src/controller/PerformanceController.java`, `src/model/SimulationResult.java` | TST-I2-010 | Startbereit für Iteration 2 |
| LH-F-009 | TF-06 | REQ-F-019 | TF-06 | 3 | Integrationstest | CMP-006, CMP-005, CMP-008 | `PowerConsumptionCalculator`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-007 | `ProcessorState`, `SimulationResult` | `src/service/PowerConsumptionCalculator.java`, `src/service/ThermalThrottlingController.java`, `src/controller/PerformanceController.java` | TST-I3-001 | Für Iteration 3 vorbereitet; aus Iteration 2 ausgeklammert, da abhängig von TF-05 |
| LH-F-007 | TF-05 | REQ-F-024 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ThermalThrottlingController`, `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TST-I3-002 | Für Iteration 3 vorbereitet |
| LH-F-008 | TF-05 | REQ-F-025 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TST-I3-003 | Für Iteration 3 vorbereitet |
| LH-F-008 | TF-05 | REQ-F-026 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TST-I3-004 | Für Iteration 3 vorbereitet |
| LH-F-008 | TF-05 | REQ-F-027 | TF-05 | 3 | Grenzwerttest | CMP-005 | `ThermalThrottlingController` | IF-006 | `ProcessorState` | `src/service/ThermalThrottlingController.java`, `src/model/ProcessorState.java` | TST-I3-005 | Für Iteration 3 vorbereitet |
| LH-F-007 | TF-05 | REQ-F-028 | TF-05 | 3 | Funktionstest | CMP-005, CMP-009 | `ThermalThrottlingController`, `UserInterface` | IF-006, IF-009 | `ProcessorState`, `SimulationResult` | `src/service/ThermalThrottlingController.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java` | TST-I3-006 | Für Iteration 3 vorbereitet |
| LH-F-007 | TF-05 | REQ-F-029 | TF-05 | 3 | Integrationstest | CMP-005, CMP-004, CMP-008 | `ThermalThrottlingController`, `TemperatureSimulator`, `PerformanceController` | IF-005, IF-006 | `ProcessorState`, `SimulationResult` | `src/service/ThermalThrottlingController.java`, `src/service/TemperatureSimulator.java`, `src/controller/PerformanceController.java` | TST-I3-007 | Für Iteration 3 vorbereitet |
| LH-F-010 | TF-07 | REQ-F-030 | TF-07 | 3 | Grenzwerttest | CMP-007, CMP-005 | `EventLogger`, `ThermalThrottlingController` | IF-006, IF-008 | `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/service/ThermalThrottlingController.java`, `src/model/LogEntry.java` | TST-I3-008 | Für Iteration 3 vorbereitet |
| LH-F-010 | TF-07 | REQ-F-031 | TF-07 | 3 | Integrationstest | CMP-007, CMP-005, CMP-008 | `EventLogger`, `ThermalThrottlingController`, `PerformanceController` | IF-006, IF-008 | `LogEntry`, `ProcessorState` | `src/service/EventLogger.java`, `src/service/ThermalThrottlingController.java`, `src/controller/PerformanceController.java` | TST-I3-009 | Für Iteration 3 vorbereitet |
| LH-F-011 | TF-07 | REQ-F-032 | TF-07 | 3 | Funktionstest | CMP-007, CMP-009 | `EventLogger`, `UserInterface` | IF-008, IF-009 | `LogEntry`, `SimulationResult` | `src/service/EventLogger.java`, `src/ui/UserInterface.java`, `src/model/SimulationResult.java` | TST-I3-010 | Für Iteration 3 vorbereitet |
| LH-F-011 | TF-07 | REQ-F-033 | TF-07 | 3 | Integrationstest | CMP-007, CMP-008 | `EventLogger`, `PerformanceController` | IF-001, IF-008 | `EventLogger`, `ProcessorState`, `SimulationResult` | `src/service/EventLogger.java`, `src/controller/PerformanceController.java`, `src/model/SimulationResult.java` | TST-I3-011 | Für Iteration 3 vorbereitet |

---

## 6. Traceability Matrix – Nicht-funktionale Requirements

| LH-ID | PH-Bezug | REQ-ID | Kategorie | Iteration | Verifikation | CMP-ID / Artefakt | Komponente / Artefakt | IF-ID | Klasse / Datenmodell | Geplantes Code-Modul / Artefakt | Geplanter Testfall | Status |
|---|---|---|---|---:|---|---|---|---|---|---|---|---|
| LH-NF-002 | PH-NF-003 | REQ-NF-001 | Determinismus | 1 / Regression 2 | Unit-Test, Wiederholungstest, Regressionstest | CMP-008, CMP-003, CMP-004, CMP-006 | `PerformanceController`, `ClockCalculator`, `TemperatureSimulator`, `PowerConsumptionCalculator` | IF-001, IF-004, IF-005, IF-007, IF-009 | `ProcessorState`, `SimulationResult` | I1: `src/controller/PerformanceController.java`, `src/service/ClockCalculator.java`; I2 geplant: `src/service/TemperatureSimulator.java`, `src/service/PowerConsumptionCalculator.java` | TST-I1-015, TST-I2-011, TST-I2-012 | I1 erfüllt; I2 Regression startbereit |
| LH-NF-005 | PH-NF-006 | REQ-NF-002 | Testbarkeit | 1 / 2 | Traceability-Review | Projektartefakt | Traceability Matrix, Testfälle, Test Reporting | alle relevanten IFs | alle relevanten Klassen | `Projekt/Iteration 1/Test cases/Iteration1_Testfaelle.md`, `Projekt/Iteration 2/Test cases/Iteration2_Testfaelle.md`, `Projekt/Traceability/Traceability_Matrix.md` | TST-I1-001 bis TST-I1-020, TST-I2-001 bis TST-I2-012 | I1 erfüllt; I2-Testfälle vorbereitet |
| LH-NF-003 | PH-NF-004 | REQ-NF-003 | Robustheit | 1 / Regression 2 | Unit-Test, Negativtest, manuelle Validierung | CMP-009, CMP-008, CMP-001, CMP-002, CMP-004, CMP-006 | `UserInterface`, `PerformanceController`, Manager- und Service-Komponenten | IF-001 bis IF-007 | `SimulationInput`, `ProcessorState` | I1-Code plus geplante I2-Services | TST-I1-017, TST-I1-018, TST-I1-019, TST-I1-020, TST-I2-012 | I1 erfüllt; I2 Regression startbereit |
| LH-NF-006 | PH-NF-007 | REQ-NF-004 | Konfigurationsmanagement | 1 / 2 | Baseline-Review | Projektartefakt | Git-Branch, Baseline, Dokumentation | nicht zutreffend | nicht zutreffend | Branch `iteration-1`, Branch `iteration-2`, Dokumentationsstand, Test Reporting | Baseline-Prüfung Iteration 1, Baseline-Prüfung Iteration 2 | I1-Baseline vorhanden; I2-Baseline offen bis Abschluss |
| nicht direkt zugeordnet | PH-NF-003 | REQ-NF-005 | Performance | 2 | Laufzeittest | CMP-008 | `PerformanceController` | IF-001 bis IF-009 | `SimulationResult` | `src/controller/PerformanceController.java`, `src/service/TemperatureSimulator.java`, `src/service/PowerConsumptionCalculator.java` | TST-I2-013 | Startbereit für Iteration 2 |
| LH-NF-004 | PH-NF-006 | REQ-NF-006 | Ausgabequalität | 2 | Funktionstest | CMP-009, CMP-008 | `UserInterface`, `PerformanceController` | IF-009 | `SimulationResult`, `ProcessorState` | `src/ui/UserInterface.java`, `src/model/SimulationResult.java`, `src/model/ProcessorState.java` | TST-I2-004, TST-I2-006 | Startbereit für Iteration 2 |
| LH-NF-005 | PH-NF-006 | REQ-NF-007 | Änderbarkeit / Traceability | 3 | Traceability-Review | Projektartefakt | Requirements, Architektur, Tests, Traceability Matrix | alle relevanten IFs | alle relevanten Klassen | `Projekt/Traceability/Traceability_Matrix.md` | TST-I3-012 | Für Iteration 3 vorbereitet |
---

## 7. Geplante Testfälle für Iteration 2

| Testfall-ID | Zugeordnete Requirements | Testart | Testziel | Status |
|---|---|---|---|---|
| TST-I2-001 | REQ-F-013 | Review, Funktionstest | Prüfen, ob die Kühlungsstufen `Niedrig`, `Mittel` und `Hoch` bereitgestellt werden. | geplant |
| TST-I2-002 | REQ-F-014 | Funktionstest | Prüfen, ob die ausgewählte Kühlungsstufe in `SimulationInput` übernommen wird. | geplant |
| TST-I2-003 | REQ-F-015 | Funktionstest, Grenzwerttest | Prüfen, ob `TemperatureSimulator` Temperaturen im Bereich 35 °C bis 100 °C bestimmt. | geplant |
| TST-I2-004 | REQ-F-016, REQ-NF-006 | Funktionstest | Prüfen, ob die CPU-Temperatur mit Einheit `°C` ausgegeben wird. | geplant |
| TST-I2-005 | REQ-F-017 | Funktionstest, Grenzwerttest | Prüfen, ob `PowerConsumptionCalculator` Werte im Bereich 8 W bis 65 W bestimmt. | geplant |
| TST-I2-006 | REQ-F-018, REQ-NF-006 | Funktionstest | Prüfen, ob der Stromverbrauch mit Einheit `W` ausgegeben wird. | geplant |
| TST-I2-007 | REQ-F-020 | Funktionstest | Prüfen, ob bei Änderung des Leistungsmodus ein Logeintrag `MODUSWECHSEL` erzeugt wird. | geplant |
| TST-I2-008 | REQ-F-021 | Funktionstest | Prüfen, ob bei Änderung des Lastszenarios ein Logeintrag `LASTSZENARIOWECHSEL` erzeugt wird. | geplant |
| TST-I2-009 | REQ-F-022 | Grenzwerttest | Prüfen, ob ab 70 °C ein Logeintrag `TEMPERATURWARNUNG` erzeugt wird. | geplant |
| TST-I2-010 | REQ-F-023 | Review, Funktionstest | Prüfen, ob ein `LogEntry` alle geforderten Pflichtfelder enthält. | geplant |
| TST-I2-011 | REQ-F-013 bis REQ-F-018, REQ-F-020 bis REQ-F-023 | Integrationstest | Prüfen, ob ein vollständiger Iteration-2-Simulationslauf Temperatur, Stromverbrauch und erste Ereignisse liefert. | geplant |
| TST-I2-012 | REQ-F-001 bis REQ-F-012, REQ-NF-001 bis REQ-NF-004 | Regressionstest | Prüfen, ob die Iteration-1-Funktionen nach Erweiterung der Architektur unverändert funktionieren. | geplant |
| TST-I2-013 | REQ-NF-005 | Laufzeittest | Prüfen, ob ein vollständiger Simulationslauf innerhalb von höchstens 5 Sekunden ein Ergebnis liefert. | geplant |

---

## 8. Testfall-ID-Konzept

| Präfix | Bedeutung |
|---|---|
| TST-I1 | Tatsächlich ausgeführte Testfälle der Iteration 1 |
| TST-I2 | Geplante Testfälle der Iteration 2 |
| TST-I3 | Geplante Testfälle der Iteration 3 |
| TC-PMM | Tests für `PerformanceModeManager` |
| TC-LSM | Tests für `LoadScenarioManager` |
| TC-CLK | Tests für `ClockCalculator` |
| TC-TMP | Tests für `TemperatureSimulator` |
| TC-THR | Tests für `ThermalThrottlingController` |
| TC-PWR | Tests für `PowerConsumptionCalculator` |
| TC-LOG | Tests für `EventLogger` |
| TC-INT | Integrationstests über mehrere Komponenten |
| TC-NF | Tests und Reviews für nicht-funktionale Requirements |

---

## 9. Aktueller Status der Traceability

| Bereich | Status |
|---|---|
| Lastenheft zu Requirements | vorhanden |
| Pflichtenheft zu Requirements | vorhanden |
| Requirements zu Teilfunktionalitäten | vorhanden |
| Requirements zu Iterationen | vorhanden |
| Requirements zu Komponenten | vorhanden |
| Requirements zu Schnittstellen | vorhanden |
| Requirements zu Klassen/Datenmodellen | vorhanden |
| Requirements zu Code-Modulen | Iteration 1 konkret; Iteration 2 geplant und Java-konform vorbereitet; Iteration 3 vorbereitet |
| Requirements zu Testfällen | Iteration 1 konkret durchgeführt; Iteration 2 geplant; Iteration 3 vorbereitet |
| Requirements zu Testergebnissen | Iteration 1 vorhanden; Iteration 2 und 3 offen |
| Regression Iteration 1 in Iteration 2 | eingeplant über TST-I2-012 |
| Baseline Iteration 2 | Startbaseline erstellt; Abschlussbaseline nach Implementierung und Testausführung erforderlich |
