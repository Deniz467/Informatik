package me.deniz.eventsystem.console.command.core;

import me.deniz.eventsystem.console.command.ConsoleCommand;

public final class ExitCommand extends ConsoleCommand {

  public ExitCommand() {
    super("exit", "exit", "Exits the application");
  }

  @Override
  public void execute(String[] args) {
    logger.info("Exiting...");
    System.exit(0);
  }
}
