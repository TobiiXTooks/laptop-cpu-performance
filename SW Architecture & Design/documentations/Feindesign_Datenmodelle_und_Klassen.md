# Feindesign: Datenmodelle und Klassen

**Version:** 0.2  

---

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt das erste Feindesign des **Laptop-Prozessorleistungsmoduls**.

Es konkretisiert die bisher erarbeitete Software-Architektur auf Klassen- und Datenmodellebene. Während die bisherigen Dokumente Systemkontext, Architekturziele, Komponenten, Schnittstellen und Datenfluss beschreiben, legt dieses Dokument fest:

- welche zentralen Klassen und Datenmodelle benötigt werden,
- welche Attribute diese Klassen besitzen,
- welche Methoden und Verantwortlichkeiten vorgesehen sind,
- wie die Klassen den Komponenten zugeordnet werden,
- wie die Klassen in den Iterationen erweitert werden,
- welche Designentscheidungen für Implementierung, Tests und Traceability relevant sind.

Dieses Dokument bildet die Grundlage für die spätere Implementierung im `src`-Verzeichnis.

---

## 2. Einordnung in die bisherige Architektur

Die bisherige Architektur verwendet eine modulare Schichtenarchitektur. Die Fachlogik ist in getrennte Komponenten aufgeteilt, während `PerformanceController` den Ablauf koordiniert und `UserInterface` die Ein- und Ausgabe übernimmt.

Die Komponenten kommunizieren über strukturierte Datenobjekte:

- `SimulationInput`
- `PerformanceMode`
- `LoadScenario`
- `ProcessorState`
- `SimulationResult`
- `LogEntry`

---

## 3. Designziele des Feindesigns

| Ziel-ID | Designziel | Beschreibung |
|---|---|---|
| DZ-001 | Klare Datenstrukturen | Eingaben, Zwischenzustände und Ergebnisse werden durch eigene Klassen beschrieben. |
| DZ-002 | Geringe Kopplung | Fachkomponenten erhalten nur die Daten, die sie für ihre Aufgabe benötigen. |
| DZ-003 | Hohe Testbarkeit | Berechnungslogik wird in separaten Klassenmethoden gekapselt. |
| DZ-004 | Iterative Erweiterbarkeit | Klassen können in Iteration 2 und 3 erweitert werden, ohne Iteration 1 zu zerstören. |
| DZ-005 | Nachvollziehbarkeit | Klassen, Methoden und Attribute lassen sich Requirements, Komponenten und Tests zuordnen. |
| DZ-006 | Deterministisches Verhalten | Berechnungen liefern bei gleicher Eingabe identische Ergebnisse. |

---

## 4. Klassenübersicht

| Klasse | Typ | Zugehörige Komponente | Iteration | Zweck |
|---|---|---|---:|---|
| `SimulationInput` | Datenklasse | `UserInterface`, `PerformanceController` | 1 | Bündelt alle Eingaben eines Simulationslaufs. |
| `PerformanceMode` | Datenklasse | `PerformanceModeManager` | 1 | Beschreibt einen Leistungsmodus und dessen Faktoren. |
| `LoadScenario` | Datenklasse | `LoadScenarioManager` | 1 | Beschreibt ein Lastszenario und dessen Lastfaktor. |
| `ProcessorState` | Datenklasse | `PerformanceController`, Fachlogik | 1–3 | Speichert den aktuellen Prozessorzustand während der Simulation. |
| `SimulationResult` | Datenklasse | `PerformanceController`, `UserInterface` | 1–3 | Bündelt die finalen Ergebniswerte. |
| `LogEntry` | Datenklasse | `EventLogger` | 2-3 | Speichert einen Protokolleintrag. |
| `UserInterface` | Steuer-/UI-Klasse | `CMP-009` | 1–3 | Nimmt Benutzereingaben entgegen und zeigt Ergebnisse an. |
| `PerformanceController` | Steuerklasse | `CMP-008` | 1–3 | Koordiniert den Simulationsablauf. |
| `PerformanceModeManager` | Serviceklasse | `CMP-001` | 1 | Validiert und liefert Leistungsmodi. |
| `LoadScenarioManager` | Serviceklasse | `CMP-002` | 1 | Validiert und liefert Lastszenarien. |
| `ClockCalculator` | Serviceklasse | `CMP-003` | 1 | Berechnet die CPU-Taktfrequenz. |
| `TemperatureSimulator` | Serviceklasse | `CMP-004` | 2 | Simuliert die CPU-Temperatur. |
| `ThermalThrottlingController` | Serviceklasse | `CMP-005` | 3 | Bewertet Thermal Throttling. |
| `PowerConsumptionCalculator` | Serviceklasse | `CMP-006` | 2-3 | Berechnet die Leistungsaufnahme. |
| `EventLogger` | Serviceklasse | `CMP-007` | 2-3 | Protokolliert Simulationsereignisse. |

---

## 5. Datenklassen im Detail

### 5.1 `SimulationInput`

**Zweck:**  
`SimulationInput` bündelt alle Eingabedaten, die für einen Simulationslauf benötigt werden.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `mode_key` | `str` | ja | Kennung des gewählten Leistungsmodus. |
| `scenario_key` | `str` | ja | Kennung des gewählten Lastszenarios. |
| `cpu_load_percent` | `float` | ja | CPU-Auslastung in Prozent im Bereich 0 bis 100. |
| `cooling_level_key` | `str` | ja ab Iteration 2 | Kennung der gewählten Kühlungsstufe, z. B. `LOW`, `MEDIUM`, `HIGH`. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `is_valid()` | `bool` | Prüft, ob Pflichtwerte vorhanden sind, die CPU-Auslastung im gültigen Bereich liegt und ab Iteration 2 eine gültige Kühlungsstufe angegeben ist. |

---

### 5.2 `PerformanceMode`

**Zweck:**  
`PerformanceMode` beschreibt einen auswählbaren Leistungsmodus.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `key` | `str` | ja | Eindeutige Moduskennung, z. B. `ENERGY_SAVE`, `BALANCED`, `PERFORMANCE`. |
| `name` | `str` | ja | Anzeigename des Leistungsmodus. |
| `clock_factor` | `float` | ja | Faktor zur Beeinflussung der Taktberechnung. |
| `power_factor` | `float` | ja | Faktor zur Beeinflussung der Leistungsaufnahme. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `get_clock_factor()` | `float` | Gibt den Taktfaktor zurück. |
| `get_power_factor()` | `float` | Gibt den Verbrauchsfaktor zurück. |

---

### 5.3 `LoadScenario`

**Zweck:**  
`LoadScenario` beschreibt ein auswählbares Nutzungsszenario des Prozessors.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `key` | `str` | ja | Eindeutige Szenariokennung, z. B. `IDLE`, `OFFICE`, `STREAMING`, `GAMING`, `FULL_LOAD`. |
| `name` | `str` | ja | Anzeigename des Lastszenarios. |
| `load_factor` | `float` | ja | Faktor zur Gewichtung der Systemlast. |
| `default_load_percent` | `float` | nein | Standardauslastung des Szenarios in Prozent. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `get_load_factor()` | `float` | Gibt den Lastfaktor zurück. |

---

### 5.4 `ProcessorState`

**Zweck:**  
`ProcessorState` speichert den aktuellen Prozessorzustand während eines Simulationslaufs. Das Objekt wird schrittweise durch die Fachkomponenten erweitert.

| Attribut | Typ | Pflicht ab Iteration | Beschreibung |
|---|---|---:|---|
| `cpu_load_percent` | `float` | 1 | CPU-Auslastung in Prozent. |
| `clock_ghz` | `float` | 1 | Simulierte CPU-Taktfrequenz in GHz. |
| `temperature_celsius` | `float` | 2 | Simulierte CPU-Temperatur in Grad Celsius. |
| `is_throttling` | `bool` | 3 | Gibt an, ob Thermal Throttling aktiv ist. |
| `effective_clock_ghz` | `float` | 3 | Effektiver Takt nach möglichem Throttling. |
| `power_watt` | `float` | 2 | Simulierte Leistungsaufnahme in Watt. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `update_clock(clock_ghz)` | `None` | Speichert die berechnete Taktfrequenz. |
| `update_temperature(temperature_celsius)` | `None` | Speichert die simulierte Temperatur. |
| `update_throttling(is_throttling, effective_clock_ghz)` | `None` | Speichert Throttling-Status und effektiven Takt. |
| `update_power(power_watt)` | `None` | Speichert die berechnete Leistungsaufnahme. |

---

### 5.5 `SimulationResult`

**Zweck:**  
`SimulationResult` bündelt das Ergebnis eines vollständigen Simulationslaufs.

| Attribut | Typ | Pflicht ab Iteration | Beschreibung |
|---|---|---:|---|
| `mode` | `PerformanceMode` | 1 | Gewählter Leistungsmodus. |
| `scenario` | `LoadScenario` | 1 | Gewähltes Lastszenario. |
| `processor_state` | `ProcessorState` | 1 | Finaler Prozessorzustand. |
| `summary` | `str` | 1 | Textuelle Ergebniszusammenfassung. |
| `warnings` | `list[str]` | 2 | Hinweise, z. B. bei Thermal Throttling. |
| `log_entries` | `list[LogEntry]` | 3 | Protokolleinträge des Simulationslaufs. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `has_warnings()` | `bool` | Gibt an, ob Hinweise oder Warnungen vorhanden sind. |
| `to_display_text()` | `str` | Erstellt eine textuelle Darstellung für die Ausgabe. |

---

### 5.6 `LogEntry`

**Zweck:**  
`LogEntry` beschreibt einen einzelnen Protokolleintrag.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `step` | `str` | ja | Name des Verarbeitungsschritts. |
| `message` | `str` | ja | Beschreibung des Ereignisses. |
| `values` | `dict` | nein | Relevante Werte des Verarbeitungsschritts. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `to_log_line()` | `str` | Erstellt eine lesbare Protokollzeile. |

---

## 6. Service- und Steuerklassen im Detail

### 6.1 `UserInterface`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-009` |
| Schicht | Benutzerschnittstelle |
| Aufgabe | Eingaben erfassen und Ergebnisse anzeigen. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `read_input()` | keine | `SimulationInput` | Liest Leistungsmodus, Lastszenario und CPU-Auslastung ein. |
| `show_result(result)` | `SimulationResult` | `None` | Gibt das Simulationsergebnis aus. |
| `show_error(message)` | `str` | `None` | Gibt eine Fehlermeldung aus. |

---

### 6.2 `PerformanceController`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-008` |
| Schicht | Anwendungssteuerung |
| Aufgabe | Koordination des vollständigen Simulationsablaufs. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `run_simulation(input_data)` | `SimulationInput` | `SimulationResult` | Führt den vollständigen Simulationslauf aus. |
| `create_initial_state(input_data)` | `SimulationInput` | `ProcessorState` | Erzeugt den initialen Prozessorzustand. |
| `create_result(mode, scenario, state, log_entries)` | Datenobjekte | `SimulationResult` | Erstellt das Ergebnisobjekt. |

---

### 6.3 `PerformanceModeManager`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-001` |
| Schicht | Fachlogik / Services |
| Aufgabe | Bereitstellung und Validierung der Leistungsmodi. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `get_mode(mode_key)` | `str` | `PerformanceMode` | Liefert den gewählten Leistungsmodus. |
| `is_valid_mode(mode_key)` | `str` | `bool` | Prüft, ob die Moduskennung gültig ist. |
| `list_modes()` | keine | `list[PerformanceMode]` | Gibt alle verfügbaren Leistungsmodi zurück. |

---

### 6.4 `LoadScenarioManager`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-002` |
| Schicht | Fachlogik / Services |
| Aufgabe | Bereitstellung und Validierung der Lastszenarien. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `get_scenario(scenario_key)` | `str` | `LoadScenario` | Liefert das gewählte Lastszenario. |
| `is_valid_scenario(scenario_key)` | `str` | `bool` | Prüft, ob die Szenariokennung gültig ist. |
| `list_scenarios()` | keine | `list[LoadScenario]` | Gibt alle verfügbaren Lastszenarien zurück. |

---

### 6.5 `ClockCalculator`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-003` |
| Schicht | Fachlogik / Services |
| Aufgabe | Berechnung der simulierten CPU-Taktfrequenz. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `calculate_clock(mode, scenario, cpu_load_percent)` | `PerformanceMode`, `LoadScenario`, `float` | `float` | Berechnet die CPU-Taktfrequenz in GHz. |
| `clamp_clock(clock_ghz)` | `float` | `float` | Begrenzung auf minimale und maximale Taktwerte. |

---

### 6.6 `TemperatureSimulator`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-004` |
| Schicht | Fachlogik / Services |
| Aufgabe | Simulation der CPU-Temperatur. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `simulate_temperature(state, mode, cooling_level_key)` | `ProcessorState`, `PerformanceMode`, `str` | `float` | Simuliert die CPU-Temperatur in Grad Celsius unter Berücksichtigung der Kühlungsstufe. |
| `clamp_temperature(temperature_celsius)` | `float` | `float` | Begrenzung auf plausible Temperaturwerte. |

---

### 6.7 `ThermalThrottlingController`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-005` |
| Schicht | Fachlogik / Services |
| Aufgabe | Bewertung von Thermal Throttling. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `evaluate_throttling(state)` | `ProcessorState` | `tuple[bool, float]` | Bestimmt Throttling-Status und effektiven Takt. |
| `is_threshold_exceeded(temperature_celsius)` | `float` | `bool` | Prüft, ob der Temperaturgrenzwert überschritten ist. |

---

### 6.8 `PowerConsumptionCalculator`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-006` |
| Schicht | Fachlogik / Services |
| Aufgabe | Berechnung der Leistungsaufnahme. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `calculate_power(state, mode)` | `ProcessorState`, `PerformanceMode` | `float` | Berechnet die Leistungsaufnahme in Watt. |
| `clamp_power(power_watt)` | `float` | `float` | Begrenzung auf plausible Verbrauchswerte. |

---

### 6.9 `EventLogger`

| Merkmal | Beschreibung |
|---|---|
| Komponente | `CMP-007` |
| Schicht | Protokollierung / Nachweisführung |
| Aufgabe | Protokollierung der Verarbeitungsschritte. |

**Methoden:**

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `log(step, message, values)` | `str`, `str`, `dict` | `LogEntry` | Erstellt einen neuen Protokolleintrag. |
| `get_entries()` | keine | `list[LogEntry]` | Gibt alle Protokolleinträge zurück. |
| `clear()` | keine | `None` | Leert vorhandene Protokolleinträge vor einem neuen Simulationslauf. |

---

## 7. Iterative Erweiterung des Feindesigns

### 7.1 Iteration 1

In Iteration 1 werden folgende Klassen vollständig benötigt:

- `SimulationInput`
- `PerformanceMode`
- `LoadScenario`
- `ProcessorState`
- `SimulationResult`
- `UserInterface`
- `PerformanceController`
- `PerformanceModeManager`
- `LoadScenarioManager`
- `ClockCalculator`

Nicht aktive Attribute in Iteration 1:

- `temperature_celsius`
- `is_throttling`
- `effective_clock_ghz`
- `power_watt`
- `log_entries`

### 7.2 Iteration 2

In Iteration 2 werden ergänzt:

- `TemperatureSimulator`
- `ThermalThrottlingController`
- Erweiterung von `ProcessorState` um Temperatur und Throttling
- Erweiterung von `SimulationResult` um Warnungen

### 7.3 Iteration 3

In Iteration 3 werden ergänzt:

- `PowerConsumptionCalculator`
- `EventLogger`
- `LogEntry`
- Erweiterung von `ProcessorState` um Leistungsaufnahme
- Erweiterung von `SimulationResult` um Protokolleinträge

---

## 8. Zuordnung zu Komponenten

| Klasse | Hauptkomponente | Bemerkung |
|---|---|---|
| `SimulationInput` | `CMP-009`, `CMP-008` | Wird durch UI erzeugt und durch Controller verarbeitet. |
| `PerformanceMode` | `CMP-001` | Datenobjekt für Leistungsmodus. |
| `LoadScenario` | `CMP-002` | Datenobjekt für Lastszenario. |
| `ProcessorState` | `CMP-008`, Fachlogik | Zentrales Zustandsobjekt für Zwischen- und Endwerte. |
| `SimulationResult` | `CMP-008`, `CMP-009` | Ergebnisobjekt für Ausgabe und Tests. |
| `LogEntry` | `CMP-007` | Datenobjekt für Protokollierung. |
| `UserInterface` | `CMP-009` | Bedienung und Ausgabe. |
| `PerformanceController` | `CMP-008` | Ablaufsteuerung. |
| `PerformanceModeManager` | `CMP-001` | Leistungsmodusverwaltung. |
| `LoadScenarioManager` | `CMP-002` | Lastszenarioverwaltung. |
| `ClockCalculator` | `CMP-003` | Taktberechnung. |
| `TemperatureSimulator` | `CMP-004` | Temperatursimulation. |
| `ThermalThrottlingController` | `CMP-005` | Throttling-Bewertung. |
| `PowerConsumptionCalculator` | `CMP-006` | Leistungsaufnahmeberechnung. |
| `EventLogger` | `CMP-007` | Protokollierung. |

---

## 9. Auswirkungen auf Tests

| Klasse / Komponente | Testfokus |
|---|---|
| `SimulationInput` | Validierung gültiger und ungültiger Eingaben. |
| `PerformanceModeManager` | Gültige Modi, ungültige Modi, Modusfaktoren. |
| `LoadScenarioManager` | Gültige Szenarien, ungültige Szenarien, Lastfaktoren. |
| `ClockCalculator` | Taktberechnung, Grenzwerte, deterministische Ergebnisse. |
| `TemperatureSimulator` | Temperaturberechnung, plausible Temperaturgrenzen. |
| `ThermalThrottlingController` | Verhalten unter, auf und über Schwellenwert. |
| `PowerConsumptionCalculator` | Leistungsaufnahme und Plausibilitätsgrenzen. |
| `EventLogger` | Vollständigkeit und Reihenfolge der Logeinträge. |
| `PerformanceController` | Vollständiger Ablauf über mehrere Komponenten. |
| `UserInterface` | Eingabeannahme, Ergebnisanzeige, Fehlermeldungen. |

---

## 10. Auswirkungen auf Traceability

| Traceability-Spalte | Bezug im Feindesign |
|---|---|
| Requirement-ID | Wird später aus Requirements übernommen. |
| Teilfunktionalität | Entspricht TF-01 bis TF-07. |
| Komponente | Entspricht CMP-001 bis CMP-009. |
| Klasse | Wird in diesem Dokument definiert. |
| Code-Datei | Wird bei Implementierung ergänzt. |
| Testfall | Wird bei Testdokumentation ergänzt. |
| Iteration | Wird je Klasse und Komponente angegeben. |