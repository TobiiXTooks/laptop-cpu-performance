# Iteration 1 – Retrospective


## 1. Zweck dieses Dokuments

Dieses Dokument reflektiert den Verlauf und das Ergebnis der Iteration 1.

Ziel der Retrospective ist es, festzuhalten:

- was in Iteration 1 umgesetzt wurde,
- welche Requirements erfüllt wurden,
- welche Tests durchgeführt wurden,
- welche Probleme oder Anpassungen aufgetreten sind,
- welche Erkenntnisse für Iteration 2 relevant sind,
- ob die Iteration für eine Baseline bereit ist.

---

## 2. Ziel der Iteration 1

Das Ziel der Iteration 1 war die Erstellung einer ersten lauffähigen Grundsimulation für das Laptop-Prozessorleistungsmodul.

Der Nutzer sollte:

1. einen Leistungsmodus auswählen,
2. ein Lastszenario auswählen,
3. eine daraus abgeleitete CPU-Auslastung erhalten,
4. eine ungedrosselte CPU-Taktfrequenz berechnet bekommen,
5. das Simulationsergebnis verständlich ausgegeben bekommen.

Temperaturberechnung, Thermal Throttling, Stromverbrauchsberechnung und Ereignisprotokollierung waren bewusst nicht Bestandteil dieser Iteration.

---

## 3. Umgesetzter Funktionsumfang

In Iteration 1 wurden folgende Teilfunktionalitäten umgesetzt:

| Teilfunktionalität | Beschreibung | Status |
|---|---|---|
| TF-01 | Leistungsmodus verwalten | umgesetzt |
| TF-02 | Lastszenario auswählen | umgesetzt |
| TF-03 | CPU-Taktfrequenz berechnen | umgesetzt |

Nicht umgesetzt wurden:

| Teilfunktionalität | Grund |
|---|---|
| TF-04 Temperaturberechnung | für spätere Iteration vorgesehen |
| TF-05 Thermal Throttling | benötigt Temperaturberechnung |
| TF-06 Stromverbrauch | für spätere Iteration vorgesehen |
| TF-07 Ereignisprotokoll | für spätere Iteration vorgesehen |

---

## 4. Erfüllte Requirements

Die folgenden funktionalen Requirements wurden in Iteration 1 umgesetzt und getestet:

| Requirement-ID | Kurzbeschreibung | Status |
|---|---|---|
| REQ-F-001 | Leistungsmodi bereitstellen | erfüllt |
| REQ-F-002 | ausgewählten Leistungsmodus übernehmen | erfüllt |
| REQ-F-003 | benutzerdefinierten Leistungsmodus annehmen | erfüllt |
| REQ-F-004 | ungültige Werte im benutzerdefinierten Modus zurückweisen | erfüllt |
| REQ-F-005 | Lastszenarien bereitstellen | erfüllt |
| REQ-F-006 | CPU-Auslastung zum Lastszenario zuordnen | erfüllt |
| REQ-F-007 | ausgewähltes Lastszenario übernehmen | erfüllt |
| REQ-F-008 | CPU-Auslastung in Prozent ausgeben | erfüllt |
| REQ-F-009 | ungedrosselte CPU-Taktfrequenz bestimmen | erfüllt |
| REQ-F-010 | CPU-Taktfrequenz auf 1,2 GHz bis 4,2 GHz begrenzen | erfüllt |
| REQ-F-011 | CPU-Taktfrequenz mit Einheit GHz ausgeben | erfüllt |
| REQ-F-012 | CPU-Taktfrequenz bei Änderung neu bestimmen | erfüllt |

Die folgenden nicht-funktionalen Requirements wurden in Iteration 1 betrachtet:

| Requirement-ID | Kurzbeschreibung | Status |
|---|---|---|
| REQ-NF-001 | gleiche Eingaben erzeugen gleiche Ausgaben | erfüllt |
| REQ-NF-002 | funktionale Requirements erhalten Testfälle | erfüllt |
| REQ-NF-003 | ungültige Eingaben führen nicht zu unkontrolliertem Programmabbruch | erfüllt |
| REQ-NF-004 | Baseline am Ende der Iteration | offen bis Git-Commit und Tag |

---

## 5. Erstellte Artefakte

In Iteration 1 wurden folgende Artefakte erstellt oder aktualisiert:

| Artefakt | Status |
|---|---|
| `Planning.md` | erstellt |
| `Iteration1_Architektur.md` | erstellt |
| Architekturdiagramm | erstellt |
| `Iteration1_Feindesign.md` | erstellt |
| Klassendiagramm | erstellt |
| Java-Implementierung | erstellt |
| `Iteration1_Testfaelle.md` | erstellt |
| `Iteration1UnitTestRunner.java` | erstellt |
| `Test reporting.md` | erstellt |
| `Traceability_Matrix.md` | aktualisiert |
| `Retrospective.md` | erstellt |

---

## 6. Implementierungsergebnis

Die Implementierung wurde in Java mit IntelliJ-Struktur umgesetzt.

Verwendete Struktur:

```text
Projekt/
└── Iteration 1/
    └── src/
        ├── Main.java
        ├── controller/
        │   └── PerformanceController.java
        ├── model/
        │   ├── SimulationInput.java
        │   ├── PerformanceMode.java
        │   ├── LoadScenario.java
        │   ├── ProcessorState.java
        │   └── SimulationResult.java
        ├── service/
        │   ├── PerformanceModeManager.java
        │   ├── LoadScenarioManager.java
        │   └── ClockCalculator.java
        └── ui/
            └── UserInterface.java
```

Die Architektur wurde in der Implementierung sinnvoll abgebildet:

| Schicht | Umsetzung |
|---|---|
| UI | `UserInterface` |
| Ablaufsteuerung | `PerformanceController` |
| Fachlogik / Services | `PerformanceModeManager`, `LoadScenarioManager`, `ClockCalculator` |
| Datenmodelle | `SimulationInput`, `PerformanceMode`, `LoadScenario`, `ProcessorState`, `SimulationResult` |
| Startpunkt | `Main` |

---

## 7. Testergebnis

Für Iteration 1 wurden automatisierte Unit- und Integrationstests sowie eine manuelle Validierung durchgeführt.

| Testbereich | Ergebnis |
|---|---|
| Unit-Tests TST-I1-001 bis TST-I1-019 | bestanden |
| Manuelle Validierung TST-I1-020 | bestanden |
| Fehlgeschlagene Tests | 0 |
| Offene fachliche Fehler | 0 |

Die Unit-Tests wurden über den TestRunner ausgeführt:

```text
Test cases/Iteration1UnitTestRunner.java
```

Die manuelle Validierung `TST-I1-020` wurde zusätzlich durchgeführt, weil der vollständige Konsolenlauf über `Main.java` und `UserInterface` interaktiv ist.

---

## 8. Traceability-Ergebnis

Die allgemeine Traceability Matrix wurde für Iteration 1 aktualisiert.

Dabei wurden:

- geplante Testfall-IDs durch konkrete `TST-I1-*`-Testfälle ersetzt,
- geplante Python-Code-Dateien durch tatsächliche Java-Code-Dateien ersetzt,
- Requirements REQ-F-001 bis REQ-F-012 mit Code und Tests verknüpft,
- REQ-NF-001 bis REQ-NF-003 mit Tests und Artefakten verknüpft,
- der Status für Iteration 1 auf `Implementiert und getestet` aktualisiert.

Offen bleibt nur REQ-NF-004, da die Baseline erst nach Abschluss dieser Retrospective gesetzt wird.

---

## 9. Was lief gut?

| Bereich | Bewertung |
|---|---|
| Strukturierung | Die Trennung in `model`, `service`, `controller` und `ui` war übersichtlich und gut nachvollziehbar. |
| Architekturbezug | Die Implementierung konnte direkt aus Architektur und Feindesign abgeleitet werden. |
| Testbarkeit | Die Serviceklassen waren getrennt testbar. |
| Traceability | Requirements konnten eindeutig zu Komponenten, Code-Dateien und Testfällen zugeordnet werden. |
| Iterationsumfang | Der Umfang war passend begrenzt und führte zu einer lauffähigen Softwareversion. |
| Erweiterbarkeit | Die Struktur kann in Iteration 2 um Temperatur und Stromverbrauch erweitert werden. |

---

## 10. Was war problematisch?

| Problem | Auswirkung | Umgang damit |
|---|---|---|
| Ursprüngliche Planung war teilweise auf Python-Dateien ausgelegt | Code-Dateien passten nicht zur Java-Implementierung | Dateistruktur und Traceability wurden auf Java angepasst. |
| Unit-Test-Ausführung über Linux-Befehl funktionierte unter Windows/IntelliJ nicht direkt | Tests konnten nicht mit dem ursprünglichen Befehl ausgeführt werden | Alternative Ausführung über IntelliJ, PowerShell oder TestRunner wurde vorgesehen. |
| Interaktive UI ist schwer als Unit-Test prüfbar | `TST-I1-020` konnte nicht sinnvoll automatisiert werden | Testfall wurde als manuelle Validierung ausgeführt und dokumentiert. |

---

## 11. Änderungen während der Iteration

Während Iteration 1 wurden folgende Anpassungen vorgenommen:

| Änderung | Begründung |
|---|---|
| Umsetzung in Java statt Python | Projekt wird in IntelliJ mit Java umgesetzt. |
| Vereinfachte Ordnerstruktur | Die tiefe Maven-Struktur war für das Projekt nicht notwendig. |
| Einführung eines TestRunners ohne externes Framework | Tests sollten einfach in IntelliJ ausführbar sein. |
| Aktualisierung der allgemeinen Traceability Matrix | Die Matrix sollte den tatsächlichen Iteration-1-Stand abbilden. |
| Manuelle Validierung für Konsolenlauf | Interaktive Eingaben sind nicht sinnvoll als klassischer Unit-Test abbildbar. |

---

## 12. Erkenntnisse für Iteration 2

Für Iteration 2 sollten folgende Punkte beachtet werden:

| Erkenntnis | Konsequenz für Iteration 2 |
|---|---|
| Testfälle sollten vor oder parallel zur Implementierung konkret geplant werden. | Für neue Funktionen direkt passende Unit-Tests erstellen. |
| Traceability sollte nicht getrennt von der allgemeinen Matrix gepflegt werden. | Iteration-2-Traceability direkt in die bestehende Matrix integrieren. |
| Interaktive Funktionen sind schwer automatisiert zu testen. | Fachlogik möglichst aus `UserInterface` heraushalten. |
| Java-Dateistruktur muss durchgängig verwendet werden. | Alle neuen Design- und Traceability-Einträge direkt mit `.java`-Dateien planen. |
| Komponenten sollten weiterhin klar getrennt bleiben. | Neue Funktionen als eigene Services ergänzen, nicht in bestehende Klassen überladen. |

---

## 13. Offene Punkte nach Iteration 1

| Offener Punkt | Status | Weiteres Vorgehen |
|---|---|---|
| Git-Baseline für Iteration 1 | offen | Commit und Tag nach Abschluss der Retrospective setzen |
| Temperaturberechnung | offen | Umsetzung in Iteration 2 |
| Stromverbrauchsberechnung | offen | Umsetzung in Iteration 2 |
| Ereignisprotokollierung | offen | Vorbereitung oder Teilumsetzung in Iteration 2 |
| Thermal Throttling | offen | Umsetzung in Iteration 3 |

---

## 14. Bewertung der Iteration 1

Iteration 1 war erfolgreich, weil:

- eine lauffähige Softwareversion erstellt wurde,
- die geplanten Teilfunktionalitäten TF-01 bis TF-03 umgesetzt wurden,
- die funktionalen Requirements REQ-F-001 bis REQ-F-012 erfüllt wurden,
- Unit-Tests und manuelle Validierung durchgeführt wurden,
- die Traceability bis zu Code-Dateien und Testfällen dokumentiert wurde,
- die Softwarestruktur für spätere Erweiterungen geeignet bleibt.

Die Iteration ist fachlich abgeschlossen.

---

## 15. Nächster Schritt

Nach dieser Retrospective wird die Iteration 1 versioniert abgeschlossen.

Empfohlene Git-Befehle:

```bash
git add .
git commit -m "Complete iteration 1"
git tag iteration-1-baseline
```

Danach kann Iteration 2 begonnen werden.
