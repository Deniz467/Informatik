package me.deniz.eventsystem.console.argument;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ArgumentParser {

  private static final Pattern ARGUMENT_PATTERN = Pattern.compile("\"([^\"]*)\"|(\\S+)");

  public static String[] parseArguments(String input) {
    final List<String> args = new ArrayList<>();
    final Matcher matcher = ARGUMENT_PATTERN.matcher(input);

    while (matcher.find()) {
      if (matcher.group(1) != null) { // "something in quotes with spaces"
        args.add(matcher.group(1));
      } else { // somethingWithoutSpaces
        args.add(matcher.group(2));
      }
    }

    return args.toArray(String[]::new);
  }
}
