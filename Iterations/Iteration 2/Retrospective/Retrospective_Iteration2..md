# Iteration 2 – Retrospective

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die Retrospective der Iteration 2 des Laptop-Prozessorleistungsmoduls.

Die Retrospective bewertet den abgeschlossenen Entwicklungsstand der Iteration 2. Dabei werden Zielerreichung, umgesetzte Funktionen, Teststatus, Traceability, bekannte Einschränkungen und Verbesserungen für Iteration 3 festgehalten.

---

## 2. Ziel der Iteration 2

Ziel der Iteration 2 war die Erweiterung der lauffähigen Grundsimulation aus Iteration 1.

Die Simulation sollte um folgende Funktionen ergänzt werden:

* Auswahl einer Kühlungsstufe,
* Berechnung einer simulierten CPU-Temperatur,
* Berechnung eines simulierten Stromverbrauchs,
* einfache Ereignisprotokollierung,
* erweiterte Ergebnisanzeige,
* Beibehaltung der lauffähigen Iteration-1-Funktionalität.

Thermal Throttling war nicht Bestandteil der Iteration 2. Diese Funktion wird bewusst erst in Iteration 3 umgesetzt.

---

## 3. Umgesetzter Funktionsumfang

In Iteration 2 wurden folgende Teilfunktionalitäten umgesetzt.

| Teilfunktionalität | Beschreibung                           | Status                     |
| ------------------ | -------------------------------------- | -------------------------- |
| TF-04              | Temperaturberechnung mit Kühlungsstufe | umgesetzt                  |
| TF-06              | Stromverbrauch in Watt ableiten        | umgesetzt                  |
| TF-07              | einfache Ereignisprotokollierung       | teilweise umgesetzt        |
| TF-01              | Leistungsmodus                         | aus Iteration 1 übernommen |
| TF-02              | Lastszenarien                          | aus Iteration 1 übernommen |
| TF-03              | Taktberechnung                         | aus Iteration 1 übernommen |

Die einfache Ereignisprotokollierung umfasst in Iteration 2:

* `MODUSWECHSEL`,
* `LASTSZENARIOWECHSEL`,
* `TEMPERATURWARNUNG`.

Nicht umgesetzt wurden Ereignisse für Thermal Throttling, da die Throttling-Logik erst in Iteration 3 folgt.

---

## 4. Nicht umgesetzter Funktionsumfang

Folgende Inhalte wurden bewusst nicht in Iteration 2 umgesetzt:

| Funktion                                        | Grund                                              |
| ----------------------------------------------- | -------------------------------------------------- |
| Mehrstufiges Thermal Throttling                 | Bestandteil von Iteration 3                        |
| Effektive CPU-Taktfrequenz nach Throttling      | abhängig von Thermal Throttling                    |
| Ereignis `THROTTLING_AKTIVIERT`                 | benötigt Throttling-Logik                          |
| Ereignis `THROTTLING_GEÄNDERT`                  | benötigt Throttling-Zustandsvergleich              |
| vollständige Reset-Logik des Ereignisprotokolls | Bestandteil der vollständigen TF-07 in Iteration 3 |

Für Iteration 2 gilt weiterhin:

```text
effektive CPU-Taktfrequenz = ungedrosselte CPU-Taktfrequenz
```

REQ-F-019 wurde deshalb nur vorbereitet und wird in Iteration 3 vollständig umgesetzt und getestet.

---

## 5. Erstellte und aktualisierte Artefakte

In Iteration 2 wurden folgende Artefakte erstellt oder aktualisiert.

| Artefakt                             | Status       |
| ------------------------------------ | ------------ |
| `Planning.md`                        | erstellt     |
| `Iteration2_Architectur.md`          | erstellt     |
| `Architekturdiagramm_Iteration2.png` | erstellt     |
| `Iteration2_Feindesign.md`           | erstellt     |
| Java-Implementierung Iteration 2     | erstellt     |
| `Iteration2UnitTestRunner.java`      | erstellt     |
| `Iteration2_Testfaelle.md`           | erstellt     |
| `Iteration2_Testreporting.md`        | erstellt     |
| `Traceability_Matrix_v0.5.md`        | aktualisiert |
| `Retrospective_Iteration2.md`        | erstellt     |

---

## 6. Implementierungsstand

Die Implementierung der Iteration 2 wurde erfolgreich erweitert.

### 6.1 Neue Klassen

| Klasse                       | Zweck                                                  | Status    |
| ---------------------------- | ------------------------------------------------------ | --------- |
| `CoolingLevel`               | Datenmodell für Kühlungsstufen                         | umgesetzt |
| `LogEntry`                   | Datenmodell für Ereignisprotokolleinträge              | umgesetzt |
| `TemperatureSimulator`       | Verwaltung der Kühlungsstufen und Temperaturberechnung | umgesetzt |
| `PowerConsumptionCalculator` | Stromverbrauchsberechnung                              | umgesetzt |
| `EventLogger`                | einfache Ereignisprotokollierung                       | umgesetzt |

### 6.2 Erweiterte Klassen

| Klasse                  | Erweiterung                                                             | Status    |
| ----------------------- | ----------------------------------------------------------------------- | --------- |
| `SimulationInput`       | Kühlungsstufe ergänzt                                                   | umgesetzt |
| `ProcessorState`        | Temperatur und Stromverbrauch ergänzt                                   | umgesetzt |
| `SimulationResult`      | Ausgabe von Kühlung, Temperatur, Stromverbrauch und Ereignissen ergänzt | umgesetzt |
| `PerformanceController` | Ablauf um Temperatur, Stromverbrauch und Logging erweitert              | umgesetzt |
| `UserInterface`         | Eingabe und Ausgabe erweitert                                           | umgesetzt |
| `Main`                  | neue Services instanziiert                                              | umgesetzt |

### 6.3 Unveränderte Kernklassen

| Klasse                   | Begründung                                   |
| ------------------------ | -------------------------------------------- |
| `PerformanceMode`        | Enthält bereits benötigte Modusdaten.        |
| `LoadScenario`           | Enthält bereits benötigte Lastszenariodaten. |
| `PerformanceModeManager` | Leistungsmodi bleiben unverändert.           |
| `LoadScenarioManager`    | Lastszenarien bleiben unverändert.           |
| `ClockCalculator`        | Taktberechnung bleibt unverändert.           |

Die bestehenden Iteration-1-Komponenten wurden damit nicht ersetzt, sondern gezielt erweitert oder unverändert übernommen.

---

## 7. Teststatus

Die automatisierten Tests der Iteration 2 wurden erfolgreich ausgeführt.

| Kennzahl                               | Ergebnis |
| -------------------------------------- | -------: |
| Ausgeführte automatisierte Tests       |       21 |
| Bestandene Tests                       |       21 |
| Fehlgeschlagene Tests                  |        0 |
| Nicht ausgeführte automatisierte Tests |        0 |

### Testergebnis

```text
passed=21
failed=0
```

Damit ist die Iteration-2-Implementierung aus Testsicht erfolgreich.

---

## 8. Erfüllte Requirements

Folgende Requirements wurden in Iteration 2 umgesetzt und erfolgreich getestet.

| Requirement-ID | Beschreibung                                                         | Status                     |
| -------------- | -------------------------------------------------------------------- | -------------------------- |
| REQ-F-013      | Kühlungsstufen bereitstellen                                         | implementiert und getestet |
| REQ-F-014      | ausgewählte Kühlungsstufe übernehmen                                 | implementiert und getestet |
| REQ-F-015      | CPU-Temperatur im Bereich 35 °C bis 100 °C bestimmen                 | implementiert und getestet |
| REQ-F-016      | CPU-Temperatur mit Einheit °C ausgeben                               | implementiert und getestet |
| REQ-F-017      | Stromverbrauch im Bereich 8 W bis 65 W bestimmen                     | implementiert und getestet |
| REQ-F-018      | Stromverbrauch mit Einheit W ausgeben                                | implementiert und getestet |
| REQ-F-020      | Moduswechsel protokollieren                                          | implementiert und getestet |
| REQ-F-021      | Lastszenario-Wechsel protokollieren                                  | implementiert und getestet |
| REQ-F-022      | Temperaturwarnung ab mindestens 70 °C protokollieren                 | implementiert und getestet |
| REQ-F-023      | Ereigniseintrag mit definierten Feldern speichern                    | implementiert und getestet |
| REQ-NF-005     | Simulationsergebnis innerhalb von höchstens 5 Sekunden bereitstellen | erfüllt                    |
| REQ-NF-006     | numerische Werte mit Wert und Einheit anzeigen                       | erfüllt                    |

---

## 9. Vorbereitete, aber noch nicht vollständig erfüllte Requirements

| Requirement-ID | Beschreibung                                         | Bewertung                                          |
| -------------- | ---------------------------------------------------- | -------------------------------------------------- |
| REQ-F-019      | Stromverbrauch nach Thermal Throttling neu bestimmen | vorbereitet, vollständige Umsetzung in Iteration 3 |

REQ-F-019 kann erst vollständig umgesetzt und getestet werden, wenn in Iteration 3 Thermal Throttling eingeführt wurde. In Iteration 2 wurde die Stromverbrauchsberechnung bereits so gekapselt, dass sie später mit der effektiven CPU-Taktfrequenz erneut verwendet werden kann.

---

## 10. Bewertung der Architektur und des Designs

Die Architekturentscheidung, Temperaturberechnung, Stromverbrauchsberechnung und Ereignisprotokollierung in eigene Komponenten auszulagern, hat sich bewährt.

| Bewertungspunkt              | Ergebnis    |
| ---------------------------- | ----------- |
| Kapselung                    | erfolgreich |
| Erweiterbarkeit              | gegeben     |
| Testbarkeit                  | verbessert  |
| Rückwärtskompatibilität      | erhalten    |
| Vorbereitung für Iteration 3 | gegeben     |

Besonders hilfreich war die Trennung zwischen:

* `TemperatureSimulator`,
* `PowerConsumptionCalculator`,
* `EventLogger`,
* `PerformanceController`.

Dadurch blieb der `PerformanceController` weiterhin eine Koordinationsklasse und enthält keine eigene Berechnungslogik.

---

## 11. Was gut funktioniert hat

| Bereich                 | Beobachtung                                                                                   |
| ----------------------- | --------------------------------------------------------------------------------------------- |
| Iterative Erweiterung   | Die bestehende Iteration-1-Struktur konnte weiterverwendet werden.                            |
| Datenmodell-Erweiterung | `SimulationInput`, `ProcessorState` und `SimulationResult` konnten sinnvoll erweitert werden. |
| Testbarkeit             | Die neuen Serviceklassen konnten isoliert getestet werden.                                    |
| Regression              | Iteration-1-Funktionen blieben lauffähig.                                                     |
| Traceability            | Requirements, Klassen und Tests konnten klar zugeordnet werden.                               |
| Abgrenzung              | Thermal Throttling wurde bewusst nicht vorgezogen.                                            |

---

## 12. Aufgetretene Herausforderungen

| Herausforderung          | Bewertung                                                                                           |
| ------------------------ | --------------------------------------------------------------------------------------------------- |
| Abgrenzung von REQ-F-019 | Das Requirement hängt fachlich von Thermal Throttling ab und konnte deshalb nur vorbereitet werden. |
| EventLogger-Umfang       | Die Protokollierung musste begrenzt werden, damit Iteration 2 nicht zu groß wird.                   |
| Rückwärtskompatibilität  | Alte Konstruktoren mussten erhalten bleiben, damit Iteration-1-Tests weiterhin funktionieren.       |
| Ergebnisdarstellung      | Die Ausgabe musste erweitert werden, ohne die bestehende Anzeige unnötig zu verändern.              |

---

## 13. Getroffene Entscheidungen

| Entscheidung                                         | Begründung                                                                           |
| ---------------------------------------------------- | ------------------------------------------------------------------------------------ |
| Keine vollständige Throttling-Logik in Iteration 2   | Throttling ist Bestandteil von Iteration 3.                                          |
| `TemperatureSimulator` verwaltet auch Kühlungsstufen | Eine eigene `CoolingLevelManager`-Klasse wäre für Iteration 2 unnötig groß.          |
| `PowerConsumptionCalculator` bleibt unabhängig       | Die Verbrauchsberechnung kann in Iteration 3 nach Throttling wiederverwendet werden. |
| `EventLogger` wird bereits eingeführt                | Die vollständige Ereignisprotokollierung kann darauf aufbauen.                       |
| Standardkühlung `MITTEL` für alte Konstruktoren      | Rückwärtskompatibilität zur Iteration 1 bleibt erhalten.                             |

---

## 14. Verbesserungen für Iteration 3

Für Iteration 3 ergeben sich folgende konkrete Verbesserungs- und Erweiterungspunkte.

| Nr. | Verbesserung / Aufgabe                                                      | Begründung                                                   |
| --: | --------------------------------------------------------------------------- | ------------------------------------------------------------ |
|   1 | `ThermalThrottlingController` implementieren                                | Erforderlich für TF-05.                                      |
|   2 | Throttling-Stufen `Normal`, `Warnung`, `Reduziert`, `Kritisch` einführen    | Erforderlich für mehrstufiges Thermal Throttling.            |
|   3 | effektive CPU-Taktfrequenz berechnen                                        | Grundlage für realistische Throttling-Auswirkung.            |
|   4 | Stromverbrauch nach Throttling neu berechnen                                | Vollständige Umsetzung von REQ-F-019.                        |
|   5 | `EventLogger` um Throttling-Ereignisse erweitern                            | Erforderlich für vollständige TF-07.                         |
|   6 | `ProcessorState` um `effectiveClockGhz` und `throttlingLevel` erweitern     | Datenmodell für Iteration 3 vorbereiten.                     |
|   7 | `SimulationResult` um effektive Taktfrequenz und Throttling-Stufe erweitern | Ergebnisanzeige muss Iteration-3-Werte enthalten.            |
|   8 | zusätzliche Tests für Temperaturgrenzen 70 °C, 80 °C und 90 °C erstellen    | Grenzwerte sind für Throttling zentral.                      |
|   9 | Traceability Matrix auf v0.6 oder v0.7 erweitern                            | Neue Requirements und Testfälle müssen nachverfolgbar sein.  |
|  10 | Regressionstests aus Iteration 1 und 2 erneut ausführen                     | Sicherstellung, dass bestehende Funktionen erhalten bleiben. |

---

## 15. Risiken für Iteration 3

| Risiko                                                   | Auswirkung                      | Gegenmaßnahme                                                   |
| -------------------------------------------------------- | ------------------------------- | --------------------------------------------------------------- |
| Throttling verändert bestehenden Simulationsablauf stark | Regression möglich              | Controller nur gezielt erweitern und Regressionstests ausführen |
| REQ-F-019 hängt von effektiver Taktfrequenz ab           | falsche Verbrauchswerte möglich | Verbrauch erst nach Throttling berechnen                        |
| Ereignisprotokoll kann unübersichtlich werden            | erschwerte Testbarkeit          | klare Ereignistypen und getrennte Tests verwenden               |
| Temperaturgrenzen können falsch interpretiert werden     | falsche Throttling-Stufe        | Grenzwerttests für 69, 70, 79, 80, 89, 90 und 100 °C verwenden  |
| Ausgabe kann überladen werden                            | geringere Verständlichkeit      | Ergebnisanzeige strukturiert halten                             |

---

## 16. Definition of Done – Bewertung

| Kriterium                              | Status  |
| -------------------------------------- | ------- |
| Iteration-2-Planning abgeschlossen     | erfüllt |
| Architektur für Iteration 2 erstellt   | erfüllt |
| Feindesign für Iteration 2 erstellt    | erfüllt |
| neue Iteration-2-Klassen implementiert | erfüllt |
| bestehende Klassen sinnvoll erweitert  | erfüllt |
| Software kompiliert                    | erfüllt |
| Software ist lauffähig                 | erfüllt |
| automatisierte Tests erstellt          | erfüllt |
| automatisierte Tests bestanden         | erfüllt |
| Testfälle dokumentiert                 | erfüllt |
| Test Reporting erstellt                | erfüllt |
| Traceability Matrix aktualisiert       | erfüllt |
| Retrospective erstellt                 | erfüllt |
| Git-Baseline erstellt                  | erfüllt |

Die fachliche Definition of Done ist erfüllt. Die organisatorische Baseline ist erst vollständig abgeschlossen, wenn der aktuelle Stand in Git committed und als Iteration-2-Baseline markiert wurde.
