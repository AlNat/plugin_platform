package dev.alnat.plugin_platform.api;

import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;

/**
 * Created by @author AlNat on 02.12.2021.
 * Licensed by Apache License, Version 2.0
 */
public interface PlatformAPI {

    PluginResponse call(PluginRequest request);
    // TODO
}
