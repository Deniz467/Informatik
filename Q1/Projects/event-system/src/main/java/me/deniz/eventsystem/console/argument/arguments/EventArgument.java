package me.deniz.eventsystem.console.argument.arguments;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.List;
import java.util.Scanner;
import me.deniz.eventsystem.console.argument.ConsoleArgument;
import me.deniz.eventsystem.console.command.exceptions.IllegalConsoleArgumentException;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventArgument extends ConsoleArgument<Object, Event> {

  private static final Logger logger = LoggerFactory.getLogger(EventArgument.class);
  private final EventService eventService;

  public EventArgument(String id, EventService eventService) {
    super(id);
    this.eventService = eventService;
  }

  @Override
  public Object parse(String input) throws IllegalConsoleArgumentException {
    try {
      return Long.parseLong(input);
    } catch (NumberFormatException e) {
      return input;
    }
  }

  @Override
  public Event transform(Object parsed) throws IllegalConsoleArgumentException {
    if (parsed instanceof Long id) {
      return getById(id);
    } else if (parsed instanceof String title) {
      return getByTitle(title);
    } else {
      throw fail("Expected long or string but got " + parsed.getClass().getName());
    }
  }

  private Event getById(long id) {
    final Event event = eventService.findById(id).join();

    if (event == null) {
      throw fail("Event with id " + id + " not found");
    }

    return event;
  }

  private Event getByTitle(String title) {
    final List<Event> matchingEvents = eventService.findEventsByTitle(title).join();

    if (matchingEvents.isEmpty()) {
      throw fail("No event found with title '%s'".formatted(title));
    }

    if (matchingEvents.size() > 1) {
      return selectEventById(matchingEvents, title);
    } else {
      return matchingEvents.get(0);
    }
  }


  private Event selectEventById(List<Event> events, String title) {
    logger.info("Multiple events found with title '{}'", title);
    logger.info("Please specify the event ID");
    stringifyEvents(events).forEach(logger::info);

    logger.info("\n\nEnter the event ID:");
    final Scanner scanner = new Scanner(System.in);
    int eventId = scanner.nextInt();

    Event event = events.stream()
        .filter(e -> e.id() == eventId)
        .findFirst()
        .orElse(null);

    if (event == null) {
      logger.info("No event found with ID '{}'", eventId);

      return selectEventById(events, title);
    }

    return event;
  }

  private List<String> stringifyEvents(List<Event> events) {
    checkNotNull(events, "events");

    return events.stream()
        .map(event -> String.format("%d: %s", event.id(), event))
        .toList();
  }
}
