# Iteration 1 – Planning

**Iteration:** 1  
**Ziel:** Lauffähige Grundsimulation für Leistungsmodus, Lastszenario und CPU-Taktfrequenz

---

## 1. Ziel der Iteration

Ziel der Iteration 1 ist die Erstellung einer ersten lauffähigen Softwareversion des Laptop-Prozessorleistungsmoduls.

Der Nutzer soll einen Leistungsmodus und ein Lastszenario auswählen können. Das System soll daraus eine CPU-Auslastung ableiten und eine simulierte ungedrosselte CPU-Taktfrequenz berechnen.

---

## 2. Fachlicher Umfang

In Iteration 1 werden folgende Teilfunktionalitäten umgesetzt:

| Teilfunktionalität | Beschreibung | Status in Iteration 1 |
|---|---|---|
| TF-01 | Leistungsmodus mit benutzerdefiniertem Modus | umzusetzen |
| TF-02 | Vordefinierte Lastszenarien | umzusetzen |
| TF-03 | Taktberechnung abhängig von Modus und Auslastung | umzusetzen |

---

## 3. Nicht Bestandteil der Iteration

Folgende Teilfunktionalitäten werden in Iteration 1 noch nicht umgesetzt:

| Teilfunktionalität | Grund |
|---|---|
| TF-04 Temperaturberechnung | Bestandteil einer späteren Iteration |
| TF-05 Thermal Throttling | benötigt Temperaturberechnung |
| TF-06 Stromverbrauch | Bestandteil einer späteren Iteration |
| TF-07 Ereignisprotokoll | vollständige Protokollierung folgt später |

---

## 4. Requirements der Iteration 1

### Funktionale Requirements

| Requirement-ID | Teilfunktionalität | Kurzbeschreibung |
|---|---|---|
| REQ-F-001 | TF-01 | Leistungsmodi bereitstellen |
| REQ-F-002 | TF-01 | ausgewählten Leistungsmodus übernehmen |
| REQ-F-003 | TF-01 | benutzerdefinierten Leistungsmodus annehmen |
| REQ-F-004 | TF-01 | ungültige Werte im benutzerdefinierten Modus zurückweisen |
| REQ-F-005 | TF-02 | Lastszenarien bereitstellen |
| REQ-F-006 | TF-02 | CPU-Auslastung zum Lastszenario zuordnen |
| REQ-F-007 | TF-02 | ausgewähltes Lastszenario übernehmen |
| REQ-F-008 | TF-02 | CPU-Auslastung in Prozent ausgeben |
| REQ-F-009 | TF-03 | ungedrosselte CPU-Taktfrequenz bestimmen |
| REQ-F-010 | TF-03 | CPU-Taktfrequenz auf 1,2 GHz bis 4,2 GHz begrenzen |
| REQ-F-011 | TF-03 | CPU-Taktfrequenz mit Einheit GHz ausgeben |
| REQ-F-012 | TF-03 | CPU-Taktfrequenz bei Änderung neu bestimmen |

### Nicht-funktionale Requirements

| Requirement-ID | Kategorie | Kurzbeschreibung |
|---|---|---|
| REQ-NF-001 | Determinismus | gleiche Eingaben erzeugen gleiche Ausgaben |
| REQ-NF-002 | Testbarkeit | funktionale Requirements erhalten Testfälle |
| REQ-NF-003 | Robustheit | ungültige Eingaben führen nicht zu unkontrolliertem Programmabbruch |
| REQ-NF-004 | Konfigurationsmanagement | am Ende der Iteration wird eine Baseline erzeugt |

---

## 5. Architektur- und Designumfang

In Iteration 1 werden folgende Komponenten aktiv verwendet:

| Komponenten-ID | Komponente | Aufgabe |
|---|---|---|
| CMP-009 | UserInterface | Eingaben erfassen und Ergebnis anzeigen |
| CMP-008 | PerformanceController | Simulationsablauf koordinieren |
| CMP-001 | PerformanceModeManager | Leistungsmodi bereitstellen und validieren |
| CMP-002 | LoadScenarioManager | Lastszenarien bereitstellen und validieren |
| CMP-003 | ClockCalculator | CPU-Taktfrequenz berechnen |

---

## 6. Datenmodelle der Iteration 1

| Datenmodell | Zweck |
|---|---|
| SimulationInput | bündelt Leistungsmodus, Lastszenario und CPU-Auslastung |
| PerformanceMode | beschreibt den ausgewählten Leistungsmodus |
| LoadScenario | beschreibt das ausgewählte Lastszenario |
| ProcessorState | speichert CPU-Auslastung und berechnete CPU-Taktfrequenz |
| SimulationResult | bündelt das Ergebnis für die Ausgabe |

---

## 7. Geplanter Ablauf der Software

1. Nutzer startet die Anwendung.
2. Nutzer wählt einen Leistungsmodus.
3. Nutzer wählt ein Lastszenario.
4. System bestimmt die CPU-Auslastung des Lastszenarios.
5. System bestimmt die ungedrosselte CPU-Taktfrequenz.
6. System begrenzt die CPU-Taktfrequenz auf den gültigen Wertebereich.
7. System erzeugt ein SimulationResult.
8. System zeigt Leistungsmodus, Lastszenario, CPU-Auslastung und CPU-Taktfrequenz an.

---

## 8. Geplante Arbeitsergebnisse

Am Ende von Iteration 1 sollen folgende Artefakte vorhanden sein:

| Artefakt | Beschreibung |
|---|---|
| Planning.md | Planung der Iteration |
| Iteration1_Architektur.md | reduzierte Architektursicht für Iteration 1 |
| Iteration1_Feindesign.md | Klassen- und Methodensicht für Iteration 1 |
| Sourcecode | lauffähige Grundsimulation |
| Testfälle | Tests zu REQ-F-001 bis REQ-F-012 und REQ-NF-001 bis REQ-NF-004 |
| Test reporting.md | dokumentierte Testergebnisse |
| Traceability Matrix.md | Zuordnung von Requirements zu Komponenten, Code und Tests |
| Retrospective.md | Bewertung der Iteration |

---

## 9. Akzeptanzkriterien

Iteration 1 gilt als abgeschlossen, wenn folgende Kriterien erfüllt sind:

| Kriterium | Erwartung |
|---|---|
| Lauffähigkeit | Die Software kann gestartet und ausgeführt werden. |
| Eingabe | Leistungsmodus und Lastszenario können ausgewählt werden. |
| Verarbeitung | CPU-Auslastung und CPU-Taktfrequenz werden berechnet. |
| Ausgabe | Das Ergebnis wird mit Einheiten angezeigt. |
| Robustheit | Ungültige Eingaben führen nicht zu einem unkontrollierten Absturz. |
| Tests | Die Requirements der Iteration 1 sind durch Testfälle abgedeckt. |
| Traceability | Requirements, Komponenten, Code-Dateien und Testfälle sind zuordenbar. |
| Baseline | Der Softwarestand der Iteration 1 ist versioniert festgehalten. |

---

## 10. Risiken und Gegenmaßnahmen

| Risiko | Auswirkung | Gegenmaßnahme |
|---|---|---|
| Umfang der Iteration wird zu groß | Iteration 1 wird nicht sauber abgeschlossen | Temperatur, Throttling, Stromverbrauch und Logging konsequent ausklammern |
| Requirements und Code passen nicht zusammen | Traceability wird unklar | Requirements vor Implementierung eindeutig den Komponenten zuordnen |
| Ungültige Eingaben werden nicht behandelt | Software kann abbrechen | Eingabevalidierung früh umsetzen |
| Spätere Erweiterungen werden erschwert | Iteration 2 und 3 benötigen Umstrukturierung | Klassen bereits erweiterbar anlegen |

---

## 11. Definition of Done

Iteration 1 ist fertig, wenn:

- alle geplanten Funktionen aus TF-01, TF-02 und TF-03 umgesetzt sind,
- die Software lauffähig ist,
- alle Iteration-1-Requirements mindestens einem Testfall zugeordnet sind,
- die Tests dokumentiert wurden,
- die Traceability Matrix für Iteration 1 gepflegt ist,
- eine Git-Baseline für Iteration 1 erstellt wurde.