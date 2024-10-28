public class BeaverQueue {
    private Beaver head = null;
    private Beaver tail = null;

    public boolean isEmpty() {
        return head == null;
    }

    public Beaver head() {
        return head;
    }

    public void queue(Beaver beaver) {
        if (isEmpty()) {
            head = beaver;
            tail = beaver;
        } else {
            tail.setNext(beaver);
            tail = beaver;
        }
    }

    public Beaver pop() {
        if (isEmpty()) {
            return null;
        }

        final Beaver temp = head;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
        }

        temp.setNext(null);

        return temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (head == null) {
            sb.append("EMPTY");
        } else {
            sb.append("[");
            sb.append(head);
            sb.append(", ");

            Beaver next = head.getNext();
            while (next != null) {
                sb.append(next);

                if (next.getNext() !=  null) {
                    sb.append(", ");
                }

                next = next.getNext();
            }

            sb.append("]");
        }

        return sb.toString();
    }
}
