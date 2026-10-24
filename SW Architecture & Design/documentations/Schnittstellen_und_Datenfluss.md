# Schnittstellen und Datenfluss

**Version:** 0.2  

---

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die Schnittstellen und den Datenfluss des **Laptop-Prozessorleistungsmoduls**.

Es konkretisiert die bisher festgelegte Architektur auf Verbindungsebene und beschreibt:

- welche Komponenten miteinander kommunizieren,
- welche Datenobjekte zwischen den Komponenten übergeben werden,
- welche Verantwortlichkeit jede Schnittstelle besitzt,
- wie der vollständige Simulationsablauf von der Eingabe bis zur Ergebnisanzeige abläuft,
- welche Schnittstellen in welcher Iteration benötigt werden,
- welche Konsequenzen sich für Implementierung, Tests und Traceability ergeben.

---

## 2. Einordnung in die bestehende Architektur

Die Architektur besteht aus folgenden Hauptbestandteilen:

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

Die Komponente `PerformanceController` übernimmt die zentrale Ablaufsteuerung. Die Benutzerschnittstelle ruft keine Fachkomponenten direkt auf. Alle fachlichen Berechnungen und Bewertungen werden durch spezialisierte Komponenten ausgeführt.

---

## 3. Kommunikationsprinzipien

| Regel-ID | Kommunikationsregel | Begründung |
|---|---|---|
| SR-001 | Die Kommunikation zwischen `UserInterface` und Fachlogik erfolgt ausschließlich über den `PerformanceController`. | Fachlogik bleibt von der Darstellung getrennt. |
| SR-002 | Fachkomponenten erhalten nur die Daten, die für ihre Aufgabe notwendig sind. | Geringe Kopplung und bessere Testbarkeit. |
| SR-003 | Fachkomponenten verändern keine Benutzereingaben direkt. | Eingaben bleiben nachvollziehbar und reproduzierbar. |
| SR-004 | Zwischenergebnisse werden in strukturierten Datenobjekten abgelegt. | Ergebnisse können getestet, protokolliert und weiterverarbeitet werden. |
| SR-005 | Der `EventLogger` darf Simulationsergebnisse nicht verändern. | Protokollierung bleibt eine Nebenfunktion ohne Einfluss auf die Fachlogik. |
| SR-006 | Der vollständige Systemablauf muss deterministisch sein. | Gleiche Eingaben führen zu gleichen Ergebnissen und testbaren Resultaten. |
| SR-007 | Schnittstellen werden so definiert, dass Requirements, Code und Tests zugeordnet werden können. | Traceability wird vorbereitet. |

---

## 4. Zentrale Datenobjekte

| Datenobjekt | Zweck | Erzeugt durch | Verwendet durch |
|---|---|---|---|
| `SimulationInput` | Bündelt Benutzereingaben für einen Simulationslauf. | `UserInterface` | `PerformanceController` |
| `PerformanceMode` | Beschreibt den ausgewählten Leistungsmodus und zugehörige Faktoren. | `PerformanceModeManager` | `ClockCalculator`, `TemperatureSimulator`, `PowerConsumptionCalculator`, `SimulationResult` |
| `LoadScenario` | Beschreibt das ausgewählte Lastszenario und den Lastfaktor. | `LoadScenarioManager` | `ClockCalculator`, `TemperatureSimulator`, `SimulationResult` |
| `ProcessorState` | Speichert den aktuellen Zustand des Prozessors während der Simulation. | `PerformanceController` und Fachkomponenten | `TemperatureSimulator`, `ThermalThrottlingController`, `PowerConsumptionCalculator`, `SimulationResult` |
| `SimulationResult` | Bündelt die finalen Ergebniswerte eines Simulationslaufs. | `PerformanceController` | `UserInterface`, `EventLogger`, Tests |
| `LogEntry` | Speichert einen protokollierten Verarbeitungsschritt. | `EventLogger` | Protokollausgabe, Tests |

---

## 5. Schnittstellenübersicht

| Schnittstellen-ID | Von | Nach | Zweck | Eingabedaten | Rückgabedaten | Iteration |
|---|---|---|---|---|---|---:|
| IF-001 | `UserInterface` | `PerformanceController` | Simulation starten | `SimulationInput` | `SimulationResult` | 1 |
| IF-002 | `PerformanceController` | `PerformanceModeManager` | Leistungsmodus validieren und abrufen | Moduskennung | `PerformanceMode` | 1 |
| IF-003 | `PerformanceController` | `LoadScenarioManager` | Lastszenario validieren und abrufen | Szenariokennung | `LoadScenario` | 1 |
| IF-004 | `PerformanceController` | `ClockCalculator` | CPU-Taktfrequenz berechnen | `PerformanceMode`, `LoadScenario`, CPU-Auslastung | Taktfrequenz in GHz | 1 |
| IF-005 | `PerformanceController` | `TemperatureSimulator` | CPU-Temperatur simulieren | `ProcessorState`, `PerformanceMode`, Kühlungsstufe | Temperatur in °C | 2 |
| IF-006 | `PerformanceController` | `ThermalThrottlingController` | Thermal Throttling bewerten | `ProcessorState`, Temperaturwert | Throttling-Stufe, effektive Taktfrequenz | 3 |
| IF-007 | `PerformanceController` | `PowerConsumptionCalculator` | Leistungsaufnahme berechnen | `ProcessorState`, `PerformanceMode` | Leistungsaufnahme in Watt | 2–3 |
| IF-008 | `PerformanceController` | `EventLogger` | Simulationsereignisse protokollieren | Eingaben, Zwischenergebnisse, Endergebnis | `LogEntry` oder Protokollstatus | 2–3 |
| IF-009 | `PerformanceController` | `UserInterface` | Ergebnis anzeigen lassen | `SimulationResult` | Darstellung im UI | 1–3 |

---

## 6. Schnittstellenbeschreibung im Detail

### 6.1 IF-001 – Simulation starten

| Feld | Beschreibung |
|---|---|
| Von | `UserInterface` |
| Nach | `PerformanceController` |
| Zweck | Startet einen vollständigen Simulationslauf. |
| Eingabe | `SimulationInput` mit Leistungsmodus, Lastszenario und CPU-Auslastung. |
| Ausgabe | `SimulationResult` mit den berechneten Ergebniswerten. |
| Vorbedingung | Die Eingaben wurden vom Benutzer erfasst. |
| Nachbedingung | Ein strukturiertes Simulationsergebnis liegt vor oder ein Fehlerzustand wurde zurückgegeben. |
| Fehlerfälle | Ungültiger Leistungsmodus, ungültiges Lastszenario, ungültige CPU-Auslastung. |

### 6.2 IF-002 – Leistungsmodus abrufen

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `PerformanceModeManager` |
| Zweck | Validiert die Modusauswahl und liefert ein strukturiertes Modusobjekt. |
| Eingabe | Moduskennung, z. B. `ENERGIESPAREN`, `AUSGEWOGEN`, `LEISTUNG`. |
| Ausgabe | `PerformanceMode`. |
| Vorbedingung | Eine Moduskennung ist im `SimulationInput` enthalten. |
| Nachbedingung | Ein gültiger Leistungsmodus liegt für weitere Berechnungen vor. |
| Fehlerfälle | Moduskennung ist leer, unbekannt oder nicht erlaubt. |

### 6.3 IF-003 – Lastszenario abrufen

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `LoadScenarioManager` |
| Zweck | Validiert die Szenarioauswahl und liefert ein strukturiertes Lastszenario. |
| Eingabe | Szenariokennung, z. B. `IDLE`, `OFFICE`, `STREAMING`, `GAMING`, `FULL_LOAD`. |
| Ausgabe | `LoadScenario`. |
| Vorbedingung | Eine Szenariokennung ist im `SimulationInput` enthalten. |
| Nachbedingung | Ein gültiges Lastszenario liegt für weitere Berechnungen vor. |
| Fehlerfälle | Szenariokennung ist leer, unbekannt oder nicht erlaubt. |

### 6.4 IF-004 – CPU-Takt berechnen

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `ClockCalculator` |
| Zweck | Berechnet die simulierte CPU-Taktfrequenz. |
| Eingabe | `PerformanceMode`, `LoadScenario`, CPU-Auslastung. |
| Ausgabe | CPU-Taktfrequenz in GHz. |
| Vorbedingung | Leistungsmodus und Lastszenario wurden erfolgreich bestimmt. |
| Nachbedingung | Der berechnete Takt wird im `ProcessorState` gespeichert. |
| Fehlerfälle | CPU-Auslastung liegt außerhalb des erlaubten Wertebereichs. |

### 6.5 IF-005 – Temperatur simulieren

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `TemperatureSimulator` |
| Zweck | Simuliert die CPU-Temperatur aus dem aktuellen Prozessorzustand. |
| Eingabe | `ProcessorState`, optional `PerformanceMode`. Kühlungsstufe (`Niedrig`, `Mittel`, `Hoch`) |
| Ausgabe | Temperaturwert in °C. |
| Vorbedingung | Ein `ProcessorState` mit CPU-Auslastung und CPU-Takt liegt vor. |
| Nachbedingung | Die simulierte Temperatur wird im `ProcessorState` gespeichert. |
| Fehlerfälle | Fehlende oder unplausible Takt- oder Auslastungswerte. |

### 6.6 IF-006 – Thermal Throttling bewerten

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `ThermalThrottlingController` |
| Zweck | Bewertet, ob aufgrund der Temperatur eine Leistungsreduzierung vorliegt. |
| Eingabe | `ProcessorState`, Temperaturwert, definierter Schwellenwert. |
| Ausgabe | Throttling-Status und ggf. reduzierter Takt. |
| Vorbedingung | Ein Temperaturwert liegt vor. |
| Nachbedingung | Der Throttling-Status wird im `ProcessorState` gespeichert. |
| Fehlerfälle | Temperaturwert fehlt oder ist außerhalb plausibler Grenzen. |

### 6.7 IF-007 – Leistungsaufnahme berechnen

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `PowerConsumptionCalculator` |
| Zweck | Berechnet die simulierte elektrische Leistungsaufnahme des Prozessors. |
| Eingabe | `ProcessorState`, `PerformanceMode`. |
| Ausgabe | Leistungsaufnahme in Watt. |
| Vorbedingung | CPU-Takt, Auslastung und Temperatur liegen vor. |
| Nachbedingung | Die Leistungsaufnahme wird im `ProcessorState` gespeichert. |
| Fehlerfälle | Fehlende Prozessorwerte oder unplausible Zwischenwerte. |

### 6.8 IF-008 – Ereignisse protokollieren

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `EventLogger` |
| Zweck | Protokolliert Eingaben, Zwischenergebnisse und Endergebnis. |
| Eingabe | `SimulationInput`, `ProcessorState`, `SimulationResult`, Ereignistyp. |
| Ausgabe | `LogEntry` oder Protokollstatus. |
| Vorbedingung | Ein protokollierbares Ereignis liegt vor. |
| Nachbedingung | Ein strukturierter Logeintrag wurde erzeugt. |
| Fehlerfälle | Logdaten fehlen oder können nicht serialisiert werden. |

### 6.9 IF-009 – Ergebnis anzeigen

| Feld | Beschreibung |
|---|---|
| Von | `PerformanceController` |
| Nach | `UserInterface` |
| Zweck | Gibt das Simulationsergebnis an die Benutzerschnittstelle zurück. |
| Eingabe | `SimulationResult`. |
| Ausgabe | Ergebnisanzeige für den Benutzer. |
| Vorbedingung | Der Simulationslauf wurde abgeschlossen oder mit einem Fehler beendet. |
| Nachbedingung | Der Benutzer kann Ergebniswerte oder Fehlermeldungen nachvollziehen. |
| Fehlerfälle | Ergebnisobjekt ist unvollständig. |

---

## 7. Iterativer Ausbau der Schnittstellen

### 7.1 Iteration 1

| Schnittstelle | Status in Iteration 1 |
|---|---|
| IF-001 | vollständig benötigt |
| IF-002 | vollständig benötigt |
| IF-003 | vollständig benötigt |
| IF-004 | vollständig benötigt |
| IF-005 | noch nicht aktiv |
| IF-006 | noch nicht aktiv |
| IF-007 | noch nicht aktiv |
| IF-008 | optional vorbereitet, noch nicht vollständig |
| IF-009 | vollständig benötigt |

**Ergebnis:** Der Benutzer kann Leistungsmodus, Lastszenario und CPU-Auslastung eingeben. Das System berechnet eine simulierte CPU-Taktfrequenz und gibt ein Ergebnis aus.

### 7.2 Iteration 2

| Schnittstelle | Status in Iteration 2 |
|---|---|
| IF-001 | unverändert |
| IF-002 | unverändert |
| IF-003 | unverändert |
| IF-004 | unverändert |
| IF-005 | neu aktiv |
| IF-006 | neu aktiv |
| IF-007 | noch nicht aktiv |
| IF-008 | optional erweitert |
| IF-009 | erweitert um Temperatur und Throttling |

**Ergebnis:** Das System zeigt zusätzlich Temperaturwert und Thermal-Throttling-Status an.

### 7.3 Iteration 3

| Schnittstelle | Status in Iteration 3 |
|---|---|
| IF-001 | erweitert um vollständige Eingaben |
| IF-002 | unverändert |
| IF-003 | unverändert |
| IF-004 | unverändert |
| IF-005 | unverändert |
| IF-006 | unverändert |
| IF-007 | neu aktiv |
| IF-008 | vollständig aktiv |
| IF-009 | erweitert um Leistungsaufnahme und Protokollausgabe |

**Ergebnis:** Das System liefert vollständige Ergebnisdaten einschließlich Leistungsaufnahme und Protokollierung.

---

## 8. Fehler- und Ausnahmefluss

Fehler werden zentral über den `PerformanceController` koordiniert und über die `UserInterface` ausgegeben.

| Fehlerfall | Erkennende Komponente | Reaktion |
|---|---|---|
| Ungültiger Leistungsmodus | `PerformanceModeManager` | Fehlerstatus an `PerformanceController` zurückgeben |
| Ungültiges Lastszenario | `LoadScenarioManager` | Fehlerstatus an `PerformanceController` zurückgeben |
| CPU-Auslastung außerhalb 0–100 % | `PerformanceController` oder `ClockCalculator` | Simulation abbrechen oder Eingabefehler zurückgeben |
| Fehlender Taktwert | `TemperatureSimulator` | Fehlerstatus zurückgeben |
| Temperatur außerhalb plausibler Grenzen | `ThermalThrottlingController` | Fehlerstatus oder Plausibilitätswarnung zurückgeben |
| Unvollständiges Ergebnisobjekt | `PerformanceController` | Ergebnis nicht freigeben, Fehlermeldung erzeugen |
| Fehler bei Protokollierung | `EventLogger` | Simulationsresultat nicht verändern, Warnung ergänzen |

---

## 9. Auswirkungen auf Tests

| Testart | Bezug zur Schnittstelle | Beispiel |
|---|---|---|
| Modultest | einzelne Fachkomponente | `ClockCalculator` berechnet erwarteten Takt für definierte Eingaben |
| Schnittstellentest | Übergabe zwischen zwei Komponenten | `PerformanceController` übergibt gültigen `ProcessorState` an `TemperatureSimulator` |
| Integrationstest | vollständiger Ablauf | Eingabe führt zu vollständigem `SimulationResult` |
| Fehlertest | ungültige Eingaben | unbekannter Leistungsmodus erzeugt definierte Fehlermeldung |
| Regressionstest | Verhalten bleibt über Iterationen stabil | Iteration-1-Taktberechnung bleibt nach Erweiterung in Iteration 2 unverändert |

---

## 10. Auswirkungen auf Traceability

| Schnittstelle | Requirement-Bezug | Komponentenbezug | Testbezug |
|---|---|---|---|
| IF-001 | REQ-F-002, REQ-F-007, REQ-NF-003 | `UserInterface`, `PerformanceController` | Integrationstest |
| IF-002 | REQ-F-001, REQ-F-002, REQ-F-003, REQ-F-004 | `PerformanceModeManager` | Modultest |
| IF-003 | REQ-F-005, REQ-F-006, REQ-F-007, REQ-F-008 | `LoadScenarioManager` | Modultest |
| IF-004 | REQ-F-009, REQ-F-010, REQ-F-011, REQ-F-012 | `ClockCalculator` | Modultest |
| IF-005 | REQ-F-013, REQ-F-014, REQ-F-015, REQ-F-016 | `TemperatureSimulator` | Modultest |
| IF-006 | REQ-F-024, REQ-F-025, REQ-F-026, REQ-F-027, REQ-F-028, REQ-F-029 | `ThermalThrottlingController` | Modultest |
| IF-007 | REQ-F-017, REQ-F-018, REQ-F-019 | `PowerConsumptionCalculator` | Modultest |
| IF-008 | REQ-F-020, REQ-F-021, REQ-F-022, REQ-F-023, REQ-F-030, REQ-F-031, REQ-F-032, REQ-F-033 | `EventLogger` | Modultest |
| IF-009 | REQ-F-008, REQ-F-011, REQ-F-016, REQ-F-018, REQ-F-028, REQ-F-032, REQ-NF-006 | `UserInterface`, `PerformanceController` | Integrationstest |
