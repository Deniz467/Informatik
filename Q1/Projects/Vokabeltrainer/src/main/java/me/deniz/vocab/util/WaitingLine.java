package me.deniz.vocab.util;

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
    head = head.next;

    return value;
  }

  public void queue(T element) {
    final Node<T> node = new Node<>(element);

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

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    Node<T> current = head;

    while (current != null) {
      builder.append("- ")
          .append(current.element.toString())
          .append(" -")
          .append(System.lineSeparator());
      current = current.next;
    }

    return builder.toString();
  }

  private static class Node<T> {

    private Node<T> next;
    private final T element;

    private Node(T element) {
      this.element = element;
    }
  }
}