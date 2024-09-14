# 1a

Das Programm findet Vorzeichenwechsel von `-` nach `+` für die zwei eingegeben
`x` Werte der Funktion `f(x) = x²-3`.\
Als erstes gibt man einen Wert für x ein und das Programm gibt den y Wert zurück.
Dann gibt man man zwei weitere x Stellen ein und das Programm überprüft ob der Vorzeichenwechsel
vorliegt.\
In Zeile 8 ist der Befehl `f(x)` ein aufruf zu der darunter deklarierten Methode `f` welche
als Parameter ein `double` hat.

# 1c

1. Zwei Zahlen werden eingeben
2. Die eingeben Zahlen werden in den Variablen `a` und `b` gespeichert
3. Überprüfen ob ein Vorzeichenwechsel von + nach - oder von - nach +
4. Wenn nicht **Abbruch**
5. `a + b / 2` = `m`
6. `f(m)` berechnen
7. Wenn kleiner als `0.001`, dann m und `f(m)` ausgeben - **Abbruch**
8. Vorzeichenwechsel zwischen `a` und `m`? -> `b = m` zurück zu 5
9. Vorzeichenwechsel zwischen `m` und `b`? -> `a = m` zurück zu 5