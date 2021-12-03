package dev.alnat.plugin_platform.core;

import dev.alnat.plugin_platform.core.model.PluginUsage;
import dev.alnat.plugin_platform.plugin.Plugin;

import java.util.Optional;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
public interface PluginPlatform {

    /**
     *
     * @param pluginName
     * @return
     */
    Optional<Plugin> loadPlugin(String pluginName);

    /**
     *
     * @param pluginName
     * @return
     */
    Optional<PluginUsage> getUsage(String pluginName);

    /**
     *
     * @param pluginName
     * @return
     */
    Optional<Boolean> clear(String pluginName);

}
