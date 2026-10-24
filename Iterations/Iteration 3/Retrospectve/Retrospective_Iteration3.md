# Retrospective – Iteration 3

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt die Retrospective der Iteration 3 des Projekts **Laptop – Prozessorleistung**.

Die Retrospective bewertet den Ablauf, die Ergebnisse, die umgesetzten Anforderungen, die Qualität der Implementierung und die Testergebnisse der dritten Iteration.

Ziel ist es, festzuhalten:

* was in Iteration 3 umgesetzt wurde,
* welche Ziele erreicht wurden,
* welche Probleme aufgetreten sind,
* welche Entscheidungen getroffen wurden,
* welche Verbesserungen gegenüber Iteration 2 erreicht wurden,
* welche offenen Punkte vor der finalen Baseline noch zu erledigen sind.

---

## 2. Ausgangssituation vor Iteration 3

Am Ende von Iteration 2 war die Software bereits lauffähig und enthielt folgende Funktionen:

* Auswahl eines Leistungsmodus,
* Auswahl eines Lastszenarios,
* Berechnung der ungedrosselten CPU-Taktfrequenz,
* Auswahl einer Kühlungsstufe,
* Berechnung der CPU-Temperatur,
* Berechnung des Stromverbrauchs,
* einfache Ereignisprotokollierung,
* Ausgabe eines Simulationsergebnisses.

Thermal Throttling war in Iteration 2 fachlich bereits vorgesehen, aber noch nicht vollständig umgesetzt. Insbesondere war `REQ-F-019` noch offen, da der Stromverbrauch erst nach einer vollständigen Throttling-Bewertung korrekt neu berechnet werden konnte.

Iteration 3 hatte daher den Schwerpunkt, die vorbereitete Funktionalität vollständig abzuschließen.

---

## 3. Zielsetzung der Iteration 3

Die Zielsetzung der Iteration 3 bestand darin, den finalen funktionalen Stand der Software zu erreichen.

Die wichtigsten Ziele waren:

| Ziel-ID  | Ziel                                                                     | Status   |
| -------- | ------------------------------------------------------------------------ | -------- |
| I3-Z-001 | Thermal Throttling vollständig implementieren                            | erreicht |
| I3-Z-002 | Throttling-Stufen `Normal`, `Warnung`, `Reduziert`, `Kritisch` einführen | erreicht |
| I3-Z-003 | Effektive CPU-Taktfrequenz nach Throttling berechnen                     | erreicht |
| I3-Z-004 | Stromverbrauch mit effektiver CPU-Taktfrequenz berechnen                 | erreicht |
| I3-Z-005 | Throttling-Stufe und effektiven Takt ausgeben                            | erreicht |
| I3-Z-006 | Ereignisprotokollierung um Throttling-Events erweitern                   | erreicht |
| I3-Z-007 | Reset-Funktion vorbereiten und testen                                    | erreicht |
| I3-Z-008 | Vererbung durch `SimulationOption` sinnvoll ergänzen                     | erreicht |
| I3-Z-009 | Interface `Displayable` für formatierte Ausgaben ergänzen                | erreicht |
| I3-Z-010 | Regression aus Iteration 1 und 2 absichern                               | erreicht |
| I3-Z-011 | Traceability Matrix auf Version 0.6 aktualisieren                        | erreicht |

---

## 4. Umgesetzte Anforderungen

In Iteration 3 wurden insbesondere folgende Requirements umgesetzt:

| Requirement | Inhalt                                                          | Status    |
| ----------- | --------------------------------------------------------------- | --------- |
| REQ-F-019   | Stromverbrauch nach Thermal Throttling neu bestimmen            | umgesetzt |
| REQ-F-024   | Throttling-Stufe anhand der Temperatur bestimmen                | umgesetzt |
| REQ-F-025   | Bei `Normal` und `Warnung` bleibt der Takt bei 100 %            | umgesetzt |
| REQ-F-026   | Bei `Reduziert` wird der Takt auf 80 % reduziert                | umgesetzt |
| REQ-F-027   | Bei `Kritisch` wird der Takt auf 60 % reduziert                 | umgesetzt |
| REQ-F-028   | Throttling-Stufe wird ausgegeben                                | umgesetzt |
| REQ-F-029   | Temperaturänderung führt zu neuer Throttling-Zuordnung          | umgesetzt |
| REQ-F-030   | `THROTTLING_AKTIVIERT` wird protokolliert                       | umgesetzt |
| REQ-F-031   | `THROTTLING_GEÄNDERT` wird protokolliert                        | umgesetzt |
| REQ-F-032   | Ereignisprotokoll wird chronologisch und laufbezogen ausgegeben | umgesetzt |
| REQ-F-033   | Simulation kann zurückgesetzt und protokolliert werden          | umgesetzt |

Zusätzlich wurden nicht-funktionale Anforderungen zu Determinismus, Testbarkeit, Ausgabequalität, Laufzeit und Änderbarkeit durch Tests nachgewiesen.

---

## 5. Umgesetzte technische Änderungen

### 5.1 Neue oder aktivierte Klassen

| Klasse                        | Zweck                                                              |
| ----------------------------- | ------------------------------------------------------------------ |
| `ThermalThrottlingController` | Bestimmt Throttling-Stufe und effektive CPU-Taktfrequenz.          |
| `ThrottlingState`             | Definiert die Stufen `NORMAL`, `WARNUNG`, `REDUZIERT`, `KRITISCH`. |
| `SimulationOption`            | Abstrakte Oberklasse für auswählbare Simulationsoptionen.          |
| `Displayable`                 | Interface für Klassen mit formatierter Textausgabe.                |

---

### 5.2 Erweiterte Klassen

| Klasse                       | Erweiterung                                                             |
| ---------------------------- | ----------------------------------------------------------------------- |
| `ProcessorState`             | Speichert zusätzlich Throttling-Stufe und effektive CPU-Taktfrequenz.   |
| `SimulationResult`           | Gibt Throttling-Stufe, effektiven Takt und aktuelle Ereignisse aus.     |
| `LogEntry`                   | Unterstützt erweiterte Ereignisse und implementiert `Displayable`.      |
| `PerformanceController`      | Koordiniert Thermal Throttling, effektiven Takt, Verbrauch und Logging. |
| `PowerConsumptionCalculator` | Berechnet Verbrauch auf Basis der effektiven CPU-Taktfrequenz.          |
| `EventLogger`                | Protokolliert Throttling-, Änderungs- und Reset-Ereignisse.             |
| `PerformanceMode`            | Erbt von `SimulationOption`.                                            |
| `LoadScenario`               | Erbt von `SimulationOption`.                                            |
| `CoolingLevel`               | Erbt von `SimulationOption`.                                            |
| `UserInterface`              | Ergebnisanzeige wurde um Iteration-3-Werte ergänzt.                     |
| `Main`                       | Instanziierung der neuen Throttling-Komponente ergänzt.                 |

---

## 6. Wichtige Designentscheidungen

### 6.1 Thermal Throttling als eigene Serviceklasse

Die Throttling-Logik wurde nicht direkt im `PerformanceController` umgesetzt, sondern in der eigenen Klasse `ThermalThrottlingController`.

**Begründung:**
Die Berechnung der Throttling-Stufe und der effektiven CPU-Taktfrequenz ist eine klar abgrenzbare Fachlogik. Durch die eigene Klasse bleibt der Controller übersichtlicher und die Logik ist separat testbar.

---

### 6.2 `ThrottlingState` als Enum

Die Throttling-Stufen wurden als Enum umgesetzt.

**Begründung:**
Die Stufen sind fest definiert und besitzen jeweils feste Temperaturbereiche und Taktfaktoren. Ein Enum verhindert ungültige Zustände und macht die Tests eindeutiger.

---

### 6.3 Trennung von ungedrosseltem und effektivem Takt

Der `ProcessorState` speichert sowohl die ungedrosselte CPU-Taktfrequenz als auch die effektive CPU-Taktfrequenz.

**Begründung:**
Dadurch ist nachvollziehbar, wie stark Thermal Throttling die Leistung reduziert. Außerdem können Ausgabe, Stromverbrauch und Tests beide Werte getrennt prüfen.

---

### 6.4 Verbrauch nach Throttling

Der Stromverbrauch wird erst nach der Throttling-Bewertung berechnet.

**Begründung:**
Damit wird `REQ-F-019` erfüllt. Der Stromverbrauch basiert nicht mehr ausschließlich auf dem ursprünglichen Takt, sondern auf dem tatsächlich wirksamen Takt nach Thermal Throttling.

---

### 6.5 `SimulationOption` als abstrakte Oberklasse

`PerformanceMode`, `LoadScenario` und `CoolingLevel` erben von `SimulationOption`.

**Begründung:**
Alle drei Klassen sind auswählbare Simulationsoptionen mit `key`, `name` und `getDisplayName()`. Die Oberklasse reduziert doppelte Strukturen und verbessert die Modellkonsistenz.

---

### 6.6 `Displayable` als Interface

`LogEntry` und `SimulationResult` implementieren `Displayable`.

**Begründung:**
Beide Klassen besitzen eine formatierte Textausgabe, sind fachlich aber unterschiedliche Objekte. Ein Interface beschreibt diese gemeinsame Fähigkeit ohne künstliche Vererbung.

---

### 6.7 Ereignisprotokoll pro Simulationslauf

Das Ergebnis eines Simulationslaufs enthält nur die Ereignisse des aktuellen Laufs.

**Begründung:**
Dadurch wird verhindert, dass Ereignisse früherer Simulationsläufe erneut im aktuellen `SimulationResult` angezeigt werden. Gleichzeitig bleibt das interne Gesamtprotokoll im `EventLogger` erhalten.

---

## 7. Testergebnis der Iteration 3

Die Iteration-3-Tests wurden mit `Iteration3UnitTestRunner.java` ausgeführt.

Gesamtergebnis:

```text
Bestanden: 56
Fehlgeschlagen: 0
Exit Code: 0
```

### 7.1 Testbereiche

| Testbereich                    | Anzahl / Bereich                | Ergebnis  |
| ------------------------------ | ------------------------------- | --------- |
| Thermal Throttling             | TC-THR-001 bis TC-THR-012       | bestanden |
| Effektive CPU-Taktfrequenz     | TC-ECLK-001 bis TC-ECLK-005     | bestanden |
| Stromverbrauch nach Throttling | TC-PWR-I3-001 bis TC-PWR-I3-004 | bestanden |
| Ereignisprotokollierung        | TC-LOG-I3-001 bis TC-LOG-I3-009 | bestanden |
| Integration                    | TC-INT-I3-001 bis TC-INT-I3-006 | bestanden |
| Objektorientierte Struktur     | TC-OO-I3-001 bis TC-OO-I3-005   | bestanden |
| Regression Iteration 1         | REG-I1-001 bis REG-I1-006       | bestanden |
| Regression Iteration 2         | REG-I2-001 bis REG-I2-005       | bestanden |
| Nicht-funktionale Tests        | TC-NF-I3-001 bis TC-NF-I3-004   | bestanden |

Die Testergebnisse zeigen, dass die neue Funktionalität korrekt umgesetzt wurde und bestehende Funktionen aus Iteration 1 und 2 weiterhin funktionieren.

---

## 8. Was gut funktioniert hat

| Bereich               | Bewertung                                                                            |
| --------------------- | ------------------------------------------------------------------------------------ |
| Iterative Erweiterung | Iteration 3 konnte sauber auf Iteration 1 und 2 aufbauen.                            |
| Architektur           | Die bestehende Komponentenstruktur musste nicht grundlegend geändert werden.         |
| Feindesign            | Die neuen Klassen konnten klar in das bestehende Design eingeordnet werden.          |
| Implementierung       | Thermal Throttling ließ sich modular ergänzen.                                       |
| Tests                 | Die automatisierten Tests decken neue Funktionalität, Integration und Regression ab. |
| Traceability          | Requirements, Code und Tests konnten eindeutig verknüpft werden.                     |
| Objektorientierung    | Oberklasse und Interface wurden sinnvoll ergänzt.                                    |
| Stabilität            | Alle 56 Tests wurden erfolgreich ausgeführt.                                         |

---

## 9. Probleme und Herausforderungen

| Problem                                                   | Auswirkung                                                                   | Lösung                                                                                                                 |
| --------------------------------------------------------- | ---------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Abgrenzung zwischen altem und aktuellem Ereignisprotokoll | Frühere Ereignisse wurden zunächst im aktuellen Ergebnis erneut angezeigt.   | `getEntriesFromIndex(int startIndex)` wurde ergänzt.                                                                   |
| Reset-Ereignis                                            | Reset-Ereignis wäre bei falscher Reihenfolge direkt wieder gelöscht worden.  | Reihenfolge wurde auf `clear()` vor `logSimulationReset()` festgelegt.                                                 |
| Klassenstruktur                                           | Zwischenzeitlich war unklar, welche Klassen sich für Vererbung eignen.       | Nur `PerformanceMode`, `LoadScenario` und `CoolingLevel` erben von `SimulationOption`.                                 |
| Interface-Nutzung                                         | `Displayable` war zunächst eher Designnachweis als technische Notwendigkeit. | `LogEntry` und `SimulationResult` implementieren das Interface konsistent.                                             |
| Testumfang                                                | Iteration 3 hatte viele neue Testbereiche.                                   | Testfälle wurden strukturiert nach Modultest, Integrationstest, Regressionstest und nicht-funktionalem Test gruppiert. |

---

## 10. Bewertung der Iteration

### 10.1 Zielerreichung

| Kriterium           | Bewertung                                                                       |
| ------------------- | ------------------------------------------------------------------------------- |
| Funktionaler Umfang | vollständig erreicht                                                            |
| Testabdeckung       | hoch                                                                            |
| Regression          | erfolgreich abgesichert                                                         |
| Traceability        | vollständig aktualisiert                                                        |
| Code-Struktur       | konsistent erweitert                                                            |
| Dokumentation       | Architektur, Feindesign, Testfälle, Testreporting und Traceability aktualisiert |
| Lauffähigkeit       | nachgewiesen                                                                    |

---

### 10.2 Qualität der Umsetzung

Die Qualität der Umsetzung wird als gut bewertet, weil:

* die neue Funktionalität modular ergänzt wurde,
* bestehende Klassen nicht unnötig verändert wurden,
* zentrale Berechnungen separat testbar sind,
* alle relevanten Requirements mit Tests verbunden sind,
* keine Regressionen aus Iteration 1 und 2 aufgetreten sind,
* die Software weiterhin ohne externe Bibliotheken lauffähig ist.

---

## 11. Vergleich zu Iteration 2

| Bereich            | Iteration 2                    | Iteration 3                                     |
| ------------------ | ------------------------------ | ----------------------------------------------- |
| Temperatur         | berechnet und ausgegeben       | weiterhin stabil                                |
| Stromverbrauch     | auf Basis des bisherigen Takts | auf Basis des effektiven Takts nach Throttling  |
| Thermal Throttling | vorbereitet                    | vollständig umgesetzt                           |
| Ereignisprotokoll  | einfache Ereignisse            | erweiterte Throttling- und Reset-Ereignisse     |
| Ergebnisanzeige    | Temperatur und Verbrauch       | zusätzlich Throttling-Stufe und effektiver Takt |
| Tests              | 21 bestanden                   | 56 bestanden                                    |
| Traceability       | v0.5                           | v0.6                                            |
| Status             | Iteration 2 abgeschlossen      | Iteration 3 funktional abgeschlossen            |

---

## 12. Offene Punkte nach Iteration 3

Fachlich und testseitig ist Iteration 3 abgeschlossen.

Vor der finalen Abgabe bleiben noch organisatorische Punkte:

| Offener Punkt  | Beschreibung                                                        |
| -------------- | ------------------------------------------------------------------- |
| Git-Commit     | Alle geänderten Dateien müssen committed werden.                    |
| Merge          | Iteration-3-Branch muss gegebenenfalls in `main` übernommen werden. |
| Baseline       | Für Iteration 3 muss ein Git-Tag gesetzt werden.                    |
| Finaler Review | Dokumentationsstruktur und Links im Projektordner prüfen.           |
| Abgabeprüfung  | Sicherstellen, dass alle erforderlichen Artefakte vorhanden sind.   |

---

## 13. Lessons Learned

| Erkenntnis                                               | Bedeutung für zukünftige Projekte                                                                                |
| -------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| Vorbereitete Architektur erleichtert spätere Erweiterung | `CMP-005` konnte in Iteration 3 aktiviert werden, ohne die Grundstruktur zu ändern.                              |
| Getrennte Fachlogik verbessert Testbarkeit               | `ThermalThrottlingController` konnte isoliert getestet werden.                                                   |
| Regressionstests sind wichtig                            | Änderungen an `ProcessorState`, `SimulationResult` und `EventLogger` hätten alte Funktionen beeinflussen können. |
| Ereignisprotokolle brauchen klare Abgrenzung             | Bei mehreren Simulationsläufen muss definiert sein, welche Ereignisse im aktuellen Ergebnis erscheinen.          |
| Traceability sollte iterativ gepflegt werden             | Die Aktualisierung auf v0.6 war möglich, weil v0.5 bereits strukturiert aufgebaut war.                           |
| Vererbung sollte sparsam eingesetzt werden               | Nur fachlich verwandte Klassen wurden in eine Vererbungshierarchie aufgenommen.                                  |
| Interfaces sind sinnvoll für gemeinsame Fähigkeiten      | `Displayable` beschreibt die gemeinsame Ausgabefähigkeit ohne falsche fachliche Vererbung.                       |

---

## 14. Fazit

Iteration 3 wurde erfolgreich abgeschlossen.

Die Software erfüllt nach Iteration 3 den geplanten finalen Funktionsumfang. Thermal Throttling ist vollständig implementiert, die effektive CPU-Taktfrequenz wird korrekt berechnet, der Stromverbrauch berücksichtigt den reduzierten Takt, und die Ereignisprotokollierung wurde erweitert.

Alle 56 automatisierten Tests wurden erfolgreich ausgeführt. Die Regressionstests zeigen, dass Funktionen aus Iteration 1 und 2 weiterhin stabil sind.

Die Traceability Matrix wurde auf Version 0.6 aktualisiert und verbindet die Iteration-3-Requirements mit konkreten Code-Modulen und Testfällen.

Damit ist Iteration 3 aus fachlicher, technischer und testbezogener Sicht abgeschlossen. Als nächster Schritt folgt die finale Baseline der Iteration 3.

---
