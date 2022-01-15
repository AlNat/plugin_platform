package dev.alnat.plugin_platform.api;

import dev.alnat.plugin_platform.core.model.PluginUsage;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Created by @author AlNat on 02.12.2021.
 * Licensed by Apache License, Version 2.0
 */
@Tag(name = "API для взаимодействия с платформой")
public interface PlatformAPI {

    @Operation(summary = "Вызов плагина")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача запущена и выполнена"),
            @ApiResponse(responseCode = "400", description = "Не корректный запрос", content = @Content),
            @ApiResponse(responseCode = "404", description = "Плагин не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = @Content)
    })
    PluginResponse call(@Parameter(description = "Имя плагина", required = true, in = ParameterIn.PATH) String pluginName,
                        @RequestBody(required = true) PluginRequest request);

    @Operation(summary = "Получение статистики вызовов плагина")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача запущена и выполнена"),
            @ApiResponse(responseCode = "400", description = "Не корректный запрос", content = @Content),
            @ApiResponse(responseCode = "404", description = "Плагин не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = @Content)
    })
    PluginUsage getStatistics(@Parameter(description = "Имя плагина", required = true, in = ParameterIn.PATH) String pluginName);

    @Operation(summary = "Удаление плагина из кеша")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Плагин удален"),
            @ApiResponse(responseCode = "400", description = "Не корректный запрос", content = @Content),
            @ApiResponse(responseCode = "404", description = "Плагин не найден", content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка", content = @Content)
    })
    void clear(@Parameter(description = "Имя плагина", required = true, in = ParameterIn.PATH) String pluginName);

}
