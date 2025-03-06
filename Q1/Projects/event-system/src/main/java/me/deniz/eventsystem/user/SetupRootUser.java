package me.deniz.eventsystem.user;

import java.util.Scanner;
import me.deniz.eventsystem.console.argument.arguments.EmailArgument;
import me.deniz.eventsystem.console.argument.arguments.PasswordArgument;
import me.deniz.eventsystem.console.argument.arguments.UsernameArgument;
import me.deniz.eventsystem.service.UserService;
import me.deniz.eventsystem.session.UserGroups;
import org.jetbrains.annotations.Blocking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SetupRootUser {

  private static final Logger LOGGER = LoggerFactory.getLogger(SetupRootUser.class);

  @Blocking
  public static void setup(UserService userService) {
    final long ownerAmount = userService.count(UserGroups.OWNER).join();

    if (ownerAmount <= 0) {
      LOGGER.warn("No owner users found. Creating root user...");

      final Scanner scanner = new Scanner(System.in);
      final String username = getUsername(scanner);
      final String email = getEmail(scanner);
      final String password = getPassword(scanner);

      final User owner = userService.create(username, email, password, UserGroups.OWNER).join();
      LOGGER.info("Created root user: {}", owner);
    }
  }

  private static String getUsername(Scanner scanner) {
    System.out.print("Enter root username: ");
    final String username = scanner.nextLine();
    final String error = UsernameArgument.validate(username);

    if (error != null) {
      LOGGER.error(error);
      return getUsername(scanner);
    }

    return username;
  }

  private static String getEmail(Scanner scanner) {
    System.out.print("Enter root email: ");
    final String email = scanner.nextLine();
    final String error = EmailArgument.validate(email);

    if (error != null) {
      LOGGER.error(error);
      return getEmail(scanner);
    }

    return email;
  }

  private static String getPassword(Scanner scanner) {
    System.out.print("Enter root password: ");
    final String password = scanner.nextLine();
    final String error = PasswordArgument.validate(password);

    if (error != null) {
      LOGGER.error(error);
      return getPassword(scanner);
    }

    return password;
  }
}
