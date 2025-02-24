package me.deniz.eventsystem.console.command.account;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import me.deniz.eventsystem.console.command.ContextAwareConsoleCommand;
import me.deniz.eventsystem.session.SessionHolder;

public class LoginCommand extends ContextAwareConsoleCommand {

  private AtomicBoolean loggedIn = new AtomicBoolean(false);
  private AtomicBoolean loginInProgress = new AtomicBoolean(false);

  public LoginCommand() {
    super("login", "login <username> <password>", "Logs in with the given username and password",
        "logout");
  }

  @Override
  public void execute(String[] args) {
    require(args.length == 2, "An username and password must be provided");
    checkState(!loginInProgress.get(), "A login is already in progress");
    checkState(!loggedIn.get(), "You are already logged in");

    loginInProgress.set(true);

    String username = args[0];
    String password = args[1];

    login(username, password);
  }

  private CompletableFuture<Void> login(String username, String password) {
    return CompletableFuture.runAsync(() -> {
      // TODO: Implement login
    }).thenRunAsync(() -> {
      loggedIn.set(true);
      loginInProgress.set(false);
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
