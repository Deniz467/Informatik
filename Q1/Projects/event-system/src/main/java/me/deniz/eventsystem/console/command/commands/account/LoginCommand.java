package me.deniz.eventsystem.console.command.commands.account;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.argument.arguments.StringArgument;
import me.deniz.eventsystem.console.command.ContextAwareConsoleCommand;
import me.deniz.eventsystem.console.command.commands.event.CreateEventCommand;
import me.deniz.eventsystem.console.command.commands.event.EditEventCommand;
import me.deniz.eventsystem.console.command.commands.event.ListUserEventsCommand;
import me.deniz.eventsystem.console.command.commands.user.CreateUserCommand;
import me.deniz.eventsystem.console.command.exceptions.InvalidCredentialsException;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.service.UserEventsService;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.session.Session;
import me.deniz.eventsystem.session.SessionHolder;

public class LoginCommand extends ContextAwareConsoleCommand {

  private final AtomicBoolean loggedIn = new AtomicBoolean(false);
  private final AtomicBoolean loginInProgress = new AtomicBoolean(false);
  private final UserService userService;

  public LoginCommand(EventService eventService, UserService userService,
      UserEventsService userEventsService) {
    super(
        "login",
        "login <username> <password>",
        "Logs in with the given username and password",
        "logout",
        null,
        List.of(new CreateEventCommand(eventService), new CreateUserCommand(userService),
            new EditEventCommand(eventService), new ListUserEventsCommand(userEventsService))
    );
    this.userService = userService;

    withArgument(new StringArgument("username"));
    withArgument(new StringArgument("password"));
  }

  @Override
  protected CompletableFuture<?> doExecuteAsync(ParsedArguments args) {
    checkState(!loginInProgress.get(), "A login is already in progress");
    checkState(!loggedIn.get(), "You are already logged in");

    loginInProgress.set(true);

    final String username = args.get("username");
    final String password = args.get("password");

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

          return new Session(user.id(), user.username(), user.password(), user.group());
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
