package me.deniz.eventsystem;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.config.EventSystemConfig;
import me.deniz.eventsystem.console.EventConsole;
import me.deniz.eventsystem.console.command.Commands;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.db.table.EventsTable;
import me.deniz.eventsystem.db.table.UserTable;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.user.User;
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
        UserTable.createIfNotExists()
    ).join();

    final EventService eventService = new EventService();
    final UserService userService = new UserService();

    final Event event = eventService.createEvent(
        "Test Event",
        "This is a test event.",
        "Test Location",
        ZonedDateTime.now(),
        ZonedDateTime.now().plusDays(1),
        10
    ).join();

    final User dummy = userService.create("dummy", "dummy@dum.com", "password").join();

    LOGGER.info("Created event: {}", event);
    LOGGER.info("Created user: {}", dummy);

    final EventConsole console = new EventConsole();
    Commands.register(console, eventService, userService);
    console.start();

    Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdown));
  }

  private static void shutdown() {
    DBConnection.INSTANCE.close();
  }
}