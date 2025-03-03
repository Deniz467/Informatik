package me.deniz.eventsystem.console.command.account;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import me.deniz.eventsystem.console.command.ContextAwareConsoleCommand;
import me.deniz.eventsystem.console.command.event.CreateEventCommand;
import me.deniz.eventsystem.console.command.exceptions.InvalidCredentialsException;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.session.Session;
import me.deniz.eventsystem.session.SessionHolder;

public class LoginCommand extends ContextAwareConsoleCommand {

  private final AtomicBoolean loggedIn = new AtomicBoolean(false);
  private final AtomicBoolean loginInProgress = new AtomicBoolean(false);
  private final UserService userService;

  public LoginCommand(UserService userService) {
    super(
        "login",
        "login <username> <password>",
        "Logs in with the given username and password",
        "logout",
        null,
        List.of(new CreateEventCommand())
    );
    this.userService = userService;
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
    return userService.findByUsername(username)
        .thenApplyAsync(user -> {
          if (user == null) {
            throw new InvalidCredentialsException();
          }

          if (!user.password().equals(password)) {
            throw new InvalidCredentialsException();
          }

          return new Session(user.username(), user.password(), user.group());
        })
        .thenAcceptAsync(session -> {
          loggedIn.set(true);
          loginInProgress.set(false);

          SessionHolder.setSession(session);
        }).exceptionally(throwable -> {
          loggedIn.set(false);
          loginInProgress.set(false);

          throw throwable instanceof RuntimeException e ? e : new RuntimeException(throwable);
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
