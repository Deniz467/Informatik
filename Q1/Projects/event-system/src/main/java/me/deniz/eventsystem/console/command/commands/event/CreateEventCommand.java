package me.deniz.eventsystem.console.command.commands.event;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.argument.arguments.IntegerArgument;
import me.deniz.eventsystem.console.argument.arguments.StringArgument;
import me.deniz.eventsystem.console.argument.arguments.ZonedDateTimeArgument;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.session.UserPermission;

public final class CreateEventCommand extends ConsoleCommand {

  private final EventService eventService;

  public CreateEventCommand(EventService eventService) {
    super(
        "create-event",
        "<title> <description> <location> <startDate> <maxParticipants> [endDate]",
        "Create a new event",
        UserPermission.CREATE_EVENT
    );
    this.eventService = eventService;

    withArgument(new StringArgument("title"));
    withArgument(new StringArgument("description"));
    withArgument(new StringArgument("location"));
    withArgument(new ZonedDateTimeArgument("startDate"));
    withArgument(new IntegerArgument("maxParticipants", 1));
    withOptionalArgument(new ZonedDateTimeArgument("endDate"));
  }

  @Override
  public CompletableFuture<?> executeAsync(ParsedArguments args) {
    final String title = args.get("title");
    final String description = args.get("description");
    final String location = args.get("location");
    final ZonedDateTime startDate = args.get("startDate");
    final int maxParticipants = args.get("maxParticipants");
    final Optional<ZonedDateTime> endDate = args.getOptional("endDate");

    require(location.length() <= 255, "Location must not exceed 255 characters");
    require(title.length() <= 255, "Title must not exceed 255 characters");
    endDate.ifPresent(end -> require(end.isAfter(startDate), "End date must be after start date"));

    return eventService.createEvent(title, description, location, startDate, endDate.orElse(null), maxParticipants)
        .thenAcceptAsync(result -> logger.info("Created event: {}", result));
  }
}
