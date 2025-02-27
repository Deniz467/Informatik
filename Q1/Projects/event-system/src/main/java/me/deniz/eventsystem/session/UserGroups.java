package me.deniz.eventsystem.session;

import java.util.Set;

public enum UserGroups {
  USER(new UserPermission[]{UserPermission.CREATE_EVENT}),
  ORGANIZER(new UserPermission[]{UserPermission.CREATE_EVENT}, UserGroups.USER),
  ADMIN(new UserPermission[]{}, UserGroups.ORGANIZER),
  OWNER(new UserPermission[]{}, UserGroups.ADMIN);

  private final Set<UserPermission> defaultPermissions;
  private final Set<UserGroups> subgroups;

  UserGroups(UserPermission[] defaultPermissions, UserGroups... subgroups) {
    this.defaultPermissions = Set.of(defaultPermissions);
    this.subgroups = Set.of(subgroups);
  }

  public boolean testPermission(UserPermission permission) {
    if (defaultPermissions.contains(permission)) {
      return true;
    }

    for (final UserGroups subgroup : subgroups) {
      if (subgroup.testPermission(permission)) {
        return true;
      }
    }

    return false;
  }
}
