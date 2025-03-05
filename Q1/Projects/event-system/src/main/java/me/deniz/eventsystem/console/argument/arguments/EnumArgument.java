package me.deniz.eventsystem.console.argument.arguments;

import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class EnumArgument<E extends Enum<E>> extends ConsoleArgument<E> {

  private final E[] enums;

  public EnumArgument(String id, Class<E> enumClass) {
    super(id);
    this.enums = enumClass.getEnumConstants();
  }

  @Override
  public E parse(String input) throws IllegalConsoleArgumentException {
    for (final E e : enums) {
      if (e.name().equalsIgnoreCase(input) || e.name().replace("_", "-").equalsIgnoreCase(input)) {
        return e;
      }
    }

    throw fail("Invalid enum value: " + input);
  }
}
