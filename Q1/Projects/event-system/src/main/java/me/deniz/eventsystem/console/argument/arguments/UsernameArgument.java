package me.deniz.eventsystem.console.argument.arguments;

import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;
import org.jetbrains.annotations.Nullable;

public class UsernameArgument extends ConsoleArgument<String> {

  public UsernameArgument(String id) {
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

  public static @Nullable String validate(String input) {
    if (input.length() < 3) {
      return "Username must be at least 3 characters long";
    }

    if (input.length() > 16) {
      return "Username must not exceed 16 characters";
    }

    if (input.contains(" ")) {
      return "Username must not contain spaces";
    }

    return null;
  }
}
