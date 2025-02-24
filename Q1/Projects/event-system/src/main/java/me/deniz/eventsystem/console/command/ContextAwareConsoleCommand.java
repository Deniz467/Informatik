package me.deniz.eventsystem.console.command;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.EventConsole;

public abstract class ContextAwareConsoleCommand extends ConsoleCommand {

  private final String contextExitCommand;

  public ContextAwareConsoleCommand(String name, String usage, String description,
      String contextExitCommand) {
    super(name, usage, description);
    this.contextExitCommand = contextExitCommand;
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

  public final void enterContext(EventConsole console) {
    logger.info("Entered context: {}", getName());
    onEnterContext();


  }
}
