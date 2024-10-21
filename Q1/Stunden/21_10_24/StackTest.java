public class StackTest {
    public static void main(String[] args) {
        System.out.println("TEst");
        Schritt schritt = new Schritt();
        Schritt schritt2 = new Schritt();
        
        Stack stack = new Stack();

        stack.push(schritt);
        stack.push(schritt2);


        System.out.println("Stack: " + stack);
    }
}
