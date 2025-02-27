package me.deniz.eventsystem.console.command.user;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.session.UserGroups;
import me.deniz.eventsystem.session.UserPermission;

public final class CreateUserCommand extends ConsoleCommand {

  private static final Pattern emailPattern = Pattern.compile("^.+@.+\\..+$");

  public CreateUserCommand() {
    super(
        "create-user",
        "<username> <password> <email> [group]",
        "Create a new user",
        UserPermission.CREATE_USER
    );
  }

  @Override
  public CompletableFuture<Void> executeAsync(String[] args) {
    return CompletableFuture.runAsync(() -> {
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


    });
  }
}
