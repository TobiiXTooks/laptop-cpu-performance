# Iteration 2 – Feindesign

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt das Feindesign für Iteration 2 des Laptop-Prozessorleistungsmoduls.

Das Feindesign konkretisiert die Software-Architektur der Iteration 2 auf Klassen-, Methoden- und Datenmodellebene. Es legt fest:

* welche bestehenden Klassen aus Iteration 1 erweitert werden,
* welche neuen Klassen in Iteration 2 benötigt werden,
* welche Attribute die Datenklassen besitzen,
* welche Methoden die Service- und Steuerklassen bereitstellen,
* welche Berechnungsregeln für Temperatur und Stromverbrauch verwendet werden,
* welche Ereignisse protokolliert werden,
* welche Requirements durch welche Klassen umgesetzt werden,
* welche Java-Dateien aus dem Design abgeleitet werden.

Iteration 2 erweitert die bestehende Grundsimulation um Kühlungsstufe, CPU-Temperatur, Stromverbrauch und eine erste Ereignisprotokollierung.

Nicht Bestandteil dieses Feindesigns ist die vollständige Umsetzung von Thermal Throttling. Die effektive CPU-Taktfrequenz entspricht in Iteration 2 weiterhin der ungedrosselten CPU-Taktfrequenz.

---

## 2. Grundlage aus der Iteration-2-Architektur

Die Iteration-2-Architektur enthält folgende aktive Komponenten:

| Komponenten-ID | Komponente                   | Aufgabe                                             |
| -------------- | ---------------------------- | --------------------------------------------------- |
| CMP-009        | `UserInterface`              | Eingaben erfassen und erweitertes Ergebnis anzeigen |
| CMP-008        | `PerformanceController`      | erweiterten Simulationsablauf koordinieren          |
| CMP-001        | `PerformanceModeManager`     | Leistungsmodi bereitstellen und validieren          |
| CMP-002        | `LoadScenarioManager`        | Lastszenarien bereitstellen und validieren          |
| CMP-003        | `ClockCalculator`            | ungedrosselte CPU-Taktfrequenz berechnen            |
| CMP-004        | `TemperatureSimulator`       | CPU-Temperatur berechnen                            |
| CMP-006        | `PowerConsumptionCalculator` | Stromverbrauch berechnen                            |
| CMP-007        | `EventLogger`                | einfache Ereignisse protokollieren                  |

Die Architektur wird als modulare Schichtenarchitektur beibehalten:

```text
Benutzerschnittstelle
        ↓
Anwendungssteuerung
        ↓
Fachlogik / Services
        ↓
Modelle und Datenobjekte
```

---

## 3. Designziele für Iteration 2

| Designziel-ID | Designziel          | Beschreibung                                                                |
| ------------- | ------------------- | --------------------------------------------------------------------------- |
| I2-DZ-001     | Bestandsschutz      | Funktionen aus Iteration 1 bleiben lauffähig.                               |
| I2-DZ-002     | Erweiterbarkeit     | Thermal Throttling kann in Iteration 3 ergänzt werden.                      |
| I2-DZ-003     | Kapselung           | Temperatur, Stromverbrauch und Logging werden in eigenen Klassen umgesetzt. |
| I2-DZ-004     | Testbarkeit         | Neue Berechnungen sind isoliert durch Unit-Tests prüfbar.                   |
| I2-DZ-005     | Determinismus       | Gleiche Eingaben erzeugen gleiche Temperatur- und Verbrauchswerte.          |
| I2-DZ-006     | Nachvollziehbarkeit | Ergebniswerte und Ereignisse werden strukturiert gespeichert.               |
| I2-DZ-007     | Traceability        | Jede neue Klasse ist Requirements und Komponenten zuordenbar.               |

---

## 4. Klassenübersicht

| Klasse                       | Typ               | Komponente                         | Zweck                                                                  |
| ---------------------------- | ----------------- | ---------------------------------- | ---------------------------------------------------------------------- |
| `SimulationInput`            | Datenklasse       | CMP-009, CMP-008                   | Bündelt Eingaben für einen Simulationslauf.                            |
| `PerformanceMode`            | Datenklasse       | CMP-001                            | Beschreibt einen Leistungsmodus.                                       |
| `LoadScenario`               | Datenklasse       | CMP-002                            | Beschreibt ein Lastszenario mit CPU-Auslastung.                        |
| `CoolingLevel`               | Datenklasse       | CMP-004                            | Beschreibt eine Kühlungsstufe mit Kühlungsfaktor.                      |
| `ProcessorState`             | Datenklasse       | CMP-008, CMP-003, CMP-004, CMP-006 | Speichert CPU-Auslastung, Taktfrequenz, Temperatur und Stromverbrauch. |
| `SimulationResult`           | Datenklasse       | CMP-008, CMP-009                   | Bündelt die finalen Ergebniswerte und Ereignisse.                      |
| `LogEntry`                   | Datenklasse       | CMP-007                            | Beschreibt einen Ereignisprotokolleintrag.                             |
| `UserInterface`              | Steuer-/UI-Klasse | CMP-009                            | Nimmt Eingaben entgegen und zeigt Ergebnisse an.                       |
| `PerformanceController`      | Steuerklasse      | CMP-008                            | Koordiniert den erweiterten Simulationsablauf.                         |
| `PerformanceModeManager`     | Serviceklasse     | CMP-001                            | Verwaltet und validiert Leistungsmodi.                                 |
| `LoadScenarioManager`        | Serviceklasse     | CMP-002                            | Verwaltet und validiert Lastszenarien.                                 |
| `ClockCalculator`            | Serviceklasse     | CMP-003                            | Berechnet die ungedrosselte CPU-Taktfrequenz.                          |
| `TemperatureSimulator`       | Serviceklasse     | CMP-004                            | Berechnet die simulierte CPU-Temperatur.                               |
| `PowerConsumptionCalculator` | Serviceklasse     | CMP-006                            | Berechnet den simulierten Stromverbrauch.                              |
| `EventLogger`                | Serviceklasse     | CMP-007                            | Erfasst und verwaltet Ereignisse.                                      |

---

## 5. Datenklassen im Detail

### 5.1 `SimulationInput`

**Zweck:**
`SimulationInput` enthält die vom Nutzer gewählten Eingabedaten für einen Simulationslauf.

**Änderung gegenüber Iteration 1:**
Die Klasse wird um die Kühlungsstufe erweitert.

| Attribut               | Typ      | Pflicht                           | Beschreibung                                             |
| ---------------------- | -------- | --------------------------------- | -------------------------------------------------------- |
| `modeKey`              | `String` | ja                                | Kennung des ausgewählten Leistungsmodus.                 |
| `scenarioKey`          | `String` | ja                                | Kennung des ausgewählten Lastszenarios.                  |
| `coolingLevelKey`      | `String` | ja                                | Kennung der ausgewählten Kühlungsstufe.                  |
| `customMaxClockGhz`    | `Double` | nur bei benutzerdefiniertem Modus | Maximale CPU-Taktfrequenz des benutzerdefinierten Modus. |
| `customPowerLimitWatt` | `Double` | nur bei benutzerdefiniertem Modus | Stromverbrauchsgrenzwert des benutzerdefinierten Modus.  |

**Zulässige Kühlungsstufen:**

| Key       | Anzeigename | Bedeutung                       |
| --------- | ----------- | ------------------------------- |
| `NIEDRIG` | Niedrig     | geringe simulierte Kühlwirkung  |
| `MITTEL`  | Mittel      | mittlere simulierte Kühlwirkung |
| `HOCH`    | Hoch        | hohe simulierte Kühlwirkung     |

**Methoden:**

| Methode                     | Rückgabe  | Beschreibung                                                |
| --------------------------- | --------- | ----------------------------------------------------------- |
| `getModeKey()`              | `String`  | Gibt die Moduskennung zurück.                               |
| `getScenarioKey()`          | `String`  | Gibt die Szenariokennung zurück.                            |
| `getCoolingLevelKey()`      | `String`  | Gibt die Kühlungsstufenkennung zurück.                      |
| `getCustomMaxClockGhz()`    | `Double`  | Gibt den benutzerdefinierten Maximal-Takt zurück.           |
| `getCustomPowerLimitWatt()` | `Double`  | Gibt das benutzerdefinierte Power-Limit zurück.             |
| `isCustomMode()`            | `boolean` | Prüft, ob der benutzerdefinierte Modus gewählt wurde.       |
| `hasRequiredValues()`       | `boolean` | Prüft, ob Modus, Szenario und Kühlungsstufe vorhanden sind. |

**Hinweis zur Rückwärtskompatibilität:**
Der bisherige Konstruktor aus Iteration 1 bleibt erhalten. Wenn keine Kühlungsstufe übergeben wird, wird `MITTEL` als Standardwert verwendet. Dadurch können bestehende Iteration-1-Tests weiterhin ausgeführt werden.

---

### 5.2 `CoolingLevel`

**Zweck:**
`CoolingLevel` beschreibt eine auswählbare Kühlungsstufe.

| Attribut        | Typ      | Pflicht | Beschreibung                          |
| --------------- | -------- | ------- | ------------------------------------- |
| `key`           | `String` | ja      | Eindeutige Kennung der Kühlungsstufe. |
| `name`          | `String` | ja      | Anzeigename der Kühlungsstufe.        |
| `coolingFactor` | `double` | ja      | Faktor für die Temperaturberechnung.  |

**Zulässige Werte:**

| key       | name    | coolingFactor |
| --------- | ------- | ------------: |
| `NIEDRIG` | Niedrig |          0.85 |
| `MITTEL`  | Mittel  |          1.00 |
| `HOCH`    | Hoch    |          1.15 |

**Methoden:**

| Methode              | Rückgabe | Beschreibung                    |
| -------------------- | -------- | ------------------------------- |
| `getKey()`           | `String` | Gibt die Kennung zurück.        |
| `getName()`          | `String` | Gibt den Namen zurück.          |
| `getCoolingFactor()` | `double` | Gibt den Kühlungsfaktor zurück. |
| `getDisplayName()`   | `String` | Gibt den Anzeigenamen zurück.   |

---

### 5.3 `ProcessorState`

**Zweck:**
`ProcessorState` speichert den aktuellen Prozessorzustand innerhalb eines Simulationslaufs.

**Änderung gegenüber Iteration 1:**
Die Klasse wird um Temperatur und Stromverbrauch erweitert.

| Attribut               | Typ      | Pflicht                       | Beschreibung                           |
| ---------------------- | -------- | ----------------------------- | -------------------------------------- |
| `cpuLoadPercent`       | `double` | ja                            | CPU-Auslastung in Prozent.             |
| `clockGhz`             | `Double` | ja, nach Taktberechnung       | Ungedrosselte CPU-Taktfrequenz in GHz. |
| `temperatureCelsius`   | `Double` | ja, nach Temperaturberechnung | Simulierte CPU-Temperatur in °C.       |
| `powerConsumptionWatt` | `Double` | ja, nach Verbrauchsberechnung | Simulierter Stromverbrauch in W.       |

**Methoden:**

| Methode                                               | Rückgabe  | Beschreibung                                |
| ----------------------------------------------------- | --------- | ------------------------------------------- |
| `getCpuLoadPercent()`                                 | `double`  | Gibt die CPU-Auslastung zurück.             |
| `getClockGhz()`                                       | `Double`  | Gibt die CPU-Taktfrequenz zurück.           |
| `getTemperatureCelsius()`                             | `Double`  | Gibt die CPU-Temperatur zurück.             |
| `getPowerConsumptionWatt()`                           | `Double`  | Gibt den Stromverbrauch zurück.             |
| `updateClock(double clockGhz)`                        | `void`    | Aktualisiert die CPU-Taktfrequenz.          |
| `updateTemperature(double temperatureCelsius)`        | `void`    | Aktualisiert die CPU-Temperatur.            |
| `updatePowerConsumption(double powerConsumptionWatt)` | `void`    | Aktualisiert den Stromverbrauch.            |
| `hasClock()`                                          | `boolean` | Prüft, ob eine Taktfrequenz vorhanden ist.  |
| `hasTemperature()`                                    | `boolean` | Prüft, ob eine Temperatur vorhanden ist.    |
| `hasPowerConsumption()`                               | `boolean` | Prüft, ob ein Stromverbrauch vorhanden ist. |

---

### 5.4 `LogEntry`

**Zweck:**
`LogEntry` beschreibt einen Ereignisprotokolleintrag.

| Attribut              | Typ             | Pflicht | Beschreibung                                              |
| --------------------- | --------------- | ------- | --------------------------------------------------------- |
| `timestamp`           | `LocalDateTime` | ja      | Zeitpunkt der Ereigniserfassung.                          |
| `eventType`           | `String`        | ja      | Definierter Ereignistyp.                                  |
| `description`         | `String`        | ja      | Textuelle Beschreibung des Ereignisses.                   |
| `performanceModeName` | `String`        | ja      | Aktueller Leistungsmodus.                                 |
| `loadScenarioName`    | `String`        | ja      | Aktuelles Lastszenario.                                   |
| `cpuLoadPercent`      | `double`        | ja      | Aktuelle CPU-Auslastung.                                  |
| `clockGhz`            | `double`        | ja      | Aktuelle CPU-Taktfrequenz.                                |
| `temperatureCelsius`  | `double`        | ja      | Aktuelle CPU-Temperatur.                                  |
| `throttlingState`     | `String`        | ja      | Aktuelle Throttling-Stufe. In Iteration 2 immer `Normal`. |

**Zulässige Ereignistypen in Iteration 2:**

| Ereignistyp           | Bedeutung                                                                               |
| --------------------- | --------------------------------------------------------------------------------------- |
| `MODUSWECHSEL`        | Leistungsmodus wurde ausgewählt oder gegenüber dem vorherigen Simulationslauf geändert. |
| `LASTSZENARIOWECHSEL` | Lastszenario wurde ausgewählt oder gegenüber dem vorherigen Simulationslauf geändert.   |
| `TEMPERATURWARNUNG`   | CPU-Temperatur beträgt mindestens 70 °C.                                                |

**Methoden:**

| Methode            | Rückgabe        | Beschreibung                                           |
| ------------------ | --------------- | ------------------------------------------------------ |
| `getTimestamp()`   | `LocalDateTime` | Gibt den Ereigniszeitpunkt zurück.                     |
| `getEventType()`   | `String`        | Gibt den Ereignistyp zurück.                           |
| `getDescription()` | `String`        | Gibt die Ereignisbeschreibung zurück.                  |
| `toDisplayText()`  | `String`        | Gibt den Ereigniseintrag als formatierten Text zurück. |

---

### 5.5 `SimulationResult`

**Zweck:**
`SimulationResult` bündelt die finalen Ergebniswerte eines Simulationslaufs.

**Änderung gegenüber Iteration 1:**
Die Klasse wird um Kühlungsstufe, Temperatur, Stromverbrauch und Ereignisliste erweitert.

| Attribut          | Typ               | Pflicht | Beschreibung                                         |
| ----------------- | ----------------- | ------- | ---------------------------------------------------- |
| `performanceMode` | `PerformanceMode` | ja      | Aktueller Leistungsmodus.                            |
| `loadScenario`    | `LoadScenario`    | ja      | Aktuelles Lastszenario.                              |
| `coolingLevel`    | `CoolingLevel`    | ja      | Aktuelle Kühlungsstufe.                              |
| `processorState`  | `ProcessorState`  | ja      | Prozessorzustand mit Takt, Temperatur und Verbrauch. |
| `logEntries`      | `List<LogEntry>`  | ja      | Ereignisse des Simulationslaufs.                     |
| `summary`         | `String`          | ja      | Zusammenfassung des Simulationsergebnisses.          |

**Methoden:**

| Methode                | Rückgabe          | Beschreibung                                           |
| ---------------------- | ----------------- | ------------------------------------------------------ |
| `getPerformanceMode()` | `PerformanceMode` | Gibt den Leistungsmodus zurück.                        |
| `getLoadScenario()`    | `LoadScenario`    | Gibt das Lastszenario zurück.                          |
| `getCoolingLevel()`    | `CoolingLevel`    | Gibt die Kühlungsstufe zurück.                         |
| `getProcessorState()`  | `ProcessorState`  | Gibt den Prozessorzustand zurück.                      |
| `getLogEntries()`      | `List<LogEntry>`  | Gibt die Ereignisliste zurück.                         |
| `getSummary()`         | `String`          | Gibt die Zusammenfassung zurück.                       |
| `toDisplayText()`      | `String`          | Erstellt den formatierten Ausgabetext für die Konsole. |

---

## 6. Service- und Steuerklassen im Detail

### 6.1 `TemperatureSimulator`

**Komponente:** CMP-004
**Zugeordnete Requirements:** REQ-F-013, REQ-F-014, REQ-F-015, REQ-F-016

**Zweck:**
`TemperatureSimulator` verwaltet die Kühlungsstufen und berechnet die simulierte CPU-Temperatur.

| Attribut                  | Typ                         | Beschreibung                            |
| ------------------------- | --------------------------- | --------------------------------------- |
| `MIN_TEMPERATURE_CELSIUS` | `static final double`       | Minimal zulässige Temperatur, 35 °C.    |
| `MAX_TEMPERATURE_CELSIUS` | `static final double`       | Maximal zulässige Temperatur, 100 °C.   |
| `coolingLevels`           | `Map<String, CoolingLevel>` | Enthält die verfügbaren Kühlungsstufen. |

**Methoden:**

| Methode                                                                 | Rückgabe             | Beschreibung                                  |
| ----------------------------------------------------------------------- | -------------------- | --------------------------------------------- |
| `listCoolingLevels()`                                                   | `List<CoolingLevel>` | Gibt alle verfügbaren Kühlungsstufen zurück.  |
| `isValidCoolingLevel(String coolingLevelKey)`                           | `boolean`            | Prüft, ob eine Kühlungsstufe gültig ist.      |
| `getCoolingLevel(String coolingLevelKey)`                               | `CoolingLevel`       | Gibt die ausgewählte Kühlungsstufe zurück.    |
| `calculateTemperature(ProcessorState processorState, CoolingLevel coolingLevel)` | `double`             | Berechnet die CPU-Temperatur.                 |
| `clampTemperature(double temperatureCelsius)`                           | `double`             | Begrenzt die Temperatur auf 35 °C bis 100 °C. |

**Berechnungsregel:**

```text
Rohwert = 35 + ((CPU-Auslastung * 0,35) + (CPU-Taktfrequenz * 8,0)) / Kühlungsfaktor
CPU-Temperatur = Rohwert begrenzt auf 35 °C bis 100 °C
```

**Beispielhafte Wirkung der Kühlung:**

| Kühlungsstufe | Wirkung                            |
| ------------- | ---------------------------------- |
| Niedrig       | Temperatur steigt stärker.         |
| Mittel        | Temperatur wird neutral berechnet. |
| Hoch          | Temperatur wird reduziert.         |

---

### 6.2 `PowerConsumptionCalculator`

**Komponente:** CMP-006
**Zugeordnete Requirements:** REQ-F-017, REQ-F-018

**Zweck:**
`PowerConsumptionCalculator` berechnet den simulierten Stromverbrauch in Watt.

| Attribut         | Typ                   | Beschreibung                             |
| ---------------- | --------------------- | ---------------------------------------- |
| `MIN_POWER_WATT` | `static final double` | Minimal zulässiger Stromverbrauch, 8 W.  |
| `MAX_POWER_WATT` | `static final double` | Maximal zulässiger Stromverbrauch, 65 W. |

**Methoden:**

| Methode                                                                 | Rückgabe | Beschreibung                                  |
| ----------------------------------------------------------------------- | -------- | --------------------------------------------- |
| `calculatePowerConsumption(PerformanceMode performanceMode, ProcessorState processorState)` | `double` | Berechnet den Stromverbrauch.                 |
| `clampPowerConsumption(double powerWatt)`                               | `double` | Begrenzt den Stromverbrauch auf 8 W bis 65 W. |

**Berechnungsregel:**

```text
Lastfaktor = 0,30 + 0,70 * (CPU-Auslastung / 100)
Taktfaktor = CPU-Taktfrequenz / maximale CPU-Taktfrequenz des Leistungsmodus
Rohwert = Power-Limit des Leistungsmodus * Lastfaktor * Taktfaktor
Stromverbrauch = Rohwert begrenzt auf 8 W bis 65 W
```

**Hinweis zu Iteration 2:**
Da Thermal Throttling noch nicht aktiv ist, wird die ungedrosselte CPU-Taktfrequenz auch für die Stromverbrauchsberechnung verwendet.

---

### 6.3 `EventLogger`

**Komponente:** CMP-007
**Zugeordnete Requirements:** REQ-F-020, REQ-F-021, REQ-F-022, REQ-F-023

**Zweck:**
`EventLogger` erfasst einfache Ereignisse eines Simulationslaufs.

| Attribut          | Typ              | Beschreibung                            |
| ----------------- | ---------------- | --------------------------------------- |
| `entries`         | `List<LogEntry>` | Liste der erfassten Ereignisse.         |
| `lastModeKey`     | `String`         | Zuletzt protokollierter Leistungsmodus. |
| `lastScenarioKey` | `String`         | Zuletzt protokolliertes Lastszenario.   |

**Methoden:**

| Methode                                                                                                                | Rückgabe         | Beschreibung                                                               |
| ---------------------------------------------------------------------------------------------------------------------- | ---------------- | -------------------------------------------------------------------------- |
| `logModeChangeIfNeeded(PerformanceMode performanceMode, LoadScenario LoadScenario, ProcessorState processorState)`                             | `void`           | Erfasst `MODUSWECHSEL`, wenn der Modus neu oder geändert ist.              |
| `logScenarioChangeIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                         | `void`           | Erfasst `LASTSZENARIOWECHSEL`, wenn das Szenario neu oder geändert ist.    |
| `logTemperatureWarningIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                     | `void`           | Erfasst `TEMPERATURWARNUNG`, wenn die Temperatur mindestens 70 °C beträgt. |
| `createEntry(String eventType, String description, PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)` | `LogEntry`       | Erstellt einen Ereignisprotokolleintrag.                                   |
| `getEntries()`                                                                                                         | `List<LogEntry>` | Gibt eine Kopie der Ereignisliste zurück.                                  |
| `clear()`                                                                                                              | `void`           | Leert das Ereignisprotokoll. Vorbereitung für Iteration 3.                 |

**Hinweis zu Iteration 2:**
Die Methode `clear()` wird bereits vorgesehen, aber die vollständige Reset-Logik wird erst in Iteration 3 umgesetzt.

---

### 6.4 `PerformanceController`

**Komponente:** CMP-008
**Zugeordnete Requirements:** REQ-F-014, REQ-F-015, REQ-F-017, REQ-F-020 bis REQ-F-023

**Zweck:**
`PerformanceController` koordiniert den erweiterten Simulationsablauf.

**Neue Abhängigkeiten gegenüber Iteration 1:**

| Attribut                     | Typ                          | Beschreibung                           |
| ---------------------------- | ---------------------------- | -------------------------------------- |
| `temperatureSimulator`       | `TemperatureSimulator`       | Service für Temperaturberechnung.      |
| `powerConsumptionCalculator` | `PowerConsumptionCalculator` | Service für Stromverbrauchsberechnung. |
| `eventLogger`                | `EventLogger`                | Service für Ereignisprotokollierung.   |

**Methoden:**

| Methode                                                                                                                                          | Rückgabe           | Beschreibung                                  |
| ------------------------------------------------------------------------------------------------------------------------------------------------ | ------------------ | --------------------------------------------- |
| `runSimulation(SimulationInput inputData)`                                                                                                       | `SimulationResult` | Führt den erweiterten Simulationslauf aus.    |
| `createInitialState(LoadScenario loadScenario)`                                                                                                      | `ProcessorState`   | Erstellt den Startzustand mit CPU-Auslastung. |
| `createResult(PerformanceMode performanceMode, LoadScenario loadScenario, CoolingLevel coolingLevel, ProcessorState processorState, List<LogEntry> logEntries)` | `SimulationResult` | Erstellt das erweiterte Simulationsergebnis.  |

**Ablauf in `runSimulation`:**

```text
1. Eingabe prüfen
2. Leistungsmodus bestimmen
3. Lastszenario bestimmen
4. Kühlungsstufe bestimmen
5. ProcessorState erstellen
6. CPU-Taktfrequenz berechnen
7. CPU-Temperatur berechnen
8. Stromverbrauch berechnen
9. Ereignisse protokollieren
10. SimulationResult erstellen und zurückgeben
```

---

### 6.5 `UserInterface`

**Komponente:** CMP-009
**Zugeordnete Requirements:** REQ-F-013, REQ-F-016, REQ-F-018, REQ-NF-006

**Zweck:**
`UserInterface` nimmt Nutzereingaben entgegen und zeigt das erweiterte Ergebnis an.

**Änderungen gegenüber Iteration 1:**

| Bereich          | Änderung                                                                   |
| ---------------- | -------------------------------------------------------------------------- |
| Eingabe          | Zusätzliche Abfrage der Kühlungsstufe.                                     |
| Anzeige          | Ausgabe von Kühlungsstufe, CPU-Temperatur, Stromverbrauch und Ereignissen. |
| Fehlerbehandlung | Ungültige Kühlungsstufe wird kontrolliert behandelt.                       |

**Neue oder geänderte Methoden:**

| Methode                               | Rückgabe          | Beschreibung                                 |
| ------------------------------------- | ----------------- | -------------------------------------------- |
| `readInput()`                         | `SimulationInput` | Liest Modus, Szenario und Kühlungsstufe ein. |
| `showAvailableCoolingLevels()`        | `void`            | Zeigt verfügbare Kühlungsstufen an.          |
| `showResult(SimulationResult result)` | `void`            | Gibt das erweiterte Simulationsergebnis aus. |

---

## 7. Erweiterter Ablauf der Ergebnisbildung

Der vollständige Ablauf der Ergebnisbildung in Iteration 2 lautet:

```text
SimulationInput
        ↓
PerformanceController
        ↓
PerformanceModeManager → PerformanceMode
        ↓
LoadScenarioManager → LoadScenario
        ↓
TemperatureSimulator → CoolingLevel
        ↓
ClockCalculator → CPU-Taktfrequenz
        ↓
TemperatureSimulator → CPU-Temperatur
        ↓
PowerConsumptionCalculator → Stromverbrauch
        ↓
EventLogger → LogEntry-Liste
        ↓
SimulationResult
        ↓
UserInterface
```

---

## 8. Geplante Java-Code-Dateien

### 8.1 Neue Dateien

| Datei                                         | Zweck                                                      |
| --------------------------------------------- | ---------------------------------------------------------- |
| `src/model/CoolingLevel.java`                 | Datenklasse für Kühlungsstufen.                            |
| `src/model/LogEntry.java`                     | Datenklasse für Ereignisprotokolleinträge.                 |
| `src/service/TemperatureSimulator.java`       | Serviceklasse für Kühlungsstufen und Temperaturberechnung. |
| `src/service/PowerConsumptionCalculator.java` | Serviceklasse für Stromverbrauchsberechnung.               |
| `src/service/EventLogger.java`                | Serviceklasse für Ereignisprotokollierung.                 |

### 8.2 Zu ändernde Dateien

| Datei                                       | Änderung                                                                 |
| ------------------------------------------- | ------------------------------------------------------------------------ |
| `src/model/SimulationInput.java`            | Erweiterung um `coolingLevelKey`.                                        |
| `src/model/ProcessorState.java`             | Erweiterung um Temperatur und Stromverbrauch.                            |
| `src/model/SimulationResult.java`           | Erweiterung um Kühlungsstufe, Temperatur, Stromverbrauch und Ereignisse. |
| `src/controller/PerformanceController.java` | Erweiterung des Simulationsablaufs.                                      |
| `src/ui/UserInterface.java`                 | Erweiterung der Eingabe und Ausgabe.                                     |
| `src/Main.java`                             | Instanziierung der neuen Services.                                       |

### 8.3 Unveränderte Dateien

| Datei                                     | Begründung                                                                         |
| ----------------------------------------- | ---------------------------------------------------------------------------------- |
| `src/model/PerformanceMode.java`          | Enthält bereits die benötigten Werte für Stromverbrauchsgrenze und maximalen Takt. |
| `src/model/LoadScenario.java`             | Enthält bereits die benötigte CPU-Auslastung.                                      |
| `src/service/PerformanceModeManager.java` | Leistungsmodi bleiben fachlich unverändert.                                        |
| `src/service/LoadScenarioManager.java`    | Lastszenarien bleiben fachlich unverändert.                                        |
| `src/service/ClockCalculator.java`        | Taktberechnung bleibt fachlich unverändert.                                        |

---

## 9. Requirement-Zuordnung

| Requirement-ID | Klasse / Komponente                                                | Umsetzung im Feindesign                                                    |
| -------------- | ------------------------------------------------------------------ | -------------------------------------------------------------------------- |
| REQ-F-013      | `TemperatureSimulator`, `CoolingLevel`, `UserInterface`            | Kühlungsstufen werden bereitgestellt und angezeigt.                        |
| REQ-F-014      | `SimulationInput`, `TemperatureSimulator`, `PerformanceController` | Gewählte Kühlungsstufe wird übernommen.                                    |
| REQ-F-015      | `TemperatureSimulator`, `ProcessorState`                           | CPU-Temperatur wird im Bereich 35 °C bis 100 °C berechnet und gespeichert. |
| REQ-F-016      | `SimulationResult`, `UserInterface`                                | CPU-Temperatur wird mit Einheit °C ausgegeben.                             |
| REQ-F-017      | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceMode`  | Stromverbrauch wird im Bereich 8 W bis 65 W berechnet und gespeichert.     |
| REQ-F-018      | `SimulationResult`, `UserInterface`                                | Stromverbrauch wird mit Einheit W ausgegeben.                              |
| REQ-F-019      | `PowerConsumptionCalculator`                                       | In Iteration 2 vorbereitet; vollständige Umsetzung in Iteration 3.         |
| REQ-F-020      | `EventLogger`, `LogEntry`                                          | Moduswechsel wird protokolliert.                                           |
| REQ-F-021      | `EventLogger`, `LogEntry`                                          | Lastszenario-Wechsel wird protokolliert.                                   |
| REQ-F-022      | `EventLogger`, `LogEntry`, `TemperatureSimulator`                  | Temperaturwarnung ab mindestens 70 °C wird protokolliert.                  |
| REQ-F-023      | `LogEntry`                                                         | Ereigniseintrag enthält definierte Ereignisdaten.                          |
| REQ-NF-005     | `PerformanceController`                                            | Simulationsablauf bleibt einfach und innerhalb von 5 Sekunden ausführbar.  |
| REQ-NF-006     | `SimulationResult`, `UserInterface`                                | Numerische Werte werden mit Einheit ausgegeben.                            |

---

## 10. Geplante Testbarkeit aus dem Feindesign

| Testbereich                            | Testobjekt                                 | Geplante Testfälle |
| -------------------------------------- | ------------------------------------------ | ------------------ |
| Kühlungsstufen bereitstellen           | `TemperatureSimulator`                     | TC-TMP-001         |
| Kühlungsstufe übernehmen               | `SimulationInput`, `PerformanceController` | TC-TMP-002         |
| Temperatur berechnen und begrenzen     | `TemperatureSimulator`                     | TC-TMP-003         |
| Temperatur ausgeben                    | `SimulationResult`                         | TC-TMP-004         |
| Stromverbrauch berechnen und begrenzen | `PowerConsumptionCalculator`               | TC-PWR-001         |
| Stromverbrauch ausgeben                | `SimulationResult`                         | TC-PWR-002         |
| Moduswechsel protokollieren            | `EventLogger`                              | TC-LOG-001         |
| Lastszenario-Wechsel protokollieren    | `EventLogger`                              | TC-LOG-002         |
| Temperaturwarnung protokollieren       | `EventLogger`                              | TC-LOG-003         |
| Ereignisdaten speichern                | `LogEntry`                                 | TC-LOG-004         |
| Ausgabe mit Einheiten                  | `SimulationResult`, `UserInterface`        | TC-NF-UNIT-001     |
| Laufzeit unter 5 Sekunden              | `PerformanceController`                    | TC-NF-PERF-001     |

---

## 11. Designentscheidungen für Iteration 2

### 11.1 Keine eigene `CoolingLevelManager`-Klasse

**Entscheidung:**
Die Kühlungsstufen werden in Iteration 2 durch den `TemperatureSimulator` bereitgestellt und validiert.

**Begründung:**
Die Kühlungsstufe wird ausschließlich für die Temperaturberechnung benötigt. Eine zusätzliche Managerklasse würde die Architektur für Iteration 2 unnötig vergrößern.

**Konsequenz:**
`TemperatureSimulator` enthält neben der Temperaturberechnung auch Methoden zur Bereitstellung und Validierung der Kühlungsstufen.

---

### 11.2 Ereignisprotokollierung bleibt einfach

**Entscheidung:**
In Iteration 2 werden nur `MODUSWECHSEL`, `LASTSZENARIOWECHSEL` und `TEMPERATURWARNUNG` protokolliert.

**Begründung:**
Throttling-Ereignisse benötigen die vollständige Throttling-Logik aus Iteration 3.

**Konsequenz:**
`EventLogger` wird bereits eingeführt, aber fachlich noch nicht vollständig ausgebaut.

---

### 11.3 Effektive Taktfrequenz wird vorbereitet

**Entscheidung:**
In Iteration 2 entspricht die effektive CPU-Taktfrequenz der ungedrosselten CPU-Taktfrequenz.

**Begründung:**
Thermal Throttling ist noch nicht aktiv.

**Konsequenz:**
Der Stromverbrauch wird in Iteration 2 auf Basis der ungedrosselten CPU-Taktfrequenz berechnet. In Iteration 3 kann stattdessen die effektive CPU-Taktfrequenz verwendet werden.