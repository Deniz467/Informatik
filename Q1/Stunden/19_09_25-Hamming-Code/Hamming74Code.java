public class Hamming74Code {

  public static int[] calculateParityBits(int[] input) {
    if (input.length != 4) {
      throw new IllegalArgumentException("Input length != 4 was: " + input.length);
    }

    final int[] bits = new int[7];

    bits[0] = input[0] | input[1] | input[3];
    bits[1] = input[0] | input[2] | input[3];
    bits[2] = input[0];
    bits[3] = input[1] | input[2] | input[3];
    bits[4] = input[1];
    bits[5] = input[2];
    bits[6] = input[3];

    return bits;
  }

  public static int[] tryParseWithParityBits(int[] input) {
    if (input.length != 7) {
      throw new IllegalArgumentException("Input length != 7 was: " + input.length);
    }

    final int[] result = new int[4];
    final int d1 = input[2], d2 = input[4], d3 = input[5], d4 = input[6];
    final int p1 = input[0], p2 = input[1], p3 = input[3];


    return result;
  }
}
