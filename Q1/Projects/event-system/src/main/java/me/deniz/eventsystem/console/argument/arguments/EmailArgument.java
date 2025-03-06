package me.deniz.eventsystem.console.argument.arguments;

import java.util.regex.Pattern;
import me.deniz.eventsystem.console.argument.SimpleConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;
import org.jetbrains.annotations.Nullable;

public class EmailArgument extends SimpleConsoleArgument<String> {

  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

  public EmailArgument(String id) {
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

  public static @Nullable String validate(String email) {
    final boolean valid = EMAIL_PATTERN.matcher(email).matches();

    if (!valid) {
      return "Invalid email address: " + email;
    }

    return null;
  }
}
