package dev.alnat.plugin_platform.plugin.plugins;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.alnat.plugin_platform.plugin.Plugin;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Пример плагина c инъекциями бинов
 * Лучше сделать отдельным модулем, для удобства сделан в этом же проекте
 *
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@NoArgsConstructor
public class SecondPlugin implements Plugin {

    @Autowired
    private ObjectMapper mapper;

    @Override
    @SneakyThrows
    public PluginResponse call(PluginRequest request) {
        return PluginResponse.builder()
                .response("Json representation request is " + mapper.writeValueAsString(request))
                .build();
    }

}
