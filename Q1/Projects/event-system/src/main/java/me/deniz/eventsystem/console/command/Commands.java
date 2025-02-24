package me.deniz.eventsystem.console.command;

import me.deniz.eventsystem.console.EventConsole;
import me.deniz.eventsystem.console.command.core.ExitCommand;

public final class Commands {

  public static void register(EventConsole console) {
    console.registerCommand(new ExitCommand());
  }
}
