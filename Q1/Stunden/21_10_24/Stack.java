public class Stack {
    private Schritt head = null;

    public void push(Schritt schritt) {
        schritt.setPrevious(head);
        head = schritt;
    }

    public Schritt pop() {
        if (head == null) {
            return null;
        }

        Schritt currentHead = head;
        Schritt previous = currentHead.previous;
        currentHead.setPrevious(null);
        head = previous;

        return currentHead;
    }

    public Schritt top() {
        return head;
    }

    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Schritt schritt = head;
        int count = 0;

        while (schritt != null) {
            sb.append("\nSchritt ("+ (count++) +"): " + schritt);
            schritt = schritt.getPrevious();
        }

        return sb.toString();
    }
}
