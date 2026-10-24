# Requirements

**Status:** Entwurf  
**Version:** 0.3  
**Datum:** 2026-05-07  

---

## 1. Ziel des Dokuments

Dieses Dokument beschreibt die funktionalen und nicht-funktionalen Anforderungen an die Software-Simulation der Prozessorleistung eines Laptops.

---

## 2. Systemkontext

Der Nutzer wählt einen Leistungsmodus, ein Lastszenario und eine Kühlungsstufe aus. Das System erzeugt daraus simulierte Werte für CPU-Auslastung, CPU-Taktfrequenz, CPU-Temperatur, Throttling-Stufe und Stromverbrauch. Zusätzlich protokolliert das System relevante Ereignisse des Simulationslaufs.

---

## 3. Systemabgrenzung

### 3.1 Enthaltene Systemfunktionen

| ID | Teilfunktionalität |
|---|---|
| TF-01 | Leistungsmodus mit benutzerdefiniertem Modus |
| TF-02 | Vordefinierte Lastszenarien |
| TF-03 | Taktberechnung abhängig von Modus und Auslastung |
| TF-04 | Temperaturberechnung mit Lüfter-/Kühlungsfaktor |
| TF-05 | Mehrstufiges Thermal Throttling |
| TF-06 | Stromverbrauch in Watt ableiten |
| TF-07 | Ereignisprotokoll für Moduswechsel, Warnungen und Throttling |

### 3.2 Ausgeschlossene Systemfunktionen

Das System umfasst nicht:

| Ausschluss | Begründung |
|---|---|
| reale CPU-Steuerung | Es steht keine reale Hardware zur Verfügung. |
| reales Auslesen von Sensorwerten | Alle Werte werden simuliert. |
| reale Energieverbrauchsmessung | Der Stromverbrauch wird berechnet. |
| Benchmark-Vergleich mit realen Prozessoren | Benchmark-Vergleiche wurden aus dem Projektumfang entfernt. |
| System- oder Abnahmetest | Für die Projektarbeit ist kein System- bzw. Abnahmetest erforderlich. |

---

## 4. Glossar und definierte Begriffe

| Begriff | Definierte Bedeutung |
|---|---|
| System | Die zu entwickelnde Software-Simulation der Laptop-Prozessorleistung. |
| Nutzer | Person, die die Simulation bedient und Eingaben auswählt. |
| Simulationslauf | Ein zusammenhängender Ablauf von Eingabeauswahl, Berechnung und Ausgabe der Simulationswerte. |
| Leistungsmodus | Auswahl, die das simulierte Leistungsverhalten des Prozessors bestimmt. |
| Benutzerdefinierter Leistungsmodus | Leistungsmodus, bei dem der Nutzer festgelegte Simulationsparameter innerhalb definierter Wertebereiche vorgibt. |
| Lastszenario | Vordefinierte Nutzungssituation mit fest zugeordneter CPU-Auslastung. |
| CPU-Auslastung | Simulierter Auslastungswert des Prozessors in Prozent. |
| CPU-Taktfrequenz | Simulierter Prozessortakt in Gigahertz. |
| Ungedrosselte CPU-Taktfrequenz | CPU-Taktfrequenz vor Anwendung von Thermal Throttling. |
| Effektive CPU-Taktfrequenz | CPU-Taktfrequenz nach Anwendung der aktuellen Throttling-Stufe. |
| CPU-Temperatur | Simulierte Prozessortemperatur in Grad Celsius. |
| Kühlungsstufe | Simulierte Stärke der Kühlung. |
| Throttling-Stufe | Zustand, der beschreibt, ob und wie stark die Prozessorleistung temperaturbedingt reduziert wird. |
| Stromverbrauch | Simulierter Energieverbrauch des Prozessors in Watt. |
| Ereignisprotokoll | Chronologische Liste relevanter Ereignisse innerhalb eines Simulationslaufs. |

---

## 5. Definierte Wertebereiche und Zustände

### 5.1 Leistungsmodi

| Leistungsmodus | Bedeutung |
|---|---|
| Energiesparen | Modus für geringe simulierte Prozessorleistung und geringen simulierten Stromverbrauch. |
| Ausgeglichen | Modus für mittlere simulierte Prozessorleistung und mittleren simulierten Stromverbrauch. |
| Höchstleistung | Modus für hohe simulierte Prozessorleistung und hohen simulierten Stromverbrauch. |
| Benutzerdefiniert | Modus mit nutzerdefinierter maximaler CPU-Taktfrequenz und nutzerdefiniertem Stromverbrauchsgrenzwert. |

### 5.2 Lastszenarien und CPU-Auslastung

| Lastszenario | CPU-Auslastung |
|---|---:|
| Leerlauf | 15 % |
| Office | 35 % |
| Multimedia | 55 % |
| Gaming | 85 % |
| Stresstest | 100 % |

### 5.3 Taktfrequenzen für feste Leistungsmodi

| Leistungsmodus | Leerlauf | Office | Multimedia | Gaming | Stresstest |
|---|---:|---:|---:|---:|---:|
| Energiesparen | 1,2 GHz | 1,4 GHz | 1,6 GHz | 1,8 GHz | 2,0 GHz |
| Ausgeglichen | 1,4 GHz | 2,0 GHz | 2,5 GHz | 3,0 GHz | 3,2 GHz |
| Höchstleistung | 2,0 GHz | 2,8 GHz | 3,4 GHz | 4,0 GHz | 4,2 GHz |

### 5.4 Wertebereich für benutzerdefinierten Leistungsmodus

| Parameter | Minimalwert | Maximalwert | Einheit |
|---|---:|---:|---|
| Maximale CPU-Taktfrequenz | 1,2 | 4,2 | GHz |
| Stromverbrauchsgrenzwert | 8 | 65 | W |

### 5.5 Kühlungsstufen

| Kühlungsstufe | Bedeutung |
|---|---|
| Niedrig | geringe simulierte Kühlwirkung |
| Mittel | mittlere simulierte Kühlwirkung |
| Hoch | hohe simulierte Kühlwirkung |

### 5.6 Temperaturbereich

| Wert | Grenze |
|---|---:|
| Minimale CPU-Temperatur | 35 °C |
| Maximale CPU-Temperatur | 100 °C |

### 5.7 Throttling-Stufen

| Throttling-Stufe | Temperaturbereich | Effektive CPU-Taktfrequenz |
|---|---:|---|
| Normal | 35 °C bis 69 °C | 100 % der ungedrosselten CPU-Taktfrequenz |
| Warnung | 70 °C bis 79 °C | 100 % der ungedrosselten CPU-Taktfrequenz |
| Reduziert | 80 °C bis 89 °C | 80 % der ungedrosselten CPU-Taktfrequenz |
| Kritisch | 90 °C bis 100 °C | 60 % der ungedrosselten CPU-Taktfrequenz |

### 5.8 Stromverbrauch

| Wert | Grenze |
|---|---:|
| Minimaler Stromverbrauch | 8 W |
| Maximaler Stromverbrauch | 65 W |

### 5.9 Ereignistypen

| Ereignistyp | Bedeutung |
|---|---|
| `MODUSWECHSEL` | Der aktuelle Leistungsmodus wurde geändert. |
| `LASTSZENARIOWECHSEL` | Das aktuelle Lastszenario wurde geändert. |
| `TEMPERATURWARNUNG` | Die CPU-Temperatur hat mindestens 70 °C erreicht. |
| `THROTTLING_AKTIVIERT` | Die Throttling-Stufe hat `Reduziert` oder `Kritisch` erreicht. |
| `THROTTLING_GEÄNDERT` | Die Throttling-Stufe wurde gegenüber dem vorherigen Simulationszustand geändert. |
| `SIMULATION_ZURÜCKGESETZT` | Der Simulationslauf wurde zurückgesetzt. |

---

## 6. Klassifizierung und Priorisierung

### 6.1 Kategorien funktionaler Requirements

| Kategorie | Bedeutung |
|---|---|
| Eingabe | Auswahl oder Übernahme von Nutzereingaben. |
| Berechnung | Bestimmung simulierter Werte. |
| Ausgabe | Bereitstellung berechneter Werte für den Nutzer. |
| Ereignisprotokoll | Erfassung und Bereitstellung von Ereignissen. |
| Zustandsführung | Verwaltung des aktuellen Simulationszustands. |

### 6.2 Kategorien nicht-funktionaler Requirements

| Kategorie | Bedeutung |
|---|---|
| Ausführbarkeit | Anforderungen an die Zielumgebung. |
| Hardware-Simulation | Anforderungen zur Trennung von realer Hardware. |
| Determinismus | Anforderungen an reproduzierbare Ergebnisse. |
| Testbarkeit | Anforderungen an Prüfbarkeit. |
| Traceability | Anforderungen an Nachverfolgbarkeit. |
| Dokumentation | Anforderungen an Projektdokumente. |
| Konfigurationsmanagement | Anforderungen an Versionierung und Baselines. |
| Performance | Anforderungen an Laufzeitverhalten. |
| Robustheit | Anforderungen an kontrolliertes Verhalten bei ungültigen Eingaben. |

### 6.3 Priorisierung

Alle Requirements in diesem Dokument besitzen die Priorität `Muss`.

---

## 7. Funktionale Requirements

| ID | TF | Iteration | Kategorie | Priorität | Requirement | Verifikation |
|---|---|---:|---|---|---|---|
| REQ-F-001 | TF-01 | 1 | Eingabe | Muss | Bei Start eines Simulationslaufs muss das System die Leistungsmodi `Energiesparen`, `Ausgeglichen`, `Höchstleistung` und `Benutzerdefiniert` bereitstellen. | Review, Funktionstest |
| REQ-F-002 | TF-01 | 1 | Zustandsführung | Muss | Wenn der Nutzer einen Leistungsmodus auswählt, muss das System den ausgewählten Leistungsmodus als aktuellen Leistungsmodus übernehmen. | Funktionstest |
| REQ-F-003 | TF-01 | 1 | Eingabe | Muss | Wenn der Nutzer den Leistungsmodus `Benutzerdefiniert` auswählt, muss das System eine maximale CPU-Taktfrequenz von 1,2 GHz bis 4,2 GHz und einen Stromverbrauchsgrenzwert von 8 W bis 65 W annehmen. | Funktionstest, Grenzwerttest |
| REQ-F-004 | TF-01 | 1 | Robustheit | Muss | Wenn der Nutzer für den Leistungsmodus `Benutzerdefiniert` einen Wert außerhalb des definierten Wertebereichs eingibt, muss das System den eingegebenen Wert zurückweisen. | Grenzwerttest |
| REQ-F-005 | TF-02 | 1 | Eingabe | Muss | Bei Start eines Simulationslaufs muss das System die Lastszenarien `Leerlauf`, `Office`, `Multimedia`, `Gaming` und `Stresstest` bereitstellen. | Review, Funktionstest |
| REQ-F-006 | TF-02 | 1 | Berechnung | Muss | Bei Auswahl eines Lastszenarios muss das System die definierte CPU-Auslastung, 15%, 35%, 55%, 85% und 100%, zuordnen. | Funktionstest |
| REQ-F-007 | TF-02 | 1 | Zustandsführung | Muss | Wenn der Nutzer ein Lastszenario auswählt, muss das System das ausgewählte Lastszenario als aktuelles Lastszenario übernehmen. | Funktionstest |
| REQ-F-008 | TF-02 | 1 | Ausgabe | Muss | Nach Übernahme eines Lastszenarios muss das System die CPU-Auslastung des aktuellen Lastszenarios in Prozent ausgeben. | Funktionstest |
| REQ-F-009 | TF-03 | 1 | Berechnung | Muss | Wenn ein Leistungsmodus und ein Lastszenario aktuell ausgewählt sind, muss das System die ungedrosselte CPU-Taktfrequenz bestimmen. | Funktionstest |
| REQ-F-010 | TF-03 | 1 | Berechnung | Muss | Nach Bestimmung der ungedrosselten CPU-Taktfrequenz muss das System die ungedrosselte CPU-Taktfrequenz auf den Bereich von 1,2 GHz bis 4,2 GHz begrenzen. | Grenzwerttest |
| REQ-F-011 | TF-03 | 1 | Ausgabe | Muss | Nach Bestimmung der ungedrosselten CPU-Taktfrequenz muss das System die ungedrosselte CPU-Taktfrequenz mit der Einheit `GHz` ausgeben. | Funktionstest |
| REQ-F-012 | TF-03 | 1 | Berechnung | Muss | Wenn der aktuelle Leistungsmodus oder das aktuelle Lastszenario geändert wird, muss das System die ungedrosselte CPU-Taktfrequenz vor der nächsten Ausgabe neu bestimmen. | Integrationstest |
| REQ-F-013 | TF-04 | 2 | Eingabe | Muss | Bei Start eines Simulationslaufs muss das System die Kühlungsstufen `Niedrig`, `Mittel` und `Hoch` bereitstellen. | Review, Funktionstest |
| REQ-F-014 | TF-04 | 2 | Zustandsführung | Muss | Wenn der Nutzer eine Kühlungsstufe auswählt, muss das System die ausgewählte Kühlungsstufe als aktuelle Kühlungsstufe übernehmen. | Funktionstest |
| REQ-F-015 | TF-04 | 2 | Berechnung | Muss | Wenn CPU-Auslastung, CPU-Taktfrequenz und Kühlungsstufe vorliegen, muss das System die CPU-Temperatur im Bereich von 35 °C bis 100 °C bestimmen. | Funktionstest, Grenzwerttest |
| REQ-F-016 | TF-04 | 2 | Ausgabe | Muss | Nach Bestimmung der CPU-Temperatur muss das System die CPU-Temperatur mit der Einheit `°C` ausgeben. | Funktionstest |
| REQ-F-017 | TF-06 | 2 | Berechnung | Muss | Wenn Leistungsmodus, CPU-Auslastung und effektive CPU-Taktfrequenz vorliegen, muss das System den Stromverbrauch im Bereich von 8 W bis 65 W bestimmen. | Funktionstest, Grenzwerttest |
| REQ-F-018 | TF-06 | 2 | Ausgabe | Muss | Nach Bestimmung des Stromverbrauchs muss das System den Stromverbrauch mit der Einheit `W` ausgeben. | Funktionstest |
| REQ-F-019 | TF-06 | 3 | Berechnung | Muss | Wenn die effektive CPU-Taktfrequenz durch Thermal Throttling geändert wird, muss das System den Stromverbrauch vor der nächsten Ausgabe neu bestimmen. | Integrationstest |
| REQ-F-020 | TF-07 | 2 | Ereignisprotokoll | Muss | Wenn der aktuelle Leistungsmodus geändert wird, muss das System einen Ereignisprotokolleintrag vom Typ `MODUSWECHSEL` erfassen. | Funktionstest |
| REQ-F-021 | TF-07 | 2 | Ereignisprotokoll | Muss | Wenn das aktuelle Lastszenario geändert wird, muss das System einen Ereignisprotokolleintrag vom Typ `LASTSZENARIOWECHSEL` erfassen. | Funktionstest |
| REQ-F-022 | TF-07 | 2 | Ereignisprotokoll | Muss | Wenn die CPU-Temperatur mindestens 70 °C beträgt, muss das System einen Ereignisprotokolleintrag vom Typ `TEMPERATURWARNUNG` erfassen. | Grenzwerttest |
| REQ-F-023 | TF-07 | 2 | Ereignisprotokoll | Muss | Bei Erfassung eines Ereignisses muss das System Ereigniszeitpunkt, Ereignistyp, Ereignisbeschreibung, Leistungsmodus, Lastszenario, CPU-Auslastung, CPU-Taktfrequenz, CPU-Temperatur und Throttling-Stufe speichern. | Review, Funktionstest |
| REQ-F-024 | TF-05 | 3 | Berechnung | Muss | Wenn die CPU-Temperatur bestimmt wurde, muss das System die Throttling-Stufe zu `Normal`, `Warnung`, `Reduziert` oder `Kritisch` zuordnen. | Grenzwerttest |
| REQ-F-025 | TF-05 | 3 | Berechnung | Muss | Wenn die Throttling-Stufe `Normal` oder `Warnung` ist, muss das System die effektive CPU-Taktfrequenz auf 100 % der ungedrosselten CPU-Taktfrequenz setzen. | Grenzwerttest |
| REQ-F-026 | TF-05 | 3 | Berechnung | Muss | Wenn die Throttling-Stufe `Reduziert` ist, muss das System die effektive CPU-Taktfrequenz auf 80 % der ungedrosselten CPU-Taktfrequenz setzen. | Grenzwerttest |
| REQ-F-027 | TF-05 | 3 | Berechnung | Muss | Wenn die Throttling-Stufe `Kritisch` ist, muss das System die effektive CPU-Taktfrequenz auf 60 % der ungedrosselten CPU-Taktfrequenz setzen. | Grenzwerttest |
| REQ-F-028 | TF-05 | 3 | Ausgabe | Muss | Nach Zuordnung der Throttling-Stufe muss das System die aktuelle Throttling-Stufe ausgeben. | Funktionstest |
| REQ-F-029 | TF-05 | 3 | Berechnung | Muss | Wenn sich die CPU-Temperatur ändert, muss das System die Throttling-Stufe vor der nächsten Ausgabe neu zuordnen. | Integrationstest |
| REQ-F-030 | TF-07 | 3 | Ereignisprotokoll | Muss | Wenn die Throttling-Stufe `Reduziert` oder `Kritisch` erreicht wird, muss das System einen Ereignisprotokolleintrag vom Typ `THROTTLING_AKTIVIERT` erfassen. | Grenzwerttest |
| REQ-F-031 | TF-07 | 3 | Ereignisprotokoll | Muss | Wenn sich die Throttling-Stufe gegenüber dem vorherigen Simulationszustand ändert, muss das System einen Ereignisprotokolleintrag vom Typ `THROTTLING_GEÄNDERT` erfassen. | Integrationstest |
| REQ-F-032 | TF-07 | 3 | Ausgabe | Muss | Wenn der Nutzer das Ereignisprotokoll anfordert, muss das System alle Ereignisprotokolleinträge in chronologisch aufsteigender Reihenfolge ausgeben. | Funktionstest |
| REQ-F-033 | TF-07 | 3 | Zustandsführung | Muss | Wenn der Nutzer einen neuen Simulationslauf startet, muss das System das Ereignisprotokoll und die aktuellen Simulationswerte zurücksetzen. | Integrationstest |

---

## 8. Nicht-funktionale Requirements

| ID | Iteration | Kategorie | Priorität | Requirement | Verifikation |
|---|---:|---|---|---|---|
| REQ-NF-001 | 1 | Determinismus | Muss | Bei identischen Eingabewerten muss das System identische Ausgabewerte erzeugen. | Wiederholungstest |
| REQ-NF-002 | 1 | Testbarkeit | Muss | Für jedes funktionale Requirement muss das Projekt mindestens einen zugeordneten Testfall definieren. | Traceability-Review |
| REQ-NF-003 | 1 | Robustheit | Muss | Bei einer ungültigen Nutzereingabe muss das System einen unkontrollierten Programmabbruch verhindern. | Negativtest |
| REQ-NF-004 | 1 | Konfigurationsmanagement | Muss | Am Ende jeder Iteration muss das Projekt eine Baseline mit Softwarestand, Requirements, Testfällen und Testergebnissen erzeugen. | Baseline-Review |
| REQ-NF-005 | 2 | Performance | Muss | Bei Durchführung einer Simulationsberechnung muss das System das Simulationsergebnis innerhalb von höchstens 5 Sekunde bereitstellen. | Laufzeittest |
| REQ-NF-006 | 2 | Ausgabequalität | Muss | Bei Ausgabe numerischer Simulationswerte muss das System Wert und Einheit anzeigen. | Funktionstest |
| REQ-NF-007 | 3 | Änderbarkeit | Muss | Bei Ergänzung eines weiteren Leistungsmodus oder Lastszenarios muss das Projekt die eindeutige Traceability zwischen Requirement, Iteration und Testfall erhalten. | Traceability-Review |
---

## 9. Requirement-Baseline je Iteration

| Iteration | Funktionale Requirements | Nicht-funktionale Requirements | Teilfunktionalitäten | Ziel der Iteration |
|---|---|---|---|---|
| Iteration 1 | REQ-F-001 bis REQ-F-012 | REQ-NF-001 bis REQ-NF-004 | TF-01, TF-02, TF-03 | Grundsimulation mit Leistungsmodus, Lastszenario und CPU-Taktfrequenz. |
| Iteration 2 | REQ-F-013 bis REQ-F-023 | REQ-NF-005, REQ-NF-006 | TF-04, TF-06, erste Version von TF-07 | Erweiterung um Kühlung, Temperatur, Stromverbrauch und erste Ereignisprotokollierung. |
| Iteration 3 | REQ-F-024 bis REQ-F-033 | REQ-NF-007 | TF-05, vollständige TF-07 | Erweiterung um Thermal Throttling und vollständiges Ereignisprotokoll. |

---

## 10. Zuordnung der funktionalen Requirements zu Teilfunktionalitäten

| Teilfunktionalität | Requirements |
|---|---|
| TF-01 | REQ-F-001, REQ-F-002, REQ-F-003, REQ-F-004 |
| TF-02 | REQ-F-005, REQ-F-006, REQ-F-007, REQ-F-008 |
| TF-03 | REQ-F-009, REQ-F-010, REQ-F-011, REQ-F-012 |
| TF-04 | REQ-F-013, REQ-F-014, REQ-F-015, REQ-F-016 |
| TF-05 | REQ-F-024, REQ-F-025, REQ-F-026, REQ-F-027, REQ-F-028, REQ-F-029 |
| TF-06 | REQ-F-017, REQ-F-018, REQ-F-019 |
| TF-07 | REQ-F-020, REQ-F-021, REQ-F-022, REQ-F-023, REQ-F-030, REQ-F-031, REQ-F-032, REQ-F-033 |

---

## 11. Dokumentation der Requirements

Die Anforderungsdokumentation besteht aus folgenden Dateien:

| Datei | Zweck |
|---|---|
| `Projekt/Requirements/Requirements.md` | Detaillierte funktionale und nicht-funktionale Requirements. |
| `Projekt/Requirements/Lastenheft.md` | Kundensicht auf Ziele, Umfang und Anforderungen. |
| `Projekt/Requirements/Pflichtenheft.md` | Entwicklungssicht mit konkretisierten Anforderungen und Spezifikationen. |