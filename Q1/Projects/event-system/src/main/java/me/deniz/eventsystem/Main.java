package me.deniz.eventsystem;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.config.EventSystemConfig;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.db.table.EventsTable;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;

public class Main {

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

    System.out.println(event);

    DBConnection.INSTANCE.close();
  }
}