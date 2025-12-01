import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kasiski {

  public static Map<String, List<Integer>> analyzeLetterSequence(String text, int sequenceLength) {
    final Map<String, List<Integer>> map = new HashMap<>();

    for (int i = 0; i < text.length() - sequenceLength; i++) {
      final String sequence = text.substring(i, i + sequenceLength);

      if (map.containsKey(sequence))
        continue;
      /*
       * final int repeats = text.split(sequence).length - 1;
       * 
       * if (repeats > 1) { map.put(sequence, repeats); }
       */


      int lastIndex = i;
      for (int j = i + 1; j < text.length() - sequenceLength; j++) {
        if (text.substring(j, j + sequenceLength).equals(sequence)) {
          map.computeIfAbsent(sequence, (s) -> new ArrayList<>()).add(j - lastIndex);
          lastIndex = j;
        }
      }
    }

    return map;
  }
}
