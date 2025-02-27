package me.deniz.eventsystem.session;

import java.util.Set;

public record Session(
    String username,
    String password,
    UserGroups group,
    Set<UserPermission> additionalPermissions
) {

  public Session(String username, String password, UserGroups group) {
    this(username, password, group, Set.of());
  }

  public Session() {
    this(null, null, UserGroups.USER, Set.of());
  }

  public boolean hasPermission(UserPermission permission) {
    return group.testPermission(permission) || additionalPermissions.contains(permission);
  }

  public boolean hasGroup(UserGroups group) {
    return this.group == group;
  }

  public boolean hasAnyGroup(UserGroups... groups) {
    for (final UserGroups g : groups) {
      if (hasGroup(g)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasAllGroups(UserGroups... groups) {
    for (final UserGroups g : groups) {
      if (!hasGroup(g)) {
        return false;
      }
    }

    return true;
  }

  public boolean hasAnyPermission(UserPermission... permissions) {
    for (final UserPermission p : permissions) {
      if (hasPermission(p)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasAllPermissions(UserPermission... permissions) {
    for (final UserPermission p : permissions) {
      if (!hasPermission(p)) {
        return false;
      }
    }

    return true;
  }
}
