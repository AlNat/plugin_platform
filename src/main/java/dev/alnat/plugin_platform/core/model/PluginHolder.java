package dev.alnat.plugin_platform.core.model;

import dev.alnat.plugin_platform.plugin.Plugin;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@Data
@AllArgsConstructor
public class PluginHolder {

    private Plugin plugin;
    private LocalDateTime loaded;
    private LocalDateTime lastCalled;
    private Long callCount;

    public static PluginHolder create(Plugin plugin) {
        return new PluginHolder(plugin, LocalDateTime.now(), LocalDateTime.now(), 1L);
    }

    public Plugin get() {
        this.lastCalled = LocalDateTime.now();
        this.callCount++;
        return this.plugin;
    }

}
