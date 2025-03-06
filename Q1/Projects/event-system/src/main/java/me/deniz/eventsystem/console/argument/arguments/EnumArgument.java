package me.deniz.eventsystem.console.argument.arguments;

import java.util.Arrays;
import me.deniz.eventsystem.console.argument.SimpleConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class EnumArgument<E extends Enum<E>> extends SimpleConsoleArgument<E> {

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

    if (enums.length > 10) {
      throw fail("Invalid enum value: " + input);
    } else {
      throw fail("Invalid enum value: " + input + ". Possible values: " + String.join(", ",
          Arrays.stream(enums).map(Enum::name).toArray(String[]::new)));
    }
  }
}
