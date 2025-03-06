package me.deniz.eventsystem.console.command.commands.event;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.argument.arguments.EventArgument;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.event.Event;
import me.deniz.eventsystem.service.EventService;
import me.deniz.eventsystem.service.UserEventsService;
import me.deniz.eventsystem.session.SessionHolder;
import me.deniz.eventsystem.session.UserPermission;

public class JoinEventCommand extends ConsoleCommand {

  private final EventService eventService;
  private final UserEventsService userEventsService;

  public JoinEventCommand(EventService eventService, UserEventsService userEventsService) {
    super("join-event", "<event>", "Join an event", UserPermission.JOIN_EVENT);
    this.eventService = eventService;
    this.userEventsService = userEventsService;

    withArgument(new EventArgument("event", eventService));

  }

  @Override
  public CompletableFuture<?> executeAsync(ParsedArguments args) {
    final Event event = args.get("event");

    return eventService.seatsAvailable(event.id()).thenComposeAsync(seats -> {
      checkState(seats > 1, "No seats available");

      return userEventsService.registerUserForEvent(SessionHolder.requiredSession().id(),
          event.id());
    });
  }
}
