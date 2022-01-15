package dev.alnat.plugin_platform.core.plaform;

import dev.alnat.plugin_platform.core.model.PluginUsage;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;

import java.util.Optional;

/**
 * Created by @author AlNat on 01.12.2021.
 * Licensed by Apache License, Version 2.0
 */
public interface PluginPlatform {

    /**
     * Вызов плагина
     *
     * @param pluginName имя плагина
     * @param request запрос плагина
     * @return ответ от плагина или пустота, если не нашли плагин
     */
    Optional<PluginResponse> call(String pluginName, PluginRequest request);

    /**
     * Получение статистики по плагину
     *
     * @param pluginName имя плагина
     * @return статистика, или пусто если он не закеширован
     */
    Optional<PluginUsage> getUsage(String pluginName);

    /**
     * Очистка плагина из кеша
     *
     * @param pluginName имя плагина
     * @return true если очистка успешна и false если плагина нет
     */
    Boolean clear(String pluginName);

}
