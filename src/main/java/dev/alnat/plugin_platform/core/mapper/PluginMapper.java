package dev.alnat.plugin_platform.core.mapper;

import dev.alnat.plugin_platform.core.model.PluginHolder;
import dev.alnat.plugin_platform.core.model.PluginUsage;
import org.mapstruct.Mapper;

/**
 * Created by @author AlNat on 02.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@Mapper(componentModel = "spring")
public interface PluginMapper {

    PluginUsage from(PluginHolder holder);

}
