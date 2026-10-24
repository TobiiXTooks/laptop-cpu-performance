# Architektur und Architekturstil

**Version:** 0.2    

---

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die Architekturziele und den gewählten Architekturstil für das **Laptop-Prozessorleistungsmodul**.

Das Dokument baut auf dem Systemkontext auf und legt fest:

- welche Architekturziele aus dem Systemkontext abgeleitet werden,
- welcher Architekturstil für das Softwaremodul verwendet wird,
- warum dieser Architekturstil für die Teilfunktion geeignet ist,
- welche Schichten und Verantwortlichkeiten vorgesehen sind,
- welche Architekturregeln für die weitere Ausarbeitung gelten,
- welche Konsequenzen sich für Komponenten, Schnittstellen, Datenmodelle und Tests ergeben.

Dieses Dokument beschreibt die **Grobarchitektur**. Klassen, Attribute, Methoden, konkrete Funktionssignaturen und UML-Detaildiagramme werden im späteren Feindesign beschrieben.

---

## 2. Ausgangspunkt aus dem Systemkontext

Das Laptop-Prozessorleistungsmodul verarbeitet leistungsrelevante Prozessorwerte und stellt daraus Ergebniswerte zur Prozessorleistung bereit.

Aus dem Systemkontext ergeben sich folgende zentrale Anforderungen an die Architektur:

| ID | Ausgangspunkt | Bedeutung für die Architektur |
|---|---|---|
| AK-001 | Das Modul ist auf die Teilfunktion **Laptop – Prozessorleistung** begrenzt. | Die Architektur muss fachlich klar abgegrenzt sein. |
| AK-002 | Eingabe, Verarbeitung und Ausgabe sind getrennte Aufgaben. | Die Architektur soll diese Verantwortlichkeiten in getrennte Bereiche aufteilen. |
| AK-003 | Prozessorwerte werden strukturiert verarbeitet. | Es werden stabile Datenmodelle benötigt. |
| AK-004 | Die Verarbeitung muss nachvollziehbar und testbar sein. | Fachlogik muss in eigenständige Komponenten zerlegt werden. |
| AK-005 | Die Umsetzung erfolgt über drei Iterationen. | Die Architektur muss schrittweise erweiterbar sein. |
| AK-006 | Ergebnisse und Verarbeitungsschritte sollen später protokolliert werden. | Eine Protokollierungskomponente muss vorbereitet werden. |
| AK-007 | Die Verarbeitung muss nachvollziehbar und testbar sein. | Fachlogik muss in eigenständige Komponenten zerlegt werden. |
| AK-008 | Requirements müssen bis zu Code und Tests nachverfolgbar sein. | Komponenten müssen eindeutig Requirements und Tests zugeordnet werden können. |

---

## 3. Architekturziele

| Ziel-ID | Architekturziel | Beschreibung |
|---|---|---|
| AZ-001 | Verständlichkeit | Die Struktur des Softwaremoduls soll so aufgebaut sein, dass Komponenten und Zuständigkeiten eindeutig erkennbar sind. |
| AZ-002 | Erweiterbarkeit | Neue Verarbeitungsschritte, z. B. Temperaturbewertung oder Leistungsaufnahmebewertung, sollen ohne grundlegende Umstrukturierung ergänzt werden können. |
| AZ-003 | Testbarkeit | Jede fachliche Komponente soll separat durch Modultests prüfbar sein. |
| AZ-004 | Nachvollziehbarkeit | Die Zuordnung von Requirements zu Komponenten, Code-Dateien und Tests soll über die Traceability Matrix möglich sein. |
| AZ-005 | Geringe Kopplung | Komponenten sollen nur über definierte Schnittstellen miteinander kommunizieren. |
| AZ-006 | Hohe Kohäsion | Jede Komponente soll genau eine klar abgegrenzte fachliche Aufgabe erfüllen. |
| AZ-007 | Iterative Ausbaufähigkeit | Die Architektur soll in Iteration 1 lauffähig sein und in Iteration 2 und 3 erweitert werden können. |
| AZ-008 | Fachliche Abgrenzung | Das Modul darf nur die Teilfunktion **Prozessorleistung** behandeln. Andere Laptop-Funktionen bleiben außerhalb der Systemgrenze. |
| AZ-009 | Determinismus | Gleiche Eingabewerte müssen bei gleicher Konfiguration gleiche Ergebniswerte erzeugen. |

---

## 4. Gewählter Architekturstil

Für das Laptop-Prozessorleistungsmodul wird eine **modulare Schichtenarchitektur** verwendet.

Die Architektur kombiniert zwei Prinzipien:

1. **Schichtenarchitektur**  
   Das System wird in übereinanderliegende Verantwortungsbereiche aufgeteilt.

2. **Modulare Architektur**  
   Die Fachlogik wird in klar getrennte Komponenten zerlegt.

---

## 5. Begründung der Architekturwahl

Die modulare Schichtenarchitektur ist für dieses Projekt geeignet, weil das System einen klaren Verarbeitungsablauf besitzt:

1. Eingaben aufnehmen,
2. Eingaben validieren und strukturieren,
3. Simulationszustand erzeugen,
4. Prozessorzustand verarbeiten,
5. Temperatur und Throttling bewerten,
6. Ergebniswerte berechnen oder bewerten,
7. Ergebnis anzeigen,
8. Verarbeitung protokollieren.

Diese Aufgaben lassen sich sinnvoll in Schichten trennen. Gleichzeitig können einzelne fachliche Aufgaben, z. B. Leistungsmodusverwaltung, Lastszenarioverwaltung, Taktbewertung, Temperaturbewertung und Leistungsaufnahmebewertung, als eigene Komponenten modelliert werden.

Damit unterstützt der Architekturstil besonders:

- klare Zuständigkeiten,
- leichte Erweiterbarkeit über mehrere Iterationen,
- gezielte Modultests,
- nachvollziehbare Traceability,
- verständliche Dokumentation für Architektur und Feindesign.

---

## 6. Vorgesehene Schichten

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

## 7. Schichtenbeschreibung

| Schicht | Aufgabe | Typische Bestandteile |
|---|---|---|
| Benutzerschnittstelle | Nimmt Eingaben entgegen und zeigt Ergebnisse an. | `UserInterface` |
| Anwendungssteuerung | Koordiniert den vollständigen Verarbeitungsablauf. | `PerformanceController` |
| Fachlogik / Services | Führt die fachliche Bewertung der Prozessorleistung durch. | `PerformanceModeManager`, `LoadScenarioManager`, `ClockCalculator`, `TemperatureSimulator`, `ThermalThrottlingController`, `PowerConsumptionCalculator` |
| Modelle und Datenobjekte | Stellt strukturierte Daten für Eingaben, Zwischenwerte und Ergebnisse bereit. | `ProcessorState`, `PerformanceMode`, `LoadScenario`, `SimulationResult` |
| Protokollierung / Nachweisführung | Speichert relevante Eingaben, Verarbeitungsschritte und Ergebniswerte. | `EventLogger`, `LogEntry` |

---

## 8. Architekturregeln

| Regel-ID | Architekturregel | Begründung |
|---|---|---|
| AR-001 | Die Benutzerschnittstelle darf keine fachliche Bewertungslogik enthalten. | Fachlogik bleibt testbar und austauschbar. |
| AR-002 | Der `PerformanceController` koordiniert den Ablauf, berechnet aber keine Detailbewertungen selbst. | Ablaufsteuerung und Fachlogik bleiben getrennt. |
| AR-003 | Fachkomponenten greifen nur auf definierte Datenmodelle zu. | Daten bleiben konsistent und nachvollziehbar. |
| AR-004 | Jede Fachkomponente besitzt eine klar abgegrenzte Aufgabe. | Komponenten bleiben verständlich und einzeln testbar. |
| AR-005 | Ergebniswerte werden über ein strukturiertes Ergebnisobjekt zurückgegeben. | Ausgabe, Tests und Traceability können auf dieselbe Ergebnisstruktur zugreifen. |
| AR-006 | Neue Iterationen erweitern bestehende Komponenten oder ergänzen neue Komponenten, ohne bestehende Verantwortlichkeiten zu vermischen. | Die Architektur bleibt über mehrere Iterationen stabil. |
| AR-007 | Protokollierung wird als eigene Komponente behandelt. | Nachweisführung wird nicht mit Fachlogik vermischt. |
| AR-008 | Gleiche Eingaben müssen gleiche Simulationsergebnisse erzeugen. | Modultests und Testergebnisse bleiben reproduzierbar. |

---

## 9. Nicht gewählte Architekturstile

| Architekturstil | Entscheidung | Begründung |
|---|---|---|
| Monolith ohne Schichtung | Nicht gewählt | Die Trennung von Eingabe, Steuerung, Fachlogik und Datenmodellen wäre unklar. |
| Client-Server-Architektur | Nicht gewählt | Für das Modul ist keine verteilte Anwendung erforderlich. |
| Microservices | Nicht gewählt | Der Projektumfang ist zu klein; der organisatorische und technische Aufwand wäre unangemessen. |
| Ereignisgetriebene Architektur | Nicht als Hauptarchitektur gewählt | Für den Kernablauf ist ein linearer Verarbeitungsfluss ausreichend. Ereignisse können später für Protokollierung genutzt werden. |
| Reines Datenbankmodell | Nicht gewählt | Persistente Datenspeicherung steht nicht im Mittelpunkt der Teilfunktion. |

---

## 10. Erste Architekturentscheidung

### Verwendung einer modularen Schichtenarchitektur

| Feld | Beschreibung |
|---|---|
| Status | Akzeptiert |
| Kontext | Das Laptop-Prozessorleistungsmodul verarbeitet Eingaben, Prozessorwerte und Ergebnisbewertungen. Die Umsetzung erfolgt über drei Iterationen. |
| Entscheidung | Das Modul wird als modulare Schichtenarchitektur aufgebaut. |
| Begründung | Die Aufgaben Eingabe, Ablaufsteuerung, Fachlogik, Datenmodellierung und Protokollierung können dadurch klar getrennt werden. |
| Konsequenz | Komponenten lassen sich einzeln entwickeln, testen, erweitern und in der Traceability Matrix nachverfolgen. |

---

## 11. Auswirkungen auf Komponenten

| Komponenten-ID | Komponente | Schicht | Hauptaufgabe |
|---|---|---|---|
| CMP-001 | `PerformanceModeManager` | Fachlogik / Services | Verwaltung und Bewertung des Leistungsmodus |
| CMP-002 | `LoadScenarioManager` | Fachlogik / Services | Verwaltung und Bewertung des Lastszenarios |
| CMP-003 | `ClockCalculator` | Fachlogik / Services | Bewertung der CPU-Taktfrequenz |
| CMP-004 | `TemperatureCalculator` | Fachlogik / Services | Bewertung der CPU-Temperatur |
| CMP-005 | `ThermalThrottlingController` | Fachlogik / Services | Bewertung des Thermal-Throttling-Status |
| CMP-006 | `PowerConsumptionCalculator` | Fachlogik / Services | Bewertung der Leistungsaufnahme |
| CMP-007 | `EventLogger` | Protokollierung / Nachweisführung | Protokollierung von Eingaben, Verarbeitungsschritten und Ergebnissen |
| CMP-008 | `PerformanceController` | Anwendungssteuerung | Koordination des vollständigen Verarbeitungsablaufs |
| CMP-009 | `UserInterface` | Benutzerschnittstelle | Eingabe und Ergebnisanzeige |

---

## 12. Auswirkungen auf Datenmodelle

| Datenmodell | Zweck |
|---|---|
| `PerformanceMode` | Beschreibt den ausgewählten Leistungsmodus. |
| `LoadScenario` | Beschreibt das ausgewählte Lastszenario. |
| `ProcessorState` | Bündelt leistungsrelevante Prozessorwerte. |
| `PerformanceInput` | Bündelt alle Eingaben. |
| `SimulationResult` | Bündelt die Ergebniswerte zur Prozessorleistung. |
| `LogEntry` | Beschreibt einen protokollierten Verarbeitungsschritt. |

---

## 13. Iterationsbezug

| Iteration | Architekturumfang | Ergebnis der Iteration |
|---:|---|---|
| 1 | Grundstruktur mit `UserInterface`, `PerformanceController`, `PerformanceModeManager`, `LoadScenarioManager`, `ClockCalculator` und Grunddatenmodellen | Lauffähige Software zur Auswahl von Leistungsmodus und Lastszenario sowie zur Berechnung einer simulierten CPU-Taktfrequenz |
| 2 | Erweiterung um `TemperatureSimulator`, `PowerConsumptionCalculator`, erste Protokollierung mit `EventLogger` und Erweiterung der Datenmodelle | Lauffähige Software mit Temperaturberechnung, Stromverbrauch und ersten Ereignissen |
| 3 | Erweiterung um `ThermalThrottlingController` und vollständige Protokollierung | Lauffähige Software mit Thermal Throttling, effektiver Taktfrequenz und vollständigem Ereignisprotokoll |

---

## 14. Qualitätsbezug

| Qualitätsmerkmal | Umsetzung in der Architektur |
|---|---|
| Wartbarkeit | Fachliche Aufgaben werden in einzelne Komponenten getrennt. |
| Testbarkeit | Komponenten besitzen klare Eingaben und Ausgaben. |
| Erweiterbarkeit | Neue Bewertungsfunktionen können als zusätzliche Services ergänzt werden. |
| Nachvollziehbarkeit | Komponenten, Datenmodelle und Tests können Requirements zugeordnet werden. |
| Verständlichkeit | Schichten und Komponenten folgen einem einheitlichen Strukturprinzip. |
| Robustheit | Eingaben werden strukturiert an die Fachlogik übergeben und können zentral validiert werden. |
| Reproduzierbarkeit | Deterministische Berechnungen liefern bei gleicher Eingabe gleiche Ergebnisse. |

---

## 15. Auswirkungen auf Tests und Traceability

Die Architektur unterstützt die spätere Test- und Traceability-Dokumentation durch eindeutige Zuordnung von Requirements zu Komponenten.

| Artefakt | Architekturbezug |
|---|---|
| Requirements | Jedes funktionale Requirement wird einer oder mehreren Komponenten zugeordnet. |
| Code-Dateien | Jede Komponente erhält im späteren `src`-Verzeichnis eine passende Implementierung oder ein klar zugeordnetes Modul. |
| Modultests | Jede Fachkomponente kann separat getestet werden. |
| Integrationstests | Der Ablauf über `UserInterface`, `PerformanceController` und Fachkomponenten kann gemeinsam geprüft werden. |
| Traceability Matrix | Requirement, Teilfunktionalität, Komponente, Code-Datei, Testfall und Iteration können tabellarisch verbunden werden. |
