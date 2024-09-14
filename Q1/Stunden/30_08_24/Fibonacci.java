public class Fibonacci {

  public static void main(String[] args) {
    long startRec = System.currentTimeMillis();
    //System.out.println("Test Rec: " + fibRec(30));
    long endRec = System.currentTimeMillis();
    //System.out.println("Rec ms: " + (endRec - startRec));

    long startIt = System.currentTimeMillis();
    System.out.println("Calculating: " + Long.MAX_VALUE);
    System.out.println("Test It: " + fibIt(Long.MAX_VALUE));
    long endIt = System.currentTimeMillis();
    System.out.println("It ms: " + (endIt - startIt));

  }

  private static int fibRec(int n) {
    if (n < 0) {
      throw new IllegalArgumentException("Only positive numbers. Got: " + n);
    }

    if (n == 0 || n == 1) {
      return n;
    }

    System.out.println(
        "Um die " + n + ". Fibonacci zu berechnen, rechne ich die " + (n - 1) + ". + " + (n - 2));

    return fibRec(n - 1) + fibRec(n - 2);
  }

  private static long fibIt(long n) {
    if (n < 0) {
      throw new IllegalArgumentException("Only positive numbers. Got: " + n);
    }

    long fib1 = 0;
    long fib2 = 1;

    for (long i = 0; i < n; i++) {
      final long temp = fib1;
      fib1 = fib2;
      fib2 += temp;
    }

    return fib1;
  }
}
