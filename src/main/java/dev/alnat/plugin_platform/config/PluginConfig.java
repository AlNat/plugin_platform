package dev.alnat.plugin_platform.config;

import lombok.Data;
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
     * Сейчас берется из application.yml
     */
    private Map<String, String> classes;

    public Optional<String> resolve(String name) {
        if (classes.containsKey(name)) {
            return Optional.of(classes.get(name));
        }

        return Optional.empty();
    }

}
