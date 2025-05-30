package me.deniz.eventsystem;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.config.EventSystemConfig;
import me.deniz.eventsystem.console.EventConsole;
import me.deniz.eventsystem.console.command.Commands;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.db.table.EventsTable;
import me.deniz.eventsystem.db.table.UserEventsTable;
import me.deniz.eventsystem.db.table.UserTable;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.service.UserEventsService;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.user.SetupRootUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void main(String[] args) {
    // Load config file
    EventSystemConfig.get();

    // Connect to the database
    DBConnection.INSTANCE.getClass();

    // Create Tables
    CompletableFuture.allOf(
        EventsTable.createIfNotExists(),
        UserTable.createIfNotExists(),
        UserEventsTable.createIfNotExists()
    ).join();

    final EventService eventService = new EventService();
    final UserService userService = new UserService();
    final UserEventsService userEventsService = new UserEventsService();

    SetupRootUser.setup(userService);

    final EventConsole console = new EventConsole();
    Commands.register(console, eventService, userService, userEventsService);
    console.start();

    Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdown));
  }

  private static void shutdown() {
    DBConnection.INSTANCE.close();
  }
}