package me.deniz.eventsystem.console.command;

import me.deniz.eventsystem.console.command.exceptions.ConsoleFailException;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ConsoleCommand {

  private final String name;
  private final String usage;
  private final String description;
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  public ConsoleCommand(String name, String usage, String description) {
    this.name = name;
    this.usage = usage;
    this.description = description;
  }

  public abstract void execute(String[] args);

  protected void require(boolean expression, String message) {
    if (!expression) {
      throw new IllegalConsoleArgumentException(message);
    }
  }

  protected void checkState(boolean expression, String message) {
    if (!expression) {
      throw new IllegalConsoleStateException(message);
    }
  }

  protected void fail(String message) {
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
}
