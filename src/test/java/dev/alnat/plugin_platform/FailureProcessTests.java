package dev.alnat.plugin_platform;

import dev.alnat.plugin_platform.core.loader.PluginLoader;
import dev.alnat.plugin_platform.plugin.Plugin;
import dev.alnat.plugin_platform.plugin.exception.PluginProcessingException;
import dev.alnat.plugin_platform.plugin.model.PluginRequest;
import dev.alnat.plugin_platform.plugin.model.PluginResponse;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Набор тестов с ошибкам работы
 * 
 * Created by @author AlNat on 15.01.2022.
 * Licensed by Apache License, Version 2.0
 */
@SpringBootTest({
        "custom.plugins.plugins-folder=classpath:/", // Указываем на файл с ресурсами для загрузки
        "custom.plugins.classes.first=dev.alnat.test", // Вручную прописываем плагины из списка
        "custom.plugins.classes.second=dev.alnat.plugin_platform.plugin.plugins.SecondPlugin"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@DirtiesContext // Сбрасываем контекст после выполнения
class FailureProcessTests extends BaseTest {

    private static final String FIRST_PLUGIN_PATH = "dev.alnat.test";

    // Мокаем загрузчик плагинов, будем создавать свои плагины на-лету
    @MockBean
    private PluginLoader mockedLoader;

    @SuppressWarnings("Convert2Lambda") // Для наглядности
    private final Plugin failurePlugin = new Plugin() {
        @Override
        public PluginResponse call(PluginRequest request) throws PluginProcessingException {
            throw new IllegalArgumentException("Test exception");
        }
    };


    /**
     * Тест-кейс
     * Вызов плагина которого нет в конфигурации
     * Ожидается 404 код в ответе на в
     */
    @Test
    void testCallNotExistsPlugin() {
        final int status = getFailureResponseToPluginRequest(THIRD_PLUGIN_NAME, "test");

        Assertions.assertEquals(404, status, "Platform returns unexpected code, check test configuration");
    }

    @Test
    void testGetStatisticsOfNotExistsPlugin() {
        final int status = deleteNotExistsPlugin(THIRD_PLUGIN_NAME);

        Assertions.assertEquals(404, status, "Platform returns unexpected code, check test configuration");
    }

    @Test
    void testPluginFailureDuringWorking() {
        Mockito.when(mockedLoader.loadPlugin(FIRST_PLUGIN_PATH))
                .thenReturn(failurePlugin);

        final int status = getFailureResponseToPluginRequest(FIRST_PLUGIN_NAME, "test");

        Assertions.assertEquals(500, status, "Platform returns unexpected code, check plugin configuration");
    }

}
