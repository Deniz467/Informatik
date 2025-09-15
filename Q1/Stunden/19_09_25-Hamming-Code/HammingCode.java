public class HammingCode {

  private final int codeLength;
  private final int dataBitCount;
  private final int parityBits;
  private final int totalCodes;

  public HammingCode(int parityBits) {
    this.parityBits = parityBits;
    this.codeLength = ((int) Math.pow(2.0, parityBits)) - 1;
    this.dataBitCount = codeLength - parityBits;

    this.totalCodes = 
  }

  public int[] calculateAllCodes() {
    final int[][] codes = new int[totalCodes][codeLength];

    for (int i = 0; i < totalCodes; i++) {
      final int[] dataBits = toBitArray(i, dataBitCount);
      final int[] code = createCode(dataBits);
      codes[i] = code;
    }

    
  }


  private int[] createCode(int[] data) {
    final int[] code = new int[codeLength];

    for (int i = 0; i < parityBits; i++) {
      final int position = (int) Math.pow(2, i);      
    }

    return code;
  }
}
