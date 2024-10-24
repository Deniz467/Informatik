public class PlateStack {
    private Plate head;

    public boolean isEmpty() {
        return head == null;
    }

    public Plate top() {
        return head;
    }

    public void push(Plate plate) {
        if (!isEmpty()) {
            plate.setNext(head);
        }

        head = plate;
    }

    public Plate pop() {
        if (isEmpty()) {
            return null;
        }

        final Plate temp = head;
        final Plate next = temp.getNext();

        if (next != null) {
            head = next;
        }

        return temp;
    }
}
