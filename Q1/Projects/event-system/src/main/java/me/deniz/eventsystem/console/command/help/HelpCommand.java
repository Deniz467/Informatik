package me.deniz.eventsystem.console.command.help;

import java.util.Map;
import javax.annotation.Nullable;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.session.Session;
import me.deniz.eventsystem.session.SessionHolder;
import org.slf4j.Logger;

public final class HelpCommand {

  public static boolean parseMaybeHelp(Logger logger, String rawInput,
      Map<String, ConsoleCommand> commands, @Nullable String exitContextCommand) {
    final String[] splittedCommand = rawInput.split(" ");
    final String commandName = splittedCommand[0];

    if (commandName.startsWith("help")) {
      final Session session = SessionHolder.getSession();

      if (splittedCommand.length == 1) {
        logger.info("Available commands:");
        logger.info("  help - Shows this help message");

        if (exitContextCommand != null) {
          logger.info("  {} - Exits the current context", exitContextCommand);
        }

        commands.values().forEach(consoleCommand -> {
          if (session == null || session.hasPermission(consoleCommand.getPermission())) {
            logger.info("  {} - {}", consoleCommand.getName(), consoleCommand.getDescription());
          }
        });
      } else {
        final String helpCommand = splittedCommand[1];
        final ConsoleCommand consoleCommand = commands.get(helpCommand);
        if (consoleCommand == null
            || session != null && !session.hasPermission(consoleCommand.getPermission())) {
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
