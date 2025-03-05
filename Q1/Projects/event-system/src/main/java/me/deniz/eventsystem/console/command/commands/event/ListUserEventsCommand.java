package me.deniz.eventsystem.console.command.commands.event;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.argument.ParsedArguments;
import me.deniz.eventsystem.console.argument.arguments.LongArgument;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.service.UserEventsService;
import me.deniz.eventsystem.session.SessionHolder;
import me.deniz.eventsystem.session.UserPermission;

public class ListUserEventsCommand extends ConsoleCommand {

  private final UserEventsService userEventsService;

  public ListUserEventsCommand(UserEventsService userEventsService) {
    super(
        "list-events",
        "[user-id]",
        "List events for a user or yourself",
        UserPermission.LIST_EVENTS
    );
    this.userEventsService = userEventsService;

    withOptionalArgument(new LongArgument("user-id"));
  }

  @Override
  public CompletableFuture<?> executeAsync(ParsedArguments args) {
    final long userId = args.getOrDefault("user-id", SessionHolder.requiredSession().id());

    return userEventsService.getEventsForUser(userId).thenAcceptAsync(events -> {
      logger.info("Events for user with ID {}: ", userId);
      ListEventsGlobalCommand.listEvents(events, logger);
    });
  }
}
