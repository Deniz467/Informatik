import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PolybiosEntryption {

  public static int[] encrypt(String key, String text) {
    key = key.toUpperCase();
    text = text.toUpperCase();

    final char[] textChars = text.toCharArray();
    final int[] encrypted = new int[textChars.length];
    final Map<Character, Integer> square = createSquare(key);

    for (int i = 0; i < textChars.length; i++) {
      Integer integer = square.get(textChars[i]);
      if (integer == null) {
        throw new IllegalStateException("No integer found char: " + textChars[i]);
      }

      encrypted[i] = integer;
    }

    return encrypted;
  }

  public static String decrypt(String key, int[] encrypted) {
    key = key.toUpperCase();

    final StringBuilder decrypted = new StringBuilder();
    final Map<Integer, Character> square = reverseMap(createSquare(key));

    for (int i : encrypted) {
      decrypted.append(square.get(i));
    }

    return decrypted.toString();
  }

  private static Map<Integer, Character> reverseMap(Map<Character, Integer> map) {
    final Map<Integer, Character> result = new HashMap<>(map.size());
    map.forEach((key, value) -> result.put(value, key));
    return result;
  }


  private static Map<Character, Integer> createSquare(String key) {
    final Map<Character, Integer> result = new HashMap<>(26);
    final Set<Character> added = new HashSet<>(26);
    final char[] keyChars = key.toCharArray();

    int line = 1;
    int column = 1;
    for (char c : keyChars) {
      if (!added.add(c)) {
        continue;
      }

      final int encrypted = Integer.parseInt(String.valueOf(column) + String.valueOf(line));
      

      if ((c == 'I' && result.containsKey('J')) || (c == 'J' && result.containsKey('J'))) {
        result.put('I', encrypted);
        result.put('J', encrypted);
      } else {
        result.put(c, encrypted);
        if (++column > 5) {
          column = 1;
          line++;
        }
      }
    }

    return result;
  }

}
