# Iteration 1 – Feindesign

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt das Feindesign für Iteration 1 des Laptop-Prozessorleistungsmoduls.

Das Feindesign konkretisiert die Software-Architektur der Iteration 1 auf Klassen-, Methoden- und Datenmodellebene. Es legt fest:

- welche Klassen in Iteration 1 benötigt werden,
- welche Attribute die Datenklassen besitzen,
- welche Methoden die Steuer- und Serviceklassen bereitstellen,
- welche Wertebereiche und Zustände in der Implementierung verwendet werden,
- welche Code-Dateien aus den Klassen abgeleitet werden,
- welche Requirements durch welche Klassen umgesetzt werden.

Iteration 1 beschränkt sich auf die Grundsimulation mit Leistungsmodus, Lastszenario, CPU-Auslastung und ungedrosselter CPU-Taktfrequenz.

Nicht Bestandteil dieses Feindesigns sind Temperaturberechnung, Thermal Throttling, Stromverbrauch und vollständige Ereignisprotokollierung.

---

## 2. Grundlage aus der Iteration-1-Architektur

Die Iteration-1-Architektur enthält folgende aktive Komponenten:

| Komponenten-ID | Komponente | Aufgabe |
|---|---|---|
| CMP-009 | `UserInterface` | Eingaben erfassen und Ergebnisse anzeigen |
| CMP-008 | `PerformanceController` | Simulationsablauf koordinieren |
| CMP-001 | `PerformanceModeManager` | Leistungsmodi bereitstellen und validieren |
| CMP-002 | `LoadScenarioManager` | Lastszenarien bereitstellen und validieren |
| CMP-003 | `ClockCalculator` | Ungedrosselte CPU-Taktfrequenz berechnen |

Daraus werden in diesem Feindesign konkrete Klassen, Attribute und Methoden abgeleitet.

---

## 3. Designziele für Iteration 1

| Designziel-ID | Designziel | Beschreibung |
|---|---|---|
| I1-DZ-001 | Verständlichkeit | Jede Klasse besitzt eine klar abgegrenzte Aufgabe. |
| I1-DZ-002 | Testbarkeit | Fachlogik wird in separaten Serviceklassen gekapselt. |
| I1-DZ-003 | Determinismus | Gleiche Eingaben erzeugen gleiche Ausgaben. |
| I1-DZ-004 | Erweiterbarkeit | Klassen können in Iteration 2 und 3 ergänzt werden. |
| I1-DZ-005 | Traceability | Jede Klasse ist Requirements und Komponenten zuordenbar. |
| I1-DZ-006 | Robustheit | Ungültige Eingaben werden kontrolliert behandelt. |

---

## 4. Klassenübersicht

| Klasse | Typ | Komponente | Zweck |
|---|---|---|---|
| `SimulationInput` | Datenklasse | CMP-009, CMP-008 | Bündelt Eingaben für einen Simulationslauf. |
| `PerformanceMode` | Datenklasse | CMP-001 | Beschreibt einen Leistungsmodus. |
| `LoadScenario` | Datenklasse | CMP-002 | Beschreibt ein Lastszenario mit CPU-Auslastung. |
| `ProcessorState` | Datenklasse | CMP-008, CMP-003 | Speichert CPU-Auslastung und berechnete Taktfrequenz. |
| `SimulationResult` | Datenklasse | CMP-008, CMP-009 | Bündelt die finalen Ergebniswerte. |
| `UserInterface` | Steuer-/UI-Klasse | CMP-009 | Nimmt Eingaben entgegen und zeigt Ergebnisse an. |
| `PerformanceController` | Steuerklasse | CMP-008 | Koordiniert den Ablauf der Grundsimulation. |
| `PerformanceModeManager` | Serviceklasse | CMP-001 | Verwaltet und validiert Leistungsmodi. |
| `LoadScenarioManager` | Serviceklasse | CMP-002 | Verwaltet und validiert Lastszenarien. |
| `ClockCalculator` | Serviceklasse | CMP-003 | Berechnet die ungedrosselte CPU-Taktfrequenz. |

---

## 5. Datenklassen im Detail

### 5.1 `SimulationInput`

**Zweck:**  
`SimulationInput` enthält die vom Nutzer gewählten Eingabedaten für einen Simulationslauf.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `mode_key` | `str` | ja | Kennung des ausgewählten Leistungsmodus. |
| `scenario_key` | `str` | ja | Kennung des ausgewählten Lastszenarios. |
| `custom_max_clock_ghz` | `float \| None` | nur bei benutzerdefiniertem Modus | Maximale CPU-Taktfrequenz des benutzerdefinierten Modus. |
| `custom_power_limit_watt` | `float \| None` | nur bei benutzerdefiniertem Modus | Stromverbrauchsgrenzwert des benutzerdefinierten Modus. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `is_custom_mode()` | `bool` | Gibt `True` zurück, wenn `mode_key` den benutzerdefinierten Modus beschreibt. |
| `has_required_values()` | `bool` | Prüft, ob `mode_key` und `scenario_key` vorhanden sind. |

---

### 5.2 `PerformanceMode`

**Zweck:**  
`PerformanceMode` beschreibt einen auswählbaren Leistungsmodus und dessen relevante Grenzwerte.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `key` | `str` | ja | Eindeutige Kennung des Leistungsmodus. |
| `name` | `str` | ja | Anzeigename des Leistungsmodus. |
| `is_custom` | `bool` | ja | Gibt an, ob der Modus benutzerdefiniert ist. |
| `max_clock_ghz` | `float` | ja | Maximale ungedrosselte CPU-Taktfrequenz des Modus. |
| `power_limit_watt` | `float` | ja | Stromverbrauchsgrenzwert des Modus. |

**Zulässige feste Modi:**

| key | name | max_clock_ghz | power_limit_watt |
|---|---|---:|---:|
| `ENERGY_SAVE` | Energiesparen | 2.0 | 20 |
| `BALANCED` | Ausgeglichen | 3.2 | 40 |
| `PERFORMANCE` | Höchstleistung | 4.2 | 65 |
| `CUSTOM` | Benutzerdefiniert | nutzerdefiniert | nutzerdefiniert |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `get_display_name()` | `str` | Gibt den Anzeigenamen zurück. |
| `get_max_clock_ghz()` | `float` | Gibt die maximale CPU-Taktfrequenz zurück. |

---

### 5.3 `LoadScenario`

**Zweck:**  
`LoadScenario` beschreibt ein vordefiniertes Nutzungsszenario und die zugehörige CPU-Auslastung.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `key` | `str` | ja | Eindeutige Kennung des Lastszenarios. |
| `name` | `str` | ja | Anzeigename des Lastszenarios. |
| `cpu_load_percent` | `float` | ja | Zugeordnete CPU-Auslastung in Prozent. |

**Zulässige Lastszenarien:**

| key | name | cpu_load_percent |
|---|---|---:|
| `IDLE` | Leerlauf | 15 |
| `OFFICE` | Office | 35 |
| `MULTIMEDIA` | Multimedia | 55 |
| `GAMING` | Gaming | 85 |
| `STRESS_TEST` | Stresstest | 100 |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `get_cpu_load_percent()` | `float` | Gibt die CPU-Auslastung des Szenarios zurück. |
| `get_display_name()` | `str` | Gibt den Anzeigenamen zurück. |

---

### 5.4 `ProcessorState`

**Zweck:**  
`ProcessorState` speichert den aktuellen Prozessorzustand innerhalb eines Simulationslaufs.

| Attribut | Typ | Pflicht in Iteration 1 | Beschreibung |
|---|---|---|---|
| `cpu_load_percent` | `float` | ja | CPU-Auslastung in Prozent. |
| `clock_ghz` | `float \| None` | ja, nach Taktberechnung | Ungedrosselte CPU-Taktfrequenz in GHz. |

**Für spätere Iterationen vorbereitet, aber in Iteration 1 nicht aktiv:**

| Attribut | Geplante Iteration | Beschreibung |
|---|---:|---|
| `temperature_celsius` | 2 | Simulierte CPU-Temperatur. |
| `throttling_level` | 3 | Aktuelle Throttling-Stufe. |
| `effective_clock_ghz` | 3 | Effektive CPU-Taktfrequenz nach Throttling. |
| `power_watt` | 2 | Simulierter Stromverbrauch. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `update_clock(clock_ghz)` | `None` | Speichert die berechnete ungedrosselte Taktfrequenz. |
| `has_clock()` | `bool` | Prüft, ob bereits eine Taktfrequenz gespeichert wurde. |

---

### 5.5 `SimulationResult`

**Zweck:**  
`SimulationResult` bündelt die Ergebniswerte, die dem Nutzer angezeigt werden.

| Attribut | Typ | Pflicht | Beschreibung |
|---|---|---|---|
| `performance_mode` | `PerformanceMode` | ja | Ausgewählter Leistungsmodus. |
| `load_scenario` | `LoadScenario` | ja | Ausgewähltes Lastszenario. |
| `processor_state` | `ProcessorState` | ja | Finaler Prozessorzustand. |
| `summary` | `str` | ja | Kurze textuelle Zusammenfassung des Ergebnisses. |

**Methoden:**

| Methode | Rückgabe | Beschreibung |
|---|---|---|
| `to_display_text()` | `str` | Erstellt eine lesbare Ergebnisdarstellung. |

**Erwartete Ausgabe in Iteration 1:**

```text
Leistungsmodus: Ausgeglichen
Lastszenario: Office
CPU-Auslastung: 35 %
Ungedrosselte CPU-Taktfrequenz: 2.0 GHz
```

---

## 6. Service- und Steuerklassen im Detail

### 6.1 `UserInterface`

**Komponente:** CMP-009  
**Schicht:** Benutzerschnittstelle

**Verantwortung:**  
Die Klasse `UserInterface` nimmt Benutzereingaben entgegen und zeigt das Ergebnis an. Sie enthält keine Berechnungslogik.

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `read_input()` | keine | `SimulationInput` | Liest Leistungsmodus und Lastszenario ein. |
| `show_result(result)` | `SimulationResult` | `None` | Gibt das Simulationsergebnis aus. |
| `show_error(message)` | `str` | `None` | Gibt eine Fehlermeldung aus. |

**Nicht erlaubt in dieser Klasse:**

- Taktberechnung,
- Validierung von Fachwerten außerhalb einfacher Eingabeprüfung,
- Verwaltung von Leistungsmodi oder Lastszenarien.

---

### 6.2 `PerformanceController`

**Komponente:** CMP-008  
**Schicht:** Anwendungssteuerung

**Verantwortung:**  
Der `PerformanceController` koordiniert den Ablauf der Simulation. Er ruft die Fachkomponenten auf, speichert Zwischenergebnisse im `ProcessorState` und erzeugt das `SimulationResult`.

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `run_simulation(input_data)` | `SimulationInput` | `SimulationResult` | Führt den vollständigen Simulationslauf aus. |
| `create_initial_state(scenario)` | `LoadScenario` | `ProcessorState` | Erstellt den initialen Prozessorzustand mit CPU-Auslastung. |
| `create_result(performanceMode, loadScenario, processorState)` | `PerformanceMode`, `LoadScenario`, `ProcessorState` | `SimulationResult` | Erstellt das finale Ergebnisobjekt. |

**Ablauf in `run_simulation(input_data)`:**

1. Eingabe auf Pflichtwerte prüfen.
2. Leistungsmodus über `PerformanceModeManager` abrufen.
3. Lastszenario über `LoadScenarioManager` abrufen.
4. `ProcessorState` mit der CPU-Auslastung des Lastszenarios erzeugen.
5. CPU-Taktfrequenz über `ClockCalculator` berechnen.
6. `ProcessorState` mit Taktfrequenz aktualisieren.
7. `SimulationResult` erzeugen.
8. Ergebnis zurückgeben.

---

### 6.3 `PerformanceModeManager`

**Komponente:** CMP-001  
**Schicht:** Fachlogik / Services

**Verantwortung:**  
Der `PerformanceModeManager` stellt die zulässigen Leistungsmodi bereit und prüft die Modusauswahl.

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `list_modes()` | keine | `list[PerformanceMode]` | Gibt alle verfügbaren Leistungsmodi zurück. |
| `is_valid_mode(mode_key)` | `str` | `bool` | Prüft, ob eine Moduskennung zulässig ist. |
| `get_mode(input_data)` | `SimulationInput` | `PerformanceMode` | Gibt den gewählten Leistungsmodus zurück. |
| `validate_custom_values(input_data)` | `SimulationInput` | `bool` | Prüft die Werte des benutzerdefinierten Modus. |

**Validierungsregeln für benutzerdefinierten Modus:**

| Parameter | Minimalwert | Maximalwert | Einheit |
|---|---:|---:|---|
| `custom_max_clock_ghz` | 1.2 | 4.2 | GHz |
| `custom_power_limit_watt` | 8 | 65 | W |

Wenn ein Wert außerhalb des Bereichs liegt, wird der benutzerdefinierte Modus zurückgewiesen.

---

### 6.4 `LoadScenarioManager`

**Komponente:** CMP-002  
**Schicht:** Fachlogik / Services

**Verantwortung:**  
Der `LoadScenarioManager` stellt die zulässigen Lastszenarien bereit und liefert die zugehörige CPU-Auslastung.

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `list_scenarios()` | keine | `list[LoadScenario]` | Gibt alle verfügbaren Lastszenarien zurück. |
| `is_valid_scenario(scenario_key)` | `str` | `bool` | Prüft, ob eine Szenariokennung zulässig ist. |
| `get_scenario(scenario_key)` | `str` | `LoadScenario` | Gibt das gewählte Lastszenario zurück. |

---

### 6.5 `ClockCalculator`

**Komponente:** CMP-003  
**Schicht:** Fachlogik / Services

**Verantwortung:**  
Der `ClockCalculator` berechnet die ungedrosselte CPU-Taktfrequenz anhand des Leistungsmodus und des Lastszenarios.

| Methode | Eingabe | Rückgabe | Beschreibung |
|---|---|---|---|
| `calculate_clock(mode, scenario)` | `PerformanceMode`, `LoadScenario` | `float` | Berechnet die CPU-Taktfrequenz in GHz. |
| `calculate_fixed_mode_clock(mode, scenario)` | `PerformanceMode`, `LoadScenario` | `float` | Ermittelt die Taktfrequenz aus der Taktfrequenztabelle. |
| `calculate_custom_mode_clock(mode, scenario)` | `PerformanceMode`, `LoadScenario` | `float` | Berechnet die Taktfrequenz für den benutzerdefinierten Modus. |
| `clamp_clock(clock_ghz)` | `float` | `float` | Begrenzt die Taktfrequenz auf 1.2 GHz bis 4.2 GHz. |

---

## 7. Taktberechnung

### 7.1 Taktfrequenzen für feste Leistungsmodi

| Leistungsmodus | Leerlauf | Office | Multimedia | Gaming | Stresstest |
|---|---:|---:|---:|---:|---:|
| Energiesparen | 1.2 GHz | 1.4 GHz | 1.6 GHz | 1.8 GHz | 2.0 GHz |
| Ausgeglichen | 1.4 GHz | 2.0 GHz | 2.5 GHz | 3.0 GHz | 3.2 GHz |
| Höchstleistung | 2.0 GHz | 2.8 GHz | 3.4 GHz | 4.0 GHz | 4.2 GHz |

### 7.2 Taktberechnung für benutzerdefinierten Modus

Für den benutzerdefinierten Modus gilt folgende Berechnungsregel:

```text
raw_clock_ghz = custom_max_clock_ghz * (cpu_load_percent / 100)
clock_ghz = clamp(raw_clock_ghz, 1.2, custom_max_clock_ghz)
```

Danach wird zusätzlich die globale Taktgrenze angewendet:

```text
clock_ghz = clamp(clock_ghz, 1.2, 4.2)
```

### 7.3 Globale Begrenzung

Jede berechnete CPU-Taktfrequenz wird auf folgenden Bereich begrenzt:

| Minimalwert | Maximalwert | Einheit |
|---:|---:|---|
| 1.2 | 4.2 | GHz |

---

## 8. Fehler- und Ausnahmebehandlung

| Fehlerfall | Erkennende Klasse | Reaktion |
|---|---|---|
| Kein Leistungsmodus angegeben | `PerformanceController` | Fehler melden, Simulation nicht fortsetzen. |
| Ungültiger Leistungsmodus | `PerformanceModeManager` | Fehler melden, Simulation nicht fortsetzen. |
| Benutzerdefinierter Taktwert außerhalb 1.2 bis 4.2 GHz | `PerformanceModeManager` | Wert zurückweisen. |
| Benutzerdefinierter Stromverbrauchsgrenzwert außerhalb 8 bis 65 W | `PerformanceModeManager` | Wert zurückweisen. |
| Kein Lastszenario angegeben | `PerformanceController` | Fehler melden, Simulation nicht fortsetzen. |
| Ungültiges Lastszenario | `LoadScenarioManager` | Fehler melden, Simulation nicht fortsetzen. |
| Berechneter Takt außerhalb gültiger Grenzen | `ClockCalculator` | Wert mit `clamp_clock()` begrenzen. |

---

## 9. Geplante Code-Dateien

Die konkrete Implementierung kann in Java umgesetzt werden. Für Iteration 1 wird folgende Dateistruktur genutzt:

```text
Projekt/
└── Iteration 1/
    └── src/
        ├── Main.java
            └── controller
                └── PerformanceController.java
            └── model
                └── LoadScenario.java
                └── PerformanceMode.java
                └── ProcessorState.java
                └── SimulationInput.java
                └── SimulationResult.java
            └── service
                └── ClockCalculator.java
                └── LoadScenarioManager.java
                └── PerformanceModeManager.java
            └── ui
                └── UserInterface.java
```

| Datei / Ordner | Enthaltene Klassen / Funktionen |
|---|---|
| `src/Main.java` | Startpunkt der Anwendung mit `main()`-Methode |
| `src/model/SimulationInput.java` | `SimulationInput` |
| `src/model/PerformanceMode.java` | `PerformanceMode` |
| `src/model/LoadScenario.java` | `LoadScenario` |
| `src/model/ProcessorState.java` | `ProcessorState` |
| `src/model/SimulationResult.java` | `SimulationResult` |
| `src/ui/UserInterface.java` | `UserInterface` |
| `src/controller/PerformanceController.java` | `PerformanceController` |
| `src/service/PerformanceModeManager.java` | `PerformanceModeManager` |
| `src/service/LoadScenarioManager.java` | `LoadScenarioManager` |
| `src/service/ClockCalculator.java` | `ClockCalculator` |

---

## 10. Ablauf aus Feindesign-Sicht

```text
1. UserInterface.read_input()
2. PerformanceController.run_simulation(input_data)
3. PerformanceModeManager.get_mode(input_data)
4. LoadScenarioManager.get_scenario(input_data.scenario_key)
5. PerformanceController.create_initial_state(scenario)
6. ClockCalculator.calculate_clock(mode, scenario)
7. ProcessorState.update_clock(clock_ghz)
8. PerformanceController.create_result(mode, scenario, state)
9. UserInterface.show_result(result)
```

---

## 11. Requirement-Zuordnung

| Requirement-ID | Klasse(n) | Begründung |
|---|---|---|
| REQ-F-001 | `PerformanceModeManager`, `PerformanceMode` | Leistungsmodi werden bereitgestellt. |
| REQ-F-002 | `PerformanceController`, `PerformanceModeManager` | Ausgewählter Modus wird übernommen. |
| REQ-F-003 | `SimulationInput`, `PerformanceModeManager`, `PerformanceMode` | Benutzerdefinierte Werte werden angenommen. |
| REQ-F-004 | `PerformanceModeManager` | Ungültige benutzerdefinierte Werte werden zurückgewiesen. |
| REQ-F-005 | `LoadScenarioManager`, `LoadScenario` | Lastszenarien werden bereitgestellt. |
| REQ-F-006 | `LoadScenarioManager`, `LoadScenario` | CPU-Auslastung wird dem Szenario zugeordnet. |
| REQ-F-007 | `PerformanceController`, `LoadScenarioManager` | Ausgewähltes Szenario wird übernommen. |
| REQ-F-008 | `SimulationResult`, `UserInterface` | CPU-Auslastung wird ausgegeben. |
| REQ-F-009 | `ClockCalculator` | Taktfrequenz wird bestimmt. |
| REQ-F-010 | `ClockCalculator` | Taktfrequenz wird begrenzt. |
| REQ-F-011 | `SimulationResult`, `UserInterface` | Taktfrequenz wird mit Einheit ausgegeben. |
| REQ-F-012 | `PerformanceController`, `ClockCalculator` | Bei geänderter Eingabe wird neu berechnet. |
| REQ-NF-001 | `ClockCalculator`, `PerformanceController` | Deterministische Berechnung. |
| REQ-NF-002 | alle Serviceklassen | Getrennte Klassen ermöglichen Testfälle. |
| REQ-NF-003 | `UserInterface`, `PerformanceController`, Manager-Klassen | Ungültige Eingaben werden kontrolliert behandelt. |
| REQ-NF-004 | Projektstruktur | Baseline wird am Ende der Iteration erzeugt. |

---

## 12. Testbezug

Aus dem Feindesign ergeben sich folgende Testschwerpunkte:

| Testbereich | Klasse | Beispiel |
|---|---|---|
| Leistungsmodi | `PerformanceModeManager` | Alle festen Modi werden bereitgestellt. |
| Benutzerdefinierter Modus | `PerformanceModeManager` | Grenzwerte 1.2 GHz, 4.2 GHz, 8 W und 65 W werden akzeptiert. |
| Ungültiger Modus | `PerformanceModeManager` | Unbekannte Moduskennung wird zurückgewiesen. |
| Lastszenarien | `LoadScenarioManager` | Szenarien liefern korrekte CPU-Auslastung. |
| Taktberechnung | `ClockCalculator` | Kombination aus Modus und Szenario liefert erwarteten Takt. |
| Taktgrenzen | `ClockCalculator` | Takt wird auf 1.2 GHz bis 4.2 GHz begrenzt. |
| Ablauf | `PerformanceController` | Vollständiger Simulationslauf erzeugt `SimulationResult`. |
| Ausgabe | `UserInterface`, `SimulationResult` | Ergebnis enthält Wert und Einheit. |

---

## 13. Abgrenzung zu späteren Iterationen

In Iteration 1 werden folgende Klassen bewusst noch nicht implementiert:

| Klasse | Geplante Iteration | Grund |
|---|---:|---|
| `TemperatureSimulator` | 2 | Temperaturberechnung benötigt zuerst die Taktberechnung. |
| `ThermalThrottlingController` | 3 | Throttling benötigt Temperaturwerte. |
| `PowerConsumptionCalculator` | 2 | Stromverbrauch wird nach der Grundsimulation ergänzt. |
| `EventLogger` | 2–3 | Ereignisprotokollierung wird später schrittweise ergänzt. |
| `LogEntry` | 2–3 | Wird erst mit Ereignisprotokollierung benötigt. |
