public class VigenereEncryption {

  public static String encrypt(String plainText, String keyword) {
    final StringBuilder encrypted = new StringBuilder(plainText.length());
    final char[] keys = keyword.toCharArray();
    int idx = 0;
    for (char c : plainText.toCharArray()) {
      final int cNum = Character.toLowerCase(c) - 'a';
      final int keyNum = Character.toLowerCase(keys[idx++]) - 'a';

      if (idx >= keys.length) {
        idx = 0;
      }

      System.out.println("cNum: " + cNum + " keyNum: " + keyNum);

      final int encrypt = (cNum + keyNum) % 26;
      encrypted.append((char) (encrypt + 'a'));
    }

    return encrypted.toString();
  }
  
  public static String decrypt(String encrypted, String keyword) {
    final StringBuilder decrypted = new StringBuilder(encrypted.length());
    final char[] keys = keyword.toCharArray();
    int idx = 0;
    for (char c : encrypted.toCharArray()) {
      final int cNum = Character.toLowerCase(c) - 'a';
      final int keyNum = Character.toLowerCase(keys[idx++]) - 'a';
      if (idx >= keys.length) {
        idx = 0;
      }

      final int decrypt = (cNum - keyNum) % 26;
      decrypted.append((char) (decrypt + 'a'));
    }

    return decrypted.toString();
  }
}
