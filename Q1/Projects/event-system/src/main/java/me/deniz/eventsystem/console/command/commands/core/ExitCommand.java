package me.deniz.eventsystem.console.command.commands.core;

import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.command.ConsoleCommand;

public final class ExitCommand extends ConsoleCommand {

  public ExitCommand() {
    super("exit", "exit", "Exits the application", null);
  }

  @Override
  public void execute(ParsedArguments args) {
    logger.info("Exiting...");
    System.exit(0);
  }
}
