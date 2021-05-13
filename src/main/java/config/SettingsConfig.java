package config;

import lombok.extern.slf4j.Slf4j;
import model.Settings;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

@Slf4j
public class SettingsConfig {
    public static Settings load() {
        Yaml yaml = new Yaml(new Constructor(Settings.class));
        InputStream inputStream = SettingsConfig.class
                .getClassLoader()
                .getResourceAsStream("settings.yaml");
        Settings settings = yaml.load(inputStream);
        log.info("Settings: {}", settings);
        return settings;
    }
}
