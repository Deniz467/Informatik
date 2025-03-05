package me.deniz.eventsystem.console.command.commands.event;

import java.util.concurrent.CompletableFuture;
import me.deniz.eventsystem.console.command.ConsoleCommand;
import me.deniz.eventsystem.session.UserPermission;

public final class CreateEventCommand extends ConsoleCommand {

  public CreateEventCommand() {
    super(
        "create-event",
        "<title> <description> <location> <startDate> <maxParticipants> [endDate]",
        "Create a new event",
        UserPermission.CREATE_EVENT
    );
  }

  @Override
  public CompletableFuture<?> executeAsync(String[] args) {
    return CompletableFuture.runAsync(() -> {
      checkRequiredArgs(args, 5, 6);
      final String title = args[0];
      final String description = args[1];
      final String rawLocation = args[2];
      final String rawStartDate = args[3];
      final String rawMaxParticipants = args[4];
      final String rawEndDate = args.length > 5 ? args[5] : null;

      require(rawLocation.length() <= 255, "Location must not exceed 255 characters");

    });
  }
}
