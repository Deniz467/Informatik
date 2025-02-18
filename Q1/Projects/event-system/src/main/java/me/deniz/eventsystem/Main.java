package me.deniz.eventsystem;

import me.deniz.eventsystem.config.EventSystemConfig;
import me.deniz.eventsystem.db.DBConnection;

public class Main {

  @SuppressWarnings("ResultOfMethodCallIgnored")
  public static void main(String[] args) {
    // Load config file
    EventSystemConfig.get();

    // Connect to the database
    DBConnection.INSTANCE.getClass();




    DBConnection.INSTANCE.close();
  }
}