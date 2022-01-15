package dev.alnat.plugin_platform.api;

import dev.alnat.plugin_platform.core.plaform.PluginPlatform;
import dev.alnat.plugin_platform.core.model.PluginUsage;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by @author AlNat on 02.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@RestController
@RequestMapping(value = "/api/platform", produces = {"application/json; charset=UTF-8"})
@RequiredArgsConstructor
public class PlatformController implements PlatformAPI {

    private static final String PLUGIN_NOT_FOUND_MESSAGE = "Plugin not found in cache";

    private final PluginPlatform platform;

    @Override
    @ResponseStatus(OK)
    @PostMapping("/plugin/{pluginName}")
    public PluginResponse call(@PathVariable String pluginName,
                               @RequestBody PluginRequest request) {
        final var resOpt = platform.call(pluginName, request);
        if (resOpt.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, PLUGIN_NOT_FOUND_MESSAGE);
        }

        return resOpt.get();
    }

    @Override
    @ResponseStatus(OK)
    @GetMapping("/statistics/{pluginName}")
    public PluginUsage getStatistics(@PathVariable String pluginName) {
        final var result = platform.getUsage(pluginName);
        if (result.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, PLUGIN_NOT_FOUND_MESSAGE);
        }

        return result.get();
    }

    @Override
    @DeleteMapping("/plugin/{pluginName}")
    @ResponseStatus(ACCEPTED)
    public void clear(@PathVariable String pluginName) {
        final var result = platform.clear(pluginName);
        if (!result) {
            throw new ResponseStatusException(NOT_FOUND, PLUGIN_NOT_FOUND_MESSAGE);
        }
    }

}
