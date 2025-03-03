package me.deniz.eventsystem.console.command.exceptions;

import java.io.Serial;

public class InvalidCredentialsException extends IllegalArgumentException implements
    ConsoleCommandException {

  @Serial
  private static final long serialVersionUID = -1973676735394575433L;

  @Override
  public void handle() {
    System.out.println("No matching credentials found");
  }
}
