# Iteration 2 – Testdokumentation / Testfälle

## 1. Zweck der Testdokumentation

Diese Testdokumentation beschreibt die geplanten und ausgeführten Tests für Iteration 2 des Laptop-Prozessorleistungsmoduls.

Ziel ist es, die in Iteration 2 ergänzten Funktionen systematisch zu prüfen und die Nachverfolgbarkeit zwischen Requirements, Komponenten, Implementierung und Testfällen vorzubereiten.

In Iteration 2 werden geprüft:

* Bereitstellung und Übernahme von Kühlungsstufen,
* Berechnung der CPU-Temperatur,
* Begrenzung der CPU-Temperatur auf 35 °C bis 100 °C,
* Ausgabe der CPU-Temperatur mit Einheit °C,
* Berechnung des Stromverbrauchs,
* Begrenzung des Stromverbrauchs auf 8 W bis 65 W,
* Ausgabe des Stromverbrauchs mit Einheit W,
* einfache Ereignisprotokollierung,
* Speicherung relevanter Ereignisdaten,
* vollständiger Integrationsablauf der erweiterten Simulation,
* Rückwärtskompatibilität zu Iteration 1,
* Laufzeitforderung von höchstens 5 Sekunden.

Nicht geprüft wird vollständiges Thermal Throttling, da diese Funktion nicht Bestandteil von Iteration 2 ist.

---

## 2. Testgrundlage

Die Tests beziehen sich auf folgende Requirements der Iteration 2:

| Requirement-ID | Teilfunktionalität | Kurzbeschreibung                                                     |
| -------------- | ------------------ | -------------------------------------------------------------------- |
| REQ-F-013      | TF-04              | Kühlungsstufen bereitstellen                                         |
| REQ-F-014      | TF-04              | ausgewählte Kühlungsstufe übernehmen                                 |
| REQ-F-015      | TF-04              | CPU-Temperatur im Bereich von 35 °C bis 100 °C bestimmen             |
| REQ-F-016      | TF-04              | CPU-Temperatur mit Einheit °C ausgeben                               |
| REQ-F-017      | TF-06              | Stromverbrauch im Bereich von 8 W bis 65 W bestimmen                 |
| REQ-F-018      | TF-06              | Stromverbrauch mit Einheit W ausgeben                                |
| REQ-F-019      | TF-06              | Stromverbrauch nach Thermal Throttling neu bestimmen                 |
| REQ-F-020      | TF-07              | Moduswechsel protokollieren                                          |
| REQ-F-021      | TF-07              | Lastszenario-Wechsel protokollieren                                  |
| REQ-F-022      | TF-07              | Temperaturwarnung ab mindestens 70 °C protokollieren                 |
| REQ-F-023      | TF-07              | Ereigniseintrag mit definierten Feldern speichern                    |
| REQ-NF-005     | Performance        | Simulationsergebnis innerhalb von höchstens 5 Sekunden bereitstellen |
| REQ-NF-006     | Ausgabequalität    | numerische Simulationswerte mit Wert und Einheit anzeigen            |

REQ-F-019 wird in Iteration 2 nur vorbereitet. Die vollständige Verifikation erfolgt in Iteration 3, da Thermal Throttling erst dort umgesetzt wird.

---

## 3. Testobjekte

| Komponenten-ID | Komponente / Klasse          | Testrelevanz                                   |
| -------------- | ---------------------------- | ---------------------------------------------- |
| CMP-009        | `UserInterface`              | Anzeige der neuen Eingabe- und Ergebniswerte   |
| CMP-008        | `PerformanceController`      | Integration des erweiterten Simulationsablaufs |
| CMP-004        | `TemperatureSimulator`       | Kühlungsstufen und Temperaturberechnung        |
| CMP-006        | `PowerConsumptionCalculator` | Stromverbrauchsberechnung                      |
| CMP-007        | `EventLogger`                | einfache Ereignisprotokollierung               |
| CMP-003        | `ClockCalculator`            | Regression der bestehenden Taktberechnung      |
| CMP-001        | `PerformanceModeManager`     | Regression der bestehenden Leistungsmoduslogik |
| CMP-002        | `LoadScenarioManager`        | Regression der bestehenden Lastszenariologik   |
| Datenmodell    | `SimulationInput`            | Erweiterung um Kühlungsstufe                   |
| Datenmodell    | `ProcessorState`             | Erweiterung um Temperatur und Stromverbrauch   |
| Datenmodell    | `SimulationResult`           | Erweiterung der Ergebnisdarstellung            |
| Datenmodell    | `CoolingLevel`               | Datenmodell für Kühlungsstufen                 |
| Datenmodell    | `LogEntry`                   | Datenmodell für Ereignisprotokolleinträge      |

---

## 4. Teststrategie für Iteration 2

Für Iteration 2 werden statische und dynamische Tests kombiniert.

### 4.1 Statischer Test

Der statische Test erfolgt als Review der Implementierung und der Dokumentation.

Ziele des statischen Tests:

* prüfen, ob die Erweiterung zum Feindesign passt,
* prüfen, ob bestehende Iteration-1-Klassen nicht unnötig verändert wurden,
* prüfen, ob neue Klassen den geplanten Verantwortlichkeiten entsprechen,
* prüfen, ob Thermal Throttling nicht versehentlich vollständig umgesetzt wurde,
* prüfen, ob Wertebereiche, Einheiten und Ereignistypen konsistent verwendet werden.

### 4.2 Dynamischer Test

Der dynamische Test erfolgt durch Ausführung der Datei:

```text
test/Iteration2UnitTestRunner.java
```

Ziele des dynamischen Tests:

* Prüfung der neuen Berechnungslogik,
* Prüfung der neuen Datenmodelle,
* Prüfung der Ereignisprotokollierung,
* Prüfung des Zusammenspiels zwischen Controller und Services,
* Prüfung der Rückwärtskompatibilität zu Iteration 1,
* Prüfung der Ausgabequalität,
* Prüfung der Performance-Anforderung.

---

## 5. Testphasen

| Testphase               | Beschreibung                                                          | Anwendung in Iteration 2                                                          |
| ----------------------- | --------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| Modultest               | Einzelne Klassen oder Methoden werden isoliert geprüft.               | `TemperatureSimulator`, `PowerConsumptionCalculator`, `EventLogger`, Datenklassen |
| Integrationstest        | Zusammenspiel mehrerer Komponenten wird geprüft.                      | `PerformanceController` mit Manager-, Calculator- und Logger-Klassen              |
| Regressionstest         | Bereits funktionierende Iteration-1-Funktionen werden erneut geprüft. | Konstruktoren, Taktberechnung, bestehender Simulationsablauf                      |
| Nicht-funktionaler Test | Qualitätsanforderungen werden geprüft.                                | Laufzeit und Ausgabe mit Einheiten                                                |
| Manuelle Validierung    | Anwendung wird aus Benutzersicht ausgeführt.                          | Start über `Main.java` und Konsoleneingabe                                        |

---

## 6. Testtechniken

| Testtechnik             | Einsatz in Iteration 2                                                                                          |
| ----------------------- | --------------------------------------------------------------------------------------------------------------- |
| Black-Box-Test          | Prüfung sichtbarer Eingabe-Ausgabe-Beziehungen, z. B. Kühlungsstufe, Temperatur, Stromverbrauch und Ereignisse. |
| Grenzwerttest           | Prüfung der Temperaturgrenzen 35 °C und 100 °C sowie Verbrauchsgrenzen 8 W und 65 W.                            |
| Negativtest             | Prüfung ungültiger Kühlungsstufen.                                                                              |
| Integrationstest        | Prüfung des erweiterten Simulationsablaufs über den `PerformanceController`.                                    |
| Regressionstest         | Prüfung, ob Iteration-1-Funktionen weiterhin korrekt funktionieren.                                             |
| Nicht-funktionaler Test | Prüfung der Laufzeitforderung und Ausgabequalität.                                                              |

---

## 7. Entscheidungstabelle für Iteration 2

| Regel | Leistungsmodus        | Lastszenario | Kühlungsstufe    | Erwartete Aktion                                                |
| ----- | --------------------- | ------------ | ---------------- | --------------------------------------------------------------- |
| R1    | gültiger fester Modus | gültig       | gültig           | Simulation wird vollständig ausgeführt.                         |
| R2    | gültiger fester Modus | gültig       | ungültig         | Simulation wird abgelehnt.                                      |
| R3    | `CUSTOM`              | gültig       | gültig           | Simulation wird mit benutzerdefiniertem Modus ausgeführt.       |
| R4    | gültiger fester Modus | hohe Last    | niedrige Kühlung | Temperaturwarnung kann protokolliert werden.                    |
| R5    | gültiger fester Modus | gültig       | hohe Kühlung     | Temperatur fällt niedriger aus als bei niedriger Kühlung.       |
| R6    | gültiger fester Modus | gültig       | gültig           | Stromverbrauch wird berechnet und begrenzt.                     |
| R7    | gültiger fester Modus | gültig       | gültig           | Ergebnis enthält Werte mit Einheiten.                           |
| R8    | gültiger fester Modus | gültig       | nicht angegeben  | Standardkühlung `MITTEL` wird für alte Konstruktoren verwendet. |

---

## 8. Testfallformat

| Feld                | Bedeutung                                                                    |
| ------------------- | ---------------------------------------------------------------------------- |
| Testfall-ID         | Eindeutige Kennung des Testfalls                                             |
| Requirement-ID      | Geprüfte Requirements                                                        |
| Testobjekt          | Zu testende Klasse oder Komponente                                           |
| Testphase           | Modultest, Integrationstest, Regressionstest oder nicht-funktionaler Test    |
| Testtechnik         | Black-Box, Grenzwerttest, Negativtest, Integrationstest oder Regressionstest |
| Vorbedingung        | Zustand vor Testausführung                                                   |
| Eingabe             | Eingabedaten des Testfalls                                                   |
| Testschritte        | Durchzuführende Schritte                                                     |
| Erwartetes Ergebnis | Erwartete Reaktion der Software                                              |
| Priorität           | P1, P2 oder P3                                                               |
| Status              | Ergebnis der Testausführung                                                  |

---

## 9. Testfälle Iteration 2

### TC-TMP-001 – Kühlungsstufen werden bereitgestellt

| Feld                | Inhalt                                            |
| ------------------- | ------------------------------------------------- |
| Requirement-ID      | REQ-F-013                                         |
| Testobjekt          | `TemperatureSimulator`, `CoolingLevel`            |
| Testphase           | Modultest                                         |
| Testtechnik         | Black-Box-Test                                    |
| Vorbedingung        | `TemperatureSimulator` ist instanziiert.          |
| Eingabe             | keine                                             |
| Testschritte        | `listCoolingLevels()` aufrufen.                   |
| Erwartetes Ergebnis | Die Liste enthält `NIEDRIG`, `MITTEL` und `HOCH`. |
| Priorität           | P1                                                |
| Status              | automatisiert                                     |

---

### TC-TMP-002 – Ausgewählte Kühlungsstufe wird übernommen

| Feld                | Inhalt                                                             |
| ------------------- | ------------------------------------------------------------------ |
| Requirement-ID      | REQ-F-014                                                          |
| Testobjekt          | `SimulationInput`, `PerformanceController`, `TemperatureSimulator` |
| Testphase           | Integrationstest                                                   |
| Testtechnik         | Black-Box-Test                                                     |
| Vorbedingung        | Vollständiger Controller ist instanziiert.                         |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `HOCH`                |
| Testschritte        | `runSimulation()` ausführen und `getCoolingLevel()` prüfen.        |
| Erwartetes Ergebnis | Im Ergebnis ist die Kühlungsstufe `HOCH` gespeichert.              |
| Priorität           | P1                                                                 |
| Status              | automatisiert                                                      |

---

### TC-TMP-NEG-001 – Ungültige Kühlungsstufe wird zurückgewiesen

| Feld                | Inhalt                                                                     |
| ------------------- | -------------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-014, REQ-NF-003                                                      |
| Testobjekt          | `TemperatureSimulator`, `PerformanceController`                            |
| Testphase           | Integrationstest                                                           |
| Testtechnik         | Negativtest                                                                |
| Vorbedingung        | Vollständiger Controller ist instanziiert.                                 |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `UNBEKANNT`                   |
| Testschritte        | `runSimulation()` ausführen.                                               |
| Erwartetes Ergebnis | Die Simulation wird mit `IllegalArgumentException` kontrolliert abgelehnt. |
| Priorität           | P1                                                                         |
| Status              | automatisiert                                                              |

---

### TC-TMP-003 – CPU-Temperatur wird berechnet

| Feld                | Inhalt                                                      |
| ------------------- | ----------------------------------------------------------- |
| Requirement-ID      | REQ-F-015                                                   |
| Testobjekt          | `TemperatureSimulator`, `ProcessorState`                    |
| Testphase           | Modultest                                                   |
| Testtechnik         | Black-Box-Test                                              |
| Vorbedingung        | `ProcessorState` enthält CPU-Auslastung und Taktfrequenz.   |
| Eingabe             | CPU-Auslastung 85 %, Taktfrequenz 4.0 GHz, Kühlung `MITTEL` |
| Testschritte        | `calculateTemperature()` aufrufen.                          |
| Erwartetes Ergebnis | Temperatur liegt im Bereich von 35 °C bis 100 °C.           |
| Priorität           | P1                                                          |
| Status              | automatisiert                                               |

---

### TC-TMP-003A – Temperatur wird auf Minimalwert begrenzt

| Feld                | Inhalt                                   |
| ------------------- | ---------------------------------------- |
| Requirement-ID      | REQ-F-015                                |
| Testobjekt          | `TemperatureSimulator`                   |
| Testphase           | Modultest                                |
| Testtechnik         | Grenzwerttest                            |
| Vorbedingung        | `TemperatureSimulator` ist instanziiert. |
| Eingabe             | Temperaturwert 20 °C                     |
| Testschritte        | `clampTemperature(20.0)` aufrufen.       |
| Erwartetes Ergebnis | Rückgabewert ist 35 °C.                  |
| Priorität           | P1                                       |
| Status              | automatisiert                            |

---

### TC-TMP-003B – Temperatur wird auf Maximalwert begrenzt

| Feld                | Inhalt                                   |
| ------------------- | ---------------------------------------- |
| Requirement-ID      | REQ-F-015                                |
| Testobjekt          | `TemperatureSimulator`                   |
| Testphase           | Modultest                                |
| Testtechnik         | Grenzwerttest                            |
| Vorbedingung        | `TemperatureSimulator` ist instanziiert. |
| Eingabe             | Temperaturwert 140 °C                    |
| Testschritte        | `clampTemperature(140.0)` aufrufen.      |
| Erwartetes Ergebnis | Rückgabewert ist 100 °C.                 |
| Priorität           | P1                                       |
| Status              | automatisiert                            |

---

### TC-TMP-003C – Höhere Kühlung reduziert Temperatur

| Feld                | Inhalt                                                                  |
| ------------------- | ----------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-015                                                               |
| Testobjekt          | `TemperatureSimulator`, `CoolingLevel`                                  |
| Testphase           | Modultest                                                               |
| Testtechnik         | Black-Box-Test                                                          |
| Vorbedingung        | `ProcessorState` enthält CPU-Auslastung und Taktfrequenz.               |
| Eingabe             | CPU-Auslastung 85 %, Taktfrequenz 4.0 GHz, Kühlung `NIEDRIG` und `HOCH` |
| Testschritte        | Temperatur für beide Kühlungsstufen berechnen.                          |
| Erwartetes Ergebnis | Temperatur bei `HOCH` ist niedriger als Temperatur bei `NIEDRIG`.       |
| Priorität           | P2                                                                      |
| Status              | automatisiert                                                           |

---

### TC-TMP-004 – CPU-Temperatur wird mit Einheit ausgegeben

| Feld                | Inhalt                                                |
| ------------------- | ----------------------------------------------------- |
| Requirement-ID      | REQ-F-016, REQ-NF-006                                 |
| Testobjekt          | `SimulationResult`                                    |
| Testphase           | Integrationstest                                      |
| Testtechnik         | Black-Box-Test                                        |
| Vorbedingung        | Vollständige Simulation wurde ausgeführt.             |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `MITTEL` |
| Testschritte        | `toDisplayText()` aufrufen.                           |
| Erwartetes Ergebnis | Ausgabetext enthält `°C`.                             |
| Priorität           | P1                                                    |
| Status              | automatisiert                                         |

---

### TC-PWR-001 – Stromverbrauch wird berechnet

| Feld                | Inhalt                                                               |
| ------------------- | -------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-017                                                            |
| Testobjekt          | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceMode`    |
| Testphase           | Modultest                                                            |
| Testtechnik         | Black-Box-Test                                                       |
| Vorbedingung        | Leistungsmodus und Prozessorzustand mit Taktfrequenz sind vorhanden. |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Taktfrequenz 2.0 GHz            |
| Testschritte        | `calculatePowerConsumption()` aufrufen.                              |
| Erwartetes Ergebnis | Stromverbrauch liegt im Bereich von 8 W bis 65 W.                    |
| Priorität           | P1                                                                   |
| Status              | automatisiert                                                        |

---

### TC-PWR-001A – Stromverbrauch wird auf Minimalwert begrenzt

| Feld                | Inhalt                                         |
| ------------------- | ---------------------------------------------- |
| Requirement-ID      | REQ-F-017                                      |
| Testobjekt          | `PowerConsumptionCalculator`                   |
| Testphase           | Modultest                                      |
| Testtechnik         | Grenzwerttest                                  |
| Vorbedingung        | `PowerConsumptionCalculator` ist instanziiert. |
| Eingabe             | Verbrauchswert 2 W                             |
| Testschritte        | `clampPowerConsumption(2.0)` aufrufen.         |
| Erwartetes Ergebnis | Rückgabewert ist 8 W.                          |
| Priorität           | P1                                             |
| Status              | automatisiert                                  |

---

### TC-PWR-001B – Stromverbrauch wird auf Maximalwert begrenzt

| Feld                | Inhalt                                         |
| ------------------- | ---------------------------------------------- |
| Requirement-ID      | REQ-F-017                                      |
| Testobjekt          | `PowerConsumptionCalculator`                   |
| Testphase           | Modultest                                      |
| Testtechnik         | Grenzwerttest                                  |
| Vorbedingung        | `PowerConsumptionCalculator` ist instanziiert. |
| Eingabe             | Verbrauchswert 100 W                           |
| Testschritte        | `clampPowerConsumption(100.0)` aufrufen.       |
| Erwartetes Ergebnis | Rückgabewert ist 65 W.                         |
| Priorität           | P1                                             |
| Status              | automatisiert                                  |

---

### TC-PWR-002 – Stromverbrauch wird mit Einheit ausgegeben

| Feld                | Inhalt                                                |
| ------------------- | ----------------------------------------------------- |
| Requirement-ID      | REQ-F-018, REQ-NF-006                                 |
| Testobjekt          | `SimulationResult`                                    |
| Testphase           | Integrationstest                                      |
| Testtechnik         | Black-Box-Test                                        |
| Vorbedingung        | Vollständige Simulation wurde ausgeführt.             |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `MITTEL` |
| Testschritte        | `toDisplayText()` aufrufen.                           |
| Erwartetes Ergebnis | Ausgabetext enthält `W`.                              |
| Priorität           | P1                                                    |
| Status              | automatisiert                                         |

---

### TC-LOG-001 – Moduswechsel wird protokolliert

| Feld                | Inhalt                                                |
| ------------------- | ----------------------------------------------------- |
| Requirement-ID      | REQ-F-020                                             |
| Testobjekt          | `EventLogger`, `LogEntry`                             |
| Testphase           | Integrationstest                                      |
| Testtechnik         | Black-Box-Test                                        |
| Vorbedingung        | Vollständige Simulation wurde ausgeführt.             |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `MITTEL` |
| Testschritte        | Ereignisliste prüfen.                                 |
| Erwartetes Ergebnis | Ereignisliste enthält `MODUSWECHSEL`.                 |
| Priorität           | P1                                                    |
| Status              | automatisiert                                         |

---

### TC-LOG-002 – Lastszenario-Wechsel wird protokolliert

| Feld                | Inhalt                                                |
| ------------------- | ----------------------------------------------------- |
| Requirement-ID      | REQ-F-021                                             |
| Testobjekt          | `EventLogger`, `LogEntry`                             |
| Testphase           | Integrationstest                                      |
| Testtechnik         | Black-Box-Test                                        |
| Vorbedingung        | Vollständige Simulation wurde ausgeführt.             |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `MITTEL` |
| Testschritte        | Ereignisliste prüfen.                                 |
| Erwartetes Ergebnis | Ereignisliste enthält `LASTSZENARIOWECHSEL`.          |
| Priorität           | P1                                                    |
| Status              | automatisiert                                         |

---

### TC-LOG-003 – Temperaturwarnung wird protokolliert

| Feld                | Inhalt                                                         |
| ------------------- | -------------------------------------------------------------- |
| Requirement-ID      | REQ-F-022                                                      |
| Testobjekt          | `EventLogger`, `LogEntry`, `TemperatureSimulator`              |
| Testphase           | Integrationstest                                               |
| Testtechnik         | Grenzwertnaher Black-Box-Test                                  |
| Vorbedingung        | Vollständiger Controller ist instanziiert.                     |
| Eingabe             | Modus `PERFORMANCE`, Szenario `STRESS_TEST`, Kühlung `NIEDRIG` |
| Testschritte        | Simulation ausführen und Ereignisliste prüfen.                 |
| Erwartetes Ergebnis | Ereignisliste enthält `TEMPERATURWARNUNG`.                     |
| Priorität           | P1                                                             |
| Status              | automatisiert                                                  |

---

### TC-LOG-004 – Ereigniseintrag enthält Simulationsdaten

| Feld                | Inhalt                                                                                                                                                              |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-023                                                                                                                                                           |
| Testobjekt          | `LogEntry`                                                                                                                                                          |
| Testphase           | Integrationstest                                                                                                                                                    |
| Testtechnik         | Black-Box-Test                                                                                                                                                      |
| Vorbedingung        | Simulation mit mindestens einem Ereignis wurde ausgeführt.                                                                                                          |
| Eingabe             | Modus `PERFORMANCE`, Szenario `STRESS_TEST`, Kühlung `NIEDRIG`                                                                                                      |
| Testschritte        | Ersten `LogEntry` auslesen und Felder prüfen.                                                                                                                       |
| Erwartetes Ergebnis | Ereigniszeitpunkt, Ereignistyp, Beschreibung, Leistungsmodus, Lastszenario, CPU-Auslastung, CPU-Taktfrequenz, CPU-Temperatur und Throttling-Zustand sind vorhanden. |
| Priorität           | P1                                                                                                                                                                  |
| Status              | automatisiert                                                                                                                                                       |

---

### REG-I1-001 – Alter `SimulationInput`-Konstruktor funktioniert weiterhin

| Feld                | Inhalt                                                                        |
| ------------------- | ----------------------------------------------------------------------------- |
| Requirement-ID      | REQ-NF-003, REQ-NF-004                                                        |
| Testobjekt          | `SimulationInput`, `PerformanceController`                                    |
| Testphase           | Regressionstest                                                               |
| Testtechnik         | Regressionstest                                                               |
| Vorbedingung        | Iteration-2-Controller ist instanziiert.                                      |
| Eingabe             | `new SimulationInput("BALANCED", "OFFICE")`                                   |
| Testschritte        | Simulation ausführen.                                                         |
| Erwartetes Ergebnis | Simulation funktioniert weiterhin und verwendet `MITTEL` als Standardkühlung. |
| Priorität           | P1                                                                            |
| Status              | automatisiert                                                                 |

---

### REG-I1-002 – Taktberechnung aus Iteration 1 funktioniert weiterhin

| Feld                | Inhalt                                                                                   |
| ------------------- | ---------------------------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-009, REQ-F-010, REQ-F-011                                                          |
| Testobjekt          | `ClockCalculator`                                                                        |
| Testphase           | Regressionstest                                                                          |
| Testtechnik         | Regressionstest                                                                          |
| Vorbedingung        | `ClockCalculator`, `PerformanceModeManager` und `LoadScenarioManager` sind instanziiert. |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`                                                      |
| Testschritte        | `calculateClock()` aufrufen.                                                             |
| Erwartetes Ergebnis | Taktfrequenz beträgt 2.0 GHz.                                                            |
| Priorität           | P1                                                                                       |
| Status              | automatisiert                                                                            |

---

### TC-INT-001 – Vollständiger Iteration-2-Simulationslauf erzeugt vollständiges Ergebnis

| Feld                | Inhalt                                                                                                                      |
| ------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-013 bis REQ-F-018, REQ-F-020 bis REQ-F-023                                                                            |
| Testobjekt          | `PerformanceController`, `SimulationResult`, `ProcessorState`                                                               |
| Testphase           | Integrationstest                                                                                                            |
| Testtechnik         | Integrationstest                                                                                                            |
| Vorbedingung        | Vollständiger Controller ist instanziiert.                                                                                  |
| Eingabe             | Modus `PERFORMANCE`, Szenario `GAMING`, Kühlung `HOCH`                                                                      |
| Testschritte        | Simulation ausführen und Ergebnisobjekt prüfen.                                                                             |
| Erwartetes Ergebnis | Ergebnis enthält Leistungsmodus, Lastszenario, Kühlungsstufe, Taktfrequenz, Temperatur, Stromverbrauch und Zusammenfassung. |
| Priorität           | P1                                                                                                                          |
| Status              | automatisiert                                                                                                               |

---

### TC-NF-PERF-001 – Simulation wird innerhalb von 5 Sekunden ausgeführt

| Feld                | Inhalt                                                         |
| ------------------- | -------------------------------------------------------------- |
| Requirement-ID      | REQ-NF-005                                                     |
| Testobjekt          | `PerformanceController`                                        |
| Testphase           | Nicht-funktionaler Test                                        |
| Testtechnik         | Laufzeittest                                                   |
| Vorbedingung        | Vollständiger Controller ist instanziiert.                     |
| Eingabe             | Modus `PERFORMANCE`, Szenario `STRESS_TEST`, Kühlung `NIEDRIG` |
| Testschritte        | Startzeit erfassen, Simulation ausführen, Endzeit erfassen.    |
| Erwartetes Ergebnis | Laufzeit beträgt höchstens 5 Sekunden.                         |
| Priorität           | P2                                                             |
| Status              | automatisiert                                                  |

---

### TC-NF-UNIT-001 – Numerische Werte werden mit Einheiten angezeigt

| Feld                | Inhalt                                                |
| ------------------- | ----------------------------------------------------- |
| Requirement-ID      | REQ-NF-006                                            |
| Testobjekt          | `SimulationResult`, `UserInterface`                   |
| Testphase           | Nicht-funktionaler Test                               |
| Testtechnik         | Black-Box-Test                                        |
| Vorbedingung        | Vollständige Simulation wurde ausgeführt.             |
| Eingabe             | Modus `BALANCED`, Szenario `OFFICE`, Kühlung `MITTEL` |
| Testschritte        | `toDisplayText()` aufrufen.                           |
| Erwartetes Ergebnis | Ausgabetext enthält `%`, `GHz`, `°C` und `W`.         |
| Priorität           | P1                                                    |
| Status              | automatisiert                                         |

---

## 10. Nicht automatisierter Testfall

### TC-MAN-001 – Manuelle Konsolenvalidierung

| Feld                | Inhalt                                                                                                                                |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| Requirement-ID      | REQ-F-013 bis REQ-F-018, REQ-F-020 bis REQ-F-023, REQ-NF-006                                                                          |
| Testobjekt          | `Main`, `UserInterface`, `PerformanceController`                                                                                      |
| Testphase           | Manuelle Validierung                                                                                                                  |
| Testtechnik         | Black-Box-Test aus Benutzersicht                                                                                                      |
| Vorbedingung        | Anwendung ist kompiliert.                                                                                                             |
| Eingabe             | Manuelle Konsoleneingabe eines gültigen Modus, Szenarios und einer Kühlungsstufe.                                                     |
| Testschritte        | Anwendung über `Main.java` starten, Werte eingeben, Ergebnis prüfen.                                                                  |
| Erwartetes Ergebnis | Anwendung zeigt CPU-Auslastung, Taktfrequenz, Kühlungsstufe, Temperatur, Stromverbrauch und Ereignisse verständlich mit Einheiten an. |
| Priorität           | P2                                                                                                                                    |
| Status              | manuell auszuführen                                                                                                                   |

---

## 11. Testabdeckung

| Bereich                        | Abgedeckte Testfälle                              |
| ------------------------------ | ------------------------------------------------- |
| Kühlungsstufen                 | TC-TMP-001, TC-TMP-002, TC-TMP-NEG-001            |
| Temperaturberechnung           | TC-TMP-003, TC-TMP-003A, TC-TMP-003B, TC-TMP-003C |
| Temperaturausgabe              | TC-TMP-004                                        |
| Stromverbrauchsberechnung      | TC-PWR-001, TC-PWR-001A, TC-PWR-001B              |
| Stromverbrauchsausgabe         | TC-PWR-002                                        |
| Ereignisprotokollierung        | TC-LOG-001, TC-LOG-002, TC-LOG-003, TC-LOG-004    |
| Regression Iteration 1         | REG-I1-001, REG-I1-002                            |
| Integration                    | TC-INT-001                                        |
| Nicht-funktionale Requirements | TC-NF-PERF-001, TC-NF-UNIT-001                    |
| Manuelle Validierung           | TC-MAN-001                                        |

---

## 12. Erwartetes Testergebnis

Bei erfolgreicher Ausführung wird erwartet:

```text
passed=21
failed=0
```

Die genaue Ausgabe enthält zusätzlich alle Testfall-IDs mit dem Status `BESTANDEN`.