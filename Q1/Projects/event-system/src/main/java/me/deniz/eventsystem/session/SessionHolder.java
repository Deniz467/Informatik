package me.deniz.eventsystem.session;

import static com.google.common.base.Preconditions.checkState;

import org.jetbrains.annotations.Nullable;

public final class SessionHolder {

  private static volatile Session session;

  public static @Nullable Session getSession() {
    return session;
  }

  public static Session requiredSession() {
    Session session = getSession();
    checkState(session != null, "Session is not set");
    return session;
  }

  public static void setSession(Session session) {
    SessionHolder.session = session;
  }
}
