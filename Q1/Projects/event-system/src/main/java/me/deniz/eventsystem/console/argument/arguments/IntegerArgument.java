package me.deniz.eventsystem.console.argument.arguments;

import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class IntegerArgument extends ConsoleArgument<Integer> {

  private final int min;
  private final int max;

  public  IntegerArgument(String id) {
    this(id, Integer.MIN_VALUE);
  }

  public IntegerArgument(String id, int min) {
    this(id, min, Integer.MAX_VALUE);
  }

  public IntegerArgument(String id, int min, int max) {
    super(id);
    this.min = min;
    this.max = max;
  }

  @Override
  public Integer parse(String input) throws IllegalConsoleArgumentException {
    try {
      final int parsed = Integer.parseInt(input);

      if (parsed < min) {
        throw fail("Value must be at least " + min);
      }

      if (parsed > max) {
        throw fail("Value must not exceed " + max);
      }

      return parsed;
    } catch (NumberFormatException e) {
      throw fail("Invalid integer: " + input);
    }
  }
}
