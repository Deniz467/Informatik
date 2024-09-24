package me.deniz.vocab;

import static me.deniz.vocab.type.Vokabel.of;

import java.util.Arrays;
import me.deniz.vocab.type.Vokabel;

public class VocabRegistry {

  public static final Vokabel[] DEFAULT_VOCABS = {
    of("Haus", "house"),
    of("Auto", "car"),
    of("Katze", "cat"),
    of("Hund", "dog"),
    of("Maus", "mouse"),
    of("Vogel", "bird"),
    of("Fisch", "fish"),
    of("Pferd", "horse"),
    of("Kuh", "cow"),
    of("Schwein", "pig"),
    of("Huhn", "chicken"),
    of("Ente", "duck"),
    of("Schaf", "sheep"),
    of("Ziege", "goat"),
    of("Elefant", "elephant"),
    of("Löwe", "lion"),
    of("Tiger", "tiger"),
    of("Bär", "bear"),
    of("Affe", "monkey"),
    of("Giraffe", "giraffe"),
    of("Krokodil", "crocodile"),
    of("Schlange", "snake"),
    of("Eidechse", "lizard"),
    of("Spinne", "spider"),
    of("Biene", "bee"),
    of("Ameise", "ant"),
    of("Fliege", "fly"),
    of("Mücke", "mosquito"),
    of("Wespe", "wasp"),
    of("Käfer", "beetle"),
    of("Schmetterling", "butterfly"),
    of("Libelle", "dragonfly"),
    of("Heuschrecke", "grasshopper"),
    of("Motte", "moth"),
    of("Kakerlake", "cockroach"),
  };

  public static Vokabel[] generateVocabs() {
    return Arrays.copyOf(DEFAULT_VOCABS, DEFAULT_VOCABS.length);
  }
}
