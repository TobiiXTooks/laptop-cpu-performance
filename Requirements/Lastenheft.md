# Lastenheft

**Status:** Entwurf  
**Version:** 0.1  
**Datum:** 2026-05-07  

---

## 1. Ziel des Lastenhefts

Dieses Lastenheft beschreibt die Anforderungen aus Kundensicht an eine Software-Simulation der Prozessorleistung eines Laptops.

Das Dokument beschreibt das erwartete Systemverhalten, ohne eine konkrete technische Umsetzung vorzugeben.

---

## 2. Ausgangssituation

Der Schwerpunkt liegt auf der Simulation von Leistungsmodus, CPU-Auslastung, CPU-Taktfrequenz, CPU-Temperatur, Thermal Throttling, Stromverbrauch und Ereignisprotokoll.

---

## 3. Zielsystem

Das Zielsystem ist eine lauffähige Software, mit der ein Nutzer die Prozessorleistung eines Laptops simulieren kann.

Der Nutzer soll einen Leistungsmodus, ein Lastszenario und eine Kühlungsstufe auswählen können. Das System soll daraus nachvollziehbare simulierte Werte berechnen und anzeigen.

---

## 4. Stakeholder

| Stakeholder | Interesse |
|---|---|
| Nutzer | Bedienung der Simulation und Anzeige verständlicher Simulationswerte. |
| Entwickler | Eindeutige Anforderungen als Grundlage für Architektur, Design und Implementierung. |
| Tester | Prüfbarkeit der Anforderungen durch Testfälle. |

---

## 5. Funktionale Kundenanforderungen

| ID | Priorität | Kundenanforderung |
|---|---|---|
| LH-F-001 | Muss | Das System muss Leistungsmodi für unterschiedliche simulierte Prozessorleistungen bereitstellen. |
| LH-F-002 | Muss | Das System muss einen benutzerdefinierten Leistungsmodus ermöglichen. |
| LH-F-003 | Muss | Das System muss vordefinierte Lastszenarien für typische Nutzungssituationen bereitstellen. |
| LH-F-004 | Muss | Das System muss aus dem ausgewählten Leistungsmodus und Lastszenario eine CPU-Auslastung und eine CPU-Taktfrequenz ableiten. |
| LH-F-005 | Muss | Das System muss eine Kühlungsstufe berücksichtigen. |
| LH-F-006 | Muss | Das System muss eine CPU-Temperatur simulieren. |
| LH-F-007 | Muss | Das System muss bei erhöhter CPU-Temperatur eine Throttling-Stufe bestimmen. |
| LH-F-008 | Muss | Das System muss bei Thermal Throttling eine reduzierte CPU-Taktfrequenz anzeigen. |
| LH-F-009 | Muss | Das System muss einen simulierten Stromverbrauch in Watt ausgeben. |
| LH-F-010 | Muss | Das System muss relevante Ereignisse während der Simulation protokollieren. |
| LH-F-011 | Muss | Das System muss das Ereignisprotokoll chronologisch bereitstellen. |

---

## 6. Nicht-funktionale Kundenanforderungen

| ID | Priorität | Kundenanforderung |
|---|---|---|
| LH-NF-001 | Muss | Das System muss alle Hardwarewerte simulieren. |
| LH-NF-002 | Muss | Das System muss bei identischen Eingaben identische Ergebnisse liefern. |
| LH-NF-003 | Muss | Das System muss ungültige Eingaben kontrolliert behandeln. |
| LH-NF-004 | Muss | Das System muss numerische Werte mit Einheit anzeigen. |
| LH-NF-005 | Muss | Das Projekt muss die Anforderungen bis zu den Testfällen nachverfolgbar dokumentieren. |
| LH-NF-006 | Muss | Das Projekt muss nach jeder Iteration eine lauffähige Softwareversion bereitstellen. |

---

## 7. Abgrenzung

Das Zielsystem muss keine echten Sensorwerte auslesen. Das Zielsystem muss keine reale CPU-Leistung verändern. Das Zielsystem muss keine Benchmarks mit realen Prozessoren durchführen.

---

## 8. Iterativer Lieferumfang

| Iteration | Lieferumfang |
|---|---|
| Iteration 1 | Auswahl von Leistungsmodus und Lastszenario sowie Berechnung der CPU-Taktfrequenz. |
| Iteration 2 | Ergänzung von Kühlungsstufe, CPU-Temperatur, Stromverbrauch und einfachen Ereignissen. |
| Iteration 3 | Ergänzung von Thermal Throttling und vollständigem Ereignisprotokoll. |

---

## 9. Abnahmekriterien aus Kundensicht

| Kriterium | Beschreibung |
|---|---|
| Lauffähigkeit | Nach jeder Iteration ist eine lauffähige Softwareversion vorhanden. |
| Verständlichkeit | Der Nutzer kann die Simulationsergebnisse anhand von Bezeichnungen und Einheiten verstehen. |
| Nachvollziehbarkeit | Die simulierten Werte beruhen auf dokumentierten Eingaben, Wertebereichen und Zuständen. |
| Prüfbarkeit | Für jede Anforderung kann ein Testfall oder ein Reviewkriterium abgeleitet werden. |
| Traceability | Anforderungen, Iterationen und Testfälle sind eindeutig verknüpfbar. |

---

## 10. Verweis auf detaillierte Requirements

Die detaillierten Requirements sind in `Projekt/Requirements/Requirements.md` dokumentiert.

---
