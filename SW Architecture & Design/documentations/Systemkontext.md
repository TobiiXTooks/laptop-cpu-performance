# Systemkontext

## 1. Zweck dieses Dokuments

Dieses Dokument beschreibt den Systemkontext der Teilfunktion **Laptop – Prozessorleistung**. Es legt fest, welche Bestandteile innerhalb des zu entwickelnden Softwaremoduls liegen und welche Bereiche nicht Bestandteil dieses Moduls sind.

Der Systemkontext bildet die Grundlage für die spätere Architektur, da daraus abgeleitet wird:

- welche Komponenten benötigt werden,
- welche fachlichen Aufgaben innerhalb der Teilfunktion liegen,
- welche Schnittstellen nach außen bestehen,
- welche Eingabe- und Ergebnisdaten verarbeitet werden,
- welche Abgrenzungen für Requirements, Implementierung und Tests gelten.

---

## 2. Systemname

**Laptop-Prozessorleistungsmodul**

Das System bildet die Teilfunktion **Prozessorleistung** eines Laptops als Softwaremodul ab. Es nimmt leistungsrelevante Prozessorwerte auf, verarbeitet diese nach definierten Regeln und stellt daraus Ergebniswerte zur Prozessorleistung bereit.

---

## 3. Kurzbeschreibung des Systems

Das Laptop-Prozessorleistungsmodul dient zur Erfassung, Bewertung und Darstellung der Prozessorleistung eines Laptops. Dazu verarbeitet es insbesondere:

- den ausgewählten Leistungsmodus,
- das ausgewählte Lastszenario,
- den CPU-Auslastungswert,
- die CPU-Taktfrequenz,
- die CPU-Temperatur,
- den Thermal-Throttling-Status,
- die Leistungsaufnahme.

Die Werte werden über definierte Eingabeschnittstellen aufgenommen, verarbeitet und in Form eines strukturierten Ergebnisses ausgegeben. Der Schwerpunkt liegt auf der Softwarefunktion **Prozessorleistung** und nicht auf anderen Laptop-Funktionen wie Display, Tastatur, Speicherverwaltung oder Akkuverwaltung.

Je nach Iteration werden folgende Ergebniswerte bereitgestellt:

| Iteration | Ergebniswerte |
|---:|---|
| 1 | Leistungsmodus, Lastszenario, CPU-Auslastung, CPU-Taktfrequenz |
| 2 | CPU-Taktfrequenz, CPU-Temperatur, Thermal-Throttling-Status |
| 3 | CPU-Taktfrequenz, CPU-Temperatur, Thermal-Throttling-Status, Leistungsaufnahme, Protokollausgabe |

---

## 4. Innerhalb des Systems

Innerhalb des Systems liegen alle Funktionen, die direkt zur Teilfunktion **Laptop – Prozessorleistung** gehören.

| Bereich | Beschreibung | Relevanz für Architektur |
|---|---|---|
| Benutzereingabe | Der Benutzer wählt Leistungsmodus und Lastszenario aus oder gibt leistungsrelevante Prozessorwerte ein. | Benötigt eine Benutzerschnittstelle. |
| Prozessorwertaufnahme | Das System übernimmt CPU-Auslastung, Taktfrequenz, Temperatur und Leistungsaufnahme als Eingabewerte. | Benötigt definierte Eingabeschnittstellen und Datenmodelle. |
| Ablaufsteuerung | Ein vollständiger Verarbeitungsablauf wird koordiniert. | Benötigt eine zentrale Steuerungskomponente. |
| Leistungsmodusverwaltung | Verfügbare Leistungsmodi werden bereitgestellt und ausgewertet. | Benötigt eine eigene Fachkomponente. |
| Lastszenarioverwaltung | Verfügbare Lastszenarien werden bereitgestellt und ausgewertet. | Benötigt eine eigene Fachkomponente. |
| Taktbewertung | Die CPU-Taktfrequenz wird in Bezug auf Modus, Lastszenario und Auslastung bewertet. | Kernfunktion in Iteration 1. |
| Temperaturbewertung | Die CPU-Temperatur wird in Bezug auf den aktuellen Prozessorzustand bewertet. | Erweiterung in Iteration 2. |
| Thermal-Throttling-Bewertung | Das System bewertet, ob aufgrund eines Temperaturgrenzwerts eine Leistungsreduzierung vorliegt. | Erweiterung in Iteration 2. |
| Leistungsaufnahmebewertung | Die elektrische Leistungsaufnahme wird als Teil des Prozessorzustands verarbeitet. | Erweiterung in Iteration 3. |
| Protokollierung | Eingaben, Verarbeitungsschritte und Ergebnisse werden protokolliert. | Erweiterung in Iteration 3. |
| Ergebnisanzeige | Ergebnisse werden dem Benutzer angezeigt. | Bestandteil jeder lauffähigen Iteration. |

---

## 5. Außerhalb des Systems

Außerhalb des Systems liegen alle Bestandteile, die nicht direkt zur Teilfunktion **Laptop – Prozessorleistung** gehören.

| Externer Bereich | Abgrenzung | Konsequenz für Architektur |
|---|---|---|
| Andere Laptop-Teilfunktionen | Display, Tastatur, Akkuverwaltung, Speicherverwaltung und weitere Laptopfunktionen sind nicht Bestandteil dieses Moduls. | Das Modul bleibt auf Prozessorleistung begrenzt. |
| Betriebssystemumgebung | Das Betriebssystem stellt die Ausführungsumgebung bereit und kann Prozessorwerte als Datenquelle bereitstellen. | Die Architektur sieht eine abgegrenzte Eingabeschnittstelle für Prozessorwerte vor. |
| Benutzer | Der Benutzer ist externer Akteur und nicht Teil des Softwaremoduls. | Die Interaktion erfolgt über die Benutzerschnittstelle. |
| Projektartefakte | Requirements, Tests und Traceability sind Nachweisdokumente und keine Laufzeitkomponenten. | IDs müssen in Architektur, Design, Code und Tests nachvollziehbar bleiben. |

---

## 6. Systemgrenze

Die Systemgrenze verläuft zwischen der Teilfunktion **Laptop – Prozessorleistung** und allen anderen Systembestandteilen des Laptops bzw. der Projektumgebung.

Innerhalb der Systemgrenze befinden sich:

- Eingabelogik,
- Ablaufsteuerung,
- Leistungsmodusverwaltung,
- Lastszenarioverwaltung,
- Aufnahme und Verarbeitung von Prozessorwerten,
- Bewertungslogik,
- Datenmodelle,
- Ergebnisanzeige,
- Protokollierung.

Außerhalb der Systemgrenze befinden sich:

- andere Laptop-Teilfunktionen,
- Betriebssystemumgebung,
- Entwicklungsumgebung

---

## 7. Annahmen

| ID | Annahme |
|---|---|
| ASM-001 | Das System wird als eigenständiges Softwaremodul für die Teilfunktion „Laptop – Prozessorleistung“ entwickelt. |
| ASM-002 | Das System verarbeitet leistungsrelevante Prozessorwerte wie CPU-Auslastung, Taktfrequenz, Temperatur und Leistungsaufnahme. |
| ASM-003 | Die fachliche Kernfunktion besteht in der Verarbeitung von Leistungsmodus, Lastszenario und Prozessorwerten zu einer Ergebnisbewertung. |
| ASM-004 | Die Ergebniswerte werden für den Benutzer nachvollziehbar dargestellt. |
| ASM-005 | Die Architektur wird so aufgebaut, dass sie über drei Iterationen schrittweise erweitert werden kann. |

---

## 8. Konsequenzen für die Architektur

Aus dem Systemkontext ergeben sich folgende Architekturkonsequenzen:

| ID | Konsequenz | Auswirkung |
|---|---|---|
| CON-001 | Die Teilfunktion wird als abgegrenztes Modul betrachtet. | Andere Laptopfunktionen werden nicht in die Architektur aufgenommen. |
| CON-002 | Eingabe, Verarbeitung und Ausgabe werden getrennt. | Eine Schichtenarchitektur ist sinnvoll. |
| CON-003 | Prozessorwerte müssen strukturiert übernommen werden. | Ein Datenmodell für `ProcessorState` wird benötigt. |
| CON-004 | Der Verarbeitungsablauf muss zentral koordiniert werden. | Ein `PerformanceController` wird benötigt. |
| CON-005 | Jede Teilfunktionalität soll separat testbar sein. | Die Fachlogik wird in einzelne Komponenten getrennt. |
| CON-006 | Ergebnisse müssen nachvollziehbar sein. | Eine Protokollierungskomponente wird vorgesehen. |
| CON-007 | Iterationen müssen lauffähige Zwischenergebnisse liefern. | Die Architektur wird schrittweise erweiterbar aufgebaut. |

---

## 9. Abgrenzung für Iteration 1

Für Iteration 1 wird aus dem Systemkontext der Kernablauf umgesetzt:

1. Benutzer wählt Leistungsmodus.
2. Benutzer wählt Lastszenario.
3. System nimmt CPU-Auslastung und CPU-Taktfrequenz als Prozessorwerte auf.
4. System verarbeitet die Eingaben zu einem Ergebnis zur Prozessorleistung.
5. System zeigt das Ergebnis an.

Nicht Bestandteil von Iteration 1:

- Temperaturbewertung,
- Thermal-Throttling-Bewertung,
- Leistungsaufnahmebewertung,
- vollständige Protokollierung,
- grafische Darstellung,
- weitere Laptop-Teilfunktionen.

