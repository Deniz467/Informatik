package me.deniz.vocab.util;

import java.util.Iterator;

public class WaitingLine<T> implements Iterable<T> {

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
      builder.append("- ").append(current.element.toString()).append(" -")
          .append(System.lineSeparator());
      current = current.next;
    }

    return builder.toString();
  }

  public void addAll(Iterable<T> elements) {
    for (final T element : elements) {
      queue(element);
    }
  }

  public void addAll(T[] elements) {
    for (final T element : elements) {
      queue(element);
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<>() {

      private Node<T> current = head;

      @Override
      public boolean hasNext() {
        return current != null;
      }

      @Override
      public T next() {
        final T element = current.element;
        current = current.next;

        return element;
      }
    };
  }

  private static class Node<T> {

    private Node<T> next;
    private final T element;

    private Node(T element) {
      this.element = element;
    }
  }
}