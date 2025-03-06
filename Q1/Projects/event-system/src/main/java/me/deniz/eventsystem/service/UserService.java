package me.deniz.eventsystem.service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.db.table.UserTable;
import me.deniz.eventsystem.session.UserGroups;
import me.deniz.eventsystem.user.User;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  public CompletableFuture<@Nullable User> findByUsername(String username) {
    return UserTable.findByUsername(username)
        .exceptionally(throwable -> {
          LOGGER.error("Error finding user by username: {}", username, throwable);
          return null;
        });
  }

  public CompletableFuture<User> create(
      String username,
      String email,
      String password
  ) {
    return create(username, email, password, UserGroups.USER);
  }

  public CompletableFuture<User> create(
      String username,
      String email,
      String password,
      UserGroups groups
  ) {
    return UserTable.createUser(username, email, password.getBytes(StandardCharsets.UTF_8), groups)
        .exceptionally(throwable -> {
          LOGGER.error("Error creating user: {}", username, throwable);
          return null;
        });
  }

  public CompletableFuture<Long> count() {
    return UserTable.count()
        .exceptionally(throwable -> {
          LOGGER.error("Error counting users", throwable);
          return null;
        });
  }

  public CompletableFuture<Long> count(UserGroups groups) {
    return UserTable.count(groups)
        .exceptionally(throwable -> {
          LOGGER.error("Error counting users by group", throwable);
          return null;
        });
  }
}
