# Neuronales Netz

## Voraussetzungen

* **Java Version:** mindestens **Java 21**
* **Build-Tool:** Gradle (Wrapper ist im Projekt enthalten)

## Projekt starten

Das Projekt kann über Gradle gestartet werden.

Im Projektverzeichnis (wo diese README liegt) ausführen:

```bash
./gradlew run
```

Beim ersten Start werden:

1. die Trainingsdaten geladen
2. das neuronale Netz trainiert
3. das trainierte Netz gespeichert
4. ein kurzer Test mit zufälligen Beispielen durchgeführt

## Einstellungen

Einstellungen befinden sich in der Klasse:

```
me.deniz.neuronalesnetz.Settings
```

Dort kann unter anderem angepasst werden:

* Lernrate (`LEARNING_RATE`)
* Anzahl der Trainingsrunden (`TRAINING_ROUNDS`)
* Trainingsschritte pro Runde (`STEPS_PER_ROUND`)
* Bildgröße (`IMG_WIDTH`, `IMG_HEIGHT`)
* Anzahl der Testbeispiele (`TESTING_SIZE`)

## Training des Netzes

Es gibt zwei Trainingsmethoden im Projekt:

### 1. Numerisches Training

* Methode: `Net.train(...)`
* Berechnet Gradienten durch kleine Änderungen der Gewichte
* **Sehr langsam**

### 2. Backpropagation

* Methode: `Net.trainBackprop(...)`

> Die Backpropagation-Implementierung wurde nicht von mir geschrieben.
> Sie wurde ergänzt, weil das numerische Training so langsam war, dass das Netz kaum sinnvoll
> getestet werden konnte.

## Speicherung des Netzes

Das trainierte Netz wird automatisch in einer Datei gespeichert (root Ordner des gesamten Projekts,
nicht in dem Ordner wo diese README ist):

```
net_save.dat
```

Beim nächsten Programmstart wird versucht, dieses Netz wieder zu laden, wenn die Netzwerkstruktur
übereinstimmt.

## Testen

Nach dem Training wird das Netz mit zufälligen Beispielen getestet:

* Ausgabe der Trefferquote
* Anzeige, wie oft jede Ziffer vorhergesagt wurde