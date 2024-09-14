# Aufwandsanalyse

---

- Zeit- und Speicheraufwand betrachten

> Die Problemgröße ist eine präzise Beschreibung des Umfangs der zu verarbeitenden Daten, von dem das Zeit- bzw.
> Speicherverhalten von Lösungalgorithmen maßgeblich beeinflusst wird.
>
_Kleines Problem = paar Zahlen sortieren. Großes Problem = 1 Millionen Zahlen sortieren_

## Kostenmaße und Kostenfunktionen

- Kostenmaß = z.B. wie viele Operationen ausgeführt werden (Vergleiche, Zuweisungen, etc.)
    - Zeitlicher Aufwand = Anzahl an Operationen
    - Alles Operationen müssen gleich schnell sein. Bei komplexen Objekten oft nicht der Fall

### Nur dominante Operation vergleichen

> Wenn man den Vergleich von Datensätzen als dominate Operation beim Sortieren ansieht, dann lässt sich das folgende
> Kostenmaß für Sortieralgorithmen festlegen: Alle Datensatzvergleiche werden als gleichaufwendig angesehen (egal wie
> die
> Daten im Detail beschaffen sind) und daher nur gezählt. Alle anderen Operationen werden nicht weiter berücksichtigt.
> Mit
> dieser Festlegung erhält man die folgende Kostenfunktion T(n) zur Beschreibung der Zeitkomplexität beim Sortieren:
>

- Kostenmaß = wie werden Operationen bei der Aufwandsbestimmung berücksichtigt
- Kostenfunktion = Gesammtkosten `T(Problemgröße n)`

## Kostenanalyse für Sortieralgorithmen

1. Aufgabe 1
    1. `11+10+9+8+7+6+5+4+3+2+1 = 66` - Die Bilanz ändert sich nicht, nur wenn sich die Anzahl an Zahlen ändert
    2. Je mehr die Zahlen von Anfang an sortiert sind, desto schneller geht es und weniger Vergleiche werden ausgeführt.

### Fallanalysen

- **best case** (bester Fall): der Fall, in dem bei der Ausführung des Algorithmus die wenigsten Kosten anfallen
- **worst case** (schlechtester Fall): der Fall, in dem bei der Ausführung des Algorithmus die meisten Kosten anfallen
- **average case** (durchschnittlicher Fall): eine Mittelung der Kosten über alle Fälle

##### Aufgabe 2

- `T_worst(n) = (n) + (n - 1) + (n - 2) + ... + (n - n)`
- `T_best(n) = n`

##### Aufgabe 40

- Größtenteils: Bubble-Sort
- Völlig unsortiert: Insertion-Sort

## Vergleich von Kostenfunktionen über das asymptotische Wachstumsverhalten

- Eine (Kosten-) Funktion f wächst schneller als eine (Kosten-) Funktion g, wenn der Quotient f(n)/g(n) mit wachsendem n
  gegen unendlich strebt.
- Eine (Kosten-) Funktion f wächst langsamer als eine (Kosten-) Funktion g, wenn der Quotient f(n)/g(n) mit wachsendem n
  gegen 0 strebt.
- Eine (Kosten-) Funktion f wächst genauso schnell wie eine (Kosten-) Funktion g, wenn der Quotient f(n)/g(n) mit
  wachsendem n gegen eine Konstante c mit c>0 strebt.