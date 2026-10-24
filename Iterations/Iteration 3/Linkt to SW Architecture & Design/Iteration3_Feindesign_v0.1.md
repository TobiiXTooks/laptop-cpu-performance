# Iteration 3 – Feindesign

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt das Feindesign für Iteration 3 des Laptop-Prozessorleistungsmoduls.

Das Feindesign konkretisiert die Software-Architektur der Iteration 3 auf Klassen-, Methoden- und Datenmodellebene. Es legt fest:

* welche bestehenden Klassen aus Iteration 2 erweitert werden,
* welche neue Fachkomponente in Iteration 3 aktiviert wird,
* welche Attribute die Datenklassen zusätzlich benötigen,
* welche Methoden die Service- und Steuerklassen bereitstellen,
* welche Berechnungsregeln für Thermal Throttling verwendet werden,
* wie der Stromverbrauch nach Thermal Throttling neu berechnet wird,
* welche Ereignisse zusätzlich protokolliert werden,
* welche Requirements durch welche Klassen umgesetzt werden,
* welche Java-Dateien aus dem Design abgeleitet werden.

Iteration 3 erweitert die bestehende Simulation um mehrstufiges Thermal Throttling, effektive CPU-Taktfrequenz, vollständige Ereignisprotokollierung und Reset-Funktionalität.

---

## 2. Grundlage aus der Iteration-3-Architektur

Die Iteration-3-Architektur enthält folgende aktive Komponenten:

| Komponenten-ID | Komponente                    | Aufgabe                                                            |
| -------------- | ----------------------------- | ------------------------------------------------------------------ |
| CMP-009        | `UserInterface`               | Eingaben erfassen und vollständiges Ergebnis anzeigen              |
| CMP-008        | `PerformanceController`       | vollständigen Simulationsablauf koordinieren                       |
| CMP-001        | `PerformanceModeManager`      | Leistungsmodi bereitstellen und validieren                         |
| CMP-002        | `LoadScenarioManager`         | Lastszenarien bereitstellen und validieren                         |
| CMP-003        | `ClockCalculator`             | ungedrosselte CPU-Taktfrequenz berechnen                           |
| CMP-004        | `TemperatureSimulator`        | CPU-Temperatur berechnen                                           |
| CMP-005        | `ThermalThrottlingController` | Throttling-Stufe und effektive CPU-Taktfrequenz bestimmen          |
| CMP-006        | `PowerConsumptionCalculator`  | Stromverbrauch auf Basis der effektiven CPU-Taktfrequenz berechnen |
| CMP-007        | `EventLogger`                 | vollständige Ereignisse protokollieren                             |

Die Architektur wird als modulare Schichtenarchitektur beibehalten:

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

---

## 3. Designziele für Iteration 3

| Designziel-ID | Designziel           | Beschreibung                                                                     |
| ------------- | -------------------- | -------------------------------------------------------------------------------- |
| I3-DZ-001     | Bestandsschutz       | Funktionen aus Iteration 1 und 2 bleiben lauffähig.                              |
| I3-DZ-002     | Vollständigkeit      | Die noch offenen Requirements der Iteration 3 werden umgesetzt.                  |
| I3-DZ-003     | Kapselung            | Throttling-Logik wird in einer eigenen Klasse umgesetzt.                         |
| I3-DZ-004     | Nachvollziehbarkeit  | Ungedrosselte und effektive CPU-Taktfrequenz werden getrennt gespeichert.        |
| I3-DZ-005     | Testbarkeit          | Throttling-Stufen und Taktfaktoren sind isoliert prüfbar.                        |
| I3-DZ-006     | Determinismus        | Gleiche Eingaben erzeugen gleiche Throttling- und Verbrauchswerte.               |
| I3-DZ-007     | Traceability         | Neue Klassen, Methoden und Tests sind Requirements zuordenbar.                   |
| I3-DZ-008     | Kontrollierter Reset | Der Simulationszustand kann kontrolliert zurückgesetzt und protokolliert werden. |

---

## 4. Klassenübersicht

| Klasse                        | Typ                | Komponente                                  | Zweck                                                                                                            |
| ----------------------------- | ------------------ | ------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| `SimulationInput`             | Datenklasse        | CMP-009, CMP-008                            | Bündelt Eingaben für einen Simulationslauf.                                                                      |
| `PerformanceMode`             | Datenklasse        | CMP-001                                     | Beschreibt einen Leistungsmodus.                                                                                 |
| `LoadScenario`                | Datenklasse        | CMP-002                                     | Beschreibt ein Lastszenario mit CPU-Auslastung.                                                                  |
| `CoolingLevel`                | Datenklasse        | CMP-004                                     | Beschreibt eine Kühlungsstufe mit Kühlungsfaktor.                                                                |
| `ProcessorState`              | Datenklasse        | CMP-008, CMP-003, CMP-004, CMP-005, CMP-006 | Speichert CPU-Auslastung, ungedrosselten Takt, Temperatur, Throttling-Stufe, effektiven Takt und Stromverbrauch. |
| `SimulationResult`            | Datenklasse        | CMP-008, CMP-009                            | Bündelt die finalen Ergebniswerte und Ereignisse.                                                                |
| `LogEntry`                    | Datenklasse        | CMP-007                                     | Beschreibt einen Ereignisprotokolleintrag.                                                                       |
| `ThrottlingState`             | Datenklasse / Enum | CMP-005                                     | Beschreibt eine Throttling-Stufe mit Taktfaktor.                                                                 |
| `UserInterface`               | Steuer-/UI-Klasse  | CMP-009                                     | Nimmt Eingaben entgegen und zeigt Ergebnisse an.                                                                 |
| `PerformanceController`       | Steuerklasse       | CMP-008                                     | Koordiniert den vollständigen Simulationsablauf.                                                                 |
| `PerformanceModeManager`      | Serviceklasse      | CMP-001                                     | Verwaltet und validiert Leistungsmodi.                                                                           |
| `LoadScenarioManager`         | Serviceklasse      | CMP-002                                     | Verwaltet und validiert Lastszenarien.                                                                           |
| `ClockCalculator`             | Serviceklasse      | CMP-003                                     | Berechnet die ungedrosselte CPU-Taktfrequenz.                                                                    |
| `TemperatureSimulator`        | Serviceklasse      | CMP-004                                     | Berechnet die simulierte CPU-Temperatur.                                                                         |
| `ThermalThrottlingController` | Serviceklasse      | CMP-005                                     | Bestimmt Throttling-Stufe und effektive CPU-Taktfrequenz.                                                        |
| `PowerConsumptionCalculator`  | Serviceklasse      | CMP-006                                     | Berechnet den simulierten Stromverbrauch.                                                                        |
| `EventLogger`                 | Serviceklasse      | CMP-007                                     | Erfasst und verwaltet Ereignisse.                                                                                |

---

## 5. Datenklassen im Detail

### 5.1 `SimulationInput`

**Zweck:**
`SimulationInput` enthält die vom Nutzer gewählten Eingabedaten für einen Simulationslauf.

**Änderung gegenüber Iteration 2:**
Keine grundlegende Änderung. Die Klasse enthält weiterhin Leistungsmodus, Lastszenario, Kühlungsstufe und optionale benutzerdefinierte Moduswerte.

| Attribut               | Typ      | Pflicht                           | Beschreibung                                             |
| ---------------------- | -------- | --------------------------------- | -------------------------------------------------------- |
| `modeKey`              | `String` | ja                                | Kennung des ausgewählten Leistungsmodus.                 |
| `scenarioKey`          | `String` | ja                                | Kennung des ausgewählten Lastszenarios.                  |
| `coolingLevelKey`      | `String` | ja                                | Kennung der ausgewählten Kühlungsstufe.                  |
| `customMaxClockGhz`    | `Double` | nur bei benutzerdefiniertem Modus | Maximale CPU-Taktfrequenz des benutzerdefinierten Modus. |
| `customPowerLimitWatt` | `Double` | nur bei benutzerdefiniertem Modus | Stromverbrauchsgrenzwert des benutzerdefinierten Modus.  |

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

---

### 5.2 `ThrottlingState`

**Zweck:**
`ThrottlingState` beschreibt eine Throttling-Stufe. Die Klasse kann als `enum` umgesetzt werden, da die zulässigen Stufen fest definiert sind.

| Attribut                | Typ      | Pflicht | Beschreibung                                           |
| ----------------------- | -------- | ------- | ------------------------------------------------------ |
| `key`                   | `String` | ja      | Eindeutige Kennung der Throttling-Stufe.               |
| `displayName`           | `String` | ja      | Anzeigename der Throttling-Stufe.                      |
| `minTemperatureCelsius` | `double` | ja      | Untere Temperaturgrenze der Stufe.                     |
| `maxTemperatureCelsius` | `double` | ja      | Obere Temperaturgrenze der Stufe.                      |
| `clockFactor`           | `double` | ja      | Faktor zur Berechnung der effektiven CPU-Taktfrequenz. |

**Zulässige Werte:**

| key         | displayName | minTemperatureCelsius | maxTemperatureCelsius | clockFactor |
| ----------- | ----------- | --------------------: | --------------------: | ----------: |
| `NORMAL`    | Normal      |                    35 |                    69 |        1.00 |
| `WARNUNG`   | Warnung     |                    70 |                    79 |        1.00 |
| `REDUZIERT` | Reduziert   |                    80 |                    89 |        0.80 |
| `KRITISCH`  | Kritisch    |                    90 |                   100 |        0.60 |

**Methoden:**

| Methode                      | Rückgabe | Beschreibung                             |
| ---------------------------- | -------- | ---------------------------------------- |
| `getKey()`                   | `String` | Gibt die Kennung zurück.                 |
| `getDisplayName()`           | `String` | Gibt den Anzeigenamen zurück.            |
| `getMinTemperatureCelsius()` | `double` | Gibt die untere Temperaturgrenze zurück. |
| `getMaxTemperatureCelsius()` | `double` | Gibt die obere Temperaturgrenze zurück.  |
| `getClockFactor()`           | `double` | Gibt den Taktfaktor zurück.              |

---

### 5.3 `ProcessorState`

**Zweck:**
`ProcessorState` speichert den aktuellen Prozessorzustand innerhalb eines Simulationslaufs.

**Änderung gegenüber Iteration 2:**
Die Klasse wird um effektive CPU-Taktfrequenz und Throttling-Stufe erweitert.

| Attribut               | Typ                             | Pflicht                       | Beschreibung                                        |
| ---------------------- | ------------------------------- | ----------------------------- | --------------------------------------------------- |
| `cpuLoadPercent`       | `double`                        | ja                            | CPU-Auslastung in Prozent.                          |
| `clockGhz`             | `Double`                        | ja, nach Taktberechnung       | Ungedrosselte CPU-Taktfrequenz in GHz.              |
| `temperatureCelsius`   | `Double`                        | ja, nach Temperaturberechnung | Simulierte CPU-Temperatur in °C.                    |
| `throttlingState`      | `String` oder `ThrottlingState` | ja, nach Throttling-Bewertung | Aktuelle Throttling-Stufe.                          |
| `effectiveClockGhz`    | `Double`                        | ja, nach Throttling-Bewertung | Effektive CPU-Taktfrequenz nach Thermal Throttling. |
| `powerConsumptionWatt` | `Double`                        | ja, nach Verbrauchsberechnung | Simulierter Stromverbrauch in W.                    |

**Methoden:**

| Methode                                                              | Rückgabe                        | Beschreibung                                                  |
| -------------------------------------------------------------------- | ------------------------------- | ------------------------------------------------------------- |
| `getCpuLoadPercent()`                                                | `double`                        | Gibt die CPU-Auslastung zurück.                               |
| `getClockGhz()`                                                      | `Double`                        | Gibt die ungedrosselte CPU-Taktfrequenz zurück.               |
| `getTemperatureCelsius()`                                            | `Double`                        | Gibt die CPU-Temperatur zurück.                               |
| `getThrottlingState()`                                               | `String` oder `ThrottlingState` | Gibt die Throttling-Stufe zurück.                             |
| `getEffectiveClockGhz()`                                             | `Double`                        | Gibt die effektive CPU-Taktfrequenz zurück.                   |
| `getPowerConsumptionWatt()`                                          | `Double`                        | Gibt den Stromverbrauch zurück.                               |
| `updateClock(double clockGhz)`                                       | `void`                          | Aktualisiert die ungedrosselte CPU-Taktfrequenz.              |
| `updateTemperature(double temperatureCelsius)`                       | `void`                          | Aktualisiert die CPU-Temperatur.                              |
| `updateThrottling(String throttlingState, double effectiveClockGhz)` | `void`                          | Aktualisiert Throttling-Stufe und effektive CPU-Taktfrequenz. |
| `updatePowerConsumption(double powerConsumptionWatt)`                | `void`                          | Aktualisiert den Stromverbrauch.                              |
| `hasClock()`                                                         | `boolean`                       | Prüft, ob eine ungedrosselte Taktfrequenz vorhanden ist.      |
| `hasTemperature()`                                                   | `boolean`                       | Prüft, ob eine Temperatur vorhanden ist.                      |
| `hasThrottlingState()`                                               | `boolean`                       | Prüft, ob eine Throttling-Stufe vorhanden ist.                |
| `hasEffectiveClock()`                                                | `boolean`                       | Prüft, ob eine effektive CPU-Taktfrequenz vorhanden ist.      |
| `hasPowerConsumption()`                                              | `boolean`                       | Prüft, ob ein Stromverbrauch vorhanden ist.                   |

---

### 5.4 `SimulationResult`

**Zweck:**
`SimulationResult` bündelt die finalen Ergebniswerte eines Simulationslaufs.

**Änderung gegenüber Iteration 2:**
Die Klasse wird um Throttling-Stufe und effektive CPU-Taktfrequenz erweitert.

| Attribut          | Typ               | Pflicht | Beschreibung                                                     |
| ----------------- | ----------------- | ------- | ---------------------------------------------------------------- |
| `performanceMode` | `PerformanceMode` | ja      | Aktueller Leistungsmodus.                                        |
| `loadScenario`    | `LoadScenario`    | ja      | Aktuelles Lastszenario.                                          |
| `coolingLevel`    | `CoolingLevel`    | ja      | Aktuelle Kühlungsstufe.                                          |
| `processorState`  | `ProcessorState`  | ja      | Prozessorzustand mit Takt, Temperatur, Throttling und Verbrauch. |
| `logEntries`      | `List<LogEntry>`  | ja      | Ereignisse des Simulationslaufs.                                 |
| `summary`         | `String`          | ja      | Zusammenfassung des Simulationsergebnisses.                      |

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

**Ausgabeinhalte in `toDisplayText()`:**

| Ausgabewert                    | Einheit / Darstellung |
| ------------------------------ | --------------------- |
| Leistungsmodus                 | Text                  |
| Lastszenario                   | Text                  |
| Kühlungsstufe                  | Text                  |
| CPU-Auslastung                 | `%`                   |
| Ungedrosselte CPU-Taktfrequenz | `GHz`                 |
| Effektive CPU-Taktfrequenz     | `GHz`                 |
| CPU-Temperatur                 | `°C`                  |
| Throttling-Stufe               | Text                  |
| Stromverbrauch                 | `W`                   |
| Ereignisprotokoll              | chronologische Liste  |

---

### 5.5 `LogEntry`

**Zweck:**
`LogEntry` beschreibt einen Ereignisprotokolleintrag.

**Änderung gegenüber Iteration 2:**
Die Klasse wird für Throttling- und Reset-Ereignisse weiterverwendet. Die gespeicherte CPU-Taktfrequenz bezieht sich in Iteration 3 auf die effektive CPU-Taktfrequenz.

| Attribut              | Typ             | Pflicht | Beschreibung                            |
| --------------------- | --------------- | ------- | --------------------------------------- |
| `timestamp`           | `LocalDateTime` | ja      | Zeitpunkt der Ereigniserfassung.        |
| `eventType`           | `String`        | ja      | Definierter Ereignistyp.                |
| `description`         | `String`        | ja      | Textuelle Beschreibung des Ereignisses. |
| `performanceModeName` | `String`        | ja      | Aktueller Leistungsmodus.               |
| `loadScenarioName`    | `String`        | ja      | Aktuelles Lastszenario.                 |
| `cpuLoadPercent`      | `double`        | ja      | Aktuelle CPU-Auslastung.                |
| `clockGhz`            | `double`        | ja      | Aktuelle effektive CPU-Taktfrequenz.    |
| `temperatureCelsius`  | `double`        | ja      | Aktuelle CPU-Temperatur.                |
| `throttlingState`     | `String`        | ja      | Aktuelle Throttling-Stufe.              |

**Zulässige Ereignistypen in Iteration 3:**

| Ereignistyp                | Bedeutung                                                                               |
| -------------------------- | --------------------------------------------------------------------------------------- |
| `MODUSWECHSEL`             | Leistungsmodus wurde ausgewählt oder gegenüber dem vorherigen Simulationslauf geändert. |
| `LASTSZENARIOWECHSEL`      | Lastszenario wurde ausgewählt oder gegenüber dem vorherigen Simulationslauf geändert.   |
| `TEMPERATURWARNUNG`        | CPU-Temperatur beträgt mindestens 70 °C.                                                |
| `THROTTLING_AKTIVIERT`     | Throttling-Stufe ist `Reduziert` oder `Kritisch`.                                       |
| `THROTTLING_GEÄNDERT`      | Throttling-Stufe wurde gegenüber dem vorherigen Zustand geändert.                       |
| `SIMULATION_ZURÜCKGESETZT` | Simulationslauf wurde zurückgesetzt.                                                    |

**Methoden:**

| Methode            | Rückgabe        | Beschreibung                                           |
| ------------------ | --------------- | ------------------------------------------------------ |
| `getTimestamp()`   | `LocalDateTime` | Gibt den Ereigniszeitpunkt zurück.                     |
| `getEventType()`   | `String`        | Gibt den Ereignistyp zurück.                           |
| `getDescription()` | `String`        | Gibt die Ereignisbeschreibung zurück.                  |
| `toDisplayText()`  | `String`        | Gibt den Ereigniseintrag als formatierten Text zurück. |

---

## 6. Service- und Steuerklassen im Detail

### 6.1 `ThermalThrottlingController`

**Komponente:** CMP-005
**Zugeordnete Requirements:** REQ-F-024, REQ-F-025, REQ-F-026, REQ-F-027, REQ-F-028, REQ-F-029

**Zweck:**
`ThermalThrottlingController` bestimmt anhand der CPU-Temperatur die Throttling-Stufe und berechnet daraus die effektive CPU-Taktfrequenz.

| Attribut               | Typ                   | Beschreibung                                |
| ---------------------- | --------------------- | ------------------------------------------- |
| `NORMAL_MIN_CELSIUS`   | `static final double` | Untere Grenze der Stufe `Normal`, 35 °C.    |
| `NORMAL_MAX_CELSIUS`   | `static final double` | Obere Grenze der Stufe `Normal`, 69 °C.     |
| `WARNING_MIN_CELSIUS`  | `static final double` | Untere Grenze der Stufe `Warnung`, 70 °C.   |
| `WARNING_MAX_CELSIUS`  | `static final double` | Obere Grenze der Stufe `Warnung`, 79 °C.    |
| `REDUCED_MIN_CELSIUS`  | `static final double` | Untere Grenze der Stufe `Reduziert`, 80 °C. |
| `REDUCED_MAX_CELSIUS`  | `static final double` | Obere Grenze der Stufe `Reduziert`, 89 °C.  |
| `CRITICAL_MIN_CELSIUS` | `static final double` | Untere Grenze der Stufe `Kritisch`, 90 °C.  |
| `CRITICAL_MAX_CELSIUS` | `static final double` | Obere Grenze der Stufe `Kritisch`, 100 °C.  |

**Methoden:**

| Methode                                                                       | Rückgabe                        | Beschreibung                                                                            |
| ----------------------------------------------------------------------------- | ------------------------------- | --------------------------------------------------------------------------------------- |
| `determineThrottlingState(double temperatureCelsius)`                         | `String` oder `ThrottlingState` | Bestimmt die Throttling-Stufe aus der CPU-Temperatur.                                   |
| `calculateEffectiveClock(double unthrottledClockGhz, String throttlingState)` | `double`                        | Berechnet die effektive CPU-Taktfrequenz.                                               |
| `applyThrottling(ProcessorState processorState)`                              | `void`                          | Bestimmt Throttling-Stufe und effektiven Takt und speichert beides im `ProcessorState`. |
| `isThrottlingActive(String throttlingState)`                                  | `boolean`                       | Gibt `true` zurück, wenn die Stufe `Reduziert` oder `Kritisch` ist.                     |

**Berechnungsregel für Throttling-Stufe:**

```text
35 °C bis 69 °C  -> Normal
70 °C bis 79 °C  -> Warnung
80 °C bis 89 °C  -> Reduziert
90 °C bis 100 °C -> Kritisch
```

**Berechnungsregel für effektive CPU-Taktfrequenz:**

```text
effektive CPU-Taktfrequenz = ungedrosselte CPU-Taktfrequenz * Taktfaktor
```

| Throttling-Stufe | Taktfaktor |
| ---------------- | ---------: |
| `Normal`         |       1.00 |
| `Warnung`        |       1.00 |
| `Reduziert`      |       0.80 |
| `Kritisch`       |       0.60 |

**Beispiel:**

```text
ungedrosselte CPU-Taktfrequenz = 4.0 GHz
Throttling-Stufe = Reduziert
effektive CPU-Taktfrequenz = 4.0 GHz * 0.80 = 3.2 GHz
```

---

### 6.2 `PowerConsumptionCalculator`

**Komponente:** CMP-006
**Zugeordnete Requirements:** REQ-F-017, REQ-F-018, REQ-F-019

**Zweck:**
`PowerConsumptionCalculator` berechnet den simulierten Stromverbrauch in Watt.

**Änderung gegenüber Iteration 2:**
In Iteration 3 verwendet die Berechnung die effektive CPU-Taktfrequenz nach Thermal Throttling.

| Attribut         | Typ                   | Beschreibung                             |
| ---------------- | --------------------- | ---------------------------------------- |
| `MIN_POWER_WATT` | `static final double` | Minimal zulässiger Stromverbrauch, 8 W.  |
| `MAX_POWER_WATT` | `static final double` | Maximal zulässiger Stromverbrauch, 65 W. |

**Methoden:**

| Methode                                                                                     | Rückgabe | Beschreibung                                                            |
| ------------------------------------------------------------------------------------------- | -------- | ----------------------------------------------------------------------- |
| `calculatePowerConsumption(PerformanceMode performanceMode, ProcessorState processorState)` | `double` | Berechnet den Stromverbrauch auf Basis der effektiven CPU-Taktfrequenz. |
| `clampPowerConsumption(double powerWatt)`                                                   | `double` | Begrenzt den Stromverbrauch auf 8 W bis 65 W.                           |

**Berechnungsregel:**

```text
Lastfaktor = 0,30 + 0,70 * (CPU-Auslastung / 100)
Taktfaktor = effektive CPU-Taktfrequenz / maximale CPU-Taktfrequenz des Leistungsmodus
Rohwert = Power-Limit des Leistungsmodus * Lastfaktor * Taktfaktor
Stromverbrauch = Rohwert begrenzt auf 8 W bis 65 W
```

**Hinweis:**
Falls im `ProcessorState` noch keine effektive CPU-Taktfrequenz vorhanden ist, darf die Berechnung nicht unkontrolliert fortgesetzt werden. Die Implementierung soll diesen Fall kontrolliert mit einer Ausnahme oder einer definierten Fehlerbehandlung abbrechen.

---

### 6.3 `EventLogger`

**Komponente:** CMP-007
**Zugeordnete Requirements:** REQ-F-020, REQ-F-021, REQ-F-022, REQ-F-023, REQ-F-030, REQ-F-031, REQ-F-032, REQ-F-033

**Zweck:**
`EventLogger` erfasst Ereignisse eines Simulationslaufs und stellt sie chronologisch bereit.

**Änderung gegenüber Iteration 2:**
Der `EventLogger` wird um Throttling- und Reset-Ereignisse erweitert.

| Attribut              | Typ              | Beschreibung                             |
| --------------------- | ---------------- | ---------------------------------------- |
| `entries`             | `List<LogEntry>` | Liste der erfassten Ereignisse.          |
| `lastModeKey`         | `String`         | Zuletzt protokollierter Leistungsmodus.  |
| `lastScenarioKey`     | `String`         | Zuletzt protokolliertes Lastszenario.    |
| `lastThrottlingState` | `String`         | Zuletzt protokollierte Throttling-Stufe. |

**Methoden:**

| Methode                                                                                                                                        | Rückgabe         | Beschreibung                                                                                                 |
| ---------------------------------------------------------------------------------------------------------------------------------------------- | ---------------- | ------------------------------------------------------------------------------------------------------------ |
| `logModeChangeIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                             | `void`           | Erfasst `MODUSWECHSEL`, wenn der Modus neu oder geändert ist.                                                |
| `logScenarioChangeIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                         | `void`           | Erfasst `LASTSZENARIOWECHSEL`, wenn das Szenario neu oder geändert ist.                                      |
| `logTemperatureWarningIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                     | `void`           | Erfasst `TEMPERATURWARNUNG`, wenn die Temperatur mindestens 70 °C beträgt.                                   |
| `logThrottlingActivatedIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                    | `void`           | Erfasst `THROTTLING_AKTIVIERT`, wenn die Stufe `Reduziert` oder `Kritisch` erreicht wird.                    |
| `logThrottlingChangedIfNeeded(PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)`                      | `void`           | Erfasst `THROTTLING_GEÄNDERT`, wenn sich die Throttling-Stufe gegenüber dem vorherigen Zustand geändert hat. |
| `logSimulationReset()`                                                                                                                         | `void`           | Erfasst `SIMULATION_ZURÜCKGESETZT`.                                                                          |
| `createEntry(String eventType, String description, PerformanceMode performanceMode, LoadScenario loadScenario, ProcessorState processorState)` | `LogEntry`       | Erstellt einen Ereignisprotokolleintrag.                                                                     |
| `getEntries()`                                                                                                                                 | `List<LogEntry>` | Gibt eine Kopie der Ereignisliste zurück.                                                                    |
| `getEntriesChronological()`                                                                                                                    | `List<LogEntry>` | Gibt die Ereignisliste chronologisch sortiert zurück.                                                        |
| `clear()`                                                                                                                                      | `void`           | Leert das Ereignisprotokoll.                                                                                 |

**Regel für Reset:**
Bei einem Reset wird zuerst das Ereignis `SIMULATION_ZURÜCKGESETZT` erfasst. Danach können gespeicherte Zustandswerte wie `lastModeKey`, `lastScenarioKey` und `lastThrottlingState` zurückgesetzt werden.

---

### 6.4 `PerformanceController`

**Komponente:** CMP-008
**Zugeordnete Requirements:** REQ-F-019, REQ-F-024 bis REQ-F-033

**Zweck:**
`PerformanceController` koordiniert den vollständigen Simulationsablauf.

**Neue Abhängigkeit gegenüber Iteration 2:**

| Attribut                      | Typ                           | Beschreibung                                                 |
| ----------------------------- | ----------------------------- | ------------------------------------------------------------ |
| `thermalThrottlingController` | `ThermalThrottlingController` | Service für Throttling-Stufe und effektive CPU-Taktfrequenz. |

**Weiterhin bestehende Abhängigkeiten:**

| Attribut                     | Typ                          | Beschreibung                                |
| ---------------------------- | ---------------------------- | ------------------------------------------- |
| `performanceModeManager`     | `PerformanceModeManager`     | Service für Leistungsmodi.                  |
| `loadScenarioManager`        | `LoadScenarioManager`        | Service für Lastszenarien.                  |
| `clockCalculator`            | `ClockCalculator`            | Service für ungedrosselte CPU-Taktfrequenz. |
| `temperatureSimulator`       | `TemperatureSimulator`       | Service für Temperaturberechnung.           |
| `powerConsumptionCalculator` | `PowerConsumptionCalculator` | Service für Stromverbrauch.                 |
| `eventLogger`                | `EventLogger`                | Service für Ereignisprotokollierung.        |

**Methoden:**

| Methode                                                                                                                                                         | Rückgabe           | Beschreibung                                                                     |
| --------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ | -------------------------------------------------------------------------------- |
| `runSimulation(SimulationInput inputData)`                                                                                                                      | `SimulationResult` | Führt den vollständigen Simulationslauf aus.                                     |
| `createInitialState(LoadScenario loadScenario)`                                                                                                                 | `ProcessorState`   | Erstellt den Startzustand mit CPU-Auslastung.                                    |
| `createResult(PerformanceMode performanceMode, LoadScenario loadScenario, CoolingLevel coolingLevel, ProcessorState processorState, List<LogEntry> logEntries)` | `SimulationResult` | Erstellt das vollständige Simulationsergebnis.                                   |
| `resetSimulation()`                                                                                                                                             | `void`             | Setzt den Simulationszustand zurück und lässt ein Reset-Ereignis protokollieren. |

**Ablauf in `runSimulation`:**

```text
1. Eingabe prüfen
2. Leistungsmodus bestimmen
3. Lastszenario bestimmen
4. Kühlungsstufe bestimmen
5. ProcessorState erstellen
6. Ungedrosselte CPU-Taktfrequenz berechnen
7. CPU-Temperatur berechnen
8. Throttling-Stufe bestimmen
9. Effektive CPU-Taktfrequenz berechnen
10. Stromverbrauch mit effektiver CPU-Taktfrequenz berechnen
11. Ereignisse protokollieren
12. SimulationResult erstellen und zurückgeben
```

**Wichtige Designregel:**
Der `PerformanceController` berechnet keine Throttling-Stufen, keine effektive CPU-Taktfrequenz und keinen Stromverbrauch selbst. Er ruft dafür die zuständigen Serviceklassen auf.

---

### 6.5 `UserInterface`

**Komponente:** CMP-009
**Zugeordnete Requirements:** REQ-F-028, REQ-F-032, REQ-NF-006

**Zweck:**
`UserInterface` nimmt Nutzereingaben entgegen und zeigt das vollständige Ergebnis an.

**Änderungen gegenüber Iteration 2:**

| Bereich           | Änderung                                                                     |
| ----------------- | ---------------------------------------------------------------------------- |
| Ergebnisanzeige   | Ausgabe der Throttling-Stufe ergänzen.                                       |
| Ergebnisanzeige   | Ausgabe der effektiven CPU-Taktfrequenz ergänzen.                            |
| Ereignisprotokoll | Chronologische Ereignisliste anzeigen.                                       |
| Reset             | Reset-Funktion über Konsolenablauf oder internen Methodenaufruf ermöglichen. |

**Methoden:**

| Methode                                     | Rückgabe          | Beschreibung                                              |
| ------------------------------------------- | ----------------- | --------------------------------------------------------- |
| `readInput()`                               | `SimulationInput` | Liest Leistungsmodus, Lastszenario und Kühlungsstufe ein. |
| `showResult(SimulationResult result)`       | `void`            | Gibt das vollständige Simulationsergebnis aus.            |
| `showLogEntries(List<LogEntry> logEntries)` | `void`            | Gibt das Ereignisprotokoll chronologisch aus.             |
| `showError(String message)`                 | `void`            | Gibt eine Fehlermeldung aus.                              |

---

## 7. Berechnungslogik der Iteration 3

### 7.1 Taktberechnung

Die ungedrosselte CPU-Taktfrequenz wird weiterhin durch `ClockCalculator` berechnet.

```text
ungedrosselte CPU-Taktfrequenz = Ergebnis der bestehenden Taktberechnung aus Modus und Lastszenario
```

Diese Berechnung bleibt unverändert, damit die Regression aus Iteration 1 stabil bleibt.

---

### 7.2 Temperaturberechnung

Die Temperaturberechnung bleibt Aufgabe des `TemperatureSimulator`.

```text
Rohwert = 35 + ((CPU-Auslastung * 0,35) + (CPU-Taktfrequenz * 8,0)) / Kühlungsfaktor
CPU-Temperatur = Rohwert begrenzt auf 35 °C bis 100 °C
```

Für die Temperaturberechnung wird die ungedrosselte CPU-Taktfrequenz verwendet.

---

### 7.3 Thermal Throttling

Die Throttling-Stufe wird anhand der CPU-Temperatur bestimmt.

| Temperaturbereich | Throttling-Stufe | Effektive CPU-Taktfrequenz                |
| ----------------: | ---------------- | ----------------------------------------- |
|   35 °C bis 69 °C | `Normal`         | 100 % der ungedrosselten CPU-Taktfrequenz |
|   70 °C bis 79 °C | `Warnung`        | 100 % der ungedrosselten CPU-Taktfrequenz |
|   80 °C bis 89 °C | `Reduziert`      | 80 % der ungedrosselten CPU-Taktfrequenz  |
|  90 °C bis 100 °C | `Kritisch`       | 60 % der ungedrosselten CPU-Taktfrequenz  |

---

### 7.4 Stromverbrauch nach Thermal Throttling

Der Stromverbrauch wird nach der Throttling-Bewertung berechnet.

```text
Lastfaktor = 0,30 + 0,70 * (CPU-Auslastung / 100)
Taktfaktor = effektive CPU-Taktfrequenz / maximale CPU-Taktfrequenz des Leistungsmodus
Rohwert = Power-Limit des Leistungsmodus * Lastfaktor * Taktfaktor
Stromverbrauch = Rohwert begrenzt auf 8 W bis 65 W
```

Damit erfüllt die Berechnung REQ-F-019, weil eine Änderung der effektiven CPU-Taktfrequenz durch Thermal Throttling vor der Verbrauchsausgabe berücksichtigt wird.

---

## 8. Zustandsführung

Der `ProcessorState` wird in Iteration 3 schrittweise befüllt:

| Schritt | Verantwortliche Klasse        | Aktualisierte Werte                          |
| ------- | ----------------------------- | -------------------------------------------- |
| 1       | `PerformanceController`       | CPU-Auslastung                               |
| 2       | `ClockCalculator`             | ungedrosselte CPU-Taktfrequenz               |
| 3       | `TemperatureSimulator`        | CPU-Temperatur                               |
| 4       | `ThermalThrottlingController` | Throttling-Stufe, effektive CPU-Taktfrequenz |
| 5       | `PowerConsumptionCalculator`  | Stromverbrauch                               |
| 6       | `EventLogger`                 | Ereigniseinträge                             |

Der Zustand darf nur über definierte Update-Methoden verändert werden.

---

## 9. Ereignislogik

### 9.1 Temperaturwarnung

```text
Wenn CPU-Temperatur >= 70 °C:
    Ereignis TEMPERATURWARNUNG erfassen
```

### 9.2 Throttling aktiviert

```text
Wenn Throttling-Stufe == Reduziert oder Kritisch:
    Ereignis THROTTLING_AKTIVIERT erfassen
```

### 9.3 Throttling geändert

```text
Wenn aktuelle Throttling-Stufe != vorherige Throttling-Stufe:
    Ereignis THROTTLING_GEÄNDERT erfassen
```

### 9.4 Simulation zurückgesetzt

```text
Wenn resetSimulation() ausgeführt wird:
    Ereignis SIMULATION_ZURÜCKGESETZT erfassen
    internen Zustand zurücksetzen
```

---

## 10. Zuordnung Requirements zu Klassen

| Requirement-ID | Klasse / Datenmodell                                                           | Umsetzung                                                               |
| -------------- | ------------------------------------------------------------------------------ | ----------------------------------------------------------------------- |
| REQ-F-019      | `PowerConsumptionCalculator`, `ProcessorState`, `PerformanceController`        | Stromverbrauch nach effektiver CPU-Taktfrequenz neu berechnen.          |
| REQ-F-024      | `ThermalThrottlingController`                                                  | Throttling-Stufe aus CPU-Temperatur bestimmen.                          |
| REQ-F-025      | `ThermalThrottlingController`, `ProcessorState`                                | Effektive CPU-Taktfrequenz bei `Normal` und `Warnung` auf 100 % setzen. |
| REQ-F-026      | `ThermalThrottlingController`, `ProcessorState`                                | Effektive CPU-Taktfrequenz bei `Reduziert` auf 80 % setzen.             |
| REQ-F-027      | `ThermalThrottlingController`, `ProcessorState`                                | Effektive CPU-Taktfrequenz bei `Kritisch` auf 60 % setzen.              |
| REQ-F-028      | `SimulationResult`, `UserInterface`, `ProcessorState`                          | Throttling-Stufe ausgeben.                                              |
| REQ-F-029      | `PerformanceController`, `ThermalThrottlingController`, `TemperatureSimulator` | Throttling-Stufe bei geänderter Temperatur neu bestimmen.               |
| REQ-F-030      | `EventLogger`, `LogEntry`, `ThermalThrottlingController`                       | `THROTTLING_AKTIVIERT` erfassen.                                        |
| REQ-F-031      | `EventLogger`, `LogEntry`, `ProcessorState`                                    | `THROTTLING_GEÄNDERT` erfassen.                                         |
| REQ-F-032      | `EventLogger`, `SimulationResult`, `UserInterface`                             | Ereignisprotokoll chronologisch ausgeben.                               |
| REQ-F-033      | `PerformanceController`, `EventLogger`                                         | Simulationslauf zurücksetzen und Reset-Ereignis erfassen.               |

---

## 11. Vorgesehene Java-Dateien

| Datei                                          | Zweck                                                           |
| ---------------------------------------------- | --------------------------------------------------------------- |
| `src/model/ThrottlingState.java`               | Enum oder Datenklasse für Throttling-Stufen.                    |
| `src/model/ProcessorState.java`                | Erweiterung um Throttling-Stufe und effektive CPU-Taktfrequenz. |
| `src/model/SimulationResult.java`              | Erweiterung der Ergebnisanzeige.                                |
| `src/model/LogEntry.java`                      | Nutzung für neue Ereignistypen.                                 |
| `src/service/ThermalThrottlingController.java` | Neue Serviceklasse für Thermal Throttling.                      |
| `src/service/PowerConsumptionCalculator.java`  | Anpassung auf effektive CPU-Taktfrequenz.                       |
| `src/service/EventLogger.java`                 | Erweiterung um Throttling- und Reset-Ereignisse.                |
| `src/controller/PerformanceController.java`    | Erweiterung des Simulationsablaufs.                             |
| `src/ui/UserInterface.java`                    | Erweiterung der Ergebnis- und Logausgabe.                       |
| `src/Main.java`                                | Instanziierung der neuen Throttling-Komponente.                 |

---

## 12. Auswirkungen auf Tests

Aus diesem Feindesign ergeben sich folgende Testbereiche:

| Testbereich                             | Testfokus                                                       |
| --------------------------------------- | --------------------------------------------------------------- |
| Modultest `ThermalThrottlingController` | Stufenzuordnung und effektive CPU-Taktfrequenz.                 |
| Grenzwerttest                           | Temperaturgrenzen 69/70, 79/80 und 89/90 °C.                    |
| Integrationstest                        | Temperatur → Throttling → effektiver Takt → Stromverbrauch.     |
| Logging-Test                            | Throttling- und Reset-Ereignisse.                               |
| Ausgabetest                             | Throttling-Stufe und effektive CPU-Taktfrequenz mit Einheit.    |
| Regressionstest                         | Funktionen aus Iteration 1 und 2 bleiben unverändert lauffähig. |
| Nicht-funktionaler Test                 | Laufzeit, Einheiten, deterministische Ergebnisse.               |

---

## 13. Designentscheidungen der Iteration 3

### 13.1 `ThrottlingState` als eigenes Modell

| Feld         | Beschreibung                                                                       |
| ------------ | ---------------------------------------------------------------------------------- |
| Entscheidung | Die Throttling-Stufe wird als eigenes Modell oder Enum geführt.                    |
| Begründung   | Die Stufen besitzen definierte Temperaturbereiche und Taktfaktoren.                |
| Konsequenz   | Throttling-Stufen können eindeutig getestet und in Traceability zugeordnet werden. |

### 13.2 Effektive CPU-Taktfrequenz getrennt speichern

| Feld         | Beschreibung                                                                                           |
| ------------ | ------------------------------------------------------------------------------------------------------ |
| Entscheidung | `ProcessorState` speichert ungedrosselte und effektive CPU-Taktfrequenz getrennt.                      |
| Begründung   | So bleibt nachvollziehbar, welche Wirkung Thermal Throttling auf die ursprüngliche Taktfrequenz hatte. |
| Konsequenz   | Ausgabe und Tests können beide Werte getrennt prüfen.                                                  |

### 13.3 Stromverbrauch nach Throttling berechnen

| Feld         | Beschreibung                                                                                     |
| ------------ | ------------------------------------------------------------------------------------------------ |
| Entscheidung | Der Stromverbrauch wird nach der Throttling-Bewertung mit effektiver CPU-Taktfrequenz berechnet. |
| Begründung   | Dadurch wird REQ-F-019 vollständig erfüllt.                                                      |
| Konsequenz   | Die Reihenfolge im `PerformanceController` ist verbindlich.                                      |

---

## 14. Designstatus am Ende der Iteration 3

Am Ende der Iteration 3 soll folgender Designstatus erreicht sein:

| Bereich                       | Erwarteter Status                                             |
| ----------------------------- | ------------------------------------------------------------- |
| `ThermalThrottlingController` | vollständig entworfen, implementiert und getestet             |
| `ProcessorState`              | um Throttling-Stufe und effektive CPU-Taktfrequenz erweitert  |
| `SimulationResult`            | vollständige Ausgabe aller Ergebniswerte                      |
| `PowerConsumptionCalculator`  | Berechnung auf effektive CPU-Taktfrequenz umgestellt          |
| `EventLogger`                 | vollständige Ereignisprotokollierung umgesetzt                |
| `PerformanceController`       | finaler Simulationsablauf umgesetzt                           |
| `UserInterface`               | vollständige Ergebnis- und Loganzeige umgesetzt               |
| Traceability                  | Requirements REQ-F-019 und REQ-F-024 bis REQ-F-033 zuordenbar |

---
