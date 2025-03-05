package me.deniz.eventsystem.console.argument.arguments;

import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class UsernameArgument extends ConsoleArgument<String> {

  public UsernameArgument(String id) {
    super(id);
  }

  @Override
  public String parse(String input) throws IllegalConsoleArgumentException {
    if (input.length() < 3) {
      throw fail("Username must be at least 3 characters long");
    }

    if (input.length() > 16) {
      throw fail("Username must not exceed 16 characters");
    }

    if (input.contains(" ")) {
      throw fail("Username must not contain spaces");
    }

    return input;
  }
}
