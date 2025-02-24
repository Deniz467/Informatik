package me.deniz.eventsystem.console.command.exceptions;

import java.io.Serial;

public class ConsoleFailException extends RuntimeException implements ConsoleCommandException {

  @Serial
  private static final long serialVersionUID = 5167246401913661932L;

  public ConsoleFailException(String message) {
    super(message);
  }

  @Override
  public void handle() {
    System.out.println(getMessage());
  }

}
