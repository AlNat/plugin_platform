package dev.alnat.plugin_platform.core.loader;

import dev.alnat.plugin_platform.plugin.Plugin;

/**
 * Загрузчик плагинов
 *
 * Created by @author AlNat on 15.01.2022.
 * Licensed by Apache License, Version 2.0
 */
public interface PluginLoader {

    Plugin loadPlugin(String pluginClass);

}
