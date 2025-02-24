package me.deniz.eventsystem.config;

import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.helper.ConfigurationHelper;

public final class ConfigManager<C> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

  private final ConfigurationHelper<C> configHelper;
  private volatile C configData;

  private ConfigManager(ConfigurationHelper<C> configHelper) {
    this.configHelper = configHelper;
  }

  public static <C> ConfigManager<C> create(Path configFolder, String fileName,
      Class<C> configClass) {
    final SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder()
        .commentMode(CommentMode.alternativeWriter())
        .build();

    final ConfigurationFactory<C> configFactory = SnakeYamlConfigurationFactory.create(
        configClass,
        ConfigurationOptions.defaults(),
        yamlOptions
    );

    return new ConfigManager<>(new ConfigurationHelper<>(configFolder, fileName, configFactory));
  }

  public void reloadConfig() {
    try {
      configData = configHelper.reloadConfigData();
    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    } catch (ConfigFormatSyntaxException ex) {
      configData = configHelper.getFactory().loadDefaults();
      LOGGER.error("""
          The yaml syntax in your configuration is invalid.
          Check your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/
          """.trim(), ex);
    } catch (InvalidConfigException ex) {
      configData = configHelper.getFactory().loadDefaults();
      LOGGER.error("""
          One of the values in your configuration is not valid.
          Check to make sure you have specified the right data types.
          """.trim(), ex);
    }
  }

  public C getConfigData() {
    C configData = this.configData;
    checkState(configData != null, "Configuration has not been loaded yet");

    return configData;
  }
}