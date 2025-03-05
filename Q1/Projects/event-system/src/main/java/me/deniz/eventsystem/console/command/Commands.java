package me.deniz.eventsystem.console.command;

import me.deniz.eventsystem.console.EventConsole;
import me.deniz.eventsystem.console.command.commands.account.LoginCommand;
import me.deniz.eventsystem.console.command.commands.core.ExitCommand;
import me.deniz.eventsystem.service.UserService;

public final class Commands {

  public static void register(EventConsole console, UserService userService) {
    console.registerCommand(new ExitCommand());
    console.registerCommand(new LoginCommand(userService));
  }
}
