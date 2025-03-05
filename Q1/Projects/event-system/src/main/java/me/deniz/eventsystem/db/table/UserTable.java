package me.deniz.eventsystem.db.table;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.db.DBConnection;
import me.deniz.eventsystem.session.UserGroups;
import me.deniz.eventsystem.user.User;
import org.jetbrains.annotations.Nullable;

public final class UserTable {

  public static final String TABLE_NAME = "users";

  public static final String ID = "id";
  public static final String USERNAME = "username";
  public static final String EMAIL = "email";
  public static final String PASSWORD = "password";
  public static final String GROUP = "group";


  public static CompletableFuture<Void> createIfNotExists() {
    final String groupEnumValues = "'" + String.join("', '",
        Arrays.stream(UserGroups.values()).map(UserGroups::name).toList()) + "'";

    return DBConnection.INSTANCE.executeStatementAsync(
        "CREATE TABLE IF NOT EXISTS `" + TABLE_NAME + "` ("
            + "`" + ID + "` BIGINT NOT NULL AUTO_INCREMENT,"
            + "`" + USERNAME + "` CHAR(16) NOT NULL,"
            + "`" + EMAIL + "` VARCHAR(252) NOT NULL,"
            + "`" + PASSWORD + "` BINARY(252) NOT NULL,"
            + "`" + GROUP + "` ENUM(" + groupEnumValues + ") NOT NULL DEFAULT '" + UserGroups.USER
            + "',"
            + "PRIMARY KEY (`id`) USING BTREE,"
            + "UNIQUE INDEX `unique username` (`" + USERNAME + "`) USING BTREE"
            + ")",
        preparedStatement -> {
        }
    );
  }

  public static CompletableFuture<User> createUser(
      String username,
      String email,
      byte[] password,
      UserGroups group
  ) {
    return DBConnection.INSTANCE.updateAsync(
        "INSERT INTO `" + TABLE_NAME + "` (`" + USERNAME + "`, `" + EMAIL + "`, `" + PASSWORD
            + "`, `" + GROUP + "`) VALUES (?, ?, ?, ?);",
        preparedStatement -> {
          preparedStatement.setString(1, username);
          preparedStatement.setString(2, email);
          preparedStatement.setBytes(3, password);
          preparedStatement.setString(4, group.name());
        },
        resultSet -> {
          resultSet.next();
          return new User(
              resultSet.getLong(1),
              username,
              email,
              password,
              group
          );
        }
    );
  }

  public static CompletableFuture<@Nullable User> findByUsername(String username) {
    return DBConnection.INSTANCE.queryAsync(
        "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(`" + USERNAME + "`) LIKE ?",
        preparedStatement -> {
          preparedStatement.setString(1, username.toLowerCase());
        },
        resultSet -> {
          if (resultSet.next()) {
            return new User(
                resultSet.getLong(ID),
                resultSet.getString(USERNAME),
                resultSet.getString(EMAIL),
                resultSet.getBytes(PASSWORD),
                UserGroups.valueOf(resultSet.getString(GROUP))
            );
          }

          return null;
        }
    );
  }
}
