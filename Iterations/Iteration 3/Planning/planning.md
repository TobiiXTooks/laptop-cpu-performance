# Iteration 3 – Planning

## 1. Ziel der Iteration

Ziel der Iteration 3 ist die Fertigstellung der vollständigen Simulation der Laptop-Prozessorleistung.

Die Software soll auf dem lauffähigen Stand der Iteration 2 aufbauen. In Iteration 2 wurden bereits Leistungsmodus, Lastszenario, CPU-Auslastung, ungedrosselte CPU-Taktfrequenz, Kühlungsstufe, CPU-Temperatur, Stromverbrauch und eine einfache Ereignisprotokollierung umgesetzt.

In Iteration 3 wird die Simulation um das mehrstufige Thermal Throttling erweitert. Dabei soll das System aus der berechneten CPU-Temperatur eine Throttling-Stufe bestimmen, daraus eine effektive CPU-Taktfrequenz ableiten, den Stromverbrauch auf Basis der effektiven CPU-Taktfrequenz neu bestimmen und die vollständigen Throttling-Ereignisse protokollieren.

Iteration 3 stellt damit den finalen funktionalen Ausbaustand des Projekts her.

---

## 2. Ausgangspunkt aus Iteration 2

In Iteration 2 wurden folgende Teilfunktionalitäten umgesetzt oder erweitert:

| Teilfunktionalität | Beschreibung                                     | Status nach Iteration 2 |
| ------------------ | ------------------------------------------------ | ----------------------- |
| TF-01              | Leistungsmodus mit benutzerdefiniertem Modus     | umgesetzt               |
| TF-02              | Vordefinierte Lastszenarien                      | umgesetzt               |
| TF-03              | Taktberechnung abhängig von Modus und Auslastung | umgesetzt               |
| TF-04              | Temperaturberechnung mit Kühlungsstufe           | umgesetzt               |
| TF-06              | Stromverbrauch in Watt ableiten                  | umgesetzt               |
| TF-07              | einfache Ereignisprotokollierung                 | teilweise umgesetzt     |

Die Iteration-2-Software kann bereits einen vollständigen Simulationslauf mit Temperatur- und Stromverbrauchsberechnung ausführen.

Für Iteration 2 galt jedoch noch:

```text
effektive CPU-Taktfrequenz = ungedrosselte CPU-Taktfrequenz
```

Thermal Throttling war noch nicht aktiv. Dadurch konnte REQ-F-019 nur vorbereitet, aber noch nicht vollständig umgesetzt und getestet werden.

---

## 3. Fachlicher Umfang der Iteration 3

In Iteration 3 werden folgende Teilfunktionalitäten umgesetzt oder abgeschlossen:

| Teilfunktionalität | Beschreibung                                                 | Status in Iteration 3  |
| ------------------ | ------------------------------------------------------------ | ---------------------- |
| TF-05              | Mehrstufiges Thermal Throttling                              | vollständig umzusetzen |
| TF-06              | Stromverbrauch nach Thermal Throttling neu bestimmen         | vollständig umzusetzen |
| TF-07              | Ereignisprotokoll für Moduswechsel, Warnungen und Throttling | vollständig umzusetzen |

Die bereits vorhandenen Funktionen aus Iteration 1 und Iteration 2 bleiben Bestandteil der Software und müssen weiterhin lauffähig sein.

---

## 4. Nicht Bestandteil der Iteration 3

Folgende Inhalte sind weiterhin nicht Bestandteil des Projekts:

| Bereich                                    | Begründung                                                                   |
| ------------------------------------------ | ---------------------------------------------------------------------------- |
| Auslesen realer CPU-Sensoren               | Das Projekt simuliert Hardwarewerte softwaretechnisch.                       |
| Steuerung echter CPU-Leistungsprofile      | Die Software verändert keine reale Hardware und keine Betriebssystemprofile. |
| Reale Energieverbrauchsmessung             | Der Stromverbrauch wird berechnet und nicht gemessen.                        |
| Benchmark-Vergleich mit realen Prozessoren | Benchmarking wurde aus dem Projektumfang ausgeschlossen.                     |
| System- oder Abnahmetest                   | Für die Projektarbeit ist kein System- bzw. Abnahmetest erforderlich.        |

---

## 5. Requirements der Iteration 3

### 5.1 Funktionale Requirements

| Requirement-ID | Teilfunktionalität | Kurzbeschreibung                                                        | Umsetzung in Iteration 3 |
| -------------- | ------------------ | ----------------------------------------------------------------------- | ------------------------ |
| REQ-F-019      | TF-06              | Stromverbrauch nach Thermal Throttling neu bestimmen                    | vollständig umzusetzen   |
| REQ-F-024      | TF-05              | Throttling-Stufe aus CPU-Temperatur bestimmen                           | vollständig umzusetzen   |
| REQ-F-025      | TF-05              | effektive CPU-Taktfrequenz bei `Normal` oder `Warnung` auf 100 % setzen | vollständig umzusetzen   |
| REQ-F-026      | TF-05              | effektive CPU-Taktfrequenz bei `Reduziert` auf 80 % setzen              | vollständig umzusetzen   |
| REQ-F-027      | TF-05              | effektive CPU-Taktfrequenz bei `Kritisch` auf 60 % setzen               | vollständig umzusetzen   |
| REQ-F-028      | TF-05              | aktuelle Throttling-Stufe ausgeben                                      | vollständig umzusetzen   |
| REQ-F-029      | TF-05              | Throttling-Stufe bei geänderter CPU-Temperatur neu zuordnen             | vollständig umzusetzen   |
| REQ-F-030      | TF-07              | Ereignis `THROTTLING_AKTIVIERT` erfassen                                | vollständig umzusetzen   |
| REQ-F-031      | TF-07              | Ereignis `THROTTLING_GEÄNDERT` erfassen                                 | vollständig umzusetzen   |
| REQ-F-032      | TF-07              | Ereignisprotokoll chronologisch ausgeben                                | vollständig umzusetzen   |
| REQ-F-033      | TF-07              | Simulationslauf zurücksetzen und Reset-Ereignis erfassen                | vollständig umzusetzen   |

---

### 5.2 Weiterhin gültige funktionale Requirements aus vorherigen Iterationen

Die folgenden Requirements bleiben in Iteration 3 relevant und müssen durch Regressionstests abgesichert werden:

| Requirement-Bereich | Requirements            | Bedeutung für Iteration 3                                                       |
| ------------------- | ----------------------- | ------------------------------------------------------------------------------- |
| Leistungsmodus      | REQ-F-001 bis REQ-F-004 | Leistungsmodi müssen weiterhin auswählbar und validierbar sein.                 |
| Lastszenario        | REQ-F-005 bis REQ-F-008 | Lastszenarien müssen weiterhin auswählbar und korrekt zuordenbar sein.          |
| Taktberechnung      | REQ-F-009 bis REQ-F-012 | Die ungedrosselte CPU-Taktfrequenz muss weiterhin korrekt berechnet werden.     |
| Temperatur          | REQ-F-013 bis REQ-F-016 | Temperaturberechnung und Kühlungsstufen müssen weiterhin korrekt funktionieren. |
| Stromverbrauch      | REQ-F-017 bis REQ-F-018 | Stromverbrauch und Ausgabe mit Einheit müssen weiterhin funktionieren.          |
| einfache Ereignisse | REQ-F-020 bis REQ-F-023 | Bestehende Ereigniseinträge müssen weiterhin erzeugt und gespeichert werden.    |

---

### 5.3 Nicht-funktionale Requirements

Folgende nicht-funktionale Requirements gelten weiterhin:

| Requirement-ID | Kategorie                | Bedeutung für Iteration 3                                                              |
| -------------- | ------------------------ | -------------------------------------------------------------------------------------- |
| REQ-NF-001     | Determinismus            | Gleiche Eingaben müssen weiterhin gleiche Ausgaben erzeugen.                           |
| REQ-NF-002     | Testbarkeit              | Alle neuen Requirements müssen Testfällen zugeordnet werden.                           |
| REQ-NF-003     | Robustheit               | Ungültige Eingaben müssen kontrolliert behandelt werden.                               |
| REQ-NF-004     | Konfigurationsmanagement | Am Ende der Iteration muss eine Baseline erzeugt werden.                               |
| REQ-NF-005     | Performance              | Das Simulationsergebnis muss innerhalb von höchstens 5 Sekunden bereitgestellt werden. |
| REQ-NF-006     | Ausgabequalität          | Numerische Simulationswerte müssen mit Wert und Einheit ausgegeben werden.             |

---

## 6. Architektur- und Designumfang

Die modulare Schichtenarchitektur bleibt erhalten.

```text
Benutzerschnittstelle
        ↓
Anwendungssteuerung
        ↓
Fachlogik / Services
        ↓
Modelle und Datenobjekte
        ↓
Protokollierung / Nachweisführung
```

Die Architektur wird nicht grundlegend verändert, sondern um die bisher vorbereitete Throttling-Komponente erweitert.

---

### 6.1 Weiterverwendete Komponenten

| Komponenten-ID | Komponente                   | Aufgabe in Iteration 3                       |
| -------------- | ---------------------------- | -------------------------------------------- |
| CMP-001        | `PerformanceModeManager`     | Leistungsmodi bereitstellen und validieren   |
| CMP-002        | `LoadScenarioManager`        | Lastszenarien bereitstellen und validieren   |
| CMP-003        | `ClockCalculator`            | ungedrosselte CPU-Taktfrequenz berechnen     |
| CMP-004        | `TemperatureSimulator`       | CPU-Temperatur berechnen                     |
| CMP-006        | `PowerConsumptionCalculator` | Stromverbrauch berechnen                     |
| CMP-007        | `EventLogger`                | Ereignisse protokollieren                    |
| CMP-008        | `PerformanceController`      | vollständigen Simulationsablauf koordinieren |
| CMP-009        | `UserInterface`              | Eingaben erfassen und Ergebnisse anzeigen    |

---

### 6.2 Neue oder vollständig aktivierte Komponenten in Iteration 3

| Komponenten-ID | Komponente                    | Aufgabe in Iteration 3                                              |
| -------------- | ----------------------------- | ------------------------------------------------------------------- |
| CMP-005        | `ThermalThrottlingController` | Throttling-Stufe bestimmen und effektive CPU-Taktfrequenz berechnen |

---

## 7. Datenmodellumfang der Iteration 3

Folgende Datenmodelle werden erweitert:

| Datenmodell        | Erweiterung in Iteration 3                                                                    |
| ------------------ | --------------------------------------------------------------------------------------------- |
| `ProcessorState`   | Ergänzung um effektive CPU-Taktfrequenz und Throttling-Stufe                                  |
| `SimulationResult` | Ergänzung um Throttling-Stufe, effektive CPU-Taktfrequenz und vollständiges Ereignisprotokoll |
| `LogEntry`         | Nutzung der Throttling-Stufe und effektiven CPU-Taktfrequenz in Ereigniseinträgen             |

Ein neues Datenmodell kann optional ergänzt werden:

| Datenmodell       | Zweck                                                                          |
| ----------------- | ------------------------------------------------------------------------------ |
| `ThrottlingState` | Beschreibung einer Throttling-Stufe mit Name, Temperaturbereich und Taktfaktor |

Falls die Implementierung klein gehalten werden soll, kann `ThrottlingState` auch als `String` oder `enum` umgesetzt werden. Für die Dokumentation ist eine eigene Modellbeschreibung sinnvoll, weil dadurch die Throttling-Stufen klar nachverfolgbar bleiben.

---

## 8. Throttling-Stufen der Iteration 3

Das System verwendet folgende Throttling-Stufen:

| Throttling-Stufe | Temperaturbereich | Effektive CPU-Taktfrequenz                |
| ---------------- | ----------------: | ----------------------------------------- |
| `Normal`         |   35 °C bis 69 °C | 100 % der ungedrosselten CPU-Taktfrequenz |
| `Warnung`        |   70 °C bis 79 °C | 100 % der ungedrosselten CPU-Taktfrequenz |
| `Reduziert`      |   80 °C bis 89 °C | 80 % der ungedrosselten CPU-Taktfrequenz  |
| `Kritisch`       |  90 °C bis 100 °C | 60 % der ungedrosselten CPU-Taktfrequenz  |

---

## 9. Geplanter Simulationsablauf in Iteration 3

Der geplante Ablauf der finalen Simulation lautet:

1. Der Nutzer startet die Anwendung.
2. Das System stellt Leistungsmodi bereit.
3. Der Nutzer wählt einen Leistungsmodus.
4. Das System stellt Lastszenarien bereit.
5. Der Nutzer wählt ein Lastszenario.
6. Das System stellt Kühlungsstufen bereit.
7. Der Nutzer wählt eine Kühlungsstufe.
8. Das System erstellt ein `SimulationInput`-Objekt.
9. Der `PerformanceController` übernimmt das `SimulationInput`-Objekt.
10. Der `PerformanceController` bestimmt den aktuellen Leistungsmodus.
11. Der `PerformanceController` bestimmt das aktuelle Lastszenario.
12. Der `PerformanceController` bestimmt die aktuelle Kühlungsstufe.
13. Der `PerformanceController` erstellt einen `ProcessorState`.
14. Der `ClockCalculator` berechnet die ungedrosselte CPU-Taktfrequenz.
15. Der `TemperatureSimulator` berechnet die CPU-Temperatur.
16. Der `ThermalThrottlingController` bestimmt die Throttling-Stufe.
17. Der `ThermalThrottlingController` berechnet die effektive CPU-Taktfrequenz.
18. Der `PowerConsumptionCalculator` berechnet den Stromverbrauch auf Basis der effektiven CPU-Taktfrequenz.
19. Der `EventLogger` protokolliert Moduswechsel, Lastszenario-Wechsel und Temperaturwarnungen.
20. Der `EventLogger` protokolliert Throttling-Ereignisse.
21. Der `PerformanceController` erstellt ein vollständiges `SimulationResult`.
22. Das `UserInterface` gibt das vollständige Simulationsergebnis aus.
23. Das `UserInterface` gibt das Ereignisprotokoll chronologisch aus.

---

## 10. Geplanter Testumfang

Für Iteration 3 werden neue Testfälle für Thermal Throttling, Stromverbrauch nach Throttling, vollständige Ereignisprotokollierung, Reset-Funktion und Regression benötigt.

| Bereich                        | Geplante Testfälle                                                                                |
| ------------------------------ | ------------------------------------------------------------------------------------------------- |
| Throttling-Stufe               | Prüfung der Zuordnung `Normal`, `Warnung`, `Reduziert`, `Kritisch`                                |
| effektive CPU-Taktfrequenz     | Prüfung von 100 %, 80 % und 60 % der ungedrosselten CPU-Taktfrequenz                              |
| Stromverbrauch nach Throttling | Prüfung, dass der Stromverbrauch nach geänderter effektiver CPU-Taktfrequenz neu berechnet wird   |
| Throttling-Ausgabe             | Prüfung, dass Throttling-Stufe und effektive CPU-Taktfrequenz angezeigt werden                    |
| Throttling-Logging             | Prüfung von `THROTTLING_AKTIVIERT` und `THROTTLING_GEÄNDERT`                                      |
| chronologische Logausgabe      | Prüfung, dass Ereignisse in zeitlich korrekter Reihenfolge bereitgestellt werden                  |
| Reset-Funktion                 | Prüfung, dass ein Simulationslauf zurückgesetzt und `SIMULATION_ZURÜCKGESETZT` protokolliert wird |
| Regression                     | Wiederholung relevanter Tests aus Iteration 1 und 2                                               |
| Nicht-funktionale Tests        | Prüfung von Laufzeit, Einheiten und deterministischem Verhalten                                   |

---

## 11. Vorgesehene Testfall-IDs

| Requirement-ID | Geplanter Testfall |
| -------------- | ------------------ |
| REQ-F-019      | TC-INT-002         |
| REQ-F-024      | TC-THR-001         |
| REQ-F-025      | TC-THR-002         |
| REQ-F-026      | TC-THR-003         |
| REQ-F-027      | TC-THR-004         |
| REQ-F-028      | TC-THR-005         |
| REQ-F-029      | TC-INT-003         |
| REQ-F-030      | TC-LOG-005         |
| REQ-F-031      | TC-LOG-006         |
| REQ-F-032      | TC-LOG-007         |
| REQ-F-033      | TC-LOG-008         |
| REQ-NF-001     | TC-NF-DET-001      |
| REQ-NF-005     | TC-NF-PERF-002     |
| REQ-NF-006     | TC-NF-UNIT-002     |

Zusätzlich werden Regressionstests aus Iteration 1 und Iteration 2 erneut ausgeführt.

---

## 12. Akzeptanzkriterien

Iteration 3 gilt als abgeschlossen, wenn folgende Kriterien erfüllt sind:

| Kriterium                      | Erwartung                                                                                                                                      |
| ------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------- |
| Lauffähigkeit                  | Die Software kann gestartet und vollständig ausgeführt werden.                                                                                 |
| Bestandsschutz                 | Funktionen aus Iteration 1 und Iteration 2 funktionieren weiterhin.                                                                            |
| Throttling-Stufe               | Das System bestimmt `Normal`, `Warnung`, `Reduziert` oder `Kritisch` korrekt aus der CPU-Temperatur.                                           |
| effektive CPU-Taktfrequenz     | Das System berechnet die effektive CPU-Taktfrequenz gemäß Throttling-Stufe.                                                                    |
| Stromverbrauch nach Throttling | Das System berechnet den Stromverbrauch nach Anwendung der effektiven CPU-Taktfrequenz neu.                                                    |
| Ergebnisanzeige                | Das System zeigt ungedrosselte CPU-Taktfrequenz, effektive CPU-Taktfrequenz, Temperatur, Throttling-Stufe und Stromverbrauch mit Einheiten an. |
| Ereignisprotokoll              | Das System protokolliert Temperaturwarnungen und Throttling-Ereignisse.                                                                        |
| Chronologie                    | Das Ereignisprotokoll wird chronologisch bereitgestellt.                                                                                       |
| Reset                          | Das System kann den Simulationslauf zurücksetzen und ein Reset-Ereignis erfassen.                                                              |
| Tests                          | Alle automatisierten Tests der Iteration 3 bestehen.                                                                                           |
| Traceability                   | Requirements, Komponenten, Schnittstellen, Klassen, Code-Dateien und Testfälle sind nachvollziehbar zugeordnet.                                |
| Baseline                       | Nach Abschluss der Iteration wird eine Baseline für Iteration 3 erzeugt.                                                                       |

---

## 13. Risiken und Gegenmaßnahmen

| Risiko                                                          | Auswirkung                                            | Gegenmaßnahme                                                          |
| --------------------------------------------------------------- | ----------------------------------------------------- | ---------------------------------------------------------------------- |
| Throttling verändert bestehende Taktlogik unerwartet            | Regression aus Iteration 1 könnte fehlschlagen        | ungedrosselte und effektive CPU-Taktfrequenz getrennt speichern        |
| Stromverbrauch wird weiterhin mit ungedrosseltem Takt berechnet | REQ-F-019 wäre nicht erfüllt                          | `PowerConsumptionCalculator` mit effektiver CPU-Taktfrequenz verwenden |
| EventLogger wird zu umfangreich                                 | Controller oder Logger könnten unübersichtlich werden | Ereignislogik klar in `EventLogger` kapseln                            |
| Reset-Logik löscht wichtige Nachweise                           | Ereignisprotokoll wäre nicht nachvollziehbar          | Reset als eigenes Ereignis protokollieren                              |
| Tests werden zu breit                                           | Fehlerursachen schwer erkennbar                       | Unit-, Integrations- und Regressionstests getrennt dokumentieren       |

---

## 14. Geplante Artefakte der Iteration 3

| Artefakt                             | Status zu Beginn der Iteration                 |
| ------------------------------------ | ---------------------------------------------- |
| `Iteration3_Planning.md`             | zu erstellen                                   |
| `Iteration3_Architectur.md`          | zu erstellen                                   |
| `Architekturdiagramm_Iteration3.png` | zu erstellen oder aus Iteration 2 zu erweitern |
| `Iteration3_Feindesign.md`           | zu erstellen                                   |
| Java-Implementierung Iteration 3     | zu erstellen                                   |
| `Iteration3UnitTestRunner.java`      | zu erstellen                                   |
| `Iteration3_Testfaelle.md`           | zu erstellen                                   |
| `Iteration3_Testreporting.md`        | zu erstellen                                   |
| `Traceability_Matrix_v0.6.md`        | zu aktualisieren                               |
| `Retrospective_Iteration3.md`        | zu erstellen                                   |
| Git-Baseline / Tag für Iteration 3   | nach erfolgreichem Abschluss zu setzen         |

---

## 15. Definition of Done

Iteration 3 ist abgeschlossen, wenn:

1. die geplanten Requirements REQ-F-019 und REQ-F-024 bis REQ-F-033 umgesetzt sind,
2. die bestehende Funktionalität aus Iteration 1 und 2 weiterhin lauffähig ist,
3. die Architektur- und Feindesign-Dokumentation aktualisiert wurde,
4. die Implementierung erfolgreich kompiliert,
5. alle automatisierten Tests erfolgreich ausgeführt wurden,
6. das Testreporting erstellt wurde,
7. die Traceability Matrix auf den Stand der Iteration 3 aktualisiert wurde,
8. die Retrospective erstellt wurde,
9. die finale Iteration-3-Baseline im Versionsmanagement gesetzt wurde.

---
