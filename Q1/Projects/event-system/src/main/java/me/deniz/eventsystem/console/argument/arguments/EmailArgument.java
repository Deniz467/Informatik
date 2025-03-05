package me.deniz.eventsystem.console.argument.arguments;

import java.util.regex.Pattern;
import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class EmailArgument extends ConsoleArgument<String> {

  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

  public EmailArgument(String id) {
    super(id);
  }

  @Override
  public String parse(String input) throws IllegalConsoleArgumentException {
    if (!EMAIL_PATTERN.matcher(input).matches()) {
      throw fail("Invalid email address: " + input);
    }

    return input;
  }
}
