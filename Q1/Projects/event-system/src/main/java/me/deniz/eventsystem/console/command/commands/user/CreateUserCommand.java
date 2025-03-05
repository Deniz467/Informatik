package me.deniz.eventsystem.console.command.commands.user;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.session.SessionHolder;
import me.deniz.eventsystem.session.UserGroups;
import me.deniz.eventsystem.session.UserPermission;

public final class CreateUserCommand extends ConsoleCommand {

  private static final Pattern emailPattern = Pattern.compile("^.+@.+\\..+$");
  private final UserService userService;

  public CreateUserCommand(UserService userService) {
    super(
        "create-user",
        "<username> <password> <email> [group]",
        "Create a new user",
        UserPermission.CREATE_USER
    );
    this.userService = userService;
  }

  @Override
  public CompletableFuture<?> executeAsync(String[] args) {
    checkRequiredArgs(args, 3, 4);
    final String username = args[0];
    final String password = args[1];
    final String email = args[2];
    final String rawGroup = args.length > 3 ? args[3] : null;

    require(username.length() >= 3, "Username must be at least 3 characters long");
    require(username.length() <= 16, "Username must not exceed 16 characters");
    require(password.length() >= 6, "Password must be at least 6 characters long");
    require(password.length() <= 32, "Password must not exceed 32 characters");
    require(email.length() <= 255, "Email must not exceed 255 characters");
    require(emailPattern.matcher(email).matches(), "Invalid email format");

    final UserGroups group = rawGroup != null
        ? UserGroups.valueOf(rawGroup.replace('-', '_').toUpperCase())
        : UserGroups.USER;

    final UserGroups creatorGroup = SessionHolder.requiredSession().group();

    System.out.println("Group ordinal: " + group.ordinal());
    System.out.println("Creator ordinal: " + creatorGroup.ordinal());

    checkState(group.ordinal() < creatorGroup.ordinal(),
        "Cannot create a user with a higher group than your own");

    return userService.create(username, email, password, group)
        .thenAcceptAsync(result -> {
          logger.info("Created user: {}", result);
        });
  }
}
