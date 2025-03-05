package me.deniz.eventsystem.db.table;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.event.Event;

public final class EventsTable {

  public static final String TABLE_NAME = "events";

  public static final String ID = "id";
  public static final String TITLE = "title";
  public static final String DESCRIPTION = "description";
  public static final String LOCATION = "location";
  public static final String START = "start";
  public static final String END = "end";
  public static final String MAX_PARTICIPANTS = "max_participants";
  public static final String TIMEZONE = "timezone";

  public static CompletableFuture<Void> createIfNotExists() {
    return DBConnection.INSTANCE.executeStatementAsync(
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + ID + " BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,"
            + TITLE + " VARCHAR(255) NOT NULL,"
            + DESCRIPTION + " TEXT NOT NULL,"
            + LOCATION + " VARCHAR(255) NOT NULL,"
            + START + " TIMESTAMP NOT NULL,"
            + END + " TIMESTAMP NULL DEFAULT NULL,"
            + MAX_PARTICIPANTS + " INT NOT NULL,"
            + TIMEZONE + " VARCHAR(255) NOT NULL"
            + ");",
        preparedStatement -> {
        }
    );
  }

  public static CompletableFuture<Event> createEvent(
      String title,
      String description,
      String location,
      Timestamp start,
      Timestamp end,
      int maxParticipants,
      String timezone
  ) {
    return DBConnection.INSTANCE.updateAsync(
        "INSERT INTO " + TABLE_NAME + " (" + TITLE + ", " + DESCRIPTION + ", " + LOCATION + ", "
            + START + ", " + END + ", " + MAX_PARTICIPANTS + ", " + TIMEZONE
            + ") VALUES (?, ?, ?, ?, ?, ?, ?);",
        preparedStatement -> {
          preparedStatement.setString(1, title);
          preparedStatement.setString(2, description);
          preparedStatement.setString(3, location);
          preparedStatement.setTimestamp(4, start);
          preparedStatement.setTimestamp(5, end);
          preparedStatement.setInt(6, maxParticipants);
          preparedStatement.setString(7, timezone);
        },
        resultSet -> {
          ZoneId timezone1 = ZoneId.of(timezone);
          ZonedDateTime startZonedTIme = start.toLocalDateTime().atZone(timezone1);
          ZonedDateTime endZonedTime = end == null ? null : end.toLocalDateTime().atZone(timezone1);

          resultSet.next();
          return new Event(
              resultSet.getLong(1),
              title,
              description,
              location,
              startZonedTIme,
              endZonedTime,
              maxParticipants
          );
        }
    );
  }

  public static CompletableFuture<List<Event>> findEventsByTitle(String titleLike) {
    return DBConnection.INSTANCE.queryAsync(
        "SELECT * FROM " + TABLE_NAME + " WHERE " + TITLE + " LIKE ?;",
        preparedStatement -> preparedStatement.setString(1, titleLike),
        resultSet -> {
          final List<Event> events = new ArrayList<>();

          while (resultSet.next()) {
            ZoneId timezone = ZoneId.of(resultSet.getString(TIMEZONE));
            ZonedDateTime start = resultSet.getTimestamp(START).toLocalDateTime().atZone(timezone);
            ZonedDateTime end = resultSet.getTimestamp(END) == null ? null : resultSet.getTimestamp(END).toLocalDateTime().atZone(timezone);

            events.add(new Event(
                resultSet.getLong(ID),
                resultSet.getString(TITLE),
                resultSet.getString(DESCRIPTION),
                resultSet.getString(LOCATION),
                start,
                end,
                resultSet.getInt(MAX_PARTICIPANTS)
            ));
          }
          return events;
        }
    );
  }

  public static CompletableFuture<Event> updateEventTitle(Event event, String newTitle) {
    return DBConnection.INSTANCE.updateAsync(
        "UPDATE " + TABLE_NAME + " SET " + TITLE + " = ? WHERE " + ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setString(1, newTitle);
          preparedStatement.setLong(2, event.id());
        },
        resultSet -> event.withTitle(newTitle)
    );
  }

  public static CompletableFuture<Event> updateEventDescription(Event event, String newDescription) {
    return DBConnection.INSTANCE.updateAsync(
        "UPDATE " + TABLE_NAME + " SET " + DESCRIPTION + " = ? WHERE " + ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setString(1, newDescription);
          preparedStatement.setLong(2, event.id());
        },
        resultSet -> event.withDescription(newDescription)
    );
  }

  public static CompletableFuture<Event> updateEventLocation(Event event, String newLocation) {
    return DBConnection.INSTANCE.updateAsync(
        "UPDATE " + TABLE_NAME + " SET " + LOCATION + " = ? WHERE " + ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setString(1, newLocation);
          preparedStatement.setLong(2, event.id());
        },
        resultSet -> event.withLocation(newLocation)
    );
  }

  public static CompletableFuture<Event> updateEventStartDate(Event event, ZonedDateTime newStartDate) {
    return DBConnection.INSTANCE.updateAsync(
        "UPDATE " + TABLE_NAME + " SET " + START + " = ? WHERE " + ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setTimestamp(1, Timestamp.valueOf(newStartDate.toLocalDateTime()));
          preparedStatement.setLong(2, event.id());
        },
        resultSet -> event.withStart(newStartDate)
    );
  }

  public static CompletableFuture<Event> updateEventEndDate(Event event, ZonedDateTime newEndDate) {
    return DBConnection.INSTANCE.updateAsync(
        "UPDATE " + TABLE_NAME + " SET " + END + " = ? WHERE " + ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setTimestamp(1, Timestamp.valueOf(newEndDate.toLocalDateTime()));
          preparedStatement.setLong(2, event.id());
        },
        resultSet -> event.withEnd(newEndDate)
    );
  }

  public static CompletableFuture<Event> updateEventMaxParticipants(Event event, int newMaxParticipants) {
    return DBConnection.INSTANCE.updateAsync(
        "UPDATE " + TABLE_NAME + " SET " + MAX_PARTICIPANTS + " = ? WHERE " + ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setInt(1, newMaxParticipants);
          preparedStatement.setLong(2, event.id());
        },
        resultSet -> event.withMaxParticipants(newMaxParticipants)
    );
  }

  public static CompletableFuture<Void> deleteEvent(Event event) {
    return DBConnection.INSTANCE.updateAsync(
        "DELETE FROM " + TABLE_NAME + " WHERE " + ID + " = ?;",
        preparedStatement -> preparedStatement.setLong(1, event.id()),
        resultSet -> null
    );
  }

  public static CompletableFuture<List<Event>> getAllEvents() {
    return DBConnection.INSTANCE.queryAsync(
        "SELECT * FROM " + TABLE_NAME + ";",
        preparedStatement -> {
        },
        resultSet -> {
          final List<Event> events = new ArrayList<>();

          while (resultSet.next()) {
            ZoneId timezone = ZoneId.of(resultSet.getString(TIMEZONE));
            ZonedDateTime start = resultSet.getTimestamp(START).toLocalDateTime().atZone(timezone);
            ZonedDateTime end = resultSet.getTimestamp(END) == null ? null : resultSet.getTimestamp(END).toLocalDateTime().atZone(timezone);

            events.add(new Event(
                resultSet.getLong(ID),
                resultSet.getString(TITLE),
                resultSet.getString(DESCRIPTION),
                resultSet.getString(LOCATION),
                start,
                end,
                resultSet.getInt(MAX_PARTICIPANTS)
            ));
          }
          return events;
        }
    );
  }

  public static CompletableFuture<List<Event>> getAllActiveEvents() {
    return DBConnection.INSTANCE.queryAsync(
        "SELECT * FROM " + TABLE_NAME + " WHERE " + END + " IS NULL OR " + END + " > NOW();",
        preparedStatement -> {
        },
        resultSet -> {
          final List<Event> events = new ArrayList<>();

          while (resultSet.next()) {
            ZoneId timezone = ZoneId.of(resultSet.getString(TIMEZONE));
            ZonedDateTime start = resultSet.getTimestamp(START).toLocalDateTime().atZone(timezone);
            ZonedDateTime end = resultSet.getTimestamp(END) == null ? null : resultSet.getTimestamp(END).toLocalDateTime().atZone(timezone);

            events.add(new Event(
                resultSet.getLong(ID),
                resultSet.getString(TITLE),
                resultSet.getString(DESCRIPTION),
                resultSet.getString(LOCATION),
                start,
                end,
                resultSet.getInt(MAX_PARTICIPANTS)
            ));
          }
          return events;
        }
    );
  }
}