package me.deniz.eventsystem.console.argument.arguments;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import me.deniz.eventsystem.console.argument.SimpleConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;

public class ZonedDateTimeArgument extends SimpleConsoleArgument<ZonedDateTime> {

  private static final DateTimeFormatter[] LENIENT_DATETIME_FORMATTERS = new DateTimeFormatter[]{
      DateTimeFormatter.ofPattern("d.M.yyyy H:mm"),
      DateTimeFormatter.ofPattern("d.M.yyyy HH:mm"),
      DateTimeFormatter.ofPattern("dd.MM.yyyy H:mm"),
      DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
  };

  private static final DateTimeFormatter[] LENIENT_DATE_FORMATTERS = new DateTimeFormatter[]{
      DateTimeFormatter.ofPattern("d.M.yyyy"),
      DateTimeFormatter.ofPattern("dd.MM.yyyy")
  };

  public ZonedDateTimeArgument(String id) {
    super(id);
  }

  @Override
  public ZonedDateTime parse(String input) throws IllegalConsoleArgumentException {
    try { // 2011-12-03T10:15:30+01:00[Europe/Paris]
      return ZonedDateTime.parse(input, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    } catch (DateTimeParseException ignored) {
    }

    try { // 2011-12-03T10:15:30
      return LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
          .atZone(ZoneId.systemDefault());
    } catch (DateTimeParseException ignored) {
    }

    for (final DateTimeFormatter formatter : LENIENT_DATETIME_FORMATTERS) {
      try {
        return LocalDateTime.parse(input, formatter).atZone(ZoneId.systemDefault());
      } catch (DateTimeParseException ignored) {
      }
    }

    for (final DateTimeFormatter formatter : LENIENT_DATE_FORMATTERS) {
      try {
        return LocalDate.parse(input, formatter).atStartOfDay(ZoneId.systemDefault());
      } catch (DateTimeParseException ignored) {
      }
    }

    throw fail(
        "Invalid date/time format. Please provide a valid date/time. Accepted formats include: 2011-12-03T10:15:30+01:00[Europe/Paris], 2011-12-03T10:15:30, 12.03.2025 16:00, 5.3.2025");
  }
}
