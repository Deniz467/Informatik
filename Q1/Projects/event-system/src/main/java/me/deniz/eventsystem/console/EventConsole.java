package me.deniz.eventsystem.console;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.console.command.exceptions.ConsoleCommandException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EventConsole extends Thread {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventConsole.class);
  private final Scanner scanner = new Scanner(System.in);
  private final Map<String, ConsoleCommand> commands = new ConcurrentHashMap<>();

  public EventConsole() {
    super("Event Console");
  }

  public void registerCommand(ConsoleCommand command) {
    commands.put(command.getName(), command);
  }

  @Override
  public void run() {
    printInput();
    while (scanner.hasNext()) {
      final String command = scanner.nextLine();
      System.out.println();

      final String[] splittedCommand = command.split(" ");
      final String commandName = splittedCommand[0];

      if (commandName.startsWith("help")) {
        if (splittedCommand.length == 1) {
          LOGGER.info("Available commands:");
          commands.values().forEach(consoleCommand -> {
            LOGGER.info("  {} - {}", consoleCommand.getName(), consoleCommand.getDescription());
          });
        } else {
          final String helpCommand = splittedCommand[1];
          final ConsoleCommand consoleCommand = commands.get(helpCommand);
          if (consoleCommand == null) {
            LOGGER.warn("Cannot find help for command: {}", helpCommand);
          } else {
            LOGGER.info("{} - {}", consoleCommand.getName(), consoleCommand.getDescription());
            LOGGER.info("Usage: {}", consoleCommand.getUsage());
          }
        }

        printInput();
        continue;
      }

      final ConsoleCommand consoleCommand = commands.get(commandName);
      if (consoleCommand == null) {
        LOGGER.warn("Unknown command: {}", commandName);
        printInput();
        continue;
      }

      final String[] args = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

      try {
        consoleCommand.execute(args);
      } catch (Throwable e) {
        if (!(e instanceof ConsoleCommandException exception)) {
          LOGGER.error("An error occurred while executing command: {}", command, e);
          printInput();
          continue;
        }

        exception.handle();
      }

      printInput();
    }
  }

  private void printInput() {
    System.out.print("\n> ");
  }
}
