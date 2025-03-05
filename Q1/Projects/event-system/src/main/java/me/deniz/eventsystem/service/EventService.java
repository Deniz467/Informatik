package me.deniz.eventsystem.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import me.deniz.eventsystem.db.table.EventsTable;
import me.deniz.eventsystem.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventService.class);

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
    checkArgument(title.length() <= 255, "title must be at most 255 characters");
    checkArgument(location.length() <= 255, "location must be at most 255 characters");

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
}
