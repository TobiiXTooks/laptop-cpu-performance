# Pflichtenheft

**Status:** Entwurf  
**Version:** 0.1  
**Datum:** 2026-05-07  

---

## 1. Ziel des Pflichtenhefts

Dieses Pflichtenheft konkretisiert das Lastenheft für die Entwicklung der Software-Simulation der Laptop-Prozessorleistung.

Das Dokument beschreibt die geforderten Systemfunktionen, Ausgaben, Wertebereiche, Zustände und Prüfkriterien aus Entwicklungssicht.

---

## 2. Produktübersicht

Das Produkt simuliert keine reale Hardware, sondern berechnet definierte Simulationswerte.

Das Produkt verarbeitet folgende Nutzereingaben:

| Eingabe | Werte |
|---|---|
| Leistungsmodus | Energiesparen, Ausgeglichen, Höchstleistung, Benutzerdefiniert |
| Lastszenario | Leerlauf, Office, Multimedia, Gaming, Stresstest |
| Kühlungsstufe | Niedrig, Mittel, Hoch |

Das Produkt erzeugt folgende Ausgaben:

| Ausgabe | Einheit oder Zustand |
|---|---|
| CPU-Auslastung | % |
| Ungedrosselte CPU-Taktfrequenz | GHz |
| Effektive CPU-Taktfrequenz | GHz |
| CPU-Temperatur | °C |
| Throttling-Stufe | Normal, Warnung, Reduziert, Kritisch |
| Stromverbrauch | W |
| Ereignisprotokoll | Liste von Ereigniseinträgen |

---

## 3. Konkretisierte Teilfunktionalitäten

| ID | Teilfunktionalität | Konkretisierung |
|---|---|---|
| TF-01 | Leistungsmodus mit benutzerdefiniertem Modus | Das Produkt stellt vier Leistungsmodi bereit und übernimmt genau einen Modus als aktuellen Leistungsmodus. |
| TF-02 | Vordefinierte Lastszenarien | Das Produkt stellt fünf Lastszenarien bereit und ordnet jedem Szenario eine feste CPU-Auslastung zu. |
| TF-03 | Taktberechnung abhängig von Modus und Auslastung | Das Produkt bestimmt eine ungedrosselte CPU-Taktfrequenz aus aktuellem Leistungsmodus und aktuellem Lastszenario. |
| TF-04 | Temperaturberechnung mit Lüfter-/Kühlungsfaktor | Das Produkt bestimmt eine simulierte CPU-Temperatur aus aktueller Last, CPU-Taktfrequenz und Kühlungsstufe. |
| TF-05 | Mehrstufiges Thermal Throttling | Das Produkt bestimmt aus der CPU-Temperatur eine Throttling-Stufe und eine effektive CPU-Taktfrequenz. |
| TF-06 | Stromverbrauch in Watt ableiten | Das Produkt bestimmt einen simulierten Stromverbrauch in Watt. |
| TF-07 | Ereignisprotokoll für Moduswechsel, Warnungen und Throttling | Das Produkt speichert relevante Ereignisse eines Simulationslaufs chronologisch. |

---

## 4. Produktspezifikation

### 4.1 Leistungsmodi

Das Produkt muss folgende Leistungsmodi bereitstellen:

| Leistungsmodus |
|---|
| Energiesparen |
| Ausgeglichen |
| Höchstleistung |
| Benutzerdefiniert |

Für den benutzerdefinierten Leistungsmodus gelten folgende Wertebereiche:

| Parameter | Minimalwert | Maximalwert | Einheit |
|---|---:|---:|---|
| Maximale CPU-Taktfrequenz | 1,2 | 4,2 | GHz |
| Stromverbrauchsgrenzwert | 8 | 65 | W |

### 4.2 Lastszenarien

| Lastszenario | CPU-Auslastung |
|---|---:|
| Leerlauf | 15 % |
| Office | 35 % |
| Multimedia | 55 % |
| Gaming | 85 % |
| Stresstest | 100 % |

### 4.3 Taktfrequenzen fester Leistungsmodi

| Leistungsmodus | Leerlauf | Office | Multimedia | Gaming | Stresstest |
|---|---:|---:|---:|---:|---:|
| Energiesparen | 1,2 GHz | 1,4 GHz | 1,6 GHz | 1,8 GHz | 2,0 GHz |
| Ausgeglichen | 1,4 GHz | 2,0 GHz | 2,5 GHz | 3,0 GHz | 3,2 GHz |
| Höchstleistung | 2,0 GHz | 2,8 GHz | 3,4 GHz | 4,0 GHz | 4,2 GHz |

### 4.4 Temperaturbereich

Das Produkt muss CPU-Temperaturen im Bereich von 35 °C bis 100 °C ausgeben.

### 4.5 Throttling-Stufen

| Throttling-Stufe | Temperaturbereich | Effektive CPU-Taktfrequenz |
|---|---:|---|
| Normal | 35 °C bis 69 °C | 100 % der ungedrosselten CPU-Taktfrequenz |
| Warnung | 70 °C bis 79 °C | 100 % der ungedrosselten CPU-Taktfrequenz |
| Reduziert | 80 °C bis 89 °C | 80 % der ungedrosselten CPU-Taktfrequenz |
| Kritisch | 90 °C bis 100 °C | 60 % der ungedrosselten CPU-Taktfrequenz |

### 4.6 Stromverbrauch

Das Produkt muss den Stromverbrauch im Bereich von 8 W bis 65 W ausgeben.

### 4.7 Ereignisprotokoll

Jeder Ereignisprotokolleintrag muss folgende Felder enthalten:

| Feld | Bedeutung |
|---|---|
| Ereigniszeitpunkt | Zeitpunkt der Ereigniserfassung. |
| Ereignistyp | Definierter Ereignistyp. |
| Ereignisbeschreibung | Textuelle Beschreibung des Ereignisses. |
| Leistungsmodus | Aktueller Leistungsmodus. |
| Lastszenario | Aktuelles Lastszenario. |
| CPU-Auslastung | Aktuelle CPU-Auslastung. |
| CPU-Taktfrequenz | Aktuelle effektive CPU-Taktfrequenz. |
| CPU-Temperatur | Aktuelle CPU-Temperatur. |
| Throttling-Stufe | Aktuelle Throttling-Stufe. |

Zulässige Ereignistypen:

| Ereignistyp |
|---|
| `MODUSWECHSEL` |
| `LASTSZENARIOWECHSEL` |
| `TEMPERATURWARNUNG` |
| `THROTTLING_AKTIVIERT` |
| `THROTTLING_GEÄNDERT` |
| `SIMULATION_ZURÜCKGESETZT` |

---


## 5. Nicht-funktionale Pflichten

| ID | Pflicht |
|---|---|
| PH-NF-001 | Das Produkt muss auf einem normalen PC ohne zusätzliche Laptop-Hardware lauffähig sein. |
| PH-NF-002 | Das Produkt darf keine echten CPU-Sensoren, Temperatur-Sensoren oder Leistungssteuerungen verwenden. |
| PH-NF-003 | Das Produkt muss bei identischen Eingaben identische Ausgaben erzeugen. |
| PH-NF-004 | Das Produkt muss ungültige Nutzereingaben kontrolliert behandeln. |
| PH-NF-005 | Das Projekt muss Requirements, Lastenheft und Pflichtenheft dokumentieren. |
| PH-NF-006 | Das Projekt muss eine Traceability von Requirements zu Testfällen ermöglichen. |
| PH-NF-007 | Das Projekt muss nach jeder Iteration eine lauffähige Baseline erzeugen. |

---

## 6. Iterationsplanung

| Iteration | Inhalt | Ergebnis |
|---|---|---|
| Iteration 1 | TF-01, TF-02, TF-03 | Lauffähige Grundsimulation mit Leistungsmodus, Lastszenario und CPU-Taktfrequenz. |
| Iteration 2 | TF-04, TF-06, erste Version TF-07 | Erweiterte Simulation mit Kühlung, Temperatur, Stromverbrauch und ersten Ereignissen. |
| Iteration 3 | TF-05, vollständige TF-07 | Vollständige Simulation mit Thermal Throttling und Ereignisprotokoll. |

---

## 7. Prüfbarkeit

Die Prüfbarkeit erfolgt über:

| Methode | Anwendung |
|---|---|
| Review | Prüfung der Dokumente, Wertebereiche und Requirements-Struktur. |
| Funktionstest | Prüfung einzelner sichtbarer Systemfunktionen. |
| Grenzwerttest | Prüfung definierter Minimal- und Maximalwerte. |
| Integrationstest | Prüfung des Zusammenspiels mehrerer Teilfunktionalitäten. |
| Traceability-Review | Prüfung der Verknüpfung zwischen Requirements und Testfällen. |
