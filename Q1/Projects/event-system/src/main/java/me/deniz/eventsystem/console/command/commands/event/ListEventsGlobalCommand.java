package me.deniz.eventsystem.console.command.commands.event;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListEventsGlobalCommand extends ConsoleCommand {

  private static final Logger logger = LoggerFactory.getLogger(ListEventsGlobalCommand.class);
  private static final int PAGE_SIZE = 5;
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  private final EventService eventService;

  public ListEventsGlobalCommand(EventService eventService) {
    super("list-events", "", "List all events with pagination", null);
    this.eventService = eventService;
  }

  @Override
  public CompletableFuture<?> executeAsync(ParsedArguments args) {
    return eventService.getAllActiveEvents().thenAcceptAsync(events -> listEvents(events, logger));
  }

  public static void listEvents(List<Event> events, Logger logger) {
    if (events.isEmpty()) {
      logger.info("No events available.");
      return;
    }

    final int totalEvents = events.size();
    final int totalPages = (totalEvents + PAGE_SIZE - 1) / PAGE_SIZE;
    int currentPage = 0;
    final Scanner scanner = new Scanner(System.in);

    while (currentPage < totalPages) {
      final int start = currentPage * PAGE_SIZE;
      final int end = Math.min(start + PAGE_SIZE, totalEvents);

      logger.info("====================================");
      logger.info("Page {}/{}", currentPage + 1, totalPages);
      logger.info("====================================");


      for (int i = start; i < end; i++) {
        Event event = events.get(i);
        String formattedStart = event.start().format(DATE_FORMATTER);
        String formattedEnd = event.end() != null ? event.end().format(DATE_FORMATTER) : "N/A";

        logger.info("--------------------------------------------------");
        logger.info("ID             : {}", event.id());
        logger.info("Title          : {}", event.title());
        logger.info("Location       : {}", event.location());
        logger.info("Start          : {}", formattedStart);
        logger.info("End            : {}", formattedEnd);
        logger.info("Max Participants: {}", event.maxParticipants());
        logger.info("Description    : {}", event.description());
        logger.info("--------------------------------------------------");
      }

      if (currentPage + 1 < totalPages) {
        logger.info("Press ENTER for next page or type 'q' to quit.");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) {
          break;
        }
      }
      currentPage++;
    }
  }
}
