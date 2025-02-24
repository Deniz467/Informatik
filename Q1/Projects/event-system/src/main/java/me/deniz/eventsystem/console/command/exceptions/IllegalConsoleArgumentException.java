package me.deniz.eventsystem.console.command.exceptions;

import java.io.Serial;

public class IllegalConsoleArgumentException extends IllegalArgumentException implements
    ConsoleCommandException {

  @Serial
  private static final long serialVersionUID = 2212433201630045271L;

  public IllegalConsoleArgumentException(String message) {
    super(message);
  }

  @Override
  public void handle() {
    System.out.println("Illegal argument: " + getMessage());
  }
}
