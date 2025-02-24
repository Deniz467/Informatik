package me.deniz.eventsystem.console.command;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public abstract class ContextAwareConsoleCommand extends ConsoleCommand {

  private final String contextExitCommand;

  public ContextAwareConsoleCommand(String name, String usage, String description,
      String contextExitCommand) {
    super(name, usage, description);
    this.contextExitCommand = contextExitCommand;
  }

  protected void doExecute(String[] args) {
  }

  protected CompletableFuture<Void> doExecuteAsync(String[] args) {
    return CompletableFuture.runAsync(() -> doExecute(args));
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

        logger.info("Context {}: Received command: {}", getName(), input);
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
