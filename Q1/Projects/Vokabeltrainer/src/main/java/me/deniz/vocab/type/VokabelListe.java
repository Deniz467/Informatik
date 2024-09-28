package me.deniz.vocab.type;

import java.util.ArrayList;
import java.util.List;
import me.deniz.vocab.VokabelTrainer;
import me.deniz.vocab.util.WaitingLine;

public class VokabelListe extends WaitingLine<Vokabel> {

  public void shuffle() {
    final List<Vokabel> vocabs = new ArrayList<>();
    while (hasNextElement()) {
      vocabs.add(poll());
    }

    for (int i = vocabs.size() - 1; i > 0; i--) {
      final int nextInt = VokabelTrainer.RANDOM.nextInt(i + 1);
      final Vokabel temp = vocabs.get(i);
      vocabs.set(i, vocabs.get(nextInt));
      vocabs.set(nextInt, temp);
    }

    final List<Vokabel> finalVocabs = new ArrayList<>();

    Vokabel previousVocab = null;
    for (final Vokabel currentVocab : vocabs) {
      if (currentVocab.equals(previousVocab)) { // Same vocab - search for a different one
        for (int i = 0; i < vocabs.size(); i++) {
          final Vokabel nextVocab = vocabs.get(i);
          if (!nextVocab.equals(previousVocab)) {
            finalVocabs.add(nextVocab);
            vocabs.remove(i);
            break;
          }
        }
      } else {
        finalVocabs.add(currentVocab);
      }

      previousVocab = currentVocab;
    }

    for (final Vokabel vocab : finalVocabs) {
      queue(vocab);
    }
  }
}
