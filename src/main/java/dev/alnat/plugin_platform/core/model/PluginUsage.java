package dev.alnat.plugin_platform.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@Data
public class PluginUsage {
    private LocalDateTime loaded;
    private LocalDateTime lastCalled;
    private Long callCount;
}
