# Iteration 2 – Planning

## 1. Ziel der Iteration

Ziel der Iteration 2 ist die Erweiterung der bestehenden lauffähigen Grundsimulation aus Iteration 1.

Die Software soll weiterhin Leistungsmodus, Lastszenario, CPU-Auslastung und ungedrosselte CPU-Taktfrequenz bestimmen. Zusätzlich soll das System eine Kühlungsstufe berücksichtigen, daraus eine simulierte CPU-Temperatur berechnen, einen simulierten Stromverbrauch ableiten und erste relevante Ereignisse protokollieren.

Iteration 2 erweitert damit den Simulationsumfang, ohne Thermal Throttling bereits vollständig umzusetzen.

---

## 2. Ausgangspunkt aus Iteration 1

In Iteration 1 wurden folgende Teilfunktionalitäten umgesetzt:

| Teilfunktionalität | Beschreibung                                     | Status    |
| ------------------ | ------------------------------------------------ | --------- |
| TF-01              | Leistungsmodus mit benutzerdefiniertem Modus     | umgesetzt |
| TF-02              | Vordefinierte Lastszenarien                      | umgesetzt |
| TF-03              | Taktberechnung abhängig von Modus und Auslastung | umgesetzt |

Die Software kann damit bereits einen vollständigen Simulationslauf für Leistungsmodus, Lastszenario, CPU-Auslastung und ungedrosselte CPU-Taktfrequenz ausführen.

Die bestehenden Funktionen aus Iteration 1 bleiben Bestandteil der Software und müssen nach der Erweiterung weiterhin lauffähig sein.

---

## 3. Fachlicher Umfang der Iteration 2

In Iteration 2 werden folgende Teilfunktionalitäten umgesetzt:

| Teilfunktionalität | Beschreibung                                                 | Status in Iteration 2        |
| ------------------ | ------------------------------------------------------------ | ---------------------------- |
| TF-04              | Temperaturberechnung mit Lüfter-/Kühlungsfaktor              | umzusetzen                   |
| TF-06              | Stromverbrauch in Watt ableiten                              | umzusetzen                   |
| TF-07              | Ereignisprotokoll für Moduswechsel, Warnungen und Throttling | als erste Version umzusetzen |

---

## 4. Nicht Bestandteil der Iteration 2

Folgende Teilfunktionalitäten werden in Iteration 2 noch nicht vollständig umgesetzt:

| Teilfunktionalität / Requirement      | Grund                                                                                                                                                                          |
| ------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| TF-05 Mehrstufiges Thermal Throttling | Thermal Throttling wird erst in Iteration 3 umgesetzt, da es eine vorhandene Temperaturberechnung voraussetzt.                                                                 |
| REQ-F-019                             | Die Neuberechnung des Stromverbrauchs nach Änderung der effektiven CPU-Taktfrequenz durch Thermal Throttling kann erst vollständig umgesetzt werden, wenn TF-05 vorhanden ist. |
| REQ-F-024 bis REQ-F-029               | Diese Requirements gehören zur vollständigen Throttling-Logik der Iteration 3.                                                                                                 |
| REQ-F-030 bis REQ-F-033               | Diese Requirements gehören zur vollständigen Ereignisprotokollierung in Verbindung mit Thermal Throttling und Reset-Funktionalität.                                            |

REQ-F-019 wird in Iteration 2 fachlich vorbereitet, aber noch nicht vollständig verifiziert. Die vollständige Umsetzung erfolgt in Iteration 3 zusammen mit Thermal Throttling.

---

## 5. Requirements der Iteration 2

### 5.1 Funktionale Requirements

| Requirement-ID | Teilfunktionalität | Kurzbeschreibung                                         | Umsetzung in Iteration 2                               |
| -------------- | ------------------ | -------------------------------------------------------- | ------------------------------------------------------ |
| REQ-F-013      | TF-04              | Kühlungsstufen bereitstellen                             | vollständig umzusetzen                                 |
| REQ-F-014      | TF-04              | ausgewählte Kühlungsstufe übernehmen                     | vollständig umzusetzen                                 |
| REQ-F-015      | TF-04              | CPU-Temperatur im Bereich von 35 °C bis 100 °C bestimmen | vollständig umzusetzen                                 |
| REQ-F-016      | TF-04              | CPU-Temperatur mit Einheit °C ausgeben                   | vollständig umzusetzen                                 |
| REQ-F-017      | TF-06              | Stromverbrauch im Bereich von 8 W bis 65 W bestimmen     | vollständig umzusetzen                                 |
| REQ-F-018      | TF-06              | Stromverbrauch mit Einheit W ausgeben                    | vollständig umzusetzen                                 |
| REQ-F-019      | TF-06              | Stromverbrauch nach Thermal Throttling neu bestimmen     | nur vorbereiten, vollständige Umsetzung in Iteration 3 |
| REQ-F-020      | TF-07              | Moduswechsel protokollieren                              | als einfache Ereignisprotokollierung umzusetzen        |
| REQ-F-021      | TF-07              | Lastszenario-Wechsel protokollieren                      | als einfache Ereignisprotokollierung umzusetzen        |
| REQ-F-022      | TF-07              | Temperaturwarnung ab mindestens 70 °C protokollieren     | vollständig umzusetzen                                 |
| REQ-F-023      | TF-07              | Ereigniseintrag mit definierten Feldern speichern        | als erste Version umzusetzen                           |

---

### 5.2 Nicht-funktionale Requirements

In Iteration 2 werden folgende nicht-funktionale Requirements neu relevant:

| Requirement-ID | Kategorie       | Kurzbeschreibung                                                                       |
| -------------- | --------------- | -------------------------------------------------------------------------------------- |
| REQ-NF-005     | Performance     | Das Simulationsergebnis muss innerhalb von höchstens 5 Sekunden bereitgestellt werden. |
| REQ-NF-006     | Ausgabequalität | Numerische Simulationswerte müssen mit Wert und Einheit ausgegeben werden.             |

Folgende nicht-funktionale Requirements aus Iteration 1 gelten weiterhin:

| Requirement-ID | Kategorie                | Bedeutung für Iteration 2                                    |
| -------------- | ------------------------ | ------------------------------------------------------------ |
| REQ-NF-001     | Determinismus            | Gleiche Eingaben müssen weiterhin gleiche Ausgaben erzeugen. |
| REQ-NF-002     | Testbarkeit              | Neue Requirements müssen Testfällen zugeordnet werden.       |
| REQ-NF-003     | Robustheit               | Ungültige Eingaben müssen kontrolliert behandelt werden.     |
| REQ-NF-004     | Konfigurationsmanagement | Am Ende der Iteration muss eine Baseline erzeugt werden.     |

---

## 6. Architektur- und Designumfang

Die Architektur aus Iteration 1 bleibt bestehen und wird erweitert.

### 6.1 Weiterverwendete Komponenten

| Komponenten-ID | Komponente             | Aufgabe                                    |
| -------------- | ---------------------- | ------------------------------------------ |
| CMP-009        | UserInterface          | Eingaben erfassen und Ergebnisse anzeigen  |
| CMP-008        | PerformanceController  | Simulationsablauf koordinieren             |
| CMP-001        | PerformanceModeManager | Leistungsmodi bereitstellen und validieren |
| CMP-002        | LoadScenarioManager    | Lastszenarien bereitstellen und validieren |
| CMP-003        | ClockCalculator        | ungedrosselte CPU-Taktfrequenz berechnen   |

### 6.2 Neue oder aktivierte Komponenten in Iteration 2

| Komponenten-ID | Komponente                 | Aufgabe in Iteration 2                              |
| -------------- | -------------------------- | --------------------------------------------------- |
| CMP-004        | TemperatureSimulator       | simulierte CPU-Temperatur berechnen                 |
| CMP-006        | PowerConsumptionCalculator | simulierten Stromverbrauch berechnen                |
| CMP-007        | EventLogger                | einfache Ereignisse eines Simulationslaufs erfassen |

---

## 7. Datenmodellumfang der Iteration 2

Folgende Datenmodelle werden aus Iteration 1 weiterverwendet:

| Datenmodell      | Änderung in Iteration 2                                          |
| ---------------- | ---------------------------------------------------------------- |
| SimulationInput  | Erweiterung um die ausgewählte Kühlungsstufe                     |
| PerformanceMode  | keine grundlegende Änderung notwendig                            |
| LoadScenario     | keine grundlegende Änderung notwendig                            |
| ProcessorState   | Erweiterung um CPU-Temperatur und Stromverbrauch                 |
| SimulationResult | Erweiterung um Temperatur-, Stromverbrauchs- und Ereignisausgabe |

Zusätzlich wird voraussichtlich ein neues Datenmodell benötigt:

| Datenmodell  | Zweck                                         |
| ------------ | --------------------------------------------- |
| CoolingLevel | Beschreibung einer auswählbaren Kühlungsstufe |
| LogEntry     | Beschreibung eines Ereignisprotokolleintrags  |

Die genaue Ausgestaltung der Klassen erfolgt im Architektur- und Feindesign der Iteration 2.

---

## 8. Geplanter Simulationsablauf in Iteration 2

Der geplante Ablauf der erweiterten Simulation lautet:

1. Der Nutzer startet die Anwendung.
2. Das System stellt Leistungsmodi bereit.
3. Der Nutzer wählt einen Leistungsmodus.
4. Das System stellt Lastszenarien bereit.
5. Der Nutzer wählt ein Lastszenario.
6. Das System stellt Kühlungsstufen bereit.
7. Der Nutzer wählt eine Kühlungsstufe.
8. Das System bestimmt die CPU-Auslastung.
9. Das System berechnet die ungedrosselte CPU-Taktfrequenz.
10. Das System berechnet die CPU-Temperatur.
11. Das System berechnet den Stromverbrauch.
12. Das System protokolliert relevante einfache Ereignisse.
13. Das System erzeugt ein erweitertes SimulationResult.
14. Das System gibt Leistungsmodus, Lastszenario, Kühlungsstufe, CPU-Auslastung, CPU-Taktfrequenz, CPU-Temperatur, Stromverbrauch und relevante Ereignisse aus.

---

## 9. Geplanter Testumfang

Für Iteration 2 werden neue Testfälle für Temperaturberechnung, Stromverbrauch und einfache Ereignisprotokollierung benötigt.

| Bereich                   | Geplante Testfälle                                                                        |
| ------------------------- | ----------------------------------------------------------------------------------------- |
| Kühlungsstufen            | Prüfung, ob Niedrig, Mittel und Hoch bereitgestellt und übernommen werden                 |
| Temperaturberechnung      | Prüfung gültiger Temperaturwerte und Begrenzung auf 35 °C bis 100 °C                      |
| Temperaturausgabe         | Prüfung der Ausgabe mit Einheit °C                                                        |
| Stromverbrauchsberechnung | Prüfung gültiger Verbrauchswerte und Begrenzung auf 8 W bis 65 W                          |
| Stromverbrauchsausgabe    | Prüfung der Ausgabe mit Einheit W                                                         |
| Ereignisprotokoll         | Prüfung einfacher Ereignisse für Moduswechsel, Lastszenario-Wechsel und Temperaturwarnung |
| Regression                | Wiederholung der relevanten Tests aus Iteration 1                                         |

---

## 10. Vorgesehene Testfall-IDs

| Requirement-ID | Geplanter Testfall           |
| -------------- | ---------------------------- |
| REQ-F-013      | TC-TMP-001                   |
| REQ-F-014      | TC-TMP-002                   |
| REQ-F-015      | TC-TMP-003                   |
| REQ-F-016      | TC-TMP-004                   |
| REQ-F-017      | TC-PWR-001                   |
| REQ-F-018      | TC-PWR-002                   |
| REQ-F-019      | Vorbereitung für Iteration 3 |
| REQ-F-020      | TC-LOG-001                   |
| REQ-F-021      | TC-LOG-002                   |
| REQ-F-022      | TC-LOG-003                   |
| REQ-F-023      | TC-LOG-004                   |
| REQ-NF-005     | TC-NF-PERF-001               |
| REQ-NF-006     | TC-NF-UNIT-001               |

---

## 11. Akzeptanzkriterien

Iteration 2 gilt als abgeschlossen, wenn folgende Kriterien erfüllt sind:

| Kriterium                 | Erwartung                                                                                                       |
| ------------------------- | --------------------------------------------------------------------------------------------------------------- |
| Lauffähigkeit             | Die Software kann weiterhin gestartet und ausgeführt werden.                                                    |
| Bestandsschutz            | Die Funktionen aus Iteration 1 funktionieren weiterhin.                                                         |
| Kühlungsstufe             | Der Nutzer kann eine Kühlungsstufe auswählen.                                                                   |
| Temperaturberechnung      | Die Software berechnet eine CPU-Temperatur im Bereich von 35 °C bis 100 °C.                                     |
| Temperaturausgabe         | Die CPU-Temperatur wird mit der Einheit °C ausgegeben.                                                          |
| Stromverbrauchsberechnung | Die Software berechnet einen Stromverbrauch im Bereich von 8 W bis 65 W.                                        |
| Stromverbrauchsausgabe    | Der Stromverbrauch wird mit der Einheit W ausgegeben.                                                           |
| Ereignisprotokollierung   | Moduswechsel, Lastszenario-Wechsel und Temperaturwarnungen können als einfache Ereignisse protokolliert werden. |
| Robustheit                | Ungültige Eingaben führen nicht zu einem unkontrollierten Programmabbruch.                                      |
| Performance               | Das Simulationsergebnis wird innerhalb von höchstens 5 Sekunden bereitgestellt.                                 |
| Tests                     | Die neuen Requirements der Iteration 2 sind durch Testfälle abgedeckt.                                          |
| Traceability              | Requirements, Komponenten, Code-Dateien und Testfälle sind zuordenbar.                                          |
| Baseline                  | Der Softwarestand der Iteration 2 ist versioniert festgehalten.                                                 |

---

## 12. Risiken und Gegenmaßnahmen

| Risiko                                                  | Auswirkung                                             | Gegenmaßnahme                                                            |
| ------------------------------------------------------- | ------------------------------------------------------ | ------------------------------------------------------------------------ |
| Iteration 2 wird zu groß                                | Iteration kann nicht sauber abgeschlossen werden       | Thermal Throttling konsequent auf Iteration 3 verschieben                |
| Ereignisprotokoll wird zu umfangreich                   | Fokus der Iteration verschiebt sich                    | In Iteration 2 nur einfache Ereignisse protokollieren                    |
| REQ-F-019 hängt von Thermal Throttling ab               | vollständige Verifikation in Iteration 2 nicht möglich | REQ-F-019 nur vorbereiten und in Iteration 3 abschließen                 |
| Berechnungslogik wird im Controller vermischt           | Architektur wird unübersichtlich                       | Temperatur, Stromverbrauch und Logging in eigene Komponenten auslagern   |
| Bestehende Funktionen aus Iteration 1 werden beschädigt | Rückschritt gegenüber Baseline                         | Regressionstests aus Iteration 1 erneut ausführen                        |
| Ergebnisdaten werden unübersichtlich                    | Ausgabe wird schwer nachvollziehbar                    | SimulationResult strukturiert erweitern                                  |
| Traceability wird unvollständig                         | Nachweisführung wird schwach                           | neue Requirements direkt mit Komponenten, Code und Testfällen verknüpfen |

---

## 13. Definition of Done

Iteration 2 ist fertig, wenn:

* TF-04 Temperaturberechnung umgesetzt ist,
* TF-06 Stromverbrauchsberechnung umgesetzt ist,
* eine erste Version von TF-07 Ereignisprotokollierung umgesetzt ist,
* Kühlungsstufen ausgewählt und verarbeitet werden können,
* Temperaturwerte mit Einheit °C ausgegeben werden,
* Stromverbrauchswerte mit Einheit W ausgegeben werden,
* Temperaturwarnungen ab mindestens 70 °C protokolliert werden,
* die Software nach der Erweiterung weiterhin lauffähig ist,
* die Funktionen aus Iteration 1 weiterhin funktionieren,
* neue Unit- und Integrationstests für Iteration 2 vorhanden sind,
* relevante Regressionstests aus Iteration 1 erneut bestanden wurden,
* die Testfälle dokumentiert wurden,
* das Test Reporting für Iteration 2 erstellt wurde,
* die Traceability Matrix für Iteration 2 aktualisiert wurde,
* eine Git-Baseline für Iteration 2 erstellt wurde.
