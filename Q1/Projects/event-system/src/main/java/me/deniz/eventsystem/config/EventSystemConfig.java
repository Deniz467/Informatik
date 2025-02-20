package me.deniz.eventsystem.config;


import java.nio.file.Path;
import space.arim.dazzleconf.annote.ConfDefault.DefaultInteger;
import space.arim.dazzleconf.annote.ConfDefault.DefaultString;
import space.arim.dazzleconf.annote.SubSection;

public interface EventSystemConfig {
  @SubSection
  DatabaseConfig database();

  interface DatabaseConfig {

    @DefaultString("sql7.freesqldatabase.com")
    String host();

    @DefaultString("sql7763365")
    String username();

    @DefaultString("password")
    String password();

    @DefaultString("sql7763365")
    String database();

    @DefaultInteger(3306)
    int port();

    static DatabaseConfig get() {
      return EventSystemConfig.get().database();
    }
  }

  static EventSystemConfig get() {
    class Holder {

      private static final ConfigManager<EventSystemConfig> manager = ConfigManager.create(
          Path.of("configs"),
          "config.yml",
          EventSystemConfig.class
      );

      static {
        manager.reloadConfig();
      }
    }

    return Holder.manager.getConfigData();
  }
}
