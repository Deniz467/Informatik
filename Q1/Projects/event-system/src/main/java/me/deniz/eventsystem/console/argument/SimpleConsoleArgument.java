package me.deniz.eventsystem.console.argument;

import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public abstract class SimpleConsoleArgument<T> extends ConsoleArgument<T, T> {

  public SimpleConsoleArgument(String id) {
    super(id);
  }

  @Override
  public final T transform(T parsed) throws IllegalConsoleArgumentException {
    return parsed;
  }
}
