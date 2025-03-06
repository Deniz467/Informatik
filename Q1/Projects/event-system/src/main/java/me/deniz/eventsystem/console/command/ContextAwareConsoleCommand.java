package me.deniz.eventsystem.console.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import me.deniz.eventsystem.console.argument.ArgumentParser;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.command.exceptions.ConsoleCommandException;
import me.deniz.eventsystem.console.command.help.HelpCommand;
import me.deniz.eventsystem.session.Session;
import me.deniz.eventsystem.session.SessionHolder;
import me.deniz.eventsystem.session.UserPermission;
import org.jetbrains.annotations.Nullable;

public abstract class ContextAwareConsoleCommand extends ConsoleCommand {

  private final String contextExitCommand;
  private final Map<String, ConsoleCommand> contextCommands;

  public ContextAwareConsoleCommand(String name, String usage, String description,
      String contextExitCommand, @Nullable UserPermission permission,
      List<ConsoleCommand> contextCommands) {
    super(name, usage, description, permission);
    this.contextExitCommand = contextExitCommand;
    this.contextCommands = contextCommands.stream()
        .collect(Collectors.toMap(ConsoleCommand::getName, Function.identity()));
  }

  protected void withContextCommand(ConsoleCommand command) {
    contextCommands.put(command.getName(), command);
  }

  protected void doExecute(ParsedArguments args) {
  }

  protected CompletableFuture<?> doExecuteAsync(ParsedArguments args) {
    return CompletableFuture.runAsync(() -> doExecute(args));
  }

  @Override
  public final void execute(ParsedArguments args) {
  }

  @Override
  public final CompletableFuture<?> executeAsync(ParsedArguments args) {
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
          break;
        }

        if (HelpCommand.parseMaybeHelp(logger, input, contextCommands, contextExitCommand)) {
          System.out.print(getName() + " > ");
          continue;
        }

        final String[] splittedCommand = input.split(" ");
        final String commandName = splittedCommand[0];
        final ConsoleCommand consoleCommand = contextCommands.get(commandName);
        final Session session = SessionHolder.getSession();

        if (consoleCommand == null
            || session != null && !session.hasPermission(consoleCommand.getPermission())) {
          logger.warn("Unknown command: {}", commandName);
          System.out.print(getName() + " > ");
          continue;
        }

        final String[] commandArgs = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        try {
          final String argInput = String.join(" ", commandArgs);
          final String[] splittedArgs = ArgumentParser.parseArguments(argInput);
          final ParsedArguments parsedArguments = consoleCommand.parseArguments(splittedArgs);

          consoleCommand.executeAsync(parsedArguments).join();
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
