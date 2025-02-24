package me.deniz.eventsystem;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.config.EventSystemConfig;
import me.deniz.eventsystem.console.EventConsole;
import me.deniz.eventsystem.console.command.Commands;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.db.table.EventsTable;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void main(String[] args) {
    // Load config file
    EventSystemConfig.get();

    // Connect to the database
    DBConnection.INSTANCE.getClass();

    // Create Tables
    CompletableFuture.allOf(
        EventsTable.createIfNotExists()
    ).join();

    final EventService eventService = new EventService();

    final Event event = eventService.createEvent(
        "Test Event",
        "This is a test event.",
        "Test Location",
        ZonedDateTime.now(),
        ZonedDateTime.now().plusDays(1),
        10
    ).join();

    LOGGER.info("Created event: {}", event);

    final EventConsole console = new EventConsole();
    Commands.register(console);
    console.start();

    Runtime.getRuntime().addShutdownHook(new Thread(Main::shutdown));
  }

  private static void shutdown() {
    DBConnection.INSTANCE.close();
  }
}