package me.deniz.eventsystem.console.argument;

import java.util.Map;
import java.util.Optional;

public class ParsedArguments {

  private final Map<String, Object> arguments;

  public ParsedArguments(Map<String, Object> arguments) {
    this.arguments = arguments;
  }

  public <T> T get(String id) {
    return (T) arguments.get(id);
  }

  public <T> Optional<T> getOptional(String id) {
    return Optional.ofNullable(get(id));
  }

  public <T> T getOrDefault(String id, T defaultValue) {
    final Optional<T> optional = getOptional(id);
    return optional.orElse(defaultValue);
  }
}
