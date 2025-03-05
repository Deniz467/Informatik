package me.deniz.eventsystem.console.argument.arguments;

import java.util.regex.Pattern;
import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class PasswordArgument extends ConsoleArgument<String> {

  private static final Pattern SAFE_PASSWORD_PATTERN = Pattern.compile(
      "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");

  public PasswordArgument(String id) {
    super(id);
  }

  @Override
  public String parse(String input) throws IllegalConsoleArgumentException {
    if (!SAFE_PASSWORD_PATTERN.matcher(input).matches()) {
      throw fail(
          "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
    }

    return input;
  }
}
