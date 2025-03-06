package me.deniz.eventsystem.console.argument.arguments;

import me.deniz.eventsystem.console.argument.SimpleConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class StringArgument extends SimpleConsoleArgument<String> {

  public StringArgument(String id) {
    super(id);
  }

  @Override
  public String parse(String input) throws IllegalConsoleArgumentException {
    return input;
  }
}
