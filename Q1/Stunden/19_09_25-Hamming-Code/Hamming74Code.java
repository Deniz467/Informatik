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
}
