package dev.alnat.plugin_platform.plugin;

import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
public interface Plugin {

    PluginResponse call(PluginRequest request);

}
