package me.deniz.eventsystem.console.command;

import me.deniz.eventsystem.console.EventConsole;
import me.deniz.eventsystem.console.command.commands.account.LoginCommand;
import me.deniz.eventsystem.console.command.commands.core.ExitCommand;
import me.deniz.eventsystem.console.command.commands.event.ListEventsGlobalCommand;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.service.UserEventsService;
import me.deniz.eventsystem.service.UserService;

public final class Commands {

  public static void register(EventConsole console, EventService eventService,
      UserService userService, UserEventsService userEventsService) {
    console.registerCommand(new ExitCommand());
    console.registerCommand(new LoginCommand(eventService, userService, userEventsService));
    console.registerCommand(new ListEventsGlobalCommand(eventService));
  }
}
