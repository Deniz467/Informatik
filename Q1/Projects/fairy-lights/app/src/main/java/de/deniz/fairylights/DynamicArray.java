package de.deniz.fairylights;

public class DynamicArray<T> {

    private Node<T> first;

    public boolean isEmpty() {
        return first == null;
    }

    public T get(int index) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException(index);
        }

        Node<T> currentNode = first;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;

            if (currentNode == null) {
                throw new IndexOutOfBoundsException(index);
            }  
        }

        return currentNode.content;
    }

    public void add(T element) {
        if (isEmpty()) {
            first = new Node<T>(element);
            return;
        }

        Node<T> node = first;

        while (node.next != null) {
            node = node.next;
        }

        node.next = new Node<T>(element);
    }

    public void insertAt(int index, T element) {
        if(isEmpty()) {
            if (index == 0) {
                add(element);
                return;
            } 
            throw new IndexOutOfBoundsException(index);
        }

        if (index == 0) {
            Node<T> newNode = new Node<T>(element);
            newNode.next = first;
            first = newNode; 
            return;
        }

        Node<T> node = first;
        for (int i = 0; i < index  - 1; i++) {
            node = node.next;

            if (node == null) {
                throw new IndexOutOfBoundsException(index);
            }
        }

        final Node<T> newNode = new Node<T>(element);
        newNode.next = node.next;
        node.next = newNode;
    }

    public void set(int index, T element) {
        if(isEmpty()) {
            if (index == 0) {
                first = new Node<T>(element);
                return;
            } 
            throw new IndexOutOfBoundsException(index);
        }

        Node<T> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;

            if (node == null) {
                throw new IndexOutOfBoundsException(index);
            }
        }

        node.content = element;
    }

    public void delete(int index) {
        if(isEmpty()) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == 0) {
            first = first.next;
            return;
        }

        Node<T> node = first;
        for (int i = 0; i < index - 1; i++) {
            node = node.next;

            if(node == null) {
                throw new IndexOutOfBoundsException(index);
            }
        }
        final Node<T> newNext = node.next.next;
        node.next = newNext;
    }

    public int getLength() {
        int length = 0;
        Node<T> node = first;
        while(node != null) {
            node = node.next;
            length++;
        }

        return length;
    }

    private static class Node<T> {
        private T content;
        private Node<T> next;

        private Node(T content) {
            this.content = content;
        }
    }
}
