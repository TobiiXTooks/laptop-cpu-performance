# Iteration 2 – Test Reporting

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die tatsächliche Testausführung für Iteration 2 des Laptop-Prozessorleistungsmoduls.

Das Test Reporting hält fest:

* welche Tests ausgeführt wurden,
* welche Testobjekte geprüft wurden,
* welche Requirements durch die Tests verifiziert wurden,
* welches Ergebnis die Testausführung geliefert hat,
* ob Fehler aufgetreten sind,
* ob die Iteration-2-Software aus Testsicht freigegeben werden kann.

Die Tests beziehen sich auf die Erweiterung der Grundsimulation um:

* Kühlungsstufe,
* CPU-Temperatur,
* Stromverbrauch,
* einfache Ereignisprotokollierung.

Thermal Throttling ist nicht Bestandteil der Iteration 2 und wurde daher nicht vollständig getestet.

---

## 2. Testgegenstand

Getestet wurde die lauffähige Softwareversion der Iteration 2.

### 2.1 Neue oder erweiterte Funktionen

| Bereich                 | Beschreibung                                                                                         |
| ----------------------- | ---------------------------------------------------------------------------------------------------- |
| Kühlungsstufe           | Das System stellt `NIEDRIG`, `MITTEL` und `HOCH` bereit und übernimmt die ausgewählte Kühlungsstufe. |
| Temperaturberechnung    | Das System berechnet eine simulierte CPU-Temperatur im Bereich von 35 °C bis 100 °C.                 |
| Stromverbrauch          | Das System berechnet einen simulierten Stromverbrauch im Bereich von 8 W bis 65 W.                   |
| Ereignisprotokollierung | Das System protokolliert Moduswechsel, Lastszenario-Wechsel und Temperaturwarnungen.                 |
| Ergebnisanzeige         | Das System gibt CPU-Auslastung, Taktfrequenz, Temperatur und Stromverbrauch mit Einheiten aus.       |
| Regression              | Bestehende Funktionen aus Iteration 1 bleiben lauffähig.                                             |

---

## 3. Testumgebung

| Eigenschaft                | Wert                                                                          |
| -------------------------- | ----------------------------------------------------------------------------- |
| Programmiersprache         | Java                                                                          |
| Testausführung             | Konsolenbasierter TestRunner                                                  |
| Testdatei                  | `test/Iteration2UnitTestRunner.java`                                          |
| Produktivcode              | `src/`                                                                        |
| Ausgabeordner              | `out/`                                                                        |
| Testart                    | automatisierte Unit-, Integrations-, Regressions- und nicht-funktionale Tests |
| Zusätzliche Testframeworks | keine                                                                         |

---

## 4. Ergebnis der Kompilierung

| Schritt                         | Ergebnis    |
| ------------------------------- | ----------- |
| Kompilierung des Produktivcodes | erfolgreich |
| Kompilierung des TestRunners    | erfolgreich |
| Start des TestRunners           | erfolgreich |

Es traten keine Kompilierungsfehler auf.

---

## 5. Gesamtergebnis der automatisierten Tests

| Kennzahl                               | Ergebnis |
| -------------------------------------- | -------: |
| Ausgeführte automatisierte Tests       |       21 |
| Bestandene Tests                       |       21 |
| Fehlgeschlagene Tests                  |        0 |
| Nicht ausgeführte automatisierte Tests |        0 |

### Zusammenfassung der Konsolenausgabe

```text
java -cp "out/production/Iteration 2" Iteration2UnitTestRunner

passed=21
failed=0
TC-TMP-001;BESTANDEN;
TC-TMP-002;BESTANDEN;
TC-TMP-NEG-001;BESTANDEN;
TC-TMP-003;BESTANDEN;
TC-TMP-003A;BESTANDEN;
TC-TMP-003B;BESTANDEN;
TC-TMP-003C;BESTANDEN;
TC-TMP-004;BESTANDEN;
TC-PWR-001;BESTANDEN;
TC-PWR-001A;BESTANDEN;
TC-PWR-001B;BESTANDEN;
TC-PWR-002;BESTANDEN;
TC-LOG-001;BESTANDEN;
TC-LOG-002;BESTANDEN;
TC-LOG-003;BESTANDEN;
TC-LOG-004;BESTANDEN;
REG-I1-001;BESTANDEN;
REG-I1-002;BESTANDEN;
TC-INT-001;BESTANDEN;
TC-NF-PERF-001;BESTANDEN;
TC-NF-UNIT-001;BESTANDEN;

Process finished with exit code 0

```

Damit wurden alle automatisierten Tests der Iteration 2 erfolgreich bestanden.

---

## 6. Testergebnisse nach Testbereich

| Testbereich               | Anzahl Tests | Ergebnis  |
| ------------------------- | -----------: | --------- |
| Kühlungsstufen            |            3 | bestanden |
| Temperaturberechnung      |            5 | bestanden |
| Stromverbrauchsberechnung |            4 | bestanden |
| Ereignisprotokollierung   |            4 | bestanden |
| Regression Iteration 1    |            2 | bestanden |
| Integration               |            1 | bestanden |
| Nicht-funktionale Tests   |            2 | bestanden |

---

## 7. Requirement-Abdeckung durch ausgeführte Tests

| Requirement-ID | Kurzbeschreibung                                                     | Testfälle                                         | Ergebnis                                      |
| -------------- | -------------------------------------------------------------------- | ------------------------------------------------- | --------------------------------------------- |
| REQ-F-013      | Kühlungsstufen bereitstellen                                         | TC-TMP-001                                        | bestanden                                     |
| REQ-F-014      | ausgewählte Kühlungsstufe übernehmen                                 | TC-TMP-002, TC-TMP-NEG-001                        | bestanden                                     |
| REQ-F-015      | CPU-Temperatur im Bereich von 35 °C bis 100 °C bestimmen             | TC-TMP-003, TC-TMP-003A, TC-TMP-003B, TC-TMP-003C | bestanden                                     |
| REQ-F-016      | CPU-Temperatur mit Einheit °C ausgeben                               | TC-TMP-004                                        | bestanden                                     |
| REQ-F-017      | Stromverbrauch im Bereich von 8 W bis 65 W bestimmen                 | TC-PWR-001, TC-PWR-001A, TC-PWR-001B              | bestanden                                     |
| REQ-F-018      | Stromverbrauch mit Einheit W ausgeben                                | TC-PWR-002                                        | bestanden                                     |
| REQ-F-019      | Stromverbrauch nach Thermal Throttling neu bestimmen                 | Vorbereitung für Iteration 3                      | nicht vollständig Bestandteil von Iteration 2 |
| REQ-F-020      | Moduswechsel protokollieren                                          | TC-LOG-001                                        | bestanden                                     |
| REQ-F-021      | Lastszenario-Wechsel protokollieren                                  | TC-LOG-002                                        | bestanden                                     |
| REQ-F-022      | Temperaturwarnung ab mindestens 70 °C protokollieren                 | TC-LOG-003                                        | bestanden                                     |
| REQ-F-023      | Ereigniseintrag mit definierten Feldern speichern                    | TC-LOG-004                                        | bestanden                                     |
| REQ-NF-005     | Simulationsergebnis innerhalb von höchstens 5 Sekunden bereitstellen | TC-NF-PERF-001                                    | bestanden                                     |
| REQ-NF-006     | numerische Werte mit Wert und Einheit anzeigen                       | TC-NF-UNIT-001                                    | bestanden                                     |

---

## 8. Regressionsergebnis für Iteration 1

Die Regressionstests prüfen, ob die bestehende Funktionalität aus Iteration 1 durch die Erweiterungen der Iteration 2 nicht beschädigt wurde.

| Testfall-ID | Geprüfte Funktion aus Iteration 1                                                    | Ergebnis  |
| ----------- | ------------------------------------------------------------------------------------ | --------- |
| REG-I1-001  | Bestehender `SimulationInput`-Konstruktor ohne Kühlungsstufe funktioniert weiterhin. | bestanden |
| REG-I1-002  | Taktberechnung für Modus `BALANCED` und Szenario `OFFICE` liefert weiterhin 2.0 GHz. | bestanden |

### Bewertung

Die Regressionstests zeigen, dass die Iteration-1-Funktionen weiterhin lauffähig sind. Die Erweiterung um Kühlungsstufe, Temperatur, Stromverbrauch und Ereignisprotokollierung hat die bisherige Grundsimulation nicht beschädigt.

---

## 9. Bewertung der nicht-funktionalen Tests

### 9.1 Performance

| Testfall-ID    | Erwartung                                                                   | Ergebnis  |
| -------------- | --------------------------------------------------------------------------- | --------- |
| TC-NF-PERF-001 | Simulationsergebnis wird innerhalb von höchstens 5 Sekunden bereitgestellt. | bestanden |

Die Berechnung der Simulation ist einfach und erfolgt ohne erkennbare Laufzeitprobleme.

### 9.2 Ausgabequalität

| Testfall-ID    | Erwartung                                        | Ergebnis  |
| -------------- | ------------------------------------------------ | --------- |
| TC-NF-UNIT-001 | Numerische Werte werden mit Einheiten angezeigt. | bestanden |

Die Ausgabe enthält die Einheiten `%`, `GHz`, `°C` und `W`.

---

## 10. Fehler und Abweichungen

| Kategorie                                | Ergebnis                                                                                       |
| ---------------------------------------- | ---------------------------------------------------------------------------------------------- |
| Kompilierungsfehler                      | keine                                                                                          |
| Laufzeitfehler während der Tests         | keine                                                                                          |
| Fehlgeschlagene Tests                    | keine                                                                                          |
| Abweichungen vom Feindesign              | keine festgestellt                                                                             |
| Nicht vollständig getestete Requirements | REQ-F-019, da vollständige Verifikation erst mit Thermal Throttling in Iteration 3 möglich ist |

---

## 11. Bekannte Einschränkungen

| Einschränkung                                                                              | Begründung                                                                                                |
| ------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------- |
| Thermal Throttling ist noch nicht aktiv.                                                   | Thermal Throttling ist Bestandteil von Iteration 3.                                                       |
| Effektive CPU-Taktfrequenz entspricht der ungedrosselten CPU-Taktfrequenz.                 | In Iteration 2 gibt es noch keine temperaturbedingte Taktreduzierung.                                     |
| Ereignisse für `THROTTLING_AKTIVIERT` und `THROTTLING_GEÄNDERT` werden noch nicht erzeugt. | Diese Ereignisse benötigen die Throttling-Logik aus Iteration 3.                                          |
| REQ-F-019 ist nur vorbereitet.                                                             | Die Neuberechnung des Stromverbrauchs nach Throttling kann erst nach Umsetzung von TF-05 getestet werden. |

---

## 12. Testfazit

Die automatisierten Tests der Iteration 2 wurden erfolgreich ausgeführt.

Alle 21 automatisierten Tests wurden bestanden. Es wurden keine Kompilierungsfehler, Laufzeitfehler oder fehlgeschlagenen Testfälle festgestellt.

Die Tests zeigen, dass:

* Kühlungsstufen bereitgestellt und verarbeitet werden,
* CPU-Temperaturen korrekt berechnet und begrenzt werden,
* Stromverbrauchswerte korrekt berechnet und begrenzt werden,
* Temperatur und Stromverbrauch mit Einheiten ausgegeben werden,
* Moduswechsel, Lastszenario-Wechsel und Temperaturwarnungen protokolliert werden,
* Ereigniseinträge die vorgesehenen Simulationsdaten enthalten,
* der vollständige Iteration-2-Ablauf funktioniert,
* die bestehende Iteration-1-Funktionalität weiterhin lauffähig ist,
* die nicht-funktionalen Anforderungen zu Performance und Ausgabequalität erfüllt werden.

Aus Testsicht ist die Implementierung der Iteration 2 für die weitere Dokumentation und Traceability-Fortschreibung freigegeben.
