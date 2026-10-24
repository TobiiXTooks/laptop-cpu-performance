# Iteration 3 – Testreporting

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die Durchführung und Auswertung der Softwaretests für Iteration 3 des Projekts **Laptop – Prozessorleistung**.

Die Tests prüfen die in Iteration 3 umgesetzten Erweiterungen:

* Thermal Throttling,
* Throttling-Stufen,
* effektive CPU-Taktfrequenz,
* Stromverbrauch nach Thermal Throttling,
* Ereignisprotokollierung,
* Reset-Funktion,
* Vererbung durch `SimulationOption`,
* Interface-Nutzung durch `Displayable`,
* Regression der Funktionen aus Iteration 1 und Iteration 2.

---

## 2. Testgrundlage

Die Testausführung basiert auf folgenden Artefakten:

| Artefakt                        | Zweck                                                        |
| ------------------------------- | ------------------------------------------------------------ |
| `Iteration3_Planning.md`        | Festlegung des Funktionsumfangs der Iteration 3              |
| `Iteration3_Architectur.md`     | Architekturgrundlage der Iteration 3                         |
| `Iteration3_Feindesign.md`      | detaillierte Klassen-, Methoden- und Datenmodellbeschreibung |
| `Iteration3_Testfaelle.md`      | geplante Testfälle der Iteration 3                           |
| `Iteration3UnitTestRunner.java` | automatisierte Testausführung                                |
| Java-Implementierung in `src/`  | zu testender Quellcode                                       |

---

## 3. Testumgebung

| Bereich               | Wert                                 |
| --------------------- | ------------------------------------ |
| Betriebssystem        | Windows                              |
| Entwicklungsumgebung  | IntelliJ IDEA 2024.3.5               |
| Java-Laufzeitumgebung | Eclipse Temurin 23.0.2               |
| Testart               | automatisierter Java-TestRunner      |
| Testdatei             | `test/Iteration3UnitTestRunner.java` |
| Testframework         | kein externes Framework              |
| Ausführung            | IntelliJ IDEA / Java-Konsole         |

---

## 4. Ausgeführter Testbefehl

Die Tests wurden über IntelliJ IDEA ausgeführt.

Auszug aus der Konsolenausgabe:

```text
java
-classpath "...out\test\Iteration 3;...out\production\Iteration 3"
Iteration3UnitTestRunner
```

Die Testdatei wurde erfolgreich gestartet:

```text
Iteration 3 – Unit- und Integrationstests
================================================
```

---

## 5. Zusammenfassung des Testergebnisses

| Kennzahl              |                            Ergebnis |
| --------------------- | ----------------------------------: |
| Ausgeführte Tests     |                                  56 |
| Bestandene Tests      |                                  56 |
| Fehlgeschlagene Tests |                                   0 |
| Erfolgsquote          |                               100 % |
| Programmende          | `Process finished with exit code 0` |

Gesamtergebnis:

```text
Testergebnis Iteration 3
Bestanden: 56
Fehlgeschlagen: 0

Process finished with exit code 0
```

---

## 6. Ausgeführte Testbereiche

### 6.1 Thermal-Throttling-Tests

| Testbereich            | Anzahl | Ergebnis  |
| ---------------------- | -----: | --------- |
| Throttling-Stufen      |      4 | bestanden |
| Temperatur-Grenzwerte  |      6 | bestanden |
| Ungültige Temperaturen |      2 | bestanden |
| Gesamt                 |     12 | bestanden |

Geprüft wurden die Stufen:

| Temperaturbereich | Erwartete Stufe | Ergebnis  |
| ----------------- | --------------- | --------- |
| 35 °C bis 69 °C   | `NORMAL`        | bestanden |
| 70 °C bis 79 °C   | `WARNUNG`       | bestanden |
| 80 °C bis 89 °C   | `REDUZIERT`     | bestanden |
| 90 °C bis 100 °C  | `KRITISCH`      | bestanden |

Zusätzlich wurden ungültige Temperaturen unterhalb und oberhalb des gültigen Bereichs geprüft.

---

### 6.2 Tests zur effektiven CPU-Taktfrequenz

| Testfallgruppe                  | Anzahl | Ergebnis  |
| ------------------------------- | -----: | --------- |
| Effektiver Takt bei `Normal`    |      1 | bestanden |
| Effektiver Takt bei `Warnung`   |      1 | bestanden |
| Effektiver Takt bei `Reduziert` |      1 | bestanden |
| Effektiver Takt bei `Kritisch`  |      1 | bestanden |
| Speicherung im `ProcessorState` |      1 | bestanden |
| Gesamt                          |      5 | bestanden |

Geprüfte Berechnungsregeln:

| Throttling-Stufe | Erwarteter Faktor | Ergebnis  |
| ---------------- | ----------------: | --------- |
| `NORMAL`         |             100 % | bestanden |
| `WARNUNG`        |             100 % | bestanden |
| `REDUZIERT`      |              80 % | bestanden |
| `KRITISCH`       |              60 % | bestanden |

---

### 6.3 Tests zum Stromverbrauch nach Throttling

| Testfall                                          | Ergebnis  |
| ------------------------------------------------- | --------- |
| Verbrauch basiert auf effektiver CPU-Taktfrequenz | bestanden |
| Mindestwert des Stromverbrauchs wird eingehalten  | bestanden |
| Maximalwert des Stromverbrauchs wird eingehalten  | bestanden |
| Fehlender effektiver Takt wird abgelehnt          | bestanden |

Damit ist nachgewiesen, dass der Stromverbrauch in Iteration 3 nicht mehr nur auf Basis des ungedrosselten Takts berechnet wird, sondern die durch Thermal Throttling reduzierte effektive CPU-Taktfrequenz berücksichtigt.

---

### 6.4 Tests zur Ereignisprotokollierung

| Ereignis / Funktion                       | Ergebnis  |
| ----------------------------------------- | --------- |
| `TEMPERATURWARNUNG` ab 70 °C              | bestanden |
| keine `TEMPERATURWARNUNG` unter 70 °C     | bestanden |
| `THROTTLING_AKTIVIERT` bei `REDUZIERT`    | bestanden |
| `THROTTLING_AKTIVIERT` bei `KRITISCH`     | bestanden |
| kein aktives Throttling bei `NORMAL`      | bestanden |
| `THROTTLING_GEÄNDERT` bei Zustandswechsel | bestanden |
| Ereignisse ab Startindex                  | bestanden |
| chronologische Ereignisausgabe            | bestanden |
| `SIMULATION_ZURÜCKGESETZT`                | bestanden |

Die Ereignisprotokollierung erfüllt damit die Anforderungen der Iteration 3.

---

### 6.5 Integrationstests

| Testfall                                                        | Ergebnis  |
| --------------------------------------------------------------- | --------- |
| vollständiger Simulationslauf mit kritischem Throttling         | bestanden |
| vollständiger Simulationslauf ohne aktives Throttling           | bestanden |
| Temperaturänderung führt zu neuer Throttling-Stufe              | bestanden |
| Stromverbrauch wird nach Throttling neu berechnet               | bestanden |
| Ergebnis enthält nur Logeinträge des aktuellen Simulationslaufs | bestanden |
| Ergebnistext enthält Throttling und Einheiten                   | bestanden |

Die Integrationstests zeigen, dass der vollständige Ablauf funktioniert:

```text
Eingabe
→ Leistungsmodus
→ Lastszenario
→ Kühlungsstufe
→ ungedrosselte CPU-Taktfrequenz
→ Temperatur
→ Throttling-Stufe
→ effektive CPU-Taktfrequenz
→ Stromverbrauch
→ Ereignisprotokoll
→ SimulationResult
```

---

### 6.6 Objektorientierte Tests

| Testfall                                       | Ergebnis  |
| ---------------------------------------------- | --------- |
| `PerformanceMode` erbt von `SimulationOption`  | bestanden |
| `LoadScenario` erbt von `SimulationOption`     | bestanden |
| `CoolingLevel` erbt von `SimulationOption`     | bestanden |
| `LogEntry` implementiert `Displayable`         | bestanden |
| `SimulationResult` implementiert `Displayable` | bestanden |

Damit wurden die in Iteration 3 ergänzten objektorientierten Strukturänderungen erfolgreich geprüft.

---

### 6.7 Regressionstests aus Iteration 1 und Iteration 2

| Bereich                                       | Ergebnis  |
| --------------------------------------------- | --------- |
| Leistungsmodus `ENERGY_SAVE` bleibt verfügbar | bestanden |
| Leistungsmodus `BALANCED` bleibt verfügbar    | bestanden |
| Leistungsmodus `PERFORMANCE` bleibt verfügbar | bestanden |
| Lastszenario `IDLE` bleibt verfügbar          | bestanden |
| Lastszenario `GAMING` bleibt verfügbar        | bestanden |
| Benutzerdefinierter Modus bleibt gültig       | bestanden |
| Kühlungsstufe `NIEDRIG` bleibt verfügbar      | bestanden |
| Kühlungsstufe `MITTEL` bleibt verfügbar       | bestanden |
| Kühlungsstufe `HOCH` bleibt verfügbar         | bestanden |
| Temperaturberechnung bleibt deterministisch   | bestanden |
| Basisevents bleiben erhalten                  | bestanden |

Die Regressionstests zeigen, dass die Funktionen aus Iteration 1 und Iteration 2 durch die Erweiterungen der Iteration 3 nicht beschädigt wurden.

---

### 6.8 Nicht-funktionale Tests

| Testfall                      | Ergebnis  |
| ----------------------------- | --------- |
| deterministisches Ergebnis    | bestanden |
| Laufzeit unter 5 Sekunden     | bestanden |
| Ausgabe enthält Einheiten     | bestanden |
| keine externen Abhängigkeiten | bestanden |

Damit sind auch die relevanten nicht-funktionalen Anforderungen der Iteration 3 erfüllt.

---

## 7. Detaillierte Konsolenausgabe

```text
Iteration 3 – Unit- und Integrationstests
================================================
[OK]   TC-THR-001 – Throttling-Stufe Normal
[OK]   TC-THR-002 – Throttling-Stufe Warnung
[OK]   TC-THR-003 – Throttling-Stufe Reduziert
[OK]   TC-THR-004 – Throttling-Stufe Kritisch
[OK]   TC-THR-005 – Grenzwert 69 °C
[OK]   TC-THR-006 – Grenzwert 70 °C
[OK]   TC-THR-007 – Grenzwert 79 °C
[OK]   TC-THR-008 – Grenzwert 80 °C
[OK]   TC-THR-009 – Grenzwert 89 °C
[OK]   TC-THR-010 – Grenzwert 90 °C
[OK]   TC-THR-011 – Ungültige Temperatur unter Minimum
[OK]   TC-THR-012 – Ungültige Temperatur über Maximum
[OK]   TC-ECLK-001 – Effektiver Takt bei Normal
[OK]   TC-ECLK-002 – Effektiver Takt bei Warnung
[OK]   TC-ECLK-003 – Effektiver Takt bei Reduziert
[OK]   TC-ECLK-004 – Effektiver Takt bei Kritisch
[OK]   TC-ECLK-005 – Throttling-Werte im ProcessorState
[OK]   TC-PWR-I3-001 – Verbrauch basiert auf effektivem Takt
[OK]   TC-PWR-I3-002 – Mindestwert Stromverbrauch
[OK]   TC-PWR-I3-003 – Maximalwert Stromverbrauch
[OK]   TC-PWR-I3-004 – Fehlender effektiver Takt
[OK]   TC-LOG-I3-001 – Temperaturwarnung ab 70 °C
[OK]   TC-LOG-I3-002 – Keine Temperaturwarnung unter 70 °C
[OK]   TC-LOG-I3-003 – Throttling aktiviert bei Reduziert
[OK]   TC-LOG-I3-004 – Throttling aktiviert bei Kritisch
[OK]   TC-LOG-I3-005 – Kein aktives Throttling bei Normal
[OK]   TC-LOG-I3-006 – Throttling-Stufe geändert
[OK]   TC-LOG-I3-007 – Ereignisse ab Startindex
[OK]   TC-LOG-I3-008 – Chronologische Ereignisausgabe
[OK]   TC-LOG-I3-009 – Reset-Ereignis wird protokolliert
[OK]   TC-INT-I3-001 – Vollständiger Lauf mit kritischem Throttling
[OK]   TC-INT-I3-002 – Vollständiger Lauf ohne aktives Throttling
[OK]   TC-INT-I3-003 – Temperaturänderung führt zu neuer Stufe
[OK]   TC-INT-I3-004 – Verbrauch nach Throttling neu berechnet
[OK]   TC-INT-I3-005 – Nur aktuelle Logeinträge im Ergebnis
[OK]   TC-INT-I3-006 – Ergebnistext enthält Throttling und Einheiten
[OK]   TC-OO-I3-001 – PerformanceMode erbt von SimulationOption
[OK]   TC-OO-I3-002 – LoadScenario erbt von SimulationOption
[OK]   TC-OO-I3-003 – CoolingLevel erbt von SimulationOption
[OK]   TC-OO-I3-004 – LogEntry implementiert Displayable
[OK]   TC-OO-I3-005 – SimulationResult implementiert Displayable
[OK]   REG-I1-001 – ENERGY_SAVE bleibt verfügbar
[OK]   REG-I1-002 – BALANCED bleibt verfügbar
[OK]   REG-I1-003 – PERFORMANCE bleibt verfügbar
[OK]   REG-I1-004 – IDLE bleibt verfügbar
[OK]   REG-I1-005 – GAMING bleibt verfügbar
[OK]   REG-I1-006 – Benutzerdefinierter Modus bleibt gültig
[OK]   REG-I2-001 – Kühlungsstufe NIEDRIG bleibt verfügbar
[OK]   REG-I2-002 – Kühlungsstufe MITTEL bleibt verfügbar
[OK]   REG-I2-003 – Kühlungsstufe HOCH bleibt verfügbar
[OK]   REG-I2-004 – Temperaturberechnung bleibt deterministisch
[OK]   REG-I2-005 – Basisevents bleiben erhalten
[OK]   TC-NF-I3-001 – Deterministisches Ergebnis
[OK]   TC-NF-I3-002 – Laufzeit unter 5 Sekunden
[OK]   TC-NF-I3-003 – Ausgabe enthält Einheiten
[OK]   TC-NF-I3-004 – Keine externen Abhängigkeiten
================================================
Testergebnis Iteration 3
Bestanden: 56
Fehlgeschlagen: 0

Process finished with exit code 0
```

---

## 8. Bewertung der Testergebnisse

Die Testergebnisse zeigen, dass die Implementierung der Iteration 3 die geplanten funktionalen und nicht-funktionalen Anforderungen erfüllt.

Besonders relevant ist:

| Bereich                        | Bewertung                                                  |
| ------------------------------ | ---------------------------------------------------------- |
| Thermal Throttling             | vollständig umgesetzt                                      |
| Effektive CPU-Taktfrequenz     | korrekt berechnet                                          |
| Stromverbrauch nach Throttling | korrekt neu berechnet                                      |
| Ereignisprotokollierung        | vollständig funktionsfähig                                 |
| Reset-Funktion                 | erfolgreich nachgewiesen                                   |
| Vererbung / Interface          | erfolgreich umgesetzt                                      |
| Regression                     | keine Fehler in Funktionen aus Iteration 1 und 2 erkennbar |

---

## 9. Abweichungen und Fehlerbehebungen

Während der finalen Testausführung traten keine fehlgeschlagenen Tests auf.

| Punkt                                  | Ergebnis |
| -------------------------------------- | -------- |
| Kompilierfehler                        | keine    |
| Laufzeitfehler                         | keine    |
| Fehlgeschlagene Tests                  | keine    |
| Nachträgliche Fehlerbehebung notwendig | nein     |

---

## 10. Abdeckung der Iteration-3-Anforderungen

| Requirement | Testnachweis                                                    |
| ----------- | --------------------------------------------------------------- |
| REQ-F-019   | Stromverbrauch wird nach Throttling neu berechnet               |
| REQ-F-024   | Throttling-Stufen werden korrekt bestimmt                       |
| REQ-F-025   | `Normal` und `Warnung` behalten 100 % Takt                      |
| REQ-F-026   | `Reduziert` reduziert auf 80 %                                  |
| REQ-F-027   | `Kritisch` reduziert auf 60 %                                   |
| REQ-F-028   | Throttling-Stufe und effektiver Takt werden ausgegeben          |
| REQ-F-029   | Temperaturänderung führt zu neuer Throttling-Stufe              |
| REQ-F-030   | `THROTTLING_AKTIVIERT` wird protokolliert                       |
| REQ-F-031   | `THROTTLING_GEÄNDERT` wird protokolliert                        |
| REQ-F-032   | Ereignisprotokoll wird chronologisch und laufbezogen ausgegeben |
| REQ-F-033   | Reset-Ereignis wird protokolliert                               |
| REQ-NF-001  | deterministische Ergebnisse                                     |
| REQ-NF-004  | objektorientierte Struktur mit Vererbung                        |
| REQ-NF-005  | Laufzeit unter 5 Sekunden                                       |
| REQ-NF-006  | Ausgabe mit Einheiten                                           |
| REQ-NF-007  | keine externen Abhängigkeiten                                   |

---

## 11. Fazit

Die Testausführung der Iteration 3 war erfolgreich.

Alle 56 automatisierten Tests wurden bestanden. Es gab keine Kompilierfehler, keine Laufzeitfehler und keine fehlgeschlagenen Testfälle.

Damit ist die Implementierung der Iteration 3 aus Testsicht abgeschlossen. Die Software ist nach Iteration 3 lauffähig und erfüllt die geplanten Erweiterungen.

---
