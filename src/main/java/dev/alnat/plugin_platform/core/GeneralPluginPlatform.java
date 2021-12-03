package dev.alnat.plugin_platform.core;

import dev.alnat.plugin_platform.config.PluginConfig;
import dev.alnat.plugin_platform.core.mapper.PluginMapper;
import dev.alnat.plugin_platform.core.model.PluginHolder;
import dev.alnat.plugin_platform.core.model.PluginUsage;
import dev.alnat.plugin_platform.plugin.Plugin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GeneralPluginPlatform implements PluginPlatform {

    private final PluginConfig config;
    private final PluginMapper mapper;
    private final AutowireCapableBeanFactory beanFactory;

    /**
     * Хранилище закешированных плагинов
     */
    private final Map<String, PluginHolder> plugins = new HashMap<>();

    private URLClassLoader classLoader;

    @PostConstruct
    public void init() {
        String path = config.getPluginsFolder();

        try {
            URL pathURL = new File(path).toURI().toURL();
            classLoader = URLClassLoader.newInstance(new URL[]{pathURL});
        } catch (MalformedURLException e) {
            log.error(""); // TODO
            throw new IllegalStateException("", e);
        }
    }

    /**
     * Получение плагина
     * Если плагин закеширован -- отдаем из кеша
     * Если нет -- пробуем его найти и загрузить если есть информация о нем
     *  Если информации нет - просто игнорируем
     */
    @Override
    public synchronized Optional<Plugin> loadPlugin(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            log.info("Found cached plugin {}", pluginName);
            return Optional.of(plugins.get(pluginName).getPlugin());
        }

        // Если не нашли -- пробуем найти плагин в файлах
        log.info("Plugin {} not found, try to find it", pluginName);

        Optional<String> pluginClassNameOpt = config.resolve(pluginName);
        if (pluginClassNameOpt.isEmpty()) {
            log.warn("Not found configuration to {}", pluginName);
            return Optional.empty();
        }

        Plugin plugin = loadPluginFromResources(pluginClassNameOpt.get());
        log.info("To {} found class {}", pluginName, plugin.getClass().getSimpleName());

        cachePlugin(pluginName, plugin);

        return Optional.of(plugin);
    }

    private Plugin loadPluginFromResources(String pluginClass) {
        Class clazz = null;
        try {
            clazz = classLoader.loadClass(pluginClass);
        } catch (ClassNotFoundException e) {
            log.error(""); // TODO
            throw new IllegalStateException("", e);
        }

        Plugin plugin;

        try {
            plugin = (Plugin) clazz
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            log.error(""); // TODO
            throw new IllegalStateException("", e);
        }

        // Внедряем туда все бины через Autowire
        beanFactory.autowireBean(plugin);

        return plugin;
    }

    private void cachePlugin(String pluginName, Plugin plugin) {
        plugins.put(pluginName, PluginHolder.create(plugin));
    }

    @Override
    public Optional<PluginUsage> getUsage(String pluginName) {
        if (!plugins.containsKey(pluginName)) {
            return Optional.empty();
        }

        return Optional.of(mapper.from(plugins.get(pluginName)));
    }

    @Override
    public Optional<Boolean> clear(String pluginName) {
        if (plugins.containsKey(pluginName)) {
            log.info("Clear cached plugin {}", pluginName);
            plugins.remove(pluginName);
            return Optional.of(Boolean.TRUE);
        }

        log.info("Not found plugin {} to clear", pluginName);
        return Optional.of(Boolean.FALSE);
    }

}
