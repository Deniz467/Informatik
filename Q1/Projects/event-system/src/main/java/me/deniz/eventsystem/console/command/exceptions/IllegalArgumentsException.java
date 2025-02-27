package me.deniz.eventsystem.console.command.exceptions;

import java.io.Serial;
import me.deniz.eventsystem.console.command.ConsoleCommand;

public class IllegalArgumentsException extends IllegalArgumentException implements
    ConsoleCommandException {

  @Serial
  private static final long serialVersionUID = 5193769608689132756L;

  private final ConsoleCommand command;

  public IllegalArgumentsException(String[] args, ConsoleCommand command) {
    super("Illegal arguments for command " + command.getName() + ": " + String.join(" ", args));
    this.command = command;
  }

  @Override
  public void handle() {
    System.out.println(getMessage());
    System.out.println("Usage: " + command.getUsage());
  }
}
