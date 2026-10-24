# Laptop-Prozessorleistung – Software Engineering Projekt

## 1. Projektüberblick

Dieses Projekt wurde im Rahmen der Veranstaltung **Software Engineering** erstellt.

Ziel des Projekts ist die Entwicklung eines einfachen Simulationsprogramms zur Bewertung der **Prozessorleistung eines Laptops**. Die Software simuliert, wie sich Leistungsmodus, Lastszenario, Kühlung, Temperatur, Thermal Throttling und Stromverbrauch gegenseitig beeinflussen.

Das Projekt wurde iterativ umgesetzt. Nach jeder Iteration liegt ein lauffähiger Softwarestand mit Dokumentation, Tests und Traceability vor.

---

## 2. Projektthema

| Bereich | Inhalt |
|---|---|
| Studiengang | Künstliche Intelligenz |
| Veranstaltung | Software Engineering |
| Projektthema | Laptop – Prozessorleistung |
| Teilbereich | Simulation von CPU-Leistung, Temperatur, Throttling und Stromverbrauch |
| Programmiersprache | Java |
| Entwicklungsumgebung | IntelliJ IDEA |
| Build / Test | `javac`, eigener Java-TestRunner |
| Externe Bibliotheken | keine |

---

## 3. Ziel der Software

Die Software soll vereinfacht simulieren, wie sich ein Laptop-Prozessor unter verschiedenen Bedingungen verhält.

Berücksichtigt werden:

- gewählter Leistungsmodus,
- gewähltes Lastszenario,
- benutzerdefinierter Modus,
- CPU-Taktfrequenz,
- Kühlungsstufe,
- CPU-Temperatur,
- Thermal Throttling,
- effektive CPU-Taktfrequenz nach Throttling,
- Stromverbrauch,
- Ereignisprotokollierung.

---

## 4. Funktionsumfang

### 4.1 Leistungsmodus

Die Software unterstützt verschiedene Leistungsmodi:

| Modus | Zweck |
|---|---|
| `ENERGY_SAVE` | energiesparender Betrieb |
| `BALANCED` | ausgeglichener Betrieb |
| `PERFORMANCE` | maximale Leistung |
| `CUSTOM` | benutzerdefinierter Modus mit eigenem Takt- und Power-Limit |

---

### 4.2 Lastszenarien

Die Software unterstützt unterschiedliche Lastszenarien:

| Szenario | CPU-Auslastung |
|---|---:|
| `IDLE` | 15 % |
| `OFFICE` | 35 % |
| `MULTIMEDIA` | 55 % |
| `GAMING` | 85 % |
| `STRESS_TEST` | 100 % |

---

### 4.3 Kühlungsstufen

Ab Iteration 2 werden Kühlungsstufen berücksichtigt:

| Kühlungsstufe | Bedeutung |
|---|---|
| `NIEDRIG` | schwache Kühlung |
| `MITTEL` | normale Kühlung |
| `HOCH` | starke Kühlung |

---

### 4.4 Thermal Throttling

Ab Iteration 3 wird Thermal Throttling aktiv simuliert.

| Temperaturbereich | Throttling-Stufe | Effektiver Takt |
|---|---|---:|
| 35 °C bis 69 °C | `NORMAL` | 100 % |
| 70 °C bis 79 °C | `WARNUNG` | 100 % |
| 80 °C bis 89 °C | `REDUZIERT` | 80 % |
| 90 °C bis 100 °C | `KRITISCH` | 60 % |

---

## 5. Iterationsübersicht

### 5.1 Iteration 1

Schwerpunkt der ersten Iteration war der grundlegende Simulationsablauf.

Umgesetzt wurden:

- Auswahl des Leistungsmodus,
- benutzerdefinierter Leistungsmodus,
- Auswahl des Lastszenarios,
- Berechnung der CPU-Taktfrequenz,
- Ausgabe eines ersten Simulationsergebnisses,
- erste Unit-Tests,
- erste Traceability-Zuordnung.

Wichtige Klassen:

| Klasse | Zweck |
|---|---|
| `PerformanceModeManager` | Verwaltung der Leistungsmodi |
| `LoadScenarioManager` | Verwaltung der Lastszenarien |
| `ClockCalculator` | Berechnung der CPU-Taktfrequenz |
| `PerformanceController` | Koordination des Simulationsablaufs |
| `SimulationInput` | Eingabedaten |
| `ProcessorState` | Prozessorzustand |
| `SimulationResult` | Simulationsergebnis |

---

### 5.2 Iteration 2

Schwerpunkt der zweiten Iteration war die Erweiterung um Temperatur, Kühlung, Stromverbrauch und Ereignisprotokollierung.

Umgesetzt wurden:

- Kühlungsstufen,
- Temperaturberechnung,
- Stromverbrauchsberechnung,
- einfache Ereignisprotokollierung,
- Erweiterung des Simulationsergebnisses,
- automatisierte Tests,
- Testreporting,

Wichtige Klassen:

| Klasse | Zweck |
|---|---|
| `CoolingLevel` | Modelliert die Kühlungsstufe |
| `TemperatureSimulator` | Berechnet die CPU-Temperatur |
| `PowerConsumptionCalculator` | Berechnet den Stromverbrauch |
| `EventLogger` | Erfasst Ereignisse |
| `LogEntry` | Modelliert einen Logeintrag |

Testergebnis Iteration 2:

```text
passed=21
failed=0
```

---

### 5.3 Iteration 3

Schwerpunkt der dritten Iteration war die vollständige Umsetzung von Thermal Throttling und die Finalisierung des Projekts.

Umgesetzt wurden:

- Thermal Throttling,
- Throttling-Stufen,
- effektive CPU-Taktfrequenz,
- Stromverbrauch nach Throttling,
- erweiterte Ereignisprotokollierung,
- Reset-Ereignis,
- abstrakte Oberklasse `SimulationOption`,
- Interface `Displayable`,
- Regressionstests für Iteration 1 und 2,

Wichtige Klassen:

| Klasse | Zweck |
|---|---|
| `ThermalThrottlingController` | Bestimmt Throttling-Stufe und effektiven Takt |
| `ThrottlingState` | Enum für `NORMAL`, `WARNUNG`, `REDUZIERT`, `KRITISCH` |
| `SimulationOption` | Abstrakte Oberklasse für auswählbare Optionen |
| `Displayable` | Interface für formatierte Textausgaben |
| `ProcessorState` | Speichert Takt, Temperatur, Throttling und Stromverbrauch |
| `SimulationResult` | Gibt vollständiges Ergebnis aus |
| `EventLogger` | Protokolliert Temperatur-, Throttling- und Reset-Ereignisse |

Testergebnis Iteration 3:

```text
Bestanden: 56
Fehlgeschlagen: 0
Exit Code: 0
```

---

## 6. Architekturüberblick

Die Architektur ist komponentenorientiert aufgebaut. Die Benutzeroberfläche nimmt Eingaben entgegen. Der `PerformanceController` koordiniert die Fachlogik und erzeugt ein `SimulationResult`.

### 6.1 Hauptkomponenten

| Komponenten-ID | Komponente | Aufgabe |
|---|---|---|
| CMP-001 | `PerformanceModeManager` | Verwaltung der Leistungsmodi |
| CMP-002 | `LoadScenarioManager` | Verwaltung der Lastszenarien |
| CMP-003 | `ClockCalculator` | Berechnung der ungedrosselten CPU-Taktfrequenz |
| CMP-004 | `TemperatureSimulator` | Berechnung der CPU-Temperatur |
| CMP-005 | `ThermalThrottlingController` | Berechnung von Throttling-Stufe und effektivem Takt |
| CMP-006 | `PowerConsumptionCalculator` | Berechnung des Stromverbrauchs |
| CMP-007 | `EventLogger` | Ereignisprotokollierung |
| CMP-008 | `PerformanceController` | Ablaufsteuerung |
| CMP-009 | `UserInterface` | Konsolenbasierte Benutzerinteraktion |

---

### 6.2 Vereinfachter Ablauf

```text
UserInterface
→ SimulationInput
→ PerformanceController
→ PerformanceModeManager
→ LoadScenarioManager
→ ClockCalculator
→ TemperatureSimulator
→ ThermalThrottlingController
→ PowerConsumptionCalculator
→ EventLogger
→ SimulationResult
→ UserInterface
```

---

## 7. Projektstruktur

Die Projektstruktur orientiert sich an der vorgegebenen Repository-Struktur. Die Implementierung, Architektur-/Design-Dokumentation, Testfälle, Testreportings, Retrospectives und Traceability-Dokumente sind nach Iterationen bzw. Projektphasen geordnet.

```text
.
├── Iterations/
│   ├── Iteration 1/
│   │   ├── Link to src/
│   │   ├── Link to SW Architecture & Design/
│   │   ├── Link to Test cases/
│   │   ├── Planning/
│   │   ├── Retrospective/
│   │   └── Test reporting/
│   │
│   ├── Iteration 2/
│   │   ├── Link to src/
│   │   ├── Link to SW Architecture & Design/
│   │   ├── Link to Test cases/
│   │   ├── Planning/
│   │   ├── Retrospective/
│   │   └── Test reporting/
│   │
│   └── Iteration 3/
│       ├── Link to src/
│       ├── Link to SW Architecture & Design/
│       ├── Link to Test cases/
│       ├── Planning/
│       ├── Retrospective/
│       └── Test reporting/
│
├── Requirements/
│
├── SW Architecture & Design/
│   ├── documentations/
│   └── images/
│
├── Traceability matrix/
│
└── README.md
```

Die eigentlichen Java-Quellcodedateien liegen über die jeweiligen `Link to src`-Verknüpfungen in den Iterationsordnern. Dadurch bleibt nachvollziehbar, welcher Quellcodestand zu welcher Iteration gehört.

---

## 8. Ausführen

Benötigt wird ein Java JDK (getestet mit Eclipse Temurin 23). Externe Bibliotheken sind nicht erforderlich. Beispiel für den finalen Stand aus Iteration 3:

```bash
cd "Iterations/Iteration 3"
find "Link to src" "Link to Test cases" -name "*.java" -printf '"%p"\n' > sources.txt
javac -d out @sources.txt
java -cp out Main                      # Simulation starten
java -cp out Iteration3UnitTestRunner  # Unit- und Regressionstests ausführen
```

Die Iterationen 1 und 2 lassen sich genauso aus ihren eigenen Ordnern starten.

---

## 9. Traceability

Die Traceability Matrix wurde über mehrere Versionen fortgeschrieben.

| Version | Bedeutung |
|---|---|
| `Traceability_Matrix_v0.1.md` | erste Grobmatrix |
| `Traceability_Matrix_v0.2.md` | vollständige Design-Traceability |
| `Traceability_Matrix_v0.3.md` | Iteration 1 implementiert und getestet |
| `Traceability_Matrix_v0.4.md` | Startbaseline Iteration 2 |
| `Traceability_Matrix_v0.5.md` | Iteration 2 implementiert und getestet |
| `Traceability_Matrix_v0.6.md` | Iteration 3 implementiert und getestet |

Die finale Version `Traceability_Matrix_v0.6.md` weist die vollständige Nachverfolgbarkeit von Anforderungen über Architektur, Feindesign, Implementierung, Testfälle und Testergebnisse nach.

---

## 10. Baselines

Am Ende jeder Iteration wurde im Kurs-Repository eine Git-Baseline (Tag) gesetzt. Jeder Iterationsstand liegt hier vollständig in einem eigenen Ordner unter `Iterations/`. Der finale Stand ist `Iterations/Iteration 3`.

---

## 11. Verwendung von KI als Hilfsmittel

Im Rahmen der Projektarbeit wurde KI unterstützend eingesetzt. Die KI wurde insbesondere verwendet, um eigene Notizen sprachlich auszuformulieren, Inhalte verständlicher zu strukturieren und Dokumentationsabschnitte übersichtlich zu gliedern.

Zusätzlich wurde KI zur Unterstützung bei der Veranschaulichung von Zusammenhängen genutzt, zum Beispiel bei der Erstellung und Strukturierung von Tabellen, Diagrammbeschreibungen und Architekturübersichten. Auch bei der Formulierung von Requirements, Testfällen, Traceability-Zuordnungen und iterativen Planungsschritten diente KI als Hilfsmittel zur sprachlichen und strukturellen Verbesserung.

Bei der Erstellung der Unittests wurde KI unterstützend genutzt, um sinnvolle Testfälle aus den Requirements und Teilfunktionalitäten abzuleiten, Testdaten zu strukturieren und erwartete Ergebnisse nachvollziehbar zu formulieren. Die finale Auswahl, Anpassung und Überprüfung der Tests erfolgte eigenständig im Rahmen der Projektumsetzung.

Die fachlichen Entscheidungen, die Auswahl der Teilfunktionalitäten, die Projektstruktur sowie die finale Bewertung und Anpassung der Inhalte wurden eigenständig vorgenommen. KI wurde damit nicht als eigenständige Quelle für fertige Projektergebnisse verwendet, sondern als unterstützendes Werkzeug zur Ausarbeitung, Gliederung und besseren Darstellung der eigenen Arbeit.
