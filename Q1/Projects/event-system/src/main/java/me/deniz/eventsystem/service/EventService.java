package me.deniz.eventsystem.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.annotation.Nullable;
import me.deniz.eventsystem.db.table.EventsTable;
import me.deniz.eventsystem.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);
  private static final int MAX_TITLE_LENGTH = 255;
  private static final int MAX_LOCATION_LENGTH = 255;

  public CompletableFuture<Event> createEvent(
      String title,
      String description,
      String location,
      ZonedDateTime start,
      @Nullable ZonedDateTime end,
      int maxParticipants
  ) {
    checkNotNull(title, "title");
    checkNotNull(description, "description");
    checkNotNull(location, "location");
    checkNotNull(start, "start");

    checkArgument(maxParticipants > 0, "maxParticipants must be greater than 0");
    checkArgument(end == null || end.isAfter(start), "end must be after start");
    checkArgument(title.length() <= MAX_TITLE_LENGTH, "title must be at most %s characters",
        MAX_TITLE_LENGTH);
    checkArgument(location.length() <= MAX_LOCATION_LENGTH,
        "location must be at most %s characters", MAX_LOCATION_LENGTH);

    return EventsTable.createEvent(
        title,
        description,
        location,
        Timestamp.from(start.toInstant()),
        end == null ? null : Timestamp.from(end.toInstant()),
        maxParticipants,
        start.getZone().getId()
    ).exceptionally(e -> {
      LOGGER.error("Failed to create event", e);
      return null;
    });
  }

  public CompletableFuture<List<Event>> findEventsByTitle(String title) {
    checkNotNull(title, "title");
    checkArgument(title.length() <= MAX_TITLE_LENGTH, "title must be at most %s characters",
        MAX_TITLE_LENGTH);

    return EventsTable.findEventsByTitle(title).exceptionally(e -> {
      LOGGER.error("Failed to find events by title", e);
      return List.of();
    });
  }

  public CompletableFuture<Event> updateEventTitle(Event event, String newTitle) {
    checkNotNull(event, "event");
    checkNotNull(newTitle, "newTitle");
    checkArgument(newTitle.length() <= MAX_TITLE_LENGTH, "newTitle must be at most %s characters",
        MAX_TITLE_LENGTH);

    return EventsTable.updateEventTitle(event, newTitle).exceptionally(e -> {
      LOGGER.error("Failed to update event title", e);
      return null;
    });
  }

  public CompletableFuture<Event> updateEventDescription(Event event, String newDescription) {
    checkNotNull(event, "event");
    checkNotNull(newDescription, "newDescription");

    return EventsTable.updateEventDescription(event, newDescription).exceptionally(e -> {
      LOGGER.error("Failed to update event description", e);
      return null;
    });
  }

  public CompletableFuture<Event> updateEventLocation(Event event, String newLocation) {
    checkNotNull(event, "event");
    checkNotNull(newLocation, "newLocation");
    checkArgument(newLocation.length() <= MAX_LOCATION_LENGTH, "newLocation must be at most %s characters",
        MAX_LOCATION_LENGTH);

    return EventsTable.updateEventLocation(event, newLocation).exceptionally(e -> {
      LOGGER.error("Failed to update event location", e);
      return null;
    });
  }

  public CompletableFuture<Event> updateEventStartDate(Event event, ZonedDateTime newStartDate) {
    checkNotNull(event, "event");
    checkNotNull(newStartDate, "newStartDate");

    return EventsTable.updateEventStartDate(event, newStartDate).exceptionally(e -> {
      LOGGER.error("Failed to update event start date", e);
      return null;
    });
  }

  public CompletableFuture<Event> updateEventEndDate(Event event, ZonedDateTime newEndDate) {
    checkNotNull(event, "event");
    checkNotNull(newEndDate, "newEndDate");

    return EventsTable.updateEventEndDate(event, newEndDate).exceptionally(e -> {
      LOGGER.error("Failed to update event end date", e);
      return null;
    });
  }

  public CompletableFuture<Event> updateEventMaxParticipants(Event event, int newMaxParticipants) {
    checkNotNull(event, "event");
    checkArgument(newMaxParticipants > 0, "newMaxParticipants must be greater than 0");

    return EventsTable.updateEventMaxParticipants(event, newMaxParticipants).exceptionally(e -> {
      LOGGER.error("Failed to update event max participants", e);
      return null;
    });
  }

  public CompletableFuture<Void> deleteEvent(Event event) {
    checkNotNull(event, "event");

    return EventsTable.deleteEvent(event).exceptionally(e -> {
      LOGGER.error("Failed to delete event", e);
      return null;
    });
  }

  public CompletableFuture<List<Event>> getAllEvents() {
    return EventsTable.getAllEvents().exceptionally(e -> {
      LOGGER.error("Failed to get all events", e);
      return List.of();
    });
  }

  public CompletableFuture<List<Event>> getAllActiveEvents() {
    return EventsTable.getAllActiveEvents().exceptionally(e -> {
      LOGGER.error("Failed to get all active events", e);
      return List.of();
    });
  }
}
