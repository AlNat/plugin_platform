package dev.alnat.plugin_platform.config;

import dev.alnat.plugin_platform.core.loader.PluginLoader;
import dev.alnat.plugin_platform.core.loader.SimpleClassLoaderPluginLoader;
import dev.alnat.plugin_platform.core.plaform.CacheablePluginPlatform;
import dev.alnat.plugin_platform.core.plaform.PluginPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Выбираем какая из реализация загрузчика и платформы нужна
 *
 * Created by @author AlNat on 16.01.2022.
 * Licensed by Apache License, Version 2.0
 */
@Configuration
public class PlatformRegistryConfiguration {

    @Bean
    @Primary
    public PluginLoader pluginLoader(SimpleClassLoaderPluginLoader loader) {
        return loader;
    }

    @Bean
    @Primary
    public PluginPlatform pluginPlatform(CacheablePluginPlatform platform) {
        return platform;
    }

}
