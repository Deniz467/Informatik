package me.deniz.eventsystem.console.command.commands.event;

import static com.google.common.base.Preconditions.checkNotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.argument.arguments.IntegerArgument;
import me.deniz.eventsystem.console.argument.arguments.StringArgument;
import me.deniz.eventsystem.console.argument.arguments.ZonedDateTimeArgument;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.console.command.ContextAwareConsoleCommand;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.session.UserPermission;

public class EditEventCommand extends ContextAwareConsoleCommand {

  private final EventService eventService;
  private Event event;

  public EditEventCommand(EventService eventService) {
    super(
        "edit-event",
        "<title>",
        "Edit an event",
        "done",
        UserPermission.EDIT_EVENT,
        List.of()
    );
    this.eventService = eventService;

    withArgument(new StringArgument("title"));

    withContextCommand(new EditTitleCommand());
    withContextCommand(new EditDescriptionCommand());
    withContextCommand(new EditLocationCommand());
    withContextCommand(new EditStartDateCommand());
    withContextCommand(new EditEndDateCommand());
    withContextCommand(new EditMaxParticipantsCommand());
    withContextCommand(new DeleteEventCommand());
  }

  @Override
  protected CompletableFuture<?> doExecuteAsync(ParsedArguments args) {
    String title = args.get("title");

    return eventService.findEventsByTitle(title).thenAcceptAsync(events -> {
      checkState(!events.isEmpty(), "No event found with title '%s'".formatted(title));

      if (events.size() > 1) {
        event = selectEventById(events, title);
      } else {
        event = events.get(0);
      }

      logger.info("Editing event '{}'", event.title());
    });
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

  private class EditTitleCommand extends ConsoleCommand {

    public EditTitleCommand() {
      super("title", "<newTitle>", "Edit the event title", null);
      withArgument(new StringArgument("newTitle"));
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      final String newTitle = args.get("newTitle");

      return eventService.updateEventTitle(event, newTitle).thenAcceptAsync(updatedEvent -> {
        logger.info("Updated event title to '{}'", updatedEvent.title());
        event = updatedEvent;
      });
    }
  }

  private class EditDescriptionCommand extends ConsoleCommand {

    public EditDescriptionCommand() {
      super("description", "<newDescription>", "Edit the event description", null);
      withArgument(new StringArgument("newDescription"));
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      final String newDescription = args.get("newDescription");

      return eventService.updateEventDescription(event, newDescription)
          .thenAcceptAsync(updatedEvent -> {
            logger.info("Updated event description to '{}'", updatedEvent.description());
            event = updatedEvent;
          });
    }
  }

  private class EditLocationCommand extends ConsoleCommand {

    public EditLocationCommand() {
      super("location", "<newLocation>", "Edit the event location", null);
      withArgument(new StringArgument("newLocation"));
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      final String newLocation = args.get("newLocation");

      return eventService.updateEventLocation(event, newLocation).thenAcceptAsync(updatedEvent -> {
        logger.info("Updated event location to '{}'", updatedEvent.location());
        event = updatedEvent;
      });
    }
  }

  private class EditStartDateCommand extends ConsoleCommand {

    public EditStartDateCommand() {
      super("start-date", "<newStartDate>", "Edit the event start date", null);
      withArgument(new ZonedDateTimeArgument("newStartDate"));
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      final ZonedDateTime newStartDate = args.get("newStartDate");

      return eventService.updateEventStartDate(event, newStartDate)
          .thenAcceptAsync(updatedEvent -> {
            logger.info("Updated event start date to '{}'", updatedEvent.start());
            event = updatedEvent;
          });
    }
  }

  private class EditEndDateCommand extends ConsoleCommand {

    public EditEndDateCommand() {
      super("end-date", "<newEndDate>", "Edit the event end date", null);
      withArgument(new ZonedDateTimeArgument("newEndDate"));
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      final ZonedDateTime newEndDate = args.get("newEndDate");

      return eventService.updateEventEndDate(event, newEndDate).thenAcceptAsync(updatedEvent -> {
        logger.info("Updated event end date to '{}'", updatedEvent.end());
        event = updatedEvent;
      });
    }
  }

  private class EditMaxParticipantsCommand extends ConsoleCommand {

    public EditMaxParticipantsCommand() {
      super("max-participants", "<newMaxParticipants>", "Edit the event max participants", null);
      withArgument(new IntegerArgument("newMaxParticipants", 0));
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      final int newMaxParticipants = args.get("newMaxParticipants");

      return eventService.updateEventMaxParticipants(event, newMaxParticipants)
          .thenAcceptAsync(updatedEvent -> {
            logger.info("Updated event max participants to '{}'", updatedEvent.maxParticipants());
            event = updatedEvent;
          });
    }
  }

  private class DeleteEventCommand extends ConsoleCommand {

    public DeleteEventCommand() {
      super("delete", "", "Delete the event", null);
    }

    @Override
    public CompletableFuture<?> executeAsync(ParsedArguments args) {
      return CompletableFuture.supplyAsync(() -> {
        logger.info("Are you sure you want to delete the event '{}'? (yes/no)", event.title());

        final Scanner scanner = new Scanner(System.in);
        final String input = scanner.nextLine();

        return input.equalsIgnoreCase("yes");
      }).thenComposeAsync(shouldDelete -> {
        if (shouldDelete) {
          return eventService.deleteEvent(event).thenAcceptAsync($ -> {
            logger.info("Deleted event '{}'", event);
          });
        }

        return CompletableFuture.completedFuture(null);
      });
    }
  }
}
