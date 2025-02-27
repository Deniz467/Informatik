package me.deniz.eventsystem.console.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.deniz.eventsystem.console.command.exceptions.ConsoleCommandException;
import me.deniz.eventsystem.console.command.help.HelpCommand;

public abstract class ContextAwareConsoleCommand extends ConsoleCommand {

  private final String contextExitCommand;
  private final Map<String, ConsoleCommand> contextCommands;

  public ContextAwareConsoleCommand(String name, String usage, String description,
      String contextExitCommand, List<ConsoleCommand> contextCommands) {
    super(name, usage, description);
    this.contextExitCommand = contextExitCommand;
    this.contextCommands = contextCommands.stream()
        .collect(Collectors.toMap(ConsoleCommand::getName, Function.identity()));
  }

  protected void doExecute(String[] args) {
  }

  protected CompletableFuture<Void> doExecuteAsync(String[] args) {
    return CompletableFuture.runAsync(() -> doExecute(args));
  }

  @Override
  public final void execute(String[] args) {
  }

  @Override
  public final CompletableFuture<Void> executeAsync(String[] args) {
    logger.info("Entering context: {}", getName());
    return onEnterContextAsync()
        .thenCompose($ -> doExecuteAsync(args))
        .thenCompose($ -> runContextLoop())
        .thenCompose($ -> onExitContextAsync());
  }

  protected CompletableFuture<Void> runContextLoop() {
    return CompletableFuture.runAsync(() -> {
      Scanner scanner = new Scanner(System.in);
      String input;
      System.out.print(getName() + " > ");
      while (scanner.hasNextLine()) {
        input = scanner.nextLine();
        if (input.equalsIgnoreCase(contextExitCommand)) {
          logger.info("Exiting context: {}", getName());
          break;
        }

        if (HelpCommand.parseMaybeHelp(logger, input, contextCommands)) {
          System.out.print(getName() + " > ");
          continue;
        }

        final String[] splittedCommand = input.split(" ");
        final String commandName = splittedCommand[0];
        final ConsoleCommand consoleCommand = contextCommands.get(commandName);

        if (consoleCommand == null) {
          logger.warn("Unknown command: {}", commandName);
          System.out.print(getName() + " > ");
          continue;
        }

        final String[] commandArgs = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        try {
          consoleCommand.executeAsync(commandArgs).join();
        } catch (Throwable e) {
          Throwable throwable = e;
          if (throwable instanceof CompletionException) {
            throwable = throwable.getCause();
          }
          if (!(throwable instanceof ConsoleCommandException exception)) {
            logger.error("An error occurred while executing command: {}", input, throwable);
            System.out.print(getName() + " > ");
            continue;
          }
          exception.handle();
        }

        System.out.print(getName() + " > ");
      }
    });
  }

  public CompletableFuture<Void> onEnterContextAsync() {
    return CompletableFuture.runAsync(this::onEnterContext);
  }

  public void onEnterContext() {
  }

  public CompletableFuture<Void> onExitContextAsync() {
    return CompletableFuture.runAsync(this::onExitContext);
  }

  public void onExitContext() {
  }
}
