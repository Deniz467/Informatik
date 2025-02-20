package me.deniz.eventsystem.db.table;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;
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
}