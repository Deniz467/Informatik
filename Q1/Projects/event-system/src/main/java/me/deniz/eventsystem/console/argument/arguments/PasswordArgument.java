package me.deniz.eventsystem.console.argument.arguments;

import java.util.regex.Pattern;
import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;
import org.jetbrains.annotations.Nullable;

public class PasswordArgument extends ConsoleArgument<String> {

  private static final Pattern SAFE_PASSWORD_PATTERN = Pattern.compile(
      "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");

  public PasswordArgument(String id) {
    super(id);
  }

  @Override
  public String parse(String input) throws IllegalConsoleArgumentException {
    final String error = validate(input);

    if (error != null) {
      throw fail(error);
    }

    return input;
  }

  public static @Nullable String validate(String password) {
    if (!SAFE_PASSWORD_PATTERN.matcher(password).matches()) {
      return "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character";
    }

    if (password.length() > 63) { // BINARY is used in the database, which has a max size of 255.
      // A single character may take up to 4 bytes.
      // 64 * 4 = 256 bytes which is too large for the database,
      // so we limit it to 63 characters.
      return "Password must be at most 64 characters long";
    }

    return null;
  }
}
