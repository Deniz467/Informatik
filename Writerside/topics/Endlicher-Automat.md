# Endlicher Automat

## Was ist ein endlicher Automat?

Ein endlicher Automat (EA) ist ein abstraktes Rechenmodell, das entscheidet, ob ein Wort (Sequenz
von Symbolen) zu einer formalen Sprache gehört. Er arbeitet Zeichen für Zeichen und hat:

1. **Zustandsmenge** ($Q$): Endliche Menge aller „Stufen“, in denen sich der Automat befinden
   kann.
2. **Eingabealphabet** ($\sum$): Endliche Menge von Eingabesymbolen, z. B. ($\{0,1\}$).
3. **Startzustand** ($q_0 \in Q$): Startzustand, in dem der Automat beginnt.
4. **Endzustände** ($F \subseteq Q$): Wenn nach Lesen aller Symbole ein Zustand in ($F$)
   erreicht ist, wird das Wort **akzeptiert**.
5. **Übergangsfunktion** ($\delta$): Legt fest, wie der Automat bei einem Symbol-Wechsel den
   Zustand wechselt.

> Automat = **5‑Tupel** ($A = (Q,\Sigma,\delta,q_0,F)$).
> 
{style="note"}