package me.deniz.eventsystem.console.command.commands.user;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.argument.arguments.EmailArgument;
import me.deniz.eventsystem.console.argument.arguments.EnumArgument;
import me.deniz.eventsystem.console.argument.arguments.PasswordArgument;
import me.deniz.eventsystem.console.argument.arguments.UsernameArgument;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.session.SessionHolder;
import me.deniz.eventsystem.session.UserGroups;
import me.deniz.eventsystem.session.UserPermission;

public final class CreateUserCommand extends ConsoleCommand {

  private final UserService userService;

  public CreateUserCommand(UserService userService) {
    super(
        "create-user",
        "<username> <password> <email> [group]",
        "Create a new user",
        UserPermission.CREATE_USER
    );
    this.userService = userService;

    withArgument(new UsernameArgument("username"));
    withArgument(new PasswordArgument("password"));
    withArgument(new EmailArgument("email"));
    withOptionalArgument(new EnumArgument<>("group", UserGroups.class));
  }

  @Override
  public CompletableFuture<?> executeAsync(ParsedArguments args) {
    final String username = args.get("username");
    final String password = args.get("password");
    final String email = args.get("email");
    final UserGroups group = args.getOrDefault("group", UserGroups.USER);

    final UserGroups creatorGroup = SessionHolder.requiredSession().group();

    System.out.println("Group ordinal: " + group.ordinal());
    System.out.println("Creator ordinal: " + creatorGroup.ordinal());

    checkState(group.ordinal() < creatorGroup.ordinal(),
        "Cannot create a user with a higher group than your own");

    return userService.create(username, email, password, group)
        .thenAcceptAsync(result -> logger.info("Created user: {}", result));
  }
}
