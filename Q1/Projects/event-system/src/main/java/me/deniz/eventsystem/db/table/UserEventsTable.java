package me.deniz.eventsystem.db.table;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.session.UserGroups;
import me.deniz.eventsystem.user.User;

public final class UserEventsTable {

  public static final String TABLE_NAME = "user_events";
  public static final String USER_ID = "user_id";
  public static final String EVENT_ID = "event_id";

  public static CompletableFuture<Void> createIfNotExists() {
    return DBConnection.INSTANCE.executeStatementAsync(
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + USER_ID + " BIGINT(20) NOT NULL, "
            + EVENT_ID + " BIGINT(20) NOT NULL, "
            + "PRIMARY KEY (" + USER_ID + ", " + EVENT_ID + "), "
            + "FOREIGN KEY (" + USER_ID + ") REFERENCES users(id) ON DELETE CASCADE, "
            + "FOREIGN KEY (" + EVENT_ID + ") REFERENCES events(id) ON DELETE CASCADE"
            + ");",
        preparedStatement -> {
        });
  }

  public static CompletableFuture<Void> registerUserForEvent(long userId, long eventId) {
    return DBConnection.INSTANCE.updateAsync(
        "INSERT INTO " + TABLE_NAME + " (" + USER_ID + ", " + EVENT_ID + ") VALUES (?, ?);",
        preparedStatement -> {
          preparedStatement.setLong(1, userId);
          preparedStatement.setLong(2, eventId);
        },
        resultSet -> null
    );
  }

  public static CompletableFuture<Void> unregisterUserFromEvent(long userId, long eventId) {
    return DBConnection.INSTANCE.updateAsync(
        "DELETE FROM " + TABLE_NAME + " WHERE " + USER_ID + " = ? AND " + EVENT_ID + " = ?;",
        preparedStatement -> {
          preparedStatement.setLong(1, userId);
          preparedStatement.setLong(2, eventId);
        },
        resultSet -> null
    );
  }

  public static CompletableFuture<List<Event>> getEventsForUser(long userId) {
    return DBConnection.INSTANCE.queryAsync(
        "SELECT ev.* FROM " + EventsTable.TABLE_NAME + " ev " +
            "INNER JOIN " + TABLE_NAME + " ue ON ev." + EventsTable.ID + " = ue." + EVENT_ID + " " +
            "WHERE ue." + USER_ID + " = ?;",
        ps -> ps.setLong(1, userId),
        rs -> {
          final List<Event> events = new ArrayList<>();

          while (rs.next()) {
            ZoneId timezone = ZoneId.of(rs.getString(EventsTable.TIMEZONE));
            ZonedDateTime start = rs.getTimestamp(EventsTable.START).toLocalDateTime().atZone(timezone);
            ZonedDateTime end = rs.getTimestamp(EventsTable.END) == null
                ? null
                : rs.getTimestamp(EventsTable.END).toLocalDateTime().atZone(timezone);
            Event event = new Event(
                rs.getLong(EventsTable.ID),
                rs.getString(EventsTable.TITLE),
                rs.getString(EventsTable.DESCRIPTION),
                rs.getString(EventsTable.LOCATION),
                start,
                end,
                rs.getInt(EventsTable.MAX_PARTICIPANTS)
            );
            events.add(event);
          }
          return events;
        }
    );
  }

  public static CompletableFuture<List<User>> getUsersForEvent(long eventId) {
    return DBConnection.INSTANCE.queryAsync(
        "SELECT user.* FROM " + UserTable.TABLE_NAME + " user " +
            "INNER JOIN " + TABLE_NAME + " ue ON user.id = ue." + USER_ID + " " +
            "WHERE ue." + EVENT_ID + " = ?;",
        ps -> ps.setLong(1, eventId),
        rs -> {
          final List<User> users = new ArrayList<>();

          while (rs.next()) {
            User user = new User(
                rs.getLong(UserTable.ID),
                rs.getString(UserTable.USERNAME),
                rs.getString(UserTable.EMAIL),
                rs.getBytes(UserTable.PASSWORD),
                UserGroups.valueOf(rs.getString(UserTable.GROUP))
            );
            users.add(user);
          }
          return users;
        }
    );
  }
}
