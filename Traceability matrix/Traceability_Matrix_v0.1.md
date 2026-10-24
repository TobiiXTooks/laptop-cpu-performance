# Traceability matrix


| Spalte                | Zweck                                                  |
| --------------------- | ------------------------------------------------------ |
| LH-ID                 | Bezug zur Kundenanforderung aus dem Lastenheft         |
| PH-ID / Abschnitt     | Bezug zur Konkretisierung im Pflichtenheft             |
| REQ-ID                | konkretes Requirement                                  |
| TF-ID                 | zugehörige Teilfunktionalität                          |
| Iteration             | geplante Umsetzung                                     |
| Verifikation          | Review, Funktionstest, Grenzwerttest, Integrationstest |
| Architekturkomponente | später ergänzen                                        |
| Code-Modul            | später ergänzen                                        |
| Testfall-ID           | später ergänzen                                        |
| Status                | Offen / Geplant / Implementiert / Getestet             |


| LH-ID     | PH-Bezug  | REQ-ID              | TF-ID | Iteration | Verifikation                 | Architekturkomponente | Code-Modul | Testfall-ID | Status  |
| --------- | --------- | ------------------- | ----- | --------: | ---------------------------- | --------------------- | ---------- | ----------- | ------- |
| LH-F-001  | TF-01     | REQ-F-001           | TF-01 |         1 | Review, Funktionstest        | offen                 | offen      | offen       | geplant |
| LH-F-002  | TF-01     | REQ-F-003           | TF-01 |         1 | Funktionstest, Grenzwerttest | offen                 | offen      | offen       | geplant |
| LH-F-003  | TF-02     | REQ-F-005           | TF-02 |         1 | Review, Funktionstest        | offen                 | offen      | offen       | geplant |
| LH-F-004  | TF-03     | REQ-F-009           | TF-03 |         1 | Funktionstest                | offen                 | offen      | offen       | geplant |
| LH-F-005  | TF-04     | REQ-F-013           | TF-04 |         2 | Review, Funktionstest        | offen                 | offen      | offen       | geplant |
| LH-F-006  | TF-04     | REQ-F-015           | TF-04 |         2 | Funktionstest                | offen                 | offen      | offen       | geplant |
| LH-F-007  | TF-05     | späteres REQ-F      | TF-05 |         3 | Funktionstest, Grenzwerttest | offen                 | offen      | offen       | geplant |
| LH-F-009  | TF-06     | späteres REQ-F      | TF-06 |         2 | Funktionstest, Grenzwerttest | offen                 | offen      | offen       | geplant |
| LH-F-010  | TF-07     | späteres REQ-F      | TF-07 |       2/3 | Integrationstest             | offen                 | offen      | offen       | geplant |
| LH-NF-005 | PH-NF-006 | REQ-NF Traceability | —     |       1–3 | Traceability-Review          | offen                 | offen      | offen       | geplant |

---

## Änderungsnotiz Version 0.1

Diese Version stellt die erste grobe Entwurfsfassung der Traceability Matrix dar.

Es wurden erstmals die grundlegenden Beziehungen zwischen Lastenheft, Pflichtenheft, Requirements, Teilfunktionalitäten, Iterationen, Verifikation, Architekturkomponenten, Code-Modulen und Testfällen vorbereitet.

Zu diesem Zeitpunkt waren Architekturkomponenten, Code-Module und Testfall-IDs noch nicht konkret ausgearbeitet. Daher sind viele Felder noch mit `offen` oder `geplant` markiert.

Die Version ist als erste Planungsgrundlage geeignet, musste in späteren Versionen jedoch um konkrete Komponenten, Schnittstellen, Klassen, Code-Dateien und Testfälle erweitert werden.