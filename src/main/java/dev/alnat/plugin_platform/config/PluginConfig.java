package dev.alnat.plugin_platform.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Optional;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "custom.plugins")
public class PluginConfig {

    private String pluginsFolder;

    /**
     * Конфигурация где ключ -- имя плагина, значение -- полный путь к классу
     *
     * Может быть в БД, файлах и тд
     * Для удобства вшито в виде мапы
     */
    private static final Map<String, String> PLUGIN_CLASSES = Map.of(
            "first", "dev.alnat.plugin_platform.plugin.plugins.FirstPlugin",
            "second", "dev.alnat.plugin_platform.plugin.plugins.SecondPlugin"
    );

    public Optional<String> resolve(String name) {
        if (PLUGIN_CLASSES.containsKey(name)) {
            return Optional.of(PLUGIN_CLASSES.get(name));
        }

        return Optional.empty();
    }

}
