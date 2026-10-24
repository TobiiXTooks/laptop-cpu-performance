# Iteration 3 – Testfälle

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die geplanten Testfälle für Iteration 3 des Projekts **Laptop – Prozessorleistung**.

Die Testfälle prüfen, ob die in Iteration 3 ergänzten Funktionen korrekt umgesetzt wurden. Der Schwerpunkt liegt auf:

* Thermal Throttling,
* Throttling-Stufen,
* effektiver CPU-Taktfrequenz,
* Stromverbrauch nach Thermal Throttling,
* Ereignisprotokollierung,
* Reset-Funktion,
* Vererbung durch `SimulationOption`,
* Interface-Nutzung durch `Displayable`,
* Regression der bisherigen Funktionen aus Iteration 1 und Iteration 2.

Die Testfälle dienen als Grundlage für die anschließende automatisierte Testimplementierung mit einem eigenen Java-TestRunner.

---

## 2. Testgegenstand

Getestet wird die Implementierung der Iteration 3.

Relevante Klassen und Komponenten:

| Klasse                        | Komponente        | Testrelevanz                                                                      |
| ----------------------------- | ----------------- | --------------------------------------------------------------------------------- |
| `ClockCalculator`             | CMP-003           | Berechnet weiterhin die ungedrosselte CPU-Taktfrequenz.                           |
| `CoolingLevel`                | CMP-004           | Erbt von `SimulationOption` und stellt Kühlungsfaktor bereit.                     |
| `Displayable`                 | Modellbereich     | Definiert die Methode `toDisplayText()`.                                          |
| `EventLogger`                 | CMP-007           | Protokolliert Modus-, Szenario-, Temperatur-, Throttling- und Reset-Ereignisse.   |
| `LoadScenario`                | CMP-002           | Erbt von `SimulationOption` und stellt CPU-Auslastung bereit.                     |
| `LogEntry`                    | CMP-007           | Speichert und formatiert Ereigniseinträge.                                        |
| `PerformanceController`       | CMP-008           | Koordiniert den vollständigen Simulationsablauf.                                  |
| `PerformanceMode`             | CMP-001           | Erbt von `SimulationOption` und stellt Takt- und Leistungsgrenzen bereit.         |
| `PowerConsumptionCalculator`  | CMP-006           | Berechnet Stromverbrauch mit effektiver CPU-Taktfrequenz.                         |
| `ProcessorState`              | CMP-008           | Speichert Takt, Temperatur, Throttling-Stufe, effektiven Takt und Stromverbrauch. |
| `SimulationInput`             | CMP-008 / CMP-009 | Bündelt Eingaben für einen Simulationslauf.                                       |
| `SimulationOption`            | Modellbereich     | Abstrakte Oberklasse für auswählbare Optionen.                                    |
| `SimulationResult`            | CMP-008 / CMP-009 | Bündelt und formatiert das Simulationsergebnis.                                   |
| `TemperatureSimulator`        | CMP-004           | Berechnet die CPU-Temperatur.                                                     |
| `ThermalThrottlingController` | CMP-005           | Bestimmt Throttling-Stufe und effektive CPU-Taktfrequenz.                         |
| `ThrottlingState`             | CMP-005           | Enum für definierte Throttling-Stufen.                                            |
| `UserInterface`               | CMP-009           | Zeigt Ergebniswerte und Ereignisprotokoll an.                                     |

---

## 3. Teststrategie

Die Tests werden als dynamische Softwaretests durchgeführt. Dabei wird der Code ausgeführt und mit erwarteten Ergebnissen verglichen.

Verwendete Testarten:

| Testart                 | Zweck                                                                         |
| ----------------------- | ----------------------------------------------------------------------------- |
| Modultest               | Einzelne Klassen und Methoden isoliert prüfen.                                |
| Grenzwerttest           | Temperaturgrenzen der Throttling-Stufen prüfen.                               |
| Integrationstest        | Zusammenspiel mehrerer Komponenten prüfen.                                    |
| Regressionstest         | Sicherstellen, dass Funktionen aus Iteration 1 und 2 weiterhin funktionieren. |
| Negativtest             | Ungültige Eingaben und Fehlerfälle prüfen.                                    |
| Nicht-funktionaler Test | Laufzeit, Ausgabeformat und deterministisches Verhalten prüfen.               |
| OO-Test                 | Vererbung, Interface-Nutzung und Klassenkommunikation prüfen.                 |

---

## 4. Testumgebung

| Bereich              | Beschreibung                         |
| -------------------- | ------------------------------------ |
| Programmiersprache   | Java                                 |
| Entwicklungsumgebung | IntelliJ IDEA / Konsole              |
| Testart              | eigener automatisierter TestRunner   |
| Testdatei            | `test/Iteration3UnitTestRunner.java` |
| Quellcode            | `src/`                               |
| Build-Ausgabe        | `out/`                               |
| Externe Bibliotheken | keine                                |
| Testausführung       | über `javac` und `java`              |

---

## 5. Kompilierung und Ausführung

Die Testfälle werden in der Testdatei `test/Iteration3UnittestRunner.java`.

---

## 6. Testfälle für Thermal Throttling

### TC-THR-001 – Throttling-Stufe `Normal`

| Feld                | Inhalt                                     |
| ------------------- | ------------------------------------------ |
| Testfall-ID         | TC-THR-001                                 |
| Requirement         | REQ-F-024                                  |
| Testart             | Modultest / Grenzwertnaher Test            |
| Testobjekt          | `ThermalThrottlingController`              |
| Vorbedingung        | Controller ist instanziiert.               |
| Eingabe             | Temperatur `65 °C`                         |
| Testschritte        | `determineThrottlingState(65.0)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.NORMAL`.     |
| Bestanden-Kriterium | Die Stufe wird als `Normal` erkannt.       |

---

### TC-THR-002 – Throttling-Stufe `Warnung`

| Feld                | Inhalt                                     |
| ------------------- | ------------------------------------------ |
| Testfall-ID         | TC-THR-002                                 |
| Requirement         | REQ-F-024                                  |
| Testart             | Modultest / Grenzwertnaher Test            |
| Testobjekt          | `ThermalThrottlingController`              |
| Vorbedingung        | Controller ist instanziiert.               |
| Eingabe             | Temperatur `75 °C`                         |
| Testschritte        | `determineThrottlingState(75.0)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.WARNUNG`.    |
| Bestanden-Kriterium | Die Stufe wird als `Warnung` erkannt.      |

---

### TC-THR-003 – Throttling-Stufe `Reduziert`

| Feld                | Inhalt                                     |
| ------------------- | ------------------------------------------ |
| Testfall-ID         | TC-THR-003                                 |
| Requirement         | REQ-F-024                                  |
| Testart             | Modultest / Grenzwertnaher Test            |
| Testobjekt          | `ThermalThrottlingController`              |
| Vorbedingung        | Controller ist instanziiert.               |
| Eingabe             | Temperatur `85 °C`                         |
| Testschritte        | `determineThrottlingState(85.0)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.REDUZIERT`.  |
| Bestanden-Kriterium | Die Stufe wird als `Reduziert` erkannt.    |

---

### TC-THR-004 – Throttling-Stufe `Kritisch`

| Feld                | Inhalt                                     |
| ------------------- | ------------------------------------------ |
| Testfall-ID         | TC-THR-004                                 |
| Requirement         | REQ-F-024                                  |
| Testart             | Modultest / Grenzwertnaher Test            |
| Testobjekt          | `ThermalThrottlingController`              |
| Vorbedingung        | Controller ist instanziiert.               |
| Eingabe             | Temperatur `95 °C`                         |
| Testschritte        | `determineThrottlingState(95.0)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.KRITISCH`.   |
| Bestanden-Kriterium | Die Stufe wird als `Kritisch` erkannt.     |

---

### TC-THR-005 – Grenzwert 69 °C

| Feld                | Inhalt                                                   |
| ------------------- | -------------------------------------------------------- |
| Testfall-ID         | TC-THR-005                                               |
| Requirement         | REQ-F-024                                                |
| Testart             | Grenzwerttest                                            |
| Testobjekt          | `ThermalThrottlingController`                            |
| Vorbedingung        | Controller ist instanziiert.                             |
| Eingabe             | Temperatur `69 °C`                                       |
| Testschritte        | `determineThrottlingState(69.0)` aufrufen.               |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.NORMAL`.                   |
| Bestanden-Kriterium | Der obere Grenzwert von `Normal` wird korrekt behandelt. |

---

### TC-THR-006 – Grenzwert 70 °C

| Feld                | Inhalt                                                     |
| ------------------- | ---------------------------------------------------------- |
| Testfall-ID         | TC-THR-006                                                 |
| Requirement         | REQ-F-024                                                  |
| Testart             | Grenzwerttest                                              |
| Testobjekt          | `ThermalThrottlingController`                              |
| Vorbedingung        | Controller ist instanziiert.                               |
| Eingabe             | Temperatur `70 °C`                                         |
| Testschritte        | `determineThrottlingState(70.0)` aufrufen.                 |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.WARNUNG`.                    |
| Bestanden-Kriterium | Der untere Grenzwert von `Warnung` wird korrekt behandelt. |

---

### TC-THR-007 – Grenzwert 79 °C

| Feld                | Inhalt                                                    |
| ------------------- | --------------------------------------------------------- |
| Testfall-ID         | TC-THR-007                                                |
| Requirement         | REQ-F-024                                                 |
| Testart             | Grenzwerttest                                             |
| Testobjekt          | `ThermalThrottlingController`                             |
| Vorbedingung        | Controller ist instanziiert.                              |
| Eingabe             | Temperatur `79 °C`                                        |
| Testschritte        | `determineThrottlingState(79.0)` aufrufen.                |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.WARNUNG`.                   |
| Bestanden-Kriterium | Der obere Grenzwert von `Warnung` wird korrekt behandelt. |

---

### TC-THR-008 – Grenzwert 80 °C

| Feld                | Inhalt                                                       |
| ------------------- | ------------------------------------------------------------ |
| Testfall-ID         | TC-THR-008                                                   |
| Requirement         | REQ-F-024                                                    |
| Testart             | Grenzwerttest                                                |
| Testobjekt          | `ThermalThrottlingController`                                |
| Vorbedingung        | Controller ist instanziiert.                                 |
| Eingabe             | Temperatur `80 °C`                                           |
| Testschritte        | `determineThrottlingState(80.0)` aufrufen.                   |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.REDUZIERT`.                    |
| Bestanden-Kriterium | Der untere Grenzwert von `Reduziert` wird korrekt behandelt. |

---

### TC-THR-009 – Grenzwert 89 °C

| Feld                | Inhalt                                                      |
| ------------------- | ----------------------------------------------------------- |
| Testfall-ID         | TC-THR-009                                                  |
| Requirement         | REQ-F-024                                                   |
| Testart             | Grenzwerttest                                               |
| Testobjekt          | `ThermalThrottlingController`                               |
| Vorbedingung        | Controller ist instanziiert.                                |
| Eingabe             | Temperatur `89 °C`                                          |
| Testschritte        | `determineThrottlingState(89.0)` aufrufen.                  |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.REDUZIERT`.                   |
| Bestanden-Kriterium | Der obere Grenzwert von `Reduziert` wird korrekt behandelt. |

---

### TC-THR-010 – Grenzwert 90 °C

| Feld                | Inhalt                                                      |
| ------------------- | ----------------------------------------------------------- |
| Testfall-ID         | TC-THR-010                                                  |
| Requirement         | REQ-F-024                                                   |
| Testart             | Grenzwerttest                                               |
| Testobjekt          | `ThermalThrottlingController`                               |
| Vorbedingung        | Controller ist instanziiert.                                |
| Eingabe             | Temperatur `90 °C`                                          |
| Testschritte        | `determineThrottlingState(90.0)` aufrufen.                  |
| Erwartetes Ergebnis | Rückgabe ist `ThrottlingState.KRITISCH`.                    |
| Bestanden-Kriterium | Der untere Grenzwert von `Kritisch` wird korrekt behandelt. |

---

### TC-THR-011 – Ungültige Temperatur unter Minimum

| Feld                | Inhalt                                      |
| ------------------- | ------------------------------------------- |
| Testfall-ID         | TC-THR-011                                  |
| Requirement         | REQ-F-024                                   |
| Testart             | Negativtest                                 |
| Testobjekt          | `ThermalThrottlingController`               |
| Vorbedingung        | Controller ist instanziiert.                |
| Eingabe             | Temperatur `20 °C`                          |
| Testschritte        | `determineThrottlingState(20.0)` aufrufen.  |
| Erwartetes Ergebnis | `IllegalArgumentException` wird ausgelöst.  |
| Bestanden-Kriterium | Ungültige Temperatur wird nicht akzeptiert. |

---

### TC-THR-012 – Ungültige Temperatur über Maximum

| Feld                | Inhalt                                      |
| ------------------- | ------------------------------------------- |
| Testfall-ID         | TC-THR-012                                  |
| Requirement         | REQ-F-024                                   |
| Testart             | Negativtest                                 |
| Testobjekt          | `ThermalThrottlingController`               |
| Vorbedingung        | Controller ist instanziiert.                |
| Eingabe             | Temperatur `120 °C`                         |
| Testschritte        | `determineThrottlingState(120.0)` aufrufen. |
| Erwartetes Ergebnis | `IllegalArgumentException` wird ausgelöst.  |
| Bestanden-Kriterium | Ungültige Temperatur wird nicht akzeptiert. |

---

## 7. Testfälle für effektive CPU-Taktfrequenz

### TC-ECLK-001 – Effektiver Takt bei `Normal`

| Feld                | Inhalt                                                           |
| ------------------- | ---------------------------------------------------------------- |
| Testfall-ID         | TC-ECLK-001                                                      |
| Requirement         | REQ-F-025                                                        |
| Testart             | Modultest                                                        |
| Testobjekt          | `ThermalThrottlingController`                                    |
| Eingabe             | ungedrosselter Takt `4.0 GHz`, Stufe `NORMAL`                    |
| Testschritte        | `calculateEffectiveClock(4.0, ThrottlingState.NORMAL)` aufrufen. |
| Erwartetes Ergebnis | `4.0 GHz`                                                        |
| Bestanden-Kriterium | Der Takt bleibt bei 100 %.                                       |

---

### TC-ECLK-002 – Effektiver Takt bei `Warnung`

| Feld                | Inhalt                                                            |
| ------------------- | ----------------------------------------------------------------- |
| Testfall-ID         | TC-ECLK-002                                                       |
| Requirement         | REQ-F-025                                                         |
| Testart             | Modultest                                                         |
| Testobjekt          | `ThermalThrottlingController`                                     |
| Eingabe             | ungedrosselter Takt `4.0 GHz`, Stufe `WARNUNG`                    |
| Testschritte        | `calculateEffectiveClock(4.0, ThrottlingState.WARNUNG)` aufrufen. |
| Erwartetes Ergebnis | `4.0 GHz`                                                         |
| Bestanden-Kriterium | Der Takt bleibt bei 100 %.                                        |

---

### TC-ECLK-003 – Effektiver Takt bei `Reduziert`

| Feld                | Inhalt                                                              |
| ------------------- | ------------------------------------------------------------------- |
| Testfall-ID         | TC-ECLK-003                                                         |
| Requirement         | REQ-F-026                                                           |
| Testart             | Modultest                                                           |
| Testobjekt          | `ThermalThrottlingController`                                       |
| Eingabe             | ungedrosselter Takt `4.0 GHz`, Stufe `REDUZIERT`                    |
| Testschritte        | `calculateEffectiveClock(4.0, ThrottlingState.REDUZIERT)` aufrufen. |
| Erwartetes Ergebnis | `3.2 GHz`                                                           |
| Bestanden-Kriterium | Der Takt wird auf 80 % reduziert.                                   |

---

### TC-ECLK-004 – Effektiver Takt bei `Kritisch`

| Feld                | Inhalt                                                             |
| ------------------- | ------------------------------------------------------------------ |
| Testfall-ID         | TC-ECLK-004                                                        |
| Requirement         | REQ-F-027                                                          |
| Testart             | Modultest                                                          |
| Testobjekt          | `ThermalThrottlingController`                                      |
| Eingabe             | ungedrosselter Takt `4.0 GHz`, Stufe `KRITISCH`                    |
| Testschritte        | `calculateEffectiveClock(4.0, ThrottlingState.KRITISCH)` aufrufen. |
| Erwartetes Ergebnis | `2.4 GHz`                                                          |
| Bestanden-Kriterium | Der Takt wird auf 60 % reduziert.                                  |

---

### TC-ECLK-005 – Throttling-Werte werden im `ProcessorState` gespeichert

| Feld                | Inhalt                                                            |
| ------------------- | ----------------------------------------------------------------- |
| Testfall-ID         | TC-ECLK-005                                                       |
| Requirement         | REQ-F-028                                                         |
| Testart             | Integrationstest innerhalb einer Klasse                           |
| Testobjekt          | `ProcessorState`, `ThermalThrottlingController`                   |
| Vorbedingung        | `ProcessorState` besitzt Takt und Temperatur.                     |
| Eingabe             | CPU-Auslastung `85 %`, Takt `4.0 GHz`, Temperatur `95 °C`         |
| Testschritte        | `applyThrottling(processorState)` aufrufen.                       |
| Erwartetes Ergebnis | `throttlingState = KRITISCH`, `effectiveClockGhz = 2.4`           |
| Bestanden-Kriterium | Throttling-Stufe und effektiver Takt sind im Zustand gespeichert. |

---

## 8. Testfälle für Stromverbrauch nach Throttling

### TC-PWR-I3-001 – Stromverbrauch basiert auf effektivem Takt

| Feld                | Inhalt                                                                                     |
| ------------------- | ------------------------------------------------------------------------------------------ |
| Testfall-ID         | TC-PWR-I3-001                                                                              |
| Requirement         | REQ-F-019                                                                                  |
| Testart             | Modultest                                                                                  |
| Testobjekt          | `PowerConsumptionCalculator`                                                               |
| Vorbedingung        | `ProcessorState` enthält effektive CPU-Taktfrequenz.                                       |
| Eingabe             | Modus mit `4.0 GHz`, Power-Limit `60 W`, CPU-Auslastung `100 %`, effektiver Takt `2.4 GHz` |
| Testschritte        | `calculatePowerConsumption(performanceMode, processorState)` aufrufen.                     |
| Erwartetes Ergebnis | Verbrauch wird auf Basis von `2.4 GHz` berechnet, nicht auf Basis von `4.0 GHz`.           |
| Bestanden-Kriterium | Ergebnis ist niedriger als bei ungedrosseltem Takt.                                        |

---

### TC-PWR-I3-002 – Mindestwert des Stromverbrauchs

| Feld                | Inhalt                                                          |
| ------------------- | --------------------------------------------------------------- |
| Testfall-ID         | TC-PWR-I3-002                                                   |
| Requirement         | REQ-F-018                                                       |
| Testart             | Grenzwerttest                                                   |
| Testobjekt          | `PowerConsumptionCalculator`                                    |
| Eingabe             | Rohwert unter `8 W`                                             |
| Testschritte        | Verbrauch berechnen oder `clampPowerConsumption(...)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist mindestens `8 W`.                                  |
| Bestanden-Kriterium | Untergrenze wird eingehalten.                                   |

---

### TC-PWR-I3-003 – Maximalwert des Stromverbrauchs

| Feld                | Inhalt                                                          |
| ------------------- | --------------------------------------------------------------- |
| Testfall-ID         | TC-PWR-I3-003                                                   |
| Requirement         | REQ-F-018                                                       |
| Testart             | Grenzwerttest                                                   |
| Testobjekt          | `PowerConsumptionCalculator`                                    |
| Eingabe             | Rohwert über `65 W`                                             |
| Testschritte        | Verbrauch berechnen oder `clampPowerConsumption(...)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist höchstens `65 W`.                                  |
| Bestanden-Kriterium | Obergrenze wird eingehalten.                                    |

---

### TC-PWR-I3-004 – Fehlender effektiver Takt

| Feld                | Inhalt                                                      |
| ------------------- | ----------------------------------------------------------- |
| Testfall-ID         | TC-PWR-I3-004                                               |
| Requirement         | REQ-F-019                                                   |
| Testart             | Negativtest                                                 |
| Testobjekt          | `PowerConsumptionCalculator`                                |
| Vorbedingung        | `ProcessorState` enthält keine effektive CPU-Taktfrequenz.  |
| Eingabe             | gültiger Leistungsmodus, unvollständiger `ProcessorState`   |
| Testschritte        | `calculatePowerConsumption(...)` aufrufen.                  |
| Erwartetes Ergebnis | `IllegalArgumentException` wird ausgelöst.                  |
| Bestanden-Kriterium | Verbrauch wird nicht mit unvollständigem Zustand berechnet. |

---

## 9. Testfälle für Ereignisprotokollierung

### TC-LOG-I3-001 – Temperaturwarnung ab 70 °C

| Feld                | Inhalt                                         |
| ------------------- | ---------------------------------------------- |
| Testfall-ID         | TC-LOG-I3-001                                  |
| Requirement         | REQ-F-022                                      |
| Testart             | Modultest / Grenzwerttest                      |
| Testobjekt          | `EventLogger`                                  |
| Eingabe             | Temperatur `70 °C`                             |
| Testschritte        | `logTemperatureWarningIfNeeded(...)` aufrufen. |
| Erwartetes Ergebnis | Ereignis `TEMPERATURWARNUNG` wird erzeugt.     |
| Bestanden-Kriterium | Ereignisliste enthält `TEMPERATURWARNUNG`.     |

---

### TC-LOG-I3-002 – Keine Temperaturwarnung unter 70 °C

| Feld                | Inhalt                                         |
| ------------------- | ---------------------------------------------- |
| Testfall-ID         | TC-LOG-I3-002                                  |
| Requirement         | REQ-F-022                                      |
| Testart             | Negativtest / Grenzwerttest                    |
| Testobjekt          | `EventLogger`                                  |
| Eingabe             | Temperatur `69 °C`                             |
| Testschritte        | `logTemperatureWarningIfNeeded(...)` aufrufen. |
| Erwartetes Ergebnis | Kein Ereignis `TEMPERATURWARNUNG`.             |
| Bestanden-Kriterium | Ereignisliste enthält keine Temperaturwarnung. |

---

### TC-LOG-I3-003 – Throttling aktiviert bei `Reduziert`

| Feld                | Inhalt                                            |
| ------------------- | ------------------------------------------------- |
| Testfall-ID         | TC-LOG-I3-003                                     |
| Requirement         | REQ-F-030                                         |
| Testart             | Modultest                                         |
| Testobjekt          | `EventLogger`                                     |
| Eingabe             | `ProcessorState` mit Throttling-Stufe `REDUZIERT` |
| Testschritte        | `logThrottlingActivatedIfNeeded(...)` aufrufen.   |
| Erwartetes Ergebnis | Ereignis `THROTTLING_AKTIVIERT` wird erzeugt.     |
| Bestanden-Kriterium | Ereignisliste enthält `THROTTLING_AKTIVIERT`.     |

---

### TC-LOG-I3-004 – Throttling aktiviert bei `Kritisch`

| Feld                | Inhalt                                           |
| ------------------- | ------------------------------------------------ |
| Testfall-ID         | TC-LOG-I3-004                                    |
| Requirement         | REQ-F-030                                        |
| Testart             | Modultest                                        |
| Testobjekt          | `EventLogger`                                    |
| Eingabe             | `ProcessorState` mit Throttling-Stufe `KRITISCH` |
| Testschritte        | `logThrottlingActivatedIfNeeded(...)` aufrufen.  |
| Erwartetes Ergebnis | Ereignis `THROTTLING_AKTIVIERT` wird erzeugt.    |
| Bestanden-Kriterium | Ereignisliste enthält `THROTTLING_AKTIVIERT`.    |

---

### TC-LOG-I3-005 – Kein aktives Throttling bei `Normal`

| Feld                | Inhalt                                           |
| ------------------- | ------------------------------------------------ |
| Testfall-ID         | TC-LOG-I3-005                                    |
| Requirement         | REQ-F-030                                        |
| Testart             | Negativtest                                      |
| Testobjekt          | `EventLogger`                                    |
| Eingabe             | `ProcessorState` mit Throttling-Stufe `NORMAL`   |
| Testschritte        | `logThrottlingActivatedIfNeeded(...)` aufrufen.  |
| Erwartetes Ergebnis | Kein Ereignis `THROTTLING_AKTIVIERT`.            |
| Bestanden-Kriterium | Ereignisliste enthält kein Aktivierungsereignis. |

---

### TC-LOG-I3-006 – Throttling-Stufe geändert

| Feld                | Inhalt                                                                             |
| ------------------- | ---------------------------------------------------------------------------------- |
| Testfall-ID         | TC-LOG-I3-006                                                                      |
| Requirement         | REQ-F-031                                                                          |
| Testart             | Zustandsbasierter Test                                                             |
| Testobjekt          | `EventLogger`                                                                      |
| Vorbedingung        | Zuvor wurde ein Zustand mit `NORMAL` protokolliert.                                |
| Eingabe             | neuer Zustand mit `KRITISCH`                                                       |
| Testschritte        | `logThrottlingChangedIfNeeded(...)` zweimal mit unterschiedlichen Stufen aufrufen. |
| Erwartetes Ergebnis | Beim Wechsel wird `THROTTLING_GEÄNDERT` erzeugt.                                   |
| Bestanden-Kriterium | Ereignisliste enthält Änderungsereignis.                                           |

---

### TC-LOG-I3-007 – Ereignisse ab Startindex ausgeben

| Feld                | Inhalt                                                                   |
| ------------------- | ------------------------------------------------------------------------ |
| Testfall-ID         | TC-LOG-I3-007                                                            |
| Requirement         | REQ-F-032                                                                |
| Testart             | Modultest                                                                |
| Testobjekt          | `EventLogger`                                                            |
| Vorbedingung        | Es existieren bereits alte Logeinträge.                                  |
| Eingabe             | Startindex nach alten Einträgen                                          |
| Testschritte        | Neue Ereignisse erzeugen und `getEntriesFromIndex(startIndex)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe enthält nur neue Ereignisse.                                    |
| Bestanden-Kriterium | Alte Ereignisse werden nicht im aktuellen Ergebnis angezeigt.            |

---

### TC-LOG-I3-008 – Chronologische Ereignisausgabe

| Feld                | Inhalt                                                  |
| ------------------- | ------------------------------------------------------- |
| Testfall-ID         | TC-LOG-I3-008                                           |
| Requirement         | REQ-F-032                                               |
| Testart             | Modultest                                               |
| Testobjekt          | `EventLogger`                                           |
| Vorbedingung        | Mehrere Ereignisse wurden erzeugt.                      |
| Eingabe             | mehrere Logeinträge                                     |
| Testschritte        | `getEntriesChronological()` aufrufen.                   |
| Erwartetes Ergebnis | Ereignisse sind nach Zeitstempel sortiert.              |
| Bestanden-Kriterium | Kein späterer Eintrag steht vor einem früheren Eintrag. |

---

### TC-LOG-I3-009 – Reset-Ereignis wird protokolliert

| Feld                | Inhalt                                                             |
| ------------------- | ------------------------------------------------------------------ |
| Testfall-ID         | TC-LOG-I3-009                                                      |
| Requirement         | REQ-F-033                                                          |
| Testart             | Modultest / Integrationstest                                       |
| Testobjekt          | `PerformanceController`, `EventLogger`                             |
| Eingabe             | Aufruf von `resetSimulation()`                                     |
| Testschritte        | Simulation oder Logger vorbereiten, `resetSimulation()` ausführen. |
| Erwartetes Ergebnis | Ereignis `SIMULATION_ZURÜCKGESETZT` wird erzeugt.                  |
| Bestanden-Kriterium | Reset ist über Ereignis nachvollziehbar.                           |

---

## 10. Integrationstests

### TC-INT-I3-001 – Vollständiger Simulationslauf mit kritischem Throttling

| Feld                | Inhalt                                                                               |
| ------------------- | ------------------------------------------------------------------------------------ |
| Testfall-ID         | TC-INT-I3-001                                                                        |
| Requirement         | REQ-F-019, REQ-F-024, REQ-F-027, REQ-F-028, REQ-F-030                                |
| Testart             | Integrationstest                                                                     |
| Testobjekt          | `PerformanceController`                                                              |
| Eingabe             | Modus `PERFORMANCE`, Szenario `GAMING` oder `STRESS_TEST`, Kühlung `NIEDRIG`         |
| Testschritte        | `runSimulation(inputData)` ausführen.                                                |
| Erwartetes Ergebnis | Temperatur ist hoch, Throttling-Stufe ist `Kritisch`, effektiver Takt ist reduziert. |
| Bestanden-Kriterium | Ergebnis enthält kritisches Throttling und reduzierten effektiven Takt.              |

---

### TC-INT-I3-002 – Vollständiger Simulationslauf ohne aktives Throttling

| Feld                | Inhalt                                                                                                |
| ------------------- | ----------------------------------------------------------------------------------------------------- |
| Testfall-ID         | TC-INT-I3-002                                                                                         |
| Requirement         | REQ-F-024, REQ-F-025, REQ-F-028                                                                       |
| Testart             | Integrationstest                                                                                      |
| Testobjekt          | `PerformanceController`                                                                               |
| Eingabe             | Modus `BALANCED`, Szenario `IDLE`, Kühlung `HOCH`                                                     |
| Testschritte        | `runSimulation(inputData)` ausführen.                                                                 |
| Erwartetes Ergebnis | Throttling-Stufe ist `Normal` oder maximal `Warnung`; effektiver Takt entspricht ungedrosseltem Takt. |
| Bestanden-Kriterium | Kein aktives Throttling wird angewendet.                                                              |

---

### TC-INT-I3-003 – Temperaturänderung führt zu neuer Throttling-Stufe

| Feld                | Inhalt                                                                               |
| ------------------- | ------------------------------------------------------------------------------------ |
| Testfall-ID         | TC-INT-I3-003                                                                        |
| Requirement         | REQ-F-029                                                                            |
| Testart             | Integrationstest                                                                     |
| Testobjekt          | `PerformanceController`, `TemperatureSimulator`, `ThermalThrottlingController`       |
| Eingabe             | Zwei Simulationsläufe mit unterschiedlicher Last oder Kühlung                        |
| Testschritte        | Simulation mit niedriger Last ausführen, danach Simulation mit hoher Last ausführen. |
| Erwartetes Ergebnis | Unterschiedliche Temperatur führt zu unterschiedlicher Throttling-Stufe.             |
| Bestanden-Kriterium | Throttling-Stufe wird nicht statisch übernommen, sondern neu berechnet.              |

---

### TC-INT-I3-004 – Stromverbrauch wird nach Throttling neu berechnet

| Feld                | Inhalt                                                                                        |
| ------------------- | --------------------------------------------------------------------------------------------- |
| Testfall-ID         | TC-INT-I3-004                                                                                 |
| Requirement         | REQ-F-019                                                                                     |
| Testart             | Integrationstest                                                                              |
| Testobjekt          | `PerformanceController`, `ThermalThrottlingController`, `PowerConsumptionCalculator`          |
| Eingabe             | Simulationslauf mit `KRITISCH`                                                                |
| Testschritte        | `runSimulation(inputData)` ausführen und Ergebnis prüfen.                                     |
| Erwartetes Ergebnis | Stromverbrauch basiert auf effektiver CPU-Taktfrequenz.                                       |
| Bestanden-Kriterium | Effektiver Takt ist kleiner als ungedrosselter Takt und Verbrauch ist entsprechend reduziert. |

---

### TC-INT-I3-005 – Ergebnis enthält nur Logeinträge des aktuellen Simulationslaufs

| Feld                | Inhalt                                                                                            |
| ------------------- | ------------------------------------------------------------------------------------------------- |
| Testfall-ID         | TC-INT-I3-005                                                                                     |
| Requirement         | REQ-F-032                                                                                         |
| Testart             | Integrationstest                                                                                  |
| Testobjekt          | `PerformanceController`, `EventLogger`, `SimulationResult`                                        |
| Eingabe             | Zwei Simulationsläufe nacheinander                                                                |
| Testschritte        | Erste Simulation ausführen, zweite Simulation ausführen, Logliste des zweiten Ergebnisses prüfen. |
| Erwartetes Ergebnis | Das zweite `SimulationResult` enthält nur neue Ereignisse des zweiten Simulationslaufs.           |
| Bestanden-Kriterium | Alte Ereignisse aus dem ersten Ergebnis erscheinen nicht erneut im zweiten Ergebnis.              |

---

### TC-INT-I3-006 – Ergebnistext enthält Throttling und effektiven Takt

| Feld                | Inhalt                                                                                |
| ------------------- | ------------------------------------------------------------------------------------- |
| Testfall-ID         | TC-INT-I3-006                                                                         |
| Requirement         | REQ-F-028, REQ-NF-006                                                                 |
| Testart             | Integrationstest / Ausgabetest                                                        |
| Testobjekt          | `SimulationResult`                                                                    |
| Eingabe             | vollständiger Simulationslauf                                                         |
| Testschritte        | `toDisplayText()` aufrufen.                                                           |
| Erwartetes Ergebnis | Text enthält `Effektive CPU-Taktfrequenz`, `Throttling-Stufe`, `GHz`, `°C`, `W`, `%`. |
| Bestanden-Kriterium | Alle relevanten Ergebniswerte werden mit Einheiten angezeigt.                         |

---

## 11. Testfälle für Vererbung und Interface

### TC-OO-I3-001 – `PerformanceMode` erbt von `SimulationOption`

| Feld                | Inhalt                                                                      |
| ------------------- | --------------------------------------------------------------------------- |
| Testfall-ID         | TC-OO-I3-001                                                                |
| Requirement         | REQ-NF-004                                                                  |
| Testart             | OO-Test / Klassenhierarchietest                                             |
| Testobjekt          | `PerformanceMode`, `SimulationOption`                                       |
| Eingabe             | Instanz von `PerformanceMode`                                               |
| Testschritte        | Prüfen, ob Objekt als `SimulationOption` verwendbar ist.                    |
| Erwartetes Ergebnis | `PerformanceMode` ist Instanz von `SimulationOption`.                       |
| Bestanden-Kriterium | Geerbte Methoden `getKey()`, `getName()`, `getDisplayName()` funktionieren. |

---

### TC-OO-I3-002 – `LoadScenario` erbt von `SimulationOption`

| Feld                | Inhalt                                                   |
| ------------------- | -------------------------------------------------------- |
| Testfall-ID         | TC-OO-I3-002                                             |
| Requirement         | REQ-NF-004                                               |
| Testart             | OO-Test / Klassenhierarchietest                          |
| Testobjekt          | `LoadScenario`, `SimulationOption`                       |
| Eingabe             | Instanz von `LoadScenario`                               |
| Testschritte        | Prüfen, ob Objekt als `SimulationOption` verwendbar ist. |
| Erwartetes Ergebnis | `LoadScenario` ist Instanz von `SimulationOption`.       |
| Bestanden-Kriterium | Geerbte Methoden funktionieren.                          |

---

### TC-OO-I3-003 – `CoolingLevel` erbt von `SimulationOption`

| Feld                | Inhalt                                                   |
| ------------------- | -------------------------------------------------------- |
| Testfall-ID         | TC-OO-I3-003                                             |
| Requirement         | REQ-NF-004                                               |
| Testart             | OO-Test / Klassenhierarchietest                          |
| Testobjekt          | `CoolingLevel`, `SimulationOption`                       |
| Eingabe             | Instanz von `CoolingLevel`                               |
| Testschritte        | Prüfen, ob Objekt als `SimulationOption` verwendbar ist. |
| Erwartetes Ergebnis | `CoolingLevel` ist Instanz von `SimulationOption`.       |
| Bestanden-Kriterium | Geerbte Methoden funktionieren.                          |

---

### TC-OO-I3-004 – `LogEntry` implementiert `Displayable`

| Feld                | Inhalt                                                             |
| ------------------- | ------------------------------------------------------------------ |
| Testfall-ID         | TC-OO-I3-004                                                       |
| Requirement         | REQ-NF-006                                                         |
| Testart             | OO-Test / Interface-Test                                           |
| Testobjekt          | `LogEntry`, `Displayable`                                          |
| Eingabe             | Instanz von `LogEntry`                                             |
| Testschritte        | Objekt als `Displayable` behandeln und `toDisplayText()` aufrufen. |
| Erwartetes Ergebnis | Formatierter Text wird zurückgegeben.                              |
| Bestanden-Kriterium | Rückgabe ist nicht leer und enthält Ereignistyp.                   |

---

### TC-OO-I3-005 – `SimulationResult` implementiert `Displayable`

| Feld                | Inhalt                                                                            |
| ------------------- | --------------------------------------------------------------------------------- |
| Testfall-ID         | TC-OO-I3-005                                                                      |
| Requirement         | REQ-NF-006                                                                        |
| Testart             | OO-Test / Interface-Test                                                          |
| Testobjekt          | `SimulationResult`, `Displayable`                                                 |
| Eingabe             | vollständiges `SimulationResult`                                                  |
| Testschritte        | Objekt als `Displayable` behandeln und `toDisplayText()` aufrufen.                |
| Erwartetes Ergebnis | Formatierter Ergebnistext wird zurückgegeben.                                     |
| Bestanden-Kriterium | Rückgabe enthält Leistungsmodus, Throttling-Stufe und effektive CPU-Taktfrequenz. |

---

## 12. Regressionstests aus Iteration 1 und 2

### REG-I1-001 – Leistungsmodus `ENERGY_SAVE` bleibt verfügbar

| Feld                | Inhalt                                |
| ------------------- | ------------------------------------- |
| Testfall-ID         | REG-I1-001                            |
| Bezug               | Iteration 1                           |
| Testart             | Regressionstest                       |
| Testobjekt          | `PerformanceModeManager`              |
| Eingabe             | `ENERGY_SAVE`                         |
| Erwartetes Ergebnis | Modus wird gefunden.                  |
| Bestanden-Kriterium | Keine Exception, korrekter Modusname. |

---

### REG-I1-002 – Leistungsmodus `BALANCED` bleibt verfügbar

| Feld                | Inhalt                                |
| ------------------- | ------------------------------------- |
| Testfall-ID         | REG-I1-002                            |
| Bezug               | Iteration 1                           |
| Testart             | Regressionstest                       |
| Testobjekt          | `PerformanceModeManager`              |
| Eingabe             | `BALANCED`                            |
| Erwartetes Ergebnis | Modus wird gefunden.                  |
| Bestanden-Kriterium | Keine Exception, korrekter Modusname. |

---

### REG-I1-003 – Leistungsmodus `PERFORMANCE` bleibt verfügbar

| Feld                | Inhalt                                |
| ------------------- | ------------------------------------- |
| Testfall-ID         | REG-I1-003                            |
| Bezug               | Iteration 1                           |
| Testart             | Regressionstest                       |
| Testobjekt          | `PerformanceModeManager`              |
| Eingabe             | `PERFORMANCE`                         |
| Erwartetes Ergebnis | Modus wird gefunden.                  |
| Bestanden-Kriterium | Keine Exception, korrekter Modusname. |

---

### REG-I1-004 – Lastszenario `IDLE` bleibt verfügbar

| Feld                | Inhalt                         |
| ------------------- | ------------------------------ |
| Testfall-ID         | REG-I1-004                     |
| Bezug               | Iteration 1                    |
| Testart             | Regressionstest                |
| Testobjekt          | `LoadScenarioManager`          |
| Eingabe             | `IDLE`                         |
| Erwartetes Ergebnis | Szenario wird gefunden.        |
| Bestanden-Kriterium | CPU-Auslastung beträgt `15 %`. |

---

### REG-I1-005 – Lastszenario `GAMING` bleibt verfügbar

| Feld                | Inhalt                         |
| ------------------- | ------------------------------ |
| Testfall-ID         | REG-I1-005                     |
| Bezug               | Iteration 1                    |
| Testart             | Regressionstest                |
| Testobjekt          | `LoadScenarioManager`          |
| Eingabe             | `GAMING`                       |
| Erwartetes Ergebnis | Szenario wird gefunden.        |
| Bestanden-Kriterium | CPU-Auslastung beträgt `85 %`. |

---

### REG-I1-006 – Benutzerdefinierter Modus bleibt gültig

| Feld                | Inhalt                                                       |
| ------------------- | ------------------------------------------------------------ |
| Testfall-ID         | REG-I1-006                                                   |
| Bezug               | Iteration 1                                                  |
| Testart             | Regressionstest                                              |
| Testobjekt          | `PerformanceModeManager`, `SimulationInput`                  |
| Eingabe             | Modus `CUSTOM`, maximaler Takt `3.6 GHz`, Power-Limit `45 W` |
| Erwartetes Ergebnis | Benutzerdefinierter Modus wird erzeugt.                      |
| Bestanden-Kriterium | Werte werden korrekt übernommen.                             |

---

### REG-I2-001 – Kühlungsstufe `NIEDRIG` bleibt verfügbar

| Feld                | Inhalt                                    |
| ------------------- | ----------------------------------------- |
| Testfall-ID         | REG-I2-001                                |
| Bezug               | Iteration 2                               |
| Testart             | Regressionstest                           |
| Testobjekt          | `TemperatureSimulator`                    |
| Eingabe             | `NIEDRIG`                                 |
| Erwartetes Ergebnis | Kühlungsstufe wird gefunden.              |
| Bestanden-Kriterium | Keine Exception, gültiger Kühlungsfaktor. |

---

### REG-I2-002 – Kühlungsstufe `MITTEL` bleibt verfügbar

| Feld                | Inhalt                                    |
| ------------------- | ----------------------------------------- |
| Testfall-ID         | REG-I2-002                                |
| Bezug               | Iteration 2                               |
| Testart             | Regressionstest                           |
| Testobjekt          | `TemperatureSimulator`                    |
| Eingabe             | `MITTEL`                                  |
| Erwartetes Ergebnis | Kühlungsstufe wird gefunden.              |
| Bestanden-Kriterium | Keine Exception, gültiger Kühlungsfaktor. |

---

### REG-I2-003 – Kühlungsstufe `HOCH` bleibt verfügbar

| Feld                | Inhalt                                    |
| ------------------- | ----------------------------------------- |
| Testfall-ID         | REG-I2-003                                |
| Bezug               | Iteration 2                               |
| Testart             | Regressionstest                           |
| Testobjekt          | `TemperatureSimulator`                    |
| Eingabe             | `HOCH`                                    |
| Erwartetes Ergebnis | Kühlungsstufe wird gefunden.              |
| Bestanden-Kriterium | Keine Exception, gültiger Kühlungsfaktor. |

---

### REG-I2-004 – Temperaturberechnung bleibt deterministisch

| Feld                | Inhalt                                           |
| ------------------- | ------------------------------------------------ |
| Testfall-ID         | REG-I2-004                                       |
| Bezug               | Iteration 2                                      |
| Testart             | Regressionstest / Nicht-funktionaler Test        |
| Testobjekt          | `TemperatureSimulator`                           |
| Eingabe             | gleicher `ProcessorState`, gleiche Kühlungsstufe |
| Testschritte        | Temperatur zweimal berechnen.                    |
| Erwartetes Ergebnis | Beide Ergebnisse sind identisch.                 |
| Bestanden-Kriterium | Keine zufälligen Abweichungen.                   |

---

### REG-I2-005 – Basisevents bleiben erhalten

| Feld                | Inhalt                                                                               |
| ------------------- | ------------------------------------------------------------------------------------ |
| Testfall-ID         | REG-I2-005                                                                           |
| Bezug               | Iteration 2                                                                          |
| Testart             | Regressionstest                                                                      |
| Testobjekt          | `EventLogger`                                                                        |
| Eingabe             | Moduswechsel, Lastszenariowechsel, Temperaturwarnung                                 |
| Erwartetes Ergebnis | `MODUSWECHSEL`, `LASTSZENARIOWECHSEL`, `TEMPERATURWARNUNG` werden weiterhin erzeugt. |
| Bestanden-Kriterium | Neue Throttling-Logik entfernt keine alten Ereignisse.                               |

---

## 13. Nicht-funktionale Tests

### TC-NF-I3-001 – Deterministisches Ergebnis

| Feld                | Inhalt                                                                                 |
| ------------------- | -------------------------------------------------------------------------------------- |
| Testfall-ID         | TC-NF-I3-001                                                                           |
| Requirement         | REQ-NF-001                                                                             |
| Testart             | Nicht-funktionaler Test                                                                |
| Testobjekt          | `PerformanceController`                                                                |
| Eingabe             | identischer `SimulationInput` in zwei getrennten Testläufen                            |
| Testschritte        | Simulation zweimal mit gleicher Eingabe ausführen.                                     |
| Erwartetes Ergebnis | Takt, Temperatur, Throttling-Stufe, effektiver Takt und Stromverbrauch sind identisch. |
| Bestanden-Kriterium | Keine zufälligen Ergebnisabweichungen.                                                 |

---

### TC-NF-I3-002 – Laufzeit unter 5 Sekunden

| Feld                | Inhalt                                                  |
| ------------------- | ------------------------------------------------------- |
| Testfall-ID         | TC-NF-I3-002                                            |
| Requirement         | REQ-NF-005                                              |
| Testart             | Nicht-funktionaler Test                                 |
| Testobjekt          | `PerformanceController`                                 |
| Eingabe             | vollständiger Simulationslauf                           |
| Testschritte        | Startzeit messen, Simulation ausführen, Endzeit messen. |
| Erwartetes Ergebnis | Laufzeit liegt unter `5 Sekunden`.                      |
| Bestanden-Kriterium | Simulationslauf wird innerhalb der Zeitgrenze beendet.  |

---

### TC-NF-I3-003 – Ausgabe enthält Einheiten

| Feld                | Inhalt                                                            |
| ------------------- | ----------------------------------------------------------------- |
| Testfall-ID         | TC-NF-I3-003                                                      |
| Requirement         | REQ-NF-006                                                        |
| Testart             | Ausgabetest                                                       |
| Testobjekt          | `SimulationResult`                                                |
| Eingabe             | vollständiges Simulationsergebnis                                 |
| Testschritte        | `toDisplayText()` aufrufen.                                       |
| Erwartetes Ergebnis | Ausgabe enthält `%`, `GHz`, `°C`, `W`.                            |
| Bestanden-Kriterium | Alle numerischen Werte sind verständlich mit Einheit dargestellt. |

---

### TC-NF-I3-004 – Keine externen Abhängigkeiten

| Feld                | Inhalt                                                    |
| ------------------- | --------------------------------------------------------- |
| Testfall-ID         | TC-NF-I3-004                                              |
| Requirement         | REQ-NF-007                                                |
| Testart             | Nicht-funktionaler Test                                   |
| Testobjekt          | gesamtes Projekt                                          |
| Eingabe             | Kompilierung mit Standard-Java                            |
| Testschritte        | Projekt ohne externe Bibliotheken kompilieren.            |
| Erwartetes Ergebnis | Kompilierung ist erfolgreich.                             |
| Bestanden-Kriterium | Keine externen Frameworks oder Bibliotheken erforderlich. |

---

## 14. Priorisierung

| Priorität | Testfälle                       | Begründung                                                                               |
| --------- | ------------------------------- | ---------------------------------------------------------------------------------------- |
| Hoch      | TC-THR-001 bis TC-THR-012       | Thermal Throttling ist Kernfunktion der Iteration 3.                                     |
| Hoch      | TC-ECLK-001 bis TC-ECLK-005     | Effektive CPU-Taktfrequenz ist zentral für REQ-F-025 bis REQ-F-028.                      |
| Hoch      | TC-PWR-I3-001 bis TC-PWR-I3-004 | REQ-F-019 war vorbereiteter Punkt aus Iteration 2 und wird in Iteration 3 abgeschlossen. |
| Hoch      | TC-LOG-I3-001 bis TC-LOG-I3-009 | Ereignisprotokollierung ist für Nachvollziehbarkeit und Traceability wichtig.            |
| Hoch      | TC-INT-I3-001 bis TC-INT-I3-006 | Integration zeigt, dass die Gesamtfunktion lauffähig ist.                                |
| Mittel    | TC-OO-I3-001 bis TC-OO-I3-005   | Prüft neue Struktur mit Oberklasse und Interface.                                        |
| Mittel    | REG-I1-001 bis REG-I2-005       | Verhindert Regressionen aus vorherigen Iterationen.                                      |
| Mittel    | TC-NF-I3-001 bis TC-NF-I3-004   | Prüft Qualitätseigenschaften und Ausgabe.                                                |

---

## 15. Akzeptanzkriterien

Die Iteration-3-Tests gelten als erfolgreich, wenn:

1. alle Throttling-Stufen korrekt bestimmt werden,
2. alle relevanten Temperaturgrenzen korrekt behandelt werden,
3. ungültige Temperaturen abgelehnt werden,
4. effektive CPU-Taktfrequenz korrekt berechnet wird,
5. Stromverbrauch auf Basis des effektiven Takts berechnet wird,
6. Throttling-Ereignisse korrekt protokolliert werden,
7. Reset-Ereignisse korrekt erzeugt werden,
8. Ereignisprotokolle pro Simulationslauf korrekt abgegrenzt werden,
9. `SimulationOption` und `Displayable` korrekt genutzt werden,
10. Funktionen aus Iteration 1 und 2 weiterhin funktionieren,
11. die Ausgabe alle relevanten Werte mit Einheiten enthält,
12. alle automatisierten Tests erfolgreich durchlaufen.

---

## 16. Traceability-Vorbereitung

Diese Testfälle werden in der Traceability Matrix v0.6 den Requirements zugeordnet.

| Requirement | Relevante Testfälle                                     |
| ----------- | ------------------------------------------------------- |
| REQ-F-019   | TC-PWR-I3-001, TC-PWR-I3-004, TC-INT-I3-004             |
| REQ-F-024   | TC-THR-001 bis TC-THR-012                               |
| REQ-F-025   | TC-ECLK-001, TC-ECLK-002, TC-INT-I3-002                 |
| REQ-F-026   | TC-ECLK-003                                             |
| REQ-F-027   | TC-ECLK-004, TC-INT-I3-001                              |
| REQ-F-028   | TC-ECLK-005, TC-INT-I3-006                              |
| REQ-F-029   | TC-INT-I3-003                                           |
| REQ-F-030   | TC-LOG-I3-003, TC-LOG-I3-004, TC-INT-I3-001             |
| REQ-F-031   | TC-LOG-I3-006                                           |
| REQ-F-032   | TC-LOG-I3-007, TC-LOG-I3-008, TC-INT-I3-005             |
| REQ-F-033   | TC-LOG-I3-009                                           |
| REQ-NF-001  | TC-NF-I3-001                                            |
| REQ-NF-004  | TC-OO-I3-001, TC-OO-I3-002, TC-OO-I3-003                |
| REQ-NF-005  | TC-NF-I3-002                                            |
| REQ-NF-006  | TC-INT-I3-006, TC-OO-I3-004, TC-OO-I3-005, TC-NF-I3-003 |
| REQ-NF-007  | TC-NF-I3-004                                            |

---
