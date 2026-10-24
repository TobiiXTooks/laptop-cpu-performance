# Iteration 1 – Testdokumentation / Testfälle

## 1. Zweck der Testdokumentation

Diese Testdokumentation beschreibt die geplanten Tests für Iteration 1 des Laptop-Prozessorleistungsmoduls.

Ziel ist es, die in Iteration 1 umgesetzten Funktionen systematisch zu prüfen und die Nachverfolgbarkeit von Requirements zu Testfällen vorzubereiten.

In Iteration 1 werden geprüft:

- Auswahl und Validierung von Leistungsmodi,
- Auswahl und Validierung von Lastszenarien,
- Zuordnung der CPU-Auslastung,
- Berechnung der ungedrosselten CPU-Taktfrequenz,
- Begrenzung der CPU-Taktfrequenz auf den gültigen Wertebereich,
- Erzeugung und Ausgabe des Simulationsergebnisses,
- kontrollierter Umgang mit ungültigen Eingaben.

Nicht geprüft werden Temperaturberechnung, Thermal Throttling, Stromverbrauchsberechnung und Ereignisprotokollierung, da diese Funktionen nicht Bestandteil von Iteration 1 sind.

---

## 2. Testgrundlage

Die Tests beziehen sich auf folgende aktiven Komponenten:

| Komponenten-ID | Komponente | Testrelevanz |
|---|---|---|
| CMP-009 | `UserInterface` | Eingabe und Ausgabe der Simulation |
| CMP-008 | `PerformanceController` | Ablaufsteuerung und Integration der Serviceklassen |
| CMP-001 | `PerformanceModeManager` | Leistungsmodusverwaltung und Validierung |
| CMP-002 | `LoadScenarioManager` | Lastszenarioverwaltung und Validierung |
| CMP-003 | `ClockCalculator` | Berechnung und Begrenzung der CPU-Taktfrequenz |

---

## 3. Teststrategie für Iteration 1

Für Iteration 1 werden statische und dynamische Tests kombiniert.

### 3.1 Statischer Test

Der statische Test erfolgt ohne Ausführung des Programms. Er wird als Review der Implementierung, der Klassenzuordnung und der Benennung durchgeführt.

Ziele des statischen Tests:

- Prüfen, ob die Klassenstruktur zum Feindesign passt,
- Prüfen, ob die Verantwortlichkeiten der Klassen eingehalten wurden,
- Prüfen, ob keine Funktionen aus späteren Iterationen unkontrolliert vorgezogen wurden,
- Prüfen, ob Namensgebung, Verständlichkeit und Wartbarkeit ausreichend sind,
- Prüfen, ob offensichtliche Fehler in Bedingungen, Wertebereichen oder Rückgabewerten vorhanden sind.

### 3.2 Dynamischer Test

Der dynamische Test erfolgt durch Ausführung der Software bzw. durch Aufruf einzelner Klassen und Methoden.

Ziele des dynamischen Tests:

- Finden von Fehlern in der Taktberechnung,
- Finden von Fehlern im Zusammenspiel zwischen Controller und Services,
- Prüfen der Robustheit bei ungültigen Eingaben,
- Prüfen der korrekten Ergebnisbildung,
- Prüfen der wiederholbaren Ausgabe bei gleichen Eingaben.

---

## 4. Testphasen

| Testphase | Beschreibung | Anwendung in Iteration 1 |
|---|---|---|
| Modultest | Einzelne Klassen oder Methoden werden isoliert geprüft. | Managerklassen, `ClockCalculator`, Datenklassen |
| Integrationstest | Zusammenspiel mehrerer Komponenten wird geprüft. | `PerformanceController` mit Managerklassen und `ClockCalculator` |
| Manuelle Validierung | Lauffähige Anwendung wird aus Benutzersicht ausgeführt. | Start über `Main.java` und Eingabe über Konsole |

Ein System- oder Abnahmetest wird in Iteration 1 nicht durchgeführt.

---

## 5. Testtechniken

| Testtechnik | Einsatz in Iteration 1 |
|---|---|
| Black-Box-Test | Prüfung sichtbarer Eingabe-Ausgabe-Beziehungen, z. B. Modus, Szenario, CPU-Auslastung und CPU-Takt. |
| Entscheidungstabellen-Test | Prüfung relevanter Kombinationen aus Modus, Szenario und benutzerdefinierten Werten. |
| White-Box-Test | Prüfung wichtiger Programmzweige, z. B. gültig/ungültig, fester/custom Modus, Grenzwert kleiner/größer als erlaubt. |
| Schnittstellentest | Prüfung der Übergaben zwischen `UserInterface`, `PerformanceController`, Managerklassen und `ClockCalculator`. |
| Test objektorientierter Software | Prüfung einzelner Klassen, Methoden, Objektzustände und Objektinteraktionen. |

---

## 6. Entscheidungstabelle für Eingabekombinationen

| Regel | Moduskennung | Szenariokennung | Custom-Werte | Erwartete Aktion |
|---|---|---|---|---|
| R1 | gültiger fester Modus | gültig | nicht erforderlich | Simulation wird ausgeführt. |
| R2 | `CUSTOM` | gültig | gültig | Simulation wird ausgeführt. |
| R3 | `CUSTOM` | gültig | Taktwert ungültig | Simulation wird abgelehnt. |
| R4 | `CUSTOM` | gültig | Power-Limit ungültig | Simulation wird abgelehnt. |
| R5 | unbekannter Modus | gültig | nicht relevant | Simulation wird abgelehnt. |
| R6 | gültiger Modus | unbekanntes Szenario | nicht relevant | Simulation wird abgelehnt. |
| R7 | leerer Modus | gültig | nicht relevant | Simulation wird abgelehnt. |
| R8 | gültiger Modus | leeres Szenario | nicht relevant | Simulation wird abgelehnt. |

Aus jeder Regel wird mindestens ein Testfall abgeleitet.

---

## 7. Testfallformat

| Feld | Bedeutung |
|---|---|
| Testfall-ID | Eindeutige Kennung des Testfalls |
| Requirement-ID | Geprüfte Requirements |
| Testobjekt | Zu testende Klasse oder Komponente |
| Testphase | Modultest, Integrationstest oder manuelle Validierung |
| Testtechnik | Black-Box, White-Box, Entscheidungstabelle oder Schnittstellentest |
| Vorbedingung | Zustand vor Testausführung |
| Eingabe | Eingabedaten des Testfalls |
| Testschritte | Durchzuführende Schritte |
| Erwartetes Ergebnis | Erwartete Reaktion der Software |
| Priorität | P1, P2 oder P3 |

---

## 8. Testfälle Iteration 1

### TST-I1-001 – Leistungsmodi werden bereitgestellt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-001 |
| Testobjekt | `PerformanceModeManager` |
| Testphase | Modultest |
| Testtechnik | Black-Box |
| Vorbedingung | `PerformanceModeManager` ist instanziiert. |
| Eingabe | keine |
| Testschritte | `listModes()` aufrufen. |
| Erwartetes Ergebnis | Die Liste enthält `ENERGY_SAVE`, `BALANCED` und `PERFORMANCE`. |
| Priorität | P1 |

### TST-I1-002 – Ausgewählter fester Leistungsmodus wird übernommen

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-002 |
| Testobjekt | `PerformanceModeManager` |
| Testphase | Modultest |
| Testtechnik | Black-Box |
| Vorbedingung | `PerformanceModeManager` ist instanziiert. |
| Eingabe | `SimulationInput("BALANCED", "OFFICE")` |
| Testschritte | `getMode(inputData)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist ein `PerformanceMode` mit `key = BALANCED`, `name = Ausgeglichen`, `maxClockGhz = 3.2`. |
| Priorität | P1 |

### TST-I1-003 – Benutzerdefinierter Leistungsmodus mit gültigen Werten wird angenommen

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-003 |
| Testobjekt | `PerformanceModeManager` |
| Testphase | Modultest |
| Testtechnik | Entscheidungstabellen-Test |
| Vorbedingung | `PerformanceModeManager` ist instanziiert. |
| Eingabe | `SimulationInput("CUSTOM", "OFFICE", 3.5, 45.0)` |
| Testschritte | `getMode(inputData)` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist ein benutzerdefinierter `PerformanceMode` mit `maxClockGhz = 3.5` und `powerLimitWatt = 45.0`. |
| Priorität | P1 |

### TST-I1-004 – Benutzerdefinierter Leistungsmodus mit zu hohem Taktwert wird abgelehnt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-004 |
| Testobjekt | `PerformanceModeManager` |
| Testphase | Modultest |
| Testtechnik | Entscheidungstabellen-Test / White-Box |
| Vorbedingung | `PerformanceModeManager` ist instanziiert. |
| Eingabe | `SimulationInput("CUSTOM", "OFFICE", 4.5, 45.0)` |
| Testschritte | `getMode(inputData)` aufrufen. |
| Erwartetes Ergebnis | `IllegalArgumentException` wird ausgelöst. |
| Priorität | P1 |

### TST-I1-005 – Benutzerdefinierter Leistungsmodus mit zu niedrigem Power-Limit wird abgelehnt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-004 |
| Testobjekt | `PerformanceModeManager` |
| Testphase | Modultest |
| Testtechnik | Entscheidungstabellen-Test / White-Box |
| Vorbedingung | `PerformanceModeManager` ist instanziiert. |
| Eingabe | `SimulationInput("CUSTOM", "OFFICE", 3.5, 7.0)` |
| Testschritte | `getMode(inputData)` aufrufen. |
| Erwartetes Ergebnis | `IllegalArgumentException` wird ausgelöst. |
| Priorität | P1 |

### TST-I1-006 – Lastszenarien werden bereitgestellt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-005 |
| Testobjekt | `LoadScenarioManager` |
| Testphase | Modultest |
| Testtechnik | Black-Box |
| Vorbedingung | `LoadScenarioManager` ist instanziiert. |
| Eingabe | keine |
| Testschritte | `listScenarios()` aufrufen. |
| Erwartetes Ergebnis | Die Liste enthält `IDLE`, `OFFICE`, `MULTIMEDIA`, `GAMING` und `STRESS_TEST`. |
| Priorität | P1 |

### TST-I1-007 – CPU-Auslastung wird dem Lastszenario zugeordnet

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-006 |
| Testobjekt | `LoadScenarioManager`, `LoadScenario` |
| Testphase | Modultest |
| Testtechnik | Black-Box |
| Vorbedingung | `LoadScenarioManager` ist instanziiert. |
| Eingabe | `OFFICE` |
| Testschritte | `getScenario("OFFICE")` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist ein `LoadScenario` mit `cpuLoadPercent = 35.0`. |
| Priorität | P1 |

### TST-I1-008 – Ausgewähltes Lastszenario wird übernommen

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-007 |
| Testobjekt | `LoadScenarioManager` |
| Testphase | Modultest |
| Testtechnik | Black-Box |
| Vorbedingung | `LoadScenarioManager` ist instanziiert. |
| Eingabe | `GAMING` |
| Testschritte | `getScenario("GAMING")` aufrufen. |
| Erwartetes Ergebnis | Rückgabe ist ein `LoadScenario` mit `key = GAMING`, `name = Gaming`, `cpuLoadPercent = 85.0`. |
| Priorität | P1 |

### TST-I1-009 – CPU-Auslastung wird im Ergebnis ausgegeben

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-008 |
| Testobjekt | `SimulationResult` |
| Testphase | Integrationstest |
| Testtechnik | Black-Box |
| Vorbedingung | Vollständiger Simulationslauf wurde ausgeführt. |
| Eingabe | Modus `BALANCED`, Szenario `OFFICE` |
| Testschritte | `runSimulation(inputData)` ausführen und `toDisplayText()` aufrufen. |
| Erwartetes Ergebnis | Ausgabetext enthält `CPU-Auslastung: 35 %`. |
| Priorität | P1 |

### TST-I1-010 – Ungedrosselte CPU-Taktfrequenz wird bestimmt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-009 |
| Testobjekt | `ClockCalculator` |
| Testphase | Modultest |
| Testtechnik | Black-Box |
| Vorbedingung | `ClockCalculator`, `PerformanceMode` und `LoadScenario` sind vorhanden. |
| Eingabe | Modus `BALANCED`, Szenario `OFFICE` |
| Testschritte | `calculateClock(mode, scenario)` aufrufen. |
| Erwartetes Ergebnis | Rückgabewert ist `2.0`. |
| Priorität | P1 |

### TST-I1-011 – CPU-Taktfrequenz wird auf Minimalwert begrenzt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-010 |
| Testobjekt | `ClockCalculator` |
| Testphase | Modultest |
| Testtechnik | White-Box / Grenzwerttest |
| Vorbedingung | `ClockCalculator` ist instanziiert. |
| Eingabe | `clampClock(0.8)` |
| Testschritte | `clampClock(0.8)` aufrufen. |
| Erwartetes Ergebnis | Rückgabewert ist `1.2`. |
| Priorität | P1 |

### TST-I1-012 – CPU-Taktfrequenz wird auf Maximalwert begrenzt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-010 |
| Testobjekt | `ClockCalculator` |
| Testphase | Modultest |
| Testtechnik | White-Box / Grenzwerttest |
| Vorbedingung | `ClockCalculator` ist instanziiert. |
| Eingabe | `clampClock(5.0)` |
| Testschritte | `clampClock(5.0)` aufrufen. |
| Erwartetes Ergebnis | Rückgabewert ist `4.2`. |
| Priorität | P1 |

### TST-I1-013 – CPU-Taktfrequenz wird mit Einheit GHz ausgegeben

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-011 |
| Testobjekt | `SimulationResult` |
| Testphase | Integrationstest |
| Testtechnik | Black-Box |
| Vorbedingung | Vollständiger Simulationslauf wurde ausgeführt. |
| Eingabe | Modus `BALANCED`, Szenario `OFFICE` |
| Testschritte | `toDisplayText()` aufrufen. |
| Erwartetes Ergebnis | Ausgabetext enthält `Ungedrosselte CPU-Taktfrequenz:` und `GHz`. |
| Priorität | P1 |

### TST-I1-014 – CPU-Taktfrequenz wird bei geänderter Eingabe neu bestimmt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-012 |
| Testobjekt | `PerformanceController`, `ClockCalculator` |
| Testphase | Integrationstest |
| Testtechnik | Black-Box |
| Vorbedingung | Controller und Services sind instanziiert. |
| Eingabe | Lauf 1: `BALANCED` + `OFFICE`; Lauf 2: `BALANCED` + `GAMING` |
| Testschritte | Zwei Simulationen mit unterschiedlichen Szenarien ausführen. |
| Erwartetes Ergebnis | Lauf 1 ergibt `2.0 GHz`, Lauf 2 ergibt `3.0 GHz`. |
| Priorität | P1 |

### TST-I1-015 – Gleiche Eingaben erzeugen gleiche Ausgaben

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-NF-001 |
| Testobjekt | `PerformanceController`, `ClockCalculator` |
| Testphase | Integrationstest |
| Testtechnik | Black-Box |
| Vorbedingung | Controller und Services sind instanziiert. |
| Eingabe | Zweimal `SimulationInput("PERFORMANCE", "STRESS_TEST")` |
| Testschritte | Simulation zweimal mit identischen Eingaben ausführen. |
| Erwartetes Ergebnis | Beide Läufe liefern `4.2 GHz` und `100 % CPU-Auslastung`. |
| Priorität | P1 |

### TST-I1-016 – Serviceklassen sind getrennt testbar

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-NF-002 |
| Testobjekt | `PerformanceModeManager`, `LoadScenarioManager`, `ClockCalculator` |
| Testphase | Modultest |
| Testtechnik | Statischer Test / Review |
| Vorbedingung | Quellcode liegt vor. |
| Eingabe | keine |
| Testschritte | Prüfen, ob die Klassen ohne `UserInterface` und ohne `Main` instanziiert und getestet werden können. |
| Erwartetes Ergebnis | Alle Serviceklassen sind unabhängig erzeugbar und besitzen klar testbare öffentliche Methoden. |
| Priorität | P2 |

### TST-I1-017 – Ungültiger Leistungsmodus führt nicht zu unkontrolliertem Programmabbruch

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-NF-003 |
| Testobjekt | `PerformanceController`, `PerformanceModeManager`, `UserInterface` |
| Testphase | Integrationstest |
| Testtechnik | Entscheidungstabellen-Test / Robustheitstest |
| Vorbedingung | Anwendung ist gestartet. |
| Eingabe | Modus `UNKNOWN`, Szenario `OFFICE` |
| Testschritte | Simulation mit unbekanntem Modus ausführen. |
| Erwartetes Ergebnis | Eine kontrollierte Fehlermeldung wird ausgegeben; die Anwendung stürzt nicht unkontrolliert ab. |
| Priorität | P1 |

### TST-I1-018 – Ungültiges Lastszenario führt nicht zu unkontrolliertem Programmabbruch

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-NF-003 |
| Testobjekt | `PerformanceController`, `LoadScenarioManager`, `UserInterface` |
| Testphase | Integrationstest |
| Testtechnik | Entscheidungstabellen-Test / Robustheitstest |
| Vorbedingung | Anwendung ist gestartet. |
| Eingabe | Modus `BALANCED`, Szenario `UNKNOWN` |
| Testschritte | Simulation mit unbekanntem Szenario ausführen. |
| Erwartetes Ergebnis | Eine kontrollierte Fehlermeldung wird ausgegeben; die Anwendung stürzt nicht unkontrolliert ab. |
| Priorität | P1 |

### TST-I1-019 – Leerer Leistungsmodus wird abgelehnt

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-NF-003 |
| Testobjekt | `SimulationInput`, `PerformanceController` |
| Testphase | Integrationstest |
| Testtechnik | White-Box / Entscheidungstabellen-Test |
| Vorbedingung | Controller und Services sind instanziiert. |
| Eingabe | `SimulationInput("", "OFFICE")` |
| Testschritte | `runSimulation(inputData)` aufrufen. |
| Erwartetes Ergebnis | `IllegalArgumentException` wird ausgelöst. |
| Priorität | P2 |

### TST-I1-020 – Vollständiger Konsolenlauf aus Benutzersicht

| Feld | Inhalt |
|---|---|
| Requirement-ID | REQ-F-001 bis REQ-F-012, REQ-NF-003 |
| Testobjekt | Gesamte Anwendung |
| Testphase | Manuelle Validierung |
| Testtechnik | Black-Box / Schnittstellentest |
| Vorbedingung | Anwendung wird über `Main.java` gestartet. |
| Eingabe | Modus `BALANCED`, Szenario `OFFICE`, danach `n` |
| Testschritte | Anwendung starten, Eingaben vornehmen, Ergebnis prüfen, Anwendung beenden. |
| Erwartetes Ergebnis | Die Anwendung zeigt Leistungsmodus, Lastszenario, CPU-Auslastung, CPU-Taktfrequenz mit Einheit und Zusammenfassung an. Danach wird die Anwendung kontrolliert beendet. |
| Priorität | P1 |

---

## 9. Testabdeckung gegen Requirements

| Requirement-ID | Zugeordnete Testfälle |
|---|---|
| REQ-F-001 | TST-I1-001, TST-I1-020 |
| REQ-F-002 | TST-I1-002, TST-I1-020 |
| REQ-F-003 | TST-I1-003 |
| REQ-F-004 | TST-I1-004, TST-I1-005 |
| REQ-F-005 | TST-I1-006, TST-I1-020 |
| REQ-F-006 | TST-I1-007 |
| REQ-F-007 | TST-I1-008, TST-I1-020 |
| REQ-F-008 | TST-I1-009, TST-I1-020 |
| REQ-F-009 | TST-I1-010, TST-I1-014 |
| REQ-F-010 | TST-I1-011, TST-I1-012 |
| REQ-F-011 | TST-I1-013, TST-I1-020 |
| REQ-F-012 | TST-I1-014 |
| REQ-NF-001 | TST-I1-015 |
| REQ-NF-002 | TST-I1-016 |
| REQ-NF-003 | TST-I1-017, TST-I1-018, TST-I1-019, TST-I1-020 |
| REQ-NF-004 | wird im Test Reporting und in der Baseline dokumentiert |

---

## 10. Abgrenzung

Nicht Bestandteil dieser Testdokumentation sind Tests für:

- Temperaturberechnung,
- Thermal Throttling,
- Stromverbrauchsberechnung,
- Ereignisprotokollierung,
- Langzeittest,
- Performancetest,
- Systemtest mit echter Hardware.

Diese Funktionen gehören nicht zum Umfang von Iteration 1 und werden in späteren Iterationen ergänzt.

