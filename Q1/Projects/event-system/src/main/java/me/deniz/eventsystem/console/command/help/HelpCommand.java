package me.deniz.eventsystem.console.command.help;

import java.util.Map;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import org.slf4j.Logger;

public final class HelpCommand {

  public static boolean parseMaybeHelp(Logger logger, String rawInput,
      Map<String, ConsoleCommand> commands) {
    final String[] splittedCommand = rawInput.split(" ");
    final String commandName = splittedCommand[0];

    if (commandName.startsWith("help")) {
      if (splittedCommand.length == 1) {
        logger.info("Available commands:");
        commands.values().forEach(consoleCommand -> {
          logger.info("  {} - {}", consoleCommand.getName(), consoleCommand.getDescription());
        });
      } else {
        final String helpCommand = splittedCommand[1];
        final ConsoleCommand consoleCommand = commands.get(helpCommand);
        if (consoleCommand == null) {
          logger.warn("Cannot find help for command: {}", helpCommand);
        } else {
          logger.info("{} - {}", consoleCommand.getName(), consoleCommand.getDescription());
          logger.info("Usage: {}", consoleCommand.getUsage());
        }
      }

      return true;
    }

    return false;
  }
}
