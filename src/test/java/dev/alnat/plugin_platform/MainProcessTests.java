package dev.alnat.plugin_platform;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Набор тестов по-общему процессу (по интеграционные тесты)
 * Все действия производятся исключительно через API эмулируя стандартный процесс для blackbox тестирования
 */
@SpringBootTest({
        "custom.plugins.plugins-folder=classpath:/", // Указываем на файл с ресурсами для загрузки
        "custom.plugins.classes.first=dev.alnat.plugin_platform.plugin.plugins.FirstPlugin", // Вручную прописываем плагины из списка
        "custom.plugins.classes.second=dev.alnat.plugin_platform.plugin.plugins.SecondPlugin"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@DirtiesContext // Сбрасываем контекст после выполнения
class MainProcessTests extends BaseTest {

    /**
     * Шаг 1
     * Запуск адаптера 'first' в первый раз
     * Адаптер должен подгрузиться в кеш и выполниться.
     * Ответ должен быть корректным
     */
    @Test
    @Order(1)
    void testCallFirstPluginInFirst() {
        final String response = getResponseToPluginRequest(FIRST_PLUGIN_NAME, "test");

        Assertions.assertEquals("Process response to request test", response, 
                "First plugin works not expected, check plugins in test resources");
    }

    /**
     * Шаг 2
     * Проверка статистики по адаптеру 'first'
     * Должна вернуться значение 1
     */
    @Test
    @Order(2)
    void testStatisticsOfFirstPluginAfterCall() {
        final Long requestCount = getStatistics(FIRST_PLUGIN_NAME);

        Assertions.assertEquals(1L, requestCount,
                "Plugin statistics is not correct, check test orders");
    }

    /**
     * Шаг 3
     * Вызов другого плагина
     * Адаптер должен подгрузиться в кеш и выполниться.
     * Ответ должен быть корректным
     */
    @Test
    @Order(3)
    void testCallSecondPlugin() {
        final String response = getResponseToPluginRequest(SECOND_PLUGIN_NAME, "test");

        Assertions.assertEquals("Json representation request is {\"request\":\"test\"}", response,
                "Second plugin works not expected, check plugins in test resources");
    }

    /**
     * Шаг 4
     * Повторный вызов первого плагина
     * Адаптер уже в кеше, ответ должен быть корректным
     */
    @Test
    @Order(4)
    void testAnotherCallFirstPlugin() {
        final String response = getResponseToPluginRequest(FIRST_PLUGIN_NAME, "test2");

        Assertions.assertEquals("Process response to request test2", response,
                "First plugin works not expected, check plugins in test resources");
    }

    /**
     * Шаг 5
     * Проверка статистики по адаптеру 'first'
     * Должна вернуться значение 2 (т.к. было 2 вызова)
     */
    @Test
    @Order(5)
    void testStatisticsOfFirstPluginAfterSecondCall() {
        final Long requestCount = getStatistics(FIRST_PLUGIN_NAME);

        Assertions.assertEquals(2L, requestCount,
                "Plugin statistics is not correct, check test orders");
    }

    /**
     * Щаг 6
     * Удаление адаптера 'first' из кеша
     * Должен вернуться код 200 и плагин должен быть удален в кеше
     */
    @Test
    @Order(6)
    void deleteFirstPlugin() {
        deletePlugin(FIRST_PLUGIN_NAME);
    }

    /**
     * Щаг 7
     * Проверка статистики по адаптеру 'second'
     * Должна вернуться значение 1 (т.к. был 1 вызов)
     */
    @Test
    @Order(7)
    void testStatisticsOfSecondPluginAfterDelete() {
        final Long requestCount = getStatistics(SECOND_PLUGIN_NAME);

        Assertions.assertEquals(1L, requestCount,
                "Plugin statistics is not correct, check test orders");
    }

}
