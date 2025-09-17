import java.util.Arrays;

public class Hamming74Code {

  public static int[] calculateParityBits(int[] input) {
    if (input.length != 4) {
      throw new IllegalArgumentException("Input length != 4 was: " + input.length);
    }

    final int[] bits = new int[7];

    bits[0] = input[0] ^ input[1] ^ input[3];
    bits[1] = input[0] ^ input[2] ^ input[3];
    bits[2] = input[0];
    bits[3] = input[1] ^ input[2] ^ input[3];
    bits[4] = input[1];
    bits[5] = input[2];
    bits[6] = input[3];

    return bits;
  }

  public static boolean isValid(int[] code) {
    if (code.length != 7) {
      throw new IllegalArgumentException("Input length != 7 was: " + code.length);
    }

    final int d1 = code[2], d2 = code[4], d3 = code[5], d4 = code[6];
    final int p1 = code[0], p2 = code[1], p3 = code[3];

    final int c1 = p1 ^ d1 ^ d2 ^ d4;
    final int c2 = p2 ^ d1 ^ d3 ^ d4;
    final int c3 = p3 ^ d2 ^ d3 ^ d4;

    return c1 == 0 && c2 == 0 && c3 == 0 && isRealCodeword(code);
  }

  private static boolean isRealCodeword(int[] code) {
    final int d1 = code[2], d2 = code[4], d3 = code[5], d4 = code[6];
    final int[] data = {d1, d2, d3, d4};
    final int[] rebuilt = Hamming74Code.calculateParityBits(data);

    return Arrays.equals(rebuilt, code);
  }

  public static int distance(int[] code1, int[] code2) {
    if (code1.length != code2.length) {
      throw new IllegalArgumentException("Input length missmatch");
    }

    int differences = 0;

    for (int i = 0; i < code1.length; i++) {
      if (code1[i] != code2[i]) {
        differences++;
      }
    }

    return differences;
  }

  public static int[][] generateAllCodewords() {
    final int[][] codes = new int[16][7];

    for (int i = 0; i < 16; i++) {
      final int[] data = toDataCode(i);
      final int[] code = calculateParityBits(data);
      codes[i] = code;
    }

    return codes;
  }

  public static int[] toDataCode(int value) {
    final int[] array = new int[4];
    final String binaryString = Integer.toBinaryString(value);
    if (binaryString.length() > 4) {
      throw new IllegalArgumentException("Value too large for 4 bits: " + value);
    }

    final String padded = "0".repeat(4 - binaryString.length()) + binaryString;

    for (int i = 0; i < 4; i++) {
      array[i] = Character.getNumericValue(padded.charAt(i));
    }

    return array;
  }

  public static String toBitString(int[] array) {
    final StringBuilder sb = new StringBuilder();
    for (int bit : array) {
      sb.append(bit);
    }
    return sb.toString();
  }
}
