package me.deniz.eventsystem.db;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.deniz.eventsystem.config.EventSystemConfig.DatabaseConfig;
import org.intellij.lang.annotations.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBConnection {

  private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);
  public static final DBConnection INSTANCE = new DBConnection();

  private final String url;
  private final String user;
  private final String password;

  private final ExecutorService executorService = Executors.newCachedThreadPool(
      new ThreadFactoryBuilder()
          .setNameFormat("db-connection-%d")
          .setUncaughtExceptionHandler((t, e) ->
              LOGGER.error("Uncaught exception in thread {}", t.getName(), e))
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
      LOGGER.error("Error connecting to the database: {}", e.getMessage(), e);
      throw new RuntimeException("Database connection failed.", e);
    }
  }

  public <T> CompletableFuture<T> updateAsync(
      @Language("sql") String query,
      StatementSetter statementSetter,
      ResultSetMapper<T> mapper
  ) {
    return CompletableFuture.supplyAsync(() -> {
      try (final PreparedStatement statement = connection.prepareStatement(query,
          Statement.RETURN_GENERATED_KEYS)) {
        statementSetter.set(statement);

        statement.executeUpdate();
        try (final ResultSet resultSet = statement.getGeneratedKeys()) {
          return mapper.map(resultSet);
        }

      } catch (SQLException e) {
        throw new RuntimeException("Error executing query.", e);
      }
    }, executorService);
  }

  public CompletableFuture<Void> executeStatementAsync(
      @Language("sql") String statement,
      StatementSetter statementSetter
  ) {
    return CompletableFuture.runAsync(() -> {
      try (final PreparedStatement preparedStatement = connection.prepareStatement(statement)) {
        statementSetter.set(preparedStatement);
        preparedStatement.execute();
      } catch (SQLException e) {
        throw new RuntimeException("Error executing statement.", e);
      }
    }, executorService);
  }

  public void close() {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
        LOGGER.info("Database connection closed successfully.");
      }
    } catch (SQLException e) {
      LOGGER.error("Error closing database connection: {}", e.getMessage(), e);
    } finally {
      executorService.shutdown();
    }
  }

  @FunctionalInterface
  public interface StatementSetter {

    void set(PreparedStatement preparedStatement) throws SQLException;
  }

  @FunctionalInterface
  public interface ResultSetMapper<T> {

    T map(ResultSet resultSet) throws SQLException;
  }

}
