package me.deniz.eventsystem.console.command.account;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import me.deniz.eventsystem.console.command.ContextAwareConsoleCommand;
import me.deniz.eventsystem.console.command.event.CreateEventCommand;
import me.deniz.eventsystem.session.Session;
import me.deniz.eventsystem.session.SessionHolder;

public class LoginCommand extends ContextAwareConsoleCommand {

  private final AtomicBoolean loggedIn = new AtomicBoolean(false);
  private final AtomicBoolean loginInProgress = new AtomicBoolean(false);

  public LoginCommand() {
    super(
        "login",
        "login <username> <password>",
        "Logs in with the given username and password",
        "logout",
        null,
        List.of(new CreateEventCommand())
    );
  }

  @Override
  protected CompletableFuture<Void> doExecuteAsync(String[] args) {
    checkRequiredArgs(args, 2);
    checkState(!loginInProgress.get(), "A login is already in progress");
    checkState(!loggedIn.get(), "You are already logged in");

    loginInProgress.set(true);

    String username = args[0];
    String password = args[1];

    return login(username, password);
  }

  private CompletableFuture<Void> login(String username, String password) {
    return CompletableFuture.runAsync(() -> {
      // TODO: Implement login
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).thenRunAsync(() -> {
      loggedIn.set(true);
      loginInProgress.set(false);

      SessionHolder.setSession(new Session());
    }).exceptionally(throwable -> {
      logger.error("Failed to login", throwable);

      loggedIn.set(false);
      loginInProgress.set(false);
      return null;
    });
  }

  @Override
  public CompletableFuture<Void> onExitContextAsync() {
    return CompletableFuture.runAsync(() -> {
      // TODO: Implement logout
    }).thenRunAsync(() -> {
      loggedIn.set(false);
      SessionHolder.setSession(null);
    }).exceptionally(throwable -> {
      logger.error("Failed to logout", throwable);
      return null;
    });
  }
}
