# Iteration 1 – Test Reporting

## 1. Zweck des Test Reports

Dieses Dokument fasst die Ausführung der automatisierten Unit- und Integrationstests für Iteration 1 zusammen.

Die Testausführung basiert auf der Datei:

```text
Iteration 1/Link to Test cases/Iteration1UnitTestRunner.java
```

Die zugrunde liegenden Testfälle stammen aus:

```text
Iteration 1/Link to Test cases/Iteration1_Testfaelle.md
```

---

## 2. Testumfang

Automatisiert geprüft wurden die Testfälle `TST-I1-001` bis `TST-I1-019`.

| Bereich | Status |
|---|---|
| Leistungsmodusverwaltung | getestet |
| Benutzerdefinierter Leistungsmodus | getestet |
| Lastszenarioverwaltung | getestet |
| CPU-Auslastungszuordnung | getestet |
| CPU-Taktberechnung | getestet |
| Taktbegrenzung | getestet |
| Simulationsergebnis | getestet |
| Deterministisches Verhalten | getestet |
| Robustheit bei ungültigen Eingaben | getestet |
| Konsolenlauf aus Benutzersicht | nicht automatisiert, manuell auszuführen |

Der Testfall `TST-I1-020` wird nicht als Unit-Test ausgeführt, weil er die interaktive Konsolenbedienung über `UserInterface` und `Main.java` betrifft. Dieser Test bleibt als manuelle Validierung vorgesehen.

---

## 3. Testumgebung

| Eigenschaft | Wert |
|---|---|
| Programmiersprache | Java |
| Testart | ausführbare Unit-Tests ohne externes Testframework |
| Testdatei | `Iteration1UnitTestRunner.java` |
| Ausführung | `javac` und `java` |
| Getestete Komponenten | `PerformanceModeManager`, `LoadScenarioManager`, `ClockCalculator`, `PerformanceController`, `SimulationResult`, `SimulationInput` |
| Nicht automatisiert getestet | interaktive Konsoleneingabe über `UserInterface.start()` |

---

## 4. Ausführungsbefehl

Die Tests wurden technisch mit folgendem Ablauf geprüft:

In IntelliJ kann die Datei `Iteration1UnitTestRunner.java` direkt über `Run` ausgeführt werden.

---

## 5. Zusammenfassung der Unit-Test-Ausführung

| Kennzahl | Ergebnis |
|---|---:|
| Automatisierte Testfälle | 19 |
| Ausgeführt | 19 |
| Bestanden | 19 |
| Fehlgeschlagen | 0 |
| Nicht automatisiert | 1 |
| Offene Fehler aus Unit-Tests | 0 |

**Unit-Test-Status:** bestanden

---

## 6. Konsolenausgabe der Testausführung

```text
passed=19
failed=0
TST-I1-001;BESTANDEN;
TST-I1-002;BESTANDEN;
TST-I1-003;BESTANDEN;
TST-I1-004;BESTANDEN;
TST-I1-005;BESTANDEN;
TST-I1-006;BESTANDEN;
TST-I1-007;BESTANDEN;
TST-I1-008;BESTANDEN;
TST-I1-009;BESTANDEN;
TST-I1-010;BESTANDEN;
TST-I1-011;BESTANDEN;
TST-I1-012;BESTANDEN;
TST-I1-013;BESTANDEN;
TST-I1-014;BESTANDEN;
TST-I1-015;BESTANDEN;
TST-I1-016;BESTANDEN;
TST-I1-017;BESTANDEN;
TST-I1-018;BESTANDEN;
TST-I1-019;BESTANDEN;
```

---

## 7. Konsolenausgabe der Systemausführung

```text
java -cp "out/production/Iteration 1" Main
Laptop-Prozessorleistungsmodul – Iteration 1
------------------------------------------------

Verfügbare Leistungsmodi:
- ENERGY_SAVE (Energiesparen, max. 2,0 GHz, 20 W)
- BALANCED (Ausgeglichen, max. 3,2 GHz, 40 W)
- PERFORMANCE (Höchstleistung, max. 4,2 GHz, 65 W)
- CUSTOM (Benutzerdefiniert)
Leistungsmodus eingeben: balanced

Verfügbare Lastszenarien:
- IDLE (Leerlauf, 15 % CPU-Auslastung)
- OFFICE (Office, 35 % CPU-Auslastung)
- MULTIMEDIA (Multimedia, 55 % CPU-Auslastung)
- GAMING (Gaming, 85 % CPU-Auslastung)
- STRESS_TEST (Stresstest, 100 % CPU-Auslastung)
Lastszenario eingeben: office

Simulationsergebnis
------------------------------------------------
Leistungsmodus: Ausgeglichen
Lastszenario: Office
CPU-Auslastung: 35 %
Ungedrosselte CPU-Taktfrequenz: 2,0 GHz
Zusammenfassung: Modus Ausgeglichen mit Szenario Office ergibt 35 % CPU-Auslastung und 2,0 GHz CPU-Takt.

Weitere Simulation ausführen? (j/n): 
```

## 8. Ergebnisübersicht nach Testfall

| Testfall-ID | Kurzbeschreibung | Ergebnis |
|---|---|---|
| TST-I1-001 | Leistungsmodi werden bereitgestellt | bestanden |
| TST-I1-002 | Ausgewählter fester Leistungsmodus wird übernommen | bestanden |
| TST-I1-003 | Benutzerdefinierter Leistungsmodus mit gültigen Werten wird angenommen | bestanden |
| TST-I1-004 | Benutzerdefinierter Leistungsmodus mit zu hohem Taktwert wird abgelehnt | bestanden |
| TST-I1-005 | Benutzerdefinierter Leistungsmodus mit zu niedrigem Power-Limit wird abgelehnt | bestanden |
| TST-I1-006 | Lastszenarien werden bereitgestellt | bestanden |
| TST-I1-007 | CPU-Auslastung wird dem Lastszenario zugeordnet | bestanden |
| TST-I1-008 | Ausgewähltes Lastszenario wird übernommen | bestanden |
| TST-I1-009 | CPU-Auslastung wird im Ergebnis ausgegeben | bestanden |
| TST-I1-010 | Ungedrosselte CPU-Taktfrequenz wird bestimmt | bestanden |
| TST-I1-011 | CPU-Taktfrequenz wird auf Minimalwert begrenzt | bestanden |
| TST-I1-012 | CPU-Taktfrequenz wird auf Maximalwert begrenzt | bestanden |
| TST-I1-013 | CPU-Taktfrequenz wird mit Einheit GHz ausgegeben | bestanden |
| TST-I1-014 | CPU-Taktfrequenz wird bei geänderter Eingabe neu bestimmt | bestanden |
| TST-I1-015 | Gleiche Eingaben erzeugen gleiche Ausgaben | bestanden |
| TST-I1-016 | Serviceklassen sind getrennt testbar | bestanden |
| TST-I1-017 | Ungültiger Leistungsmodus wird kontrolliert abgelehnt | bestanden |
| TST-I1-018 | Ungültiges Lastszenario wird kontrolliert abgelehnt | bestanden |
| TST-I1-019 | Leerer Leistungsmodus wird kontrolliert abgelehnt | bestanden |
| TST-I1-020 | Vollständiger Konsolenlauf aus Benutzersicht | offen / manuell auszuführen |

---

## 9. Requirement-Abdeckung durch Unit-Tests

| Requirement-ID | Testfälle | Status |
|---|---|---|
| REQ-F-001 | TST-I1-001 | bestanden |
| REQ-F-002 | TST-I1-002 | bestanden |
| REQ-F-003 | TST-I1-003 | bestanden |
| REQ-F-004 | TST-I1-004, TST-I1-005 | bestanden |
| REQ-F-005 | TST-I1-006 | bestanden |
| REQ-F-006 | TST-I1-007 | bestanden |
| REQ-F-007 | TST-I1-008 | bestanden |
| REQ-F-008 | TST-I1-009 | bestanden |
| REQ-F-009 | TST-I1-010, TST-I1-014 | bestanden |
| REQ-F-010 | TST-I1-011, TST-I1-012 | bestanden |
| REQ-F-011 | TST-I1-013 | bestanden |
| REQ-F-012 | TST-I1-014 | bestanden |
| REQ-NF-001 | TST-I1-015 | bestanden |
| REQ-NF-002 | TST-I1-016 | bestanden |
| REQ-NF-003 | TST-I1-017, TST-I1-018, TST-I1-019 | bestanden |
| REQ-NF-004 | Baseline-Erstellung | offen bis Git-Baseline |

---

## 10. Bewertung

Die automatisierten Tests zeigen, dass die fachlichen Kernfunktionen der Iteration 1 funktionieren:

- Leistungsmodi werden korrekt bereitgestellt und übernommen.
- Benutzerdefinierte Leistungsmodi werden innerhalb der Grenzwerte akzeptiert.
- Ungültige benutzerdefinierte Werte werden abgelehnt.
- Lastszenarien liefern die erwartete CPU-Auslastung.
- Die CPU-Taktfrequenz wird korrekt berechnet.
- Die Taktfrequenz wird auf 1,2 GHz bis 4,2 GHz begrenzt.
- Geänderte Eingaben führen zu einer Neuberechnung.
- Gleiche Eingaben führen zu gleichen Ausgaben.
- Ungültige Eingaben führen zu kontrollierten Exceptions.

---

## 11. Fazit

Die automatisierten Unit- und Integrationstests der Iteration 1 wurden erfolgreich ausgeführt.

**Unit-Test-Status:** bestanden  
**Fehlgeschlagene Unit-Tests:** 0  