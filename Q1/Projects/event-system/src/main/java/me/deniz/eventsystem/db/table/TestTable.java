package me.deniz.eventsystem.db.table;

import me.deniz.eventsystem.db.DBConnection;

public final class TestTable {

  private static final String TABLE_NAME = "test_table";

  public static void createIfNotExists() {
    DBConnection.INSTANCE.executeQueryAsync(
        "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + "id INT PRIMARY KEY AUTO_INCREMENT,"
            + "name VARCHAR(255) NOT NULL"
            + ");"
    );
  }
}
