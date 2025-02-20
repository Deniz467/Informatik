package me.deniz.eventsystem.event;

import com.google.common.base.MoreObjects;
import java.time.ZonedDateTime;
import javax.annotation.Nullable;

public record Event(long id, String title, String description, String location, ZonedDateTime start,
                    @Nullable ZonedDateTime end, int maxParticipants) {

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("title", title)
        .add("description", description)
        .add("location", location)
        .add("start", start)
        .add("end", end)
        .add("maxParticipants", maxParticipants)
        .toString();
  }
}
