
public class WaitingLine<T> {

    private Node<T> head;
    private Node<T> tail;

    public T pop() {
        return head == null ? null : head.element;
    }

    public T poll() {
        if (head == null) {
            return null;
        }

        final T value = head.element;
        final Node<T> next = head.next;

        head = next;

        return value;
    }

    public void queue(T element) {
        final node = new Node<>(element);
        
        if (head == null) {
            head = node;
            tail = node;
            return;
        }

        tail.next = node;
        tail = node;
    }

    public boolean hasNextElement() {
        return pop() != null;
    }

    private class Node<T> {
        private Node<T> next;
        private final T element;

        private Node(T element) {
            this.element = element;
        }
    }
}