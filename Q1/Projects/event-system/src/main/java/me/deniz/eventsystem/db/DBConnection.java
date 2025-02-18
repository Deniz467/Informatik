package me.deniz.eventsystem.db;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.deniz.eventsystem.config.EventSystemConfig.DatabaseConfig;
import org.intellij.lang.annotations.Language;

public final class DBConnection {

  private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());
  public static final DBConnection INSTANCE = new DBConnection();

  private final String url;
  private final String user;
  private final String password;

  private final ExecutorService executorService = Executors.newCachedThreadPool(
      new ThreadFactoryBuilder()
          .setNameFormat("db-connection-%d")
          .setUncaughtExceptionHandler((t, e) ->
              LOGGER.log(Level.SEVERE, "Uncaught exception in thread " + t.getName(), e))
          .build()
  );

  private Connection connection;

  private DBConnection() {
    final DatabaseConfig config = DatabaseConfig.get();

    this.url = "jdbc:mysql://%s:%d/%s".formatted(config.host(), config.port(), config.database());
    this.user = config.username();
    this.password = config.password();

    connect();
  }

  private void connect() {
    try {
      if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(url, user, password);
        LOGGER.info("Database connection established successfully.");
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error connecting to the database: " + e.getMessage(), e);
      throw new RuntimeException("Database connection failed.", e);
    }
  }

  public Future<Void> executeQueryAsync(@Language("sql") String query) {
    return executeQueryAsync(query, resultSet -> null);
  }

  public <T> Future<T> executeQueryAsync(
      @Language("sql") String query,
      Function<ResultSet, T> mapper
  ) {
    return executorService.submit(() -> {
      try (final PreparedStatement statement = connection.prepareStatement(query);
          final ResultSet resultSet = statement.executeQuery()) {
        return mapper.apply(resultSet);
      } catch (SQLException e) {
        throw new RuntimeException("Error executing query.", e);
      }
    });
  }

  public void close() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        LOGGER.info("Database connection closed successfully.");
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error closing database connection: " + e.getMessage(), e);
    } finally {
      executorService.shutdown();
    }
  }
}
