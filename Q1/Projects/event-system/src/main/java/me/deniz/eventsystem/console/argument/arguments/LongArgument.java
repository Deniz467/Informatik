package me.deniz.eventsystem.console.argument.arguments;

import me.deniz.eventsystem.console.argument.ConsoleArgument;

public class LongArgument extends ConsoleArgument<Long> {

  private final long min;
  private final long max;

  public LongArgument(String id) {
    this(id, Long.MIN_VALUE);
  }

  public LongArgument(String id, long min) {
    this(id, min, Long.MAX_VALUE);
  }

  public LongArgument(String id, long min, long max) {
    super(id);
    this.min = min;
    this.max = max;
  }

  @Override
  public Long parse(String input) {
    try {
      final long parsed = Long.parseLong(input);

      if (parsed < min) {
        throw fail("Value must be at least " + min);
      }

      if (parsed > max) {
        throw fail("Value must not exceed " + max);
      }

      return parsed;
    } catch (NumberFormatException e) {
      throw fail("Invalid long: " + input);
    }
  }
}
