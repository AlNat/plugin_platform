package dev.alnat.plugin_platform.plugin.plugins;

import dev.alnat.plugin_platform.plugin.Plugin;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;
import lombok.NoArgsConstructor;

/**
 * Пример плагина
 * Лучше сделать отдельным модулем, для удобства сделан в этом же проекте
 *
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@NoArgsConstructor
public class FirstPlugin implements Plugin {

    @Override
    public PluginResponse call(PluginRequest request) {
        return PluginResponse.builder()
                .response("Process response to request " + request.getRequest())
                .build();
    }

}
