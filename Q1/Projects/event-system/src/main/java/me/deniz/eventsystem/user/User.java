package me.deniz.eventsystem.user;

import java.nio.charset.StandardCharsets;
import me.deniz.eventsystem.session.UserGroups;

public record User(
    long id,
    String username,
    String email,
    String password,
    UserGroups group
) {

  public User(long id, String username, String email, byte[] password, UserGroups group) {
    this(id, username, email, decryptPassword(password), group);
  }

  private static String decryptPassword(byte[] password) {
    return new String(password, StandardCharsets.UTF_8).trim();
  }

  public byte[] encryptPassword() {
    return password.trim().getBytes(StandardCharsets.UTF_8);
  }
}
