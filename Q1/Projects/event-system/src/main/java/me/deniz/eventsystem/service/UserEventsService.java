package me.deniz.eventsystem.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.db.table.UserEventsTable;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserEventsService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserEventsService.class);

  public CompletableFuture<Void> registerUserForEvent(long userId, long eventId) {
    return UserEventsTable.registerUserForEvent(userId, eventId).exceptionally(e -> {
      LOGGER.error("Failed to register user for event", e);
      return null;
    });
  }

  public CompletableFuture<Void> unregisterUserFromEvent(long userId, long eventId) {
    return UserEventsTable.unregisterUserFromEvent(userId, eventId).exceptionally(e -> {
      LOGGER.error("Failed to unregister user from event", e);
      return null;
    });
  }

  public CompletableFuture<List<Event>> getEventsForUser(long userId) {
    return UserEventsTable.getEventsForUser(userId).exceptionally(e -> {
      LOGGER.error("Failed to get events for user", e);
      return null;
    });
  }

  public CompletableFuture<List<User>> getUsersForEvent(long eventId) {
    return UserEventsTable.getUsersForEvent(eventId).exceptionally(e -> {
      LOGGER.error("Failed to get users for event", e);
      return null;
    });
  }
}
