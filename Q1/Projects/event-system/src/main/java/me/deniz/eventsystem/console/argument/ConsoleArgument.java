package me.deniz.eventsystem.console.argument;

import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public abstract class ConsoleArgument<T, R> {

  private final String id;
  private boolean optional = false;

  public ConsoleArgument(String id) {
    this.id = id;
  }

  public abstract T parse(String input) throws IllegalConsoleArgumentException;

  public abstract R transform(T parsed) throws IllegalConsoleArgumentException;

  public String getId() {
    return id;
  }

  public boolean isOptional() {
    return optional;
  }

  public void setOptional(boolean optional) {
    this.optional = optional;
  }

  protected final IllegalConsoleArgumentException fail(String message) {
    return new IllegalConsoleArgumentException(message);
  }
}
