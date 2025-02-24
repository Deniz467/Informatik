package me.deniz.eventsystem.console.command.exceptions;

import java.io.Serial;

public class IllegalConsoleStateException extends IllegalStateException implements
    ConsoleCommandException {

  @Serial
  private static final long serialVersionUID = 4767822461510620163L;

  public IllegalConsoleStateException(String message) {
    super(message);
  }

  @Override
  public void handle() {
    System.out.println("Illegal state: " + getMessage());
  }
}
