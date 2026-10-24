# Iteration 1 – Software Architektur

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die Software-Architektur für Iteration 1 des Laptop-Prozessorleistungsmoduls.

Die Datei konkretisiert die bestehende Gesamtarchitektur für den ersten lauffähigen Entwicklungsstand. In Iteration 1 wird nur der fachliche Kern umgesetzt:

- Auswahl eines Leistungsmodus,
- Auswahl eines Lastszenarios,
- Zuordnung einer CPU-Auslastung,
- Berechnung einer ungedrosselten CPU-Taktfrequenz,
- Ausgabe des Simulationsergebnisses.

Nicht Bestandteil dieser Architekturversion sind Temperaturberechnung, Thermal Throttling, Stromverbrauch und vollständige Ereignisprotokollierung.

---

## 2. Einordnung in die Gesamtarchitektur

Die Gesamtarchitektur des Projekts ist als modulare Schichtenarchitektur aufgebaut.

Für Iteration 1 werden daraus folgende Schichten verwendet:

```text
Benutzerschnittstelle
        ↓
Anwendungssteuerung
        ↓
Fachlogik / Services
        ↓
Modelle und Datenobjekte
```

Die Schicht `Protokollierung / Nachweisführung` wird in Iteration 1 noch nicht aktiv umgesetzt.

---

## 3. Architekturziel der Iteration 1

Das Ziel der Architektur in Iteration 1 ist eine kleine, lauffähige und erweiterbare Grundstruktur.

Die Architektur muss ermöglichen, dass:

- der Nutzer Eingaben vornehmen kann,
- der Simulationsablauf zentral gesteuert wird,
- Leistungsmodus und Lastszenario getrennt verwaltet werden,
- die CPU-Taktfrequenz in einer eigenen Komponente berechnet wird,
- die Ergebnisdaten strukturiert zurückgegeben werden,
- spätere Komponenten in Iteration 2 und 3 ergänzt werden können.

---

## 4. Aktive Komponenten in Iteration 1

| Komponenten-ID | Komponente | Schicht | Aufgabe in Iteration 1 |
|---|---|---|---|
| CMP-009 | `UserInterface` | Benutzerschnittstelle | Nimmt Eingaben entgegen und zeigt Ergebnisse an. |
| CMP-008 | `PerformanceController` | Anwendungssteuerung | Koordiniert den Simulationsablauf. |
| CMP-001 | `PerformanceModeManager` | Fachlogik / Services | Stellt Leistungsmodi bereit und validiert die Auswahl. |
| CMP-002 | `LoadScenarioManager` | Fachlogik / Services | Stellt Lastszenarien bereit und validiert die Auswahl. |
| CMP-003 | `ClockCalculator` | Fachlogik / Services | Berechnet die ungedrosselte CPU-Taktfrequenz. |

---

## 5. Noch nicht aktive Komponenten

Die folgenden Komponenten sind Teil der Gesamtarchitektur, werden in Iteration 1 aber noch nicht aktiv verwendet:

| Komponenten-ID | Komponente | Geplante Iteration | Grund |
|---|---|---:|---|
| CMP-004 | `TemperatureSimulator` | 2 | Temperaturberechnung folgt nach der Taktberechnung. |
| CMP-005 | `ThermalThrottlingController` | 3 | Throttling benötigt vorher eine Temperaturberechnung. |
| CMP-006 | `PowerConsumptionCalculator` | 2 | Stromverbrauch wird erst nach Grundsimulation ergänzt. |
| CMP-007 | `EventLogger` | 2–3 | Ereignisprotokollierung wird später schrittweise ergänzt. |

---

## 6. Datenfluss in Iteration 1

Der Datenfluss in Iteration 1 ist linear aufgebaut.

1. `UserInterface` nimmt Leistungsmodus und Lastszenario entgegen.
2. `UserInterface` erstellt ein `SimulationInput`-Objekt.
3. `PerformanceController` erhält das `SimulationInput`-Objekt.
4. `PerformanceController` ruft den `PerformanceModeManager` auf.
5. `PerformanceModeManager` liefert ein `PerformanceMode`-Objekt.
6. `PerformanceController` ruft den `LoadScenarioManager` auf.
7. `LoadScenarioManager` liefert ein `LoadScenario`-Objekt.
8. `PerformanceController` erstellt einen `ProcessorState`.
9. `PerformanceController` ruft den `ClockCalculator` auf.
10. `ClockCalculator` berechnet die ungedrosselte CPU-Taktfrequenz.
11. `ProcessorState` wird mit der berechneten Taktfrequenz aktualisiert.
12. `PerformanceController` erstellt ein `SimulationResult`.
13. `UserInterface` zeigt das Ergebnis an.

---

## 7. Aktive Schnittstellen in Iteration 1

| Schnittstellen-ID | Von | Nach | Zweck | Status |
|---|---|---|---|---|
| IF-001 | `UserInterface` | `PerformanceController` | Simulation starten | aktiv |
| IF-002 | `PerformanceController` | `PerformanceModeManager` | Leistungsmodus abrufen | aktiv |
| IF-003 | `PerformanceController` | `LoadScenarioManager` | Lastszenario abrufen | aktiv |
| IF-004 | `PerformanceController` | `ClockCalculator` | CPU-Taktfrequenz berechnen | aktiv |
| IF-009 | `PerformanceController` | `UserInterface` | Ergebnis zurückgeben | aktiv |

Nicht aktiv in Iteration 1:

| Schnittstellen-ID | Grund |
|---|---|
| IF-005 | Temperaturberechnung wird erst später umgesetzt. |
| IF-006 | Thermal Throttling wird erst später umgesetzt. |
| IF-007 | Leistungsaufnahme wird erst später umgesetzt. |
| IF-008 | Ereignisprotokollierung wird erst später vollständig umgesetzt. |

---

## 8. Architekturregeln für Iteration 1

| Regel-ID | Regel |
|---|---|
| I1-AR-001 | `UserInterface` enthält keine Berechnungslogik. |
| I1-AR-002 | `PerformanceController` koordiniert den Ablauf, berechnet aber keine Taktfrequenz selbst. |
| I1-AR-003 | `PerformanceModeManager` ist ausschließlich für Leistungsmodi zuständig. |
| I1-AR-004 | `LoadScenarioManager` ist ausschließlich für Lastszenarien zuständig. |
| I1-AR-005 | `ClockCalculator` ist ausschließlich für die Taktberechnung zuständig. |
| I1-AR-006 | Ergebnisdaten werden über `SimulationResult` zurückgegeben. |
| I1-AR-007 | Zwischenergebnisse werden in `ProcessorState` gespeichert. |
| I1-AR-008 | Die Architektur muss ohne Umbau um Temperatur, Throttling, Stromverbrauch und Logging erweiterbar bleiben. |

---

## 9. Zuordnung zu Teilfunktionalitäten

| Teilfunktionalität | Architekturkomponente | Beschreibung |
|---|---|---|
| TF-01 | `PerformanceModeManager` | Bereitstellung und Auswahl des Leistungsmodus. |
| TF-02 | `LoadScenarioManager` | Bereitstellung und Auswahl des Lastszenarios. |
| TF-03 | `ClockCalculator` | Berechnung der ungedrosselten CPU-Taktfrequenz. |

---

## 10. Zuordnung zu Requirements

| Requirement-ID | Komponente | Architekturbezug |
|---|---|---|
| REQ-F-001 | `PerformanceModeManager` | Leistungsmodi bereitstellen. |
| REQ-F-002 | `PerformanceModeManager`, `PerformanceController` | Ausgewählten Leistungsmodus übernehmen. |
| REQ-F-003 | `PerformanceModeManager` | Benutzerdefinierten Leistungsmodus annehmen. |
| REQ-F-004 | `PerformanceModeManager` | Ungültige benutzerdefinierte Werte zurückweisen. |
| REQ-F-005 | `LoadScenarioManager` | Lastszenarien bereitstellen. |
| REQ-F-006 | `LoadScenarioManager` | CPU-Auslastung zuordnen. |
| REQ-F-007 | `LoadScenarioManager`, `PerformanceController` | Ausgewähltes Lastszenario übernehmen. |
| REQ-F-008 | `UserInterface`, `SimulationResult` | CPU-Auslastung ausgeben. |
| REQ-F-009 | `ClockCalculator` | CPU-Taktfrequenz bestimmen. |
| REQ-F-010 | `ClockCalculator` | CPU-Taktfrequenz begrenzen. |
| REQ-F-011 | `UserInterface`, `SimulationResult` | CPU-Taktfrequenz mit Einheit ausgeben. |
| REQ-F-012 | `PerformanceController`, `ClockCalculator` | Taktfrequenz bei Änderung neu bestimmen. |
| REQ-NF-001 | alle Berechnungskomponenten | Deterministisches Verhalten. |
| REQ-NF-002 | alle Komponenten | Testbarkeit durch klare Trennung. |
| REQ-NF-003 | `UserInterface`, `PerformanceController`, Manager-Klassen | Kontrollierter Umgang mit ungültigen Eingaben. |
| REQ-NF-004 | gesamte Iteration | Baseline am Ende der Iteration. |

---

## 11. Erwartetes Ergebnis der Architektur in Iteration 1

Nach Umsetzung dieser Architektur ist die Software in der Lage, einen vollständigen Simulationslauf für den reduzierten Iteration-1-Umfang auszuführen.

Das erwartete Ergebnis enthält:

- gewählten Leistungsmodus,
- gewähltes Lastszenario,
- zugeordnete CPU-Auslastung in Prozent,
- berechnete ungedrosselte CPU-Taktfrequenz in GHz.

---

## 12. Abgrenzung zur nächsten Iteration

Iteration 2 erweitert diese Architektur um zusätzliche Verarbeitungsschritte.

Geplante Erweiterungen nach Iteration 1:

- Ergänzung von `TemperatureSimulator`,
- Erweiterung von `ProcessorState` um `temperature_celsius`,
- Erweiterung von `SimulationResult` um Temperaturausgabe,
- spätere Vorbereitung von Stromverbrauch und Ereignisprotokollierung.

Die bestehenden Komponenten aus Iteration 1 bleiben erhalten und werden nicht durch fremde Verantwortlichkeiten erweitert.
