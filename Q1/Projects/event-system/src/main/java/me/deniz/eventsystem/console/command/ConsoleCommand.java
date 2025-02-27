package me.deniz.eventsystem.console.command;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.command.exceptions.ConsoleFailException;
import me.deniz.eventsystem.console.command.exceptions.IllegalArgumentsException;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleStateException;
import me.deniz.eventsystem.session.UserPermission;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ConsoleCommand {

  private final String name;
  private final String usage;
  private final String description;
  private final @Nullable UserPermission permission;
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  public ConsoleCommand(String name, String usage, String description,
      @Nullable UserPermission permission) {
    this.name = name;
    this.usage = usage;
    this.description = description;
    this.permission = permission;
  }

  public void execute(String[] args) {

  }

  public CompletableFuture<Void> executeAsync(String[] args) {
    return CompletableFuture.runAsync(() -> execute(args));
  }

  protected final void require(boolean expression, String message) {
    if (!expression) {
      throw new IllegalConsoleArgumentException(message);
    }
  }

  protected final void checkState(boolean expression, String message) {
    if (!expression) {
      throw new IllegalConsoleStateException(message);
    }
  }

  protected final void checkRequiredArgs(String[] args, int required) {
    checkRequiredArgs(args, required, required);
  }

  protected final void checkRequiredArgs(String[] args, int required, int max) {
    if (args.length < required || args.length > max) {
      throw new IllegalArgumentsException(args, this);
    }
  }

  protected final void fail(String message) {
    throw new ConsoleFailException(message);
  }

  public String getName() {
    return name;
  }

  public String getUsage() {
    return usage;
  }

  public String getDescription() {
    return description;
  }

  public @Nullable UserPermission getPermission() {
    return permission;
  }
}
