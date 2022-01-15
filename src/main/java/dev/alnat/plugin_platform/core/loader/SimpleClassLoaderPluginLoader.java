package dev.alnat.plugin_platform.core.loader;

import dev.alnat.plugin_platform.config.PluginConfig;
import dev.alnat.plugin_platform.plugin.Plugin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Загрузчик плагинов из файлов через стандартный classloader
 *
 * Created by @author AlNat on 15.01.2022.
 * Licensed by Apache License, Version 2.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SimpleClassLoaderPluginLoader implements PluginLoader {

    private URLClassLoader classLoader;

    private final PluginConfig config;
    private final AutowireCapableBeanFactory beanFactory;

    @PostConstruct
    public void init() {
        final String path = config.getPluginsFolder();

        try {
            URL pathURL = new File(path).toURI().toURL();
            classLoader = URLClassLoader.newInstance(new URL[]{pathURL});
        } catch (MalformedURLException e) {
            log.error("Configure plugin path ({}) is not valid!", path);
            throw new IllegalStateException("Can't create class loader", e);
        }
    }

    public Plugin loadPlugin(String pluginClass) {
        final Class clazz;
        try {
            clazz = classLoader.loadClass(pluginClass);
        } catch (ClassNotFoundException e) {
            log.error("Cant find plugin [{}]", pluginClass);
            throw new IllegalArgumentException("Cant find plugin!", e);
        }

        final Plugin plugin;
        try {
            plugin = (Plugin) clazz
                    .getConstructor()
                    .newInstance();
        } catch (Exception e) {
            log.error("Exception at constructing plugin [{}]", pluginClass, e);
            throw new IllegalStateException(e);
        }

        // Внедряем туда все бины через Autowire
        beanFactory.autowireBean(plugin);

        return plugin;
    }

}
